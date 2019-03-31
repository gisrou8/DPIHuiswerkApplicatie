package JMSMessage;

import Models.Assignment;
import com.owlike.genson.Genson;

public class Serializer {
    private Genson genson;


    public Serializer(){
        genson = new Genson();
    }

    public String assignmentToString(Assignment asg){
        String json = genson.serialize(asg);
        return json;
    }


    public Assignment stringToAssigment(String json){
        return genson.deserialize(json, Assignment.class);
    }
}
