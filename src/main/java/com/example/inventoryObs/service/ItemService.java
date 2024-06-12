package com.example.inventoryObs.service;

import com.example.inventoryObs.entity.Item;
import com.example.inventoryObs.request.ItemRequest;

import java.util.List;
import java.util.Map;

public interface ItemService {
  Map<String, Object> getAllItem(Integer pageNo, Integer pageSize, String sortBy, String sortDirection);
  Item getItemById(Long id);
  Item createItem(ItemRequest item);
  Item updateItem(Long id, ItemRequest itemDetails);
  void deleteItem(Long id);
}
