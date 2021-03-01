package parameters.submodules.manage.features.approverManagement.getCecoDetails;

import java.sql.SQLException;
import java.util.Random;

import database.submodules.manage.ManageDBHelper;
import database.submodules.manage.features.approverManagement.CecoDBHelper;
import database.submodules.manage.features.approverManagement.CrossApproverDBHelper;
import dto.submodules.manage.approverManagement.getCecos.ViewCecoApproverList;
import dto.submodules.manage.approverManagement.postApprover.ApproversResponseDTO;
import parameters.submodules.manage.features.ApproverManagementParameters;
import parameters.submodules.manage.features.approverManagement.putCecoRequiredAllLevels.CecoRequiredAllLevelsParameters;
import properties.manage.ManageProperties;

/**
 * @author german.massello
 *
 */
public class GetCecoDetailsParameters extends ApproverManagementParameters {
	
	/**
	 * Default constructor.
	 * @throws SQLException
	 */
	public GetCecoDetailsParameters() throws SQLException {
		this.cecoNumber=new ManageDBHelper().getRandomCeco();
	}

	/**
	 * This constructor use PostApproverResponseDTO in order to construct the object.
	 * @param approvers
	 */
	public GetCecoDetailsParameters(ApproversResponseDTO approvers) throws SQLException {
		this.cecoNumber=approvers.getContent().getCecoNumber();
	}
	
	/**
	 * This constructor use CecoRequiredAllLevelsParameters in order to construct the object.
	 * @param parameters
	 */
	public GetCecoDetailsParameters(CecoRequiredAllLevelsParameters parameters) throws SQLException {
		this.cecoNumber=parameters.getCecoNumber();
	}
	
	/**
	 * This constructor use ViewCecoApproverList in order to construct the object.
	 * @param ceco
	 */
	public GetCecoDetailsParameters(ViewCecoApproverList ceco) throws SQLException {
		this.cecoNumber=ceco.getCecoNumber();
	}
	
	/**
	 * This constructor use CrossApprover in order to construct the object.
	 * @param crossApprover
	 * @throws SQLException 
	 */
	public GetCecoDetailsParameters(CrossApprover crossApprover) throws SQLException {
		this.username = new CecoDBHelper().getRandomGloberByRol(ManageProperties.rolOptions[new Random().nextInt(ManageProperties.rolOptions.length)]);
		this.crossApproverId = new CrossApproverDBHelper().getExistingCrossApproverId();
		this.crossApprover = new CrossApproverDBHelper().getGloberUsername(this.crossApproverId);
		this.cecoNumber = new CecoDBHelper().getExistingCecoNumber();
	}
	
	/**
	 * This constructor use a cecoNumber in order to construct the object.
	 * @throws SQLException
	 */
	public GetCecoDetailsParameters(String cecoNumber) throws SQLException {
		this.cecoNumber=cecoNumber;
	}
	
}
