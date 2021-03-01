package database.submodules.expense.features;

import java.sql.SQLException;

import com.aventstack.extentreports.Status;

import database.submodules.expense.ExpenseDBHelper;
import utils.ExtentHelper;

/**
 * @author german.massello
 *
 */
public class CreateNewExpenseDBHelper extends ExpenseDBHelper {

	public CreateNewExpenseDBHelper() {
	}

	/**
	 * This method will transform a cash expense into a credit card expense.
	 * 
	 * @param creditCardName
	 * @param expenseId
	 * @throws SQLException 
	 */
	public void transformToCreditCardExpense (String creditCardName, String expenseId) throws SQLException {
		String query=
				"UPDATE public.ticket_details\n" + 
				"SET \n" + 
				"payment_type_fk= (SELECT id FROM public.payment_type WHERE name='"+creditCardName+"')\n" + 
				", amex_ticket=true\n" + 
				"WHERE id="+expenseId;
		try {
			executeUpdateQuery(query);
		}finally{
			endConnection();
		}	
		ExtentHelper.test.log(Status.INFO, "The expenses was transformed to a "+creditCardName+" expense");
	}	
}
