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

package de.fhws.fiw.fds.exam02.api.states.delete;

import de.fhws.fiw.fds.exam02.strings.studentStrings.StudentRelTypes;
import de.fhws.fiw.fds.exam02.strings.studentStrings.StudentUri;
import de.fhws.fiw.fds.exam02.database.DaoFactory;
import de.fhws.fiw.fds.exam02.models.Student;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.delete.AbstractDeleteState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;

import javax.ws.rs.core.MediaType;

public class DeleteSingleStudent extends AbstractDeleteState<Student>
{
	public DeleteSingleStudent(final Builder builder)
	{
		super(builder);
	}

	@Override protected void authorizeRequest()
	{

	}

	@Override protected SingleModelResult<Student> loadModel()
	{
		return DaoFactory.getInstance().getStudentDao().readById(this.modelIdToDelete);
	}

	@Override protected NoContentResult deleteModel()
	{
		return DaoFactory.getInstance().getStudentDao().delete(this.modelIdToDelete);
	}

	@Override protected void defineTransitionLinks()
	{
		addLink(StudentUri.REL_PATH, StudentRelTypes.GET_ALL_STUDENTS, MediaType.APPLICATION_JSON);
	}

	public static class Builder extends AbstractDeleteStateBuilder
	{
		@Override public AbstractState build()
		{
			return new DeleteSingleStudent(this);
		}
	}
}
