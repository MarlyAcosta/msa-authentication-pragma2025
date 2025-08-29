package co.com.bancolombia.api;

import co.com.bancolombia.api.config.UserPath;
import co.com.bancolombia.api.dto.RegisterUserDTO;
import co.com.bancolombia.api.mapper.UserDTOMapper;
import co.com.bancolombia.model.user.User;
import co.com.bancolombia.usecase.registeruser.RegisterUserUseCase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.Date;

@WebFluxTest()
@Import({ Handler.class, RouterRest.class, UserPath.class })
class RouterRestTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private RegisterUserUseCase registerUserUseCase;

    @MockitoBean
    private UserDTOMapper userDTOMapper;

    @MockitoBean
    private UserPath userPath;

    private final User user = User.builder()
            .id(1)
            .name("User 1")
            .lastname("Last 1")
            .email("user1@gmail.com")
            .phone("309343389")
            .baseSalary(1000000)
            .address("Direccion 1")
            .build();

    RegisterUserDTO registerDto = new RegisterUserDTO(
            "User 1",
            "Last 1",
            Date.valueOf("1990-01-01"),
            "Direccion 1",
            "309343389",
            "user1@gmail.com",
            1000000);

    @Test
    void shouldPostSaveUser() {
        when(userDTOMapper.toModel(any(RegisterUserDTO.class))).thenReturn(user);
        when(registerUserUseCase.register(any(User.class))).thenReturn(Mono.just(user));
        when(userPath.getUsers()).thenReturn("/api/v1/users");

        webTestClient.post()
                .uri(userPath.getUsers())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(registerDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .value(saved -> Assertions.assertThat(saved.getEmail()).isEqualTo(user.getEmail()));
    }
}
