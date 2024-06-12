package com.example.inventoryObs.service;

import com.example.inventoryObs.entity.Item;
import com.example.inventoryObs.exception.ResourceNotFoundException;
import com.example.inventoryObs.repository.ItemRepository;
import com.example.inventoryObs.request.ItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService {
  @Autowired
  ItemRepository itemRepository;
  @Override
  public Map<String, Object> getAllItem(Integer pageNo,Integer pageSize,String sortBy,String sortDirection){
    Sort.Direction direction;
    try {
      direction = Sort.Direction.valueOf(sortDirection.trim().toUpperCase());
    } catch (IllegalArgumentException | NullPointerException e) {
      direction = Sort.Direction.ASC;
    }
    Map<String, Object> result = new HashMap<>();
    Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));
    Page<Item> pagedResult = itemRepository.findAll(paging);
    result.put("data", pagedResult.getContent());
    result.put("total_result", pagedResult.getTotalElements());
    result.put("total_page", pagedResult.getTotalPages());

    return result;
  }

  @Override
  public Item getItemById(Long id) {
    return itemRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Item not found for this id :: " + id));
  }

  @Override
  public Item createItem(ItemRequest itemRequest) {
    Item item = new Item();
    item.setPrice(itemRequest.getPrice());
    item.setName(itemRequest.getName());
    return itemRepository.save(item);
  }

  @Override
  public Item updateItem(Long id, ItemRequest itemDetails) {
    Item item = itemRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Item not found for this id :: " + id));

    item.setName(itemDetails.getName());
    item.setPrice(itemDetails.getPrice());

    return itemRepository.save(item);
  }

  @Override
  public void deleteItem(Long id) {
    Item item = itemRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Item not found for this id :: " + id));
    itemRepository.delete(item);
  }
}
