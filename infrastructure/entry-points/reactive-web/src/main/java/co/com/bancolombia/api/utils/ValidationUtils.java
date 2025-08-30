package co.com.bancolombia.api.utils;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolation;
import reactor.core.publisher.Mono;
import java.util.Set;
import java.util.stream.Collectors;

import co.com.bancolombia.api.exception.ValidationException;


public class ValidationUtils {
    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private ValidationUtils() {}

    public static <T> Mono<T> validate(T object) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            String errors = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(", "));
            return Mono.error(new ValidationException(errors));
        }
        return Mono.just(object);
    }
}