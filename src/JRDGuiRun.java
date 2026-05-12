import java.awt.EventQueue;

public class JRDGuiRun {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JRDGui();
            }
        });
    }
}