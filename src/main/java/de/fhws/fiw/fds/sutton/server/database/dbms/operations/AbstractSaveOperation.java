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
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public abstract class AbstractSaveOperation<P extends AbstractModel> extends
	AbstractWriteOperation<P>
{
	public AbstractSaveOperation( final IPersistency persistency )
	{
		super( persistency );
	}

	protected final void createDatabaseStatement( ) throws SQLException
	{
		this.databaseStatement =
			this.databaseConnection.prepareStatement( this.databaseSQLStatement, RETURN_GENERATED_KEYS );
	}

	protected final void createDatabaseSqlStatement( ) throws SQLException
	{
		final StringBuffer sqlStmt = new StringBuffer( );

		sqlStmt.append( "INSERT INTO " );
		sqlStmt.append( getTableName( ) );
		sqlStmt.append( "(" );
		sqlStmt.append( columnsNamesSeparatedByComma( ) );
		sqlStmt.append( ")" );
		sqlStmt.append( "VALUES(" );
		sqlStmt.append( columnValuesReplacedByQuestionMarksAndSeparatedByComma( ) );
		sqlStmt.append( ")" );

		this.databaseSQLStatement = sqlStmt.toString( );
	}

	protected abstract List<String> columnNames( );

	protected final void executeSQLStatement( ) throws SQLException
	{
		this.databaseStatement.executeUpdate( );
		this.params.setId( getNewId( ) );
	}

	private long getNewId( ) throws SQLException
	{
		final ResultSet resultSet = this.databaseStatement.getGeneratedKeys( );
		resultSet.next( );
		return resultSet.getLong( 1 );
	}

	private String columnsNamesSeparatedByComma( )
	{
		return columnNames( ).stream( ).collect( Collectors.joining( "," ) );
	}

	private String columnValuesReplacedByQuestionMarksAndSeparatedByComma( )
	{
		return columnNames( ).stream( )
							 .map( c -> "?" )
							 .collect( Collectors.joining( "," ) );
	}
}
