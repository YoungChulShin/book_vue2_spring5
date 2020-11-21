package app.sample.messages;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @SecurityCheck
    @GetMapping("/welcome")
    public String welcome() throws InterruptedException {

        return "Hello, Welcome to Spring boot";
    }
}
