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

import de.fhws.fiw.fds.exam02.api.query.QueryPageParameter;
import de.fhws.fiw.fds.exam02.api.query.QueryStudent;
import de.fhws.fiw.fds.exam02.api.states.delete.DeleteSingleStudentTrip;
import de.fhws.fiw.fds.exam02.api.states.get.GetAllStudentTrips;
import de.fhws.fiw.fds.exam02.api.states.get.GetAllStudents;
import de.fhws.fiw.fds.exam02.api.states.get.GetSingleStudent;
import de.fhws.fiw.fds.exam02.api.states.get.GetSingleStudentTrip;
import de.fhws.fiw.fds.exam02.api.states.post.PostNewStudentTrip;
import de.fhws.fiw.fds.exam02.api.states.put.PutSingleStudentTrip;
import de.fhws.fiw.fds.exam02.database.DaoFactory;
import de.fhws.fiw.fds.exam02.models.StudentTrip;
import de.fhws.fiw.fds.sutton.server.api.services.AbstractService;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("StudentTrips") public class StudentTripService extends AbstractService
{
	@GET @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML }) public Response getAllStudentTrips(
		@DefaultValue("1") @QueryParam("page") final int pageNumber, @DefaultValue("") @QueryParam("name") String name,
		@DefaultValue("") @QueryParam("city") String city, @DefaultValue("") @QueryParam("country") String country,
		@DefaultValue("") @QueryParam("start") String start, @DefaultValue("") @QueryParam("end") String end)
	{
		return new GetAllStudentTrips.Builder().setQuery(
				new QueryPageParameter(pageNumber, name, city, country, start, end)).setUriInfo(this.uriInfo)
			.setRequest(this.request).setHttpServletRequest(this.httpServletRequest).setContext(this.context).build()
			.execute();
	}

	@GET @Path("{id: \\d+}") @Produces({ MediaType.APPLICATION_JSON,
		MediaType.APPLICATION_XML }) public Response getSingleStudentTrip(@PathParam("id") final long id)
	{
		return new GetSingleStudentTrip.Builder().setRequestedId(id).setUriInfo(this.uriInfo).setRequest(this.request)
			.setHttpServletRequest(this.httpServletRequest).setContext(this.context).build().execute();
	}

	@GET @Path("{id: \\d+}/Students") @Produces({ MediaType.APPLICATION_JSON,
		MediaType.APPLICATION_XML }) public Response getStudentsFromStudentTrip(@PathParam("id") final long id,
		@QueryParam("page") @DefaultValue("1") final int pageNumber)
	{
		SingleModelResult<StudentTrip> studentTrip = DaoFactory.getInstance().getStudentTripDao().readById(id);
		if (studentTrip.isEmpty())
		{
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return new GetAllStudents.Builder().setQuery(
				new QueryStudent(studentTrip.getResult().getStudentIds(), pageNumber)).setUriInfo(this.uriInfo)
			.setRequest(this.request).setHttpServletRequest(this.httpServletRequest).setContext(this.context).build()
			.execute();
	}

	@GET @Path("{StudentTripId: \\d+}/Students/{StudentId: \\d+}") @Produces({ MediaType.APPLICATION_JSON,
		MediaType.APPLICATION_XML }) public Response getSingleStudentFromStudentTrip(
		@PathParam("StudentTripId") final long studentTripId, @PathParam("StudentId") final long studentId)
	{
		SingleModelResult<StudentTrip> studentTrip = DaoFactory.getInstance().getStudentTripDao()
			.readById(studentTripId);
		if (studentTrip.isEmpty() || !studentTrip.getResult().getStudentIds().contains(studentId))
		{
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return new GetSingleStudent.Builder().setRequestedId(studentId).setUriInfo(this.uriInfo)
			.setRequest(this.request).setHttpServletRequest(this.httpServletRequest).setContext(this.context).build()
			.execute();
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
