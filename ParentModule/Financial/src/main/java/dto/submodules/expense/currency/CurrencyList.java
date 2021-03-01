
package dto.submodules.expense.currency;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrencyList {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("common")
    @Expose
    private String common;
    @SerializedName("isoCode")
    @Expose
    private String isoCode;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CurrencyList() {
    }

    /**
     * 
     * @param code
     * @param common
     * @param isoCode
     * @param description
     * @param id
     */
    public CurrencyList(String id, String description, String code, String common, String isoCode) {
        super();
        this.id = id;
        this.description = description;
        this.code = code;
        this.common = common;
        this.isoCode = isoCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCommon() {
        return common;
    }

    public void setCommon(String common) {
        this.common = common;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

}
