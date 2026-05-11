package exceptions;

/**
 * Thrown to indicate that the selected file doesn't exist.
 * 
 * @author Minghe
 * @version 1.0
 */
public class WrongFileNameException extends Exception {
    
    public WrongFileNameException(String message) {
        super(message);
    }
    
}
