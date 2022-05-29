import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SortingMainGraphics extends JFrame{

    public static void main(String[] args){

        SortingMainGraphics window = new SortingMainGraphics();
        JPanel p = new JPanel();
        p.add(new SortingPanel()); 
        window.setTitle("Sorting with Graphics");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(p);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

}