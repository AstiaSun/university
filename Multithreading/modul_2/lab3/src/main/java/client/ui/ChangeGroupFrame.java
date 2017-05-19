package client.ui;

import client.Client;
import common.Utilities;
import server.core.AbstractProcessor;
import generated.Group;
import client.ui.BaseClasses.ChangeObjectFrame;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * Created by anastasia on 4/24/17.
 */
public class ChangeGroupFrame extends ChangeObjectFrame {

    private JComboBox<String> nameComboBox;
    private JTextField newNameField;
    private JTextField facultyField;
    private JTextField courseField;

    public ChangeGroupFrame(Client client, Point parentLocation) {
        super("Change group data", client, parentLocation);
    }

    @Override
    protected void submitChanges() {
        String name = (String) nameComboBox.getSelectedItem();
        String newName = newNameField.getText();
        String faculty = facultyField.getText();
        int course;
        try {
            if (Objects.equals(faculty, "")) {
                throw new Exception("Empty fields are not allowed");
            }
            course = Integer.valueOf(courseField.getText());
            if ((course < 1) || (course > 6)) {
                throw  new Exception("Only decimal numbers (1-6) are allowed");
            }
        } catch (Exception e1) {
            errorLabel.setText(e1.getMessage());
            return;
        }
        try {
            int id = client.getGroupIdFromDatabase(name);
            if(!Objects.equals(newName, "") && !Objects.equals(newName, null) && !Objects.equals(newName, name)){
                name = newName;
            }
            Group group = Utilities.createGroup(id, name, faculty, course);
            client.updateGroupInDatabase(group);
            MainFrame.dataWasChanged = true;
            dispose();
        } catch (Exception e1) {
            errorLabel.setText(e1.getMessage());
        }
    }

    @Override
    protected JPanel createFieldsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(nameComboBox);
        panel.add(Box.createRigidArea(MainFrame.verticalGap));
        addField(panel, newNameField);
        addField(panel, facultyField);
        addField(panel, courseField);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return panel;
    }

    @Override
    protected JPanel createLabelsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        addLabel(panel, "Name:");
        addLabel(panel, "New Name:");
        addLabel(panel, "Faculty:");
        addLabel(panel, "Course:");
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return panel;
    }

    @Override
    protected void initFields() {
        nameComboBox = createComboBox();
        fillGroupComboBoxWithInfo(nameComboBox);
        addComboBoxAction();
        newNameField = createTextField();
        facultyField = createTextField();
        courseField = createTextField();
    }

    private void addComboBoxAction() {
        nameComboBox.addActionListener(e -> {
            if (e.getSource() == nameComboBox) {
                String groupName = (String) nameComboBox.getSelectedItem();
                Group group = client.getGroupFromDatabase(groupName);
                facultyField.setText(group.getFaculty());
                courseField.setText(String.valueOf(group.getCourse()));
            }
        });
    }
}
