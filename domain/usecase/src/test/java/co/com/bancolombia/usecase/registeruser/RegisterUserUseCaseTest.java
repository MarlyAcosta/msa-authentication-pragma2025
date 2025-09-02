
package co.com.bancolombia.usecase.registeruser;

import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.gateways.UserRepository;
import co.com.bancolombia.model.user.gateways.UserValidator;
import co.com.bancolombia.model.user.exception.EmailAlreadyExistsException;
import co.com.bancolombia.model.user.exception.InvalidSalaryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class RegisterUserUseCaseTest {

    private UserValidator validator;
    private UserRepository repository;
    private RegisterUserUseCase useCase;

    @BeforeEach
    void setUp() {
        validator = mock(UserValidator.class);
        repository = mock(UserRepository.class);
        useCase = new RegisterUserUseCase(validator, repository);
    }

    User user1 = User.builder()
            .name("John")
            .lastname("Doe")
            .email("john@email.com")
            .baseSalary(5000)
            .build();

    User user2 = User.builder()
            .name("John")
            .lastname("Doe")
            .email("john@email.com")
            .baseSalary(5000)
            .build();

    User user3 = User.builder()
            .name("John")
            .lastname("Doe")
            .email("john@email.com")
            .baseSalary(20_000_000)
            .build();

    @Test
    void registerUser_success() {
        when(validator.validateSalary(user1)).thenReturn(Mono.just(user1));
        when(validator.validateEmailUnique(user1)).thenReturn(Mono.just(user1));
        when(repository.save(user1)).thenReturn(Mono.just(user1));

        StepVerifier.create(useCase.register(user1))
                .expectNext(user1)
                .verifyComplete();
    }

    @Test
    void registerUser_invalidSalary() {

        when(validator.validateSalary(user2)).thenReturn(Mono.error(new InvalidSalaryException(user2.getBaseSalary())));

        StepVerifier.create(useCase.register(user2))
                .expectError(InvalidSalaryException.class)
                .verify();
    }

    @Test
    void registerUser_emailExists() {

        when(validator.validateSalary(user3)).thenReturn(Mono.just(user3));
        when(validator.validateEmailUnique(user3))
                .thenReturn(Mono.error(new EmailAlreadyExistsException(user3.getEmail())));

        StepVerifier.create(useCase.register(user3))
                .expectError(EmailAlreadyExistsException.class)
                .verify();
    }
}
