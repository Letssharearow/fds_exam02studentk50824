/*
 * Copyright 2021 University of Applied Sciences Würzburg-Schweinfurt, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package de.fhws.fiw.fds.exam02.database.inmemory;

import de.fhws.fiw.fds.exam02.database.DaoFactory;
import de.fhws.fiw.fds.exam02.database.StudentDao;
import de.fhws.fiw.fds.exam02.database.StudentTripDao;
import de.fhws.fiw.fds.exam02.models.Student;
import de.fhws.fiw.fds.exam02.models.StudentTrip;
import de.fhws.fiw.fds.sutton.server.database.inmemory.AbstractInMemoryStorage;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import org.apache.commons.lang.StringUtils;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.function.Predicate;

public class StudentTripInMemoryStorage extends AbstractInMemoryStorage<StudentTrip> implements StudentTripDao
{
	public StudentTripInMemoryStorage()
	{
		super();
		populateData();
	}

	public CollectionModelResult<StudentTrip> order(CollectionModelResult<StudentTrip> result)
	{
		return result; //TODO
	}

	private void populateData()
	{
		LocalDate date = LocalDate.of(1960, 2, 9);
		HashSet<Long> set = new HashSet<>();
		//set.add(1L);
		StudentTrip model = new StudentTrip("Felix", date, date, "partnerUni", "city", "country", set);
		model.setId(nextId());
		this.storage.put(model.getId(), model);

	}

	@Override public CollectionModelResult<StudentTrip> readByNameCityCountryDate(String name, String city,
		String country, String start, String stop)
	{
		return readByPredicate(
			studentTrip -> matchString(studentTrip.getName(), name) && matchString(studentTrip.getCity(), city)
				&& matchString(studentTrip.getCountry(), country) && matchTimeperiod(studentTrip, start, stop));
	}

	@Override public NoContentResult create(final StudentTrip model)
	{
		Set<Long> studentIds = model.getStudentIds();
		StudentDao studentDao = DaoFactory.getInstance().getStudentDao();
		studentIds.forEach(studentId -> {
			if (studentDao.readById(studentId).isEmpty())
			{
				throw new WebApplicationException(Response.Status.BAD_REQUEST);
			}
		});
		model.setId(nextId());
		this.storage.put(model.getId(), model);
		return new NoContentResult();
	}

	private boolean matchString(String variable, String value)
	{
		return StringUtils.isEmpty(value) || variable.startsWith(value) || variable.contains(value)
			|| variable.equalsIgnoreCase(value);
	}

	public boolean matchTimeperiod(final StudentTrip studentTrip, final String start, final String stop)
	{
		Optional<LocalDate> optionalLocalStart = toDate(start);
		Optional<LocalDate> optionalLocalStop = toDate(stop);
		LocalDate tripEnd = studentTrip.getEnd();
		LocalDate tripStart = studentTrip.getStart();

		//returns true if given time period is anywhere inside the start and end of the studenttrip, or no valid date is given
		if (start.isEmpty())
		{
			if (stop.isEmpty())
			{
				return true;
			}
			else
			{
				return !optionalLocalStop.isPresent() || (studentTrip.getEnd().isAfter(optionalLocalStop.get()));
			}
		}
		else
		{
			if (stop.isEmpty())
			{
				return !optionalLocalStart.isPresent() || (tripEnd.isBefore(optionalLocalStart.get()));
			}
			else
			{
				if (!optionalLocalStart.isPresent() || !optionalLocalStop.isPresent())
				{
					return true;
				}
				LocalDate startSearch = optionalLocalStart.get();
				LocalDate stopSearch = optionalLocalStop.get();
				return (tripStart.isBefore(startSearch.plusDays(1)) && tripEnd.isAfter(startSearch.minusDays(1)) || (
					tripStart.isBefore(stopSearch.plusDays(1)) && tripEnd.isAfter(stopSearch.minusDays(1)))
					|| tripStart.isAfter(startSearch.minusDays(1)) && tripEnd.isBefore(stopSearch.plusDays(1)));
			}
		}

	}

	//checks for ISO_LOCAL_DATE
	private Optional<LocalDate> toDate(String date)
	{

		Optional<LocalDate> optionalLocalDate = Optional.empty();
		try
		{
			optionalLocalDate = Optional.of(LocalDate.parse(date));
		}
		catch (DateTimeParseException e)
		{
			System.out.println(e.getMessage());
		}
		return optionalLocalDate;
	}

	public static void main(String[] args)
	{
		LocalDate date = LocalDate.of(1960, 2, 9);
		HashSet<Long> set = new HashSet<>();
		set.add(1L);
		StudentTrip studentTrip = new StudentTrip("Felix", date, date, "partnerUni", "city", "country", set);
		StudentTripInMemoryStorage studentTripInMemoryStorage = new StudentTripInMemoryStorage();
		System.out.println(studentTripInMemoryStorage.matchTimeperiod(studentTrip, "dasdfa", ""));
	}

}
