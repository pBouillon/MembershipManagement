package eu.telecomnancy.receivers.client.monitoring.services.counters;

public abstract class CounterService implements Counter {

    private int count = 0;

    public void decrement() {
        --count;
    }

    public int getCount() {
        return count;
    }

    public void increment() {
        ++count;
    }

}
