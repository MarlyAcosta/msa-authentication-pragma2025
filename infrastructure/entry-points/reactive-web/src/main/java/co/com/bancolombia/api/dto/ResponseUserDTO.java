package co.com.bancolombia.api.dto;

import java.time.LocalDate;

public record ResponseUserDTO (Integer id, String name, String lastname, LocalDate  birthdate, String address, String phone, String email, Integer baseSalary) {
    
}
