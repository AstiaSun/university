import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * Main class which creates UI components
 * Created by anastasia on 4/12/17.
 */
class Form {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());
        final DrawArea drawArea = new DrawArea();
        content.add(drawArea, BorderLayout.CENTER);

        JPanel controls = new JPanel();

        JTextField amountOfPoints = new JTextField("100");
        amountOfPoints.setPreferredSize(new Dimension(50, 25));
        JButton generatePointsButton = new JButton("Generate");
        JButton clearButton = new JButton("Clear");
        JButton okButton = new JButton("OK");
        JButton saveButton = new JButton("Save");

        ActionListener actionListener = e -> {
            if (e.getSource() == clearButton) {
                drawArea.clearData();
            } else if (e.getSource() == okButton) {
                drawArea.triangulate();
            } else if (e.getSource() == generatePointsButton) {
                if (!Objects.equals(amountOfPoints.getText(), "")) {
                    drawArea.generatePoints(Integer.parseInt(amountOfPoints.getText()));
                }
            } else if (e.getSource() == saveButton) {
                drawArea.save();
            }
        };

        generatePointsButton.addActionListener(actionListener);
        clearButton.addActionListener(actionListener);
        okButton.addActionListener(actionListener);
        saveButton.addActionListener(actionListener);
        controls.add(amountOfPoints);
        controls.add(generatePointsButton);
        controls.add(clearButton);
        controls.add(okButton);
        controls.add(saveButton);
        content.add(controls, BorderLayout.NORTH);

        frame.setSize(1200, 900);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
