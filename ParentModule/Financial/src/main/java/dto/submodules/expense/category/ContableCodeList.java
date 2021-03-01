
package dto.submodules.expense.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContableCodeList {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("rebillableCode")
    @Expose
    private String rebillableCode;
    @SerializedName("requireExpenseDays")
    @Expose
    private String requireExpenseDays;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ContableCodeList() {
    }

    /**
     * 
     * @param code
     * @param requireExpenseDays
     * @param name
     * @param active
     * @param id
     * @param rebillableCode
     */
    public ContableCodeList(String id, String code, String name, String active, String rebillableCode, String requireExpenseDays) {
        super();
        this.id = id;
        this.code = code;
        this.name = name;
        this.active = active;
        this.rebillableCode = rebillableCode;
        this.requireExpenseDays = requireExpenseDays;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getRebillableCode() {
        return rebillableCode;
    }

    public void setRebillableCode(String rebillableCode) {
        this.rebillableCode = rebillableCode;
    }

    public String getRequireExpenseDays() {
        return requireExpenseDays;
    }

    public void setRequireExpenseDays(String requireExpenseDays) {
        this.requireExpenseDays = requireExpenseDays;
    }

}
