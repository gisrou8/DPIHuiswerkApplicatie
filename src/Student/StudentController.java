package Student;

import Models.Assignment;
import StudentMessaging.StudentGateway;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class StudentController {


    @FXML
    private TextField tfName;

    @FXML
    private TextField tfClass;

    @FXML
    private TextField tfAssignment;

    @FXML
    private TextField tfSubject;

    @FXML
    private Button btnSubmit;

    @FXML
    private Button btnChoose;

    @FXML
    private ListView<Assignment> lvSubmitted;

    @FXML
    private ListView<Assignment> lvReviewed;

    private Assignment assignment;

    private ObservableList<Assignment> submitted = FXCollections.observableArrayList();
    private ObservableList<Assignment> reviewed = FXCollections.observableArrayList();

    private StudentGateway gateway;


    @FXML
    void btnSubmit_Click(ActionEvent event) {
        assignment = new Assignment(tfName.getText(), tfClass.getText(), tfAssignment.getText(), tfSubject.getText());
        submitted.add(assignment);
        lvSubmitted.setItems(submitted);
        gateway.sendAssignment(assignment);
    }


    public void setListener(String Tchannel, String Qchannel){
        gateway = new StudentGateway(Tchannel, Qchannel) {
            @Override
            public void onAssigmentArrived(Assignment asg) {
                reviewed.add(asg);
                lvReviewed.setItems(reviewed);
            }
        };

    }
}
