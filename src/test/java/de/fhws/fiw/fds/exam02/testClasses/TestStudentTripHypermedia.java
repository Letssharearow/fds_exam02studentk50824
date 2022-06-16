package de.fhws.fiw.fds.exam02.testClasses;

import de.fhws.fiw.fds.exam02.api.WebApiClient;
import de.fhws.fiw.fds.exam02.api.WebApiResponse;
import de.fhws.fiw.fds.exam02.models.StudentTripView;
import junit.framework.TestFailure;
import org.junit.Test;
import org.junit.internal.runners.statements.Fail;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class TestStudentTripHypermedia
{

	public StudentTripView getEmptyStudentTripView()
	{
		Set<Long> ids = new HashSet<>();

		return new StudentTripView("template", 0L, LocalDate.of(1960, 2, 9), LocalDate.of(1861, 3, 10), "partnerUni",
			"city", "country", ids);
	}

	@Test public void testDispatcherLinks()
	{
		final WebApiClient client = new WebApiClient();
		WebApiResponse response = null;
		try
		{
			response = client.getStates().getDispatcher();
		}
		catch (IOException e)
		{
			fail(e.getMessage());
		}
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

	public WebApiResponse getDispatcherState(WebApiClient client) throws IOException
	{
		return client.getStates().getDispatcher();
	}

	public WebApiResponse getGetAllStudenTripsState(WebApiClient client) throws IOException
	{
		String url = getDispatcherState(client).getLink("getAllStudentTrips");
		return client.getStates().loadAllStudentTripsByUrl(url);
	}

	public WebApiResponse getCreateStudenTripState(WebApiClient client, StudentTripView studentTripView)
		throws IOException
	{
		String url = getDispatcherState(client).getLink("createStudentTrip");
		return client.getStates().postStudentTrip(url, studentTripView);
	}

	public WebApiResponse getGetSingleStudentTripState(WebApiClient client) throws IOException
	{

		String postUrl = getDispatcherState(client).getLink("createStudentTrip");
		WebApiResponse postResponse = client.getStates().postStudentTrip(postUrl, getEmptyStudentTripView());
		String getUrl = postResponse.getLink("getStudentTrip");
		WebApiResponse returnValue = client.getStates().loadStudentTripByURL(getUrl);
		client.getStates().deleteStudentTrip(returnValue.getLink("deleteStudentTrip"));
		return returnValue;
	}

	@Test public void testGetAllStudentTripsHypermeidaGetSingleStudentripLink()
	{
		final WebApiClient client = new WebApiClient();
		WebApiResponse response = null;
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
		final WebApiClient client = new WebApiClient();
		WebApiResponse response = null;
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
		final WebApiClient client = new WebApiClient();
		WebApiResponse response = null;
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
		final WebApiClient client = new WebApiClient();
		WebApiResponse response = null;
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
		final WebApiClient client = new WebApiClient();
		WebApiResponse response = null;
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
		final WebApiClient client = new WebApiClient();
		WebApiResponse response = null;
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
		final WebApiClient client = new WebApiClient();
		WebApiResponse response = null;
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
		final WebApiClient client = new WebApiClient();
		WebApiResponse response = null;
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
		final WebApiClient client = new WebApiClient();
		WebApiResponse response = null;
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
