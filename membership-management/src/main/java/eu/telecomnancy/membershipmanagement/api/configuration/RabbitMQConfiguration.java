package eu.telecomnancy.membershipmanagement.api.configuration;

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
     * RabbitMQ topic name
     */
    @Value("${amqp.topic.name}")
    private String topicName;

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
