package de.fhws.fiw.fds.exam02.api.query;

import de.fhws.fiw.fds.exam02.models.StudentTrip;
import de.fhws.fiw.fds.sutton.server.api.queries.PagingBehavior;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class PagingPageParameter extends PagingBehavior<StudentTrip>
{
	private static final int DEFAULT_PAGE_SIZE = 10;
	private int pageNumber;
	private final String path;

	public PagingPageParameter(int pageNumber, String path)
	{
		this.pageNumber = pageNumber;
		this.path = path;
	}

	@Override protected List<StudentTrip> page(Collection<StudentTrip> result)
	{
		final int firstIndex = (Math.max(1, pageNumber) - 1) * DEFAULT_PAGE_SIZE;
		return result.stream().skip(firstIndex).limit(DEFAULT_PAGE_SIZE).collect(Collectors.toList());
	}

	@Override protected boolean hasNextLink(CollectionModelResult<?> result)
	{
		final int maxValuesAmount = pageNumber * DEFAULT_PAGE_SIZE;
		return result.getTotalNumberOfResult() > maxValuesAmount;
	}

	@Override protected boolean hasPrevLink()
	{
		return pageNumber > 1;
	}

	@Override protected URI getSelfUri(UriInfo uriInfo)
	{
		return uriInfo.getBaseUriBuilder().path(path).queryParam("page", pageNumber).build();
	}

	@Override protected URI getPrevUri(UriInfo uriInfo)
	{
		return uriInfo.getBaseUriBuilder().path(path).queryParam("page", pageNumber - 1).build();
	}

	@Override protected URI getNextUri(UriInfo uriInfo, CollectionModelResult<?> result)
	{
		return uriInfo.getBaseUriBuilder().path(path).queryParam("page", pageNumber + 1).build();
	}
}
