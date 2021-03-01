
package dto.submodules.expense.report.post;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
    private String originName;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("currencyId")
    @Expose
    private String currencyId;
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
    private String approverId;
    @SerializedName("tickeableEntityType")
    @Expose
    private String tickeableEntityType;
    @SerializedName("requesterName")
    @Expose
    private String requesterName;
    @SerializedName("approverName")
    @Expose
    private String approverName;
    @SerializedName("updateAction")
    @Expose
    private String updateAction;
    @SerializedName("currencyCode")
    @Expose
    private String currencyCode;
    @SerializedName("currencyDescription")
    @Expose
    private String currencyDescription;
    @SerializedName("statusComments")
    @Expose
    private String statusComments;
    @Expose
    private String notificationNumber;
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
    private String firstLevelSafe;
    @SerializedName("canApprove")
    @Expose
    private String canApprove;
    @SerializedName("validateExceptionDto")
    @Expose
    private String validateExceptionDto;
    @SerializedName("usdAmount")
    @Expose
    private String usdAmount;
    private String clientName;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Content() {
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
    public Content(String admitsBillableExpenses, String contractType, String id, String title, String originId, String originName, String amount, String currencyId, String creationDate, String lastModification, String requesterId, String status, String approverId, String tickeableEntityType, String requesterName, String approverName, String updateAction, String currencyCode, String currencyDescription, String statusComments, List<String> expenses, String notificationNumber, String position, String seniority, String expensesCount, String expenseReportOrigin, String accountBankCompleted, String firstLevelSafe, String canApprove, String validateExceptionDto, String usdAmount, String clientName) {
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

    public Content withAdmitsBillableExpenses(String admitsBillableExpenses) {
        this.admitsBillableExpenses = admitsBillableExpenses;
        return this;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public Content withContractType(String contractType) {
        this.contractType = contractType;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Content withId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Content withTitle(String title) {
        this.title = title;
        return this;
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public Content withOriginId(String originId) {
        this.originId = originId;
        return this;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public Content withOriginName(String originName) {
        this.originName = originName;
        return this;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Content withAmount(String amount) {
        this.amount = amount;
        return this;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public Content withCurrencyId(String currencyId) {
        this.currencyId = currencyId;
        return this;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public Content withCreationDate(String creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public String getLastModification() {
        return lastModification;
    }

    public void setLastModification(String lastModification) {
        this.lastModification = lastModification;
    }

    public Content withLastModification(String lastModification) {
        this.lastModification = lastModification;
        return this;
    }

    public String getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(String requesterId) {
        this.requesterId = requesterId;
    }

    public Content withRequesterId(String requesterId) {
        this.requesterId = requesterId;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Content withStatus(String status) {
        this.status = status;
        return this;
    }

    public String getApproverId() {
        return approverId;
    }

    public void setApproverId(String approverId) {
        this.approverId = approverId;
    }

    public Content withApproverId(String approverId) {
        this.approverId = approverId;
        return this;
    }

    public String getTickeableEntityType() {
        return tickeableEntityType;
    }

    public void setTickeableEntityType(String tickeableEntityType) {
        this.tickeableEntityType = tickeableEntityType;
    }

    public Content withTickeableEntityType(String tickeableEntityType) {
        this.tickeableEntityType = tickeableEntityType;
        return this;
    }

    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }

    public Content withRequesterName(String requesterName) {
        this.requesterName = requesterName;
        return this;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public Content withApproverName(String approverName) {
        this.approverName = approverName;
        return this;
    }

    public String getUpdateAction() {
        return updateAction;
    }

    public void setUpdateAction(String updateAction) {
        this.updateAction = updateAction;
    }

    public Content withUpdateAction(String updateAction) {
        this.updateAction = updateAction;
        return this;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Content withCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public String getCurrencyDescription() {
        return currencyDescription;
    }

    public void setCurrencyDescription(String currencyDescription) {
        this.currencyDescription = currencyDescription;
    }

    public Content withCurrencyDescription(String currencyDescription) {
        this.currencyDescription = currencyDescription;
        return this;
    }

    public String getStatusComments() {
        return statusComments;
    }

    public void setStatusComments(String statusComments) {
        this.statusComments = statusComments;
    }

    public Content withStatusComments(String statusComments) {
        this.statusComments = statusComments;
        return this;
    }

    public String getNotificationNumber() {
        return notificationNumber;
    }

    public void setNotificationNumber(String notificationNumber) {
        this.notificationNumber = notificationNumber;
    }

    public Content withNotificationNumber(String notificationNumber) {
        this.notificationNumber = notificationNumber;
        return this;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Content withPosition(String position) {
        this.position = position;
        return this;
    }

    public String getSeniority() {
        return seniority;
    }

    public void setSeniority(String seniority) {
        this.seniority = seniority;
    }

    public Content withSeniority(String seniority) {
        this.seniority = seniority;
        return this;
    }

    public String getExpensesCount() {
        return expensesCount;
    }

    public void setExpensesCount(String expensesCount) {
        this.expensesCount = expensesCount;
    }

    public Content withExpensesCount(String expensesCount) {
        this.expensesCount = expensesCount;
        return this;
    }

    public String getExpenseReportOrigin() {
        return expenseReportOrigin;
    }

    public void setExpenseReportOrigin(String expenseReportOrigin) {
        this.expenseReportOrigin = expenseReportOrigin;
    }

    public Content withExpenseReportOrigin(String expenseReportOrigin) {
        this.expenseReportOrigin = expenseReportOrigin;
        return this;
    }

    public String getAccountBankCompleted() {
        return accountBankCompleted;
    }

    public void setAccountBankCompleted(String accountBankCompleted) {
        this.accountBankCompleted = accountBankCompleted;
    }

    public Content withAccountBankCompleted(String accountBankCompleted) {
        this.accountBankCompleted = accountBankCompleted;
        return this;
    }

    public String getFirstLevelSafe() {
        return firstLevelSafe;
    }

    public void setFirstLevelSafe(String firstLevelSafe) {
        this.firstLevelSafe = firstLevelSafe;
    }

    public Content withFirstLevelSafe(String firstLevelSafe) {
        this.firstLevelSafe = firstLevelSafe;
        return this;
    }

    public String getCanApprove() {
        return canApprove;
    }

    public void setCanApprove(String canApprove) {
        this.canApprove = canApprove;
    }

    public Content withCanApprove(String canApprove) {
        this.canApprove = canApprove;
        return this;
    }

    public String getValidateExceptionDto() {
        return validateExceptionDto;
    }

    public void setValidateExceptionDto(String validateExceptionDto) {
        this.validateExceptionDto = validateExceptionDto;
    }

    public Content withValidateExceptionDto(String validateExceptionDto) {
        this.validateExceptionDto = validateExceptionDto;
        return this;
    }

    public String getUsdAmount() {
        return usdAmount;
    }

    public void setUsdAmount(String usdAmount) {
        this.usdAmount = usdAmount;
    }

    public Content withUsdAmount(String usdAmount) {
        this.usdAmount = usdAmount;
        return this;
    }

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

}
