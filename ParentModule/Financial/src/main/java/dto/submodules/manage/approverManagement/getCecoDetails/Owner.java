
package dto.submodules.manage.approverManagement.getCecoDetails;


/**
 * @author german.massello
 *
 */
public class Owner {

    private String globerId;
    private String globerName;
    private String username;
    private String email;
    private Object urlAvatar;

    public String getGloberId() {
        return globerId;
    }

    public void setGloberId(String globerId) {
        this.globerId = globerId;
    }

    public String getGloberName() {
        return globerName;
    }

    public void setGloberName(String globerName) {
        this.globerName = globerName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getUrlAvatar() {
        return urlAvatar;
    }

    public void setUrlAvatar(Object urlAvatar) {
        this.urlAvatar = urlAvatar;
    }

}
