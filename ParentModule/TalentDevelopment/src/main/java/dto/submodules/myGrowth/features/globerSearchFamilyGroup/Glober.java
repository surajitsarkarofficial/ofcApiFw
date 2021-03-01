package dto.submodules.myGrowth.features.globerSearchFamilyGroup;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author christian.chacon
 */
public class Glober {

    @SerializedName("googleId")
    @Expose
    private Object googleId;
    @SerializedName("givenName")
    @Expose
    private Object givenName;
    @SerializedName("gplusLink")
    @Expose
    private Object gplusLink;
    @SerializedName("hd")
    @Expose
    private Object hd;
    @SerializedName("internal")
    @Expose
    private Object internal;
    @SerializedName("picture")
    @Expose
    private Object picture;
    @SerializedName("phone")
    @Expose
    private Object phone;
    @SerializedName("globerId")
    @Expose
    private String globerId;
    @SerializedName("globerName")
    @Expose
    private List<String> globerName = null;
    @SerializedName("seniority")
    @Expose
    private String seniority;
    @SerializedName("position")
    @Expose
    private String position;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("entryDate")
    @Expose
    private String entryDate;
    @SerializedName("type")
    @Expose
    private Object type;
    @SerializedName("globalId")
    @Expose
    private Integer globalId;
    @SerializedName("familyGroup")
    @Expose
    private String familyGroup;

    public Object getGoogleId() {
        return googleId;
    }

    public void setGoogleId(Object googleId) {
        this.googleId = googleId;
    }

    public Object getGivenName() {
        return givenName;
    }

    public void setGivenName(Object givenName) {
        this.givenName = givenName;
    }

    public Object getGplusLink() {
        return gplusLink;
    }

    public void setGplusLink(Object gplusLink) {
        this.gplusLink = gplusLink;
    }

    public Object getHd() {
        return hd;
    }

    public void setHd(Object hd) {
        this.hd = hd;
    }

    public Object getInternal() {
        return internal;
    }

    public void setInternal(Object internal) {
        this.internal = internal;
    }

    public Object getPicture() {
        return picture;
    }

    public void setPicture(Object picture) {
        this.picture = picture;
    }

    public Object getPhone() {
        return phone;
    }

    public void setPhone(Object phone) {
        this.phone = phone;
    }

    public String getGloberId() {
        return globerId;
    }

    public void setGloberId(String globerId) {
        this.globerId = globerId;
    }

    public List<String> getGloberName() {
        return globerName;
    }

    public void setGloberName(List<String> globerName) {
        this.globerName = globerName;
    }

    public String getSeniority() {
        return seniority;
    }

    public void setSeniority(String seniority) {
        this.seniority = seniority;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }

    public Integer getGlobalId() {
        return globalId;
    }

    public void setGlobalId(Integer globalId) {
        this.globalId = globalId;
    }

    public String getFamilyGroup() {
        return familyGroup;
    }

    public void setFamilyGroup(String familyGroup) {
        this.familyGroup = familyGroup;
    }

}
