package dto.submodules.myGrowth.features.todayBirthdays;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author christian.chacon
 */
public class GlobersTodayDTO {

    @SerializedName("googleId")
    @Expose
    private Object googleId;
    @SerializedName("givenName")
    @Expose
    private String givenName;
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
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("username")
    @Expose
    private String username;
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
    @SerializedName("entryDate")
    @Expose
    private String entryDate;
    @SerializedName("roles")
    @Expose
    private Object roles;
    @SerializedName("organizationalUnit")
    @Expose
    private String organizationalUnit;
    @SerializedName("studio")
    @Expose
    private Studio studio;
    @SerializedName("champions")
    @Expose
    private Object champions;
    @SerializedName("mentor")
    @Expose
    private Object mentor;
    @SerializedName("leader")
    @Expose
    private Object leader;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("isPodCompanion")
    @Expose
    private Boolean isPodCompanion;
    @SerializedName("projectIds")
    @Expose
    private List<Integer> projectIds = null;
    @SerializedName("podIds")
    @Expose
    private List<Integer> podIds = null;
    @SerializedName("globalId")
    @Expose
    private Integer globalId;
    @SerializedName("familyGroup")
    @Expose
    private String familyGroup;

    @SerializedName("isMentor")
    @Expose
    private Object isMentor;

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public Object getRoles() {
        return roles;
    }

    public void setRoles(Object roles) {
        this.roles = roles;
    }

    public String getOrganizationalUnit() {
        return organizationalUnit;
    }

    public void setOrganizationalUnit(String organizationalUnit) {
        this.organizationalUnit = organizationalUnit;
    }

    public Studio getStudio() {
        return studio;
    }

    public void setStudio(Studio studio) {
        this.studio = studio;
    }

    public Object getChampions() {
        return champions;
    }

    public void setChampions(Object champions) {
        this.champions = champions;
    }

    public Object getMentor() {
        return mentor;
    }

    public void setMentor(Object mentor) {
        this.mentor = mentor;
    }

    public Object getLeader() {
        return leader;
    }

    public void setLeader(Object leader) {
        this.leader = leader;
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

    public Boolean getIsPodCompanion() {
        return isPodCompanion;
    }

    public void setIsPodCompanion(Boolean isPodCompanion) {
        this.isPodCompanion = isPodCompanion;
    }

    public List<Integer> getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(List<Integer> projectIds) {
        this.projectIds = projectIds;
    }

    public List<Integer> getPodIds() {
        return podIds;
    }

    public void setPodIds(List<Integer> podIds) {
        this.podIds = podIds;
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

    public Object getIsMentor() {
        return isMentor;
    }

    public void setIsMentor(Object isMentor) {
        this.isMentor = isMentor;
    }
}
