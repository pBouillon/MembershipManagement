package eu.telecomnancy.receivers.client.monitoring.receivers;

import eu.telecomnancy.receivers.client.monitoring.services.MonitoringService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * Custom service listening to the RabbitMQ messages in order to keep track of the number of users and teams
 * in tha API based on the received messages
 */
@Service
public class ContentOperationReceiver {

    private final MonitoringService monitoringService;

    public ContentOperationReceiver(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    /**
     * Entry point to all received messages from the RabbitMQ queue
     *
     * @param dequeuedMessage The message extracted from the queue
     */
    @RabbitListener(queues = "#{autoDeleteQueue.name}")
    public void RabbitListener(String dequeuedMessage) {
        monitoringService.alterCountFromOperation(dequeuedMessage);
        System.out.print("Current count:\t" + monitoringService.toString() + "\r");
    }

}
