package com.example.Bank.dto;

import com.example.Bank.entity.BankAccountEntity;
import com.example.Bank.entity.CustomerEntity;
import com.example.Bank.entity.TransactionEntity;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@org.mapstruct.Mapper
public interface BankMapper {
    BankMapper INSTANCE = Mappers.getMapper(BankMapper.class);

    @Mapping(target = "customer.accounts", ignore = true) // Avoid cyclic mapping
    CustomerEntity customerDtoToEntity(CustomerDTO dto);

    CustomerDTO customerEntityToDto(CustomerEntity entity);

    @Mapping(target = "customer.accounts", ignore = true)
    BankAccountEntity bankAccountDtoToEntity(BankAccountDTO dto);

    BankAccountDTO bankAccountEntityToDto(BankAccountEntity entity);

    TransactionEntity transactionDtoToEntity(TransactionDTO dto);

    TransactionDTO transactionEntityToDto(TransactionEntity entity);
}
