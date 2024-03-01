package com.services.productService.Service;

import com.services.productService.Dtos.ProductRequest;
import com.services.productService.Dtos.ProductResponse;
import com.services.productService.Model.Product;
import com.services.productService.Repository.ProductRepository;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Builder
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductRequest creatProduct(ProductRequest productRequest)
    {
        var savedProduct = mapToProduct(productRequest);
        productRepository.save(savedProduct);
        return productRequest;
    }
    public List<ProductResponse> getAllProduct()
    {

        List<Product> products = productRepository.findAll();

        return products.stream().map(this::mapToProductResponse).toList();
    }
    private Product mapToProduct(ProductRequest productRequest)
    {
        return Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
    }
    private ProductResponse mapToProductResponse(Product product)
    {

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }


}
