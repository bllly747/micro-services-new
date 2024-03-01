package com.services.productService.Controller;

import com.services.productService.Dtos.ProductRequest;
import com.services.productService.Dtos.ProductResponse;
import com.services.productService.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductRequest createProduct(
            @RequestBody ProductRequest productRequest
    )
    {
        return productService.creatProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProduct()
    {
        return productService.getAllProduct();
    }
}
