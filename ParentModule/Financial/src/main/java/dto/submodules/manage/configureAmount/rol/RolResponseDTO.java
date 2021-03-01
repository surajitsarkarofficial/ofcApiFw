
package dto.submodules.manage.configureAmount.rol;

import java.sql.SQLException;

import database.submodules.manage.features.ConfigureAmountDBHelper;

/**
 * @author german.massello
 *
 */
public class RolResponseDTO {

    private String status;
    private String message;
    private Content content;

    
    /**
     * This method will return an non existing rol.
     * @param rol
     * @throws SQLException
     */
    public RolResponseDTO(NonExistingRol rol) throws SQLException {
    	this.content= new Content(new ConfigureAmountDBHelper().getAnNonExistingRolId());
	}

	public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

}
