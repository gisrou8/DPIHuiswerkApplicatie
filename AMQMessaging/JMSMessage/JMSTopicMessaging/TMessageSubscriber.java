package JMSMessage.JMSTopicMessaging;

import JMSMessage.MessageSetup;

import javax.jms.*;

public class TMessageSubscriber extends MessageSetup {
    private Topic topic;
    private MessageConsumer consumer;


    public TMessageSubscriber(String topicChannel){
        super();
        try {
            topic = session.createTopic(topicChannel);
            consumer = session.createConsumer(topic);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


    public void setListner(MessageListener ml){
        try {
            consumer.setMessageListener(ml);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
