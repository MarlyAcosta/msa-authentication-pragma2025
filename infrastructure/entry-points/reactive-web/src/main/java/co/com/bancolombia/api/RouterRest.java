package co.com.bancolombia.api;

import co.com.bancolombia.api.config.UserPath;
import co.com.bancolombia.api.dto.RegisterUserDTO;
import co.com.bancolombia.api.dto.ResponseUserDTO;
import co.com.bancolombia.api.dto.ResponseErrorDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class RouterRest {
    private final UserPath userPath;
    private final Handler userHandler;

    @Bean
    @RouterOperations({ @RouterOperation(path = "/api/v1/users", produces = { "application/json" }, method = {
            org.springframework.web.bind.annotation.RequestMethod.POST }, beanClass = Handler.class, beanMethod = "listenSaveUser", operation = @Operation(operationId = "registerUser", summary = "Registrar un usuario", description = "Registra un usuario nuevo en el sistema", tags = {
                    "Users" }, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(schema = @Schema(implementation = RegisterUserDTO.class))), responses = {
                            @ApiResponse(responseCode = "200", description = "Usuario registrado", content = @Content(schema = @Schema(implementation = ResponseUserDTO.class))),
                            @ApiResponse(responseCode = "400", description = "Error de validación", content = @Content(schema = @Schema(implementation = ResponseErrorDTO.class)))
                    })) })

    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(POST(userPath.getUsers()), userHandler::listenSaveUser);
    }
}
