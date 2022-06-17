package de.fhws.fiw.fds.exam02.testClasses;

import de.fhws.fiw.fds.exam02.api.AbstractClient;
import de.fhws.fiw.fds.exam02.api.AbstractWebApiResponse;
import de.fhws.fiw.fds.exam02.api.WebApiClientStudent;
import de.fhws.fiw.fds.exam02.api.WebApiClientStudentTrip;
import de.fhws.fiw.fds.exam02.models.StudentTripView;
import de.fhws.fiw.fds.exam02.models.StudentView;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class TestStudentTripHypermedia
{
	private final String studentURL = "http://localhost:8080/exam02/api/Students";

	public StudentTripView getEmptyStudentTripView()
	{
		Set<Long> ids = new HashSet<>();

		return new StudentTripView("template", 0L, LocalDate.of(1960, 2, 9), LocalDate.of(2022, 3, 10), "partnerUni",
			"city", "country", ids);
	}

	@Test public void testDispatcherLinks()
	{
		final AbstractClient<StudentTripView> client = new WebApiClientStudentTrip();
		AbstractWebApiResponse<StudentTripView> response = null;
		try
		{
			response = client.getDispatcher();
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

	public AbstractWebApiResponse<StudentTripView> getDispatcherState(AbstractClient<StudentTripView> client)
		throws IOException
	{
		return client.getDispatcher();
	}

	public AbstractWebApiResponse<StudentTripView> getGetAllStudenTripsState(AbstractClient<StudentTripView> client)
		throws IOException
	{
		String url = getDispatcherState(client).getLink("getAllStudentTrips");
		return client.loadAllObjectsByUrl(url);
	}

	public AbstractWebApiResponse<StudentTripView> getCreateStudenTripState(AbstractClient<StudentTripView> client,
		StudentTripView studentTripView) throws IOException
	{
		String url = getDispatcherState(client).getLink("createStudentTrip");
		return client.postObject(url, studentTripView);
	}

	public AbstractWebApiResponse<StudentTripView> getGetSingleStudentTripState(AbstractClient<StudentTripView> client)
		throws IOException
	{

		String postUrl = getDispatcherState(client).getLink("createStudentTrip");
		AbstractWebApiResponse<StudentTripView> postResponse = client.postObject(postUrl, getEmptyStudentTripView());
		String getUrl = postResponse.getLink("getStudentTrip");
		AbstractWebApiResponse<StudentTripView> returnValue = client.loadObjectByURL(getUrl);
		client.deleteObject(returnValue.getLink("deleteStudentTrip"));
		return returnValue;
	}

	@Test public void testGetAllStudentTripsHypermeidaGetSingleStudentripLink()
	{
		final AbstractClient<StudentTripView> client = new WebApiClientStudentTrip();
		AbstractWebApiResponse<StudentTripView> response = null;
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
		final AbstractClient<StudentTripView> client = new WebApiClientStudentTrip();
		AbstractWebApiResponse<StudentTripView> response = null;
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
		final AbstractClient<StudentTripView> client = new WebApiClientStudentTrip();
		AbstractWebApiResponse<StudentTripView> response = null;
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
		final AbstractClient<StudentTripView> client = new WebApiClientStudentTrip();
		AbstractWebApiResponse<StudentTripView> response = null;
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
		final AbstractClient<StudentTripView> client = new WebApiClientStudentTrip();
		AbstractWebApiResponse<StudentTripView> response = null;
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
		final AbstractClient<StudentTripView> client = new WebApiClientStudentTrip();
		AbstractWebApiResponse<StudentTripView> response = null;
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
		final AbstractClient<StudentTripView> client = new WebApiClientStudentTrip();
		AbstractWebApiResponse<StudentTripView> response = null;
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
		final AbstractClient<StudentTripView> client = new WebApiClientStudentTrip();
		AbstractWebApiResponse<StudentTripView> response = null;
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
		final AbstractClient<StudentTripView> client = new WebApiClientStudentTrip();
		AbstractWebApiResponse<StudentTripView> response = null;
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

	@Test public void testLinkforStudentsOfStudenTripHypermedia()
	{
		final WebApiClientStudentTrip webApiClientStudentTrip = new WebApiClientStudentTrip();
		WebApiClientStudent webApiClientStudent = new WebApiClientStudent();
		AbstractWebApiResponse<StudentTripView> response = null;

		StudentView studentView = new StudentView("name", "lastName", "course", 4, 5120051, "email");
		StudentTripView studentTripView = getEmptyStudentTripView();
		try
		{
			AbstractWebApiResponse<StudentView> studentPostResponse = webApiClientStudent.postObject(studentURL,
				studentView);
			long studentId = webApiClientStudent.loadObjectByURL(studentPostResponse.getLink("getStudent"))
				.getResponseData().stream().findFirst().get().getId();
			studentTripView.getStudentIds().add(studentId);
			response = getDispatcherState(webApiClientStudentTrip);
			AbstractWebApiResponse<StudentTripView> studentTripPostResponse = webApiClientStudentTrip.postObject(
				response.getLink("createStudentTrip"), studentTripView);

			AbstractWebApiResponse<StudentTripView> studentTripGetResponse = webApiClientStudentTrip.loadObjectByURL(
				studentTripPostResponse.getLink("getStudentTrip"));

			Map<String, Map<String, String>> links = studentTripGetResponse.getLinks();
			Map<String, String> link = links.get("getStudents");
			assertNotNull(link);
			assertFalse(link.isEmpty());
			checkLinkAndType(link, studentTripPostResponse.getLink("self") + "/Students", "application/json");

			deleteStudentView(webApiClientStudent, studentPostResponse);
			deleteStudentTripView(webApiClientStudentTrip, studentTripPostResponse);
		}
		catch (IOException e)
		{
			fail(e.getMessage());
		}

	}

	private void deleteStudentView(AbstractClient<StudentView> client, AbstractWebApiResponse<StudentView> postResponse)
		throws IOException
	{
		AbstractWebApiResponse<StudentView> getStudent = client.loadObjectByURL(postResponse.getLink("getStudent"));
		client.deleteObject(getStudent.getLink("deleteStudent"));
	}

	private void deleteStudentTripView(AbstractClient<StudentTripView> client,
		AbstractWebApiResponse<StudentTripView> postResponse) throws IOException
	{
		AbstractWebApiResponse<StudentTripView> getStudent = client.loadObjectByURL(
			postResponse.getLink("getStudentTrip"));
		client.deleteObject(getStudent.getLink("deleteStudentTrip"));
	}

	public void checkLinkAndType(Map<String, String> link_get, String link, String type)
	{
		assertEquals(link, link_get.get("link"));
		assertEquals(type, link_get.get("type"));
	}

}
