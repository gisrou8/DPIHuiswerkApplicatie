package Teacher;

import Models.Assignment;
import TeacherMessaging.TeacherGateway;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class TeacherMain extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Teacherpage.fxml"));

        primaryStage.setTitle("Teacher");
        Parent root = loader.load();
        TeacherController controller = loader.getController();
        controller.setListener("chef", "StudentResponse");
        controller.setListener("Sexy Juf", "StudentResponse");
        controller.setListener("Leraar", "StudentResponse");
        controller.setListener("Knappe juffrouw", "StudentResponse");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }




}
