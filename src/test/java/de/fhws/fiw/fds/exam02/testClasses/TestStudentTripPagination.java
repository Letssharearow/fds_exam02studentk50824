package de.fhws.fiw.fds.exam02.testClasses;

import de.fhws.fiw.fds.exam02.api.AbstractClient;
import de.fhws.fiw.fds.exam02.api.AbstractWebApiResponse;
import de.fhws.fiw.fds.exam02.api.WebApiClientStudentTrip;
import de.fhws.fiw.fds.exam02.models.StudentTripView;
import org.junit.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;

public class TestStudentTripPagination
{

	static final List<AbstractWebApiResponse<StudentTripView>> responses = new ArrayList<>();

	public static AbstractWebApiResponse<StudentTripView> getDispatcherState(AbstractClient<StudentTripView> client)
		throws IOException
	{
		return client.getDispatcher();
	}

	@BeforeClass public static void createData()
	{
		StudentTripView model = getStudentTripView();

		WebApiClientStudentTrip clientStudentTrip = new WebApiClientStudentTrip();
		AbstractWebApiResponse<StudentTripView> dispatcherState;
		try
		{
			dispatcherState = getDispatcherState(clientStudentTrip);
			for (int i = 0; i < 45; i++)
			{
				responses.add(clientStudentTrip.postObject(dispatcherState.getLink("createStudentTrip"), model));
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
		set.add(1L);
		return new StudentTripView("Felix", 0L, start, end, "partnerUni", "city", "country", set);
	}

	@AfterClass public static void removeData()
	{
		WebApiClientStudentTrip clientStudentTrip = new WebApiClientStudentTrip();

		for (AbstractWebApiResponse<StudentTripView> response : responses)
		{
			try
			{
				clientStudentTrip.deleteObject(response.getLink("getStudentTrip"));
			}
			catch (IOException e)
			{
				fail(e.getMessage());
			}
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
		//TODO
	}
	//TODO: only one page exists, next and prev? I need assert that I am in a fresh environment
}
