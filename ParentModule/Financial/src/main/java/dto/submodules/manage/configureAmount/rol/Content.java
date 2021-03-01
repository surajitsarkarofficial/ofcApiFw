
package dto.submodules.manage.configureAmount.rol;

import java.util.List;

/**
 * @author german.massello
 *
 */
public class Content {

    private String id;
    private String name;
    private Object description;
    private String status;
    private String maxAmount;
    private List<ListPosition> listPositions = null;

    
    public Content(String id) {
		this.id = id;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(String maxAmount) {
        this.maxAmount = maxAmount;
    }

    public List<ListPosition> getListPositions() {
        return listPositions;
    }

    public void setListPositions(List<ListPosition> listPositions) {
        this.listPositions = listPositions;
    }

}
