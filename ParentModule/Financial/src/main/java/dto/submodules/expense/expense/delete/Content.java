
package dto.submodules.expense.expense.delete;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Content {

    @SerializedName("ids")
    @Expose
    private List<Object> ids = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Content() {
    }

    /**
     * 
     * @param ids
     */
    public Content(List<Object> ids) {
        super();
        this.ids = ids;
    }

    public List<Object> getIds() {
        return ids;
    }

    public void setIds(List<Object> ids) {
        this.ids = ids;
    }

    public Content withIds(List<Object> ids) {
        this.ids = ids;
        return this;
    }

}
