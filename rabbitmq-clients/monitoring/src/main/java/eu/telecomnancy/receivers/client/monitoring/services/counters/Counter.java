package eu.telecomnancy.receivers.client.monitoring.services.counters;

/**
 * Represent a class that can count a resource or an entity
 */
public interface Counter {

    /**
     * Decrement the count of the resource
     */
    void decrement();

    /**
     * Retrieve the current count
     *
     * @return The current count of the resource
     */
    int getCount();

    /**
     * Increment the count of the resource
     */
    void increment();

}
