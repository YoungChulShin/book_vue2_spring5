package app.sample.messages;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration  // AppConfig가 빈을 정의하기 위한 것임을 알려주는 어노테이션
@ComponentScan("app.sample.messages")
public class AppConfig {

    // 빈을 정의
    // 메서드의 이름이 빈의 이름
    @Bean
    public MessageRepository messageRepository() {
        return new MessageRepository();
    }

    @Bean
    public MessageService messageService() {
        return new MessageService(messageRepository());
    }
}
