package de.fhws.fiw.fds.sutton.server.database.dbms.tables;

import de.fhws.fiw.fds.sutton.server.database.dbms.operations.PreparedStatementHelper;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.function.Function;

public abstract class AbstractSetParametersInSqlStatement<T extends AbstractModel>
{
	public void set( final T model, final PreparedStatement statement ) throws
		SQLException
	{
		for ( final Map.Entry<Integer, Function<T, Object>> e : getMapOfIndexToGetterMethod( ).entrySet( ) )
		{
			set( statement, e.getKey( ), e.getValue( ).apply( model ) );
		}
	}

	protected abstract Map<Integer, Function<T, Object>> getMapOfIndexToGetterMethod( );

	private void set( final PreparedStatement statement, final int index, final Object value ) throws SQLException
	{
		PreparedStatementHelper.set( statement, index, value );
	}

}
