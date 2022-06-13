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

package de.fhws.fiw.fds.exam02.api.services;

import de.fhws.fiw.fds.exam02.api.states.DispatcherState;
import de.fhws.fiw.fds.exam02.api.states.delete.DeleteSingleStudentTrip;
import de.fhws.fiw.fds.exam02.api.states.get.GetAllStudentTrips;
import de.fhws.fiw.fds.exam02.api.states.get.GetSingleStudentTrip;
import de.fhws.fiw.fds.exam02.api.states.post.PostNewStudentTrip;
import de.fhws.fiw.fds.exam02.api.states.put.PutSingleStudentTrip;
import de.fhws.fiw.fds.exam02.models.StudentTrip;
import de.fhws.fiw.fds.sutton.server.api.services.AbstractService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("StudentTrips") public class StudentTripService extends AbstractService
{
	@GET @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML }) public Response getAllStudentTrips()
	{
		return new GetAllStudentTrips.Builder().setQuery(null).setUriInfo(this.uriInfo).setRequest(this.request)
			.setHttpServletRequest(this.httpServletRequest).setContext(this.context).build().execute();
	}

	@GET @Path("{id: \\d+}") @Produces({ MediaType.APPLICATION_JSON,
		MediaType.APPLICATION_XML }) public Response getSingleStudentTrip(@PathParam("id") final long id)
	{
		return new GetSingleStudentTrip.Builder().setRequestedId(id).setUriInfo(this.uriInfo).setRequest(this.request)
			.setHttpServletRequest(this.httpServletRequest).setContext(this.context).build().execute();
	}

	@POST @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML }) public Response createSingleStudentTrip(
		final StudentTrip StudentTripModel)
	{
		return new PostNewStudentTrip.Builder().setModelToCreate(StudentTripModel).setUriInfo(this.uriInfo)
			.setRequest(this.request).setHttpServletRequest(this.httpServletRequest).setContext(this.context).build()
			.execute();
	}

	@PUT @Path("{id: \\d+}") @Consumes({ MediaType.APPLICATION_JSON,
		MediaType.APPLICATION_XML }) public Response updateSingleStudentTrip(@PathParam("id") final long id,
		final StudentTrip StudentTripModel)
	{
		return new PutSingleStudentTrip.Builder().setRequestedId(id).setModelToUpdate(StudentTripModel)
			.setUriInfo(this.uriInfo).setRequest(this.request).setHttpServletRequest(this.httpServletRequest)
			.setContext(this.context).build().execute();
	}

	@DELETE @Path("{id: \\d+}") @Consumes({ MediaType.APPLICATION_JSON,
		MediaType.APPLICATION_XML }) public Response deleteSingleStudentTrip(@PathParam("id") final long id)
	{
		return new DeleteSingleStudentTrip.Builder().setRequestedId(id).setUriInfo(this.uriInfo)
			.setRequest(this.request).setHttpServletRequest(this.httpServletRequest).setContext(this.context).build()
			.execute();
	}
}