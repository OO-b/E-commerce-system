package kr.hhplus.be.server;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@OpenAPIDefinition(
		info = @Info(title = "E-Commerce-System", version = "1.0", description = "E-Commerce-System API 명세"),
		servers = {
				@Server(url = "http://localhost:8080", description = "로컬"),
		}
)
@EnableJpaRepositories(basePackages = "kr.hhplus.be.server.infrastructure")
@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

}
