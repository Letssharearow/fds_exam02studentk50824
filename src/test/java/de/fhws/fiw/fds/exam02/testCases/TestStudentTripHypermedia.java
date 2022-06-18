package de.fhws.fiw.fds.exam02.testCases;

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
import java.util.*;

import static org.junit.Assert.*;

public class TestStudentTripHypermedia
{

	public StudentTripView getEmptyStudentTripView()
	{
		Set<Long> ids = new HashSet<>();

		return new StudentTripView("template", 0L, LocalDate.of(1960, 2, 9), LocalDate.of(2022, 3, 10), "partnerUni",
			"city", "country", ids);
	}

	@Test public void testDispatcherLinks()
	{
		final AbstractClient<StudentTripView> client = new WebApiClientStudentTrip();
		AbstractWebApiResponse<StudentTripView> response;
		response = getDispatcherState(client);
		checkHypermediaLinks(response, "getAllStudentTrips", "http://localhost:8080/exam02/api/StudentTrips",
			"application/json");
		checkHypermediaLinks(response, "createStudentTrip", "http://localhost:8080/exam02/api/StudentTrips",
			"application/json");
		checkHypermediaLinks(response, "self", "http://localhost:8080/exam02/api", null);
	}

	public AbstractWebApiResponse<StudentTripView> getDispatcherState(AbstractClient<StudentTripView> client)
	{
		try
		{
			return client.getDispatcher();
		}
		catch (IOException e)
		{
			fail(e.getMessage());
		}
		return null;
	}

	public AbstractWebApiResponse<StudentTripView> getGetAllStudentTripsState(AbstractClient<StudentTripView> client)

	{
		String url = getDispatcherState(client).getLink("getAllStudentTrips");
		try
		{
			return client.loadAllObjectsByUrl(url);
		}
		catch (IOException e)
		{
			fail(e.getMessage());
		}
		return null;
	}

	public AbstractWebApiResponse<StudentTripView> getGetSingleStudentTripState(AbstractClient<StudentTripView> client)

	{

		AbstractWebApiResponse<StudentTripView> returnValue = null;
		try
		{
			String postUrl = getDispatcherState(client).getLink("createStudentTrip");
			AbstractWebApiResponse<StudentTripView> postResponse = client.postObject(postUrl,
				getEmptyStudentTripView());
			String getUrl = postResponse.getLink("getStudentTrip");
			returnValue = client.loadObjectByURL(getUrl);
			client.deleteObject(returnValue.getLink("deleteStudentTrip"));
		}
		catch (IOException e)
		{
			fail(e.getMessage());
		}
		return returnValue;
	}

	@Test public void testGetAllStudentTripsHypermediaGetSingleStudentTripLink()
	{
		final AbstractClient<StudentTripView> client = new WebApiClientStudentTrip();
		AbstractWebApiResponse<StudentTripView> response;
		response = getGetAllStudentTripsState(client);
		checkHypermediaLinks(response, "getStudentTrip", "http://localhost:8080/exam02/api/StudentTrips/{id}",
			"application/json");
	}

	private static void checkHypermediaLinks(AbstractWebApiResponse<StudentTripView> response, String rel, String link,
		String type)
	{
		Map<String, Map<String, String>> links = response.getLinks();

		Map<String, String> linkMap = links.get(rel);
		assertNotNull(linkMap);
		assertFalse(linkMap.isEmpty());
		checkLinkAndType(linkMap, link, type);
	}

	public static <S extends AbstractModel> void checkHypermediaLinksWithId(
		AbstractWebApiResponse<StudentTripView> response, String rel, String link, Collection<S> collection,
		String type)
	{
		Optional<S> model = collection.stream().findFirst();
		assertTrue(model.isPresent());
		checkHypermediaLinks(response, rel, link + model.get().getId(), type);
	}

	@Test public void testGetAllStudentTripsHypermediaSearchByNameLink()
	{
		final AbstractClient<StudentTripView> client = new WebApiClientStudentTrip();
		AbstractWebApiResponse<StudentTripView> response;
		response = getGetAllStudentTripsState(client);
		checkHypermediaLinks(response, "searchStudentTripByName",
			"http://localhost:8080/exam02/api/StudentTrips?name=Name", "application/json");
	}

