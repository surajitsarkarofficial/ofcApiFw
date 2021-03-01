package dto.submodules.myGrowth.features.globerSearchFamilyGroup;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author christian.chacon
 */
public class GloberSearchResponse {

    @SerializedName("offset")
    @Expose
    private Integer offset;
    @SerializedName("rowCount")
    @Expose
    private Integer rowCount;
    @SerializedName("globers")
    @Expose
    private List<Glober> globers = null;

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getRowCount() {
        return rowCount;
    }

    public void setRowCount(Integer rowCount) {
        this.rowCount = rowCount;
    }

    public List<Glober> getGlobers() {
        return globers;
    }

    public void setGlobers(List<Glober> globers) {
        this.globers = globers;
    }
}
