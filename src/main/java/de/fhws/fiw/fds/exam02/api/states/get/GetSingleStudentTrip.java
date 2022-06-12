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
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetState;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;

import javax.ws.rs.core.MediaType;

public class GetSingleStudentTrip extends AbstractGetState<StudentTrip>
{
	public GetSingleStudentTrip(final AbstractGetStateBuilder builder)
	{
		super(builder);
	}

	@Override protected void authorizeRequest()
	{

	}

	@Override protected SingleModelResult<StudentTrip> loadModel()
	{
		return DaoFactory.getInstance().getStudentTripDao().readById(this.requestedId);
	}

	@Override protected void defineTransitionLinks()
	{
		addLink(StudentTripUri.REL_PATH_ID, StudentTripRelTypes.UPDATE_SINGLE_STUDENTTRIP, MediaType.APPLICATION_JSON);
		addLink(StudentTripUri.REL_PATH_ID, StudentTripRelTypes.DELETE_SINGLE_STUDENTTRIP, MediaType.APPLICATION_JSON);
		addLink(StudentTripUri.REL_PATH, StudentTripRelTypes.GET_ALL_STUDENTTRIPS, MediaType.APPLICATION_JSON);
	}

	public static class Builder extends AbstractGetStateBuilder
	{
		@Override public AbstractState build()
		{
			return new GetSingleStudentTrip(this);
		}
	}
}
