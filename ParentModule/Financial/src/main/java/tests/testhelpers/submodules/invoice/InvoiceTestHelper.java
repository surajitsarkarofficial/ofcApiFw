package tests.testhelpers.submodules.invoice;

import parameters.submodules.invoice.InvoiceParameter;
import tests.testhelpers.submodules.manage.ManageTestHelper;
import utils.file.CopyFileHelper;

/**
 * @author german.massello
 *
 */
public class InvoiceTestHelper extends ManageTestHelper {

	public InvoiceTestHelper(String userName) throws Exception {
		super(userName);
	}
	
	/**
	 * Create a new invoice.
	 * @param parameters
	 * @throws Exception 
	 */
	public void createInvoiceFromScratch(InvoiceParameter parameters) throws Exception {
		CreateXML.createXML(parameters);
		CompleteXMLFile.completeXMLFile(parameters);
		CopyFileHelper.fileCopyToServer(parameters);
		InvoiceJobExecution.invoiceJobExecution(parameters);
	}

}
