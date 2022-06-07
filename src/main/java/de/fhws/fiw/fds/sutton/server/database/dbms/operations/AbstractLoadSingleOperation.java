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
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractLoadSingleOperation<P, R extends AbstractModel>
	extends AbstractLoadOperation<P, SingleModelResult<R>>
{
	public AbstractLoadSingleOperation( final IPersistency persistency )
	{
		super( persistency );
	}

	@Override protected void processResultSet( ) throws SQLException
	{
		if ( this.resultSet.next( ) )
		{
			this.queryResult = new SingleModelResult<>( createModel( this.resultSet ) );
		}
		else
		{
			this.queryResult = new SingleModelResult<>( );
		}
	}

	@Override protected void createDatabaseSQLStatement( ) throws SQLException
	{
		final StringBuffer returnValue = new StringBuffer( );

		returnValue.append( "SELECT * FROM " );
		returnValue.append( sqlStatementWithoutSelectStartingAfterFrom( ) );

		this.databaseSQLStatement = returnValue.toString( );
	}

	protected abstract String sqlStatementWithoutSelectStartingAfterFrom( );

	protected abstract R createModel( ResultSet resultSet ) throws SQLException;

	@Override protected SingleModelResult<R> createDatabaseError( )
	{
		final SingleModelResult returnValue = new SingleModelResult( );
		returnValue.setError( );
		return returnValue;
	}
}
