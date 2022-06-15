package de.fhws.fiw.fds.exam02.dataBase;

import de.fhws.fiw.fds.exam02.api.WebApiClient;
import de.fhws.fiw.fds.exam02.api.WebApiResponse;
import de.fhws.fiw.fds.exam02.database.inmemory.StudentTripInMemoryStorage;
import de.fhws.fiw.fds.exam02.models.StudentTrip;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class TestTimeperiod
{

	@Test public void testDispatcherLinks() throws IOException
	{
		LocalDate start = LocalDate.of(2022, 2, 15);
		LocalDate end = LocalDate.of(2022, 3, 1);
		HashSet<Long> set = new HashSet<>();
		set.add(1L);
		StudentTrip studentTrip = new StudentTrip("Felix", start, end, "partnerUni", "city", "country", set);
		StudentTripInMemoryStorage studentTripInMemoryStorage = new StudentTripInMemoryStorage();
		assertTrue(studentTripInMemoryStorage.matchTimeperiod(studentTrip, "sdfdfs", "sdffsd"));
		assertFalse(studentTripInMemoryStorage.matchTimeperiod(studentTrip, convertTime("01.02.2022"),
			convertTime("14.02.2022")));
		assertTrue(studentTripInMemoryStorage.matchTimeperiod(studentTrip, convertTime("01.02.2022"),
			convertTime("31.03.2022")));
		assertTrue(studentTripInMemoryStorage.matchTimeperiod(studentTrip, convertTime("01.02.2022"),
			convertTime("15.02.2022")));
		assertTrue(studentTripInMemoryStorage.matchTimeperiod(studentTrip, convertTime("01.03.2022"),
			convertTime("31.03.2022")));
		assertFalse(studentTripInMemoryStorage.matchTimeperiod(studentTrip, convertTime("02.03.2022"),
			convertTime("31.03.2022")));
	}

	public String convertTime(String time)
	{
		String[] split = time.split("\\.");
		return split[2] + "-" + split[1] + "-" + split[0];
	}
}
