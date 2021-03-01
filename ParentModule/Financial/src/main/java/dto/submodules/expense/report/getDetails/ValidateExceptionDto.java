
package dto.submodules.expense.report.getDetails;


/**
 * @author german.massello
 *
 */
public class ValidateExceptionDto {

    private String skipException;
    private String maxAmount;

    public String getSkipException() {
        return skipException;
    }

    public void setSkipException(String skipException) {
        this.skipException = skipException;
    }

    public String getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(String maxAmount) {
        this.maxAmount = maxAmount;
    }

}
