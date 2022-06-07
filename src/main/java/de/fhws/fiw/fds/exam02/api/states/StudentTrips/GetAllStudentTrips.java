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

package de.fhws.fiw.fds.exam02.api.states.StudentTrips;

import de.fhws.fiw.fds.exam02.database.DaoFactory;
import de.fhws.fiw.fds.exam02.models.StudentTrip;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetCollectionState;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;

import javax.ws.rs.core.GenericEntity;
import java.util.Collection;

public class GetAllStudentTrips extends AbstractGetCollectionState<StudentTrip>
{
	private final String firstName;

	private final String lastName;

	public GetAllStudentTrips(final Builder builder)
	{
		super(builder);
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
	}

	protected void defineHttpResponseBody()
	{
		this.responseBuilder.entity(new GenericEntity<Collection<StudentTrip>>(this.result.getResult())
		{
		});
	}

	@Override protected void defineTransitionLinks()
	{

	}

	@Override protected void authorizeRequest()
	{

	}

	@Override protected CollectionModelResult<StudentTrip> loadModels()
	{
		return DaoFactory.getInstance().getStudentTripDao().readByNameAndLastName(this.firstName, this.lastName); //TODO
	}

	public static class Builder extends AbstractGetCollectionStateBuilder<StudentTrip>
	{
		private String firstName;

		private String lastName;

		public Builder setFirstName(final String firstName)
		{
			this.firstName = firstName;
			return this;
		}

		public Builder setLastName(final String lastName)
		{
			this.lastName = lastName;
			return this;
		}

		@Override public AbstractState build()
		{
			return new GetAllStudentTrips(this);
		}
	}
}
