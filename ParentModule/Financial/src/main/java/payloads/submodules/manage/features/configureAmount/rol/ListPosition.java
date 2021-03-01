
package payloads.submodules.manage.features.configureAmount.rol;

import dto.submodules.manage.configureAmount.getPosition.Content;

/**
 * @author german.massello
 *
 */
public class ListPosition {

    private String id;
    private String position;
    
    /**
     * Default constructor.
     * @param positionParam
     */
    public ListPosition(Content positionParam) {
    	this.id=positionParam.getId();
    	this.position=positionParam.getPosition();
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

}
