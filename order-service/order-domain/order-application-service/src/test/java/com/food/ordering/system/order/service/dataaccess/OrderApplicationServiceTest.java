package com.food.ordering.system.order.service.dataaccess;

import com.food.ordering.system.order.service.dataaccess.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.dataaccess.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.dataaccess.dto.create.OrderAddress;
import com.food.ordering.system.order.service.dataaccess.dto.create.OrderItem;
import com.food.ordering.system.order.service.dataaccess.entity.Customer;
import com.food.ordering.system.order.service.dataaccess.entity.Order;
import com.food.ordering.system.order.service.dataaccess.entity.Product;
import com.food.ordering.system.order.service.dataaccess.entity.Restaurant;
import com.food.ordering.system.order.service.dataaccess.mapper.OrderDataMapper;
import com.food.ordering.system.order.service.dataaccess.ports.input.service.OrderApplicationService;
import com.food.ordering.system.order.service.dataaccess.ports.output.repository.CustomerRepository;
import com.food.ordering.system.order.service.dataaccess.ports.output.repository.OrderRepository;
import com.food.ordering.system.order.service.dataaccess.ports.output.repository.RestaurantRepository;
import com.food.ordering.system.valueobject.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = OrderTestConfiguration.class)
public class OrderApplicationServiceTest {

    @Autowired
    private OrderApplicationService orderApplicationService;
    @Autowired
    private OrderDataMapper orderDataMapper;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;

    private CreateOrderCommand createOrderCommand;
    private CreateOrderCommand createOrderCommandWrongPrice;
    private CreateOrderCommand createOrderCommandWrongProductPrice;

    private final UUID CUSTOMER_ID = UUID.fromString("b0df0430-fdd2-42fb-a4f1-7253731817aa");
    private final UUID RESTAURANT_ID = UUID.fromString("2981658b-45bc-4271-a152-17f8e78c8474");
    private final UUID PRODUCT_ID = UUID.fromString("f7258ff8-bc55-4c21-9cd5-d99606e66f43");
    private final UUID ORDER_ID= UUID.fromString("9edb53f2-8780-4ff4-8d68-67d856fb3fc8");
    private final BigDecimal PRICE = new BigDecimal("200.00");

    @BeforeAll
    public  void init(){
        createOrderCommand = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .orderAddress(OrderAddress.builder()
                        .street("Street_1")
                        .postalCode("60234")
                        .city("Yangon")
                        .build())
                .price(PRICE)
                .orderItems(List.of(
                        OrderItem.builder()
                        .productId(PRODUCT_ID)
                        .quantity(1)
                        .price(new BigDecimal("50.00"))
                        .subTotal(new BigDecimal("50.00"))
                        .build(),
                        OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(3)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("150.00"))
                                .build()
                ))
                .build();
        createOrderCommandWrongPrice = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .orderAddress(OrderAddress.builder()
                        .street("Street_1")
                        .postalCode("60234")
                        .city("Yangon")
                        .build())
                .price(new BigDecimal("250.00"))
                .orderItems(List.of(
                        OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(1)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("50.00"))
                                .build(),
                        OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(1)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("150.00"))
                                .build()))
                .build();
        createOrderCommandWrongProductPrice = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .orderAddress(OrderAddress.builder()
                        .street("Street_1")
                        .postalCode("60234")
                        .city("Yangon")
                        .build())
                .price(new BigDecimal("210.00"))
                .orderItems(List.of(
                        OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(1)
                                .price(new BigDecimal("60.00"))
                                .subTotal(new BigDecimal("60.00"))
                                .build()
                        ,
                        OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(1)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("150.00"))
                                .build()
                                ))
                .build();

        Customer customer = new Customer();
        customer.setId(new CustomerId(CUSTOMER_ID));

        Restaurant restaurantResponse = Restaurant.builder()
                .restaurantId(new RestaurantId(RESTAURANT_ID))
                .products(List.of(
                        new Product(new ProductId(PRODUCT_ID),"Product-1",new Money(new BigDecimal("50.00"))),
                        new Product(new ProductId(PRODUCT_ID),"Product-2",new Money(new BigDecimal("50.00")))))
                .active(true)
                .build();
        Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        order.setId(new OrderId(ORDER_ID));

        when(customerRepository.findCustomer(CUSTOMER_ID)).thenReturn(Optional.of(customer));
        when(restaurantRepository.findRestaurantInformation(orderDataMapper.createOrderCommandToRestaurant(createOrderCommand)))
                .thenReturn(Optional.of(restaurantResponse));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
    }
    @Test
    public  void test(){
        CreateOrderResponse createOrderResponse = orderApplicationService.createOrder(createOrderCommand);
        assertEquals(createOrderResponse.getOrderStatus(),OrderStatus.PENDING);
        assertEquals(createOrderResponse.getMessage(),"Order created successfully.");
//        assertNotNull(createOrderResponse.getTrackingId());
    }
}
