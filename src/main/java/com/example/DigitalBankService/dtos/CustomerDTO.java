package com.example.DigitalBankService.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class CustomerDTO {
    private Long id;
    private System fullName;
    private String email;
}
