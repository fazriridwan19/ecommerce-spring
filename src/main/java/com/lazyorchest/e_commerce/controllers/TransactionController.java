package com.lazyorchest.e_commerce.controllers;

import com.lazyorchest.e_commerce.dto.PaymentRequest;
import com.lazyorchest.e_commerce.models.*;
import com.lazyorchest.e_commerce.services.CartService;
import com.lazyorchest.e_commerce.services.TransactionService;
import com.lazyorchest.e_commerce.services.UserService;
import com.lazyorchest.e_commerce.utils.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    private final CartService cartService;
    private final JwtService jwtService;
    private final UserService userService;
    @Operation(summary = "Checkout the provided product in cart", tags = "Transaction Controller")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Transaction.class))
            }),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @PostMapping("/v1/transactions/checkout/cart-detail/{cartDetailId}")
    public ResponseEntity<Transaction> checkout(HttpServletRequest request, @PathVariable Long cartDetailId, @RequestBody PaymentRequest paymentRequest) {
        final String token = request.getHeader("Authorization").substring(7);
        final String username = jwtService.extractUsername(token);
        User user = userService.getUserByUsername(username);
        Cart cart = cartService.findByCurrentUser(user);
        CartDetail cartDetail = cartService.findCartDetailById(cart, cartDetailId);
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.createTransaction(cartDetail, paymentRequest.getAmount()));
    }
    @Operation(summary = "Get all transactions", tags = "Transaction Controller")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Transaction.class))
            }),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @GetMapping("/v1/transactions")
    public ResponseEntity<List<Transaction>> getAllCurrentUserTransaction(HttpServletRequest request) {
        final String token = request.getHeader("Authorization").substring(7);
        final String username = jwtService.extractUsername(token);
        User user = userService.getUserByUsername(username);
        Cart cart = cartService.findByCurrentUser(user);
        return ResponseEntity.ok(transactionService.getAllCurrentUserTransaction(cart));
    }
}
