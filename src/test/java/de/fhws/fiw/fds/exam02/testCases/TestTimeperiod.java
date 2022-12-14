package de.fhws.fiw.fds.exam02.testCases;

import de.fhws.fiw.fds.exam02.api.AbstractWebApiResponse;
import de.fhws.fiw.fds.exam02.api.WebApiClientStudentTrip;
import de.fhws.fiw.fds.exam02.database.inmemory.StudentTripInMemoryStorage;
import de.fhws.fiw.fds.exam02.models.StudentTrip;
import de.fhws.fiw.fds.exam02.models.StudentTripView;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.Assert.*;

public class TestTimeperiod
{
	final String url = "http://localhost:8080/exam02/api/StudentTrips";

	@Test public void testDateInStorage()
	{
		LocalDate start = LocalDate.of(2022, 2, 15);
		LocalDate end = LocalDate.of(2022, 3, 1);
		HashSet<Long> set = new HashSet<>();
		set.add(1L);
		StudentTrip studentTrip = new StudentTrip("Felix", start, end, "partnerUni", "city", "country", set);
		StudentTripInMemoryStorage studentTripInMemoryStorage = new StudentTripInMemoryStorage();
		assertTrue(studentTripInMemoryStorage.isOverlappingWithTimeperiod(studentTrip, "sdfdfs", "sdffsd"));
		assertFalse(studentTripInMemoryStorage.isOverlappingWithTimeperiod(studentTrip, convertTime("01.02.2022"),
			convertTime("14.02.2022")));
		assertTrue(studentTripInMemoryStorage.isOverlappingWithTimeperiod(studentTrip, convertTime("01.02.2022"),
			convertTime("31.03.2022")));
		assertTrue(studentTripInMemoryStorage.isOverlappingWithTimeperiod(studentTrip, convertTime("01.02.2022"),
			convertTime("15.02.2022")));
		assertTrue(studentTripInMemoryStorage.isOverlappingWithTimeperiod(studentTrip, convertTime("01.03.2022"),
			convertTime("31.03.2022")));
		assertFalse(studentTripInMemoryStorage.isOverlappingWithTimeperiod(studentTrip, convertTime("02.03.2022"),
			convertTime("31.03.2022")));
	}

	@Test public void testDateInService() throws IOException
	{
		LocalDate start = LocalDate.of(2022, 2, 15);
		LocalDate end = LocalDate.of(2022, 3, 1);
		HashSet<Long> set = new HashSet<>();
		StudentTripView studentTrip = new StudentTripView("Felix", 0L, start, end, "partnerUni", "city", "country",
			set);

		WebApiClientStudentTrip client = new WebApiClientStudentTrip();

		AbstractWebApiResponse<StudentTripView> apiResponsePost = client.postObject(url, studentTrip);

		String startString = convertTime("01.02.2022");
		String endString = convertTime("01.02.2022");
		assertFalse(tripExistsInTimeperiod(client, startString, endString));

		startString = convertTime("01.02.2022");
		endString = convertTime("31.03.2022");
		assertTrue(tripExistsInTimeperiod(client, startString, endString));

		startString = convertTime("01.02.2022");
		endString = convertTime("15.02.2022");
		assertTrue(tripExistsInTimeperiod(client, startString, endString));

		startString = convertTime("01.03.2022");
		endString = convertTime("31.03.2022");
		assertTrue(tripExistsInTimeperiod(client, startString, endString));

		startString = convertTime("02.03.2022");
		endString = convertTime("31.03.2022");
		assertFalse(tripExistsInTimeperiod(client, startString, endString));

		startString = "";
		endString = convertTime("15.02.2022");
		assertTrue(tripExistsInTimeperiod(client, startString, endString));

		startString = convertTime("01.03.2022");
		endString = "";
		assertTrue(tripExistsInTimeperiod(client, startString, endString));

		startString = "";
		endString = convertTime("14.02.2022");
		assertFalse(tripExistsInTimeperiod(client, startString, endString));

		startString = convertTime("02.03.2022");
		endString = "";
		assertFalse(tripExistsInTimeperiod(client, startString, endString));

		client.deleteObject(apiResponsePost.getLink("getStudentTrip"));
	}

	private boolean tripExistsInTimeperiod(WebApiClientStudentTrip client, String startString, String endString)
		throws IOException
	{
		return client.loadAllObjectsByUrl(
				"http://localhost:8080/exam02/api/StudentTrips?start=" + startString + "&end=" + endString)
			.getResponseData().stream().findFirst().isPresent();
	}

	public String convertTime(String time)
	{
		String[] split = time.split("\\.");
		return split[2] + "-" + split[1] + "-" + split[0];
	}
}
