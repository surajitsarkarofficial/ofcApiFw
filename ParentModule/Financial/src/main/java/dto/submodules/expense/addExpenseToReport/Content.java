
package dto.submodules.expense.addExpenseToReport;

import java.util.LinkedList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import parameters.submodules.expense.features.getReports.GetExpenseReportParameters;

/**
 * @author german.massello
 *
 */
public class Content {

    @SerializedName("admitsBillableExpenses")
    @Expose
    private String admitsBillableExpenses;
    @SerializedName("contractType")
    @Expose
    private String contractType;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("originId")
    @Expose
    private String originId;
    @SerializedName("originName")
    @Expose
    private Object originName;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("currencyId")
    @Expose
    private Object currencyId;
    @SerializedName("creationDate")
    @Expose
    private String creationDate;
    @SerializedName("lastModification")
    @Expose
    private String lastModification;
    @SerializedName("requesterId")
    @Expose
    private String requesterId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("approverId")
    @Expose
    private Object approverId;
    @SerializedName("tickeableEntityType")
    @Expose
    private String tickeableEntityType;
    @SerializedName("requesterName")
    @Expose
    private Object requesterName;
    @SerializedName("approverName")
    @Expose
    private String approverName;
    @SerializedName("updateAction")
    @Expose
    private Object updateAction;
    @SerializedName("currencyCode")
    @Expose
    private String currencyCode;
    @SerializedName("currencyDescription")
    @Expose
    private String currencyDescription;
    @SerializedName("statusComments")
    @Expose
    private Object statusComments;
    @SerializedName("expenses")
    @Expose
    private List<Expense> expenses = null;
    @SerializedName("notificationNumber")
    @Expose
    private Object notificationNumber;
    @SerializedName("position")
    @Expose
    private String position;
    @SerializedName("seniority")
    @Expose
    private String seniority;
    @SerializedName("expensesCount")
    @Expose
    private String expensesCount;
    @SerializedName("expenseReportOrigin")
    @Expose
    private String expenseReportOrigin;
    @SerializedName("accountBankCompleted")
    @Expose
    private String accountBankCompleted;
    @SerializedName("firstLevelSafe")
    @Expose
    private Object firstLevelSafe;
    @SerializedName("canApprove")
    @Expose
    private String canApprove;
    @SerializedName("validateExceptionDto")
    @Expose
    private Object validateExceptionDto;
    @SerializedName("usdAmount")
    @Expose
    private Object usdAmount;
    private String clientName;
    private String provider;

    /**
     * This is the default constructor.
     */
    public Content() {
    	List<Expense> expenses = new LinkedList <Expense>();
    	Expense firstBenefit = new Expense();
    	Expense secondBenefit = new Expense();
    	expenses.add(firstBenefit);
    	expenses.add(secondBenefit);
    	this.expenses=expenses;
    }
    
    /**
     * This constructor will initialize the List<Expense>
     * @param parameter
     */
    public Content(FromBenefitToExpense parameter) {
    	this();
    }

    /**
     * This constructor will initialize the List<Expense> and set the report id.
     * @param parameter
     */
    public Content(GetExpenseReportParameters parameter) {
    	this();
    	this.setId(parameter.getReportId());
    	this.setTitle(parameter.getTitle());
    }
   
