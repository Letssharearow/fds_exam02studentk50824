/*
 * Copyright (c) peter.braun@fhws.de
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package de.fhws.fiw.fds.sutton.server.database.dbms.operations;

import de.fhws.fiw.fds.sutton.server.database.dbms.IPersistency;
import de.fhws.fiw.fds.sutton.server.database.results.AbstractResult;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractLoadOperation<P, R extends AbstractResult> extends AbstractDatabaseOperation<P, R>
{
	protected PreparedStatement databaseStatement;

	protected String databaseSQLStatement;

	protected ResultSet resultSet;

	protected R queryResult;

	public AbstractLoadOperation( final IPersistency persistency )
	{
		super( persistency );
	}

	@Override protected R executeDatabaseOperations( ) throws SQLException
	{
		createDatabaseSQLStatement( );

		createDatabaseStatement( );

		configureDatabaseStatement( );

		executeDatabaseStatement( );

		processResultSet( );

		closeResultSet( );

		closeDatabaseStatement( );

		return this.queryResult;
	}

	protected abstract void createDatabaseSQLStatement( ) throws SQLException;

	protected void createDatabaseStatement( ) throws SQLException
	{
		this.databaseStatement = this.databaseConnection.prepareStatement( this.databaseSQLStatement );
	}

	protected void configureDatabaseStatement( ) throws SQLException
	{
		setQueryParametersToPreparedStatement( );
	}

	protected abstract void setQueryParametersToPreparedStatement( ) throws SQLException;

	protected void executeDatabaseStatement( ) throws SQLException
	{
		this.resultSet = this.databaseStatement.executeQuery( );
	}

	protected abstract void processResultSet( ) throws SQLException;

	protected void closeResultSet( ) throws SQLException
	{
		if ( this.resultSet != null )
		{
			this.resultSet.close( );
		}
	}

	protected void closeDatabaseStatement( ) throws SQLException
	{
		if ( this.databaseStatement != null )
		{
			this.databaseStatement.close( );
		}
	}
}
