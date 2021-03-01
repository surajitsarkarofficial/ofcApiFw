
package payloads.submodules.manage.features.configureAmount.rol;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import database.submodules.manage.features.ConfigureAmountDBHelper;
import dto.submodules.manage.configureAmount.getPosition.Content;
import dto.submodules.manage.configureAmount.rol.RolResponseDTO;
import utils.Utilities;

/**
 * @author german.massello
 *
 */
public class RolPayLoadHelper {

    private String maxAmount;
    private String name;
    private List<ListPosition> listPositions;
    private String id;
   		
    /**
     * This method will create the payload in order to create a new rol with positions.
     * @param positionParam
     */
    public RolPayLoadHelper(Content positionParam) {
    	String value = "1"+Utilities.getTodayInMilliseconds().substring(7, 13);
    	this.maxAmount=value;
    	this.name="RolName"+value;
    	this.listPositions= new LinkedList<>();
    	ListPosition position = new ListPosition(positionParam);
    	this.listPositions.add(position);
	}

    /**
     * This method will create the payload in order to edit a rol.
     * @param rol
     */
    public RolPayLoadHelper(RolResponseDTO rol) {
    	this.id=rol.getContent().getId();
    	this.maxAmount=String.valueOf(Long.valueOf(rol.getContent().getMaxAmount())+1);
    	this.name=rol.getContent().getName()+" Edited";
    }

    /**
     * This method will create the payload in order to edit a rol with an existing rol name.
     * @param rol
     * @param name
     * @throws SQLException 
     */
    public RolPayLoadHelper(RolResponseDTO rol, SameName name) throws SQLException {
    	this.id=rol.getContent().getId();
    	this.maxAmount=String.valueOf(Long.valueOf(rol.getContent().getMaxAmount())+1);
    	this.name=new ConfigureAmountDBHelper().getExistingRolName(rol.getContent().getName());
    }
 
    /**
     * This method will create the payload in order to edit a rol with an existing rol amount.
     * @param rol
     * @param amount
     * @throws SQLException
     */
    public RolPayLoadHelper(RolResponseDTO rol, SameAmount amount) throws SQLException {
    	this.id=rol.getContent().getId();
    	this.maxAmount=new ConfigureAmountDBHelper().getExistingRolAmount(Long.valueOf(rol.getContent().getMaxAmount()));
    	this.name=rol.getContent().getName();
    }
    
	public String getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(String maxAmount) {
        this.maxAmount = maxAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ListPosition> getListPositions() {
        return listPositions;
    }

    public void setListPositions(List<ListPosition> listPositions) {
        this.listPositions = listPositions;
    }

	public String getId() {
		return id;
	}

}
