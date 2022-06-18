package de.fhws.fiw.fds.exam02.api;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;
import de.fhws.fiw.fds.exam02.models.StudentView;

import java.util.*;

public class WebApiClientStudent extends AbstractClient<StudentView>
{

	public WebApiClientStudent()
	{
		super();
	}

	@Override void setStudentTripStudentsLink(Map<String, Map<String, String>> bigMap, String json)

	{

	}

	@Override public Collection<StudentView> deserializeToObjectCollection(Genson genson, String data)

	{
		return genson.deserialize(data, new GenericType<Collection<StudentView>>()
		{
		});
	}

	@Override public Optional<StudentView> deserializeToObject(Genson genson, String data)
	{
		return Optional.ofNullable(genson.deserialize(data, StudentView.class));
	}

}
