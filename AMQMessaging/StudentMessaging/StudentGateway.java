package StudentMessaging;

import JMSMessage.JMSQueueMessaging.QMessageReceiver;
import JMSMessage.JMSQueueMessaging.QMessageSender;
import JMSMessage.Serializer;
import Models.Assignment;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public abstract class StudentGateway {
    private QMessageSender sender;
    private QMessageReceiver receiver;
    private Serializer serializer;
    private String correlationId;


    public StudentGateway(){
        serializer = new Serializer();
        sender = new QMessageSender("StudentSubmit");
        receiver = new QMessageReceiver("ServerToStudent");
        receiver.setListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {

                try {
                    TextMessage msg = (TextMessage) message;
                    correlationId = msg.getJMSCorrelationID();
                    String response = msg.getText();
                    Assignment asg = serializer.stringToAssigment(response);
                    onAssigmentArrived(asg);
                } catch (JMSException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public void sendAssignment(Assignment asg){
        String json = serializer.assignmentToString(asg);
        Message msg = sender.CreateTextMessage(json);
        sender.Send(msg);
    }

    public abstract void onAssigmentArrived(Assignment asg);
}
