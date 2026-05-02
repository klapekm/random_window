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
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        setLayout(new GridBagLayout());
        setResizable(false);

        //Adding an input window(file size)
        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 75;
        c.ipady = 25;
        add(new JLabel("File Size"), c);
        fileSize = new JTextField();
        fileSize.setText("100");
        fileSize.setPreferredSize(new Dimension(75, 25));
        c.gridx = 1;
        c.gridy = 0;
        add(fileSize,c);
        //Adding an input window(generator choice)
        c.gridx = 0;
        c.gridy = 1;
        add(new JLabel("Generator"),c);
        generators = new JComboBox<>();
        c.gridx = 1;
        c.gridy = 1;
        add(generators,c);

        Provider[] providers = Security.getProviders();
        for (Provider provider: providers) {
            for (Provider.Service service : provider.getServices()) {
                if (service.getType().equals("SecureRandom")) {
                    generators.addItem(service.getAlgorithm());
                }
            }
        }

        //Adding an input window(file path)
        c.gridx = 0;
        c.gridy = 2;
        add(new JLabel("File Path"), c);
        localization = new JTextField();
        localization.setPreferredSize(new Dimension(75, 25));
        c.gridx = 1;
        c.gridy = 2;
        add(localization,c);
        //Generate file button
        generateButton = new JButton();
        generateButton.setText("Generate File");
        generateButton.addActionListener(this);
//        generateButton.setPreferredSize(new Dimension(400, 50));
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        add(generateButton,c);

        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent a) {
        Object source = a.getSource();
        if (source == generateButton) {
            String loc = localization.getText();
            String size = fileSize.getText();
            SecureRandom rndGen;

            if (Objects.equals(loc, "")) {
                localization.setText(generators.getSelectedItem()+"_sample"+".txt");
            }
            else {
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
                    for (int i = 0; i < (Integer.parseInt(size)); i++) {
                        myWriter.write(rndGen.nextInt(11) + "\n");
                    }
                    myWriter.close();
                } catch (IOException e) {
                    System.out.println("ojojoj");
                }
            }

        }
    }
}