package common;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import generated.Group;
import generated.Student;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Анастасия on 08.05.2017.
 */
public class Utilities {
    private static final Gson GSON = new Gson();

    public static String toJSON(Object object) {
        return GSON.toJson(object);
    }

    public static Student parseStudent(String json) {
        return GSON.fromJson(json, Student.class);
    }

    public static ArrayList<Student> parseStudents(String json) {
        Type listType = new TypeToken<ArrayList<Student>>(){}.getType();
        ArrayList<Student> students = GSON.fromJson(json, listType);
        return students;
    }

    public static Group parseGroup(String json) {
        return GSON.fromJson(json, Group.class);
    }

    public static ArrayList<Group> parseGroups(String json) {
        Type listType = new TypeToken<ArrayList<Group>>(){}.getType();
        ArrayList<Group> groups = GSON.fromJson(json, listType);
        return groups;
    }

    public static Group createGroup(int id, String name, String faculty, int course) {
        Group group = new Group();
        group.setId(id);
        group.setName(name);
        group.setFaculty(faculty);
        group.setCourse(course);
        return group;
    }

    public static Student createStudent(
            int id, String firstName, String lastName, String address, String phoneNumber, int groupId) {
        Student student = new Student();
        student.setId(id);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setAddress(address);
        student.setPhoneNumber(phoneNumber);
        student.setGroupId(groupId);
        return student;
    }

    public static String groupToString(Group group) {
        String info = group.getId() + "\t" + group.getName() + "\t" + group.getFaculty() + "\t" + group.getCourse() + "\n";
        for (int i = 0; i < group.getStudents().size(); i++) {
            info += "\t" + studentToString(group.getStudents().get(i));
        }
        return info;
    }

    public static String studentToString(Student student) {
        return student.getGroupId() + "\t" + student.getId() + "\t" + student.getFirstName() + "\t" + student.getLastName() + "\t" +
                student.getPhoneNumber() + "\t" + student.getAddress() + "\n";
    }
}
