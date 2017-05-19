package client.ui.BaseClasses;

import client.Client;
import server.core.AbstractProcessor;
import generated.Group;
import generated.Student;
import client.ui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by anastasia on 4/24/17.
 */
public abstract class ShowDetailedObjectFrame extends BaseFrame {

    protected JTextArea info;

    protected Client client;

    public ShowDetailedObjectFrame(String title, Client client, Point parentLocation) {
        super(title);
        this.client = client;
        setLocationAccordingToParent(parentLocation);
        initialize();
    }

    private void initialize() {
        setSize(WIDTH, HEIGHT);
        setVisible(true);
        setResizable(false);
        Container content = getContentPane();
        content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
        content.add(createPanel());
    }

    protected JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(Box.createRigidArea(new Dimension(WIDTH, HEIGHT / 4)));
        panel.add(createLabelsAndFieldsPanel());
        panel.add(Box.createRigidArea(MainFrame.verticalGap));
        panel.add(info);
        panel.add(Box.createRigidArea(MainFrame.verticalGap));
        panel.add(createButtonsPanel());
        panel.add(Box.createRigidArea(MainFrame.verticalGap));
        return panel;
    }

    protected String getInfo(Group group) {
        return group.getName() + "\t" + group.getFaculty() + "\t" + group.getCourse() + "\n";
    }

    protected String getInfo(Student student) {
        return student.getFirstName() + "\t" + student.getLastName() + "\t" + student.getAddress() + "\t" + student.getPhoneNumber() + "\n";
    }

    protected void fillGroupComboBoxWithInfo(JComboBox<String> comboBox) {
        ArrayList<Group> groups = client.getGroupsFromDatabase();
        if (groups != null) {
            for (int i = 0; i < groups.size(); i++) {
                comboBox.addItem(groups.get(i).getName());
            }
        }
    }
}
