package com.food.ordering.system.order.service.dataaccess.ports.input.message.listener.payment;

import com.food.ordering.system.order.service.dataaccess.dto.message.PaymentResponse;

public interface PaymentResponseMessageListener {

    void paymentCompleted(PaymentResponse paymentResponse);
    void paymentCanceled(PaymentResponse paymentResponse);
}
