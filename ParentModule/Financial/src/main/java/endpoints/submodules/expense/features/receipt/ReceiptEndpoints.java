package endpoints.submodules.expense.features.receipt;

import endpoints.submodules.expense.ExpenseEndpoints;

public class ReceiptEndpoints extends ExpenseEndpoints {
	public static String uploadReceipt = "/proxy/glow/filemanagerservice/resources/file";
	public static String getReceipt = "/proxy/glow/filemanagerservice/resources/static";
	public static String editReceipt = "/proxy/glow/filemanagerservice/metadata/entry/%s";
}
