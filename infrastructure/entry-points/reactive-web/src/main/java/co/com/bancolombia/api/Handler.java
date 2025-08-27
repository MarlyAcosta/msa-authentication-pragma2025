package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.RegisterUserDTO;
import co.com.bancolombia.api.dto.ResponseErrorDTO;
import co.com.bancolombia.api.mapper.UserDTOMapper;
import co.com.bancolombia.usecase.registeruser.RegisterUserUseCase;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {

    private final RegisterUserUseCase registerUserUseCase;
    private final UserDTOMapper userDTOMapper;

    public Mono<ServerResponse> listenSaveUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(RegisterUserDTO.class)
                .map(userDTOMapper::toModel)
                .flatMap(registerUserUseCase::register)
                .map(userDTOMapper::toResponse)
                .flatMap(userResponse -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(userResponse))
                .onErrorResume(this::handleError);
    }

    public Mono<ServerResponse> handleError(Throwable e) {
        if (e instanceof IllegalArgumentException) {
            return ServerResponse.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(new ResponseErrorDTO("400", e.getMessage()));
        }
        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ResponseErrorDTO("500", "Ocurrió un error inesperado"));
    }
}
