package com.example.inventoryObs.controller;

import com.example.inventoryObs.entity.Inventory;
import com.example.inventoryObs.request.InventoryRequest;
import com.example.inventoryObs.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
  @Autowired
  InventoryService inventoryService;
  @GetMapping
  public ResponseEntity<Map<String, Object>> getAllInventory(
      @RequestParam(name = "page_no", defaultValue = "0") Integer pageNo,
      @RequestParam(name = "page_size", defaultValue = "10") Integer pageSize,
      @RequestParam(name = "sort_by", defaultValue = "id") String sortBy,
      @RequestParam(name = "sort", defaultValue = "asc") String sortDirection
  ) {
    Map<String, Object> items = inventoryService.getAllInventory(pageNo,pageSize,sortBy,sortDirection);
    return ResponseEntity.ok(items);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Inventory> getInventoryById(@PathVariable Long id) {
    Inventory inventory = inventoryService.getInventoryById(id);
    return ResponseEntity.ok(inventory);
  }

  @PostMapping
  public ResponseEntity<Inventory> createInventory(@RequestBody InventoryRequest inventory) {
    Inventory createdInventory = inventoryService.createInventory(inventory);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdInventory);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Inventory> updateInventory(@PathVariable Long id, @RequestBody InventoryRequest inventoryDetails) {
    Inventory updatedInventory = inventoryService.updateInventory(id, inventoryDetails);
    return ResponseEntity.ok(updatedInventory);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Map<String, Boolean>> deleteInventory(@PathVariable Long id) {
    inventoryService.deleteInventory(id);
    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return ResponseEntity.ok(response);
  }
}