	@Test public void testGetAllStudentTripsHypermediaSearchByCityLink()
	{
		final AbstractClient<StudentTripView> client = new WebApiClientStudentTrip();
		AbstractWebApiResponse<StudentTripView> response;
		response = getGetAllStudentTripsState(client);
		checkHypermediaLinks(response, "searchStudentTripByCity",
			"http://localhost:8080/exam02/api/StudentTrips?city=City", "application/json");
	}

	@Test public void testGetAllStudentTripsHypermediaSearchByCountryLink()
	{
		final AbstractClient<StudentTripView> client = new WebApiClientStudentTrip();
		AbstractWebApiResponse<StudentTripView> response;
		response = getGetAllStudentTripsState(client);
		checkHypermediaLinks(response, "searchStudentTripByCountry",
			"http://localhost:8080/exam02/api/StudentTrips?country=Country", "application/json");
	}

	@Test public void testGetAllStudentTripsHypermediaSearchByDateLink()
	{
		final AbstractClient<StudentTripView> client = new WebApiClientStudentTrip();
		AbstractWebApiResponse<StudentTripView> response;
		response = getGetAllStudentTripsState(client);
		checkHypermediaLinks(response, "searchStudentTripByDate",
			"http://localhost:8080/exam02/api/StudentTrips?start=1900-01-22&end=2022-12-05", "application/json");
	}

	@Test public void testGetSingleStudentTripsHypermediaDelete()
	{
		final AbstractClient<StudentTripView> client = new WebApiClientStudentTrip();
		AbstractWebApiResponse<StudentTripView> response;
		response = getGetSingleStudentTripState(client);
		checkHypermediaLinksWithId(response, "deleteStudentTrip", "http://localhost:8080/exam02/api/StudentTrips/",
			response.getResponseData(), "application/json");
	}

	@Test public void testGetSingleStudentTripsHypermediaPut()
	{
		final AbstractClient<StudentTripView> client = new WebApiClientStudentTrip();
		AbstractWebApiResponse<StudentTripView> response;
		response = getGetSingleStudentTripState(client);
		checkHypermediaLinksWithId(response, "updateStudentTrip", "http://localhost:8080/exam02/api/StudentTrips/",
			response.getResponseData(), "application/json");
	}

	@Test public void testGetSingleStudentTripsHypermediaSelf()
	{
		final AbstractClient<StudentTripView> client = new WebApiClientStudentTrip();
		AbstractWebApiResponse<StudentTripView> response;
		response = getGetSingleStudentTripState(client);
		checkHypermediaLinksWithId(response, "self", "http://localhost:8080/exam02/api/StudentTrips/",
			response.getResponseData(), null);
	}

	@Test public void testGetSingleStudentTripsHypermediaGetAllStudentTrips()
	{
		final AbstractClient<StudentTripView> client = new WebApiClientStudentTrip();
		AbstractWebApiResponse<StudentTripView> response;
		response = getGetSingleStudentTripState(client);
		checkHypermediaLinks(response, "getAllStudentTrips", "http://localhost:8080/exam02/api/StudentTrips",
			"application/json");
	}

	@Test public void testLinkforStudentsOfStudentTripHypermedia()
	{
		final WebApiClientStudentTrip webApiClientStudentTrip = new WebApiClientStudentTrip();
		WebApiClientStudent webApiClientStudent = new WebApiClientStudent();
		AbstractWebApiResponse<StudentTripView> response;

		StudentView studentView = new StudentView("name", "lastName", "course", 4, 5120051, "email");
		StudentTripView studentTripView = getEmptyStudentTripView();
		try
		{
			String studentURL = "http://localhost:8080/exam02/api/Students";
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

			checkHypermediaLinks(studentTripGetResponse, "getStudents",
				studentTripGetResponse.getLink("self") + "/Students", "application/json");

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

	public static void checkLinkAndType(Map<String, String> link_get, String link, String type)
	{
		assertEquals(link, link_get.get("link"));
		assertEquals(type, link_get.get("type"));
	}

}
