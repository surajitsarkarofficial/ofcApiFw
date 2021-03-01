package utils;

public class GlowEnums {
	/**
	 * This enum contains list of execution environments
	 * @author surajit.sarkar
	 *
	 */
	/*public static enum ExecutionEnvironments{
		QA,UAT,PreProd
	}*/
	/**
	 * This enum contains list of execution suite group names.
	 * @author surajit.sarkar
	 *
	 */
	public static enum ExecutionSuiteGroups{
		Smoke(ExeGroups.Smoke),
		Regression(ExeGroups.Regression),
		Sanity(ExeGroups.Sanity),
		Dev(ExeGroups.NotAvailableInPreProd),
		Debug(ExeGroups.Debug);
		
		public class ExeGroups{
			public static final String Smoke="Smoke";
			public static final String Regression="Regression";
			public static final String Sanity="Sanity";
			public static final String NotAvailableInPreProd="NotAvailableInPreProd";
			public static final String Debug="Debug";
		}
		
		private final String group;
		private ExecutionSuiteGroups(String group) {
	        this.group = group;
	    }
		public String getGroup() {
			return group;
		}
	}

}
