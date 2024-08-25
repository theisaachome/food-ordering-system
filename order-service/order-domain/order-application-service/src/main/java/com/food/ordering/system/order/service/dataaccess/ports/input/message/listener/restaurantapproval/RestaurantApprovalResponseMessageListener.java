package com.food.ordering.system.order.service.dataaccess.ports.input.message.listener.restaurantapproval;

import com.food.ordering.system.order.service.dataaccess.dto.message.RestaurantApprovalResponse;

public interface RestaurantApprovalResponseMessageListener {
    void orderApproved(RestaurantApprovalResponse restaurantApprovalResponse);
    void orderRejected(RestaurantApprovalResponse restaurantApprovalResponse);
}
