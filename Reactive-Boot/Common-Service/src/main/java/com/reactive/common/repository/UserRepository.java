package com.reactive.common.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.reactive.common.model.UserEntity;

@Repository
public interface UserRepository extends ReactiveMongoRepository<UserEntity, String> {

}
