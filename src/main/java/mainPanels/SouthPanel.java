package mainPanels;

import bilancio.MainValues;
import exceptions.WrongFileFormatException;
import exceptions.WrongFileNameException;
import panels.DatePanel;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import saving.*;

/**
 * One of the panels that appears on the main frame.
 * <p>
 * This panel contains several buttons to save and load voices from external files and an information searching tool.
 * 
 * @author Minghe
 * @version 1.1
 */
public class SouthPanel extends JPanel implements ActionListener{
    private JButton search,load,save;
    private JTextField searchField;
    private JTable tab;
    
    private ArrayList<MainValues> list;
    private int searchIndex = 0;
    
    /**
     * Constructs the new JPanel.
     * 
     * @param list the main list to operate on.
     */
    public SouthPanel(ArrayList<MainValues> list){
        super();
        
        this.list = list;
        this.tab = TabPanel.getTab();
        
        searchField = new JTextField(20);
        
        search = new JButton("Search");
        load = new JButton("Load");
        save = new JButton("Save");
        
        add(searchField);
        add(search);
        add(Box.createHorizontalStrut(110));
        add(load);
        add(save);
        
        search.addActionListener(this);
        save.addActionListener(this);
        load.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Search" -> {
                if(list.isEmpty())
                    JOptionPane.showMessageDialog(null, "Nessuna voce presente.", "Errore",JOptionPane.ERROR_MESSAGE);
                else{
                    String text = searchField.getText();
                    ArrayList<MainValues> l;
                    
                    if(DatePanel.filterCheck())
                        l = DatePanel.getFilteredList();
                    else
                        l = list;
                    
                    int i = searchIndex;
                    for(;i<l.size();i++)
                        if(l.get(i).getDesc().contains(text)){
                            tab.setRowSelectionInterval(i,i);
                            break;
                        }
                    if(i == l.size()){
                        tab.clearSelection();
                        searchIndex = 0;
                    }else
                        searchIndex = i+1;
                }
            }
            case "Save" -> {
                IOProcedures s;
                String options[] = {".txt",".csv"};
                if(DatePanel.filterCheck()){
                    JOptionPane.showMessageDialog(null,"Disinserire i filtri per salvare.","Errore",JOptionPane.ERROR_MESSAGE);
                }
                else if(list.isEmpty())
                    JOptionPane.showMessageDialog(null,"Nessuna voce presente.","Errore",JOptionPane.ERROR_MESSAGE);
                else{
                    int sel = JOptionPane.showOptionDialog(null, "Seleziona Formato:", "Scegli formato",
                            0,JOptionPane.INFORMATION_MESSAGE,null, options, null);
                    
                    if(sel != -1){
                        JFileChooser c = new JFileChooser();
                        int ret = c.showSaveDialog(null);
                        if(ret == JFileChooser.APPROVE_OPTION){
                            try {
                                File f = c.getSelectedFile();

                                //System.out.println(ret);
                                if(sel == 0)
                                    s = new TxtIO();
                                else
                                    s = new CsvIO();

                                s.save(list,f);                  //<------
                            } catch (WrongFileFormatException ex) {
                                JOptionPane.showMessageDialog(null,"Estensione non corrispondente!","Errore Salvataggio",JOptionPane.ERROR_MESSAGE);
                            } catch (IOException ex) {
                                JOptionPane.showMessageDialog(null,"Errore in I/O","Errore I/O",JOptionPane.ERROR_MESSAGE);
                            } catch (WrongFileNameException ex) {
                                JOptionPane.showMessageDialog(null,"Nome file errato!","Errore Salvataggio",JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }
            }
            case "Load" -> {
                int choise = 0 ;
                if(DatePanel.filterCheck()){
                    JOptionPane.showMessageDialog(null,"Disinserire i filtri per caricare.","Errore",JOptionPane.ERROR_MESSAGE);
                }
                else if(!list.isEmpty())
                    choise = JOptionPane.showConfirmDialog(null,"Il caricamento da file esterno comporta la perdita dei dati attuali,"
                            + " procedere comunque?","Caricamento",JOptionPane.YES_NO_OPTION);
                if(choise == 0){
                    IOProcedures r;
                    
                    ArrayList<MainValues> newList;
                    
                    JFileChooser c = new JFileChooser();
                    int ret = c.showSaveDialog(null);
                    
                    if(ret == JFileChooser.APPROVE_OPTION){
                        File f = c.getSelectedFile();
                        System.out.println(f);
                        
                        try {
                            String file = f.toString();
                            String ext[] = file.split("\\.");
                            if(ext.length <= 1){
                                JOptionPane.showMessageDialog(null,"Nome file errato!","Errore Lettura",JOptionPane.ERROR_MESSAGE);
                            }
                            else{
                                if(ext[1].equals("txt"))
                                    r = new TxtIO();
                                else
                                    r = new CsvIO();

                                newList = r.read(f);            //<-------

                                list.clear();
                                list.addAll(newList);
                                tab.revalidate();
                            }
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null,"Errore in I/O","Errore I/O",JOptionPane.ERROR_MESSAGE);
                        } catch(NumberFormatException ex){
                            JOptionPane.showMessageDialog(null,"Formato file non compatibile","Errore in lettura",JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
            default -> {}
        }
    }
}