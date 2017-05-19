package client;

import common.Request;
import common.Utilities;
import generated.Group;
import generated.Student;
import server.core.AbstractProcessor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by Анастасия on 08.05.2017.
 */
public class Client {
    AbstractProcessor processor;

    public Client() {
        try {
            initConnection();
        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void initConnection() throws RemoteException, NotBoundException, MalformedURLException {
       String URL = "//localhost:123/database";
       processor = (AbstractProcessor) Naming.lookup(URL);
       System.out.println("RMI object was found");
    }

    public Student getStudentFromDatabase(int id) {
        try {
            return processor.getStudent(id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Group getGroupFromDatabase(String name) {
        try {
            return processor.getGroup(name);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Group> getGroupsFromDatabase() {
        try {
            return (ArrayList<Group>) processor.getGroups();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public int getGroupIdFromDatabase(String groupName) {
        try {
            return processor.getGroup(groupName).getId();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void addStudentToDatabase(Student student) {
        try {
            processor.addStudent(student);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addGroupToDatabase(Group group) {
        try {
            processor.addGroup(group);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateStudentInDatabase(Student student) {
        try {
            processor.changeStudent(student);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateGroupInDatabase(Group group) {
        try {
            processor.changeGroup(group);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean deleteStudentFromDatabase(int id) {
        try {
            return processor.deleteStudent(id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteGroupFromDatabase(String name) {
        try {
            return processor.deleteGroup(name);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void closeConnection() {
        try {
            processor.closeDatabase();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
