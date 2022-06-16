package de.fhws.fiw.fds.exam02.testClasses;

import com.owlike.genson.Genson;
import de.fhws.fiw.fds.exam02.api.*;
import de.fhws.fiw.fds.exam02.api.AbstractClient;
import de.fhws.fiw.fds.exam02.models.Student;
import junit.framework.TestFailure;
import okhttp3.Response;
import org.junit.Test;
import org.junit.internal.runners.statements.Fail;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;

public class TestStudentHypermedia
{

	public Student getEmptyStudent()
	{
		Set<Long> ids = new HashSet<>();

		return null; //TODO;
	}

	@Test public void testDispatcherLinks()
	{
		final AbstractClient<Student> client = new WebApiClientStudent();
		WebApiResponse response = null;
		Map<String, Map<String, String>> links = response.getLinks();

		Map<String, String> link_get = links.get("getAllStudentTrips");
		assertNotNull(link_get);
		assertFalse(link_get.isEmpty());
		checkLinkAndType(link_get, "http://localhost:8080/exam02/api/StudentTrips", "application/json");

		Map<String, String> link_post = links.get("createStudentTrip");
		assertNotNull(link_post);
		assertFalse(link_post.isEmpty());
		checkLinkAndType(link_post, "http://localhost:8080/exam02/api/StudentTrips", "application/json");

		Map<String, String> link_self = links.get("self");
		assertNotNull(link_self);
		assertFalse(link_self.isEmpty());
		assertEquals("http://localhost:8080/exam02/api", link_self.get("link"));
	}

	public AbstractWebApiResponse<Student> getDispatcherState(AbstractClient<Student> client) throws IOException
	{
		return client.getDispatcher();
	}

	public AbstractWebApiResponse<Student> getGetAllStudenTripsState(AbstractClient<Student> client) throws IOException
	{
		String url = getDispatcherState(client).getLink("getAllStudentTrips");
		return client.loadAllObjectsByUrl(url);
	}

	public AbstractWebApiResponse<Student> getCreateStudenTripState(AbstractClient<Student> client,
		Student studentTripView) throws IOException
	{
		String url = getDispatcherState(client).getLink("createStudentTrip");
		return client.postObject(url, studentTripView);
	}

	public AbstractWebApiResponse<Student> getGetSingleStudentTripState(AbstractClient<Student> client)
		throws IOException
	{

		String postUrl = getDispatcherState(client).getLink("createStudentTrip");
		AbstractWebApiResponse<Student> postResponse = client.postObject(postUrl, getEmptyStudent());
		String getUrl = postResponse.getLink("getStudentTrip");
		AbstractWebApiResponse<Student> returnValue = client.loadObjectByURL(getUrl);
		client.deleteObject(returnValue.getLink("deleteStudentTrip"));
		return returnValue;
	}

	@Test public void testGetAllStudentTripsHypermeidaGetSingleStudentripLink()
	{
		final AbstractClient<Student> client = new WebApiClientStudent();
		AbstractWebApiResponse<Student> response = null;
		try
		{
			response = getGetAllStudenTripsState(client);
		}
		catch (IOException e)
		{
			fail(e.getMessage());
		}
		Map<String, Map<String, String>> links = response.getLinks();

		Map<String, String> link = links.get("getStudentTrip");
		assertNotNull(link);
		assertFalse(link.isEmpty());
		checkLinkAndType(link, "http://localhost:8080/exam02/api/StudentTrips/{id}", "application/json");
	}

	@Test public void testGetAllStudentTripsHypermeidaSearchByNameLink()
	{
		final AbstractClient<Student> client = new WebApiClientStudent();
		AbstractWebApiResponse<Student> response = null;
		try
		{
			response = getGetAllStudenTripsState(client);
		}
		catch (IOException e)
		{
			fail(e.getMessage());
		}
		Map<String, Map<String, String>> links = response.getLinks();

		Map<String, String> link = links.get("searchStudentTripByName");
		assertNotNull(link);
		assertFalse(link.isEmpty());
		checkLinkAndType(link, "http://localhost:8080/exam02/api/StudentTrips?name=Name", "application/json");
	}

	@Test public void testGetAllStudentTripsHypermeidaSearchByCityLink()
	{
		final AbstractClient<Student> client = new WebApiClientStudent();
		AbstractWebApiResponse<Student> response = null;
		try
		{
			response = getGetAllStudenTripsState(client);
		}
		catch (IOException e)
		{
			fail(e.getMessage());
		}
		Map<String, Map<String, String>> links = response.getLinks();

		Map<String, String> link = links.get("searchStudentTripByCity");
		assertNotNull(link);
		assertFalse(link.isEmpty());
		checkLinkAndType(link, "http://localhost:8080/exam02/api/StudentTrips?city=City", "application/json");
	}

