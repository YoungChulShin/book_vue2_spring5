package app.sample.messages;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration  // AppConfig가 빈을 정의하기 위한 것임을 알려주는 어노테이션
@ComponentScan("app.sample.messages")
public class AppConfig {

    // 빈을 정의
    // 메서드의 이름이 빈의 이름
//    @Bean
//    public MessageRepository messageRepository() {
//        return new MessageRepository();
//    }
//
//    @Bean
//    public MessageService messageService() {
//        return new MessageService(messageRepository());
//    }

    @Bean
    public FilterRegistrationBean<AuditingFilter> auditingFilterFilterRegistrationBean() {
        FilterRegistrationBean<AuditingFilter> registration = new FilterRegistrationBean<>();
        AuditingFilter filter = new AuditingFilter();
        registration.setFilter(filter);
        registration.setOrder(Integer.MAX_VALUE);
        registration.setUrlPatterns(Arrays.asList("/messages/*"));
        return registration;
    }
}
