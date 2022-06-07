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

package de.fhws.fiw.fds.suttondemo.api.services;

import de.fhws.fiw.fds.sutton.server.api.services.AbstractService;
import de.fhws.fiw.fds.suttondemo.api.states.persons.*;
import de.fhws.fiw.fds.suttondemo.models.Person;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("persons") public class PersonService extends AbstractService
{
	@GET @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML }) public Response getAllPersons(
		@DefaultValue("") @QueryParam("firstname") final String firstName,
		@DefaultValue("") @QueryParam("lastname") final String lastName)
	{
		return new GetAllPersons.Builder().setFirstName(firstName)

			.setLastName(lastName).setQuery(null).setUriInfo(this.uriInfo).setRequest(this.request)
			.setHttpServletRequest(this.httpServletRequest).setContext(this.context).build().execute();
	}

	@GET @Path("{id: \\d+}") @Produces({ MediaType.APPLICATION_JSON,
		MediaType.APPLICATION_XML }) public Response getSinglePerson(@PathParam("id") final long id)
	{
		return new GetSinglePerson.Builder().setRequestedId(id).setUriInfo(this.uriInfo).setRequest(this.request)
			.setHttpServletRequest(this.httpServletRequest).setContext(this.context).build().execute();
	}

	@POST @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML }) public Response createSinglePerson(
		final Person personModel)
	{
		return new PostNewPerson.Builder().setModelToCreate(personModel).setUriInfo(this.uriInfo)
			.setRequest(this.request).setHttpServletRequest(this.httpServletRequest).setContext(this.context).build()
			.execute();
	}

	@PUT @Path("{id: \\d+}") @Consumes({ MediaType.APPLICATION_JSON,
		MediaType.APPLICATION_XML }) public Response updateSinglePerson(@PathParam("id") final long id,
		final Person personModel)
	{
		return new PutSinglePerson.Builder().setRequestedId(id).setModelToUpdate(personModel).setUriInfo(this.uriInfo)
			.setRequest(this.request).setHttpServletRequest(this.httpServletRequest).setContext(this.context).build()
			.execute();
	}

	@DELETE @Path("{id: \\d+}") @Consumes({ MediaType.APPLICATION_JSON,
		MediaType.APPLICATION_XML }) public Response deleteSinglePerson(@PathParam("id") final long id)
	{
		return new DeleteSinglePerson.Builder().setRequestedId(id).setUriInfo(this.uriInfo).setRequest(this.request)
			.setHttpServletRequest(this.httpServletRequest).setContext(this.context).build().execute();
	}
}
