package fr.ekod.cda.ja.tp7.mapper;

import fr.ekod.cda.ja.tp7.dto.user.RegisterRequestDTO;
import fr.ekod.cda.ja.tp7.dto.user.UserDTO;
import fr.ekod.cda.ja.tp7.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDto(User user);
    User toEntity(RegisterRequestDTO userDTO);

}
