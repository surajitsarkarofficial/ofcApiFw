package parameters.submodules.manage.features.approverManagement;

import dto.submodules.manage.approverManagement.postCeco.PostCecoResponseDTO;
import parameters.submodules.manage.features.approverManagement.getCecos.NonExistentCecoNumber;

/**
 * @author german.massello
 *
 */
public class DeleteCecoParameters {
	private String cecoNumber;

	
	/**
	 * This constructor will create a object with an existent ceco number.
	 * @param ceco
	 */
	public DeleteCecoParameters(PostCecoResponseDTO ceco ) {
		this.cecoNumber=ceco.getContent().getCecoNumber();
	}
	
	/**
	 * This constructor will create a object with a non existent ceco number.
	 * @param ceco
	 */
	public DeleteCecoParameters(NonExistentCecoNumber ceco ) {
		this.cecoNumber=ceco.getNonExistentCecoNumber();
	}

	public String getCecoNumber() {
		return cecoNumber;
	}

	public void setCecoNumber(String cecoNumber) {
		this.cecoNumber = cecoNumber;
	}
	
}
