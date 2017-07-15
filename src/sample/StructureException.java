//TODO: ADD THE NEEDED COMMENT, AND VERIFY NO UNUSED IMPORTS
package sample;

/**
 * The StructureException class will provide the needed data for the structural db exception
 */
public class StructureException extends DBException {

    private static final String title = "WRONG QUERY STRUCTURE";

    /**
     * The constructor for the StructureException class
     * @param message the message of the reported exception
     * @param stack the stack trace of the exception
     */
    public StructureException(String message, StackTraceElement[] stack) {
        super(message, stack);
    }

    /**
     * getTitle function will be used to retrieve the title of the exception
     * @return the title of the exception
     */
    public String getTitle() {
        return title;
    }
}
