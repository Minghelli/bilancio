package panels;

import mainPanels.TabPanel;
import bilancio.MainValues;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import exceptions.WrongDatePatternException;

/**
 * The panel containing the instructions for voices modifing.
 * <p>
 * It extends the CustomPanel class adding the necessary toggles for voices modifing.
 * 
 * @author Minghe
 * @version 1.1
 */
public class ModPanel extends CustomPanel implements ActionListener{
    private JButton mod,canc;
    private JFrame fr;
    private JTable tab;
    private JTextField valueField,dateField,descField;
    
    private int index;
    private MainValues values;
    private ArrayList<MainValues> list,newList;
    
    /**
     * Constructs the new panel.
     * 
     * @param list The main list to operate on.
     * @param fr The parent frame to close when the operations are finished.
     * @param index The voice's index in the list.
     */
    public ModPanel(ArrayList<MainValues> list,JFrame fr,int index){
        super();
        
        this.list = list;
        this.fr = fr;
        this.tab = TabPanel.getTab();
        this.valueField = getValueField();
        this.dateField = getDateField();
        this.descField = getDescField();
        
        this.index = index;
        
        mod = new JButton("Mod");
        canc = new JButton("Canc");
        
        add(mod);
        add(canc);
        
        values = new MainValues();
        if(DatePanel.filterCheck()){
            newList = DatePanel.getFilteredList();
            values = newList.get(index);
        }else
            values = this.list.get(index);
        
        valueField.setText(String.valueOf(values.getValue()));
        descField.setText(values.getDesc());
        dateField.setText(values.getDate());
        
        mod.addActionListener(this);
        canc.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Mod")){
            try{
                isValid(dateField.getText());
                
                values.setValue(Double.parseDouble(valueField.getText()));
                values.setDate(dateField.getText());
                values.setDesc(descField.getText());

                if(DatePanel.filterCheck()){
                    int mainIndex = list.indexOf(values);
                    
                    newList.set(index,values);
                    list.set(mainIndex, values);
                }
                else
                    list.set(index,values);
                
                tab.revalidate();
                tab.repaint();
                
                fr.setVisible(false);
            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(null,"Inserire un valore numerico corretto","ERRORE",JOptionPane.ERROR_MESSAGE);
            }catch(WrongDatePatternException ex) {
                JOptionPane.showMessageDialog(null,"Pattern data errato, correggere!","ERRORE",JOptionPane.ERROR_MESSAGE);
            }
        }
        if(e.getActionCommand().equals("Canc")){
            fr.setVisible(false);
        }
    }
    
}
