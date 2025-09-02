package co.com.bancolombia.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.Assertions.assertThat;

import co.com.bancolombia.model.user.gateways.UserRepository;
import co.com.bancolombia.usecase.registeruser.RegisterUserUseCase;

@SpringBootTest
class UseCasesConfigTest {

    @MockitoBean
    private UserRepository userRepository;

    @Autowired
    private RegisterUserUseCase registerUserUseCase;

    @Test
    void testUseCaseBeansExist() {
        assertThat(registerUserUseCase).isNotNull();
    }
}