package Fix4Me.application;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "Fix4Me")
@SpringBootApplication
public class Fix4Me  {

	
	public static void main(String[] args) {
		SpringApplication.run(Fix4Me.class, args);

	}

}
