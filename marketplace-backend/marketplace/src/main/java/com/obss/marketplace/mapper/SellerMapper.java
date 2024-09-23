package com.obss.marketplace.mapper;

import com.obss.marketplace.dto.SellerDTO;
import com.obss.marketplace.model.Seller;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SellerMapper {
    SellerMapper INSTANCE = Mappers.getMapper(SellerMapper.class);

    @Mapping(source = "id", target = "sellerId")
    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.lastName", target = "lastName")
    @Mapping(source = "user.email", target = "email")
    SellerDTO toDTO(Seller seller);
}
