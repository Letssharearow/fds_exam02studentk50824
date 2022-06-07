package de.fhws.fiw.fds.sutton.server.api.queries;

import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class PagingBehaviorUsingPage<T extends AbstractModel> extends PagingBehavior<T>
{
	public static final String QUERY_PARAM_PAGE = "page";
	private static final int DEFAULT_PAGE_SIZE = 10;

	protected String pageQueryParamName = QUERY_PARAM_PAGE;

	protected int pageNumber;

	public PagingBehaviorUsingPage( final String pageQueryParamName, final int pageNumber )
	{
		this.pageQueryParamName = pageQueryParamName;
		setPageNumber( pageNumber );
	}

	public PagingBehaviorUsingPage( final int pageNumber )
	{
		setPageNumber( pageNumber );
	}

	@Override protected List<T> page( final Collection<T> result )
	{
		this.pageNumber = Math.min( this.pageNumber, highestPageNumberAllowed( result ) );
		final int skip = ( this.pageNumber - 1 ) * DEFAULT_PAGE_SIZE;
		return result.stream( ).skip( skip ).limit( DEFAULT_PAGE_SIZE ).collect( Collectors.toList( ) );
	}

	private int highestPageNumberAllowed( final Collection<T> result )
	{
		return Math.max( 1, ( int ) Math.ceil( ( double ) result.size( ) / DEFAULT_PAGE_SIZE ) );
	}

	@Override protected boolean hasNextLink( final CollectionModelResult<?> result )
	{
		return this.pageNumber * DEFAULT_PAGE_SIZE < result.getTotalNumberOfResult( );
	}

	@Override protected boolean hasPrevLink( )
	{
		return this.pageNumber > 1;
	}

	@Override protected URI getSelfUri( final UriInfo uriInfo )
	{
		final UriBuilder uriBuilder = createUriBuilder( uriInfo );
		return uriBuilder.build( this.pageNumber );
	}

	@Override protected URI getPrevUri( final UriInfo uriInfo )
	{
		final UriBuilder uriBuilder = createUriBuilder( uriInfo );
		return uriBuilder.build( this.pageNumber - 1 );
	}

	@Override protected URI getNextUri( final UriInfo uriInfo, final CollectionModelResult<?> result )
	{
		final UriBuilder uriBuilder = createUriBuilder( uriInfo );
		return uriBuilder.build( this.pageNumber + 1 );
	}

	private void setPageNumber( final int pageNumber )
	{
		this.pageNumber = Math.max( 1, pageNumber );
	}

	private UriBuilder createUriBuilder( final UriInfo uriInfo )
	{
		return uriInfo.getRequestUriBuilder( )
					  .replaceQueryParam( getPageParamName( ), getQueryParamPageAsTemplate( ) );
	}

	private String getPageParamName( )
	{
		return this.pageQueryParamName;
	}

	private final String getQueryParamPageAsTemplate( )
	{
		return "{" + getPageParamName( ) + "}";
	}

}
