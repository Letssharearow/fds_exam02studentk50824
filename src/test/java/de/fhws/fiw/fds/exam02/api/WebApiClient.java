package de.fhws.fiw.fds.exam02.api;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;
import de.fhws.fiw.fds.exam02.models.StudentTripView;
import okhttp3.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class WebApiClient extends AbstractClient<StudentTripView>
{
	public static final String DISPATCHER_URL = "http://localhost:8080/exam02/api";

	private final OkHttpClient client;

	private final Genson genson;

	private final WebApiClientStudentTrip studentTripClient;
	private final WebApiClientStudent studentClient;

	public WebApiClient()
	{
		this.client = new OkHttpClient();
		this.genson = new Genson();
		this.studentTripClient = new WebApiClientStudentTrip();
		this.studentClient = new WebApiClientStudent();
	}

	//TODO remove
	@Override void setStudenTripStudentsLink(Response response, Map<String, Map<String, String>> bigMap, String json)
		throws IOException
	{

	}

	@Override public Collection<StudentTripView> deserializeToObjectCollection(Genson genson, String data)
		throws IOException
	{
		return null;
	}

	@Override public Optional<StudentTripView> deserializeToObject(Genson genson, String data) throws IOException
	{
		return Optional.empty();
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

	public WebApiClientStudentTrip getStudentTripClient()
	{
		return studentTripClient;
	}

	public WebApiClientStudent getStudentClient()
	{
		return studentClient;
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

			StudentTripView studentTripView = new StudentTripView(generatedString, 0L, LocalDate.parse("2022-05-06"),
				LocalDate.parse("2022-05-06"), generatedString, generatedString, generatedString, null);

		}
	}
}

