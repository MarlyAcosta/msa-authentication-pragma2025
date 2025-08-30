package co.com.bancolombia.r2dbc.helper;

import org.springframework.stereotype.Component;

import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.gateways.UserRepository;
import co.com.bancolombia.model.user.gateways.UserValidator;
import co.com.bancolombia.model.user.exception.EmailAlreadyExistsException;
import co.com.bancolombia.model.user.exception.InvalidSalaryException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserValidatorAdapter implements UserValidator{

    private final UserRepository userRepository;

    private static final int MAX_SALARY = 15_000_000;
    private static final int MIN_SALARY = 0;

    @Override
    public Mono<User> validateSalary(User user) {
        if (user.getBaseSalary() < MIN_SALARY || user.getBaseSalary() > MAX_SALARY) {
            return Mono.error(new InvalidSalaryException(user.getBaseSalary()));
        }
        return Mono.just(user);
    }

    @Override
    public Mono<User> validateEmailUnique(User user) {
        return userRepository.findByEmail(user.getEmail())
                .flatMap(existing -> Mono.<User>error(new EmailAlreadyExistsException(user.getEmail())))
                .switchIfEmpty(Mono.just(user));
    }
}