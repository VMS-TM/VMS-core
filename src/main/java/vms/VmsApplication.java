package vms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VmsApplication {
	private static final Logger logger = LoggerFactory.getLogger(VmsApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(VmsApplication.class, args);
		logger.debug("--Application Started--");
	}
}
