package payloads.submodules.manage.features.configureExpense;

import java.util.Random;

import payloads.submodules.manage.ManagePayLoadHelper;
import utils.Utilities;

/**
 * @author german.massello
 *
 */
public class ExceptionRolPayLoadHelper extends ManagePayLoadHelper {

	private String id;
	private String name;
	private String amount;

	/**
	 * This method will construct a payload in order to create an Exception Rol.
	 * @author german.massello
	 */
	public ExceptionRolPayLoadHelper() {
		this.name = "ExceptionRol"+Utilities.getTodayInMilliseconds();
		this.amount = amountOptions[new Random().nextInt(amountOptions.length)];
	}
	
	/**
	 * This method will construct a payload in order to edit an Exception Rol.
	 * @param id
	 * @author german.massello
	 */
	public ExceptionRolPayLoadHelper(String id) {
		this.id = id;
		this.name = "EditedExceptionRol"+todayInMs;
		this.amount = amountOptions[new Random().nextInt(amountOptions.length)];
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAmount() {
		return amount;
	}
	
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
}
