package AssignmentServer;



import Models.Assignment;
import ServerMessaging.ServerGateway;
import javafx.application.Application;
import javafx.stage.Stage;

public class ServerMain extends Application {
    private ServerGateway gateway;
    @Override
    public void start(Stage primaryStage) throws Exception {
        gateway = new ServerGateway() {
            @Override
            public void onStudentAssigmentArrived(Assignment asg, String correlationID) {
                switch (asg.getStudentClass()){
                    case "S66":
                        checkSubject(asg, correlationID);
                        break;
                    case "S67":
                        checkSubject(asg, correlationID);
                        break;
                    case "S68":
                        checkSubject(asg, correlationID);
                        break;
                }
            }

            @Override
            public void onTeacherAssigmentArrived(Assignment asg, String correlationID) {
                gateway.sendReviewedAssignment(asg, correlationID);
            }
        };


    }

    public void checkSubject(Assignment asg, String correlationID){
        switch(asg.getAssignmentSubject()){
            case "JEA":
               sendMessage(asg, "chef", correlationID);
               break;
            case "DPI":
                sendMessage(asg, "Leraar", correlationID);
                break;
            case "SOL":
                sendMessage(asg, "Knappe juffrouw", correlationID);
                break;
            case "ESN":
                sendMessage(asg, "Sexy Juf", correlationID);
                break;
        }
    }

    public void sendMessage(Assignment asg, String channel, String correlationID){
        gateway.publishMessage(asg, channel, correlationID);
    }


}
