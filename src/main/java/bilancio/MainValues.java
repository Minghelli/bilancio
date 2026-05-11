package bilancio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * This class represents the main objects of the ArrayList.
 * @author Minghe
 */
public class MainValues {
    private double value;
    private String date;
    private String desc;
    
    /**
     * Empty default contructor.
     * <p>
     * Constructs a new empty object with today's date setted by default.
     */
    public MainValues(){
        this.value = 0.0;
        
        LocalDate d = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("d/M/yyyy");
        
        this.date = d.format(format);
        this.desc = "";
    }
    
    /**
     * Parametric constructor.
     * <p>
     * Constructs a new object with the specified parameters.
     * 
     * @param value The numeric value.
     * @param date The date.
     * @param desc A short desctiption.
     */
    public MainValues(double value,String date,String desc) {
        this.value = value;
        this.date = date;
        this.desc = desc;
    }
    
    /**
     * Array constructor.
     * <p>
     * Constructs a new object reading the parameters from the line String array.
     * 
     * @param line the String array containing the parameters.
     */
    public MainValues(String line[]) {
        this.value = Double.parseDouble(line[0]);
        this.date = line[1];
        this.desc = line[2];
    }

    /**
     * Retruns the object's numeric value.
     * 
     * @return the value
     */
    public double getValue() {
        return value;
    }

    /**
     * Sets the object's numeric value.
     * 
     * @param value the value to set.
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * Retruns the object's description.
     * 
     * @return the description
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Sets the object's description.
     * 
     * @param desc the description to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    /**
     * Returns the object's date.
     * 
     * @return the date
     */
    public String getDate(){
        return date;
    }
    
    /**
     * Sets the object's date
     * 
     * @param date the date to set
     */
    public void setDate(String date){
        this.date = date;
    }
    
    /**
     * Retruns a String containing the values separated by a comma.
     * 
     * @return The String containing the values.
     */
    public String toStringCSV(){
        return String.valueOf(this.value) + "," + this.date + "," + this.desc;
    }
    
    @Override
    public String toString(){
        return String.valueOf(this.value) + " " + this.date + " " + this.desc;
    }
}
