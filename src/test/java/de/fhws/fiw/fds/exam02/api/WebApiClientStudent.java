package de.fhws.fiw.fds.exam02.api;

import com.owlike.genson.Genson;
import com.owlike.genson.JsonBindingException;
import de.fhws.fiw.fds.exam02.models.StudentView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class WebApiClientStudent extends AbstractClient<StudentView>
{
	private OkHttpClient client;

	private Genson genson;

	public WebApiClientStudent(OkHttpClient client, Genson genson)
	{
		this.client = client;
		this.genson = genson;
	}

	public WebApiResponse loadStudentByURL(String url, long studentId) throws IOException
	{
		final Response response = sendGetRequest(combineUrlAndId(url, studentId));
		try
		{
			return new WebApiResponse(deserializeToStudent(genson, response), response.code(),
				getHypermediaLinks(response));
		}
		catch (JsonBindingException e)
		{
			return new WebApiResponse(response.code(), getHypermediaLinks(response));
		}
	}

	public WebApiResponse loadStudentByURL(String url) throws IOException
	{
		final Response response = sendGetRequest(url);
		try
		{
			return new WebApiResponse(deserializeToStudent(genson, response), response.code(),
				getHypermediaLinks(response));
		}
		catch (JsonBindingException e)
		{
			return new WebApiResponse(response.code(), getHypermediaLinks(response));
		}
	}

	public WebApiResponse loadAllStudentsByUrl(String url) throws IOException
	{
		final Response response = sendGetRequest(url);
		Map<String, Map<String, String>> hypermediaLinks = getHypermediaLinks(response);
		try

		{
			return new WebApiResponse(deserializeToStudentCollection(genson, response), response.code(),
				hypermediaLinks);
		}
		catch (JsonBindingException e)
		{
			return new WebApiResponse(response.code(), hypermediaLinks);
		}
	}

	public WebApiResponse postStudent(String url, StudentView student) throws IOException
	{
		final Response response = sendPostRequest(url, student);
		return new WebApiResponse(response.code(), getHypermediaLinks(response));
	}

	public WebApiResponse putStudent(StudentView student, long studentId, String url) throws IOException
	{
		final Response response = sendPutRequest(student, studentId, url);
		return new WebApiResponse(response.code(), getHypermediaLinks(response));
	}

	public WebApiResponse deleteStudent(String url, long studentId) throws IOException
	{
		final Response response = sendDeleteRequest(combineUrlAndId(url, studentId));
		return new WebApiResponse(response.code(), getHypermediaLinks(response));
	}

	public WebApiResponse deleteStudent(String url) throws IOException
	{
		final Response response = sendDeleteRequest(url);
		return new WebApiResponse(response.code(), getHypermediaLinks(response));
	}

	public WebApiResponse deleteStudentByURL(String location) throws IOException
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

	private Response sendPutRequest(final StudentView studentView, long studentId, final String url) throws IOException
	{
		final Request request = new Request.Builder().url(combineUrlAndId(url, studentId))
			.put(WebApiClient.getStudentRequestBody(genson, studentView)).build();
		return this.client.newCall(request).execute();
	}

	private Response sendPostRequest(String url, final StudentView studentViewstudent) throws IOException
	{
		final Request request = new Request.Builder().url(url)
			.post(WebApiClient.getStudentRequestBody(genson, studentViewstudent)).build();
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

	@Override public Collection<StudentView> deserializeToObjectCollection(Genson genson, Response response)
	{
		return null;
	}

	@Override public Optional<StudentView> deserializeToObject(Genson genson, Response response)
	{
		return Optional.empty();
	}
}
