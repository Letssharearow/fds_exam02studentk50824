package de.fhws.fiw.fds.exam02.testCases;

import de.fhws.fiw.fds.exam02.api.AbstractClient;
import de.fhws.fiw.fds.exam02.api.AbstractWebApiResponse;
import de.fhws.fiw.fds.exam02.api.WebApiClientStudent;
import de.fhws.fiw.fds.exam02.api.WebApiClientStudentTrip;
import de.fhws.fiw.fds.exam02.models.StudentTripView;
import de.fhws.fiw.fds.exam02.models.StudentView;
import org.junit.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class TestStudentTripPagination
{
	private static final String studentURL = "http://localhost:8080/exam02/api/Students";

	static final List<AbstractWebApiResponse<StudentTripView>> RESPONSES_STUDENT_TRIP = new ArrayList<>();
	static final List<AbstractWebApiResponse<StudentView>> RESPONSES_STUDENT_VIEW = new ArrayList<>();

	public static AbstractWebApiResponse<StudentTripView> getDispatcherState(AbstractClient<StudentTripView> client)
		throws IOException
	{
		return client.getDispatcher();
	}

	private static StudentView getStudentView()
	{
		return new StudentView("juli", "lastname", "course", 4, 5120051, "email");
	}

	@BeforeClass public static void createData()
	{
		StudentTripView model = getStudentTripView();

		WebApiClientStudentTrip clientStudentTrip = new WebApiClientStudentTrip();
		AbstractWebApiResponse<StudentTripView> dispatcherState;
		try
		{
			AbstractClient<StudentView> clientStudent = new WebApiClientStudent();
			RESPONSES_STUDENT_VIEW.add(clientStudent.postObject(studentURL, getStudentView()));
			long getStudentId = clientStudent.loadObjectByURL(RESPONSES_STUDENT_VIEW.get(0).getLink("getStudent"))
				.getResponseData().stream().findFirst().get().getId();
			model.getStudentIds().add(getStudentId);
			dispatcherState = getDispatcherState(clientStudentTrip);
			for (int i = 0; i < 45; i++)
			{
				RESPONSES_STUDENT_TRIP.add(
					clientStudentTrip.postObject(dispatcherState.getLink("createStudentTrip"), model));
			}
		}
		catch (IOException e)
		{
			fail(e.getMessage());
		}
	}

	private static StudentTripView getStudentTripView()
	{
		LocalDate start = LocalDate.of(2022, 2, 15);
		LocalDate end = LocalDate.of(2022, 3, 1);
		HashSet<Long> set = new HashSet<>();
		return new StudentTripView("Felix", 0L, start, end, "partnerUni", "city", "country", set);
	}

	@AfterClass public static void removeData()
	{
		WebApiClientStudentTrip clientStudentTrip = new WebApiClientStudentTrip();
		WebApiClientStudent student = new WebApiClientStudent();

		try
		{
			for (AbstractWebApiResponse<StudentTripView> response : RESPONSES_STUDENT_TRIP)
			{
				assertEquals(204,
					clientStudentTrip.deleteObject(response.getLink("getStudentTrip")).getLastStatusCode());
			}
			assertEquals(204,
				student.deleteObject(RESPONSES_STUDENT_VIEW.get(0).getLink("getStudent")).getLastStatusCode());
		}
		catch (IOException e)
		{
			fail(e.getMessage());
		}
	}

	@Test public void testPageAmount10()
	{
		WebApiClientStudentTrip clientStudentTrip = new WebApiClientStudentTrip();
		try
		{
			AbstractWebApiResponse<StudentTripView> dispatcherState = getDispatcherState(clientStudentTrip);
			AbstractWebApiResponse<StudentTripView> getResponse = clientStudentTrip.loadAllObjectsByUrl(
				dispatcherState.getLink("getAllStudentTrips"));
			assertEquals(10, getResponse.getResponseData().size());
			getResponse = clientStudentTrip.loadAllObjectsByUrl(getResponse.getLink("next"));
			assertEquals(10, getResponse.getResponseData().size());
			getResponse = clientStudentTrip.loadAllObjectsByUrl(getResponse.getLink("next"));
			assertEquals(10, getResponse.getResponseData().size());
			getResponse = clientStudentTrip.loadAllObjectsByUrl(getResponse.getLink("next"));
			assertEquals(10, getResponse.getResponseData().size());
			getResponse = clientStudentTrip.loadAllObjectsByUrl(getResponse.getLink("prev"));
			assertEquals(10, getResponse.getResponseData().size());
			getResponse = clientStudentTrip.loadAllObjectsByUrl(getResponse.getLink("prev"));
			assertEquals(10, getResponse.getResponseData().size());
			getResponse = clientStudentTrip.loadAllObjectsByUrl(getResponse.getLink("next"));
			assertEquals(10, getResponse.getResponseData().size());
		}
		catch (IOException e)
		{
			fail(e.getMessage());
		}

	}

	@Test public void testDefaultPage()
	{
		WebApiClientStudentTrip clientStudentTrip = new WebApiClientStudentTrip();
		AbstractWebApiResponse<StudentTripView> dispatcherState;
		try
		{
			dispatcherState = getDispatcherState(clientStudentTrip);
			AbstractWebApiResponse<StudentTripView> getResponse = clientStudentTrip.loadAllObjectsByUrl(
				dispatcherState.getLink("getAllStudentTrips"));
			assertEquals("http://localhost:8080/exam02/api/StudentTrips?page=1", getResponse.getLink("self"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Test public void testPageLinkOnFirstPage()
	{
		WebApiClientStudentTrip clientStudentTrip = new WebApiClientStudentTrip();
		AbstractWebApiResponse<StudentTripView> dispatcherState;
		try
		{
			dispatcherState = getDispatcherState(clientStudentTrip);
			AbstractWebApiResponse<StudentTripView> getResponse = clientStudentTrip.loadAllObjectsByUrl(
				dispatcherState.getLink("getAllStudentTrips"));
			assertNotNull(getResponse.getLinks().get("next"));
			assertNull(getResponse.getLinks().get("prev"));
			assertNotNull(getResponse.getLinks().get("self"));

			assertEquals("http://localhost:8080/exam02/api/StudentTrips?page=1", getResponse.getLink("self"));
			assertEquals("http://localhost:8080/exam02/api/StudentTrips?page=2", getResponse.getLink("next"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Test public void testPageLinkNumberOnAnyPage()
	{
		WebApiClientStudentTrip clientStudentTrip = new WebApiClientStudentTrip();
		AbstractWebApiResponse<StudentTripView> dispatcherState;
		try
		{
			dispatcherState = getDispatcherState(clientStudentTrip);
			AbstractWebApiResponse<StudentTripView> getResponse = clientStudentTrip.loadAllObjectsByUrl(
				dispatcherState.getLink("getAllStudentTrips"));

			getResponse = clientStudentTrip.loadAllObjectsByUrl(getResponse.getLink("next"));
			assertEquals("http://localhost:8080/exam02/api/StudentTrips?page=1", getResponse.getLink("prev"));
			assertEquals("http://localhost:8080/exam02/api/StudentTrips?page=2", getResponse.getLink("self"));
			assertEquals("http://localhost:8080/exam02/api/StudentTrips?page=3", getResponse.getLink("next"));

			getResponse = clientStudentTrip.loadAllObjectsByUrl(getResponse.getLink("next"));
			assertEquals("http://localhost:8080/exam02/api/StudentTrips?page=2", getResponse.getLink("prev"));
			assertEquals("http://localhost:8080/exam02/api/StudentTrips?page=3", getResponse.getLink("self"));
			assertEquals("http://localhost:8080/exam02/api/StudentTrips?page=4", getResponse.getLink("next"));

			getResponse = clientStudentTrip.loadAllObjectsByUrl(getResponse.getLink("next"));
			assertEquals("http://localhost:8080/exam02/api/StudentTrips?page=3", getResponse.getLink("prev"));
			assertEquals("http://localhost:8080/exam02/api/StudentTrips?page=4", getResponse.getLink("self"));
			assertEquals("http://localhost:8080/exam02/api/StudentTrips?page=5", getResponse.getLink("next"));

			getResponse = clientStudentTrip.loadAllObjectsByUrl(getResponse.getLink("prev"));
			assertEquals("http://localhost:8080/exam02/api/StudentTrips?page=2", getResponse.getLink("prev"));
			assertEquals("http://localhost:8080/exam02/api/StudentTrips?page=3", getResponse.getLink("self"));
			assertEquals("http://localhost:8080/exam02/api/StudentTrips?page=4", getResponse.getLink("next"));

			getResponse = clientStudentTrip.loadAllObjectsByUrl(getResponse.getLink("prev"));
			assertEquals("http://localhost:8080/exam02/api/StudentTrips?page=1", getResponse.getLink("prev"));
			assertEquals("http://localhost:8080/exam02/api/StudentTrips?page=2", getResponse.getLink("self"));
			assertEquals("http://localhost:8080/exam02/api/StudentTrips?page=3", getResponse.getLink("next"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Test public void testPageLinkNextOnLastPage()
	{
		WebApiClientStudentTrip clientStudentTrip = new WebApiClientStudentTrip();
		AbstractWebApiResponse<StudentTripView> dispatcherState;
		try
		{
			dispatcherState = getDispatcherState(clientStudentTrip);
			AbstractWebApiResponse<StudentTripView> getResponse = clientStudentTrip.loadAllObjectsByUrl(
				dispatcherState.getLink("getAllStudentTrips"));

			getResponse = clientStudentTrip.loadAllObjectsByUrl(getResponse.getLink("next"));
			getResponse = clientStudentTrip.loadAllObjectsByUrl(getResponse.getLink("next"));
			getResponse = clientStudentTrip.loadAllObjectsByUrl(getResponse.getLink("next"));
			getResponse = clientStudentTrip.loadAllObjectsByUrl(getResponse.getLink("next"));
			Map<String, String> next = getResponse.getLinks().get("next");
			assertNull(next);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	//TODO: only one page exists, next and prev? I need assert that I am in a fresh environment
}
