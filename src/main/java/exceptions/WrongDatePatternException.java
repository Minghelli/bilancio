package exceptions;

/**
 * Thrown to indicate that the inserted date doesn't match the correct pattern
 * 
 * @author Minghe
 * @version 1.0
 */
public class WrongDatePatternException extends RuntimeException {

    public WrongDatePatternException(String message) {
        super(message);
    }
    
}
