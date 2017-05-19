package client.ui;

import client.Client;
import common.Utilities;
import server.core.AbstractProcessor;
import generated.Group;
import client.ui.BaseClasses.ShowDetailedObjectFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Анастасия on 03.05.2017.
 */
public class GroupInfoFrame extends ShowDetailedObjectFrame {
    private JComboBox<String> nameComboBox;

    public GroupInfoFrame(Client client, Point parentLocation) {
        super("Group Info", client, parentLocation);
    }

    @Override
    protected void submitChanges() {
    }

    @Override
    protected JPanel createFieldsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(nameComboBox);
        return panel;
    }

    @Override
    protected JPanel createLabelsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        addLabel(panel, "Select group:");
        return panel;
    }

    @Override
    protected void initFields() {
        nameComboBox = createComboBox();
        fillGroupComboBoxWithInfo(nameComboBox);
        addComboBoxAction();
        info = new JTextArea();
    }

    private void addComboBoxAction() {
        nameComboBox.addActionListener(e -> {
            if (e.getSource() == nameComboBox) {
                String name = (String) nameComboBox.getSelectedItem();
                Group group = client.getGroupFromDatabase(name);
                info.setText(Utilities.groupToString(group));
                okButton.setEnabled(true);
            }
        });
    }
}
