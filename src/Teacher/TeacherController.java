package Teacher;

import Models.Assignment;
import TeacherMessaging.TeacherGateway;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherController {
    @FXML
    private ListView<Assignment> lvIncoming;

    @FXML
    private Button btnSend;

    @FXML
    private ListView<Assignment> lvReviewed;

    @FXML
    private Button btnReviewed;


    private TeacherGateway gateway;
    private ObservableList<Assignment> submitted = FXCollections.observableArrayList();
    private ObservableList<Assignment> reviewed = FXCollections.observableArrayList();
    private Map<String, Assignment> assignments = new HashMap<>();
    private String correlationID;


    @FXML
    void btnReviewed_onClick(ActionEvent event) {
        Assignment asg = lvIncoming.getSelectionModel().getSelectedItem();
        asg.setReviewed(true);
        reviewed.add(asg);
        lvReviewed.setItems(reviewed);
    }

    @FXML
    void btnSend_onClick(ActionEvent event) {
        Assignment asg = lvReviewed.getSelectionModel().getSelectedItem();
        gateway.sendResponse(asg, asg.getCorrelationId());


    }


    public void setListener(String Tchannel, String Qchannel){
        gateway = new TeacherGateway(Tchannel, Qchannel) {
            @Override
            public void onMessageArrived(Assignment asg, String correlationId) {
                asg.setReviewedBy(Tchannel);
                correlationID = correlationId;
                assignments.put(correlationId, asg);
                submitted.add(asg);
                lvIncoming.setItems(submitted);
            }
        };
    }
}
