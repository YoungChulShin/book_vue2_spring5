package app.sample;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component
public class MessageRepository {

    private final static Log log = LogFactory.getLog(MessageRepository.class);

    public void saveMessage(Message message) {
        // DB에 메시지를 저장한다
        log.info("Saved message: " + message.getText());

        System.out.println(message.getText());
    }
}