package tests.testhelpers.submodules.invoice;

import java.io.IOException;
import java.sql.SQLException;
import org.jdom2.JDOMException;

import dto.submodules.invoice.Invoice;
import parameters.submodules.invoice.InvoiceParameter;
import utils.xml.XMLHelper;

/**
 * @author german.massello
 *
 */
public class CompleteXMLFile  {

	/**
	 * Complete the xml file.
	 * @param parameters
	 * @throws JDOMException
	 * @throws IOException
	 * @throws SQLException
	 */
	public static void completeXMLFile(InvoiceParameter parameters) throws JDOMException, IOException, SQLException {
		Invoice invoice = parameters.getInvoice();
		XMLHelper.updateXML("DOCUMENT", parameters.getFileOriginPath(), parameters.getFileName(), "SUBMITTER", invoice.getSubmitter()+"@globant.com");
		XMLHelper.updateXML("DOCUMENT", parameters.getFileOriginPath(), parameters.getFileName(), "VIMID", invoice.getVimId());
		XMLHelper.updateXML("DOCUMENT", parameters.getFileOriginPath(), parameters.getFileName(), "SOCIETY", invoice.getSociety());
		XMLHelper.updateXML("DOCUMENT", parameters.getFileOriginPath(), parameters.getFileName(), "VENDOR", invoice.getVendor());
		XMLHelper.updateXML("DOCUMENT", parameters.getFileOriginPath(), parameters.getFileName(), "AMOUNT", invoice.getAmount());
		XMLHelper.updateXML("DOCUMENT", parameters.getFileOriginPath(), parameters.getFileName(), "DESCRIPTION", invoice.getDescription());
		XMLHelper.updateXML("DOCUMENT", parameters.getFileOriginPath(), parameters.getFileName(), "CURRENCY", invoice.getCurrency());
		XMLHelper.updateXML("DOCUMENT", parameters.getFileOriginPath(), parameters.getFileName(), "URL_IMG", invoice.getUrlImg());
		XMLHelper.updateXML("DOCUMENT", parameters.getFileOriginPath(), parameters.getFileName(), "RECEPTION_DATE", invoice.getReceptionDate());
		XMLHelper.updateXML("DOCUMENT", parameters.getFileOriginPath(), parameters.getFileName(), "RATE_CONVERTION", invoice.getRateConvertion());
		XMLHelper.updateXML("DOCUMENT", parameters.getFileOriginPath(), parameters.getFileName(), "INVOICE_NUMBER", invoice.getInvoiceNumber());
		XMLHelper.updateXML("DOCUMENT", parameters.getFileOriginPath(), parameters.getFileName(), "LOCALAMOUNT", invoice.getLocalAmount());
	}

}
