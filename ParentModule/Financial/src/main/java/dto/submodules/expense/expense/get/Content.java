
package dto.submodules.expense.expense.get;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Content {

    @SerializedName("totalItems")
    @Expose
    private Integer totalItems;
    @SerializedName("expenseList")
    @Expose
    private List<ExpenseList> expenseList = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Content() {
    }

    /**
     * 
     * @param totalItems
     * @param expenseList
     */
    public Content(Integer totalItems, List<ExpenseList> expenseList) {
        super();
        this.totalItems = totalItems;
        this.expenseList = expenseList;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public Content withTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
        return this;
    }

    public List<ExpenseList> getExpenseList() {
        return expenseList;
    }

    public void setExpenseList(List<ExpenseList> expenseList) {
        this.expenseList = expenseList;
    }

    public Content withExpenseList(List<ExpenseList> expenseList) {
        this.expenseList = expenseList;
        return this;
    }

}
