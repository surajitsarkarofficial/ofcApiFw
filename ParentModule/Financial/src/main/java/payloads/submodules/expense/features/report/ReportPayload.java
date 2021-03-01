
package payloads.submodules.expense.features.report;

import java.sql.SQLException;
import java.util.Random;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import database.GlowDBHelper;
import dto.submodules.expense.addExpenseToReport.ReportWithExpenseDTO;
import utils.Utilities;

public class ReportPayload {

    @SerializedName("originId")
    @Expose
    private String originId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("tickeableEntityType")
    @Expose
    private String tickeableEntityType;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("expenseReportOrigin")
    @Expose
    private String expenseReportOrigin;
    private String id;

	public static String [] titleOptions = {"Lunchs Reports", "Dinners Reports", "Breakfasts Reports", "Bus Trips Reports", "Cab Rides Reports", "Internet Reports", "Cell Phone Subscriptions Reports"
			,"Travels by plane Reports"};
	private static String todayInMs = Utilities.getTodayInMilliseconds();
	
	/**
	 * This constructor will create a payload in order to create a report.
	 */
	public ReportPayload() {
		this.status = "UNKNOWN";
		this.tickeableEntityType = "PROJECT";
		this.expenseReportOrigin = "Glow";
		this.title = titleOptions[new Random().nextInt(titleOptions.length)]+todayInMs;
    }

	/**
	 * This constructor will create a payload in order to edit a report.
	 * @param reportWithExpenseDTO
	 * @throws SQLException 
	 */
	public ReportPayload(ReportWithExpenseDTO reportWithExpenseDTO ) throws SQLException {
		this.tickeableEntityType = "PROJECT";
		this.expenseReportOrigin = "Glow";
		this.status = "DRAFT";
		this.title = reportWithExpenseDTO.getContent().getTitle()+ "Edited";
		this.id = reportWithExpenseDTO.getContent().getId();
		this.originId = new GlowDBHelper().getProjectId();
    }
	
    /**
     * 
     * @param tickeableEntityType
     * @param originId
     * @param expenseReportOrigin
     * @param title
     * @param status
     */
    public ReportPayload(String originId, String status, String tickeableEntityType, String title, String expenseReportOrigin) {
        super();
        this.originId = originId;
        this.status = status;
        this.tickeableEntityType = tickeableEntityType;
        this.title = title;
        this.expenseReportOrigin = expenseReportOrigin;
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public ReportPayload withOriginId(String originId) {
        this.originId = originId;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ReportPayload withStatus(String status) {
        this.status = status;
        return this;
    }

    public String getTickeableEntityType() {
        return tickeableEntityType;
    }

    public void setTickeableEntityType(String tickeableEntityType) {
        this.tickeableEntityType = tickeableEntityType;
    }

    public ReportPayload withTickeableEntityType(String tickeableEntityType) {
        this.tickeableEntityType = tickeableEntityType;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ReportPayload withTitle(String title) {
        this.title = title;
        return this;
    }

    public String getExpenseReportOrigin() {
        return expenseReportOrigin;
    }

    public void setExpenseReportOrigin(String expenseReportOrigin) {
        this.expenseReportOrigin = expenseReportOrigin;
    }

    public ReportPayload withExpenseReportOrigin(String expenseReportOrigin) {
        this.expenseReportOrigin = expenseReportOrigin;
        return this;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
    
}
