package com.lazyorchest.e_commerce.services;

import com.lazyorchest.e_commerce.models.Cart;
import com.lazyorchest.e_commerce.models.CartDetail;
import com.lazyorchest.e_commerce.models.Transaction;
import com.lazyorchest.e_commerce.models.User;
import com.lazyorchest.e_commerce.repositories.TransactionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepo transactionRepo;
    public Transaction createTransaction(CartDetail cartDetail, Double amount) {
//        Transaction transaction = Transaction.builder()
//                .createdAt(LocalDateTime.now())
//                .isSuccess(false)
//                .totalAmount(cartDetail.getQuantity() * cartDetail.getProduct().getPrice())
//                .cartDetail(cartDetail)
//                .build();
//        transactionRepo.save(transaction);
        return makePayment(cartDetail.getTransaction().getId(), amount);
    }
    public Transaction makePayment(Long id, Double amount) {
        Transaction transaction = transactionRepo.findById(id).orElseThrow();
        if (amount >= transaction.getTotalAmount()) {
            transaction.setIsSuccess(true);
        }
        transactionRepo.save(transaction);
        return transaction;
    }
    public Transaction getTransactionByCartDetail(CartDetail cartDetail) {
        return transactionRepo.findOne(
                Example.of(Transaction.builder().cartDetail(cartDetail).build())
        ).orElseThrow();
    }
    public List<Transaction> getAllCurrentUserTransaction(Cart cart) {
        CartDetail cartDetail = CartDetail.builder().cart(cart).build();
        return transactionRepo.findAll(
                Example.of(Transaction.builder().cartDetail(cartDetail).build())
        );
    }
}
