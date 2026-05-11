package mainPanels;

import bilancio.MainValues;
import panels.*;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.TableModel;

/**
 * One of the panels that appears on the main frame.
 * <p>
 * This panel contains several buttons to add, remove, modify voices and to filter them by different time periods.
 * 
 * @author Minghe
 * @version 1.0
 */
public class RightPanel extends JPanel implements ActionListener{
    private JButton add,remove,mod,filter,clear;
    private JTable tab;
    
    private ArrayList<MainValues> list,newList;
    private TableModel mainMod;
    
    /**
     * Constructs the new JPanel.
     * 
     * @param list the main list to operate on.
     */
    public RightPanel(ArrayList<MainValues> list){
        super();
        
        this.list = list;
        this.tab = TabPanel.getTab();
        this.mainMod = tab.getModel();
        
        this.add = new JButton("Add");
        this.remove = new JButton("Remove");
        this.mod = new JButton("Mod");
        this.filter = new JButton("Date Filter");
        this.clear = new JButton("Clear");
        
        add(Box.createVerticalStrut(5));
        add(this.add); 
        add(Box.createVerticalStrut(15));
        add(this.remove); 
        add(this.mod);
        add(Box.createVerticalStrut(25));
        add(this.filter);
        add(Box.createVerticalStrut(25));
        add(this.clear);
        
        BoxLayout layout = new BoxLayout(this,BoxLayout.Y_AXIS);
        setLayout(layout);
        
        Dimension dim = new Dimension(100,25);
        
        add.setMaximumSize(dim);
        remove.setMaximumSize(dim);
        mod.setMaximumSize(dim);
        filter.setMaximumSize(dim);
        clear.setMaximumSize(dim);
        
        this.add.addActionListener(this);
        this.remove.addActionListener(this);
        this.mod.addActionListener(this);
        this.filter.addActionListener(this);
        this.clear.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Date Filter" -> {
                if(list.isEmpty())
                    JOptionPane.showMessageDialog(null,"Nessuna voce presente!","Errore",JOptionPane.ERROR_MESSAGE);
                else{
                    JFrame dateFrame = new JFrame("Date Filter");
                    DatePanel pan = new DatePanel(list,dateFrame,filter,add);
                    dateFrame.add(pan);
                    dateFrame.setLocation(530,300);
                    dateFrame.setSize(530,100);
                    dateFrame.setVisible(true);
                }
            }
            case "End Filter" -> {
                tab.setModel(mainMod);
                filter.setText("Date Filter");
                DatePanel.setFilterCheck(false);
                add.setEnabled(true);
            }
            case "Remove" -> {
                if(list.isEmpty())
                    JOptionPane.showMessageDialog(null, "Nessuna voce presente.", "Errore",JOptionPane.ERROR_MESSAGE);
                else if(tab.getSelectedRow() == -1 || tab.getSelectedRow() == list.size()){
                    JOptionPane.showMessageDialog(null, "Selezionare una voce corretta", "Errore",JOptionPane.ERROR_MESSAGE);
                }else{
                    if(DatePanel.filterCheck()){
                        newList = DatePanel.getFilteredList();
                        
                        removeFilteredVoice(newList);
                    }else{
                        int index = tab.getSelectedRow();
                        
                        int res = JOptionPane.showConfirmDialog(null,"""
                                                                     Rimuovere la voce?
                                                                     Valore:  """ + list.get(index).getValue() + " €\n"
                                + "Data: " + list.get(index).getDate(), "RIMUOVI",JOptionPane.YES_NO_OPTION);
                        
                        if(res == JOptionPane.YES_OPTION){
                            list.remove(index);
                            tab.revalidate();
                        }
                    }
                }
            }
            case "Clear" -> {
                if(list.isEmpty())
                    JOptionPane.showMessageDialog(null, "Nessuna voce presente.", "Errore",JOptionPane.ERROR_MESSAGE);
                else{
                    int choise = JOptionPane.showConfirmDialog(null,"Sicuro di voler cancellare le voci??","Clear",JOptionPane.YES_NO_OPTION);

                    if(choise == 0){
                        if(DatePanel.filterCheck()){
                            newList = DatePanel.getFilteredList();
                            list.removeAll(newList);
                            newList.clear();
                        }else
                            list.clear();
                        
                        tab.revalidate();
                    }
                }
            }
            default -> {
                try{
                    JFrame addFrame;
                    CustomPanel pan;
                    if(e.getActionCommand().equals("Add")){
                        addFrame = new JFrame("Aggiungi voce");
                        pan = new AddPanel(list,addFrame);
                    }
                    else{
                        addFrame = new JFrame("Modifica voce");
                        int index = tab.getSelectedRow();
                        pan = new ModPanel(list,addFrame,index);
                    }
                    addFrame.add(pan);
                    addFrame.setLocation(400,300);
                    addFrame.pack();
                    addFrame.setVisible(true);
                }catch(IndexOutOfBoundsException ex){
                    JOptionPane.showMessageDialog(null, "Selezionare una voce corretta", "Errore",JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    /**
     * Removes a voice from both filtered and main list.
     * 
     * @param newList the filtered list
     */
    public void removeFilteredVoice(ArrayList<MainValues> newList){
        int row = tab.getSelectedRow();
        MainValues values = newList.get(row);

        int res = JOptionPane.showConfirmDialog(null,"""
                                                     Rimuovere la voce?
                                                     Valore: """ + newList.get(row).getValue() + " €\n"
                + "Data: " + newList.get(row).getDate(), "RIMUOVI",JOptionPane.YES_NO_OPTION);

        if(res == JOptionPane.YES_OPTION){
            newList.remove(values);
            list.remove(values);
            tab.revalidate();
        }
    }
}
