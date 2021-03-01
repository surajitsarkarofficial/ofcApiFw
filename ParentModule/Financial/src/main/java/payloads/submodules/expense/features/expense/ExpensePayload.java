package payloads.submodules.expense.features.expense;

import java.util.Random;

import dto.submodules.expense.addExpenseToReport.ReportWithExpenseDTO;
import dto.submodules.expense.receipt.ReceiptForExpense;
import payloads.submodules.expense.ExpensePayLoadHelper;

/**
 * @author german.massello
 *
 */
public class ExpensePayload extends ExpensePayLoadHelper{

    private String id;
    private String title;
    private String amount;
    private String amountUSD;
    private String authorId;
    private String authorCompleteName;
    private String currencyId;
    private String currencyCode;
    private String currencyDescription;
    private String expenseDate;
    private String categoryId;
    private String categoryCode;
    private String categoryDescription;
    private String expenseReportId;
    private String expenseType;
    private String creationDate;
    private String urlImage;
    private Boolean amexTicket;
    private String receiptId;
    transient String paymentType;
    private String provider;
    private Boolean rejectableFlag;
    private String expenseOrigin;
    
	private static String [] titleOptions = {"Lunch ", "Dinner ", "Breakfast ",
			"Bus Trip ", "Cab Ride ", "Internet ", "Cell Phone Subscription ", 
			"Travel by plane ", "Hotel ", "Uber "};
 
	/**
	 * This constructor will create a payload in order to create an expense.
	 */
	public ExpensePayload() {
    	this.title = titleOptions[new Random().nextInt(titleOptions.length)]+todayInMs;
    	this.amount = amountOptions[new Random().nextInt(amountOptions.length)];
    	this.expenseDate = todayInMs;
		this.expenseType = "REPORTABLE";
		this.provider = "provider N:"+todayInMs;
		this.paymentType = "Cash";
    }

	/**
	 * This constructor will create a payload in order to add a receipt to an expense.
	 * @param myBenefit
	 * @param receipt
	 */
	public ExpensePayload(ReportWithExpenseDTO myBenefit, ReceiptForExpense receipt, int position) {
		this.id=myBenefit.getContent().getExpenses().get(position).getId();
		this.urlImage=receipt.getUrlImage();
		this.receiptId=receipt.getReceiptId();
	}
	
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ExpensePayload withId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ExpensePayload withTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public ExpensePayload withAmount(String amount) {
        this.amount = amount;
        return this;
    }

    public String getAmountUSD() {
        return amountUSD;
    }

    public void setAmountUSD(String amountUSD) {
        this.amountUSD = amountUSD;
    }

    public ExpensePayload withAmountUSD(String amountUSD) {
        this.amountUSD = amountUSD;
        return this;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public ExpensePayload withAuthorId(String authorId) {
        this.authorId = authorId;
        return this;
    }

    public String getAuthorCompleteName() {
        return authorCompleteName;
    }

    public void setAuthorCompleteName(String authorCompleteName) {
        this.authorCompleteName = authorCompleteName;
    }

    public ExpensePayload withAuthorCompleteName(String authorCompleteName) {
        this.authorCompleteName = authorCompleteName;
        return this;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public ExpensePayload withCurrencyId(String currencyId) {
        this.currencyId = currencyId;
        return this;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public ExpensePayload withCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public String getCurrencyDescription() {
        return currencyDescription;
    }

    public void setCurrencyDescription(String currencyDescription) {
        this.currencyDescription = currencyDescription;
    }

    public ExpensePayload withCurrencyDescription(String currencyDescription) {
        this.currencyDescription = currencyDescription;
        return this;
    }

    public String getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
    }

    public ExpensePayload withExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
        return this;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public ExpensePayload withCategoryId(String categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public ExpensePayload withCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
        return this;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public ExpensePayload withCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
        return this;
    }

    public String getExpenseReportId() {
        return expenseReportId;
    }

    public void setExpenseReportId(String expenseReportId) {
        this.expenseReportId = expenseReportId;
    }

    public ExpensePayload withExpenseReportId(String expenseReportId) {
        this.expenseReportId = expenseReportId;
        return this;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    public ExpensePayload withExpenseType(String expenseType) {
        this.expenseType = expenseType;
        return this;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public ExpensePayload withCreationDate(String creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public ExpensePayload withUrlImage(String urlImage) {
        this.urlImage = urlImage;
        return this;
    }

    public Boolean getAmexTicket() {
        return amexTicket;
    }

    public void setAmexTicket(Boolean amexTicket) {
        this.amexTicket = amexTicket;
    }

    public ExpensePayload withAmexTicket(Boolean amexTicket) {
        this.amexTicket = amexTicket;
        return this;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public ExpensePayload withReceiptId(String receiptId) {
        this.receiptId = receiptId;
        return this;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public ExpensePayload withPaymentType(String paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public ExpensePayload withProvider(String provider) {
        this.provider = provider;
        return this;
    }

    public Boolean getRejectableFlag() {
        return rejectableFlag;
    }

    public void setRejectableFlag(Boolean rejectableFlag) {
        this.rejectableFlag = rejectableFlag;
    }

    public ExpensePayload withRejectableFlag(Boolean rejectableFlag) {
        this.rejectableFlag = rejectableFlag;
        return this;
    }

    public String getExpenseOrigin() {
        return expenseOrigin;
    }

    public void setExpenseOrigin(String expenseOrigin) {
        this.expenseOrigin = expenseOrigin;
    }

    public ExpensePayload withExpenseOrigin(String expenseOrigin) {
        this.expenseOrigin = expenseOrigin;
        return this;
    }

}