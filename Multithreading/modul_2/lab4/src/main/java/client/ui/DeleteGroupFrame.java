package client.ui;

import client.Client;
import server.core.AbstractProcessor;
import generated.Group;
import client.ui.BaseClasses.ShowDetailedObjectFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Created by anastasia on 4/24/17.
 */
public class DeleteGroupFrame extends ShowDetailedObjectFrame {

    private JComboBox<String> nameComboBox;

    public DeleteGroupFrame(Client client, Point parentLocation) {
        super("Delete group", client, parentLocation);
    }

    @Override
    protected void submitChanges() {
        String name = (String) nameComboBox.getSelectedItem();
        if (client.deleteGroupFromDatabase(name)) {
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
                info.setText(getInfo(group));
                okButton.setEnabled(true);
            }
        });
    }
}
