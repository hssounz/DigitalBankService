package com.example.DigitalBankService.web;

import com.example.DigitalBankService.entities.Customer;
import com.example.DigitalBankService.services.IBankAccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@RequestMapping("/customers")
public class CustomerController {
    private IBankAccountService bankAccountService;

    public CustomerController(IBankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/customers")
    public List<Customer> customers() {
        return bankAccountService.getCustomers();
    }
}
