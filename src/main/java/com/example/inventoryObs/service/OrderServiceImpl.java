package com.example.inventoryObs.service;

import com.example.inventoryObs.entity.Order;
import com.example.inventoryObs.exception.ResourceNotFoundException;
import com.example.inventoryObs.projection.InventoryProjection;
import com.example.inventoryObs.repository.InventoryRepository;
import com.example.inventoryObs.repository.OrderRepository;
import com.example.inventoryObs.request.InventoryRequest;
import com.example.inventoryObs.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService{
  @Autowired
  OrderRepository orderRepository;
  @Autowired
  InventoryRepository inventoryRepository;
  @Autowired
  InventoryService inventoryService;
  @Override
  public Map<String, Object> getAllOrder(Integer pageNo, Integer pageSize, String sortBy, String sortDirection){
    Sort.Direction direction;
    try {
      direction = Sort.Direction.valueOf(sortDirection.trim().toUpperCase());
    } catch (IllegalArgumentException | NullPointerException e) {
      direction = Sort.Direction.ASC;
    }
    Map<String, Object> result = new HashMap<>();
    Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));
    Page<Order> pagedResult = orderRepository.findAll(paging);
    result.put("data", pagedResult.getContent());
    result.put("total_result", pagedResult.getTotalElements());
    result.put("total_page", pagedResult.getTotalPages());

    return result;
  }

  @Override
  public Order getOrderById(Long id) {
    return orderRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Item not found for this id :: " + id));
  }

  @Override
  public String createOrder(OrderRequest orderRequest) {
    try {
      if (orderRequest == null) {
        throw new IllegalArgumentException("Order request cannot be null");
      }

      List<InventoryProjection> inventories = inventoryRepository.getInventoryByItemId(orderRequest.getItem_id());
      Long stock = 0L;
      for (InventoryProjection inventory: inventories) {
        if(Objects.equals(inventory.getType(), "T")){
          stock = stock + inventory.getQty();
        }else{
          stock = stock - inventory.getQty();
        }
      }
      if(stock < orderRequest.getQty()){
        return "Insufficient stock";
      }

      Long longOrderNo = orderRepository.getMaxId()+1;
      String orderNo = longOrderNo.toString();

      Order order = new Order();
      order.setQty(orderRequest.getQty());
      order.setItem_id(orderRequest.getItem_id());
      order.setOrder_no("O"+orderNo);

      orderRepository.save(order);

      InventoryRequest inventoryRequest = new InventoryRequest();
      inventoryRequest.setItem_id(orderRequest.getItem_id());
      inventoryRequest.setQty(orderRequest.getQty());
      inventoryRequest.setType("W");
      inventoryService.createInventory(inventoryRequest);

      return "Order created";
    } catch (Exception e) {
      e.printStackTrace();
      return "Error creating order: " + e.getMessage();
    }
  }

  @Override
  public Order updateOrder(Long id, OrderRequest orderDetails) {
    Order order = orderRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + id));

    order.setOrder_no(orderDetails.getOrder_no());
    order.setItem_id(orderDetails.getItem_id());
    order.setQty(orderDetails.getQty());

    return orderRepository.save(order);
  }

  @Override
  public void deleteOrder(Long id) {
    Order order = orderRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + id));
    orderRepository.delete(order);
  }
}
