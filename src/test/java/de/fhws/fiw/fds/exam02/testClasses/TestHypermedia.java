package de.fhws.fiw.fds.exam02.testClasses;

import de.fhws.fiw.fds.exam02.api.WebApiClient;
import de.fhws.fiw.fds.exam02.api.WebApiResponse;
import de.fhws.fiw.fds.exam02.models.StudentTripView;
import de.fhws.fiw.fds.exam02.models.StudentView;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestHypermedia
{
	//GET
	@Test public void load_studentTrip_by_id_status200() throws IOException
	{
		final WebApiClient client = new WebApiClient();
		StudentTripView studentTripViewPost = new StudentTripView("template", 0L,
			(Arrays.asList(new StudentView("Julian", "Sehne", "BIN", 4, 5120051.0, "email"))), LocalDate.of(1960, 2, 9),
			LocalDate.of(1861, 3, 10), "partnerUni", "city", "country");
		long id = client.loadStudentTripByURL(client.postStudentTrip(studentTripViewPost).getLocation())
			.getResponseData().stream().findFirst().get().getId();

		final WebApiResponse response = client.loadStudentTripById(id);

		assertEquals(200, response.getLastStatusCode());
		assertEquals(1, response.getResponseData().size());

		final Optional<StudentTripView> result = response.getResponseData().stream().findFirst();
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

		assertEquals(204, client.deleteStudentTrip(studentTrip.getId()).getLastStatusCode());
	}

	@Test public void testDispatcherLinks()
	{
		//get Call on dispatcher
	}

	public void loadDispatcherState()
	{

	}

	public static void main(String[] args)
	{
		System.out.println(5120051.0 == 5120051.0);
	}
}
