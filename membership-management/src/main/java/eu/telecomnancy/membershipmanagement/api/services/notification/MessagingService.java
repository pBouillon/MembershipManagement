package eu.telecomnancy.membershipmanagement.api.services.notification;

import eu.telecomnancy.membershipmanagement.api.controllers.utils.cqrs.CqrsOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Notification service, dispatching messages on RabbitMQ queues
 */
@Log4j2
@Service
public class MessagingService {

    /**
     * Rabbit route key used to notify of an event relative to the content of the API such as
     * the creation of a user or of a team
     */
    @Value("${amqp.routing-key.content}")
    private String contentRouteKey;

    /**
     * RabbitMQ template used to dispatch messages
     */
    private final RabbitTemplate template;

    /**
     * Topic exchange used as a route to propagate messages
     */
    private final TopicExchange topic;

    /**
     * Default constructor used to create a messaging service that will dispatch messages on RabbitMQ queues
     *
     * @param template RabbitMQ template used to dispatch messages
     * @param topic Topic exchange used as a route to propagate messages
     */
    @Autowired
    public MessagingService(RabbitTemplate template, TopicExchange topic) {
        this.template = template;
        this.topic = topic;
    }

    /**
     * Send a message to RabbitMQ with the appropriate route key
     *
     * @param message Message to send
     * @param routeKey Route key to be used
     */
    private void send(String message, String routeKey) {
        template.convertAndSend(topic.getName(), routeKey, message);

        log.info(
                String.format("Send message (on key '%s'): %s", routeKey, message));
    }

    /**
     * Send a message containing a CQRS operation that has occurred in the application to the RabbitMQ broker
     *
     * @param operation The CQRS notification
     */
    public void sendContentMessage(CqrsOperation operation) {
        // The message is sent with no specific route key so that all RabbitMQ listeners can listen to it
        send(operation.toString(), "");
    }

    /**
     * Send a message related to an update of the application's content to the RabbitMQ broker
     *
     * @param operation The CQRS notification
     */
    public void sendContentUpdatedMessage(CqrsOperation operation) {
        send(operation.toString(), contentRouteKey);
    }

}
