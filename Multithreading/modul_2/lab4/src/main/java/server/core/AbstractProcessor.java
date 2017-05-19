package server.core;

import generated.*;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by anastasia on 4/25/17.
 */
public interface AbstractProcessor extends Remote {

    List<Group> getGroups() throws RemoteException;

    void checkValidData(Group group) throws Exception;

    void checkValidData(Student newStudent) throws Exception;

    void addGroup(Group group) throws Exception;

    void addStudent(Student student) throws Exception;

    void changeGroup(Group group) throws Exception;

    void changeStudent(Student student) throws Exception;

    Student getStudent(int id) throws RemoteException;

    boolean deleteGroup(String name) throws RemoteException;

    boolean deleteStudent(int studentIndex) throws RemoteException;

    Group getGroup(String groupName) throws RemoteException;

    List<Student> getStudentsFromGroup(String groupName) throws RemoteException;

    void closeDatabase() throws RemoteException;
}
