package eu.telecomnancy.receivers.client.monitoring.services.counters;

public interface Counter {

    void decrement();

    int getCount();

    void increment();

}
