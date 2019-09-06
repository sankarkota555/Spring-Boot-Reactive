package com.reactive.user.management.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.reactive.common.constants.PathVariables;
import com.reactive.common.dto.UserDto;
import com.reactive.common.handler.CommonHandler;
import com.reactive.common.mappers.UserMapper;
import com.reactive.common.model.UserEntity;
import com.reactive.common.repository.UserRepository;

import reactor.core.publisher.Mono;

@Service
public class UserHandler extends CommonHandler {

	private static final Logger log = LoggerFactory.getLogger(UserHandler.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserMapper userMapper;

	public Mono<ServerResponse> saveUser(ServerRequest request) {
		Mono<UserDto> userMono = request.bodyToMono(UserDto.class);
		return userMono.flatMap(user -> {
			Mono<UserEntity> savedUser = userRepository.save(userMapper.mapNewUser(user));
			return ok().body(savedUser, UserEntity.class);
		});

	}

	public Mono<ServerResponse> updateUser(ServerRequest request) {
		String userId = request.pathVariable(PathVariables.ID);
		Mono<UserDto> userMono = request.bodyToMono(UserDto.class);
		return userMono.flatMap(user -> {
			log.info("update user id: {}", userId);
			user.setId(userId);
			Mono<UserEntity> savedUser = userRepository.save(userMapper.mapExistingUser(user));
			return ok().body(savedUser, UserEntity.class);
		});

	}
}
