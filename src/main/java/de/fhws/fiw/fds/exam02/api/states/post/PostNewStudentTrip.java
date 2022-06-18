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

package de.fhws.fiw.fds.exam02.api.states.post;

import de.fhws.fiw.fds.exam02.strings.studentTripStrings.StudentTripRelTypes;
import de.fhws.fiw.fds.exam02.strings.studentTripStrings.StudentTripUri;
import de.fhws.fiw.fds.exam02.database.DaoFactory;
import de.fhws.fiw.fds.exam02.models.StudentTrip;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.post.AbstractPostState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;

import javax.ws.rs.core.MediaType;

public class PostNewStudentTrip extends AbstractPostState<StudentTrip>
{
	public PostNewStudentTrip(final Builder builder)
	{
		super(builder);
	}

	@Override protected void authorizeRequest()
	{

	}

	@Override protected NoContentResult saveModel()
	{
		return DaoFactory.getInstance().getStudentTripDao().create(this.modelToStore);
	}

	@Override protected void defineTransitionLinks()
	{
		addLink(StudentTripUri.REL_PATH_ID.replaceAll("\\{id}", this.modelToStore.getId() + ""),
			StudentTripRelTypes.GET_STUDENT_TRIP, MediaType.APPLICATION_JSON);
	}

	public static class Builder extends AbstractPostStateBuilder<StudentTrip>
	{
		@Override public AbstractState build()
		{
			return new PostNewStudentTrip(this);
		}
	}
}
