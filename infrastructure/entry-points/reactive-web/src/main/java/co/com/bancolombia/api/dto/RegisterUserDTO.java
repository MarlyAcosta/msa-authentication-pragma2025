package co.com.bancolombia.api.dto;

import java.sql.Date;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterUserDTO(@NotBlank(message = "El nombre es obligatorio") String name,
        @NotBlank(message = "El apellido es obligatorio") String lastname, Date birthdate, String address, String phone,
        @NotBlank(message = "El correo es obligatorio") @Email(message = "El correo no es válido") String email,
        Integer baseSalary) {
}