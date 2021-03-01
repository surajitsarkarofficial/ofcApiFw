package parameters.submodules.purchaseOrder;

import java.sql.SQLException;

import database.PurchasesDBHelper;
import parameters.submodules.PurchasesParameters;

/**
 * @author german.massello
 *
 */
public class PurchaseOrderParameters extends PurchasesParameters{

	/**
	 * Default constructor.
	 * @throws SQLException 
	 */
	public PurchaseOrderParameters() throws SQLException {
		super();
		this.status="PURCHASED";
		this.isGlobalView="true";
		this.userName=new PurchasesDBHelper().getRandomGloberByRol("Facilities");
	}
	
}
