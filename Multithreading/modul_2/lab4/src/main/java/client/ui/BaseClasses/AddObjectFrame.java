package client.ui.BaseClasses;

import client.Client;
import server.core.AbstractProcessor;

import javax.swing.*;
import java.awt.*;

/**
 * Created by anastasia on 4/24/17.
 */
public abstract class AddObjectFrame extends BaseFrame {

    protected Client client;

    protected AddObjectFrame(String name, Client client, Point parentLocation) {
        super(name);
        this.client = client;
        setLocationAccordingToParent(parentLocation);
        initialize();
    }

    private void initialize() {
        setSize(WIDTH, HEIGHT + 50);
        setVisible(true);
        setResizable(false);
        Container content = getContentPane();
        content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
        content.add(createPanel());
    }
}
