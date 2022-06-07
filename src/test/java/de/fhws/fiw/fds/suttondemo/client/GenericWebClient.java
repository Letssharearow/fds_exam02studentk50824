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

package de.fhws.fiw.fds.suttondemo.client;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;
import de.fhws.fiw.fds.suttondemo.models.AbstractModel;
import okhttp3.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class GenericWebClient<T extends AbstractModel>
{
	private static final MediaType MEDIA_TYPE_JSON = MediaType.parse( "application/json" );

	private final OkHttpClient client;

	private final Genson genson;

	public GenericWebClient( )
	{
		this( "", "" );
	}

	public GenericWebClient( final String userName, final String password )
	{
		this.client = new OkHttpClient.Builder( )
			.addInterceptor( new BasicAuthInterceptor( userName, password ) )
			.build( );
		this.genson = new Genson( );
	}

	public WebApiResponse<T> sendGetSingleRequest( final String url,
		final Class<T> clazz ) throws IOException
	{
		final Request request = new Request.Builder( )
			.url( url )
			.get( )
			.build( );

		final Response response = this.client.newCall( request ).execute( );
		final int statusCodeOfLastRequest = response.code( );

		if ( statusCodeOfLastRequest == 200 )
		{
			return new WebApiResponse<>( deserialize( response, clazz ),
				response.code( ) );
		}
		else
		{
			return new WebApiResponse( statusCodeOfLastRequest );
		}
	}

	public WebApiResponse<T> sendGetCollectionRequest( final String url,
		final GenericType<List<T>> genericType )
		throws IOException
	{
		final Request request = new Request.Builder( )
			.url( url )
			.get( )
			.build( );

		final Response response = this.client.newCall( request ).execute( );
		final int statusCodeOfLastRequest = response.code( );

		if ( statusCodeOfLastRequest == 200 )
		{
			return new WebApiResponse<>( deserializeToCollection( response, genericType ),
				response.code( ) );
		}
		else
		{
			return new WebApiResponse( statusCodeOfLastRequest );
		}
	}

	public WebApiResponse sendPostRequest( final String url, final T object )
		throws IOException
	{
		final RequestBody body = RequestBody.create( MEDIA_TYPE_JSON, serialize( object ) );

		final Request request = new Request.Builder( )
			.url( url )
			.post( body )
			.build( );

		final Response response = this.client.newCall( request ).execute( );
		final int statusCodeOfLastRequest = response.code( );

		if ( statusCodeOfLastRequest == 201 )
		{
			return new WebApiResponse<T>( 201, response.header( "Location" ) );
		}
		else
		{
			return new WebApiResponse( statusCodeOfLastRequest );
		}
	}

	public WebApiResponse<T> sendPutRequest( final String url, final T object )
		throws IOException
	{
		final RequestBody body = RequestBody.create( MEDIA_TYPE_JSON, serialize( object ) );

		final Request request = new Request.Builder( )
			.url( url )
			.put( body )
			.build( );

		final Response response = this.client.newCall( request ).execute( );

		return new WebApiResponse<>( response.code( ) );
	}

	public WebApiResponse<T> sendDeleteRequest( final String url ) throws IOException
	{
		final Request request = new Request.Builder( )
			.url( url )
			.delete( )
			.build( );

		final Response response = this.client.newCall( request ).execute( );

		return new WebApiResponse<>( response.code( ) );
	}

	private String serialize( final T object )
	{
		return this.genson.serialize( object );
	}

	private List<T> deserializeToCollection( final Response response, final GenericType<List<T>> genericType )
		throws IOException
	{
		final String data = response.body( ).string( );
		return genson.deserialize( data, genericType );
	}

	private Optional<T> deserialize( final Response response, final Class<T> clazz ) throws IOException
	{
		final String data = response.body( ).string( );
		return Optional.ofNullable( genson.deserialize( data, clazz ) );
	}
}
