package JMSMessage.JMSQueueMessaging;

import JMSMessage.MessageSetup;

import javax.jms.*;
import java.util.Random;

public class QMessageSender extends MessageSetup {

    private Destination destination;
    private MessageProducer producer;


    public QMessageSender(String channelName){
        super();
        try {
            destination = session.createQueue(channelName);
            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public Message CreateTextMessage(String body){
        try {
            TextMessage message = session.createTextMessage(body);
            return message;
        } catch (JMSException e) {
            e.printStackTrace();
        }

        return null;
    }


    public void Send(Message msg){
        try {
            String correlationID = createRandomString();
            msg.setJMSCorrelationID(correlationID);
            producer.send(msg);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


    public void Send(Message msg, String correlationId){
        try {
            msg.setJMSCorrelationID(correlationId);
            producer.send(msg);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

    private String createRandomString() {
        Random random = new Random(System.currentTimeMillis());
        long randomLong = random.nextLong();
        return Long.toHexString(randomLong);
    }
}
