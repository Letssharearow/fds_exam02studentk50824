package de.fhws.fiw.fds.exam02.testClasses;

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
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestWrongPostInputForStudentTrip
{

	private final String studentURL = "http://localhost:8080/exam02/api/Students";

	public StudentTripView getEmptyStudentTripView()
	{
		Set<Long> ids = new HashSet<>();

		return new StudentTripView("template", 0L, LocalDate.of(1960, 2, 9), LocalDate.of(2022, 3, 10), "partnerUni",
			"city", "country", ids);
	}

	private StudentView getStudentView()
	{
		String randomFirstName = "juli";
		String randomLastname = "sehne";
		return new StudentView(randomFirstName, randomLastname, "course", 4, 5120051, "email");
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

	@Test public void testStudentIdsContainsOnlyNotExistentStudent()
	{
		WebApiClientStudentTrip client = new WebApiClientStudentTrip();
		StudentTripView emptyStudentTripView = getEmptyStudentTripView();
		emptyStudentTripView.getStudentIds().add(Long.MAX_VALUE);
		try
		{
			AbstractWebApiResponse<StudentTripView> dispatcherState = getDispatcherState(client);
			AbstractWebApiResponse<StudentTripView> postResponse = client.postObject(
				dispatcherState.getLink("createStudentTrip"), emptyStudentTripView);
			assertEquals(400, postResponse.getLastStatusCode());
		}
		catch (IOException e)
		{
			fail(e.getMessage());
		}
	}

	@Test public void testStudentIdsContainsSomeNotExistentStudent()
	{
		WebApiClientStudentTrip clientTrip = new WebApiClientStudentTrip();
		WebApiClientStudent clientStudent = new WebApiClientStudent();
		StudentTripView emptyStudentTripView = getEmptyStudentTripView();
		StudentView studentView = getStudentView();
		emptyStudentTripView.getStudentIds().add(Long.MAX_VALUE);
		try
		{
			AbstractWebApiResponse<StudentView> studentResponse = clientStudent.postObject(studentURL, studentView);
			emptyStudentTripView.getStudentIds().add(
				clientStudent.loadObjectByURL(studentResponse.getLink("getStudent")).getResponseData().stream()
					.findFirst().get().getId());
			AbstractWebApiResponse<StudentTripView> dispatcherState = getDispatcherState(clientTrip);
			AbstractWebApiResponse<StudentTripView> postResponse = clientTrip.postObject(
				dispatcherState.getLink("createStudentTrip"), emptyStudentTripView);
			assertEquals(400, postResponse.getLastStatusCode());
		}
		catch (IOException e)
		{
			fail(e.getMessage());
		}

	}

	public void deleteStudentTrip(WebApiClientStudentTrip client, AbstractWebApiResponse<StudentTripView> response)
		throws IOException
	{
		AbstractWebApiResponse<StudentTripView> getStudentTrip = client.loadObjectByURL(
			response.getLink("getStudentTrip"));
		client.deleteObject(getStudentTrip.getLink("deleteStudentTrip"));
	}

	@Test public void testDateStartIsAfterEnd()
	{
		WebApiClientStudentTrip clientTrip = new WebApiClientStudentTrip();
		StudentTripView emptyStudentTripView = getEmptyStudentTripView();
		emptyStudentTripView.setEnd(LocalDate.of(2022, 2, 15));
		emptyStudentTripView.setStart(LocalDate.of(2022, 2, 15));
		try
		{
			AbstractWebApiResponse<StudentTripView> dispatcherState = getDispatcherState(clientTrip);
			AbstractWebApiResponse<StudentTripView> postResponse = clientTrip.postObject(
				dispatcherState.getLink("createStudentTrip"), emptyStudentTripView);
			assertEquals(201, postResponse.getLastStatusCode());
			deleteStudentTrip(clientTrip, postResponse);
			emptyStudentTripView.setStart(LocalDate.of(2022, 2, 16));
			emptyStudentTripView.setEnd(LocalDate.of(2022, 2, 15));
			postResponse = clientTrip.postObject(dispatcherState.getLink("createStudentTrip"), emptyStudentTripView);
			assertEquals(400, postResponse.getLastStatusCode());
		}
		catch (IOException e)
		{
			fail(e.getMessage());
		}
	}
}
