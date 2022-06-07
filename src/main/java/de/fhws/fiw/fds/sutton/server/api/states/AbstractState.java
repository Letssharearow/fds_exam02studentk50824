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

package de.fhws.fiw.fds.sutton.server.api.states;

import de.fhws.fiw.fds.sutton.server.api.hyperlinks.Hyperlinks;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

public abstract class AbstractState
{
	protected UriInfo uriInfo;

	protected HttpServletRequest httpServletRequest;

	protected Request request;

	protected ContainerRequestContext context;

	protected Response.ResponseBuilder responseBuilder;

	protected AbstractState( final AbstractStateBuilder builder )
	{
		this.uriInfo = builder.uriInfo;
		this.httpServletRequest = builder.httpServletRequest;
		this.request = builder.request;
		this.context = builder.context;
		this.responseBuilder = Response.ok( );
	}

	/**
	 * This is the main method to start execution of this state implementation.
	 *
	 * @return the response sent back to the client
	 */
	public final Response execute( )
	{
		try
		{
			return buildInternal( );
		}
		catch ( final WebApplicationException f )
		{
			throw f;
		}
		catch ( final Exception e )
		{
			e.printStackTrace( );

			return this.responseBuilder.status( Response.Status.INTERNAL_SERVER_ERROR )
									   .build( );
		}
	}

	/**
	 * Extended states must implement this method which contains the main work flow depending on the state.
	 *
	 * @return the response sent back to the client
	 */
	protected abstract Response buildInternal( );

	/**
	 * This method can be used to configure the state. If extended classes implement the work flow
	 * in different ways, this method would be the correct place to decide. For example, the PUT state
	 * could be implemented so that it returns always status code 200 with the resource OR to return status
	 * code 204 without the resource.
	 */
	protected void configureState( )
	{

	}

	/**
	 * Add a link to the response builder. This method should be called by sub-classes during
	 * processing of the request, for example as part of method {@link #buildInternal()}.
	 *
	 * @param uriTemplate a template of an absolute URI
	 * @param relType     the relation type of this link
	 * @param params      parameters that are replaced in the given template
	 */
	protected final void addLink( final String uriTemplate,
		final String relType,
		final String mediaType,
		final Object... params )
	{
		Hyperlinks.addLink( this.uriInfo, this.responseBuilder, uriTemplate, relType, mediaType, params );
	}

	protected final void addLink( final String uriTemplate, final String relType, final Object... params )
	{
		Hyperlinks.addLink( this.uriInfo, this.responseBuilder, uriTemplate, relType, null, params );
	}

	protected final String getAcceptRequestHeader( )
	{
		return getRequestHeader( "Accept" );
	}

	protected final String getRequestHeader( final String headerName )
	{
		return this.httpServletRequest.getHeader( headerName );
	}

	public static abstract class AbstractStateBuilder
	{
		protected UriInfo uriInfo;

		protected HttpServletRequest httpServletRequest;

		protected Request request;

		protected ContainerRequestContext context;

		public AbstractStateBuilder setUriInfo( final UriInfo uriInfo )
		{
			this.uriInfo = uriInfo;
			return this;
		}

		public AbstractStateBuilder setHttpServletRequest( final HttpServletRequest httpServletRequest )
		{
			this.httpServletRequest = httpServletRequest;
			return this;
		}

		public AbstractStateBuilder setRequest( final Request request )
		{
			this.request = request;
			return this;
		}

		public AbstractStateBuilder setContext( final ContainerRequestContext context )
		{
			this.context = context;
			return this;
		}

		public abstract AbstractState build( );
	}
}
