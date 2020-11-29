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
     * RabbitMQ fanout name
     */
    @Value("${amqp.fanout.name}")
    private String fanoutName;

    /**
     * RabbitMQ queue name
     */
    @Value("${amqp.queue.name}")
    private String queueName;

    /**
     * RabbitMQ queue persistence
     */
    @Value("${amqp.queue.is-durable}")
    private boolean isQueueDurable;

    /**
     * Bean to initialize the binding between a queue and a fanout
     *
     * @param queue Queue to be bound
     * @param fanout Fanout used for binding
     * @return The resulting binding
     */
    @Bean
    Binding binding(Queue queue, FanoutExchange fanout) {
        return BindingBuilder.bind(queue)
                .to(fanout);
    }

    /**
     * Bean to create the RabbitMQ fanout
     *
     * @return An initialized fanout
     */
    @Bean
    public FanoutExchange fanout() {
        return new FanoutExchange(fanoutName);
    }

    /**
     * Bean to create the RabbitMQ queue used for message exchanges
     *
     * @return A messaging queue
     */
    @Bean
    public Queue queue() {
        return new Queue(queueName, isQueueDurable);
    }

}
