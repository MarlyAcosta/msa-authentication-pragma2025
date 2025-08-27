package co.com.bancolombia.usecase.registeruser;

import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class RegisterUserUseCase {

    private final UserRepository userRepository;

    public Mono<User> register(User user) {
        // Validaciones de negocio
        if (user.getName() == null || user.getLastname() == null || user.getEmail() == null) {
            return Mono.error(new IllegalArgumentException("Campos obligatorios faltantes"));
        }

        if (user.getBaseSalary() < 0 || user.getBaseSalary() > 15000000) {
            return Mono.error(new IllegalArgumentException("Salario fuera de rango permitido"));
        }

        // Validar duplicados
        return userRepository.findByEmail(user.getEmail())
                .flatMap(existing -> Mono.<User>error(new IllegalArgumentException("El correo ya está registrado")))
                .switchIfEmpty(userRepository.save(user));
    }
}
