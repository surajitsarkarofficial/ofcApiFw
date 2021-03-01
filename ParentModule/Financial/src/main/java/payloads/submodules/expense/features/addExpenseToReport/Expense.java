
package payloads.submodules.expense.features.addExpenseToReport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author german.massello
 *
 */
public class Expense {

    @SerializedName("id")
    @Expose
    private String id;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Expense() {
    }

    /**
     * 
     * @param id
     */
    public Expense(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Expense withId(String id) {
        this.id = id;
        return this;
    }

}
