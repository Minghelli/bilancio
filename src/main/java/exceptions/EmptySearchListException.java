package exceptions;

/**
 * Thrown to indicate that the expected returning list (or array or vector ecc...) is empty.
 * 
 * @author Minghe
 * @version 1.0
 */
public class EmptySearchListException extends RuntimeException {

    public EmptySearchListException(String message) {
        super(message);
    }
}
