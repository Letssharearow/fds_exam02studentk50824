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

	private final WebApiClientStudentTrip studentTripClient;
	private final WebApiClientStudent studentClient;

	public WebApiClient()
	{
		super();
		this.studentTripClient = new WebApiClientStudentTrip();
		this.studentClient = new WebApiClientStudent();
	}

	//TODO remove
	@Override void setStudentTripStudentsLink(Map<String, Map<String, String>> bigMap, String json)

	{

	}

	@Override public Collection<StudentTripView> deserializeToObjectCollection(Genson genson, String data)

	{
		return null;
	}

	@Override public Optional<StudentTripView> deserializeToObject(Genson genson, String data)
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
		assert response.body() != null;
		final String data = response.body().string();
		return genson.deserialize(data, new GenericType<List<StudentTripView>>()
		{
		});
	}

	public static Optional<StudentTripView> deserializeToStudentTrip(Genson genson, final Response response)
		throws IOException
	{
		assert response.body() != null;
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

}

