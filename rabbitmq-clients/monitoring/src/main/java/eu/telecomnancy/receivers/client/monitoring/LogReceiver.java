package eu.telecomnancy.receivers.client.monitoring;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * Logging service, recording all the executed API operations
 */
@Log4j2
@Service
public class LogReceiver {

    /**
     * Entry point to all received messages from the RabbitMQ queue
     *
     * @param dequeuedMessage The message extracted from the queue
     */
    @RabbitListener(queues = "#{autoDeleteQueue.name}")
    public void RabbitListener(String dequeuedMessage) {
        log.info(dequeuedMessage);
    }

}
