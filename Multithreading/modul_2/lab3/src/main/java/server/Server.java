package server;

import com.google.gson.Gson;
import common.Request;
import common.Utilities;
import generated.Group;
import generated.Student;
import server.core.AbstractProcessor;
import server.core.JDBCProcessor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

import static common.Utilities.parseGroup;
import static common.Utilities.parseStudent;


public class Server  {
    private final Gson GSON = new Gson();

    private ServerSocket serverSocket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    private AbstractProcessor processor;

    public static void main(String[] args) {
        new Server().run();
    }


    public void run() {
        try {
            serverSocket = new ServerSocket(5678);
            processor = new JDBCProcessor();
            while (true) {
                Socket connection = serverSocket.accept();
                output = new ObjectOutputStream(connection.getOutputStream());
                input = new ObjectInputStream(connection.getInputStream());
                Request request = (Request) input.readObject();
                processRequest(request);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean processRequest(Request request) {
        System.out.println(request.getObjectType());
        try {
            if (Objects.equals(request.getObjectType(), Student.class.getName())) {
                processStudentRequest(request);
            } else if (Objects.equals(request.getObjectType(), Group.class.getName())) {
                processGroupRequest(request);
            } else {
                processor.closeDatabase();
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private void processStudentRequest(Request request) throws Exception {
        Student student = parseStudent(request.getData());
        switch (request.getCommand()) {
            case POST:
                processor.addStudent(student);
                break;
            case PUT:
                processor.changeStudent(student);
                break;
            case DELETE:
                boolean status = processor.deleteStudent(student.getId());
                output.writeBoolean(status);
                break;
            case GET:
                student = processor.getStudent(student.getId());
                output.writeObject(GSON.toJson(student));
                break;
        }
    }

    private void processGroupRequest(Request request) throws Exception {
        if (request.getData() == null) {
            ArrayList<Group> groups = (ArrayList<Group>) processor.getGroups();
            output.writeObject(GSON.toJson(groups));
        } else {
            processSingleGroupRequest(request);
        }
    }

    private void processSingleGroupRequest(Request request) throws Exception {
        Group group = parseGroup(request.getData());
        switch (request.getCommand()) {
            case POST:
                processor.addGroup(group);
                break;
            case PUT:
                processor.changeGroup(group);
                break;
            case DELETE:
                boolean status = processor.deleteGroup(group.getName());
                System.out.println(status);
                output.writeBoolean(status);
                break;
            case GET:
                group = processor.getGroup(group.getName());
                output.writeObject(GSON.toJson(group));
                break;
        }
    }
}
