package com.obss.marketplace.mapper;

import com.obss.marketplace.dto.ProductDTO;
import com.obss.marketplace.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "seller.user.firstName", target = "sellerFirstName")
    @Mapping(source = "seller.user.lastName", target = "sellerLastName")
    ProductDTO toDTO(Product product);

    Product toEntity(ProductDTO productDTO);
}
