package com.obss.marketplace.service;

import com.obss.marketplace.dto.ProductDTO;
import com.obss.marketplace.dto.ProductUpdateDTO;
import com.obss.marketplace.exception.CategoryNotFoundException;
import com.obss.marketplace.exception.ProductNotFoundException;
import com.obss.marketplace.exception.SellerNotFoundException;
import com.obss.marketplace.mapper.ProductMapper;
import com.obss.marketplace.model.Category;
import com.obss.marketplace.model.Product;
import com.obss.marketplace.model.Seller;
import com.obss.marketplace.repository.CategoryRepository;
import com.obss.marketplace.repository.ProductRepository;
import com.obss.marketplace.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SellerRepository sellerRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, SellerRepository sellerRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.sellerRepository = sellerRepository;
    }

    public Page<ProductDTO> findAll(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest).map(ProductMapper.INSTANCE::toDTO);
    }

    public Page<ProductDTO> findAllByCategory(Long categoryId, PageRequest pageRequest) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ProductNotFoundException("Category with ID " + categoryId + " not found"));
        return productRepository.findByCategory(category, pageRequest).map(ProductMapper.INSTANCE::toDTO);
    }

    public ProductDTO findById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + productId + " not found"));
        return ProductMapper.INSTANCE.toDTO(product);
    }

    public ProductDTO createProduct(ProductDTO productDTO, Long sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new SellerNotFoundException("Seller with ID " + sellerId + " not found"));

        Category category = categoryRepository.findByName(productDTO.getCategoryName())
                .orElseThrow(() -> new CategoryNotFoundException("Category with name " + productDTO.getCategoryName() + " not found"));

        Product product = ProductMapper.INSTANCE.toEntity(productDTO);
        product.setSeller(seller);
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);
        return ProductMapper.INSTANCE.toDTO(savedProduct);
    }


    public ProductDTO updateProduct(Long productId, ProductUpdateDTO productDTO) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + productId + " not found or does not belong to this seller"));


        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        product.setImageUrl(productDTO.getImageUrl());

        Product updatedProduct = productRepository.save(product);
        return ProductMapper.INSTANCE.toDTO(updatedProduct);
    }

    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + productId + " not found or does not belong to this seller"));

        productRepository.delete(product);
    }


    // product search
    public Page<ProductDTO> searchProducts(String query, PageRequest pageRequest) {
        return productRepository.findByNameContainingOrDescriptionContaining(query, query, pageRequest)
                .map(ProductMapper.INSTANCE::toDTO);
    }


    public Page<ProductDTO> findAllBySeller(Long sellerId, PageRequest pageRequest) {
        // Fetch products from the repository
        Page<Product> products = productRepository.findAllBySellerId(sellerId, pageRequest);

        // Convert Page<Product> to Page<ProductDTO> using a mapper
        return products.map(ProductMapper.INSTANCE::toDTO);
    }
}
