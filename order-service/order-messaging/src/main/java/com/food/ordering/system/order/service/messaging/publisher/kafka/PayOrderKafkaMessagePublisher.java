package com.food.ordering.system.order.service.messaging.publisher.kafka;

import com.food.ordering.system.kafka.config.data.KafkaConfigData;
import com.food.ordering.system.kafka.order.avro.model.RestaurantApprovalRequestAvroModel;
import com.food.ordering.system.kafka.producer.service.KafkaProducer;
import com.food.ordering.system.order.service.domain.config.OrderServiceConfigData;
import com.food.ordering.system.order.service.domain.event.OrderPaidEvent;
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.restaurantapproval.OrderPaidRestaurantRequestMessagePublisher;
import com.food.ordering.system.order.service.messaging.mapper.OrderMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PayOrderKafkaMessagePublisher implements OrderPaidRestaurantRequestMessagePublisher {

    private final OrderServiceConfigData orderServiceConfigData;
    private  final OrderMessagingDataMapper orderMessagingDataMapper;
    private  final KafkaProducer<String, RestaurantApprovalRequestAvroModel> kafkaProducer;
    private final OrderKafkaMessageHelper orderKafkaMessageHelper;

    public PayOrderKafkaMessagePublisher(OrderServiceConfigData orderServiceConfigData,
                                         OrderMessagingDataMapper orderMessagingDataMapper,
                                         KafkaProducer<String,RestaurantApprovalRequestAvroModel> kafkaProducer,
                                         OrderKafkaMessageHelper orderKafkaMessageHelper) {
        this.orderServiceConfigData = orderServiceConfigData;
        this.orderMessagingDataMapper = orderMessagingDataMapper;
        this.kafkaProducer = kafkaProducer;
        this.orderKafkaMessageHelper = orderKafkaMessageHelper;
    }

    @Override
    public void publish(OrderPaidEvent domainEvent) {
        String orderId = domainEvent.getOrder().getId().toString();
        log.info("Received OrderPaidEvent for Order id {}", orderId);
        try {
            RestaurantApprovalRequestAvroModel restaurantApprovalRequestAvroModel =orderMessagingDataMapper
                    .orderPaidEventToRestaurantApprovalRequestAvroModel(domainEvent);
            kafkaProducer.send(orderServiceConfigData.getRestaurantApprovalRequestTopicName(),
                    orderId,
                    restaurantApprovalRequestAvroModel,
                    orderKafkaMessageHelper.getKafkaCallback(
                            orderServiceConfigData.getRestaurantApprovalRequestTopicName(),
                            restaurantApprovalRequestAvroModel,
                            orderId,"RestaurantApprovalRequestAvroModel"));
            log.info("RestaurantApprovalRequestAvroModel sent to Kafka for Order ID:{}",orderId);
        } catch (Exception e) {
            log.error("Error while sending RestaurantApprovalRequestAvroModel message to Kafka for Order id: {}, error:{}", orderId, e.getMessage());
        }
    }
}
