package tests.testhelpers.submodules.invoice;

import java.sql.SQLException;

import parameters.submodules.invoice.InvoiceParameter;
import utils.Utilities;
import utils.xml.XMLHelper;

/**
 * @author german.massello
 *
 */
public class CreateXML {

	/**
	 * Create a new xml file.
	 * @param parameters
	 * @return FileParameters
	 * @throws SQLException 
	 */
	public static InvoiceParameter createXML(InvoiceParameter parameters) throws SQLException {
		parameters.setFileName("VIM_"+parameters.getInvoice().getVimId()+Utilities.getFutureDate("yyyyMMddhhmmss", 0)+".xml");
		String sourceXml = parameters.getFileOriginPath()+parameters.getFileOriginName(); 
		String destinationXml = parameters.getFileOriginPath()+parameters.getFileName();
		XMLHelper.cloneXML(sourceXml, destinationXml);
		return parameters;
	}

}
