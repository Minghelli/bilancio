package panels;

import java.util.regex.Pattern;
import javax.swing.*;
import exceptions.WrongDatePatternException;
import java.text.*;

/**
 * Custom JPanel instance.
 * <p>
 * This panel creates a JPanel instance to be used by other subclasses for a management of the list's voices.
 * 
 * @author Minghe
 * @version 1.1
 */
public class CustomPanel extends JPanel{
    private JLabel valueLabel,dateLabel,descLabel;
    private JTextField valueField,dateField,descField;
    
    /**
     * Constructs a new JPanel instance adding JLabels and JTextFiellds.
     */
    public CustomPanel(){
        super();
        
        valueLabel = new JLabel("Value:");
        dateLabel = new JLabel("Date:");
        descLabel = new JLabel("Desc:");
        
        valueField = new JTextField(20);
        dateField = new JTextField(20);
        descField = new JTextField(20);
        
        add(valueLabel); add(valueField); add(descLabel);
        add(descField); add(dateLabel); add(dateField); 
    }
    
    /**
     * Check if any date string matches the 'regex' pattern.
     * <p>
     * Throws an exception if the date doesn't match the pattern.
     * 
     * @param match the char sequence representing the date.
     * 
     * @throws WrongDatePatternException 
     * @deprecated Replaced by isValid(String).
     */
    @Deprecated
    public static void dateCheck(String match) throws WrongDatePatternException{
        String regex = "^([1-9]|[12][0-9]|3[01])/([1-9]|1[012])/((19|2[0-9])[0-9]{2})$";
        
        if(!Pattern.matches(regex,match))
            throw new WrongDatePatternException("Data errata");
    }
    
    /**
     * Check if any date string is a valid date.
     * <p>
     * Throws an exception if the date doesn't match the pattern.
     * 
     * @param date the char sequence representing the date.
     * 
     * @throws WrongDatePatternException 
     */
    public static void isValid(String date) throws WrongDatePatternException{
        SimpleDateFormat format = new SimpleDateFormat("d/M/yyyy");
        format.setLenient(false);
        
        try {
            format.parse(date);
        } catch (ParseException ex) {
            throw new WrongDatePatternException("Data non valida");
        }
    }
    /**
     * Returns the value's text field.
     * 
     * @return the value field
     */
    public JTextField getValueField(){
        return valueField;
    }
    
    /**
     * Returns the date's text field.
     * 
     * @return the date field
     */
    public JTextField getDateField(){
        return dateField;
    }
    
    /**
     * Returns the description's text field.
     * 
     * @return the description field
     */
    public JTextField getDescField(){
        return descField;
    }
}
