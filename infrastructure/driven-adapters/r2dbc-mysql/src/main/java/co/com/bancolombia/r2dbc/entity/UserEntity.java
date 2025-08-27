package co.com.bancolombia.r2dbc.entity;

import lombok.*;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserEntity {

    @Id
    @Column("id")
    private Integer id;
    private String name;
    private String lastname;
    private LocalDate birthday;
    private String address;
    private String phone;
    private String email;
    @Column("base_salary")
    private Integer baseSalary;
}