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

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractDatabaseOperation<P, R extends AbstractResult>
{
	protected final IPersistency mysql;

	protected Connection databaseConnection;

	protected P params;

	protected AbstractDatabaseOperation( final IPersistency persistency )
	{
		this.mysql = persistency;
	}

	public final R execute( final P param )
	{
		this.params = param;

		try
		{
			return _process( );
		}
		catch ( final SQLException e )
		{
			return handleExceptions( e );
		}
		finally
		{
			closeConnection( );
		}
	}

	private R _process( ) throws SQLException
	{
		final long startTime = System.currentTimeMillis( );

		preProcessModel( );

		this.databaseConnection = this.mysql.getConnection( );

		this.databaseConnection.setAutoCommit( false );

		final R returnValue = executeDatabaseOperations( );

		final long stopTime = System.currentTimeMillis( );

		returnValue.setTimes( startTime, stopTime );

		this.databaseConnection.commit( );

		return returnValue;
	}

	protected void preProcessModel( )
	{

	}

	protected abstract R executeDatabaseOperations( ) throws SQLException;

	private R handleExceptions( final SQLException e )
	{
		e.printStackTrace( );
		if ( this.databaseConnection != null )
		{
			try
			{
				this.databaseConnection.rollback( );
			}
			catch ( final Exception ee )
			{
				ee.printStackTrace( );
			}
		}

		return createDatabaseError( );
	}

	private void closeConnection( )
	{
		if ( this.databaseConnection != null )
		{
			try
			{
				this.databaseConnection.close( );
			}
			catch ( final Exception ee )
			{
				ee.printStackTrace( );
			}
		}
	}

	protected abstract R createDatabaseError( );

}
