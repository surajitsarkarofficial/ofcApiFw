package parameters.submodules.invoice;

import java.sql.SQLException;

import dto.submodules.invoice.Invoice;
import properties.invoice.InvoiceProperties;
import utils.file.FileParameters;

/**
 * @author german.massello
 *
 */
public class InvoiceParameter extends FileParameters {

	private Invoice invoice;
	private String batchJobName;

	public InvoiceParameter(String enzoServer, String coorporativeUsername, String coorporativePassword,
			String fileOriginPath, String fileOriginName, String fileDestinationServer, String fileDestinationPath,
			String fileDestinationName, String submitterUsername) throws SQLException {
		super(	enzoServer, 
				coorporativeUsername, 
				coorporativePassword, 
				fileOriginPath, 
				fileOriginName, 
				fileDestinationServer,
				fileDestinationPath, 
				fileDestinationName);
		Invoice invoice = new Invoice(submitterUsername);
		this.invoice = invoice;
		this.batchJobName = InvoiceProperties.batchJobName;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public String getBatchJobName() {
		return batchJobName;
	}

	public void setBatchJobName(String batchJobName) {
		this.batchJobName = batchJobName;
	}
	
}
