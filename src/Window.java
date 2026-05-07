import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Provider;
import java.security.Security;
import java.util.Objects;
import java.lang.Math;

public class Window extends JFrame implements ActionListener{
    private final JButton generateButton;
    private final JTextField fileSize;
    private final JTextField localization;
    private final JComboBox<String> generators;
    private final JComboBox<String> data_type;
    private final long UPPERBOUND = (long) Math.pow(2, 32);
    //Creating the window
    public Window() {
        super("Illin'");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 275);
        setLocation(50, 50);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        setLayout(new GridBagLayout());
        setResizable(false);

        //Padding to fix a weird bug
        c.ipadx = 75;
        c.ipady = 25;

        //Adding an combobox(data type)
        c.gridx = 0;
        c.gridy = 0;
        add(new JLabel("Data Type"),c);
        data_type = new JComboBox<>(new String[] {"Text", "Bytes"});
        c.gridx = 1;
        c.gridy = 0;
        add(data_type,c);


        //Adding an input window(file size)
        c.gridx = 0;
        c.gridy = 1;
        add(new JLabel("File Size(num amount/bytes)"), c);
        fileSize = new JTextField();
        fileSize.setText("100");
        fileSize.setPreferredSize(new Dimension(75, 25));
        c.gridx = 1;
        c.gridy = 1;
        add(fileSize,c);

        //Adding an input window(generator choice)
        c.gridx = 0;
        c.gridy = 2;
        add(new JLabel("Generator"),c);
        generators = new JComboBox<>();
        c.gridx = 1;
        c.gridy = 2;
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
        c.gridy = 3;
        add(new JLabel("File Path"), c);
        localization = new JTextField();
        localization.setPreferredSize(new Dimension(75, 25));
        c.gridx = 1;
        c.gridy = 3;
        add(localization,c);
        //Generate file button
        generateButton = new JButton();
        generateButton.setText("Generate File");
        generateButton.addActionListener(this);
//        generateButton.setPreferredSize(new Dimension(400, 50));
        c.gridx = 0;
        c.gridy = 4;
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
            int choice_id = data_type.getSelectedIndex();
            //If the localization input window is empty fill it with an example path
            if (Objects.equals(loc, "")) {
                if (choice_id == 0) {
                    localization.setText(generators.getSelectedItem() + "_sample" + ".txt");
                }
                else {
                    localization.setText(generators.getSelectedItem() + "_sample" + ".bin");
                }
            }
            //If not proceed with generating the file
            else {
                //Setting up the correct algorithm
                try {
                    rndGen = SecureRandom.getInstance((String) Objects.requireNonNull(generators.getSelectedItem()));
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
                try {
                    //Creating either a bin or txt file based on user choice
                    if (choice_id == 0) {
                        //Opening or creating a file
                        FileWriter myFileWriter = new FileWriter(loc);
                        myFileWriter.flush();
                        //Writing the header
                        myFileWriter.write("#==================================================================\n" +
                                "# generator Java " + generators.getSelectedItem() + "\n" +
                                "#==================================================================\n");
                        myFileWriter.write("type: d\n" + "count: " + size + "\n" + "numbit: 32\n");
                        //Writing the random numbers
                        for (int i = 0; i < (Integer.parseInt(size)); i++) {
                            myFileWriter.write(rndGen.nextLong(UPPERBOUND) + "\n");
                        }
                        myFileWriter.close();
                    } else {
                        //Opening or creating a file
                        DataOutputStream myOutputStream = new DataOutputStream(new FileOutputStream(loc));
                        myOutputStream.flush();
                        //Generating and writing random bytes
                        byte[] my_bytes = new byte[1024];
                        rndGen.nextBytes(my_bytes);
                        myOutputStream.write(ByteBuffer.wrap(my_bytes).array());
                        myOutputStream.close();
                    }

                } catch (IOException e) {
                    System.out.println("ojojoj");
                }
            }

        }
    }
}