package ServerMessaging;

import JMSMessage.JMSQueueMessaging.QMessageReceiver;
import JMSMessage.JMSQueueMessaging.QMessageSender;
import JMSMessage.JMSTopicMessaging.TMessagePublisher;
import JMSMessage.Serializer;
import Models.Assignment;

import javax.jms.*;

public abstract class ServerGateway {
    private QMessageSender sender;
    private QMessageReceiver studentReceiver;
    private QMessageReceiver teacherReceiver;
    private Serializer serializer;
    private TMessagePublisher publisher;
    private String correlationId;

    public ServerGateway(){
        serializer = new Serializer();
        studentReceiver = new QMessageReceiver("StudentSubmit");
        studentReceiver.setListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    TextMessage msg = (TextMessage) message;
                    correlationId = msg.getJMSCorrelationID();
                    String response = msg.getText();
                    Assignment asg = serializer.stringToAssigment(response);
                    onStudentAssigmentArrived(asg, correlationId);
                } catch (JMSException e) {
                    e.printStackTrace();
                }

            }
        });

        teacherReceiver = new QMessageReceiver("StudentResponse");
        teacherReceiver.setListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    TextMessage msg = (TextMessage) message;
                    correlationId = msg.getJMSCorrelationID();
                    String response = msg.getText();
                    Assignment asg = serializer.stringToAssigment(response);
                    onTeacherAssigmentArrived(asg, correlationId);
                } catch (JMSException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    public void publishMessage(Assignment asg, String channel, String correlationID){
        try {
            publisher = new TMessagePublisher(channel);
            String json = serializer.assignmentToString(asg);
            Message msg = publisher.CreateTextMessage(json);
            msg.setJMSCorrelationID(correlationID);
            publisher.Send(msg);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void sendReviewedAssignment(Assignment asg, String correlationId){
        sender = new QMessageSender("ServerToStudent");
        String json = serializer.assignmentToString(asg);
        Message msg = sender.CreateTextMessage(json);
        sender.Send(msg, correlationId);
    }


    public abstract void onStudentAssigmentArrived(Assignment asg, String correlationID);

    public abstract void onTeacherAssigmentArrived(Assignment asg, String correlationID);
}
