package panels;

import mainPanels.TabPanel;
import exceptions.*;
import bilancio.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;
import java.util.regex.Pattern;
import javax.swing.*;

/**
 * The date filter panel.
 * <p>
 * This panel allows to filter the list's voices, choosing them by determinated days, weeks, months, years or arbitrary time periods.
 * 
 * @author Minghe
 * @version 1.1
 */
public class DatePanel extends JPanel implements ActionListener{
    private JRadioButton day,week,month,year,period;
    private JButton select,filter,add;
    private JLabel selection,start,end;
    private JTextField startField,endField;
    private JFrame fr;
    private JTable tab;
    
    private ArrayList<MainValues> list;
    
    private static ArrayList<MainValues> newList;
    private static boolean dateFilterCheck = false;
    
    /**
     * Constructs the date filter panel.
     * 
     * @param list the main list tom operate on.
     * @param fr the parent frame to close when the operation are finisherd.
     * @param filter the filter button pointer, when the filter is active it's used to chance the text of the button.
     * @param add the add button pointer, when the filter is active the button is disabled.
     */
    public DatePanel(ArrayList<MainValues> list,JFrame fr,JButton filter,JButton add){
        super();
        
        this.filter = filter;
        this.add = add;
        this.list = list;
        this.fr = fr;
        this.tab = TabPanel.getTab();
        
        selection = new JLabel("Seleziona Per:");
        start = new JLabel("");
        end = new JLabel("");
        
        day = new JRadioButton("Giorno");
        week = new JRadioButton("Settimana");
        month = new JRadioButton("Mese");
        year = new JRadioButton("Anno");
        period = new JRadioButton("Periodo arbitrario");
        
        ButtonGroup grp = new ButtonGroup();
        grp.add(day); grp.add(week); grp.add(month); 
        grp.add(year); grp.add(period);
        
        startField = new JTextField(10);
        endField = new JTextField(10);
        
        startField.setEnabled(false);
        endField.setVisible(false);
        
        select = new JButton("Select");
        
        select.setEnabled(false);
        
        add(selection); add(day); add(week);
        add(month); add(year); add(period);
        add(start); add(startField); add(end);
        add(endField); add(select);
        
        select.addActionListener(this);
        day.addActionListener(this);
        week.addActionListener(this);
        month.addActionListener(this);
        year.addActionListener(this);
        period.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Select" -> {
                try {
                    String startDate = startField.getText();
                    if(day.isSelected()){
                        CustomPanel.isValid(startDate);
                        newList = periodFilter(startDate,1);
                    }else if(week.isSelected()){
                        CustomPanel.isValid(startDate);
                        newList = periodFilter(startDate,2);
                    }else if(month.isSelected()){
                        monthCheck(startDate);
                        newList = periodFilter(startDate,3);
                    }else if(year.isSelected()){
                        yearCheck(startDate);
                        newList = periodFilter(startDate,4);
                    }else{
                        String endDate = endField.getText();

                        CustomPanel.isValid(startDate);
                        CustomPanel.isValid(endDate);
                       
                        newList = periodFilter(startDate,endDate);
                    }
                
                    
                    TabModel newMod = new TabModel(newList);
                    tab.setModel(newMod);
                    
                    dateFilterCheck = true;
                    fr.setVisible(false);
                    
                    filter.setText("End Filter");
                    add.setEnabled(false);
                }catch(ParseException ex) {
                    JOptionPane.showMessageDialog(null, "Parse Error!", "ERROR",JOptionPane.ERROR_MESSAGE);
                }catch(WrongDatePatternException ex){
                    JOptionPane.showMessageDialog(null, "Pattern della data errato, correggere!", "ERRORE",JOptionPane.ERROR_MESSAGE);
                }catch(EmptySearchListException ex) {
                    JOptionPane.showMessageDialog(null, "Nessuna voce trovata!", "ERRORE",JOptionPane.ERROR_MESSAGE);
                }
            }
            case "Giorno" -> {
                start.setText("Giorno: ");
                end.setText("");
                startField.setEnabled(true);
                endField.setVisible(false);
                select.setEnabled(true);
            }
            case "Settimana" -> {
                start.setText("Settimana dal giorno: ");
                end.setText("");
                startField.setEnabled(true);
                endField.setVisible(false);
                select.setEnabled(true);
            }
            case "Mese" -> {
                start.setText("Mese: ");
                end.setText("");
                startField.setEnabled(true);
                endField.setVisible(false);
                select.setEnabled(true);
            }
            case "Anno" -> {
                start.setText("Anno: ");
                end.setText("");
                startField.setEnabled(true);
                endField.setVisible(false);
                select.setEnabled(true);
            }
            case "Periodo arbitrario" -> {
                start.setText("Inizio: ");
                end.setText("Fine: ");
                startField.setEnabled(true);
                endField.setVisible(true);
                select.setEnabled(true);
            }
            default -> {}
        }
    }
    
    /**
     * Returns a new filtered list.
     * <p>
     * The list contains only the voices included in the requested period.
     * 
     * @param date the starting date (day, month or year)
     * @param select allows to choose which operation must be performed
     *               1 - filters a day;
     *               2 - filters a week from the first day;
     *               3 - filters a month;
     *               4 - filters a year;
     * 
     * @return the new ArrayList.
     * @throws ParseException
     * @throws EmptySearchListException 
     */
    public ArrayList<MainValues> periodFilter(String date,int select) throws ParseException,EmptySearchListException{
        ArrayList<MainValues> newList = new ArrayList<>(0);
        
        String compDate;
        switch (select) {
            case 1 -> {
                for (MainValues mainValues : list) {
                    compDate = mainValues.getDate();
                    if (date.equals(compDate))
                        newList.add(mainValues);
                }
            }
            case 2 -> {
                String end = addWeek(date);
                newList = periodFilter(date,end);
            }
            case 3 -> {
                for (MainValues mainValues : list) {
                    compDate = mainValues.getDate();
                    compDate = splitMonthOrYear(compDate, false);
                    if (date.equals(compDate))
                        newList.add(mainValues);
                }
            }
            case 4 -> {
                for (MainValues mainValues : list) {
                    compDate = mainValues.getDate();
                    compDate = splitMonthOrYear(compDate, true);
                    if (date.equals(compDate))
                        newList.add(mainValues);
                }
            }
            default -> {}
        }
       
        if(newList.isEmpty())
            throw new EmptySearchListException("Empty list.");
        return newList;
    }
    
    /**
     * Returns a new filtered ArrayList.
     * <p>
     * The list contains only the voices included in a determinated period of time.
     * 
     * @param start The starting date of the period of time.
     * @param end The ending date of the period of time.
     * @return The new list
     * @throws EmptySearchListException
     * @throws ParseException 
     */
    public ArrayList<MainValues> periodFilter(String start,String end) throws EmptySearchListException,ParseException{
        ArrayList<MainValues> newList = new ArrayList<>(0);

        for (MainValues mainValues : list) {
            String compDate = mainValues.getDate();
            if (compDays(start, end, compDate))
                newList.add(mainValues);
        }
        
        if(newList.isEmpty())
            throw new EmptySearchListException("");
        return newList;
    }
    
    /**
     * Returns a new date incremented by a week.
     * <p>
     * Increments the date by a week using Calendar class.
     * 
     * @param date The date to increment.
     * @return The date incremented by a week.
     * @throws ParseException 
     */
    public String addWeek(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");
        
        Calendar c = Calendar.getInstance();
        c.setTime(dateFormat.parse(date));
        c.add(Calendar.DATE,7);
        
        return dateFormat.format(c.getTime());
    }
    
    /**
     * Returns true if the comparation date is included between the "start" date and the "end" date.
     * 
     * @param start The start date
     * @param end The end date
     * @param comp The date to check
     * @return true if the date is between the start and the end of the period
     * @throws ParseException 
     */
    public boolean compDays(String start,String end,String comp) throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat("d/M/yyyy");
        Date s = format.parse(start);
        Date e = format.parse(end);
        Date c = format.parse(comp);
        
        return c.compareTo(s) >= 0 && c.compareTo(e) <= 0;
    }
    
    /**
     * Return only the month or the year part of the date.
     * <p>
     * The return depends on the "year" parameter.
     * 
     * @param date The date to split.
     * @param year When true returns only the year portion of the date.
     * @return The divided date.
     */
    public String splitMonthOrYear(String date,boolean year){
        String[] split = date.split("/");
        
        if(year)
            return split[2];
        return split[1].concat("/").concat(split[2]);
    }
    
    /**
     * Checks if the date String matches the "regex" pattern.
     * <p>
     * Throws an exception if the pattern isn't matched.
     * 
     * @param match The String to check.
     * @throws WrongDatePatternException 
     */
    public void monthCheck(CharSequence match) throws WrongDatePatternException{
        String regex = "^([1-9]|1[012])/((19|2[0-9])[0-9]{2})$";
        
        if(!Pattern.matches(regex,match))
            throw new WrongDatePatternException("Data errata");
    }
    
    /**
     * Checks if the year is valid.
     * <p>
     * Throws an exception if the year isn't between 1900 and 2099.
     * 
     * @param year The year to check.
     * @throws WrongDatePatternException 
     */
    public void yearCheck(String year) throws WrongDatePatternException{
        int y = Integer.parseInt(year);
        
        if(y < 1900 || y > 2099)
            throw new WrongDatePatternException("Data errata");
    }
    
    /**
     * Returns the new list filtered by a time period.
     * 
     * @return the new list.
     */
    public static ArrayList<MainValues> getFilteredList(){
        return newList;
    }
    
    /**
     * Returns true if the date filter is engaged.
     * 
     * @return true if the filter is engaged.
     */
    public static boolean filterCheck(){
        return dateFilterCheck;
    }
    
    /**
     * Sets the filter's boolean flag.
     * 
     * @param b The flag to set
     */
    public static void setFilterCheck(boolean b){
        dateFilterCheck = b;
    }
}