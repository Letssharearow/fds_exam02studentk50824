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

package de.fhws.fiw.fds.suttondemo.api.states.persons;

import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.post.AbstractPostState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.suttondemo.database.DaoFactory;
import de.fhws.fiw.fds.suttondemo.models.Person;

public class PostNewPerson extends AbstractPostState<Person>
{
	public PostNewPerson(final Builder builder)
	{
		super(builder);
	}

	@Override protected void authorizeRequest()
	{

	}

	@Override protected NoContentResult saveModel()
	{
		return DaoFactory.getInstance().getPersonDao().create(this.modelToStore);
	}

	@Override protected void defineTransitionLinks()
	{

	}

	public static class Builder extends AbstractPostStateBuilder<Person>
	{
		@Override public AbstractState build()
		{
			return new PostNewPerson(this);
		}
	}
}
