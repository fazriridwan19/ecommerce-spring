package com.lazyorchest.e_commerce.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart_detail")
public class CartDetail {
    @Id
    @GeneratedValue
    private Long id;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cart;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
    private Integer quantity;
    @JsonIgnore
    @OneToOne(mappedBy = "cartDetail")
    private Transaction transaction;
}
