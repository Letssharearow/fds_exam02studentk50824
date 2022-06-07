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
import java.util.Date;
import java.util.function.Predicate;

public class StudentTripInMemoryStorage extends AbstractInMemoryStorage<StudentTrip> implements StudentTripDao
{
	public StudentTripInMemoryStorage()
	{
		super();
		populateData();
	}

	@Override public CollectionModelResult<StudentTrip> readByNameAndLastName(final String Name, final String lastName)
	{
		return readByPredicate(byNameAndLastName(Name, lastName));
	}

	private void populateData()
	{
		LocalDate date = LocalDate.of(1960, 2, 9);
		create(new StudentTrip("Felix", null, date, date, "partnerUni", "city", "country"));
	}

	private Predicate<StudentTrip> byNameAndLastName(final String Name, final String lastName)
	{
		return p -> matchName(p, Name) && matchLastName(p, lastName);
	}

	private boolean matchName(final StudentTrip StudentTrip, final String Name)
	{
		return StringUtils.isEmpty(Name) || StudentTrip.getName().equals(Name);
	}

	private boolean matchLastName(final StudentTrip StudentTrip, final String lastName)
	{
		return StringUtils.isEmpty(lastName) || StudentTrip.getName().equals(lastName);
	}
}
