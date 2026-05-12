package saving;

import bilancio.MainValues;
import exceptions.WrongFileFormatException;
import exceptions.WrongFileNameException;
import java.util.*;
import java.io.*;

/**
 * Implements saving and reading methods.
 * <p>
 * This class implements methods for managing files in CSV format.
 * 
 * @author Minghe
 * @version 1.1
 */
public class CsvIO implements IOProcedures {
    public CsvIO (){}

    /**
     * Saves the list voices on the chosen file in CSV format.
     * 
     * 
     * @param list The list that needs to be saved.
     * @param f The file.
     * @throws WrongFileFormatException
     * @throws exceptions.WrongFileNameException
     * @throws IOException
     */
    @Override
    public void save(ArrayList<MainValues> list,File f) throws WrongFileFormatException,WrongFileNameException,IOException {
        if(!checkFileFormat(f))
            throw new WrongFileFormatException("");
        
        FileWriter file = new FileWriter(f);
        BufferedWriter writer = new BufferedWriter(file);

        for (MainValues mainValues : list) {
            String buffer = mainValues.toStringCSV();

            writer.write(buffer);
            writer.newLine();
        }

        writer.close();
    }

    /**
     * Returns the list read from the chosen CSV file.
     * 
     * @param f The file 
     * @return The new list
     * @throws IOException 
     */
    @Override
    public ArrayList<MainValues> read(File f) throws IOException{
        ArrayList<MainValues> newList = new ArrayList<>(0);
        
        System.out.println(f);
        
        FileReader file = new FileReader(f);
        Scanner scan = new Scanner(file);

        while(scan.hasNextLine()){
            String s = scan.nextLine();
            System.out.println(s);

            String[] split = s.split(",");
            MainValues values = new MainValues(split);

            newList.add(values);
        }
        scan.close();
        
        return newList;
    }
    
    /**
     * Returns true if the file format is compatible.
     * 
     * @param f The file to check
     * @return True if the format is correct
     * @throws exceptions.WrongFileNameException
     */
    @Override
    public boolean checkFileFormat(File f) throws WrongFileNameException{
        String file = f.toString();
        String[] ext = file.split("\\.");
        
        if(ext.length <= 1){
            throw new WrongFileNameException("");
        }
        return ext[1].equals("csv");
    }
    
}
