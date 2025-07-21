package za.co.titandynamix.exception;

import lombok.Getter;

import java.io.Serial;


/**
 * Exception for business rules
 *
 * @author clivememela
 */
@Getter
public class BusinessRuleException extends Exception {
    /**
     * The Constant serialVersionUID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    public static final String EMPTY = "";

    private final String errorCode;

    /**
     * Throws valid business rule exception
     */
    public BusinessRuleException() {
        super();
        this.errorCode = EMPTY;
    }

    /**
     * Instantiates a new business rule exception.
     *
     * @param errorMsg the error msg
     */
    public BusinessRuleException(final String errorMsg) {
        super(errorMsg);
        this.errorCode = EMPTY;
    }

    /**
     * Instantiates a new business rule exception.
     *
     * @param errorCode the error code
     * @param errorMsg the error msg
     */
    public BusinessRuleException(final String errorCode, final String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
    }

    /**
     * @param cause the cause
     */
    public BusinessRuleException(Throwable cause) {
        super(cause);
        this.errorCode = EMPTY;
    }

    /**
     * Instantiates a new business rule exception.
     *
     * @param errorMsg the error msg
     * @param cause the cause
     */
    public BusinessRuleException(final String errorMsg, final Throwable cause) {
        super(errorMsg, cause);
        this.errorCode = EMPTY;
    }

    /**
     * Instantiates a new business rule exception.
     *
     * @param errorCode the error code
     * @param errorMsg the error msg
     * @param cause the cause
     */
    public BusinessRuleException(final String errorCode, final String errorMsg, final Throwable cause) {
        super(errorMsg, cause);
        this.errorCode = errorCode;
    }

}
