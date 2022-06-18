package de.fhws.fiw.fds.exam02.api;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;
import de.fhws.fiw.fds.exam02.models.StudentTripView;

import javax.ws.rs.core.Link;
import java.net.URI;
import java.util.*;

public class WebApiClientStudentTrip extends AbstractClient<StudentTripView>
{

	public WebApiClientStudentTrip()
	{
		super();
	}

	@Override void setStudentTripStudentsLink(Map<String, Map<String, String>> allLinks, String json)

	{
		Optional<StudentTripView> studentTripView = deserializeToObject(this.genson, json);
		if (studentTripView.isPresent())
		{
			Link studentsLink = studentTripView.get().getStudentsLink();
			if (studentsLink != null)
			{
				Map<String, String> map = new HashMap<>();
				map.put("link", studentsLink.getUri().toString());
				map.put("type", studentsLink.getType());
				allLinks.put(studentsLink.getRel(), map);
			}
		}

	}

	@Override public Collection<StudentTripView> deserializeToObjectCollection(Genson genson, String data)
	{
		return genson.deserialize(data, new GenericType<Collection<StudentTripView>>()
		{
		});
	}

	@Override public Optional<StudentTripView> deserializeToObject(Genson genson, String data)
	{
		return Optional.ofNullable(genson.deserialize(data, StudentTripView.class));
	}
}
