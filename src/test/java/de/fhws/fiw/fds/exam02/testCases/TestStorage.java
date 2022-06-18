package de.fhws.fiw.fds.exam02.testCases;

import de.fhws.fiw.fds.exam02.api.AbstractClient;
import de.fhws.fiw.fds.exam02.api.AbstractWebApiResponse;
import de.fhws.fiw.fds.exam02.api.WebApiClientStudent;
import de.fhws.fiw.fds.exam02.api.WebApiClientStudentTrip;
import de.fhws.fiw.fds.exam02.models.StudentTripView;
import de.fhws.fiw.fds.exam02.models.StudentView;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestStorage
{
	private static final String studentURL = "http://localhost:8080/exam02/api/Students";

	private static StudentView getStudentView()
	{
		return new StudentView("juli", "lastname", "course", 4, 5120051, "email");
	}

	@Test public void testStudentsLink() throws IOException
	{

		AbstractClient<StudentView> clientStudent = new WebApiClientStudent();
		AbstractWebApiResponse<StudentView> response = clientStudent.postObject(studentURL, getStudentView());
		long getStudentId = clientStudent.loadObjectByURL(response.getLink("getStudent")).getResponseData().stream()
			.findFirst().get().getId();
		LocalDate start = LocalDate.of(2022, 2, 15);
		LocalDate end = LocalDate.of(2022, 3, 1);
		HashSet<Long> set = new HashSet<>();
		set.add(getStudentId);
		StudentTripView studentTrip = new StudentTripView("Felix", 0L, start, end, "partnerUni", "city", "country",
			set);

		WebApiClientStudentTrip client = new WebApiClientStudentTrip();
		AbstractWebApiResponse<StudentTripView> apiResponsePost = client.postObject(
			client.getDispatcher().getLink("createStudentTrip"), studentTrip);
		AbstractWebApiResponse<StudentTripView> apiResponseGet = client.loadObjectByURL(
			apiResponsePost.getLink("getStudentTrip"));
		Optional<StudentTripView> first = apiResponseGet.getResponseData().stream().findFirst();
		assertTrue(first.isPresent());
		client.deleteObject(apiResponsePost.getLink("getStudentTrip"));
	}
}
