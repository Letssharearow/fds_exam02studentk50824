package de.fhws.fiw.fds.exam02.api;

import com.owlike.genson.Genson;
import com.owlike.genson.JsonBindingException;
import de.fhws.fiw.fds.exam02.models.StudentTripView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class WebApiClientStates
{
	private String url = "http://localhost:8080/demo/examples/studentTrips";

	private OkHttpClient client;

	private Genson genson;

	public WebApiClientStates(String url, OkHttpClient client, Genson genson)
	{
		this.client = client;
		this.genson = genson;
		this.url = url;
	}

	public WebApiResponse loadStudentTripById(final long id) throws IOException
	{
		final String theUrl = String.format("%s/%d", url, id);
		return loadStudentTripByURL(theUrl);
	}

	public WebApiResponse loadStudentTripByURL(String url) throws IOException
	{
		final Response response = sendGetRequest(url);
		try
		{
			return new WebApiResponse(WebApiClient.deserializeToStudentTrip(genson, response), response.code());
		}
		catch (JsonBindingException e)
		{
			return new WebApiResponse(response.code());
		}
	}

	public WebApiResponse loadAllStudentTrips() throws IOException
	{
		return loadAllStudentTripsByNameTypeAndSemester("", "", "", "");
	}

	public WebApiResponse loadAllStudentTripsByNameTypeAndSemester(final String name, final String time,
		final String country, final String city) throws IOException
	{
		final String theUrl = String.format("%s?name=%s&time=%s&country=%s&city=%s", url, name, time, country, city);
		final Response response = sendGetRequest(theUrl);
		try
		{
			return new WebApiResponse(WebApiClient.deserializeToStudentTripCollection(genson, response),
				response.code());
		}
		catch (JsonBindingException e)
		{
			return new WebApiResponse(response.code());
		}
	}

	public WebApiResponse postStudentTrip(StudentTripView studentTrip) throws IOException
	{
		final Response response = sendPostRequest(studentTrip);
		return new WebApiResponse(response, response.code());
	}

	public WebApiResponse putStudentTrip(StudentTripView studentTrip, long studentTripId) throws IOException
	{
		final Response response = sendPutRequest(studentTrip, studentTripId);
		return new WebApiResponse(response.code());
	}

	public WebApiResponse deleteStudentTrip(long studentTripId) throws IOException
	{
		final Response response = sendDeleteRequest(studentTripId);
		return new WebApiResponse(response.code());
	}

	public WebApiResponse deleteStudentTripByURL(String location) throws IOException
	{
		final Response response = sendDeleteRequestByURL(location);
		return new WebApiResponse(response.code());
	}

	private Response sendGetRequest(final String url) throws IOException
	{
		final Request request = new Request.Builder().url(url).get().build();
		return this.client.newCall(request).execute();
	}

	private Response sendPutRequest(final StudentTripView studentTripView, final long studentTripId) throws IOException
	{
		String url = getURLFromId(studentTripId);
		final Request request = new Request.Builder().url(url)
			.put(WebApiClient.getStudentTripRequestBody(genson, studentTripView)).build();
		return this.client.newCall(request).execute();
	}

	private Response sendPostRequest(final StudentTripView studentTripViewstudentTrip) throws IOException
	{
		final Request request = new Request.Builder().url(url)
			.post(WebApiClient.getStudentTripRequestBody(genson, studentTripViewstudentTrip)).build();
		return this.client.newCall(request).execute();
	}

	private Response sendDeleteRequest(final long id) throws IOException
	{
		String url = getURLFromId(id);
		return sendDeleteRequestByURL(url);
	}

	private Response sendDeleteRequestByURL(final String url) throws IOException
	{
		final Request request = new Request.Builder().url(url).delete().build();
		return this.client.newCall(request).execute();
	}

	private String getURLFromId(final long studentTripId)
	{
		return url + "/" + studentTripId;
	}
}
