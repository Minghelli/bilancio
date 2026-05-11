package panels;

import mainPanels.TabPanel;
import bilancio.MainValues;
import exceptions.WrongDatePatternException;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.*;

/**
 * The panel containing the instructions for voices adding.
 * <p>
 * It extends the CustomPanel class adding the necessary toggles for voices adding.
 * 
 * @author Minghe
 * @version 1.1
 */
public class AddPanel extends CustomPanel implements ActionListener{
    private JButton add,canc;
    private JFrame fr;
    private JTable tab;
    private JTextField valueField,dateField,descField;
    
    private ArrayList<MainValues> list;

    /**
     * Constructs the new panel.
     * 
     * @param list the main list to operate on-
     * @param fr the parent frame to close when the operations are finished.
     */
    public AddPanel(ArrayList<MainValues> list,JFrame fr){
        super();
        
        this.list = list;
        this.fr = fr;
        this.tab = TabPanel.getTab();
        this.valueField = getValueField();
        this.dateField = getDateField();
        this.descField = getDescField();
        
        add = new JButton("Add");
        canc = new JButton("Canc");
        
        LocalDate d = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("d/M/yyyy");
        String d2 = d.format(format);
        
        descField.setText("-");
        dateField.setText(d2);
        
        add(this.add);
        add(this.canc);
        
        add.addActionListener(this);
        canc.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Add")){
            try{
                MainValues values = new MainValues();
                
                isValid(dateField.getText());

                values.setValue(Double.parseDouble(valueField.getText()));
                values.setDate(dateField.getText());
                values.setDesc(descField.getText());

                list.add(values);
                tab.revalidate();

                fr.setVisible(false);
            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(null, "Inserire un valore numerico corretto", "ERRORE",JOptionPane.ERROR_MESSAGE);
            }catch(WrongDatePatternException ex) {
                JOptionPane.showMessageDialog(null, "Pattern della data errato, correggere!", "ERRORE",JOptionPane.ERROR_MESSAGE);
            }
        }
        if(e.getActionCommand().equals("Canc")){
            fr.setVisible(false);
        }
    }
}
