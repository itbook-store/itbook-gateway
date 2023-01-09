package shop.itbook.itbookgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ItbookGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItbookGatewayApplication.class, args);
    }

}
