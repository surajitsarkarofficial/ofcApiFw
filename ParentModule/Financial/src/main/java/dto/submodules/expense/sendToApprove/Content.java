
package dto.submodules.expense.sendToApprove;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Content {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("error")
    @Expose
    private String error;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Content() {
    }

    /**
     * 
     * @param id
     * @param error
     */
    public Content(String id, String error) {
        super();
        this.id = id;
        this.error = error;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Content withId(String id) {
        this.id = id;
        return this;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Content withError(String error) {
        this.error = error;
        return this;
    }

}
