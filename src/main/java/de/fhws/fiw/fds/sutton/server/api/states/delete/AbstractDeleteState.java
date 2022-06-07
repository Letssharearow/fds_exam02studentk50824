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

package de.fhws.fiw.fds.sutton.server.api.states.delete;

import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

import javax.ws.rs.core.Response;

public abstract class AbstractDeleteState<T extends AbstractModel> extends AbstractState
{
	protected long modelIdToDelete;

	protected SingleModelResult<T> modelToDelete;

	protected NoContentResult resultAfterDelete;

	public AbstractDeleteState( final AbstractDeleteStateBuilder builder )
	{
		super( builder );
		this.modelIdToDelete = builder.requestedId;
	}

	@Override
	protected Response buildInternal( )
	{
		configureState( );

		authorizeRequest( );

		this.modelToDelete = loadModel( );

		if ( this.modelToDelete.isEmpty( ) )
		{
			return Response.status( Response.Status.NOT_FOUND )
						   .build( );
		}

		if ( clientDoesNotKnowCurrentModelState( this.modelToDelete.getResult( ) ) )
		{
			return Response.status( Response.Status.PRECONDITION_FAILED ).build( );
		}

		this.resultAfterDelete = deleteModel( );

		if ( this.resultAfterDelete.hasError( ) )
		{
			return Response.serverError( )
						   .build( );
		}

		return createResponse( );
	}

	protected abstract void authorizeRequest( );

	protected abstract SingleModelResult<T> loadModel( );

	protected boolean clientDoesNotKnowCurrentModelState( final AbstractModel modelFromDatabase )
	{
		return false;
	}

	protected abstract NoContentResult deleteModel( );

	protected Response createResponse( )
	{
		defineResponseStatus( );

		defineHttpResponseBody( );

		defineTransitionLinks( );

		return this.responseBuilder.build( );
	}

	private void defineResponseStatus( )
	{
		this.responseBuilder.status( Response.Status.NO_CONTENT );
	}

	private void defineHttpResponseBody( )
	{
		this.responseBuilder.entity( this.modelToDelete.getResult( ) );
	}

	/**
	 * This method is used to define all transition links based on the idea of a REST system as
	 * a finite state machine.
	 */
	protected abstract void defineTransitionLinks( );

	public static abstract class AbstractDeleteStateBuilder extends AbstractState.AbstractStateBuilder
	{
		protected long requestedId;

		public AbstractDeleteStateBuilder setRequestedId( final long requestedId )
		{
			this.requestedId = requestedId;
			return this;
		}
	}
}
