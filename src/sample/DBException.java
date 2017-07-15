//TODO: ADD THE NEEDED COMMENT, AND VERIFY NO UNUSED IMPORTS
package sample;

/**
 * The DBException class will provide the needed methods for all types of DB exceptions
 */
public abstract class DBException extends Exception {


    /**
     * The constructor for the DBException class
     * @param message the message of the reported exception
     * @param stack the stack trace of the exception
     */
    public DBException(String message, StackTraceElement[] stack) {
        super(message);
        this.setStackTrace(stack);
    }

    /**
     * getTitle function will be used to retrieve the title of the exception
     * @return the title of the exception
     */
    abstract String getTitle();
}
