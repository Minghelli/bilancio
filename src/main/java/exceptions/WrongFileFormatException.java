package exceptions;

/**
 * Thrown to indicate that the selected file format is incompatible.
 * 
 * @author Minghe
 * @version 1.0
 */
public class WrongFileFormatException extends Exception {
    
    public WrongFileFormatException(String message) {
        super(message);
    }
    
}
