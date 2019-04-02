package JMSMessage.JMSQueueMessaging;

import JMSMessage.MessageSetup;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;

public class QMessageReceiver extends MessageSetup {

    private Destination destination;
    private MessageConsumer consumer;


    public QMessageReceiver(String channelName){
        super();
        try {
            destination = session.createQueue(channelName);
            consumer = session.createConsumer(destination);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }


    public void setListener(MessageListener ml){
        try {
            consumer.setMessageListener(ml);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
