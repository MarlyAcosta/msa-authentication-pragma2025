package co.com.bancolombia.usecase.registeruser.exception;

import java.util.List;

public class MissingFieldsException extends RuntimeException {
    public MissingFieldsException(List<String> missingFields) {
        super("Campos obligatorios faltantes: " + String.join(", ", missingFields));
    }
}
