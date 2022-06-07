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

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class PreparedStatementHelper
{
	public static void set( final PreparedStatement preparedStatement, final int index, final Object value )
		throws SQLException
	{
		if ( value instanceof String )
		{
			preparedStatement.setString( index, ( String ) value );
		}
		else if ( value instanceof Long )
		{
			preparedStatement.setLong( index, ( Long ) value );
		}
		else if ( value instanceof Integer )
		{
			preparedStatement.setInt( index, ( Integer ) value );
		}
		else if ( value instanceof Date )
		{
			preparedStatement.setDate( index, new java.sql.Date( ( ( Date ) value ).getTime( ) ) );
		}
		else if ( value instanceof LocalDate )
		{
			preparedStatement.setDate( index, java.sql.Date.valueOf( ( LocalDate ) value ) );
		}
		else if ( value instanceof LocalDateTime )
		{
			preparedStatement.setTimestamp( index, java.sql.Timestamp.valueOf( ( LocalDateTime ) value ) );
		}
		else if ( value instanceof Boolean )
		{
			preparedStatement.setBoolean( index, ( Boolean ) value );
		}
		else if ( value instanceof Double )
		{
			preparedStatement.setDouble( index, ( Double ) value );
		}
		else if ( value instanceof Float )
		{
			preparedStatement.setFloat( index, ( Float ) value );
		}
	}
}
