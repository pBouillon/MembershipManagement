package eu.telecomnancy.receivers.client.monitoring.services.counters;

/**
 * Base class for a counter, implementing its core logic
 */
public abstract class CounterService implements Counter {

    /**
     * Inner-count of the resource
     */
    private int count = 0;

    /**
     * {@inheritDoc}
     */
    @Override
    public final void decrement() {
        --count;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getCount() {
        return count;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void increment() {
        ++count;
    }

}
