package com.bootcamp.portal.db.update;

import org.apache.log4j.Logger;

import com.googlecode.flyway.core.Flyway;
import com.googlecode.flyway.core.exception.FlywayException;
import com.googlecode.flyway.core.util.jdbc.DriverDataSource;
import com.mysql.jdbc.Driver;

/**
 * UpdateDB Singleton class
 * 
 * @author andriy.shevchun check and update db to last version folder for
 *         updates: resources/db/migration file format :
 *         V<version>__<comment>.sql
 */
public class UpdateDB extends Flyway {
	private static Logger LOGGER = Logger.getLogger(UpdateDB.class);
	private static UpdateDB instance = new UpdateDB();

	public static UpdateDB getInstance() {
		return instance;
	}

	private UpdateDB() {
		super();
	}

	/**
	 * Check and modify db structure
	 */
	public void migrate(String dbUrl, String userName, String password) {
		try {
			LOGGER.info("Check for update for database");
			this.setLocations("migration");
			this.setDataSource(new DriverDataSource(new Driver(), dbUrl,
					userName, password));
			try {
				LOGGER.info("Initialization Flyway");
				this.init();
			} catch (FlywayException e) {
				LOGGER.info("Flyway already initialized");
			}

			this.migrate();
			LOGGER.info("You have last version of database");
		} catch (Exception e) {
			LOGGER.error(e);
		}
	}
}