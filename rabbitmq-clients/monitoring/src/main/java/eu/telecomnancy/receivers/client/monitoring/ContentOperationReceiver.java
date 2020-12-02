package eu.telecomnancy.receivers.client.monitoring;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * Custom service listening to the RabbitMQ messages in order to keep track of the number of users and teams
 * in tha API based on the received messages
 */
@Log4j2
@Service
public class ContentOperationReceiver {

    /**
     * Entry point to all received messages from the RabbitMQ queue
     *
     * @param dequeuedMessage The message extracted from the queue
     */
    @RabbitListener(queues = "#{autoDeleteQueue.name}")
    public void RabbitListener(String dequeuedMessage) {
        log.debug(dequeuedMessage);
    }

}
