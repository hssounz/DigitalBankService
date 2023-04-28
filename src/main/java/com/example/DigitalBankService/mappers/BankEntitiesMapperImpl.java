package com.example.DigitalBankService.mappers;

import com.example.DigitalBankService.dtos.CustomerDTO;
import com.example.DigitalBankService.entities.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankEntitiesMapperImpl implements IBankEntitiesMapper {


    @Override
    public CustomerDTO fromCustomer(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        return customerDTO;
    }

    @Override
    public Customer fromCustomerDto(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }
}
