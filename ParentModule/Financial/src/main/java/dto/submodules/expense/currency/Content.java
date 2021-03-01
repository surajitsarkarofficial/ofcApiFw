
package dto.submodules.expense.currency;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Content {

    @SerializedName("totalItems")
    @Expose
    private Integer totalItems;
    @SerializedName("currencyList")
    @Expose
    private List<CurrencyList> currencyList = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Content() {
    }

    /**
     * 
     * @param totalItems
     * @param currencyList
     */
    public Content(Integer totalItems, List<CurrencyList> currencyList) {
        super();
        this.totalItems = totalItems;
        this.currencyList = currencyList;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public List<CurrencyList> getCurrencyList() {
        return currencyList;
    }

    public void setCurrencyList(List<CurrencyList> currencyList) {
        this.currencyList = currencyList;
    }

}
