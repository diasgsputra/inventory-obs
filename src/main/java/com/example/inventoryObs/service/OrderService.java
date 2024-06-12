package com.example.inventoryObs.service;

import com.example.inventoryObs.entity.Item;
import com.example.inventoryObs.entity.Order;
import com.example.inventoryObs.request.OrderRequest;

import java.util.Map;

public interface OrderService {
  Map<String, Object> getAllOrder(Integer pageNo, Integer pageSize, String sortBy, String sortDirection);
  Order getOrderById(Long id);
  String createOrder(OrderRequest order);
  Order updateOrder(Long id, OrderRequest orderDetails);
  void deleteOrder(Long id);
}
