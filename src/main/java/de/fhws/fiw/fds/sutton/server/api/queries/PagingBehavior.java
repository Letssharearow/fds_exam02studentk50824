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

import de.fhws.fiw.fds.sutton.server.api.hyperlinks.Hyperlinks;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Collection;
import java.util.List;

public abstract class PagingBehavior<T extends AbstractModel>
{
	public final CollectionModelResult<T> page( final CollectionModelResult<T> fullResult )
	{
		final CollectionModelResult<T> returnValue = new CollectionModelResult<>( );
		returnValue.getResult( ).addAll( page( fullResult.getResult( ) ) );
		returnValue.setTotalNumberOfResult( fullResult.getTotalNumberOfResult( ) );
		return returnValue;
	}

	public final void addSelfLink( final PagingContext pagingContext )
	{
		Hyperlinks.addLink( pagingContext.getResponseBuilder( ),
			getSelfUri( pagingContext.getUriInfo( ) ),
			"self",
			pagingContext.getMediaType( ) );
	}

	public final void addPrevPageLink( final PagingContext pagingContext )
	{
		if ( hasPrevLink( ) )
		{
			Hyperlinks.addLink( pagingContext.getResponseBuilder( ),
				getPrevUri( pagingContext.getUriInfo( ) ),
				"prev",
				pagingContext.getMediaType( ) );
		}
	}

	public final void addNextPageLink( final PagingContext pagingContext,
		final CollectionModelResult<?> databaseResult )
	{
		if ( hasNextLink( databaseResult ) )
		{
			Hyperlinks.addLink( pagingContext.getResponseBuilder( ),
				getNextUri( pagingContext.getUriInfo( ), databaseResult ),
				"next",
				pagingContext.getMediaType( ) );
		}
	}

	protected abstract List<T> page( final Collection<T> result );

	protected abstract boolean hasNextLink( final CollectionModelResult<?> result );

	protected abstract boolean hasPrevLink( );

	protected abstract URI getSelfUri( final UriInfo uriInfo );

	protected abstract URI getPrevUri( final UriInfo uriInfo );

	protected abstract URI getNextUri( final UriInfo uriInfo, final CollectionModelResult<?> result );
}
