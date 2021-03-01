package database;

import properties.GlowProperties;

public class GlowDBHelper extends BaseDBHelper implements GloberDBHelper, ClientDBHelper, ProjectDBHelper, StudioDBHelper
, LocationDBHelper, SowDBHelper, PositionDBHelper, RolDBHelper, WatcherDBHelper, SafeApproverDBHelper, FinanceDBHelper {
	
	/**
	 * set DB properties
	 
	 */
	public GlowDBHelper() {
		dbName = GlowProperties.database;
		userName = GlowProperties.dbUser;
		password = GlowProperties.dbPassword;
	}
	
}