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
import de.fhws.fiw.fds.exam02.database.OrderData;
import de.fhws.fiw.fds.exam02.database.StudentDao;
import de.fhws.fiw.fds.exam02.database.StudentTripDao;
import de.fhws.fiw.fds.exam02.models.StudentTrip;
import de.fhws.fiw.fds.sutton.server.database.inmemory.AbstractInMemoryStorage;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import org.apache.commons.lang.StringUtils;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class StudentTripInMemoryStorage extends AbstractInMemoryStorage<StudentTrip>
	implements StudentTripDao, OrderData<StudentTrip>
{
	public StudentTripInMemoryStorage()
	{
		super();
	}

	@Override public CollectionModelResult<StudentTrip> orderCollection(CollectionModelResult<StudentTrip> result)
	{
		ArrayList<StudentTrip> studentTripList = new ArrayList<>(result.getResult());
		studentTripList.sort(Comparator.comparing(StudentTrip::getStart));
		return new CollectionModelResult<>(studentTripList);
	}

	@Override public CollectionModelResult<StudentTrip> readByNameCityCountryDate(String name, String city,
		String country, String start, String end)
	{
		CollectionModelResult<StudentTrip> result = readByPredicate(
			studentTrip -> isEmptyStartContainedOrEqual(studentTrip.getName(), name) && isEmptyStartContainedOrEqual(
				studentTrip.getCity(), city) && isEmptyStartContainedOrEqual(studentTrip.getCountry(), country)
				&& isOverlappingWithTimeperiod(studentTrip, start, end));
		return orderCollection(result);
	}

	@Override public NoContentResult create(final StudentTrip model)
	{
		if (!isValidStudentTrip(model))
		{
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}
		model.setId(nextId());
		this.storage.put(model.getId(), model);
		return new NoContentResult();
	}

	@Override public boolean isValidStudentTrip(final StudentTrip model)
	{
		return isValidDate(model) && isValidStudentIds(model);
	}

	private static boolean isValidDate(StudentTrip model)
	{
		LocalDate start = model.getStart();
		LocalDate end = model.getEnd();
		return start == null || end == null || !start.isAfter(end);
	}

	private boolean isValidStudentIds(StudentTrip model)
	{
		AtomicBoolean returnValue = new AtomicBoolean(true);
		Set<Long> studentIds = model.getStudentIds();
		studentIds.remove(0L);
		StudentDao studentDao = DaoFactory.getInstance().getStudentDao();
		studentIds.forEach(studentId -> {
			if (returnValue.get() && studentDao.readById(studentId).isEmpty())
			{
				returnValue.set(false);
			}
		});
		return returnValue.get();
	}

	@Override public boolean isEmptyStartContainedOrEqual(String variable, String value)
	{
		variable = variable.toLowerCase(Locale.ROOT);
		value = value.toLowerCase(Locale.ROOT);
		return StringUtils.isEmpty(value) || variable.startsWith(value) || variable.contains(value)
			|| variable.equalsIgnoreCase(value);
	}

	//returns true if given time period is anywhere inside the start and end of the studenttrip, or no valid date is given
	@Override public boolean isOverlappingWithTimeperiod(final StudentTrip studentTrip, final String start,
		final String end)
	{
		Optional<LocalDate> optionalLocalStart = StringToISO_LOCAL_DATE(start);
		Optional<LocalDate> optionalLocalEnd = StringToISO_LOCAL_DATE(end);
		LocalDate tripEnd = studentTrip.getEnd();
		LocalDate tripStart = studentTrip.getStart();

		boolean startExists = !start.isEmpty() && optionalLocalStart.isPresent();
		boolean endExists = !end.isEmpty() && optionalLocalEnd.isPresent();

		if (!startExists)
		{
			if (!endExists)
			{
				return true;
			}
			else
			{
				LocalDate endSearch = optionalLocalEnd.get();
				return isInbetween(tripStart, endSearch, tripEnd);
			}
		}
		else
		{
			if (!endExists)
			{
				LocalDate startSearch = optionalLocalStart.get();
				return isInbetween(tripStart, startSearch, tripEnd);
			}
			else
			{
				LocalDate startSearch = optionalLocalStart.get();
				LocalDate endSearch = optionalLocalEnd.get();
				return isInbetween(tripStart, endSearch, tripEnd) || isInbetween(tripStart, startSearch, tripEnd)
					|| tripStart.isAfter(startSearch.minusDays(1)) && tripEnd.isBefore(endSearch.plusDays(1));
			}
		}

	}

	private boolean isInbetween(LocalDate start, LocalDate middle, LocalDate end)
	{
		return end.isAfter(middle.minusDays(1)) && start.isBefore(middle.plusDays(1));
	}

	//checks for ISO_LOCAL_DATE
	private Optional<LocalDate> StringToISO_LOCAL_DATE(String date)
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

}
