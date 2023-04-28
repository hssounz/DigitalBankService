package com.example.DigitalBankService.web;

import com.example.DigitalBankService.dtos.CustomerDTO;
import com.example.DigitalBankService.entities.Customer;
import com.example.DigitalBankService.exceptions.CustomerNotFoundException;
import com.example.DigitalBankService.services.IBankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/customers")
public class CustomerController {
    private IBankAccountService bankAccountService;

    public CustomerController(IBankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/customers")
    public List<CustomerDTO> customers() {
        return bankAccountService.getCustomers();
    }
    @GetMapping("/customers/{id}")
    public CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
        return bankAccountService.getCustomer(customerId);
    }

    @PostMapping("/customers")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO request) {

        /*
        * TODO
         */
    return null;
    }
}
