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

public abstract class AbstractLoadByIdOperation<R extends AbstractModel> extends AbstractLoadSingleOperation<Long, R>
{
	public AbstractLoadByIdOperation( final IPersistency persistency )
	{
		super( persistency );
	}

	@Override protected String sqlStatementWithoutSelectStartingAfterFrom( )
	{
		return getTableName( ) + " WHERE " + AbstractTable.COL_ID + " = ?";
	}

	@Override protected void setQueryParametersToPreparedStatement( ) throws SQLException
	{
		this.databaseStatement.setLong( 1, this.params );
	}

	protected abstract String getTableName( );

}
