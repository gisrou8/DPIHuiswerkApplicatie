package TeacherMessaging;

import JMSMessage.JMSQueueMessaging.QMessageSender;
import JMSMessage.JMSTopicMessaging.TMessageSubscriber;
import JMSMessage.Serializer;
import Models.Assignment;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public abstract class TeacherGateway {

    private TMessageSubscriber subscriber;
    private QMessageSender sender;
    private String correlationId;
    private Serializer serializer;


    public TeacherGateway(String Tchannel, String Qchannel){
        serializer = new Serializer();
        sender = new QMessageSender(Qchannel);
        subscriber = new TMessageSubscriber(Tchannel);
        subscriber.setListner(new MessageListener() {
            @Override
            public void onMessage(Message message) {

                try {
                    TextMessage msg = (TextMessage) message;
                    correlationId = msg.getJMSCorrelationID();
                    String response = msg.getText();
                    Assignment asg = serializer.stringToAssigment(response);
                    onMessageArrived(asg, correlationId);
                } catch (JMSException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    public void sendResponse(Assignment asg, String correlationId){
        try {
            String json = serializer.assignmentToString(asg);
            Message msg = sender.CreateTextMessage(json);
            msg.setJMSCorrelationID(correlationId);
            sender.Send(msg, correlationId);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }


    public abstract void onMessageArrived(Assignment asg, String correlationId);
}
