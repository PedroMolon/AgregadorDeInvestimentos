package com.pedromolon.agregadordeinvestimentos.mapper;

import com.pedromolon.agregadordeinvestimentos.dto.request.AccountRequest;
import com.pedromolon.agregadordeinvestimentos.dto.response.AccountResponse;
import com.pedromolon.agregadordeinvestimentos.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    Account toEntity(AccountRequest request);

    @Mapping(target = "username", source = "user.username")
    AccountResponse toResponse(Account account);

}
