package client.ui;

import client.Client;
import server.core.AbstractProcessor;
import generated.Group;
import generated.Student;
import client.ui.BaseClasses.ShowDetailedObjectFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * Created by anastasia on 4/24/17.
 */
public class DeleteStudentFrame extends ShowDetailedObjectFrame {
    private JComboBox<String> groupComboBox;
    private JComboBox<Integer> studentComboBox;

    public DeleteStudentFrame(Client client, Point parentLocation) {
        super("Delete student", client, parentLocation);
    }

    @Override
    protected void submitChanges() {
        Integer studentId = (Integer) studentComboBox.getSelectedItem();
        try {
            if (!client.deleteStudentFromDatabase(studentId)) {
                throw new Exception();
            }
            JOptionPane.showMessageDialog(this, "Successfully deleted!");
            MainFrame.dataWasChanged = true;
            dispose();
        } catch (Exception e) {
            errorLabel.setText("Failed to delete");
        }
    }

    @Override
    protected JPanel createFieldsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        addComboBox(panel, groupComboBox);
        addComboBox(panel, studentComboBox);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return panel;
    }

    @Override
    protected JPanel createLabelsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        addLabel(panel, "Group Name:");
        addLabel(panel, "Student ID:");
        panel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        return panel;
    }

    @Override
    protected void initFields() {
        groupComboBox = createComboBox();
        fillGroupComboBoxWithInfo(groupComboBox);
        studentComboBox = createIntegerComboBox();
        addComboBoxActions();
        info = new JTextArea();
    }

    private void addComboBoxActions() {
        groupComboBox.addActionListener(this::responseAction);
        studentComboBox.addActionListener(this::responseAction);
    }

    private void responseAction(ActionEvent e) {
        if (e.getSource() == groupComboBox) {
            String name = (String) groupComboBox.getSelectedItem();
            Group group = client.getGroupFromDatabase(name);
            fillStudentComboBoxWithInfo(group);
        } else if (e.getSource() == studentComboBox) {
            int studentId = (Integer) studentComboBox.getSelectedItem();
            Student student = client.getStudentFromDatabase(studentId);
            fillFieldsWithData(student);
        }
    }

    private void fillFieldsWithData(Student student) {
        info.setText(getInfo(student));
    }

    private void fillStudentComboBoxWithInfo(Group group) {
        ArrayList<Student> students = (ArrayList<Student>) group.getStudents();
        for (int i = 0; i < students.size(); i++) {
            studentComboBox.addItem(students.get(i).getId());
        }
    }
}
