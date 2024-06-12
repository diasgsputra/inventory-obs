package com.example.inventoryObs.controller;

import com.example.inventoryObs.entity.Order;
import com.example.inventoryObs.request.OrderRequest;
import com.example.inventoryObs.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
public class OrderController {
  @Autowired
  OrderService orderService;
  @GetMapping
  public ResponseEntity<Map<String, Object>> getAllOrder(
      @RequestParam(name = "page_no", defaultValue = "0") Integer pageNo,
      @RequestParam(name = "page_size", defaultValue = "10") Integer pageSize,
      @RequestParam(name = "sort_by", defaultValue = "id") String sortBy,
      @RequestParam(name = "sort", defaultValue = "asc") String sortDirection
  ) {
    Map<String, Object> order = orderService.getAllOrder(pageNo,pageSize,sortBy,sortDirection);
    return ResponseEntity.ok(order);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
    Order order = orderService.getOrderById(id);
    return ResponseEntity.ok(order);
  }

  @PostMapping
  public ResponseEntity<String> createOrder(@RequestBody OrderRequest order) {
    String createdOrder = orderService.createOrder(order);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody OrderRequest orderDetails) {
    Order updatedOrder = orderService.updateOrder(id, orderDetails);
    return ResponseEntity.ok(updatedOrder);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Map<String, Boolean>> deleteOrder(@PathVariable Long id) {
    orderService.deleteOrder(id);
    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return ResponseEntity.ok(response);
  }
}
