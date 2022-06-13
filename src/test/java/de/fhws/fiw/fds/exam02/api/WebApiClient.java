package de.fhws.fiw.fds.exam02.api;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;
import com.owlike.genson.JsonBindingException;
import de.fhws.fiw.fds.exam02.models.StudentTripView;
import okhttp3.*;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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
		this.states = new WebApiClientStates(this.client, this.genson);
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

	public static void main(String[] args)
	{
		WebApiClient client = new WebApiClient();
		for (int i = 0; i < 100; i++)
		{
			int leftLimit = 97; // letter 'a'
			int rightLimit = 122; // letter 'z'
			int targetStringLength = 10;
			Random random = new Random();

			String generatedString = random.ints(leftLimit, rightLimit + 1).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

			StudentTripView studentTripView = new StudentTripView(generatedString, 0L, null,
				LocalDate.parse("2022-05-06"), LocalDate.parse("2022-05-06"), generatedString, generatedString,
				generatedString);
			try
			{
				client.getStates().postStudentTrip(studentTripView, "http://localhost:8080/exam02/api/StudentTrips");
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}

