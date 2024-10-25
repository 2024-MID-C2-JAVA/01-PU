package com.bank.account.management.config;

import com.bank.account.management.mapper.BankAccountMapper;
import com.bank.account.management.mapper.CustomerMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public BankAccountMapper bankAccountMapper() {
        return Mappers.getMapper(BankAccountMapper.class);
    }

    @Bean
    public CustomerMapper customerMapperMapper() {
        return Mappers.getMapper(CustomerMapper.class);
    }
}
