
package dto.submodules.expense.report.getAmounts;

import java.util.List;

/**
 * @author german.massello
 *
 */
public class Content {

    private Integer totalItems;
    private List<TicketAmountsList> ticketAmountsList = null;

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public List<TicketAmountsList> getTicketAmountsList() {
        return ticketAmountsList;
    }

    public void setTicketAmountsList(List<TicketAmountsList> ticketAmountsList) {
        this.ticketAmountsList = ticketAmountsList;
    }

}
