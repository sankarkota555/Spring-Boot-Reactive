package com.reactive.common.handler;

import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.function.server.ServerResponse.BodyBuilder;

public class CommonHandler {

	public BodyBuilder ok() {
		return ServerResponse.ok();
	}
}
