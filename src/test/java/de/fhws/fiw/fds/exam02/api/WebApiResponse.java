package de.fhws.fiw.fds.exam02.api;

import de.fhws.fiw.fds.exam02.models.StudentTripView;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class WebApiResponse
{
	private final int lastStatusCode;
	private okhttp3.Response response;
	private Map<String, Map<String, String>> links;

	public WebApiResponse(final int lastStatusCode)
	{
		this(Collections.EMPTY_LIST, lastStatusCode);
	}

	public WebApiResponse(final StudentTripView responseData, final int lastStatusCode)
	{
		this(Optional.of(responseData), lastStatusCode);
	}

	public WebApiResponse(final Optional<StudentTripView> responseData, final int lastStatusCode)
	{
		this(convertToList(responseData), lastStatusCode);
	}

	public WebApiResponse(final Collection<StudentTripView> responseData, final int lastStatusCode)
	{
		this.responseData = responseData;
		this.lastStatusCode = lastStatusCode;
	}

	public WebApiResponse(okhttp3.Response response, int lastStatusCode)
	{
		this(Collections.EMPTY_LIST, lastStatusCode);
		this.response = response;
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

	public String getLink(String operation, String link)
	{
		return links.get(operation).get(link);
	}

	private static Collection<StudentTripView> convertToList(final Optional<StudentTripView> studentTripView)
	{
		return studentTripView.map(Collections::singletonList).orElse(Collections.emptyList());
	}
}