package database.submodules.requisition.features;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.testng.SkipException;

import database.submodules.requisition.RequisitionDBHelper;

/**
 * @author german.massello
 *
 */
public class GetRequisitionDBHelper extends RequisitionDBHelper {

	/**
	 * Get puma requester with requisition.
	 * @param requisitionStatus
	 * @param requisitionState
	 * @return Map<String,String>
	 * @throws SQLException
	 */
	public Map<String,String> getPumaRequester(String requisitionStatus, String requisitionState) throws SQLException  {
		if (requisitionStatus.isEmpty()) requisitionStatus="IS NULL";
		else requisitionStatus = " = '"+requisitionStatus+"'";
		Map<String,String> glober=new HashMap<String,String>();
		try {
			String query = "SELECT g.username, COUNT(*) AS quantity, MAX(req.group_id_fk) AS groupId,  MAX(req.ID) AS reqId, "
					+ "MAX(req.requester_fk) AS requesterId\n" + 
					"FROM available_users_authorities aua \n" + 
					"INNER JOIN users ON aua.user_fk = users.id\n" + 
					"INNER JOIN globers g ON g.id = users.resume_fk INNER JOIN contracts_information ci ON ci.id = g.contract_information_fk\n" + 
					"INNER JOIN contracts_data cd ON cd.contract_information_fk = ci.id\n" + 
					"LEFT JOIN requisitions req ON g.id=req.requester_fk\n" + 
					"WHERE aua.authority_fk =\n" + 
					"(SELECT id\n" + 
					"FROM public.authorities\n" + 
					"WHERE name='RequesterPUMA')\n" + 
					"AND (LOWER(SUBSTRING(g.username\n" + 
					"FROM 1\n" + 
					"FOR 3)) != 'old')\n" + 
					"AND (cd.end_date IS NULL)\n" + 
					"AND ((CURRENT_DATE - 0) > g.creation_date)\n" + 
					"AND req.status " +requisitionStatus +"\n" + 
					"AND req.state='"+requisitionState+"'\n" + 
					"GROUP BY g.username\n" + 
					"ORDER BY quantity DESC\n" + 
					"LIMIT 1";
			ResultSet rs = getResultSet(query);
			String skipMessage="No available requisition for this status: "+requisitionStatus+ "State: "+requisitionState+" for a puma requester";
			if(!rs.next()) throw new SkipException(skipMessage);
			glober.put("username", rs.getString("username"));
			glober.put("groupId", rs.getString("groupId"));
			glober.put("reqId", rs.getString("reqId"));
			glober.put("requesterId", rs.getString("requesterId"));
		}finally{
			endConnection();
		}	
		return glober;
	}
	
	/**
	 * Get requisition approver with requisition.
	 * @param requisitionStatus
	 * @param requisitionState
	 * @return Map<String,String>
	 * @throws SQLException
	 */
	public Map<String,String> getRequisitionApprover(String requisitionStatus, String requisitionState) throws SQLException  {
		if (requisitionStatus.isEmpty()) requisitionStatus="IS NULL";
		else requisitionStatus = " = '"+requisitionStatus+"'";
		Map<String,String> glober=new HashMap<String,String>();
		try {
			String query = "SELECT g.username, COUNT(*) AS quantity\n" + 
					"FROM available_users_authorities aua \n" + 
					"INNER JOIN users ON aua.user_fk = users.id\n" + 
					"INNER JOIN globers g ON g.id = users.resume_fk INNER JOIN contracts_information ci ON ci.id = g.contract_information_fk\n" + 
					"INNER JOIN contracts_data cd ON cd.contract_information_fk = ci.id\n" + 
					"LEFT JOIN approval_chain_notification acn ON g.id=acn.owner_fk\n" + 
					"LEFT JOIN approval_chain_header ach ON ach.id = acn.header_fk\n" + 
					"LEFT JOIN requisitions req ON ach.object_id = req.id\n" + 
					"WHERE aua.authority_fk =\n" + 
					"(SELECT id\n" + 
					"FROM public.authorities\n" + 
					"WHERE name='RequisitionApprover')\n" + 
					"AND (LOWER(SUBSTRING(g.username\n" + 
					"FROM 1\n" + 
					"FOR 3)) != 'old')\n" + 
					"AND (cd.end_date IS NULL)\n" + 
					"AND ((CURRENT_DATE - 0) > g.creation_date)\n" + 
					"AND req.status " +requisitionStatus +"\n" + 
					"AND req.state='"+requisitionState+"'\n" + 
					"AND acn.status='PENDING'\n" + 
					"AND ach.module='PUMA'\n" + 
					"GROUP BY g.username\n" + 
					"ORDER BY quantity DESC\n" + 
					"LIMIT 1";
			ResultSet rs = getResultSet(query);
			String skipMessage="No available requisition for this status: "+requisitionStatus+ "State: "+requisitionState+" for a Requisition Approver";
			if(!rs.next()) throw new SkipException(skipMessage);
			glober.put("username", rs.getString("username"));
		}finally{
			endConnection();
		}	
		return glober;
	}
	
}
