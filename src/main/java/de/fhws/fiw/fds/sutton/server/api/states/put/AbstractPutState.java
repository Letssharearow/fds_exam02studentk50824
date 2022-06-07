/*
 * Copyright 2019 University of Applied Sciences Würzburg-Schweinfurt, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.fhws.fiw.fds.sutton.server.api.states.put;

import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public abstract class AbstractPutState<T extends AbstractModel> extends AbstractState
{
	protected T modelToUpdate;

	protected long requestedId;

	protected SingleModelResult<T> resultAfterGet;

	protected T storedModel;

	protected NoContentResult resultAfterUpdate;

	protected AbstractPutState(final AbstractPutStateBuilder<T> builder)
	{
		super(builder);
		this.requestedId = builder.requestedId;
		this.modelToUpdate = builder.modelToUpdate;
	}

	@Override protected Response buildInternal()
	{
		configureState();

		if (this.requestedId != this.modelToUpdate.getId())
		{
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		this.resultAfterGet = loadModel();

		if (this.resultAfterGet.isEmpty())
		{
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		this.storedModel = this.resultAfterGet.getResult();

		if (clientDoesNotKnowCurrentModelState(this.storedModel))
		{
			return Response.status(Response.Status.PRECONDITION_FAILED).build();
		}

		this.resultAfterUpdate = updateModel();

		if (this.resultAfterUpdate.hasError())
		{
			return Response.serverError().build();
		}

		return createResponse();
	}

	//	protected abstract void authorizeRequest( );

	protected abstract SingleModelResult<T> loadModel();

	protected boolean clientDoesNotKnowCurrentModelState(final AbstractModel modelFromDatabase)
	{
		return false;
	}

	protected abstract NoContentResult updateModel();

	protected Response createResponse()
	{
		defineResponseStatus();

		defineHttpCaching();

		defineHttpResponseBody();

		defineSelfLink();

		return this.responseBuilder.build();
	}

	private void defineResponseStatus()
	{
		this.responseBuilder.status(Response.Status.NO_CONTENT);
	}

	protected void defineHttpCaching()
	{

	}

	private void defineHttpResponseBody()
	{
		this.responseBuilder.entity("");
	}

	/**
	 * This method is used to define all transition links based on the idea of a REST system as
	 * a finite state machine.
	 */
	//	protected abstract void defineTransitionLinks( );
	protected void defineSelfLink()
	{
		final UriBuilder builder = this.uriInfo.getAbsolutePathBuilder();
		final URI self = builder.build();

		this.responseBuilder.link(self, "self");
	}

	public static abstract class AbstractPutStateBuilder<T extends AbstractModel>
		extends AbstractState.AbstractStateBuilder
	{
		protected long requestedId;

		protected T modelToUpdate;

		public AbstractPutStateBuilder setRequestedId(final long requestedId)
		{
			this.requestedId = requestedId;
			return this;
		}

		public AbstractPutStateBuilder setModelToUpdate(final T modelToUpdate)
		{
			this.modelToUpdate = modelToUpdate;
			return this;
		}
	}
}
