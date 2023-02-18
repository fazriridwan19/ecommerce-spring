package com.lazyorchest.e_commerce.controllers;

import com.lazyorchest.e_commerce.dto.CartDetailRequest;
import com.lazyorchest.e_commerce.dto.RegisterResponse;
import com.lazyorchest.e_commerce.models.Cart;
import com.lazyorchest.e_commerce.models.CartDetail;
import com.lazyorchest.e_commerce.models.User;
import com.lazyorchest.e_commerce.services.CartService;
import com.lazyorchest.e_commerce.services.TransactionService;
import com.lazyorchest.e_commerce.services.UserService;
import com.lazyorchest.e_commerce.utils.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final JwtService jwtService;
    private final UserService userService;
    private final TransactionService transactionService;
    @Operation(summary = "Add product to cart", description = "This endpoint will take one parameter and one body request as input, " +
            "the provided input used to find product and bring it to cart", tags = "Cart Controller")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cart has been created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CartDetail.class))
            }),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @PostMapping("/v1/carts/add/product/{productId}")
    public ResponseEntity<CartDetail> addProductToCart(HttpServletRequest request, @PathVariable Long productId, @RequestBody CartDetailRequest cartDetailRequest) {
        final String token = request.getHeader("Authorization").substring(7);
        final String username = jwtService.extractUsername(token);
        User user = userService.getUserByUsername(username);
        Cart cart = cartService.findByCurrentUser(user);
        CartDetail cartDetail = cartService.addProductToCart(productId, cart, cartDetailRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartDetail);
    }
//    @GetMapping("/v1/carts")
//    public ResponseEntity<Cart> readAllCartByCurrentUser(HttpServletRequest request) {
//        final String token = request.getHeader("Authorization").substring(7);
//        final String username = jwtService.extractUsername(token);
//        User user = userService.getUserByUsername(username);
//        return ResponseEntity.ok(cartService.findByCurrentUser(user));
//    }
    @Operation(summary = "Get all product stored in cart", description = "This endpoint will find all product " +
            "that has been stored in cart by current user", tags = "Cart Controller")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get all products in current user cart", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CartDetail.class))
            }),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @GetMapping("/v1/carts/detail")
    public ResponseEntity<List<CartDetail>> readAllCartDetailByCurrentUser(HttpServletRequest request) {
        final String token = request.getHeader("Authorization").substring(7);
        final String username = jwtService.extractUsername(token);
        User user = userService.getUserByUsername(username);
        return ResponseEntity.ok(cartService.findAllCartDetailByCurrentUser(user));
    }
    @Operation(summary = "Get product stored in cart by id", description = "This endpoint will find one product " +
            "that has been stored in cart by current user based on cart detail id. " +
            "If some of product was already checkout then product will not be shown in the cart", tags = "Cart Controller")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get product in current user cart by id", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CartDetail.class))
            }),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @GetMapping("/v1/carts/detail/{id}")
    public ResponseEntity<CartDetail> readCurrentUserCartDetailById(HttpServletRequest request, @PathVariable Long id) {
        final String token = request.getHeader("Authorization").substring(7);
        final String username = jwtService.extractUsername(token);
        User user = userService.getUserByUsername(username);
        Cart cart = cartService.findByCurrentUser(user);
        return ResponseEntity.ok(cartService.findCartDetailById(cart, id));
    }
    @Operation(summary = "Update quantity of product stored in cart", description = "This endpoint will take one parameter " +
            "and one body request as input, the provided input used to find product and update the quantity", tags = "Cart Controller")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Quantity product has been updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CartDetail.class))
            }),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @PutMapping("/v1/carts/detail/{id}")
    public ResponseEntity<CartDetail> updateQuantityCurrentUserCartDetailById(HttpServletRequest request, @PathVariable Long id, @RequestBody CartDetailRequest cartDetailRequest) {
        final String token = request.getHeader("Authorization").substring(7);
        final String username = jwtService.extractUsername(token);
        User user = userService.getUserByUsername(username);
        Cart cart = cartService.findByCurrentUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.updateQuantityCartDetail(cart, id, cartDetailRequest));
    }
    @Operation(summary = "Delete product from cart", description = "This endpoint will take one parameter as input, " +
            "the provided input used to find product and delete it from cart", tags = "Cart Controller")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product has been deleted from cart", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))
            }, useReturnTypeSchema = true),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @DeleteMapping("/v1/carts/detail/{id}")
    public ResponseEntity<Map<String, String>> deleteCurrentUserCartDetailById(HttpServletRequest request, @PathVariable Long id) {
        final String token = request.getHeader("Authorization").substring(7);
        final String username = jwtService.extractUsername(token);
        User user = userService.getUserByUsername(username);
        Cart cart = cartService.findByCurrentUser(user);
        cartService.deleteCartDetailFromCart(cart, id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "product deleted");
        return ResponseEntity.ok(response);
    }
}
