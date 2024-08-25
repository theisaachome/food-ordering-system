package com.food.ordering.system.order.service.dataaccess;

import com.food.ordering.system.order.service.dataaccess.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.dataaccess.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.dataaccess.entity.Customer;
import com.food.ordering.system.order.service.dataaccess.entity.Order;
import com.food.ordering.system.order.service.dataaccess.entity.Restaurant;
import com.food.ordering.system.order.service.dataaccess.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.dataaccess.exception.OrderDomainException;
import com.food.ordering.system.order.service.dataaccess.mapper.OrderDataMapper;
import com.food.ordering.system.order.service.dataaccess.ports.output.repository.CustomerRepository;
import com.food.ordering.system.order.service.dataaccess.ports.output.repository.OrderRepository;
import com.food.ordering.system.order.service.dataaccess.ports.output.repository.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class OrderCreateCommandHandler {

    private final OrderDomainService orderDomainService;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrderDataMapper orderDataMapper;

    public OrderCreateCommandHandler(OrderDomainService orderDomainService,
                                     OrderRepository orderRepository,
                                     CustomerRepository customerRepository,
                                     RestaurantRepository restaurantRepository,
                                     OrderDataMapper orderDataMapper) {
        this.orderDomainService = orderDomainService;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.restaurantRepository = restaurantRepository;
        this.orderDataMapper = orderDataMapper;
    }

    @Transactional
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        checkCustomer(createOrderCommand.getCustomerId());
        Restaurant restaurant= checkRestaurant(createOrderCommand);
        Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        OrderCreatedEvent orderCreatedEvent= orderDomainService.validateAndInitiateOrder(order,restaurant);
        Order orderResult= saveOrder(orderCreatedEvent.getOrder());
        log.info("Order id created with successfully:{}", orderResult.getId().getValue());
        return orderDataMapper.orderToCreateOrderResponse(orderResult,"Order created successfully.");
    }


    private void checkCustomer(UUID customerId) {
        Optional<Customer> customer = customerRepository.findCustomer(customerId);
        if(customer.isEmpty()){
            log.warn("Could not find customer with id {}", customerId);
            throw new OrderDomainException("Could not find customer with id " + customerId);
        }
    }
    private Restaurant checkRestaurant(CreateOrderCommand createOrderCommand) {
        Restaurant restaurant = orderDataMapper.createOrderCommandToRestaurant(createOrderCommand);
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findRestaurantInformation(restaurant);
        if(optionalRestaurant.isEmpty()){
            log.warn("Could not find restaurant with id {}", restaurant.getId());
            throw new OrderDomainException("Could not find restaurant with id " + restaurant.getId());
        }
        return optionalRestaurant.get();
    }

    private Order saveOrder(Order order) {
        Order savedOrder = orderRepository.save(order);
        if(savedOrder == null){
            log.error("Could not save order");
            throw new OrderDomainException("Could not save order");
        }
        log.info("Saved order: {}", savedOrder);
        return savedOrder;
    }

}
