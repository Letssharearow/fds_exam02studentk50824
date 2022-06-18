package de.fhws.fiw.fds.suttondemo.client;

import de.fhws.fiw.fds.suttondemo.models.AbstractModel;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class WebApiResponse<T extends AbstractModel>
{
	private final Collection<T> responseData;

	private final int lastStatusCode;

	private String locationHeader;

	public WebApiResponse(final int lastStatusCode)
	{
		this(Collections.EMPTY_LIST, lastStatusCode);
	}

	public WebApiResponse(final int lastStatusCode, final String locationHeader)
	{
		this(Collections.EMPTY_LIST, lastStatusCode);
		this.locationHeader = locationHeader;
	}

	public WebApiResponse(final T responseData, final int lastStatusCode)
	{
		this(Optional.of(responseData), lastStatusCode);
	}

	public WebApiResponse(final Optional<T> responseData, final int lastStatusCode)
	{
		this(convertToList(responseData), lastStatusCode);
	}

	public WebApiResponse(final Collection<T> responseData, final int lastStatusCode)
	{
		this.responseData = responseData;
		this.lastStatusCode = lastStatusCode;
	}

	public Collection<T> getResponseData()
	{
		return responseData;
	}

	public Optional<T> getFirstResponse()
	{
		return this.responseData.stream().findFirst();
	}

	public int getLastStatusCode()
	{
		return lastStatusCode;
	}

	public String getLocationHeader()
	{
		return locationHeader;
	}

	private static <T> Collection<T> convertToList(final Optional<T> object)
	{
		return object.map(Collections::singletonList).orElse(Collections.emptyList());
	}
}
