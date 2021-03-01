package database.submodules.quotation.features;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.testng.SkipException;

import database.submodules.quotation.QuotationDBHelper;
import parameters.submodules.quotation.GetQuotationParameters;

/**
 * @author german.massello
 *
 */
public class GetQuotationDBHelper extends QuotationDBHelper {
	
	/**
	 * This method will return a GetQuotationParameters object.
	 * @param parameters
	 * @return getQuotationParameters
	 * @throws SQLException
	 * @author german.massello
	 */
	public GetQuotationParameters getGloberThatHaveQuotation(GetQuotationParameters parameters) throws SQLException  {
		try {
			String query = "SELECT g.username, req.id\n" + 
					"FROM public.requisitions req\n" + 
					"LEFT JOIN globers g ON g.id=req.requester_fk\n" + 
					"WHERE Req.state='"+parameters.getState()+"'\n";
			if (!parameters.getStatus().isEmpty())	query = query+"AND req.status='"+parameters.getStatus()+"'\n";
			query=query + "ORDER BY RANDOM() LIMIT 1";
			ResultSet rs = getResultSet(query);
			if(!rs.next()) throw new SkipException("No available quotation for the glober: "+parameters.getUserName()+" and quotation in status: "+parameters.getStatus()+ " and state: "+parameters.getState());
			parameters.setUserName(rs.getString("username"));
			parameters.setQuotationId(rs.getString("id"));
		}finally{
			endConnection();
		}	
		return parameters;
	}
	
}
