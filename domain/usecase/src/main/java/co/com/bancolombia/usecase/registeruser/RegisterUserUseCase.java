package co.com.bancolombia.usecase.registeruser;

import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.gateways.UserRepository;
import co.com.bancolombia.usecase.registeruser.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class RegisterUserUseCase {

    private final UserValidator userValidator;
    private final UserRepository userRepository;

    public Mono<User> register(User user) {
        return Mono.just(user)
                .flatMap(userValidator::validateRequiredFields)
                .flatMap(userValidator::validateSalary)
                .flatMap(userMono -> userValidator.validateEmailUnique(userMono, userRepository))
                .flatMap(userRepository::save);
    }
}
