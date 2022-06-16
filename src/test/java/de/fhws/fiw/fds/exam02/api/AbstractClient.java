package de.fhws.fiw.fds.exam02.api;

import com.owlike.genson.Genson;
import com.owlike.genson.JsonBindingException;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;
import okhttp3.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractClient<T extends AbstractModel>
{
	public static final String DISPATCHER_URL = "http://localhost:8080/exam02/api";

	private final OkHttpClient client;

	private final Genson genson;

	public AbstractClient()
	{
		this.client = new OkHttpClient();
		this.genson = new Genson();
	}

	public AbstractWebApiResponse loadObjectByURL(String url, long id) throws IOException
	{
		final Response response = sendGetRequest(combineUrlAndId(url, id));
		try
		{
			return new AbstractWebApiResponse<T>(deserializeToObject(genson, response), response.code(),
				getHypermediaLinks(response));
		}
		catch (JsonBindingException e)
		{
			return new AbstractWebApiResponse(response.code(), getHypermediaLinks(response));
		}
	}

	public AbstractWebApiResponse loadObjectByURL(String url) throws IOException
	{
		final Response response = sendGetRequest(url);
		try
		{
			return new AbstractWebApiResponse(deserializeToObject(genson, response), response.code(),
				getHypermediaLinks(response));
		}
		catch (JsonBindingException e)
		{
			return new AbstractWebApiResponse(response.code(), getHypermediaLinks(response));
		}
	}

	public AbstractWebApiResponse loadAllObjectsByUrl(String url) throws IOException
	{
		final Response response = sendGetRequest(url);
		Map<String, Map<String, String>> hypermediaLinks = getHypermediaLinks(response);
		try

		{
			return new AbstractWebApiResponse(deserializeToObjectCollection(genson, response), response.code(),
				hypermediaLinks);
		}
		catch (JsonBindingException e)
		{
			return new AbstractWebApiResponse(response.code(), hypermediaLinks);
		}
	}

	public AbstractWebApiResponse postObject(String url, T object) throws IOException
	{
		final Response response = sendPostRequest(url, object);
		return new AbstractWebApiResponse(response.code(), getHypermediaLinks(response));
	}

	public AbstractWebApiResponse putObject(T object, long objectId, String url) throws IOException
	{
		final Response response = sendPutRequest(object, objectId, url);
		return new AbstractWebApiResponse(response.code(), getHypermediaLinks(response));
	}

	public AbstractWebApiResponse deleteObject(String url, long objectId) throws IOException
	{
		final Response response = sendDeleteRequest(combineUrlAndId(url, objectId));
		return new AbstractWebApiResponse(response.code(), getHypermediaLinks(response));
	}

	public AbstractWebApiResponse deleteObject(String url) throws IOException
	{
		final Response response = sendDeleteRequest(url);
		return new AbstractWebApiResponse(response.code(), getHypermediaLinks(response));
	}

	public AbstractWebApiResponse deleteObjectByURL(String location) throws IOException
	{
		final Response response = sendDeleteRequestByURL(location);
		return new AbstractWebApiResponse(response.code(), getHypermediaLinks(response));
	}

	public AbstractWebApiResponse getDispatcher() throws IOException
	{
		Response response = sendGetRequest(WebApiClient.DISPATCHER_URL);
		return new AbstractWebApiResponse(response.code(), getHypermediaLinks(response));
	}

	private Map<String, Map<String, String>> getHypermediaLinks(Response response)
	{
		List<String> headers = response.headers("Link");
		List<String[]> collect = headers.stream().map(header -> (header.split(";"))).collect(Collectors.toList());

		Map<String, Map<String, String>> bigMap = new HashMap<>();
		for (String[] strings : collect)
		{
			Map<String, String> map = new HashMap<>();
			map.put("link", strings[0].replaceAll("<|>", ""));
			String[] split = strings[1].replaceAll("\"", "").split("=");
			bigMap.put(split[1], map);

			if (strings.length > 2)
			{
				split = strings[2].replaceAll("\"", "").split("=");
				map.put(split[0], split[1]);
			}
		}
		return bigMap;
	}

	private String combineUrlAndId(String url, long id)
	{
		return url.replaceAll("\\{id}", "" + id);
	}

	private Response sendGetRequest(final String url) throws IOException
	{
		final Request request = new Request.Builder().url(url).get().build();
		return this.client.newCall(request).execute();
	}

	private Response sendPutRequest(final T objectView, long objectId, final String url) throws IOException
	{
		final Request request = new Request.Builder().url(combineUrlAndId(url, objectId))
			.put(getObjectRequestBody(genson, objectView)).build();
		return this.client.newCall(request).execute();
	}

	private Response sendPostRequest(String url, final T objectView) throws IOException
	{
		final Request request = new Request.Builder().url(url).post(getObjectRequestBody(genson, objectView)).build();
		return this.client.newCall(request).execute();
	}

	private Response sendDeleteRequest(String url) throws IOException
	{
		return sendDeleteRequestByURL(url);
	}

	private Response sendDeleteRequestByURL(final String url) throws IOException
	{
		final Request request = new Request.Builder().url(url).delete().build();
		return this.client.newCall(request).execute();
	}

	public RequestBody getObjectRequestBody(Genson genson, T objectView)
	{
		String studentTripJSON = genson.serialize(objectView);
		return RequestBody.create(MediaType.parse("application/json"), studentTripJSON);
	}

	public abstract Collection<T> deserializeToObjectCollection(Genson genson, final Response response);

	public abstract Optional<T> deserializeToObject(Genson genson, final Response response);
}
