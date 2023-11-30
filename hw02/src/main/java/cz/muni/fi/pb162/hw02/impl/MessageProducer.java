package cz.muni.fi.pb162.hw02.impl;

import cz.muni.fi.pb162.hw02.mesaging.broker.Broker;
import cz.muni.fi.pb162.hw02.mesaging.broker.Message;
import cz.muni.fi.pb162.hw02.mesaging.client.Producer;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Michaela Kecskesova
 */
public class MessageProducer extends MessageClient implements Producer {

    /**
     * Create MessageProducer instance with an assigned broker
     * @param broker assigned broker
     */
    public MessageProducer(Broker broker) {
        super(broker);
    }

    @Override
    public Message produce(Message message) {
        ArrayList<Message> collection = new ArrayList<Message>(1);
        collection.add(message);
        Collection<Message> brokerMessages = this.getBroker().push(collection);
        return (Message) brokerMessages.toArray()[0];
    }

    @Override
    public Collection<Message> produce(Collection<Message> messages) {
        return this.getBroker().push(messages);
    }
}