	@Test public void testGetAllStudentTripsHypermeidaSearchByCountryLink()
	{
		final AbstractClient<Student> client = new WebApiClientStudent();
		AbstractWebApiResponse<Student> response = null;
		try
		{
			response = getGetAllStudenTripsState(client);
		}
		catch (IOException e)
		{
			fail(e.getMessage());

		}
		Map<String, Map<String, String>> links = response.getLinks();

		Map<String, String> link = links.get("searchStudentTripByCountry");
		assertNotNull(link);
		assertFalse(link.isEmpty());
		checkLinkAndType(link, "http://localhost:8080/exam02/api/StudentTrips?country=Country", "application/json");
	}

	@Test public void testGetAllStudentTripsHypermeidaSearchByDateLink()
	{
		final AbstractClient<Student> client = new WebApiClientStudent();
		AbstractWebApiResponse<Student> response = null;
		try
		{
			response = getGetAllStudenTripsState(client);
		}
		catch (IOException e)
		{
			fail(e.getMessage());
		}
		Map<String, Map<String, String>> links = response.getLinks();

		Map<String, String> link = links.get("searchStudentTripByDate");
		assertNotNull(link);
		assertFalse(link.isEmpty());
		checkLinkAndType(link, "http://localhost:8080/exam02/api/StudentTrips?start=1900-01-22&end=2022-12-05",
			"application/json");
	}

	@Test public void testGetSingleStudentTripsHypermeidaDelete()
	{
		final AbstractClient<Student> client = new WebApiClientStudent();
		AbstractWebApiResponse<Student> response = null;
		try
		{
			response = getGetSingleStudentTripState(client);
		}
		catch (IOException e)
		{
			fail(e.getMessage());
		}
		Map<String, Map<String, String>> links = response.getLinks();

		Map<String, String> link = links.get("deleteStudentTrip");
		assertNotNull(link);
		assertFalse(link.isEmpty());
		checkLinkAndType(link,
			"http://localhost:8080/exam02/api/StudentTrips/" + response.getResponseData().stream().findFirst().get()
				.getId(), "application/json");
	}

	@Test public void testGetSingleStudentTripsHypermeidaPut()
	{
		final AbstractClient<Student> client = new WebApiClientStudent();
		AbstractWebApiResponse<Student> response = null;
		try
		{
			response = getGetSingleStudentTripState(client);
		}
		catch (IOException e)
		{
			fail(e.getMessage());
		}
		Map<String, Map<String, String>> links = response.getLinks();

		Map<String, String> link = links.get("updateStudentTrip");
		assertNotNull(link);
		assertFalse(link.isEmpty());
		checkLinkAndType(link,
			"http://localhost:8080/exam02/api/StudentTrips/" + response.getResponseData().stream().findFirst().get()
				.getId(), "application/json");
	}

	@Test public void testGetSingleStudentTripsHypermeidaSelf()
	{
		final AbstractClient<Student> client = new WebApiClientStudent();
		AbstractWebApiResponse<Student> response = null;
		try
		{
			response = getGetSingleStudentTripState(client);
		}
		catch (IOException e)
		{
			fail(e.getMessage());
		}
		Map<String, Map<String, String>> links = response.getLinks();

		Map<String, String> link = links.get("self");
		assertNotNull(link);
		assertFalse(link.isEmpty());
		assertEquals(
			"http://localhost:8080/exam02/api/StudentTrips/" + response.getResponseData().stream().findFirst().get()
				.getId(), link.get("link"));
	}

	@Test public void testGetSingleStudentTripsHypermeidaGetAllStudentTrips()
	{
		final AbstractClient<Student> client = new WebApiClientStudent();
		AbstractWebApiResponse<Student> response = null;
		try
		{
			response = getGetSingleStudentTripState(client);
		}
		catch (IOException e)
		{
			fail(e.getMessage());
		}
		Map<String, Map<String, String>> links = response.getLinks();

		Map<String, String> link = links.get("getAllStudentTrips");
		assertNotNull(link);
		assertFalse(link.isEmpty());
		checkLinkAndType(link, "http://localhost:8080/exam02/api/StudentTrips", "application/json");
	}

	public void checkLinkAndType(Map<String, String> link_get, String link, String type)
	{
		assertEquals(link, link_get.get("link"));
		assertEquals(type, link_get.get("type"));
	}

}
