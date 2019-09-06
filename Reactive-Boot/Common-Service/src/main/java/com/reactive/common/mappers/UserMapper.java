package com.reactive.common.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reactive.common.dto.UserDto;
import com.reactive.common.model.UserEntity;
import com.reactive.common.repository.UserRepository;

import reactor.core.publisher.Mono;

@Component
public class UserMapper {

	@Autowired
	private UserRepository userRepository;

	public UserEntity mapNewUser(UserDto userDto) {
		UserEntity user = new UserEntity();
		user.setContactDetails(userDto.getContactDetails());
		user.setDeleted(userDto.isDeleted());
		user.setDeletedDate(userDto.getDeletedDate());
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setMiddleName(userDto.getMiddleName());
		user.setVerificationKey(userDto.getVerificationKey());

		return user;

	}

	public UserEntity mapExistingUser(UserDto userDto) {
		Mono<UserEntity> userMono = userRepository.findById(userDto.getId());
		UserEntity user = userMono.toProcessor().peek();
		if (!userDto.getContactDetails().isEmpty())
			user.setContactDetails(userDto.getContactDetails());
		user.setDeleted(userDto.isDeleted());
		if (userDto.getDeletedDate() != null)
			user.setDeletedDate(userDto.getDeletedDate());

		if (userDto.getFirstName() != null)
			user.setFirstName(userDto.getFirstName());

		if (userDto.getLastName() != null)
			user.setLastName(userDto.getLastName());

		if (userDto.getMiddleName() != null)
			user.setMiddleName(userDto.getMiddleName());

		if (userDto.getVerificationKey() != null)
			user.setVerificationKey(userDto.getVerificationKey());

		return user;
	}

}
