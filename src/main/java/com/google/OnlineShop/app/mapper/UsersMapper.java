package com.google.OnlineShop.app.mapper;

import com.google.OnlineShop.app.entity.Users;
import com.google.OnlineShop.app.model.UsersModel;
import com.google.OnlineShop.config.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL
        , componentModel = "spring"
)
public interface UsersMapper extends BaseMapperConfig<Users, UsersModel> {

    static UsersMapper get() {
        return Mappers.getMapper(UsersMapper.class);
    }

    UsersModel entityToModel(Users entity);

    Users modelToEntity(UsersModel model);
}
