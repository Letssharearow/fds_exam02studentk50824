package de.fhws.fiw.fds.exam02.api;

import de.fhws.fiw.fds.exam02.models.StudentTripView;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class WebApiResponse
{
	private final int lastStatusCode;
	private final Collection<StudentTripView> responseData;
	private final Map<String, Map<String, String>> links;
	private final long id;

	public WebApiResponse(final int lastStatusCode, Map<String, Map<String, String>> links)
	{
		this(Collections.EMPTY_LIST, lastStatusCode, links, -1);
	}

	public WebApiResponse(final StudentTripView responseData, final int lastStatusCode,
		Map<String, Map<String, String>> links)
	{
		this(Optional.of(responseData), lastStatusCode, links);
	}

	public WebApiResponse(final int lastStatusCode, Map<String, Map<String, String>> links, long id)
	{
		this(Collections.EMPTY_LIST, lastStatusCode, links, id);
	}

	public WebApiResponse(final StudentTripView responseData, final int lastStatusCode,
		Map<String, Map<String, String>> links, long id)
	{
		this(Optional.of(responseData), lastStatusCode, links);
	}

	public WebApiResponse(final Optional<StudentTripView> responseData, final int lastStatusCode,
		Map<String, Map<String, String>> links)
	{
		this(convertToList(responseData), lastStatusCode, links, -1);
	}

	public WebApiResponse(final Collection<StudentTripView> responseData, final int lastStatusCode,
		Map<String, Map<String, String>> links)
	{
		this(responseData, lastStatusCode, links, -1);
	}

	public WebApiResponse(final Collection<StudentTripView> responseData, final int lastStatusCode,
		Map<String, Map<String, String>> links, long id)
	{
		this.responseData = responseData;
		this.lastStatusCode = lastStatusCode;
		this.links = links;
		this.id = id;
	}

	public Collection<StudentTripView> getResponseData()
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

	private static Collection<StudentTripView> convertToList(final Optional<StudentTripView> studentTripView)
	{
		return studentTripView.map(Collections::singletonList).orElse(Collections.emptyList());
	}
}