package co.com.bancolombia.model.user;
import lombok.Builder;

import java.sql.Date;

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
    private Date birthdate;
    private String address;
    private String phone;
    private String email;
    private Integer baseSalary;
}
