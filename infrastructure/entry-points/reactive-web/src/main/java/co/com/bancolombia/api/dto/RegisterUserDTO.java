package co.com.bancolombia.api.dto;

import java.sql.Date;

public record RegisterUserDTO(String name, String lastname, Date birthdate, String address, String phone,
                String email, Integer baseSalary) {
}