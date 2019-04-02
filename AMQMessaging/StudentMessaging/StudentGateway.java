package StudentMessaging;

import JMSMessage.JMSQueueMessaging.QMessageReceiver;
import JMSMessage.JMSQueueMessaging.QMessageSender;
import JMSMessage.Serializer;
import Models.Assignment;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public abstract class StudentGateway {
    private QMessageSender sender;
    private QMessageReceiver receiver;
    private Serializer serializer;
    private String correlationId;
    private Map<String, Integer> assignmentCorrelations = new HashMap<>();


    public StudentGateway(String Tchannel, String Qchannel){
        serializer = new Serializer();
        sender = new QMessageSender(Tchannel);
        receiver = new QMessageReceiver(Qchannel);
        receiver.setListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {

                try {
                    TextMessage msg = (TextMessage) message;
                    correlationId = msg.getJMSCorrelationID();
                    String response = msg.getText();
                    Assignment asg = serializer.stringToAssigment(response);
                    msg.acknowledge();
                    for(Map.Entry<String, Integer> entity : assignmentCorrelations.entrySet()){
                        if(entity.getValue().equals(asg.getID())){
                            if(entity.getKey().equals(correlationId)){
                                onAssigmentArrived(asg);
                            }
                            else{
                                System.out.println("correlation id komt niet overeen");
                            }
                        }
                    }
//                    if(asg.getCorrelationId().equals(correlationId)){
//                        onAssigmentArrived(asg);
//                    }
//                    else{
//                        System.out.println("correlation id komt niet overeen");
//                    }

                } catch (JMSException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public void sendAssignment(Assignment asg){
        try {
            String correlationID = createRandomString();
            asg.setCorrelationId(correlationID);
            String json = serializer.assignmentToString(asg);
            Message msg = sender.CreateTextMessage(json);
            msg.setJMSCorrelationID(correlationID);
            assignmentCorrelations.put(correlationID, asg.getID());
            sender.Send(msg);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

    private String createRandomString() {
        Random random = new Random(System.currentTimeMillis());
        long randomLong = random.nextLong();
        return Long.toHexString(randomLong);
    }



    public abstract void onAssigmentArrived(Assignment asg);
}
