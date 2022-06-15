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

package de.fhws.fiw.fds.exam02.api.states.get;

import de.fhws.fiw.fds.exam02.StudentTrips.StudentTripRelTypes;
import de.fhws.fiw.fds.exam02.StudentTrips.StudentTripUri;
import de.fhws.fiw.fds.exam02.database.DaoFactory;
import de.fhws.fiw.fds.exam02.models.StudentTrip;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetCollectionState;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

public class GetAllStudentTrips extends AbstractGetCollectionState<StudentTrip>
{

	public GetAllStudentTrips(final Builder builder)
	{
		super(builder);
	}

	protected void defineHttpResponseBody()
	{
		this.responseBuilder.entity(new GenericEntity<Collection<StudentTrip>>(this.result.getResult())
		{
		});
	}

	@Override protected void defineTransitionLinks()
	{
		addLink(StudentTripUri.REL_PATH_ID, StudentTripRelTypes.GET_SINGLE_STUDENTTRIP, MediaType.APPLICATION_JSON);
		addLink(StudentTripUri.SEARCH_CITY, StudentTripRelTypes.SEARCH_STUDENTTRIP_BY_CITY, MediaType.APPLICATION_JSON);
		addLink(StudentTripUri.SEARCH_DATE, StudentTripRelTypes.SEARCH_STUDENTTRIP_BY_DATE, MediaType.APPLICATION_JSON);
		addLink(StudentTripUri.SEARCH_COUNTRY, StudentTripRelTypes.SEARCH_STUDENTTRIP_BY_COUNTRY,
			MediaType.APPLICATION_JSON);
		addLink(StudentTripUri.SEARCH_NAME, StudentTripRelTypes.SEARCH_STUDENTTRIP_BY_NAME, MediaType.APPLICATION_JSON);
	}

	@Override protected void authorizeRequest()
	{

	}

	public static class Builder extends AbstractGetCollectionStateBuilder<StudentTrip>
	{
		@Override public AbstractState build()
		{
			return new GetAllStudentTrips(this);
		}
	}
}
