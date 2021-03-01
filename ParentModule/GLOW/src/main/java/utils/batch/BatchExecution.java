package utils.batch;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.google.gson.Gson;

import dto.batch.JobObject;
import utils.UtilsBase;

/**
 * @author german.massello
 *
 */
public class BatchExecution extends UtilsBase {

	/**
	 * Get job status
	 * @param serviceUrl
	 * @param timeOut
	 * @throws JSONException
	 * @throws IOException
	 */
	public static void getJobstatus(String serviceUrl, int timeOut) throws JSONException, IOException {
		SoftAssert soft_assert = new SoftAssert();
		Boolean executionInProgress = true;
		long startTime = System.currentTimeMillis();
		while (executionInProgress&&(System.currentTimeMillis()-startTime)<timeOut) {
			HttpURLConnection con = BatchConnection.getBatchHttpsConnection(serviceUrl, "GET");
			Assert.assertEquals(con.getResponseCode(), 200," serviceUrl"+serviceUrl);
			log("Respose code: " + con.getResponseCode());
			JSONObject json = UrlRequestManager.getJsonObject(con,"jobExecution");
			Gson gson = new Gson();
			JobObject job = gson.fromJson(json.toString(), JobObject.class);
			if(job.getExitCode().equals("COMPLETED")) executionInProgress=false;
			if(job.getExitCode().equals("FAILED")) {
				executionInProgress=false;
				soft_assert.fail("getExitCode: FAILED ");
				}
			log("getStatus:"+job.getStatus());
			log("getExitCode:"+job.getExitCode());
			log("getDuration:"+job.getDuration());
		}
		if((System.currentTimeMillis()-startTime)>timeOut) soft_assert.fail("time out");
		log("Execution time: (milis) " + Long.toString(System.currentTimeMillis()-startTime));
		soft_assert.assertAll("BatchExecution.getJobstatus has failed");
	}

}
