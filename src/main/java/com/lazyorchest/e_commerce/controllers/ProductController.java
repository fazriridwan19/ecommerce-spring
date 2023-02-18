package com.lazyorchest.e_commerce.controllers;

import com.lazyorchest.e_commerce.dto.ProductRequest;
import com.lazyorchest.e_commerce.models.Product;
import com.lazyorchest.e_commerce.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @Operation(summary = "Create new product", description = "This endpoint required admin authority to generate new product", tags = "Product Controller")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product has been created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))
            }),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @PostMapping("/v1/products")
    public ResponseEntity<Product> create(@RequestBody ProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(request));
    }
    @Operation(summary = "Get all products", tags = "Product Controller")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))
            }),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @SecurityRequirements
    @GetMapping("/v1/products")
    public ResponseEntity<List<Product>> readProducts(@RequestParam(name = "category") Optional<String> category) {

        return ResponseEntity.ok(productService.readAllProduct(category.orElse("ALL")));
    }
    @Operation(summary = "Get product by id", tags = "Product Controller")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))
            }),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @SecurityRequirements
    @GetMapping("/v1/products/{id}")
    public ResponseEntity<Product> readProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.readProductById(id));
    }
    @Operation(summary = "Update product by id", description = "This endpoint required admin authority to update product", tags = "Product Controller")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))
            }),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @PutMapping("/v1/products/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody ProductRequest request, @PathVariable Long id) {
        return ResponseEntity.ok(productService.updateProduct(request, id));
    }
    @Operation(summary = "Delete product by id", description = "This endpoint required admin authority to delete product", tags = "Product Controller")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))
            }),
            @ApiResponse(responseCode = "403", description = "Bad Request", content = @Content)
    })
    @DeleteMapping("/v1/products/{id}")
    public ResponseEntity<Map<String, String>> deleteProduct(@PathVariable Long id) {
        Map<String, String> map = new HashMap<>();
        map.put("message", "product deleted");
        productService.deleteProduct(id);
        return ResponseEntity.ok(map);
    }
}
