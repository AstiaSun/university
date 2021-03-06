package client.ui;

import client.Client;
import common.Utilities;
import generated.Group;
import generated.Student;
import client.ui.BaseClasses.AddObjectFrame;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Created by anastasia on 4/24/17.
 */
public class AddStudentFrame extends AddObjectFrame {

    private JComboBox<String> groupComboBox;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField addressField;
    private JTextField phoneNumberField;

    public AddStudentFrame(Client client, Point parentLocation) {
        super("Add new student", client, parentLocation);
    }


    protected void initFields() {
        firstNameField = new JTextField();
        lastNameField = new JTextField();
        addressField = new JTextField();
        phoneNumberField = new JTextField();
    }

    public JPanel createLabelsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        addLabel(panel, "Group Name:\t");
        addLabel(panel, "First Name:\t");
        addLabel(panel, "SecondName:\t");
        addLabel(panel, "Address:\t");
        addLabel(panel, "Phone Number:\t");
        panel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        return panel;
    }

    protected JPanel createFieldsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        addGroupComboBox(panel);
        firstNameField = createTextField();
        addField(panel, firstNameField);
        lastNameField = createTextField();
        addField(panel, lastNameField);
        addressField = createTextField();
        addField(panel, addressField);
        phoneNumberField = createTextField();
        addField(panel, phoneNumberField);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return panel;
    }

    private void addGroupComboBox(JPanel panel) {
        groupComboBox = createComboBox();
        fillComboBoxWithInfo();
        panel.add(groupComboBox);
        panel.add(Box.createRigidArea(MainFrame.verticalGap));
    }

    private void fillComboBoxWithInfo() {
        ArrayList<Group> groups = client.getGroupsFromDatabase();
        if (groups != null) {
            for (int i = 0; i < groups.size(); i++) {
                groupComboBox.addItem(groups.get(i).getName());
            }
        }
    }

    protected void submitChanges() {
        String groupName = (String) groupComboBox.getSelectedItem();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String address = addressField.getText();
        String phoneNumber = phoneNumberField.getText();
        try {
            if (Objects.equals(groupName, "") || (Objects.equals(firstName, ""))
                    || (Objects.equals(lastName, "")) || Objects.equals(address, "")
                    || Objects.equals(phoneNumber, "")) {
                throw new Exception("Empty fields are not allowed");
            }
        } catch (Exception e1) {
            errorLabel.setText(e1.getMessage());
            return;
        }
        try {
            int groupId = client.getGroupIdFromDatabase(groupName);
            if (groupId == -1)
                throw new Exception("Cannot resolve group name");
            Student student = Utilities.createStudent(0, firstName, lastName, address, phoneNumber, groupId);
            client.addStudentToDatabase(student);
            MainFrame.dataWasChanged = true;
            dispose();
        } catch (Exception e1) {
            errorLabel.setText(e1.getMessage());
        }
    }
}
