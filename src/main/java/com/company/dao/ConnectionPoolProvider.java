/**
 * 
 */
package com.company.dao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * @author 04-14
 *
 */
public class ConnectionPoolProvider {
	public static DataSource getDataSource() throws RuntimeException{
		try {
			DataSource dataSource;
			Context context = new InitialContext();
			Context env = (Context) context.lookup("java:/comp/env");
			dataSource = (DataSource) env.lookup("jdbc/oracle");
			return dataSource;
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}
