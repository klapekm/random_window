import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Window extends JFrame implements ActionListener{

    private JButton generateButton;
    private JTextField fileSize;
    private JTextField localization;

    public Window() {
        super("Illin'");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 250);
        setLocation(50, 50);
        setLayout(new GridLayout(5, 3));

        add(new JLabel("Wielkość Pliku[MB]"));
        fileSize = new JTextField();
        add(fileSize);

        String[] choices = {"Lullaby", "Boys Don't Cry", "Power in the Blood"};
        add(new JLabel("Generator"));
        add(new JComboBox<String>(choices));

        add(new JLabel("Ścieżka"));

        localization = new JTextField();
        add(localization);

        generateButton = new JButton();
        generateButton.addActionListener(this);
        add(generateButton);

        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent a) {
        Object source = a.getSource();

        if (source == generateButton) {
            String loc = localization.getText();
            String size = fileSize.getText();
            Random rnd = new Random();

            try {
                FileWriter myWriter = new FileWriter(loc);
                myWriter.flush();
                for (int i = 0; i<(Integer.parseInt(size)); i++) {
                    myWriter.write(rnd.nextInt(11)+"\n");
                }
                myWriter.close();
            } catch (IOException e) {
                System.out.println("ojojojoj");
                e.printStackTrace();
            }

        }
    }
}