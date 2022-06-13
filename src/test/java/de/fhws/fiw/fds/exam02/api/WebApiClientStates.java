package de.fhws.fiw.fds.exam02.api;

import com.owlike.genson.Genson;
import com.owlike.genson.JsonBindingException;
import de.fhws.fiw.fds.exam02.models.StudentTripView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class WebApiClientStates
{
	private OkHttpClient client;

	private Genson genson;

	public WebApiClientStates(OkHttpClient client, Genson genson)
	{
		this.client = client;
		this.genson = genson;
	}

	public WebApiResponse loadStudentTripByURL(String url, long studentTripId) throws IOException
	{
		final Response response = sendGetRequest(combineUrlAndId(url, studentTripId));
		try
		{
			return new WebApiResponse(WebApiClient.deserializeToStudentTrip(genson, response), response.code(),
				getHypermediaLinks(response));
		}
		catch (JsonBindingException e)
		{
			return new WebApiResponse(response.code(), getHypermediaLinks(response));
		}
	}

	public WebApiResponse loadAllStudentTripsByUrl(String url) throws IOException
	{
		final Response response = sendGetRequest(url);
		Map<String, Map<String, String>> hypermediaLinks = getHypermediaLinks(response);
		try

		{
			return new WebApiResponse(WebApiClient.deserializeToStudentTripCollection(genson, response),
				response.code(), hypermediaLinks);
		}
		catch (JsonBindingException e)
		{
			return new WebApiResponse(response.code(), hypermediaLinks);
		}
	}

	public WebApiResponse postStudentTrip(StudentTripView studentTrip, String url) throws IOException
	{
		final Response response = sendPostRequest(studentTrip, url);
		return new WebApiResponse(response.code(), getHypermediaLinks(response));
	}

	public WebApiResponse putStudentTrip(StudentTripView studentTrip, long studentTripId, String url) throws IOException
	{
		final Response response = sendPutRequest(studentTrip, studentTripId, url);
		return new WebApiResponse(response.code(), getHypermediaLinks(response));
	}

	public WebApiResponse deleteStudentTrip(long studentTripId, String url) throws IOException
	{
		final Response response = sendDeleteRequest(studentTripId, url);
		return new WebApiResponse(response.code(), getHypermediaLinks(response));
	}

	public WebApiResponse deleteStudentTripByURL(String location) throws IOException
	{
		final Response response = sendDeleteRequestByURL(location);
		return new WebApiResponse(response.code(), getHypermediaLinks(response));
	}

	public WebApiResponse getDispatcher() throws IOException
	{
		Response response = sendGetRequest(WebApiClient.DISPATCHER_URL);
		return new WebApiResponse(response.code(), getHypermediaLinks(response));
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

	private Response sendPutRequest(final StudentTripView studentTripView, long studentTripId, final String url)
		throws IOException
	{
		final Request request = new Request.Builder().url(combineUrlAndId(url, studentTripId))
			.put(WebApiClient.getStudentTripRequestBody(genson, studentTripView)).build();
		return this.client.newCall(request).execute();
	}

	private Response sendPostRequest(final StudentTripView studentTripViewstudentTrip, String url) throws IOException
	{
		final Request request = new Request.Builder().url(url)
			.post(WebApiClient.getStudentTripRequestBody(genson, studentTripViewstudentTrip)).build();
		return this.client.newCall(request).execute();
	}

	private Response sendDeleteRequest(final long id, String url) throws IOException
	{
		return sendDeleteRequestByURL(combineUrlAndId(url, id));
	}

	private Response sendDeleteRequestByURL(final String url) throws IOException
	{
		final Request request = new Request.Builder().url(url).delete().build();
		return this.client.newCall(request).execute();
	}
}
