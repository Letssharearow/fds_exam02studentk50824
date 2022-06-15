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

import de.fhws.fiw.fds.exam02.models.Student;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetCollectionState;

import javax.ws.rs.core.GenericEntity;
import java.util.Collection;

public class GetAllStudents extends AbstractGetCollectionState<Student>
{

	public GetAllStudents(final Builder builder)
	{
		super(builder);
	}

	protected void defineHttpResponseBody()
	{
		this.responseBuilder.entity(new GenericEntity<Collection<Student>>(this.result.getResult())
		{
		});
	}

	@Override protected void defineTransitionLinks()
	{
	}

	@Override protected void authorizeRequest()
	{

	}

	public static class Builder extends AbstractGetCollectionStateBuilder<Student>
	{
		@Override public AbstractState build()
		{
			return new GetAllStudents(this);
		}
	}
}
