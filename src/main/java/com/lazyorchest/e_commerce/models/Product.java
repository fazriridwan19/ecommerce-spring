package com.lazyorchest.e_commerce.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    @Column(length = 100)
    private String name;
    private Double price;
    private Integer stock;
    @Column(length = 50)
    private String category;
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<CartDetail> cartDetailLis;
}
