package cz.muni.fi.pb162.hw02.impl;

import cz.muni.fi.pb162.hw02.mesaging.broker.Broker;
import cz.muni.fi.pb162.hw02.mesaging.broker.Message;
import cz.muni.fi.pb162.hw02.mesaging.client.Consumer;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author Michaela Kecskesova
 */
public class MessageConsumer extends MessageClient implements Consumer {
    private Map<String, Long> offsets;

    /**
     * Create MessageConsumer instance with an assigned broker
     * @param broker assigned broker
     */
    public MessageConsumer(Broker broker) {
        super(broker);
        this.offsets = new HashMap<>();
    }

    @Override
    public Collection<Message> consume(int num, String... topics) {

        Collection<Message> consumed = consume(this.offsets, num, topics);

        for (String topic : topics) {
            Long max = 0L;
            int count = 0;

            for (Message message : consumed) {
                if (count == num) {  // TestBroker always returns the full batch for some reason
                    break;
                }
                if (message.topics().contains(topic) && message.id() > max) {
                    max = message.id();
                    ++count;
                }
            }
            this.offsets.put(topic, max);
        }

        return consumed;
    }

    @Override
    public Collection<Message> consume(Map<String, Long> offsets, int num, String... topics) {
        HashSet<String> topicsToConsume = new HashSet<>(Arrays.asList(topics));
        return this.getBroker().poll(offsets, num, topicsToConsume);
    }

    @Override
    public Map<String, Long> getOffsets() {
        return new HashMap<>(this.offsets);
    }

    @Override
    public void setOffsets(Map<String, Long> offsets) {
        this.offsets = offsets;
    }

    @Override
    public void clearOffsets() {
        this.offsets = new HashMap<>();
    }

    @Override
    public void updateOffsets(Map<String, Long> offsets) {
        for (String topic : offsets.keySet()) {
            if (this.offsets.get(topic) != null) {
                this.offsets.put(topic, offsets.get(topic));
            }
        }
    }
}
