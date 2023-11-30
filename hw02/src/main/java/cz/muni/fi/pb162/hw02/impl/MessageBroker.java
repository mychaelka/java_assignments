package cz.muni.fi.pb162.hw02.impl;

import cz.muni.fi.pb162.hw02.mesaging.broker.Broker;
import cz.muni.fi.pb162.hw02.mesaging.broker.Message;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeSet;

/**
 * @author Michaela Kecskesova
 */
public class MessageBroker implements Broker {

    private final HashMap<String, ArrayList<Message>> internalMessages;

    /**
     * Default constructor creates a new empty map for internalMessages
     */
    public MessageBroker() {
        internalMessages = new HashMap<>();
    }
    @Override
    public Collection<String> listTopics() {
        return internalMessages.keySet();
    }

    @Override
    public Collection<Message> push(Collection<Message> messages) {
        Collection<Message> pushedMessages = new HashSet<>();

        for (Message message : messages) {
            DataMessage dataMessage = new DataMessage(message.topics(), message.data());
            dataMessage.setId();
            pushedMessages.add(dataMessage);

            for (String topic : dataMessage.topics()) {
                internalMessages.putIfAbsent(topic, new ArrayList<>());
                internalMessages.get(topic).add(dataMessage);
            }
        }

        return pushedMessages;
    }

    @Override
    public Collection<Message> poll(Map<String, Long> offsets, int num, Collection<String> topics) {
        TreeSet<Message> polled = new TreeSet<>(this::comp);

        for (String topic : topics) {
            int counter = 0;

            for (Message message : internalMessages.getOrDefault(topic, new ArrayList<>())) {
                if (counter == num) {
                    break;
                }
                if (offsets.get(topic) == null || message.id() > offsets.get(topic)) {
                    polled.add(message);
                    counter++;
                }
            }
        }

        return polled;
    }

    /**
     * Comparator for two messages as viewed by the Broker
     * @param m1 message 1
     * @param m2 message 2
     * @return 0 if messages are equal, negative int if m1 < m2, positive int if m1 > m2</>
     */
    public int comp(Message m1, Message m2) {
        return m1.id().compareTo(m2.id());
    }
}
