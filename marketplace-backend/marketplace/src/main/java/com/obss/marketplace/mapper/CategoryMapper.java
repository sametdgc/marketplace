package com.obss.marketplace.mapper;

import com.obss.marketplace.dto.CategoryDTO;
import com.obss.marketplace.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDTO toDTO(Category category);

    Category toEntity(CategoryDTO categoryDTO);

    List<CategoryDTO> toDTOs(List<Category> categories);
}
