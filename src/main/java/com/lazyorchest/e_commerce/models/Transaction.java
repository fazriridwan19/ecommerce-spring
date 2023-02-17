package com.lazyorchest.e_commerce.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue
    private Long id;
    private Boolean isSuccess = false;
    private Double totalAmount;
    private LocalDateTime createdAt;
    @OneToOne(cascade = CascadeType.PERSIST)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "cart_detail_id", referencedColumnName = "id")
    private CartDetail cartDetail;
}
