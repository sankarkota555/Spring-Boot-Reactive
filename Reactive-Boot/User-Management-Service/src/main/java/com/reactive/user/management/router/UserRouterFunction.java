package com.reactive.user.management.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.reactive.common.constants.CommonRoutes;
import com.reactive.common.constants.PathVariables;
import com.reactive.user.management.handler.UserHandler;

@Configuration
public class UserRouterFunction {

	@Bean
	RouterFunction<ServerResponse> userRouter(UserHandler userHandler) {
		return RouterFunctions.route()
				// save user request
				.POST(CommonRoutes.USER_ROUTE, RequestPredicates.accept(MediaType.APPLICATION_JSON),
						userHandler::saveUser)
				// Nested requests for user/...
				.path(CommonRoutes.USER_ROUTE + "/", b1 -> {
					// update user request
					b1.PUT(PathVariables.ID, userHandler::updateUser);
					// delete user request
					b1.DELETE(PathVariables.ID, userHandler::updateUser);
				}).build();

	}

}
