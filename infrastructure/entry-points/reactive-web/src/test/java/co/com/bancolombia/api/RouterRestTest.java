package co.com.bancolombia.api;

import co.com.bancolombia.api.config.UserPath;
import co.com.bancolombia.model.user.User;
import co.com.bancolombia.usecase.registeruser.RegisterUserUseCase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = { RouterRest.class, Handler.class })
@WebFluxTest
class RouterRestTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private RegisterUserUseCase registerUserUseCase;

    private final String users = "/api/v1/users";

    private final User user = User.builder()
            .id(1)
            .name("User 1")
            .lastname("Last 1")
            .email("user1@gmail.com")
            .phone("309343389")
            .baseSalary(1000000)
            .address("Direccion 1")
            .build();

    // private final User otherUser = User.builder()
    //         .id(2)
    //         .name("User 2")
    //         .lastname("Last 2")
    //         .email("user2@gmail.com")
    //         .phone("3092342349")
    //         .baseSalary(100000000)
    //         .address("Direccion 2")
    //         .build();

    @Autowired
    private UserPath userPath;

    @Test
    void shouldPostSaveUser() {

        when(registerUserUseCase.register(any(User.class))).thenReturn(Mono.just(user));

        webTestClient.post()
                .uri(users)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(user)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .value(saved -> Assertions.assertThat(saved.getEmail()).isEqualTo(user.getEmail()));
    }
}
