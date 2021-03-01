
package dto.submodules.expense.report.get;

import java.util.List;

/**
 * @author german.massello
 *
 */
public class Content {

    private Integer totalItems;
    private List<ExpensesReportList> expensesReportList = null;

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public List<ExpensesReportList> getExpensesReportList() {
        return expensesReportList;
    }

    public void setExpensesReportList(List<ExpensesReportList> expensesReportList) {
        this.expensesReportList = expensesReportList;
    }

}
