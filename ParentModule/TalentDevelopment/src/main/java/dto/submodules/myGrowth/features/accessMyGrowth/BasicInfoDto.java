package dto.submodules.myGrowth.features.accessMyGrowth;

import java.util.List;

/**
 * @author christian.chacon
 */
public class BasicInfoDto {

    private Object googleId;
    private String givenName;
    private Object gplusLink;
    private String hd;
    private Object internal;
    private Object picture;
    private String phone;
    private Integer globerId;
    private List<String> globerName = null;
    private String position;
    private String seniority;
    private String location;
    private String username;
    private String gender;
    private String email;
    private List<Role> roles = null;
    private String entryDate;
    private Integer globalId;
    private String familyGroup;
    private Object myGrowth;

    public Object getGoogleId() {
        return googleId;
    }

    public void setGoogleId(Object googleId) {
        this.googleId = googleId;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public Object getGplusLink() {
        return gplusLink;
    }

    public void setGplusLink(Object gplusLink) {
        this.gplusLink = gplusLink;
    }

    public String getHd() {
        return hd;
    }

    public void setHd(String hd) {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getGloberId() {
        return globerId;
    }

    public void setGloberId(Integer globerId) {
        this.globerId = globerId;
    }

    public List<String> getGloberName() {
        return globerName;
    }

    public void setGloberName(List<String> globerName) {
        this.globerName = globerName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSeniority() {
        return seniority;
    }

    public void setSeniority(String seniority) {
        this.seniority = seniority;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
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

    public Object getMyGrowth() {
        return myGrowth;
    }

    public void setMyGrowth(Object myGrowth) {
        this.myGrowth = myGrowth;
    }

}
