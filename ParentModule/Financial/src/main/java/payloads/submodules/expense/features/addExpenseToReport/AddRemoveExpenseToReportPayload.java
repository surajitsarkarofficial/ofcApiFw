
package payloads.submodules.expense.features.addExpenseToReport;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author german.massello
 *
 */
public class AddRemoveExpenseToReportPayload {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("updateAction")
    @Expose
    private String updateAction;
    @SerializedName("expenses")
    @Expose
    private List<Expense> expenses = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AddRemoveExpenseToReportPayload() {
    }

    /**
     * This construct will create a payload in order to add a expense to a report.
     * @param updateAction
     * @param id
     * @param expenses
     */
    public AddRemoveExpenseToReportPayload(String id, String updateAction, List<Expense> expenses) {
        this.id = id;
        this.updateAction = updateAction;
        this.expenses = expenses;
    }

    /**
     * This construct will create a payload in order to delete a benefit from a report.
     * @param parameter
     */
    public AddRemoveExpenseToReportPayload(RemoveBenefit parameter) {
        this.id = parameter.getReportId();
        this.updateAction = parameter.getUpdateAction();
        this.expenses = parameter.getExpenses();
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AddRemoveExpenseToReportPayload withId(String id) {
        this.id = id;
        return this;
    }

    public String getUpdateAction() {
        return updateAction;
    }

    public void setUpdateAction(String updateAction) {
        this.updateAction = updateAction;
    }

    public AddRemoveExpenseToReportPayload withUpdateAction(String updateAction) {
        this.updateAction = updateAction;
        return this;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public AddRemoveExpenseToReportPayload withExpenses(List<Expense> expenses) {
        this.expenses = expenses;
        return this;
    }

}
