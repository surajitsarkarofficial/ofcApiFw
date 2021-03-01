
package dto.submodules.expense.expense.post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Content {

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

    /**
     * No args constructor for use in serialization
     * 
     */
    public Content() {
    }

    /**
     * 
     * @param amount
     * @param amountUSD
     * @param currencyDescription
     * @param expenseType
     * @param categoryCode
     * @param title
     * @param authorId
     * @param creationDate
     * @param amexTicket
     * @param authorCompleteName
     * @param expenseDate
     * @param categoryDescription
     * @param paymentType
     * @param provider
     * @param expenseOrigin
     * @param id
     * @param currencyId
     * @param currencyCode
     * @param receiptId
     * @param rejectableFlag
     * @param categoryId
     * @param expenseReportId
     * @param urlImage
     */
    public Content(String id, String title, String amount, String amountUSD, String authorId, String authorCompleteName, String currencyId, String currencyCode, String currencyDescription, String expenseDate, String categoryId, String categoryCode, String categoryDescription, String expenseReportId, String expenseType, String creationDate, String urlImage, String amexTicket, String receiptId, String paymentType, String provider, String rejectableFlag, String expenseOrigin) {
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmountUSD() {
        return amountUSD;
    }

    public void setAmountUSD(String amountUSD) {
        this.amountUSD = amountUSD;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthorCompleteName() {
        return authorCompleteName;
    }

    public void setAuthorCompleteName(String authorCompleteName) {
        this.authorCompleteName = authorCompleteName;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
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

    public String getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public String getExpenseReportId() {
        return expenseReportId;
    }

    public void setExpenseReportId(String expenseReportId) {
        this.expenseReportId = expenseReportId;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getAmexTicket() {
        return amexTicket;
    }

    public void setAmexTicket(String amexTicket) {
        this.amexTicket = amexTicket;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getRejectableFlag() {
        return rejectableFlag;
    }

    public void setRejectableFlag(String rejectableFlag) {
        this.rejectableFlag = rejectableFlag;
    }

    public String getExpenseOrigin() {
        return expenseOrigin;
    }

    public void setExpenseOrigin(String expenseOrigin) {
        this.expenseOrigin = expenseOrigin;
    }

}
