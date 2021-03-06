package ui;

import builder.XMLProcessor;
import generated.Group;
import generated.Student;
import ui.BaseClasses.ShowDetailedObjectFrame;

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

    public DeleteStudentFrame(XMLProcessor processor, Point parentLocation) {
        super("Delete student", processor, parentLocation);
    }

    @Override
    protected void submitChanges() {
        String groupName = (String) groupComboBox.getSelectedItem();
        Integer studentId = (Integer) studentComboBox.getSelectedItem();

        if (processor.deleteStudentByIndex(groupName, studentId)) {
            JOptionPane.showMessageDialog(this, "Successfully deleted!");
            MainFrame.dataWasChanged = true;
            dispose();
        } else {
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
            Group group = processor.getGroupByUniqueName((String) groupComboBox.getSelectedItem());
            fillStudentComboBoxWithInfo(group);
        } else if (e.getSource() == studentComboBox) {
            Student student = processor.getStudentsFromGroupByUniqueName((String) groupComboBox.getSelectedItem())
                    .get(studentComboBox.getSelectedIndex());
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
