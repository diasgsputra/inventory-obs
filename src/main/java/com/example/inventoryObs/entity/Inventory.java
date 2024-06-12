package com.example.inventoryObs.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "inventory")
public class Inventory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long item_id;
  private Long qty;
  private String type;
}
