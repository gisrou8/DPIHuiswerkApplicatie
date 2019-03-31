package JMSMessage.JMSTopicMessaging;

import JMSMessage.MessageSetup;

import javax.jms.*;
import java.util.Random;

public class TMessagePublisher extends MessageSetup {
    private Topic topic;
    private MessageProducer producer;

    public TMessagePublisher(String channelName){
        super();
        try {
            topic = session.createTopic(channelName);
            producer = session.createProducer(topic);
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

    private String createRandomString() {
        Random random = new Random(System.currentTimeMillis());
        long randomLong = random.nextLong();
        return Long.toHexString(randomLong);
    }
}
