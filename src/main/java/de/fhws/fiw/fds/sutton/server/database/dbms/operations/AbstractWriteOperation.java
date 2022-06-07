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
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class AbstractWriteOperation<P extends AbstractModel>
	extends AbstractDatabaseOperation<P, NoContentResult>
{
	protected PreparedStatement databaseStatement;

	protected String databaseSQLStatement;

	public AbstractWriteOperation( final IPersistency persistency )
	{
		super( persistency );
	}

	@Override protected NoContentResult executeDatabaseOperations( ) throws SQLException
	{
		createDatabaseSqlStatement( );

		createDatabaseStatement( );

		configureDatabaseStatement( );

		executeSQLStatement( );

		closeDatabaseStatement( );

		return new NoContentResult( );
	}

	protected abstract void createDatabaseStatement( ) throws SQLException;

	protected abstract void createDatabaseSqlStatement( ) throws SQLException;

	protected abstract void executeSQLStatement( ) throws SQLException;

	protected void configureDatabaseStatement( ) throws SQLException
	{
		setValuesToPreparedStatement( );
	}

	protected abstract void setValuesToPreparedStatement( ) throws SQLException;

	protected void defineColumnValueByIndex( final int index, final Object value )
		throws SQLException
	{
		PreparedStatementHelper.set( this.databaseStatement, index, value );
	}

	@Override protected NoContentResult createDatabaseError( )
	{
		final NoContentResult returnValue = new NoContentResult( );
		returnValue.setError( );
		return returnValue;
	}

	protected abstract String getTableName( );

	private void closeDatabaseStatement( ) throws SQLException
	{
		if ( this.databaseStatement != null )
		{
			this.databaseStatement.close( );
		}
	}
}
