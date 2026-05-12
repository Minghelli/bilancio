package bilancio;

import java.util.ArrayList;
import javax.swing.table.*;

/**
 * The main table model.
 * <p>
 * Determinates how every cell must be.
 * 
 * @author Minghe
 * @version 1.0
 */
public class TabModel extends AbstractTableModel {
    private final ArrayList<MainValues> list;
    private double tot;
    
    private final String[] columns = {"Data","Valore","Descrizione"};
    
    /**
     * Constructs the new model.
     * 
     * @param list The main list to display on the table.
     */
    public TabModel(ArrayList<MainValues> list){
        this.list = list;
        this.tot = 0;
    }
    
    @Override
    public int getColumnCount(){
        return columns.length;
    }
    
    @Override
    public int getRowCount(){
        return list.size()+1;
    }
    
    @Override
    public String getColumnName(int c){
        return columns[c];
    }
    
    @Override
    public Object getValueAt(int r,int c){
        if(r == list.size()){
            if(c == 1){
                tot = 0;
                for (MainValues mainValues : list) tot += mainValues.getValue();
            }
            return switch(c){
                case 0 -> "TOTALE: ";
                case 1 -> tot;
                default -> "";
            };
        }
        
        
        return switch(c){
            case 0 -> list.get(r).getDate();
            case 1 -> list.get(r).getValue();
            case 2 -> list.get(r).getDesc();
            default -> "";
        };
    }
}
