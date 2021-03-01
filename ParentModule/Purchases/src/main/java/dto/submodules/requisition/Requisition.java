package dto.submodules.requisition;

import java.util.Map;

import constants.submodules.RequisitionConstants;

/**
 * @author german.massello
 *
 */
public class Requisition implements RequisitionConstants {

	private String id="";
	private String groupId="";
	private String requesterId="";
	
	/**
	 * Default constructor
	 * @param requisition
	 * @param advancedFilters
	 */
	public Requisition(Map<String,String> requisition, String advancedFilters) {
		this.id=requisition.get("reqId");
		switch(advancedFilters) {
		  case ADVANCE_FILTERS_GROUP:
			this.groupId=requisition.get("groupId");
		    break;
		  case ADVANCE_FILTERS_REQUESTER:
			  this.requesterId=requisition.get("requesterId");
			break;  
		  case ADVANCE_FILTERS_FALSE:
			    break;
		  default:
		}		
	}
	
	public Requisition() {
	}
	
	public String getId() {
		return id;
	}
	public Requisition setId(String id) {
		this.id = id;
		return this;
	}
	public String getGroupId() {
		return groupId;
	}
	public Requisition setGroupId(String groupId) {
		this.groupId = groupId;
		return this;
	}

	public String getRequesterId() {
		return requesterId;
	}

	public void setRequesterId(String requesterId) {
		this.requesterId = requesterId;
	}
	
}
