package com.obss.marketplace.mapper;

import com.obss.marketplace.dto.ProductDTO;
import com.obss.marketplace.model.Category;
import com.obss.marketplace.model.Product;
import com.obss.marketplace.model.Seller;
import com.obss.marketplace.model.User;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-15T10:12:26+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductDTO toDTO(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();

        productDTO.setCategoryName( productCategoryName( product ) );
        productDTO.setSellerFirstName( productSellerUserFirstName( product ) );
        productDTO.setSellerLastName( productSellerUserLastName( product ) );
        productDTO.setId( product.getId() );
        productDTO.setName( product.getName() );
        productDTO.setDescription( product.getDescription() );
        productDTO.setPrice( product.getPrice() );
        productDTO.setQuantity( product.getQuantity() );
        productDTO.setImageUrl( product.getImageUrl() );

        return productDTO;
    }

    @Override
    public Product toEntity(ProductDTO productDTO) {
        if ( productDTO == null ) {
            return null;
        }

        Product product = new Product();

        product.setId( productDTO.getId() );
        product.setName( productDTO.getName() );
        product.setDescription( productDTO.getDescription() );
        product.setPrice( productDTO.getPrice() );
        product.setQuantity( productDTO.getQuantity() );
        product.setImageUrl( productDTO.getImageUrl() );

        return product;
    }

    private String productCategoryName(Product product) {
        if ( product == null ) {
            return null;
        }
        Category category = product.getCategory();
        if ( category == null ) {
            return null;
        }
        String name = category.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String productSellerUserFirstName(Product product) {
        if ( product == null ) {
            return null;
        }
        Seller seller = product.getSeller();
        if ( seller == null ) {
            return null;
        }
        User user = seller.getUser();
        if ( user == null ) {
            return null;
        }
        String firstName = user.getFirstName();
        if ( firstName == null ) {
            return null;
        }
        return firstName;
    }

    private String productSellerUserLastName(Product product) {
        if ( product == null ) {
            return null;
        }
        Seller seller = product.getSeller();
        if ( seller == null ) {
            return null;
        }
        User user = seller.getUser();
        if ( user == null ) {
            return null;
        }
        String lastName = user.getLastName();
        if ( lastName == null ) {
            return null;
        }
        return lastName;
    }
}
