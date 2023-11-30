package cz.muni.fi.pb162.hw02.impl;

import cz.muni.fi.pb162.hw02.mesaging.broker.Message;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Michaela Kecskesova
 */
public class DataMessage implements Message {
    private static final long NOT_STORED = -1;
    private static final AtomicLong LONG_ID = new AtomicLong(0);
    private long id;
    private final Set<String> topicTags;
    private final Map<String, Object> messageData;

    /**
     * Create a DataMessage object
     * @param topics topics to which the message belongs
     * @param data data to be stored in the message
     */
    public DataMessage(Set<String> topics, Map<String, Object> data) {
        this.id = NOT_STORED;
        this.topicTags = topics;
        this.messageData = data;
    }

    @Override
    public Long id() {
        if (this.id == NOT_STORED) {
            return null;
        }
        return this.id;
    }

    /**
     * Set id of the message
     */
    public void setId() {
        this.id = LONG_ID.getAndIncrement();
    }


    @Override
    public Set<String> topics() {
        return this.topicTags;
    }

    @Override
    public Map<String, Object> data() {
        return this.messageData;
    }
}
