package client;

import common.Request;
import common.Utilities;
import generated.Group;
import generated.Student;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Анастасия on 08.05.2017.
 */
public class Client {
    static private Socket connection;
    static private ObjectInputStream input;
    static private ObjectOutputStream output;

    private void initConnection() {
        try {
            connection = new Socket(InetAddress.getByName("127.0.0.1"), 5678);
            output = new ObjectOutputStream(connection.getOutputStream());
            input = new ObjectInputStream(connection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendRequest(Request request) {
        initConnection();
        try {
            output.flush();
            output.writeObject(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getResponse() {
        try {
            return (String) input.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Student getStudentFromDatabase(int id) {
        Student student = new Student();
        student.setId(id);
        Request request = new Request();
        request.get(Student.class.getName(), Utilities.toJSON(student));
        sendRequest(request);
        String response = getResponse();
        return Utilities.parseStudent(response);
    }

    public Group getGroupFromDatabase(String name) {
        Group group = new Group();
        group.setName(name);
        Request request = new Request();
        request.get(Group.class.getName(), Utilities.toJSON(group));
        sendRequest(request);
        String response = getResponse();
        return Utilities.parseGroup(response);
    }

    public ArrayList<Group> getGroupsFromDatabase() {
        Request request = new Request();
        request.get(Group.class.getName(), null);
        sendRequest(request);
        String response = getResponse();
        if (response != null) {
            return Utilities.parseGroups(response);
        }
        return new ArrayList<>();
    }

    public int getGroupIdFromDatabase(String groupName) {
        Request request = new Request();
        Group group = new Group();
        group.setName(groupName);
        request.get(Group.class.getName(), Utilities.toJSON(group));
        sendRequest(request);
        try {
            group = Utilities.parseGroup((String) input.readObject());
            return group.getId();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void addStudentToDatabase(Student student) {
        Request request = new Request();
        request.post(Student.class.getName(), Utilities.toJSON(student));
        sendRequest(request);
    }

    public void addGroupToDatabase(Group group) {
        Request request = new Request();
        request.post(Group.class.getName(), Utilities.toJSON(group));
        sendRequest(request);
    }

    public void updateStudentInDatabase(Student student) {
        Request request = new Request();
        request.put(Student.class.getName(), Utilities.toJSON(student));
        sendRequest(request);
    }

    public void updateGroupInDatabase(Group group) {
        Request request = new Request();
        request.put(Group.class.getName(), Utilities.toJSON(group));
        sendRequest(request);
    }

    public boolean deleteStudentFromDatabase(int id) {
        Student student = new Student();
        student.setId(id);
        Request request = new Request();
        request.delete(Student.class.getName(), Utilities.toJSON(student));
        sendRequest(request);
        try {
            return input.readBoolean();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
        return false;
    }

    public boolean deleteGroupFromDatabase(String name) {
        Group group = new Group();
        group.setName(name);
        Request request = new Request();
        request.delete(Group.class.getName(), Utilities.toJSON(group));
        sendRequest(request);
        try {
            return input.readBoolean();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void closeConnection() {
        Request request = new Request();
        request.setObjectType("close");
        sendRequest(request);
    }
}
