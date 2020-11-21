package app.sample.messages;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

//@SpringBootApplication
public class MessagesApplication {

    public static void main(String[] args) {
        //SpringApplication.run(MessagesApplication.class, args);

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        MessageService messageService = context.getBean(MessageService.class);
        messageService.save("Hello, Spring");
    }
}
