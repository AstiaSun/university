package server.core;

import generated.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by anastasia on 4/25/17.
 */
public abstract class AbstractProcessor {

    public abstract List<Group> getGroups();

    public abstract int getAmountOfGroups();

    public abstract void checkValidData(Group group) throws Exception;

    public abstract void checkValidData(Student newStudent) throws Exception;

    public abstract void addGroup(Group group) throws Exception;

    public abstract void addStudent(Student student) throws Exception;

    private void assertNotEqual(Student firstStudent, Student secondStudent) throws Exception {
        String duplicateError = "ERROR: duplicates in student are not allowed.";
        if (firstStudent.getFirstName().equals(secondStudent.getFirstName()) &&
                firstStudent.getLastName().equals(secondStudent.getLastName()) &&
                firstStudent.getPhoneNumber().equals(secondStudent.getPhoneNumber())) {
            throw new Exception(duplicateError);
        }
    }

    public abstract void changeGroup(Group group) throws Exception;

    public abstract void changeStudent(Student student) throws Exception;

    public abstract Student getStudent(int id);

    public abstract boolean deleteGroup(String name);

    public abstract boolean deleteGroup(Group group);

    public abstract boolean deleteStudent(int studentIndex);

    public abstract Group getGroup(String groupName);

    public abstract List<Student> getStudentsFromGroup(String groupName);

    public abstract void closeDatabase();
}
