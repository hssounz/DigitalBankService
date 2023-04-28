package com.example.DigitalBankService;

import com.example.DigitalBankService.dao.AccountOperationRepository;
import com.example.DigitalBankService.dao.BankAccountRepository;
import com.example.DigitalBankService.dao.CustomerRepository;
import com.example.DigitalBankService.entities.BankAccount;
import com.example.DigitalBankService.entities.CurrentsAccount;
import com.example.DigitalBankService.entities.Customer;
import com.example.DigitalBankService.enums.AccountStatus;
import com.example.DigitalBankService.exceptions.AccountOperationsException;
import com.example.DigitalBankService.exceptions.AccountTypeException;
import com.example.DigitalBankService.exceptions.BankAccountNotFoundException;
import com.example.DigitalBankService.exceptions.CustomerNotFoundException;
import com.example.DigitalBankService.services.IBankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class DigitalBankServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigitalBankServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(IBankAccountService bankAccountService) {
		return args -> {

			Stream.of("Hassen", "Leila", "Mohammed").forEach(c -> {
				Customer customer = new Customer();
				customer.setEmail(c + "@gmail.com");
				customer.setFullName(c);
				bankAccountService.saveCustomer(customer);
			});

			bankAccountService.getCustomers().forEach(customer -> {
				try {
					bankAccountService.saveBankAccount(
							Math.random()*9000,
							Math.random() < 0.5 ? "current" : "savings",
							customer.getId(),
							Math.random()*5
					);
				} catch (AccountTypeException | CustomerNotFoundException e) {
					throw new RuntimeException(e);
				}
			});

			bankAccountService.getBankAccounts().forEach(bankAccount -> {
				try {
					bankAccountService.depose(bankAccount.getId(), 100 + Math.random()*200, "depose cash");
					bankAccountService.withdrawal(bankAccount.getId(), 200 + Math.random()*200, "withdrawal");
				} catch (BankAccountNotFoundException | AccountOperationsException e) {
					throw new RuntimeException(e);
				}

			});


		};
	}

}
