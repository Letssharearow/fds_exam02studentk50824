package de.fhws.fiw.fds.exam02.api;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;
import com.owlike.genson.JsonBindingException;
import de.fhws.fiw.fds.exam02.models.Student;
import de.fhws.fiw.fds.exam02.models.StudentView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class WebApiClientStudent extends AbstractClient<Student>
{

	public WebApiClientStudent()
	{
		super();
	}

	@Override public Collection<Student> deserializeToObjectCollection(Genson genson, String data) throws IOException
	{
		return genson.deserialize(data, new GenericType<Collection<Student>>()
		{
			@Override public Type getType()
			{
				return super.getType();
			}
		});
	}

	@Override public Optional<Student> deserializeToObject(Genson genson, String data) throws IOException
	{
		return Optional.ofNullable(genson.deserialize(data, Student.class));
	}

}
