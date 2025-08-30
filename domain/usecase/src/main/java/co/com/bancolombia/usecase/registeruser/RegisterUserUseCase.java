package co.com.bancolombia.usecase.registeruser;

import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.gateways.UserRepository;
import co.com.bancolombia.model.user.gateways.UserValidator;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class RegisterUserUseCase {

    private final UserValidator userValidator;
    private final UserRepository userRepository;

    public Mono<User> register(User user) {
        return Mono.just(user)
                .flatMap(userValidator::validateSalary)
                .flatMap(userValidator::validateEmailUnique)
                .flatMap(userRepository::save);
    }
}
