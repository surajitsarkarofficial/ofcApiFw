package utils;

public class StaffingEnum {
	public static enum ExecutionSuiteGroups {

		servicesGroup(StaffingGroup.services), stgGroup(StaffingGroup.stg);

		public class StaffingGroup {
			public static final String services = "services";
			public static final String stg = "stg";
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
