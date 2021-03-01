package endpoints.submodules.manage.features;

import endpoints.submodules.manage.ManageEndpoints;

/**
 * @author german.massello
 *
 */
public class ConfigureExpenseEndpoints extends ManageEndpoints {
	public static String addPositionToExceptionRol = "/proxy/glow/ticketsexceptionsservice/tickets/exceptions/position";
	public static String createExceptionRol = "/proxy/glow/ticketsexceptionsservice/tickets/exceptions";
	public static String deleteExceptionRol = "/proxy/glow/ticketsexceptionsservice/tickets/exceptions/role/";
	public static String editExceptionRol = "/proxy/glow/ticketsexceptionsservice/tickets/exceptions";
	public static String getExceptionRol = "/proxy/glow/ticketsexceptionsservice/tickets/exceptions/roles";
	public static String removePositionFromExceptionRol = "/proxy/glow/ticketsexceptionsservice/tickets/exceptions/position/delete";
	public static String validateExceptionRol = "/proxy//glow/ticketsexceptionsservice/tickets/exceptions/validate";
}
