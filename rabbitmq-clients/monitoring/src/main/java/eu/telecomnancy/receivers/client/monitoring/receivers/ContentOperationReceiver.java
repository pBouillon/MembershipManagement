package eu.telecomnancy.receivers.client.monitoring.receivers;

import eu.telecomnancy.receivers.client.monitoring.services.MonitoringService;
import eu.telecomnancy.receivers.client.monitoring.utils.ColorUtils;
import eu.telecomnancy.receivers.client.monitoring.utils.Colors;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * Custom service listening to the RabbitMQ messages in order to keep track of the number of users and teams
 * in tha API based on the received messages
 */
@Service
public class ContentOperationReceiver {

    /**
     * Monitoring service to track the API resources count
     */
    private final MonitoringService monitoringService;

    /**
     * Create the queue listener
     *
     * @param monitoringService Monitoring service to track the API resources count
     */
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
        // Update the current count of each resources
        monitoringService.alterCountFromOperation(dequeuedMessage);

        // Display the updated values
        System.out.print("\t" + monitoringService.toString() + "\r");
    }

}
