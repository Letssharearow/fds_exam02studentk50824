package de.fhws.fiw.fds.exam02.api;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;
import com.owlike.genson.JsonBindingException;
import de.fhws.fiw.fds.exam02.models.Student;
import de.fhws.fiw.fds.exam02.models.StudentTripView;
import de.fhws.fiw.fds.exam02.models.StudentView;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
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

	public AbstractWebApiResponse<T> loadObjectByURLAndId(String url, long id) throws IOException
	{
		final Response response = sendGetRequest(combineUrlAndId(url, id));
		try
		{
			return new AbstractWebApiResponse<T>(deserializeToObject(genson, response.body().string()), response.code(),
				getHypermediaLinks(response));
		}
		catch (JsonBindingException e)
		{
			return new AbstractWebApiResponse<T>(response.code(), getHypermediaLinks(response));
		}
	}

	public AbstractWebApiResponse<T> loadObjectByURL(String url) throws IOException
	{
		final Response response = sendGetRequest(url);
		try
		{
			return new AbstractWebApiResponse<T>(deserializeToObject(genson, response.body().string()), response.code(),
				getHypermediaLinks(response));
		}
		catch (JsonBindingException e)
		{
			return new AbstractWebApiResponse<T>(response.code(), getHypermediaLinks(response));
		}
	}

	public AbstractWebApiResponse<T> loadAllObjectsByUrl(String url) throws IOException
	{
		final Response response = sendGetRequest(url);
		Map<String, Map<String, String>> hypermediaLinks = getHypermediaLinks(response);
		try

		{
			return new AbstractWebApiResponse<T>(deserializeToObjectCollection(genson, response.body().string()),
				response.code(), hypermediaLinks);
		}
		catch (JsonBindingException e)
		{
			return new AbstractWebApiResponse<T>(response.code(), hypermediaLinks);
		}
	}

	public AbstractWebApiResponse<T> postObject(String url, T object) throws IOException
	{
		final Response response = sendPostRequest(url, object);
		return new AbstractWebApiResponse<T>(response.code(), getHypermediaLinks(response));
	}

	public AbstractWebApiResponse<T> putObject(T object, long objectId, String url) throws IOException
	{
		final Response response = sendPutRequest(object, objectId, url);
		return new AbstractWebApiResponse<T>(response.code(), getHypermediaLinks(response));
	}

	public AbstractWebApiResponse<T> deleteObject(String url, long objectId) throws IOException
	{
		final Response response = sendDeleteRequest(combineUrlAndId(url, objectId));
		return new AbstractWebApiResponse<T>(response.code(), getHypermediaLinks(response));
	}

	public AbstractWebApiResponse<T> deleteObject(String url) throws IOException
	{
		final Response response = sendDeleteRequest(url);
		return new AbstractWebApiResponse<T>(response.code(), getHypermediaLinks(response));
	}

	public AbstractWebApiResponse<T> deleteObjectByURL(String location) throws IOException
	{
		final Response response = sendDeleteRequestByURL(location);
		return new AbstractWebApiResponse<T>(response.code(), getHypermediaLinks(response));
	}

	public AbstractWebApiResponse<T> getDispatcher() throws IOException
	{
		Response response = sendGetRequest(WebApiClient.DISPATCHER_URL);
		return new AbstractWebApiResponse<T>(response.code(), getHypermediaLinks(response));
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

	public abstract Collection<T> deserializeToObjectCollection(Genson genson, final String data) throws IOException;

	public abstract Optional<T> deserializeToObject(Genson genson, final String data) throws IOException;

	public static void main(String[] args)
	{
		AbstractClient<StudentTripView> studentTripViewAbstractClient = new WebApiClientStudentTrip();
		String data = "{\"id\":1}";
		StudentTripView deserialize = studentTripViewAbstractClient.genson.deserialize(data,
			new GenericType<StudentTripView>()
			{
				@Override public Type getType()
				{
					return super.getType();
				}
			});
		try
		{
			studentTripViewAbstractClient.deserializeToObjectCollection(studentTripViewAbstractClient.genson,
				"[\n" + "    {\n" + "        \"city\": \"city\",\n" + "        \"country\": \"country\",\n"
					+ "        \"end\": \"1960-02-09\",\n" + "        \"id\": 1,\n" + "        \"name\": \"Felix\",\n"
					+ "        \"partnerUniversity\": \"partnerUni\",\n" + "        \"start\": \"1960-02-09\",\n"
					+ "        \"studentIds\": []\n" + "    }\n" + "]");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		try
		{
			Optional<StudentTripView> studentTripView = studentTripViewAbstractClient.deserializeToObject(
				studentTripViewAbstractClient.genson, data);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		int i = 1;
	}
}
