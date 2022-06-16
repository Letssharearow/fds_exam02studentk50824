package de.fhws.fiw.fds.exam02.api;

import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class AbstractWebApiResponse<T extends AbstractModel>
{
	private final int lastStatusCode;
	private final Collection<T> responseData;
	private final Map<String, Map<String, String>> links;
	private final long id;

	public AbstractWebApiResponse(final int lastStatusCode, Map<String, Map<String, String>> links)
	{
		this(Collections.EMPTY_LIST, lastStatusCode, links, -1);
	}

	public AbstractWebApiResponse(final T responseData, final int lastStatusCode,
		Map<String, Map<String, String>> links)
	{
		this(Optional.of(responseData), lastStatusCode, links);
	}

	public AbstractWebApiResponse(final int lastStatusCode, Map<String, Map<String, String>> links, long id)
	{
		this(Collections.EMPTY_LIST, lastStatusCode, links, id);
	}

	public AbstractWebApiResponse(final T responseData, final int lastStatusCode,
		Map<String, Map<String, String>> links, long id)
	{
		this(Optional.of(responseData), lastStatusCode, links);
	}

	public AbstractWebApiResponse(final Optional<T> responseData, final int lastStatusCode,
		Map<String, Map<String, String>> links)
	{
		this(responseData, lastStatusCode, links, -1);
	}

	public AbstractWebApiResponse(final Collection<T> responseData, final int lastStatusCode,
		Map<String, Map<String, String>> links)
	{
		this(responseData, lastStatusCode, links, -1);
	}

	public AbstractWebApiResponse(final Collection<T> responseData, final int lastStatusCode,
		Map<String, Map<String, String>> links, long id)
	{
		this.responseData = responseData;
		this.lastStatusCode = lastStatusCode;
		this.links = links;
		this.id = id;
	}

	public AbstractWebApiResponse(final Optional<T> responseData, final int lastStatusCode,
		Map<String, Map<String, String>> links, long id)
	{
		this.responseData = convertToList(responseData);
		this.lastStatusCode = lastStatusCode;
		this.links = links;
		this.id = id;
	}

	public Collection<T> getResponseData()
	{
		return responseData;
	}

	public int getLastStatusCode()
	{
		return lastStatusCode;
	}

	public Map<String, Map<String, String>> getLinks()
	{
		return links;
	}

	public String getLink(String operation)
	{
		return links.get(operation).get("link");
	}

	public long getId()
	{
		return this.id;
	}

	private Collection<T> convertToList(final Optional<T> objectView)
	{
		return objectView.map(Collections::singletonList).orElse(Collections.emptyList());
	}
}