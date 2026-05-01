import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Provider;
import java.security.Security;
import java.util.Objects;

public class Window extends JFrame implements ActionListener{
    private final JButton generateButton;
    private final JTextField fileSize;
    private final JTextField localization;
    private final JComboBox<String> generators;
    //Creating the window
    public Window() {
        super("Illin'");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 250);
        setLocation(50, 50);
        setLayout(new GridLayout(5, 3));
        //Adding an input window(file size)
        add(new JLabel("File Size"));
        fileSize = new JTextField();
        add(fileSize);
        //Adding an input window(generator choice)
        add(new JLabel("Generator"));
        generators = new JComboBox<>();
        add(generators);

        Provider[] providers = Security.getProviders();
        for (Provider provider: providers) {
            for (Provider.Service service : provider.getServices()) {
                if (service.getType().equals("SecureRandom")) {
                    generators.addItem(service.getAlgorithm());
                }
            }
        }
        //Adding an input window(file path)
        add(new JLabel("File Path"));
        localization = new JTextField();
        add(localization);
        //Generate file button
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
            SecureRandom rndGen;

            //Setting up the correct algorithm
            try {
                rndGen = SecureRandom.getInstance((String) Objects.requireNonNull(generators.getSelectedItem()));
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            //Creates a new file, if it does exist clear it
            try {
                FileWriter myWriter = new FileWriter(loc);
                myWriter.flush();
                //Writing the random numbers
                for (int i = 0; i<(Integer.parseInt(size)); i++) {
                    myWriter.write(rndGen.nextInt(11)+"\n");
                }
                myWriter.close();
            } catch (IOException e) {
                System.out.println("ojojoj");
            }

        }
    }
}