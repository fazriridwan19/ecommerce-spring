package com.lazyorchest.e_commerce.repositories;

import com.lazyorchest.e_commerce.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {
}