    /**
     * 
     * @param usdAmount
     * @param contractType
     * @param title
     * @param approverName
     * @param updateAction
     * @param tickeableEntityType
     * @param originId
     * @param expenseReportOrigin
     * @param canApprove
     * @param firstLevelSafe
     * @param requesterName
     * @param accountBankCompleted
     * @param id
     * @param currencyId
     * @param admitsBillableExpenses
     * @param notificationNumber
     * @param amount
     * @param currencyDescription
     * @param requesterId
     * @param approverId
     * @param creationDate
     * @param expensesCount
     * @param statusComments
     * @param lastModification
     * @param validateExceptionDto
     * @param position
     * @param currencyCode
     * @param seniority
     * @param originName
     * @param status
     * @param expenses
     */
    public Content(String admitsBillableExpenses, String contractType, String id, String title, String originId, Object originName, String amount, Object currencyId, String creationDate, String lastModification, String requesterId, String status, Object approverId, String tickeableEntityType, Object requesterName, String approverName, Object updateAction, String currencyCode, String currencyDescription, Object statusComments, List<Expense> expenses, Object notificationNumber, String position, String seniority, String expensesCount, String expenseReportOrigin, String accountBankCompleted, Object firstLevelSafe, String canApprove, Object validateExceptionDto, Object usdAmount, String clientName) {
        super();
        this.admitsBillableExpenses = admitsBillableExpenses;
        this.contractType = contractType;
        this.id = id;
        this.title = title;
        this.originId = originId;
        this.originName = originName;
        this.amount = amount;
        this.currencyId = currencyId;
        this.creationDate = creationDate;
        this.lastModification = lastModification;
        this.requesterId = requesterId;
        this.status = status;
        this.approverId = approverId;
        this.tickeableEntityType = tickeableEntityType;
        this.requesterName = requesterName;
        this.approverName = approverName;
        this.updateAction = updateAction;
        this.currencyCode = currencyCode;
        this.currencyDescription = currencyDescription;
        this.statusComments = statusComments;
        this.expenses = expenses;
        this.notificationNumber = notificationNumber;
        this.position = position;
        this.seniority = seniority;
        this.expensesCount = expensesCount;
        this.expenseReportOrigin = expenseReportOrigin;
        this.accountBankCompleted = accountBankCompleted;
        this.firstLevelSafe = firstLevelSafe;
        this.canApprove = canApprove;
        this.validateExceptionDto = validateExceptionDto;
        this.usdAmount = usdAmount;
        this.clientName = clientName;
    }

    public String getAdmitsBillableExpenses() {
        return admitsBillableExpenses;
    }

    public void setAdmitsBillableExpenses(String admitsBillableExpenses) {
        this.admitsBillableExpenses = admitsBillableExpenses;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public Object getOriginName() {
        return originName;
    }

    public void setOriginName(Object originName) {
        this.originName = originName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Object getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Object currencyId) {
        this.currencyId = currencyId;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getLastModification() {
        return lastModification;
    }

    public void setLastModification(String lastModification) {
        this.lastModification = lastModification;
    }

    public String getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(String requesterId) {
        this.requesterId = requesterId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getApproverId() {
        return approverId;
    }

    public void setApproverId(Object approverId) {
        this.approverId = approverId;
    }

    public String getTickeableEntityType() {
        return tickeableEntityType;
    }

    public void setTickeableEntityType(String tickeableEntityType) {
        this.tickeableEntityType = tickeableEntityType;
    }

    public Object getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(Object requesterName) {
        this.requesterName = requesterName;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public Object getUpdateAction() {
        return updateAction;
    }

    public void setUpdateAction(Object updateAction) {
        this.updateAction = updateAction;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyDescription() {
        return currencyDescription;
    }

    public void setCurrencyDescription(String currencyDescription) {
        this.currencyDescription = currencyDescription;
    }

    public Object getStatusComments() {
        return statusComments;
    }

    public void setStatusComments(Object statusComments) {
        this.statusComments = statusComments;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public Object getNotificationNumber() {
        return notificationNumber;
    }

    public void setNotificationNumber(Object notificationNumber) {
        this.notificationNumber = notificationNumber;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSeniority() {
        return seniority;
    }

    public void setSeniority(String seniority) {
        this.seniority = seniority;
    }

    public String getExpensesCount() {
        return expensesCount;
    }

    public void setExpensesCount(String expensesCount) {
        this.expensesCount = expensesCount;
    }

    public String getExpenseReportOrigin() {
        return expenseReportOrigin;
    }

    public void setExpenseReportOrigin(String expenseReportOrigin) {
        this.expenseReportOrigin = expenseReportOrigin;
    }

    public String getAccountBankCompleted() {
        return accountBankCompleted;
    }

    public void setAccountBankCompleted(String accountBankCompleted) {
        this.accountBankCompleted = accountBankCompleted;
    }

    public Object getFirstLevelSafe() {
        return firstLevelSafe;
    }

    public void setFirstLevelSafe(Object firstLevelSafe) {
        this.firstLevelSafe = firstLevelSafe;
    }

    public String getCanApprove() {
        return canApprove;
    }

    public void setCanApprove(String canApprove) {
        this.canApprove = canApprove;
    }

    public Object getValidateExceptionDto() {
        return validateExceptionDto;
    }

    public void setValidateExceptionDto(Object validateExceptionDto) {
        this.validateExceptionDto = validateExceptionDto;
    }

    public Object getUsdAmount() {
        return usdAmount;
    }

    public void setUsdAmount(Object usdAmount) {
        this.usdAmount = usdAmount;
    }

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

}
