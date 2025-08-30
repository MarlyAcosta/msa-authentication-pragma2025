package co.com.bancolombia.model.user.gateways;

import co.com.bancolombia.model.user.User;
import reactor.core.publisher.Mono;

public interface UserValidator {
    Mono<User> validateSalary(User user);

    Mono<User> validateEmailUnique(User user);
}
