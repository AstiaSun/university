import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * Main class which creates UI components
 * Created by anastasia on 4/12/17.
 */
public class Form {
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

        ActionListener actionListener = e -> {
            if (e.getSource() == clearButton) {
                drawArea.clearData();
            } else if (e.getSource() == okButton) {
                drawArea.triangulate();
            } else if (e.getSource() == generatePointsButton) {
                if (!Objects.equals(amountOfPoints.getText(), "")) {
                    drawArea.generatePoints(Integer.parseInt(amountOfPoints.getText()));
                }
            }
        };

        generatePointsButton.addActionListener(actionListener);
        clearButton.addActionListener(actionListener);
        okButton.addActionListener(actionListener);
        controls.add(amountOfPoints);
        controls.add(generatePointsButton);
        controls.add(clearButton);
        controls.add(okButton);
        content.add(controls, BorderLayout.NORTH);

        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
