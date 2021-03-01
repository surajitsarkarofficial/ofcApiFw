
package dto.submodules.expense.expense.get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExpenseList {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("amountUSD")
    @Expose
    private String amountUSD;
    @SerializedName("authorId")
    @Expose
    private String authorId;
    @SerializedName("authorCompleteName")
    @Expose
    private String authorCompleteName;
    @SerializedName("currencyId")
    @Expose
    private String currencyId;
    @SerializedName("currencyCode")
    @Expose
    private String currencyCode;
    @SerializedName("currencyDescription")
    @Expose
    private String currencyDescription;
    @SerializedName("expenseDate")
    @Expose
    private String expenseDate;
    @SerializedName("categoryId")
    @Expose
    private String categoryId;
    @SerializedName("categoryCode")
    @Expose
    private String categoryCode;
    @SerializedName("categoryDescription")
    @Expose
    private String categoryDescription;
    @SerializedName("expenseReportId")
    @Expose
    private String expenseReportId;
    @SerializedName("expenseType")
    @Expose
    private String expenseType;
    @SerializedName("creationDate")
    @Expose
    private String creationDate;
    @SerializedName("urlImage")
    @Expose
    private String urlImage;
    @SerializedName("amexTicket")
    @Expose
    private String amexTicket;
    @SerializedName("receiptId")
    @Expose
    private String receiptId;
    @SerializedName("paymentType")
    @Expose
    private String paymentType;
    @SerializedName("provider")
    @Expose
    private String provider;
    @SerializedName("rejectableFlag")
    @Expose
    private String rejectableFlag;
    @SerializedName("expenseOrigin")
    @Expose
    private String expenseOrigin;
    @SerializedName("outOfPolicyFlag")
    @Expose
    private String outOfPolicyFlag;
    @SerializedName("fixedAssetFlag")
    @Expose
    private String fixedAssetFlag;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ExpenseList() {
    }

    /**
     * 
     * @param amountUSD
     * @param title
     * @param amexTicket
     * @param authorCompleteName
     * @param categoryDescription
     * @param paymentType
     * @param provider
     * @param id
     * @param currencyId
     * @param receiptId
     * @param rejectableFlag
     * @param urlImage
     * @param amount
     * @param currencyDescription
     * @param expenseType
     * @param categoryCode
     * @param authorId
     * @param creationDate
     * @param expenseDate
     * @param outOfPolicyFlag
     * @param fixedAssetFlag
     * @param expenseOrigin
     * @param currencyCode
     * @param categoryId
     * @param expenseReportId
     */
    public ExpenseList(String id, String title, String amount, String amountUSD, String authorId, String authorCompleteName, String currencyId, String currencyCode, String currencyDescription, String expenseDate, String categoryId, String categoryCode, String categoryDescription, String expenseReportId, String expenseType, String creationDate, String urlImage, String amexTicket, String receiptId, String paymentType, String provider, String rejectableFlag, String expenseOrigin, String outOfPolicyFlag, String fixedAssetFlag) {
        super();
        this.id = id;
        this.title = title;
        this.amount = amount;
        this.amountUSD = amountUSD;
        this.authorId = authorId;
        this.authorCompleteName = authorCompleteName;
        this.currencyId = currencyId;
        this.currencyCode = currencyCode;
        this.currencyDescription = currencyDescription;
        this.expenseDate = expenseDate;
        this.categoryId = categoryId;
        this.categoryCode = categoryCode;
        this.categoryDescription = categoryDescription;
        this.expenseReportId = expenseReportId;
        this.expenseType = expenseType;
        this.creationDate = creationDate;
        this.urlImage = urlImage;
        this.amexTicket = amexTicket;
        this.receiptId = receiptId;
        this.paymentType = paymentType;
        this.provider = provider;
        this.rejectableFlag = rejectableFlag;
        this.expenseOrigin = expenseOrigin;
        this.outOfPolicyFlag = outOfPolicyFlag;
        this.fixedAssetFlag = fixedAssetFlag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ExpenseList withId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ExpenseList withTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public ExpenseList withAmount(String amount) {
        this.amount = amount;
        return this;
    }

    public String getAmountUSD() {
        return amountUSD;
    }

    public void setAmountUSD(String amountUSD) {
        this.amountUSD = amountUSD;
    }

    public ExpenseList withAmountUSD(String amountUSD) {
        this.amountUSD = amountUSD;
        return this;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public ExpenseList withAuthorId(String authorId) {
        this.authorId = authorId;
        return this;
    }

    public String getAuthorCompleteName() {
        return authorCompleteName;
    }

    public void setAuthorCompleteName(String authorCompleteName) {
        this.authorCompleteName = authorCompleteName;
    }

    public ExpenseList withAuthorCompleteName(String authorCompleteName) {
        this.authorCompleteName = authorCompleteName;
        return this;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public ExpenseList withCurrencyId(String currencyId) {
        this.currencyId = currencyId;
        return this;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public ExpenseList withCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public String getCurrencyDescription() {
        return currencyDescription;
    }

    public void setCurrencyDescription(String currencyDescription) {
        this.currencyDescription = currencyDescription;
    }

    public ExpenseList withCurrencyDescription(String currencyDescription) {
        this.currencyDescription = currencyDescription;
        return this;
    }

    public String getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
    }

    public ExpenseList withExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
        return this;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public ExpenseList withCategoryId(String categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public ExpenseList withCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
        return this;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public ExpenseList withCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
        return this;
    }

    public String getExpenseReportId() {
        return expenseReportId;
    }

    public void setExpenseReportId(String expenseReportId) {
        this.expenseReportId = expenseReportId;
    }

    public ExpenseList withExpenseReportId(String expenseReportId) {
        this.expenseReportId = expenseReportId;
        return this;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    public ExpenseList withExpenseType(String expenseType) {
        this.expenseType = expenseType;
        return this;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public ExpenseList withCreationDate(String creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public ExpenseList withUrlImage(String urlImage) {
        this.urlImage = urlImage;
        return this;
    }

    public String getAmexTicket() {
        return amexTicket;
    }

    public void setAmexTicket(String amexTicket) {
        this.amexTicket = amexTicket;
    }

    public ExpenseList withAmexTicket(String amexTicket) {
        this.amexTicket = amexTicket;
        return this;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public ExpenseList withReceiptId(String receiptId) {
        this.receiptId = receiptId;
        return this;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public ExpenseList withPaymentType(String paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public ExpenseList withProvider(String provider) {
        this.provider = provider;
        return this;
    }

    public String getRejectableFlag() {
        return rejectableFlag;
    }

    public void setRejectableFlag(String rejectableFlag) {
        this.rejectableFlag = rejectableFlag;
    }

    public ExpenseList withRejectableFlag(String rejectableFlag) {
        this.rejectableFlag = rejectableFlag;
        return this;
    }

    public String getExpenseOrigin() {
        return expenseOrigin;
    }

    public void setExpenseOrigin(String expenseOrigin) {
        this.expenseOrigin = expenseOrigin;
    }

    public ExpenseList withExpenseOrigin(String expenseOrigin) {
        this.expenseOrigin = expenseOrigin;
        return this;
    }

    public String getOutOfPolicyFlag() {
        return outOfPolicyFlag;
    }

    public void setOutOfPolicyFlag(String outOfPolicyFlag) {
        this.outOfPolicyFlag = outOfPolicyFlag;
    }

    public ExpenseList withOutOfPolicyFlag(String outOfPolicyFlag) {
        this.outOfPolicyFlag = outOfPolicyFlag;
        return this;
    }

    public String getFixedAssetFlag() {
        return fixedAssetFlag;
    }

    public void setFixedAssetFlag(String fixedAssetFlag) {
        this.fixedAssetFlag = fixedAssetFlag;
    }

    public ExpenseList withFixedAssetFlag(String fixedAssetFlag) {
        this.fixedAssetFlag = fixedAssetFlag;
        return this;
    }

}
