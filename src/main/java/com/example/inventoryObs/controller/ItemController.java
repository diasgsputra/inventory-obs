package com.example.inventoryObs.controller;

import com.example.inventoryObs.entity.Item;
import com.example.inventoryObs.request.ItemRequest;
import com.example.inventoryObs.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/item")
public class ItemController {
  @Autowired
  ItemService itemService;
  @GetMapping
  public ResponseEntity<Map<String, Object>> getAllItems(
      @RequestParam(name = "page_no", defaultValue = "0") Integer pageNo,
      @RequestParam(name = "page_size", defaultValue = "10") Integer pageSize,
      @RequestParam(name = "sort_by", defaultValue = "id") String sortBy,
      @RequestParam(name = "sort", defaultValue = "asc") String sortDirection
  ) {
    Map<String, Object> items = itemService.getAllItem(pageNo,pageSize,sortBy,sortDirection);
    return ResponseEntity.ok(items);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Item> getItemById(@PathVariable Long id) {
    Item item = itemService.getItemById(id);
    return ResponseEntity.ok(item);
  }

  @PostMapping
  public ResponseEntity<Item> createItem(@RequestBody ItemRequest item) {
    Item createdItem = itemService.createItem(item);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Item> updateItem(@PathVariable Long id, @RequestBody ItemRequest itemDetails) {
    Item updatedItem = itemService.updateItem(id, itemDetails);
    return ResponseEntity.ok(updatedItem);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Map<String, Boolean>> deleteItem(@PathVariable Long id) {
    itemService.deleteItem(id);
    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return ResponseEntity.ok(response);
  }
}
