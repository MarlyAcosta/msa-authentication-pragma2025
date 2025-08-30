package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.RegisterUserDTO;
import co.com.bancolombia.api.mapper.UserDTOMapper;
import co.com.bancolombia.api.utils.ResponseMessages;
import co.com.bancolombia.api.utils.ValidationUtils;
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
                log.info("Iniciando creación de usuario. Path: {}, QueryParams: {}", serverRequest.path(),
                                serverRequest.queryParams());
                return serverRequest.bodyToMono(RegisterUserDTO.class)
                                .flatMap(ValidationUtils::validate)
                                .map(userDTOMapper::toModel)
                                .flatMap(registerUserUseCase::register)
                                .flatMap(user -> Mono.just(
                                                userDTOMapper.toResponse(user, ResponseMessages.USER_CREATED)))
                                .flatMap(userResponse -> ServerResponse.status(HttpStatus.CREATED)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .bodyValue(userResponse))
                                .doOnError(e -> log.error("Error durante la creación de usuario: {}", e.getMessage(),
                                                e));
        }
}
