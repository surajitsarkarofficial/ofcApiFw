
package dto.submodules.expense.receipt.post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Details {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("module")
    @Expose
    private String module;
    @SerializedName("inputId")
    @Expose
    private Object inputId;
    @SerializedName("destinationPath")
    @Expose
    private String destinationPath;
    @SerializedName("staticResourcePath")
    @Expose
    private String staticResourcePath;
    @SerializedName("metadata")
    @Expose
    private Object metadata;
    @SerializedName("filename")
    @Expose
    private String filename;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Details() {
    }

    /**
     * 
     * @param inputId
     * @param metadata
     * @param filename
     * @param staticResourcePath
     * @param module
     * @param id
     * @param destinationPath
     */
    public Details(String id, String module, Object inputId, String destinationPath, String staticResourcePath, Object metadata, String filename) {
        super();
        this.id = id;
        this.module = module;
        this.inputId = inputId;
        this.destinationPath = destinationPath;
        this.staticResourcePath = staticResourcePath;
        this.metadata = metadata;
        this.filename = filename;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Details withId(String id) {
        this.id = id;
        return this;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Details withModule(String module) {
        this.module = module;
        return this;
    }

    public Object getInputId() {
        return inputId;
    }

    public void setInputId(Object inputId) {
        this.inputId = inputId;
    }

    public Details withInputId(Object inputId) {
        this.inputId = inputId;
        return this;
    }

    public String getDestinationPath() {
        return destinationPath;
    }

    public void setDestinationPath(String destinationPath) {
        this.destinationPath = destinationPath;
    }

    public Details withDestinationPath(String destinationPath) {
        this.destinationPath = destinationPath;
        return this;
    }

    public String getStaticResourcePath() {
        return staticResourcePath;
    }

    public void setStaticResourcePath(String staticResourcePath) {
        this.staticResourcePath = staticResourcePath;
    }

    public Details withStaticResourcePath(String staticResourcePath) {
        this.staticResourcePath = staticResourcePath;
        return this;
    }

    public Object getMetadata() {
        return metadata;
    }

    public void setMetadata(Object metadata) {
        this.metadata = metadata;
    }

    public Details withMetadata(Object metadata) {
        this.metadata = metadata;
        return this;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Details withFilename(String filename) {
        this.filename = filename;
        return this;
    }

}
