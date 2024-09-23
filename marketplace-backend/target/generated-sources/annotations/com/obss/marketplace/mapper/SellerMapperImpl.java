package com.obss.marketplace.mapper;

import com.obss.marketplace.dto.SellerDTO;
import com.obss.marketplace.model.Seller;
import com.obss.marketplace.model.User;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-15T10:12:26+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
public class SellerMapperImpl implements SellerMapper {

    @Override
    public SellerDTO toDTO(Seller seller) {
        if ( seller == null ) {
            return null;
        }

        SellerDTO sellerDTO = new SellerDTO();

        sellerDTO.setSellerId( seller.getId() );
        sellerDTO.setFirstName( sellerUserFirstName( seller ) );
        sellerDTO.setLastName( sellerUserLastName( seller ) );
        sellerDTO.setEmail( sellerUserEmail( seller ) );

        return sellerDTO;
    }

    private String sellerUserFirstName(Seller seller) {
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

    private String sellerUserLastName(Seller seller) {
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

    private String sellerUserEmail(Seller seller) {
        if ( seller == null ) {
            return null;
        }
        User user = seller.getUser();
        if ( user == null ) {
            return null;
        }
        String email = user.getEmail();
        if ( email == null ) {
            return null;
        }
        return email;
    }
}
