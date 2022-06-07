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

import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class PagingBehaviorUsingOffsetSize<T extends AbstractModel> extends PagingBehavior<T>
{
	public static final int DEFAULT_PAGE_SIZE = 10;
	public static final String DEFAULT_PAGE_SIZE_STR = "" + DEFAULT_PAGE_SIZE;

	public static final int DEFAULT_MAX_PAGE_SIZE = 20;

	public static final String QUERY_PARAM_SIZE = "size";

	public static final String QUERY_PARAM_OFFSET = "offset";

	protected int offset;

	protected int size;

	protected String offsetQueryParamName = QUERY_PARAM_OFFSET;

	protected String sizeQueryParamName = QUERY_PARAM_SIZE;

	private PagingBehaviorUsingOffsetSize( )
	{
	}

	private PagingBehaviorUsingOffsetSize( final String offsetQueryParamName, final String sizeQueryParamName )
	{
		this.offsetQueryParamName = offsetQueryParamName;
		this.sizeQueryParamName = sizeQueryParamName;
	}

	public PagingBehaviorUsingOffsetSize( final String offsetQueryParamName, final String sizeQueryParamName,
		final int offset, final int size )
	{
		this( offsetQueryParamName, sizeQueryParamName );
		setOffset( offset );
		setSize( size );
	}

	public PagingBehaviorUsingOffsetSize( final int offset, final int size )
	{
		setOffset( offset );
		setSize( size );
	}

	@Override
	protected boolean hasNextLink( final CollectionModelResult<?> result )
	{
		return this.offset + this.size < result.getTotalNumberOfResult( );
	}

	@Override
	protected URI getNextUri( final UriInfo uriInfo, final CollectionModelResult<?> result )
	{
		final UriBuilder uriBuilder = createUriBuilder( uriInfo );
		final int newOffset = Math.min( this.offset + this.size, result.getTotalNumberOfResult( ) - 1 );

		return uriBuilder.build( newOffset, this.size );
	}

	@Override
	protected boolean hasPrevLink( )
	{
		return this.offset > 0;
	}

	@Override
	protected URI getPrevUri( final UriInfo uriInfo )
	{
		final UriBuilder uriBuilder = createUriBuilder( uriInfo );
		final int newOffset = Math.max( this.offset - this.size, 0 );

		return uriBuilder.build( newOffset, this.size );
	}

	@Override
	protected URI getSelfUri( final UriInfo uriInfo )
	{
		final UriBuilder uriBuilder = createUriBuilder( uriInfo );

		return uriBuilder.build( this.offset, this.size );
	}

	@Override
	protected List<T> page( final Collection<T> result )
	{
		if ( this.offset >= result.size( ) )
		{
			this.offset = Math.max( 0, result.size( ) - this.size );
		}

		return result.stream( ).skip( offset ).limit( size ).collect( Collectors.toList( ) );
	}

	protected int getDefaultMaxPageSize( )
	{
		return DEFAULT_MAX_PAGE_SIZE;
	}

	private void setOffset( final int offset )
	{
		this.offset = Math.max( 0, offset );
	}

	private void setSize( final int size )
	{
		this.size = Math.max( 1, Math.min( size, getDefaultMaxPageSize( ) ) );
	}

	private UriBuilder createUriBuilder( final UriInfo uriInfo )
	{
		return uriInfo.getRequestUriBuilder( )
					  .replaceQueryParam( getOffsetParamName( ), getQueryParamOffsetAsTemplate( ) )
					  .replaceQueryParam( getSizeParamName( ), getQueryParamSizeAsTemplate( ) );
	}

	private String getOffsetParamName( )
	{
		return this.offsetQueryParamName;
	}

	private String getSizeParamName( )
	{
		return this.sizeQueryParamName;
	}

	private final String getQueryParamOffsetAsTemplate( )
	{
		return "{" + getOffsetParamName( ) + "}";
	}

	private final String getQueryParamSizeAsTemplate( )
	{
		return "{" + getSizeParamName( ) + "}";
	}

}
