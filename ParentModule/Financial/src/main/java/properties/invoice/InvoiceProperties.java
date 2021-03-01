package properties.invoice;

import properties.FinancialProperties;

/**
 * @author german.massello
 *
 */
public class InvoiceProperties extends FinancialProperties {
	public static String invoiceOriginPath = "../Financial/src/main/resources/files/invoice/";
	public static String invoiceDestinationPath = " /opt/globant/glow-vim-prod/glow-inbox/ ";
	public static String invoiceTemplateName = "VIM_VIM_ID_YYYYMMDDhhmmss.xml";
	public static String invoiceImageUrl = "http://invoiceImageDummyUrl";
	public static String batchJobName = "vimInvoiceJob.json";
	public static int vimInvoiceJobTimeOut = 80000;
}
