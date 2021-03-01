package dto.myTeam.features;

import dto.myTeam.MyTeamDTO;

/**
 * @author imran.khan
 *
 */

public class UserPreferencesDtoList extends MyTeamDTO {
	private boolean active;
	private String value;
	private String componentKey;

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getComponentKey() {
		return componentKey;
	}

	public void setComponentKey(String componentKey) {
		this.componentKey = componentKey;
	}

}
