package de.fhws.fiw.fds.exam02.api;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;
import com.owlike.genson.JsonBindingException;
import de.fhws.fiw.fds.exam02.models.StudentTripView;
import okhttp3.*;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class WebApiClient
{
	private static final String URL = "http://localhost:8080/exam02/api/StudentTrips";
	public static final String DISPATCHER_URL = "http://localhost:8080/exam02/api"; //TODO: better URL placement

	private final OkHttpClient client;

	private final Genson genson;

	private final WebApiClientStates states;

	public WebApiClient()
	{
		this.client = new OkHttpClient();
		this.genson = new Genson();
		this.states = new WebApiClientStates(URL, this.client, this.genson);
	}

	public static RequestBody getStudentTripRequestBody(Genson genson, StudentTripView studentTripView)
	{
		String studentTripJSON = genson.serialize(studentTripView);
		return RequestBody.create(MediaType.parse("application/json"), studentTripJSON);
	}

	public static Collection<StudentTripView> deserializeToStudentTripCollection(Genson genson, final Response response)
		throws IOException
	{
		final String data = response.body().string();
		return genson.deserialize(data, new GenericType<List<StudentTripView>>()
		{
		});
	}

	public static Optional<StudentTripView> deserializeToStudentTrip(Genson genson, final Response response)
		throws IOException
	{
		final String data = response.body().string();
		return Optional.ofNullable(genson.deserialize(data, StudentTripView.class));
	}

	public WebApiClientStates getStates()
	{
		return states;
	}
}

