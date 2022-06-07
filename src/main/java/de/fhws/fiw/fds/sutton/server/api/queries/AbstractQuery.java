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

package de.fhws.fiw.fds.sutton.server.api.queries;

import de.fhws.fiw.fds.sutton.server.database.DatabaseException;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

import java.util.function.Predicate;

public abstract class AbstractQuery<T extends AbstractModel>
{
	protected CollectionModelResult<T> result;

	protected PagingBehavior pagingBehavior = new OnePageWithAllResults( );

	protected AbstractQuery( )
	{
	}

	public AbstractQuery setPagingBehavior( final PagingBehavior pagingBehavior )
	{
		this.pagingBehavior = pagingBehavior;
		return this;
	}

	public final CollectionModelResult<T> startQuery( )
	{
		/* DON'T OPTIMIZE THE FOLLOWING TWO LINES. WE NEED THE RESULT IN OTHER METHODS LATER. */
		this.result = executeQuery( );
		return this.result;
	}

	protected CollectionModelResult<T> executeQuery( )
	{
		CollectionModelResult<T> result;

		try
		{
			result = selectRequestedPage( doExecuteQuery( ) );
		}
		catch ( final DatabaseException e )
		{
			result = new CollectionModelResult<>( );
		}

		return result;
	}

	protected CollectionModelResult<T> selectRequestedPage( final CollectionModelResult<T> fullResult )
	{
		return this.pagingBehavior.page( fullResult );
	}

	protected abstract CollectionModelResult<T> doExecuteQuery( ) throws DatabaseException;

	public final void addSelfLink( final PagingContext pagingContext )
	{
		this.pagingBehavior.addSelfLink( pagingContext );
	}

	public final void addPrevPageLink( final PagingContext pagingContext )
	{
		this.pagingBehavior.addPrevPageLink( pagingContext );
	}

	public final void addNextPageLink( final PagingContext pagingContext )
	{
		this.pagingBehavior.addNextPageLink( pagingContext, this.result );
	}

	protected Predicate<T> all( )
	{
		return p -> true;
	}
}
