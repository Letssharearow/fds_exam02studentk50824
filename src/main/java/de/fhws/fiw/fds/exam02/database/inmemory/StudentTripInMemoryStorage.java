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

import de.fhws.fiw.fds.exam02.database.StudentTripDao;
import de.fhws.fiw.fds.exam02.models.StudentTrip;
import de.fhws.fiw.fds.sutton.server.database.inmemory.AbstractInMemoryStorage;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import org.apache.commons.lang.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Predicate;

public class StudentTripInMemoryStorage extends AbstractInMemoryStorage<StudentTrip> implements StudentTripDao
{
	private final String regex = "OR";

	public StudentTripInMemoryStorage()
	{
		super();
		populateData();
	}

	@Override public CollectionModelResult<StudentTrip> readBySearchParam(String serachWords)
	{
		return readByPredicate(bySearch(serachWords.split(regex)));
	}

	public CollectionModelResult<StudentTrip> order(CollectionModelResult<StudentTrip> result)
	{
		
		return result;
	}

	private void populateData()
	{
		LocalDate date = LocalDate.of(1960, 2, 9);
		create(new StudentTrip("Felix", null, date, date, "partnerUni", "city", "country"));
	}

	private Predicate<StudentTrip> bySearch(final String[] searchWords)
	{
		return p -> matchAllWords(p, searchWords);
	}

	//Filtering Name, Timeperiod, City, Country
	private boolean matchAllWords(final StudentTrip studentTrip, final String[] words)
	{
		for (String word : words)
		{
			word = word.toLowerCase(Locale.ROOT);
			if (matchString(studentTrip.getName().toLowerCase(Locale.ROOT), word) || matchString(
				studentTrip.getCity().toLowerCase(Locale.ROOT), word) || matchString(
				studentTrip.getCountry().toLowerCase(Locale.ROOT), word) || matchTimeperiod(studentTrip, word))
				return true;
		}
		return false;
	}

	private boolean matchString(String variable, String value)
	{
		return StringUtils.isEmpty(value) || variable.startsWith(value) || variable.contains(value)
			|| variable.equalsIgnoreCase(value);
	}

	private boolean matchTimeperiod(final StudentTrip studentTrip, final String date)
	{
		Optional<LocalDate> optionalLocalDate = toDate(date);
		return optionalLocalDate.isPresent() && studentTrip.getStart().isAfter(optionalLocalDate.get())
			&& studentTrip.getEnd().isAfter(optionalLocalDate.get());
	}

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
		//TODO: check for more dateFormats
	}
}
