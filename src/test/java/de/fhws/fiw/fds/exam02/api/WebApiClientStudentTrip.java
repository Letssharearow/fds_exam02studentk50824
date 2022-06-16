package de.fhws.fiw.fds.exam02.api;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;
import com.owlike.genson.JsonBindingException;
import de.fhws.fiw.fds.exam02.models.StudentTripView;
import de.fhws.fiw.fds.exam02.models.StudentView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class WebApiClientStudentTrip extends AbstractClient<StudentTripView>
{

	public WebApiClientStudentTrip()
	{
		super();
	}

	@Override public Collection<StudentTripView> deserializeToObjectCollection(Genson genson, String data)
		throws IOException
	{
		return genson.deserialize(data, new GenericType<Collection<StudentTripView>>()
		{
			@Override public Type getType()
			{
				return super.getType();
			}
		});
	}

	@Override public Optional<StudentTripView> deserializeToObject(Genson genson, String data) throws IOException
	{
		return Optional.ofNullable(genson.deserialize(data, StudentTripView.class));
	}
}
