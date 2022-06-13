package de.fhws.fiw.fds.exam02.testClasses;

import de.fhws.fiw.fds.exam02.api.WebApiClient;
import de.fhws.fiw.fds.exam02.api.WebApiResponse;
import de.fhws.fiw.fds.exam02.models.StudentTrip;
import de.fhws.fiw.fds.exam02.models.StudentTripView;
import de.fhws.fiw.fds.exam02.models.StudentView;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;

public class TestHypermedia
{

	public StudentTripView getEmptyStudentTripView()
	{
		return new StudentTripView("template", 0L,
			(Arrays.asList(new StudentView("Julian", "Sehne", "BIN", 4, 5120051.0, "email"))), LocalDate.of(1960, 2, 9),
			LocalDate.of(1861, 3, 10), "partnerUni", "city", "country");
	}

	//GET
	@Test public void load_studentTrip_by_id_status200() throws IOException
	{
		final WebApiClient client = new WebApiClient();
		StudentTripView studentTripViewPost = new StudentTripView("template", 0L,
			(Arrays.asList(new StudentView("Julian", "Sehne", "BIN", 4, 5120051.0, "email"))), LocalDate.of(1960, 2, 9),
			LocalDate.of(1861, 3, 10), "partnerUni", "city", "country");

		final WebApiResponse dispatcherResponse = client.getStates().getDispatcher();
		final WebApiResponse postResponse = client.getStates()
			.postStudentTrip(studentTripViewPost, dispatcherResponse.getLink("createStudentTrip"));
		final WebApiResponse getResponse = client.getStates()
			.loadStudentTripByURL(postResponse.getLink("getStudentTrip"), postResponse.getId());

		assertEquals(200, getResponse.getLastStatusCode());
		assertEquals(1, getResponse.getResponseData().size());

		final Optional<StudentTripView> result = getResponse.getResponseData().stream().findFirst();
		assertTrue(result.isPresent());
		final StudentTripView studentTrip = result.get();

		assertEquals("template", studentTrip.getName());
		assertEquals(LocalDate.of(1960, 2, 9), studentTrip.getStart());
		assertEquals(LocalDate.of(1861, 3, 10), studentTrip.getEnd());
		assertEquals("partnerUni", studentTrip.getPartnerUniversity());
		assertEquals("country", studentTrip.getCountry());
		assertEquals("city", studentTrip.getCity());

		Optional<StudentView> studentView = studentTrip.getStudents().stream().findFirst();
		assertTrue(studentView.isPresent());
		StudentView studentViewJulian = studentView.get();

		assertEquals("Julian", studentViewJulian.getFirstName());
		assertEquals("Sehne", studentViewJulian.getLastName());
		assertEquals("BIN", studentViewJulian.getCourse());
		assertEquals(4, studentViewJulian.getSemester());
		assertEquals(5120051.0, studentViewJulian.getImmatriculationNumber(), 0);
		assertEquals("email", studentViewJulian.getEmail());

		assertEquals(204,
			client.getStates().deleteStudentTrip(postResponse.getId(), getResponse.getLink("deleteStudentTrip"))
				.getLastStatusCode());
	}

	@Test public void testDispatcherLinks() throws IOException
	{
		final WebApiClient client = new WebApiClient();
		WebApiResponse response = client.getStates().getDispatcher();
		assertEquals(200, response.getLastStatusCode());
		Map<String, Map<String, String>> links = response.getLinks();

		Map<String, String> link_get = links.get("getAllStudentTrips");
		assertNotNull(link_get);
		assertFalse(link_get.isEmpty());
		checkRelAndType(link_get, "http://localhost:8080/exam02/api/StudentTrips", "application/json");

		Map<String, String> link_post = links.get("createStudentTrip");
		assertNotNull(link_post);
		assertFalse(link_post.isEmpty());
		checkRelAndType(link_post, "http://localhost:8080/exam02/api/StudentTrips", "application/json");

		Map<String, String> link_self = links.get("self");
		assertNotNull(link_self);
		assertFalse(link_self.isEmpty());
		assertEquals("http://localhost:8080/exam02/api", link_self.get("link"));
	}

	@Test public void testGetAllStudentTripsLinks() throws IOException
	{
		final WebApiClient client = new WebApiClient();
		WebApiResponse response = client.getStates().loadAllStudentTripsByUrl(""); //TODO: dispatcher -> get
		assertEquals(200, response.getLastStatusCode());
		Map<String, Map<String, String>> links = response.getLinks();

		Map<String, String> link_get = links.get("getAllStudentTrips");
		assertNotNull(link_get);
		assertFalse(link_get.isEmpty());
		checkRelAndType(link_get, "http://localhost:8080/exam02/api/StudentTrips", "application/json");

		Map<String, String> link_post = links.get("createStudentTrip");
		assertNotNull(link_post);
		assertFalse(link_post.isEmpty());
		checkRelAndType(link_post, "http://localhost:8080/exam02/api/StudentTrips", "application/json");

		Map<String, String> link_self = links.get("self");
		assertNotNull(link_self);
		assertFalse(link_self.isEmpty());
		assertEquals("http://localhost:8080/exam02/api", link_self.get("link"));
	}

	public void checkRelAndType(Map<String, String> link_get, String link, String type)
	{
		assertEquals(link, link_get.get("link"));
		assertEquals(type, link_get.get("type"));
	}

}
