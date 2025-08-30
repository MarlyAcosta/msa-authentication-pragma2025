package co.com.bancolombia.api;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebInputException;
import org.springframework.http.MediaType;

import co.com.bancolombia.api.dto.ResponseErrorDTO;
import co.com.bancolombia.api.exception.ValidationException;
import co.com.bancolombia.api.utils.ResponseMessages;
import co.com.bancolombia.model.user.exception.EmailAlreadyExistsException;
import co.com.bancolombia.model.user.exception.InvalidSalaryException;
import reactor.core.publisher.Mono;

@Component
public class GlobalErrorHandler implements HandlerFilterFunction<ServerResponse, ServerResponse> {

    @Override
    public Mono<ServerResponse> filter(ServerRequest request, HandlerFunction<ServerResponse> next) {
        return next.handle(request)
                .onErrorResume(this::mapExceptionToResponse);
    }

    private Mono<ServerResponse> mapExceptionToResponse(Throwable e) {
        if (e instanceof EmailAlreadyExistsException ||
                e instanceof InvalidSalaryException || e instanceof ValidationException) {
            return badRequest(e);
        }

        if (e instanceof ServerWebInputException) {
            return ServerResponse.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(new ResponseErrorDTO("Campos inválidos o faltantes en la solicitud"));
        }

        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ResponseErrorDTO(ResponseMessages.ERROR_INTERNAL));
    }

    private Mono<ServerResponse> badRequest(Throwable e) {
        return ServerResponse.badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ResponseErrorDTO(e.getMessage()));
    }
}