
package dto.submodules.expense.category;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Content {

    @SerializedName("totalItems")
    @Expose
    private Integer totalItems;
    @SerializedName("contableCodeList")
    @Expose
    private List<ContableCodeList> contableCodeList = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Content() {
    }

    /**
     * 
     * @param totalItems
     * @param contableCodeList
     */
    public Content(Integer totalItems, List<ContableCodeList> contableCodeList) {
        super();
        this.totalItems = totalItems;
        this.contableCodeList = contableCodeList;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public List<ContableCodeList> getContableCodeList() {
        return contableCodeList;
    }

    public void setContableCodeList(List<ContableCodeList> contableCodeList) {
        this.contableCodeList = contableCodeList;
    }

}
