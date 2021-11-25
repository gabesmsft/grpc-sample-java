package io.hollan.https_frontend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	private static final GrpcClient grpcClient = new GrpcClient();

	@GetMapping("/hello")
	public String index() {
		return grpcClient.greet("Azure Container Apps");
	}

}