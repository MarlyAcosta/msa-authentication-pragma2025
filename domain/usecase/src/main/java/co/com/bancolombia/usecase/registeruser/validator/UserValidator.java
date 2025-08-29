package co.com.bancolombia.usecase.registeruser.validator;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.gateways.UserRepository;
import co.com.bancolombia.usecase.registeruser.exception.EmailAlreadyExistsException;
import co.com.bancolombia.usecase.registeruser.exception.InvalidSalaryException;
import co.com.bancolombia.usecase.registeruser.exception.MissingFieldsException;
import reactor.core.publisher.Mono;

public class UserValidator {

    private static final int MAX_SALARY = 15_000_000;
    private static final int MIN_SALARY = 0;

    public Mono<User> validateRequiredFields(User user) {
        List<String> missingFields = Stream.of(
                Map.entry("name", user.getName()),
                Map.entry("lastname", user.getLastname()),
                Map.entry("email", user.getEmail()))
                .filter(e -> e.getValue() == null || e.getValue().isBlank())
                .map(Map.Entry::getKey)
                .toList();

        if (!missingFields.isEmpty()) {
            return Mono.error(new MissingFieldsException(missingFields));
        }
        return Mono.just(user);
    }

    public Mono<User> validateSalary(User user) {
        if (user.getBaseSalary() < MIN_SALARY || user.getBaseSalary() > MAX_SALARY) {
            return Mono.error(new InvalidSalaryException(user.getBaseSalary()));
        }
        return Mono.just(user);
    }

    public Mono<User> validateEmailUnique(User user, UserRepository userRepository) {
        return userRepository.findByEmail(user.getEmail())
                .flatMap(existing -> Mono.<User>error(new EmailAlreadyExistsException(user.getEmail())))
                .switchIfEmpty(Mono.just(user));
    }
}