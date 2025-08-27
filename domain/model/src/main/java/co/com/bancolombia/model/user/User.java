package co.com.bancolombia.model.user;
import lombok.Builder;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {
    private Integer id;
    private String name;
    private String lastname;
    private LocalDate birthday;
    private String address;
    private String phone;
    private String email;
    private Integer baseSalary;
}
