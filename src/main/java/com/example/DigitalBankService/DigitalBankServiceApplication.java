package com.example.DigitalBankService;

import com.example.DigitalBankService.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DigitalBankServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigitalBankServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(BankAccountService bankAccountService) {
		return args -> {

//			Stream.of("Hassen", "Leila", "Mohammed").forEach(c -> {
//				CustomerDTO customer = new CustomerDTO();
//				customer.setEmail(c + "@gmail.com");
//				customer.setFullName(c);
//				bankAccountService.saveCustomer(customer);
//			});
//
//			bankAccountService.getCustomers().forEach(customer -> {
//				try {
//					bankAccountService.saveBankAccount(
//							Math.random()*9000,
//							Math.random() < 0.5 ? "current" : "savings",
//							customer.getId(),
//							Math.random()*5
//					);
//				} catch (AccountTypeException | CustomerNotFoundException e) {
//					throw new RuntimeException(e);
//				}
//			});
//
//			bankAccountService.getBankAccounts().forEach(bankAccount -> {
//				try {
//					bankAccountService.depose(bankAccount.getId(), 100 + Math.random()*200, "depose cash");
//					bankAccountService.withdrawal(bankAccount.getId(), 200 + Math.random()*200, "withdrawal");
//				} catch (BankAccountNotFoundException | AccountOperationsException e) {
//					throw new RuntimeException(e);
//				}
//
//			});


		};
	}

}
