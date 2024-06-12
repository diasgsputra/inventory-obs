package com.example.inventoryObs.entity;

import javax.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "item")
public class Item {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private Long price;
}
