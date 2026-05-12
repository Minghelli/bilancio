package mainPanels;

import bilancio.MainValues;
import bilancio.TabModel;
import java.util.ArrayList;
import javax.swing.*;

/**
 * One of the panels that appears on the main frame.
 * <p>
 * This panel contains a JTable with a scroll pane, representing all the list's elements and one other row containing the algebraic sum of
 * the numeric values.
 * 
 * @author Minghe
 * @version 1.0
 */
public class TabPanel extends JPanel{
    private static JTable tab;
    
    /**
     * Constructs the new panel within the JTable.
     * 
     * @param list The main list to display
     */
    public TabPanel(ArrayList<MainValues> list){
        super();
        
        TabModel model = new TabModel(list);
        
        tab = new JTable(model);
        JScrollPane pane = new JScrollPane(tab,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(pane);
    }
    
    /**
     * Returns the Table pointer.
     * <p>
     * Returns the table to be used in other classes.
     * 
     * @return the main table.
     */
    public static JTable getTab(){
        return tab;
    }
}
