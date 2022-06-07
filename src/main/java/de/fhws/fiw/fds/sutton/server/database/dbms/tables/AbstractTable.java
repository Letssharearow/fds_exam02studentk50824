/*
 * Copyright 2019 University of Applied Sciences Würzburg-Schweinfurt, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.fhws.fiw.fds.sutton.server.database.dbms.tables;

import de.fhws.fiw.fds.sutton.server.database.dbms.IPersistency;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractTable
{
	public static final String COL_ID = "id";

	private final IPersistency persistency;

	protected AbstractTable( final IPersistency persistency )
	{
		this.persistency = persistency;
	}

	public void createTable( )
	{
		final StringBuffer sb = new StringBuffer( );
		sb.append( "CREATE TABLE IF NOT EXISTS " )
		  .append( getTableName( ) )
		  .append( " ( " + COL_ID + " bigint unsigned NOT NULL AUTO_INCREMENT," );

		appendColumns( sb );

		sb.append( getPrimaryKey( ) );
		sb.append( ")" );

		try
		{
			final Connection con = this.persistency.getConnection( );
			final Statement stmt = con.createStatement( );
			stmt.executeUpdate( sb.toString( ) );
		}
		catch ( final SQLException e )
		{
			e.printStackTrace( );
		}
	}

	protected String getPrimaryKey( )
	{
		return "PRIMARY KEY (`" + COL_ID + "`)";
	}

	protected abstract String getTableName( );

	protected abstract void appendColumns( StringBuffer sb );
}
