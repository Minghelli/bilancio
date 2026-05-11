package saving;

import bilancio.MainValues;
import exceptions.WrongFileFormatException;
import exceptions.WrongFileNameException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Contains methods for reading and saving procedures.
 * @author Minghe
 */
public interface IOProcedures {
    
    /**
     * Saves the list voices on the choosen file.
     * 
     * 
     * @param list The list that needs to be saved.
     * @param f The file.
     * @throws WrongFileFormatException
     * @throws exceptions.WrongFileNameException
     * @throws IOException
     */
    public abstract void save(ArrayList<MainValues> list,File f) throws WrongFileFormatException,WrongFileNameException,IOException;
    
    /**
     * Returns the list read from the choosen file.
     * 
     * @param f The file 
     * @return The new list
     * @throws IOException 
     */
    public abstract ArrayList<MainValues> read(File f) throws IOException;
    
    /**
     * Returns true if the file format is compatible.
     * 
     * @param f The file to check
     * @return True if the format is correct
     * @throws exceptions.WrongFileNameException
     */
    public abstract boolean checkFileFormat(File f)  throws WrongFileNameException;
}