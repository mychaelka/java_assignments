package cz.muni.fi.pb162.hw02.impl;

import cz.muni.fi.pb162.hw02.mesaging.broker.Broker;
import cz.muni.fi.pb162.hw02.mesaging.client.Client;

import java.util.Collection;

/**
 * @author Michaela Kecskesova
 */
public class MessageClient implements Client {
    private final Broker broker;

    /**
     * Create MessageClient instance with an assigned broker
     * @param broker assigned broker
     */
    public MessageClient(Broker broker) {
        this.broker = broker;
    }
    @Override
    public Broker getBroker() {
        return broker;
    }

    @Override
    public Collection<String> listTopics() {
        return broker.listTopics();
    }
}
