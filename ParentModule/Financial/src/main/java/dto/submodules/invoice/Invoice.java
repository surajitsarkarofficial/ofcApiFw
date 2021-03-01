package dto.submodules.invoice;

import java.sql.SQLException;
import java.util.Random;

import database.GlowDBHelper;
import properties.invoice.InvoiceProperties;
import utils.Utilities;

/**
 * @author german.massello
 *
 */
public class Invoice {

	private String submitter;
	private String vimId;
	private String society;
	private String vendor;
	private String amount;
	private String description;
	private String currency;
	private String urlImg;
	private String receptionDate;
	private String rateConvertion;
	private String invoiceNumber;
	private String localAmount;
	private static String [] amountOptions = {"100"};
	private static String [] vendorOptions = {"Intel", "Apple", "Acer", "Asus", "Epson", "Dell", "Samsung"};
	private static String [] descriptionsOptions = {"Notebook", "HeadSeat", "Training"};
	private static String [] rateOptions = {"2", "3", "4"};
	
	/**
	 * Default constructor
	 * @param submitter
	 * @throws SQLException
	 */
	public Invoice(String submitter) throws SQLException {
		this.submitter = submitter;
		this.vimId = Utilities.getTodayInMilliseconds().substring(1, 13);
		this.society = new GlowDBHelper().getGloberSociety(submitter);
		this.vendor = vendorOptions[new Random().nextInt(vendorOptions.length)];
		this.amount = amountOptions[new Random().nextInt(amountOptions.length)];
		this.description = descriptionsOptions[new Random().nextInt(descriptionsOptions.length)];
		this.currency = new GlowDBHelper().getGloberCurrency(submitter);
		this.urlImg = InvoiceProperties.invoiceImageUrl;
		this.receptionDate = Utilities.getYesterday("yyyyMMdd");
		this.rateConvertion = rateOptions[new Random().nextInt(rateOptions.length)];
		this.invoiceNumber = "FC9"+this.vimId;
		this.localAmount = String.valueOf((Double.valueOf(this.amount)*Double.valueOf(this.rateConvertion)));
	}
	
	public String getSubmitter() {
		return submitter;
	}
	public Invoice setSubmitter(String submitter) {
		this.submitter = submitter;
		return this;
	}
	public String getVimId() {
		return vimId;
	}
	public Invoice setVimId(String vimId) {
		this.vimId = vimId;
		return this;
	}
	public String getSociety() {
		return society;
	}
	public Invoice setSociety(String society) {
		this.society = society;
		return this;
	}
	public String getVendor() {
		return vendor;
	}
	public Invoice setVendor(String vendor) {
		this.vendor = vendor;
		return this;
	}
	public String getAmount() {
		return amount;
	}
	public Invoice setAmount(String amount) {
		this.amount = amount;
		return this;
	}
	public String getDescription() {
		return description;
	}
	public Invoice setDescription(String description) {
		this.description = description;
		return this;
	}
	public String getCurrency() {
		return currency;
	}
	public Invoice setCurrency(String currency) {
		this.currency = currency;
		return this;
	}
	public String getUrlImg() {
		return urlImg;
	}
	public Invoice setUrlImg(String urlImg) {
		this.urlImg = urlImg;
		return this;
	}
	public String getReceptionDate() {
		return receptionDate;
	}
	public Invoice setReceptionDate(String receptionDate) {
		this.receptionDate = receptionDate;
		return this;
	}
	public String getRateConvertion() {
		return rateConvertion;
	}
	public Invoice setRateConvertion(String rateConvertion) {
		this.rateConvertion = rateConvertion;
		return this;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public Invoice setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
		return this;
	}
	public String getLocalAmount() {
		return localAmount;
	}
	public Invoice setLocalAmount(String localAmount) {
		this.localAmount = localAmount;
		return this;
	}
	
}
