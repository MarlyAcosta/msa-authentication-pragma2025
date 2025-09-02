package co.com.bancolombia.api.mapper;

import co.com.bancolombia.api.dto.RegisterUserDTO;
import co.com.bancolombia.api.dto.ResponseUserDTO;
import co.com.bancolombia.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserDTOMapper {
  @Mapping(target = "id", ignore = true)
  User toModel(RegisterUserDTO registerUserDTO);
  ResponseUserDTO toResponse(User user, String message);
}