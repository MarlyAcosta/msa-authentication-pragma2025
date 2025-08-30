package co.com.bancolombia.model.user.exception;

public class InvalidSalaryException extends RuntimeException {
    public InvalidSalaryException(Integer base_salary) {
        super("Salario fuera de rango permitido: " + base_salary);
    }
}
