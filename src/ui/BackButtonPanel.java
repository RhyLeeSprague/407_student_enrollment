package ui;

import javax.swing.*;
import java.awt.*;

public class BackButtonPanel extends JPanel {

    public BackButtonPanel(JFrame currentFrame) {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("⬅ Back to Menu");

        backButton.addActionListener(e -> {
            currentFrame.dispose();
            AppLauncher.main(null);
        });

        add(backButton);
    }
}

