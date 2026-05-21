package ir.aspireapps.authservice.mapper;

import ir.aspireapps.authservice.dto.user.UserDetailsResponse;
import ir.aspireapps.authservice.dto.user.UserRegisterRequest;
import ir.aspireapps.authservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserMapper {

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "passwordUpdatedAt", ignore = true)
    User toEntity(UserRegisterRequest request);

    UserDetailsResponse toResponse(User user);
}
