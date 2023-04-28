package com.example.DigitalBankService.mappers;

import com.example.DigitalBankService.dtos.CustomerDTO;
import com.example.DigitalBankService.entities.Customer;

public interface IBankEntitiesMapper {
    CustomerDTO fromCustomer(Customer customer);
    Customer fromCustomerDto(CustomerDTO customerDTO);

}
