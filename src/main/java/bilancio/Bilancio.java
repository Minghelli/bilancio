package bilancio;

import mainPanels.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
/**
 * This is the project's main class.
 * 
 * @author Minghe
 * @version 1.0
 */
public class Bilancio {

    /**
     * The first method called during a program's execution.
     * <p>
     * It creates a new JFrame within the three main panels. It's called by the main method.
     */
    public static void init(){
        ArrayList<MainValues> list = new ArrayList(0);
        
        JFrame mainFrame = new JFrame("Gestione bilancio");
        mainFrame.setLocation(500,150);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        TabPanel tab = new TabPanel(list);
        RightPanel rPan = new RightPanel(list);
        SouthPanel sPan = new SouthPanel(list);
        
        mainFrame.setLayout(new BorderLayout());
        mainFrame.add(tab,BorderLayout.LINE_START);
        mainFrame.add(rPan,BorderLayout.CENTER);
        mainFrame.add(Box.createHorizontalStrut(8),BorderLayout.LINE_END);
        mainFrame.add(sPan,BorderLayout.SOUTH);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
    
    public static void main(String[] args) {
        init();
    }
}
