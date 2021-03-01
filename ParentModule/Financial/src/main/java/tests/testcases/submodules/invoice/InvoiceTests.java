package tests.testcases.submodules.invoice;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import database.submodules.invoice.InvoiceDBHelper;
import dataproviders.submodules.invoice.InvoiceDataProviders;
import dto.submodules.invoice.Invoice;
import parameters.submodules.invoice.InvoiceParameter;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.invoice.CompleteXMLFile;
import tests.testhelpers.submodules.invoice.CreateXML;
import tests.testhelpers.submodules.invoice.InvoiceJobExecution;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;
import utils.file.CopyFileHelper;

public class InvoiceTests extends ManageBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("InvoiceTests");
	}

	/**
	 * Create an invoice file.
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "invoice", dataProviderClass = InvoiceDataProviders.class, groups = {ExeGroups.NotAvailableInPreProd}
	, invocationCount = 1)
	public void createInvoice(InvoiceParameter parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		CreateXML.createXML(parameters);
		CompleteXMLFile.completeXMLFile(parameters);
		CopyFileHelper.fileCopyToServer(parameters);
		InvoiceJobExecution.invoiceJobExecution(parameters);
		Invoice invoice = parameters.getInvoice();
		Map<String,String> dbInvoice = new InvoiceDBHelper().getInvoice(invoice.getVimId());
		softAssert.assertEquals(dbInvoice.get("submmiter"), invoice.getSubmitter(), "getSubmitter issue");
		softAssert.assertEquals(dbInvoice.get("status"), "Pending action", "getStatus issue");
		softAssert.assertEquals(dbInvoice.get("society"), invoice.getSociety(), "getSociety issue");
		softAssert.assertEquals(dbInvoice.get("invoiceNumber"), invoice.getInvoiceNumber(), "getInvoiceNumber issue");
		softAssert.assertEquals(dbInvoice.get("vendor"), invoice.getVendor(), "getVendor issue");
		softAssert.assertEquals(dbInvoice.get("amount"), invoice.getAmount(), "getAmount issue");
		softAssert.assertEquals(dbInvoice.get("localAmount"), invoice.getLocalAmount(), "getLocalAmount issue");
		softAssert.assertEquals(dbInvoice.get("urlImage"), invoice.getUrlImg(), "getUrlImg issue");
		softAssert.assertEquals(dbInvoice.get("rateConvertion"), invoice.getRateConvertion(), "getRateConvertion issue");
		softAssert.assertEquals(dbInvoice.get("processingStatus"), "active", "getLocalAmount issue");
		softAssert.assertAll(); 
	}

	/**
	 * Create custom invoice
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "invoice", dataProviderClass = InvoiceDataProviders.class, groups = {ExeGroups.NotAvailableInPreProd}
	, invocationCount = 1)
	public void createCustomInvoice(InvoiceParameter parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		Invoice invoice = parameters.getInvoice();
		invoice.setAmount("100").setCurrency("ARS").setSubmitter("rodrigo.delvalle").setRateConvertion("2");
		CreateXML.createXML(parameters);
		CompleteXMLFile.completeXMLFile(parameters);
		CopyFileHelper.fileCopyToServer(parameters);
		InvoiceJobExecution.invoiceJobExecution(parameters);
		Map<String,String> dbInvoice = new InvoiceDBHelper().getInvoice(invoice.getVimId());
		softAssert.assertEquals(dbInvoice.get("submmiter"), invoice.getSubmitter(), "getSubmitter issue");
		softAssert.assertAll(); 
	}

}
