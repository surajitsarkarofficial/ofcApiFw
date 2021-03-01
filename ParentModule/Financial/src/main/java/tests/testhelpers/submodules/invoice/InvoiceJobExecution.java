	package tests.testhelpers.submodules.invoice;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;
import com.google.gson.Gson;

import dto.batch.JobObject;
import parameters.submodules.invoice.InvoiceParameter;
import properties.FinancialProperties;
import properties.invoice.InvoiceProperties;
import utils.batch.BatchConnection;
import utils.batch.BatchExecution;
import utils.batch.UrlRequestManager;


/**
 * @author german.massello
 *
 */
public class InvoiceJobExecution {

	/**
	 * Invoice job execution
	 * @param parameters
	 * @throws IOException
	 */
	public static void invoiceJobExecution(InvoiceParameter parameters) throws IOException {
		String baseBatchUrl = "https://"+FinancialProperties.batchServer+"/glow-batch-job/jobs/";
		String batchUrl = baseBatchUrl+InvoiceProperties.batchJobName;
		HttpURLConnection con = BatchConnection.getBatchHttpsConnection(batchUrl, "POST");
		Reporter.log("Respose code: " + con.getResponseCode(),true);
		Assert.assertEquals(con.getResponseCode(), 200,"this batch execution has failed: "+batchUrl);
		JSONObject json = UrlRequestManager.getJsonObject(con,"jobExecution");
		Gson gson = new Gson();
		JobObject job = gson.fromJson(json.toString(), JobObject.class);
		Reporter.log(job.getName(),true);
		batchUrl=baseBatchUrl+"executions/"+job.getId()+".json";
		BatchExecution.getJobstatus(batchUrl, InvoiceProperties.vimInvoiceJobTimeOut);
	}
	
}
