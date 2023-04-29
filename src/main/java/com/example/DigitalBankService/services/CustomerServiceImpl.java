package com.example.DigitalBankService.services;

import com.example.DigitalBankService.dao.CustomerRepository;
import com.example.DigitalBankService.dtos.CustomerDTO;
import com.example.DigitalBankService.entities.Customer;
import com.example.DigitalBankService.exceptions.CustomerNotFoundException;
import com.example.DigitalBankService.mappers.IBankEntitiesMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service @Slf4j
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    private IBankEntitiesMapper dtoMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, IBankEntitiesMapper dtoMapper) {
        this.customerRepository = customerRepository;
        this.dtoMapper = dtoMapper;
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("Saving a new customer");
        Customer savedCustomer = customerRepository.save(dtoMapper.fromCustomerDto(customerDTO));
        return dtoMapper.fromCustomer(savedCustomer);
    }
    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        Customer savedCustomer = customerRepository.save(dtoMapper.fromCustomerDto(customerDTO));
        return dtoMapper.fromCustomer(savedCustomer);
    }
    @Override
    public void deleteCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    @Override
    public List<CustomerDTO> getCustomers() {
        List<Customer> customers = customerRepository.findAll();

        return customers
                .stream()
                .map(customer -> dtoMapper.fromCustomer(customer))
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {

        return dtoMapper.fromCustomer(
                customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new)
        );
    }
}
