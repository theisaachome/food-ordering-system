package com.food.ordering.system.order.service.application.ports.input.message.listener.payment;

import com.food.ordering.system.order.service.application.dto.message.PaymentResponse;

public interface PaymentResponseMessageListener {

    void paymentCompleted(PaymentResponse paymentResponse);
    void paymentCanceled(PaymentResponse paymentResponse);
}
