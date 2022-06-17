package de.fhws.fiw.fds.exam02.api.services;

import de.fhws.fiw.fds.exam02.api.Query.QueryAllStudents;
import de.fhws.fiw.fds.exam02.api.states.delete.DeleteSingleStudent;
import de.fhws.fiw.fds.exam02.api.states.get.GetAllStudents;
import de.fhws.fiw.fds.exam02.api.states.get.GetSingleStudent;
import de.fhws.fiw.fds.exam02.api.states.post.PostNewStudent;
import de.fhws.fiw.fds.exam02.api.states.put.PutSingleStudent;
import de.fhws.fiw.fds.exam02.models.Student;
import de.fhws.fiw.fds.sutton.server.api.services.AbstractService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("Students") public class StudentService extends AbstractService
{
	@GET @Produces({ MediaType.APPLICATION_JSON }) public Response getAllStudents(
		@DefaultValue("1") @QueryParam("page") final int pageNumber)
	{
		return new GetAllStudents.Builder().setQuery(new QueryAllStudents(pageNumber)).setUriInfo(this.uriInfo)
			.setRequest(this.request).setHttpServletRequest(this.httpServletRequest).setContext(this.context).build()
			.execute();
	}

	@GET @Path("{id: \\d+}") @Produces({ MediaType.APPLICATION_JSON,
		MediaType.APPLICATION_XML }) public Response getSingleStudent(@PathParam("id") final long id)
	{
		return new GetSingleStudent.Builder().setRequestedId(id).setUriInfo(this.uriInfo).setRequest(this.request)
			.setHttpServletRequest(this.httpServletRequest).setContext(this.context).build().execute();
	}

	@POST @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML }) public Response createSingleStudent(
		final Student StudentModel)
	{
		return new PostNewStudent.Builder().setModelToCreate(StudentModel).setUriInfo(this.uriInfo)
			.setRequest(this.request).setHttpServletRequest(this.httpServletRequest).setContext(this.context).build()
			.execute();
	}

	@PUT @Path("{id: \\d+}") @Consumes({ MediaType.APPLICATION_JSON,
		MediaType.APPLICATION_XML }) public Response updateSingleStudent(@PathParam("id") final long id,
		final Student StudentModel)
	{
		return new PutSingleStudent.Builder().setRequestedId(id).setModelToUpdate(StudentModel).setUriInfo(this.uriInfo)
			.setRequest(this.request).setHttpServletRequest(this.httpServletRequest).setContext(this.context).build()
			.execute();
	}

	@DELETE @Path("{id: \\d+}") @Consumes({ MediaType.APPLICATION_JSON,
		MediaType.APPLICATION_XML }) public Response deleteSingleStudent(@PathParam("id") final long id)
	{
		return new DeleteSingleStudent.Builder().setRequestedId(id).setUriInfo(this.uriInfo).setRequest(this.request)
			.setHttpServletRequest(this.httpServletRequest).setContext(this.context).build().execute();
	}
}
