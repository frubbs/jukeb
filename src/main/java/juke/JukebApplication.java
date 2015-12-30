package juke;

import java.util.Arrays;

import org.h2.tools.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class JukebApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(JukebApplication.class, args);

        System.out.println("Let's inspect the beans provided by Spring Boot:");

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }


        try {
            Server webServer = Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}