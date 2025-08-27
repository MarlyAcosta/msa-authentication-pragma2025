package co.com.bancolombia.r2dbc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;

import co.com.bancolombia.model.user.User;
import co.com.bancolombia.r2dbc.entity.UserEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserReactiveRepositoryAdapterTest {
    // TODO: change four you own tests

    @InjectMocks
    UserReactiveRepositoryAdapter repositoryAdapter;

    @Mock
    UserReactiveRepository repository;

    @Mock
    ObjectMapper mapper;

    private final UserEntity userEntity = UserEntity.builder()
            .id(1)
            .name("User 1")
            .lastname("Last 1")
            .email("user1@gmail.com")
            .phone("309343389")
            .baseSalary(1000000)
            .address("Direccion 1")
            .build();

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
    // .id(2)
    // .name("User 2")
    // .lastname("Last 2")
    // .email("user2@gmail.com")
    // .phone("3092342349")
    // .baseSalary(100000000)
    // .address("Direccion 3")
    // .build();

    @Test
    void mustSaveUser() {
        when(mapper.map(userEntity, User.class)).thenReturn(user);
        when(mapper.map(user, UserEntity.class)).thenReturn(userEntity);
        when(repository.save(userEntity)).thenReturn(Mono.just(userEntity));

        Mono<User> result = repositoryAdapter.save(user);

        StepVerifier.create(result)
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    void shouldFindUserByEmail() {

        when(mapper.map(userEntity, User.class)).thenReturn(user);

        when(repository.findByEmail("user1@gmail.com")).thenReturn(Mono.just(userEntity));

        Mono<User> result = repositoryAdapter.findByEmail("user1@gmail.com");

        StepVerifier.create(result)
                .expectNextMatches(t -> t.getEmail().equals("user1@gmail.com") && t.getName().equals("User 1"))
                .verifyComplete();
    }

}
