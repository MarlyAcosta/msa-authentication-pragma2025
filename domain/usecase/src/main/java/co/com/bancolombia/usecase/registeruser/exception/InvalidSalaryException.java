package co.com.bancolombia.usecase.registeruser.exception;

public class InvalidSalaryException extends RuntimeException {
    public InvalidSalaryException(Integer base_salary) {
        super("Salario fuera de rango permitido: " + base_salary);
    }
}
