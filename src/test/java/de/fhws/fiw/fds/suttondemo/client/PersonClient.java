package de.fhws.fiw.fds.suttondemo.client;

import com.owlike.genson.GenericType;
import de.fhws.fiw.fds.suttondemo.models.PersonModel;

import java.io.IOException;
import java.util.List;

public class PersonClient
{
	private static final String BASE_URL = "http://localhost:8080/exam02/api/persons";

	private final GenericWebClient<PersonModel> client;

	public PersonClient( final String userName, final String password )
	{
		this.client = new GenericWebClient<>( userName, password );
	}

	public WebApiResponse create( final PersonModel Person ) throws IOException
	{
		return this.client.sendPostRequest( BASE_URL, Person );
	}

	public WebApiResponse loadSingleById( final long id ) throws IOException
	{
		final String theUrl = String.format( "%s/%d", BASE_URL, id );
		return this.client.sendGetSingleRequest( theUrl, PersonModel.class );
	}

	public WebApiResponse loadSingleByUrl( final String url ) throws IOException
	{
		return this.client.sendGetSingleRequest( url, PersonModel.class );
	}

	public WebApiResponse loadSinglePerson( final PersonModel Person ) throws IOException
	{
		final String theUrl = String.format( "%s/%d", BASE_URL, Person.getId( ) );
		return this.client.sendGetSingleRequest( theUrl, PersonModel.class );
	}

	public WebApiResponse loadAllPersons( ) throws IOException
	{
		return loadAllPersonsByParameters( "", "" );
	}

	public WebApiResponse loadAllPersonsByParameters(
		final String firstName,
		final String lastName
	)
		throws IOException
	{
		final String theUrl = String.format(
			"%s?firstname=%s&lastname=%s",
			BASE_URL,
			firstName,
			lastName
		);

		return this.client.sendGetCollectionRequest( theUrl, new GenericType<List<PersonModel>>( )
		{
		} );
	}

	public WebApiResponse update( final PersonModel person ) throws IOException
	{
		final String theUrl = String.format( "%s/%d", BASE_URL, person.getId( ) );
		return this.client.sendPutRequest( theUrl, person );
	}

	public WebApiResponse delete( final PersonModel person ) throws IOException
	{
		final String theUrl = String.format( "%s/%d", BASE_URL, person.getId( ) );
		return this.client.sendDeleteRequest( theUrl );
	}
}
