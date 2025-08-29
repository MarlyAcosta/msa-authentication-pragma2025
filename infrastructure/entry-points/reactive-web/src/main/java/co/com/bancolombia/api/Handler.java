package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.RegisterUserDTO;
import co.com.bancolombia.api.dto.ResponseErrorDTO;
import co.com.bancolombia.api.mapper.UserDTOMapper;
import co.com.bancolombia.usecase.registeruser.RegisterUserUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class Handler {

        private final RegisterUserUseCase registerUserUseCase;
        private final UserDTOMapper userDTOMapper;

        public Mono<ServerResponse> listenSaveUser(ServerRequest serverRequest) {
                log.info("Iniciando creación de usuario. Path: {}, QueryParams: {} body: {}", serverRequest.path(),
                                serverRequest.queryParams(), serverRequest.bodyToMono(RegisterUserDTO.class));
                return serverRequest.bodyToMono(RegisterUserDTO.class)
                                .doOnNext(dto -> log.info("Datos recibidos para creación de usuario: {}", dto))
                                .map(userDTOMapper::toModel)
                                .flatMap(registerUserUseCase::register)
                                .flatMap(user -> Mono.just(
                                                userDTOMapper.toResponse(user, "Usuario registrado exitosamente")))
                                .flatMap(userResponse -> ServerResponse.status(HttpStatus.CREATED)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .bodyValue(userResponse))
                                .doOnError(e -> log.error("Error durante la creación de usuario: {}", e.getMessage(),
                                                e))
                                .onErrorResume(this::handleError);
        }

        public Mono<ServerResponse> handleError(Throwable e) {
                if (e instanceof IllegalArgumentException) {
                        return ServerResponse.badRequest()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(new ResponseErrorDTO(e.getMessage()));
                }
                return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new ResponseErrorDTO("Ocurrió un error inesperado"));
        }
}
