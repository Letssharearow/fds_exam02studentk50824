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

package de.fhws.fiw.fds.sutton.server.api.hyperlinks;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class Hyperlinks
{
	public static void addLink( final UriInfo uriInfo,
		final Response.ResponseBuilder responseBuilder,
		final String path,
		final String relationType,
		final String mediaType,
		final Object... params )
	{
		final UriBuilder builder = uriInfo.getAbsolutePathBuilder( );
		builder.replacePath( beforeQuestionMark( path ) );
		builder.replaceQuery( afterQuestionMark( path ) );
		String uriTemplate = builder.toTemplate( );

		for ( final Object p : params )
		{
			uriTemplate = replaceFirstTemplate( uriTemplate, p );
		}

		responseBuilder.header( "Link", linkHeader( uriTemplate, relationType, mediaType ) );
	}

	private static String beforeQuestionMark( final String path )
	{
		if ( path.contains( "?" ) )
		{
			return path.substring( 0, path.indexOf( "?" ) );
		}
		else
		{
			return path;
		}
	}

	private static String afterQuestionMark( final String path )
	{
		if ( path.contains( "?" ) )
		{
			return path.substring( path.indexOf( "?" ) + 1 );
		}
		else
		{
			return "";
		}
	}

	public static String replaceFirstTemplate( final String uri, final Object value )
	{
		return uri.replaceFirst( "\\{id\\}", value.toString( ) );
	}

	public static String linkHeader( final String uri, final String rel, final String mediaType )
	{
		final StringBuilder sb = new StringBuilder( );
		sb.append( '<' ).append( uri ).append( ">;" );
		sb.append( "rel" ).append( "=\"" ).append( rel ).append( "\"" );
		if ( mediaType != null && !mediaType.isEmpty( ) )
		{
			sb.append( ";" );
			sb.append( "type" ).append( "=\"" ).append( mediaType ).append( "\"" );
		}

		return sb.toString( );
	}

	public static void addLink( final Response.ResponseBuilder responseBuilder,
		final URI uri,
		final String relType,
		final String mediaType )
	{
		responseBuilder.header( "Link", linkHeader( uri.toASCIIString( ), relType, mediaType ) );
	}
}
