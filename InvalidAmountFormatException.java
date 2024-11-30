/**
 * Custom exception class for handling invalid monetary amount formats in banking transactions.
 * This exception is thrown when an amount is provided in an incorrect format or is invalid
 * (e.g., negative amounts, incorrect decimal places, non-numeric input).
 */

public class InvalidAmountFormatException extends Exception {
    /**
     * Constructs a new InvalidAmountFormatException with the specified error message.
     * 
     * @param message The detailed error message explaining why the amount format is invalid
     */
    
    public InvalidAmountFormatException(String message) {
        super(message);
    }
}