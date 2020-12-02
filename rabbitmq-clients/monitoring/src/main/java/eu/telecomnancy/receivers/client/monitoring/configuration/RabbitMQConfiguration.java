package eu.telecomnancy.receivers.client.monitoring.configuration;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ components configuration
 *
 * See: https://www.rabbitmq.com/tutorials/tutorial-three-spring-amqp.html
 */
@Configuration
public class RabbitMQConfiguration {

    /**
     * RabbitMQ routing key for messages related to the content of the API
     */
    private final static String CONTENT_KEY = "content";

    /**
     * RabbitMQ topic name
     */
    @Value("${amqp.topic.name}")
    private String topicName;

    /**
     * Bean to create the RabbitMQ queue
     *
     * @return An initialized queue
     */
    @Bean
    public Queue autoDeleteQueue() {
        return new AnonymousQueue();
    }

    /**
     * Bean to bind a RabbitMQ topic exchange and a queue
     *
     * @param topicExchange The topic exchange to bind with a queue
     * @param autoDeleteQueue The queue to bind
     * @return A binding between a topic exchange and a queue
     */
    @Bean
    public Binding binding(TopicExchange topicExchange, Queue autoDeleteQueue) {
        return BindingBuilder.bind(autoDeleteQueue)
                .to(topicExchange)
                .with(CONTENT_KEY);
    }

    /**
     * Bean to create the RabbitMQ topic exchange
     *
     * @return An initialized topic exchange
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(topicName);
    }

}
