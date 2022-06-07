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
import de.fhws.fiw.fds.sutton.server.database.dbms.tables.AbstractTable;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractUpdateOperation<P extends AbstractModel> extends AbstractWriteOperation<P>
{
	public AbstractUpdateOperation( final IPersistency persistency )
	{
		super( persistency );
	}

	protected void createDatabaseStatement( ) throws SQLException
	{
		this.databaseStatement = this.databaseConnection.prepareStatement( this.databaseSQLStatement );
	}

	protected void createDatabaseSqlStatement( )
	{
		final StringBuffer sqlStmt = new StringBuffer( );

		sqlStmt.append( "UPDATE " );
		sqlStmt.append( getTableName( ) );
		sqlStmt.append( " SET " );
		sqlStmt.append( columnNamesEqualsQuestionMark( ) );
		sqlStmt.append( " WHERE " );
		sqlStmt.append( AbstractTable.COL_ID );
		sqlStmt.append( " = " );
		sqlStmt.append( this.params.getId( ) );

		this.databaseSQLStatement = sqlStmt.toString( );
	}

	protected abstract List<String> columnNames( );

	protected void executeSQLStatement( ) throws SQLException
	{
		this.databaseStatement.executeUpdate( );
	}

	private String columnNamesEqualsQuestionMark( )
	{
		return columnNames( ).stream( )
							 .map( c -> createColumnNameEqualsQuestionMark( c ) )
							 .collect( Collectors.joining( "," ) );
	}

	private String createColumnNameEqualsQuestionMark( final String columnName )
	{
		return columnName + " = ?";
	}

}
