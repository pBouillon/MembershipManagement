package eu.telecomnancy.membershipmanagement.api.services;

import eu.telecomnancy.membershipmanagement.api.services.notification.MessagingService;

/**
 * Abstract class to be extended by the services of the API implementing its business logic
 *
 * It provides a {@link MessagingService} to dispatch notifications on a messaging broker
 */
public abstract class MembershipManagementService {

    /**
     * RabbitMQ message dispatcher
     */
    protected final MessagingService messagingService;

    /**
     * Create the Membership Management core service
     *
     * @param messagingService RabbitMQ message dispatcher
     */
    protected MembershipManagementService(MessagingService messagingService) {
        this.messagingService = messagingService;
    }

}
