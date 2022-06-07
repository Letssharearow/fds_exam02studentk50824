package de.fhws.fiw.fds.suttondemo;

import de.fhws.fiw.fds.suttondemo.client.PersonClient;
import de.fhws.fiw.fds.suttondemo.client.WebApiResponse;
import de.fhws.fiw.fds.suttondemo.models.PersonModel;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class SuttondemoIT
{
	@Test
	public void test_get_collection_of_persons( ) throws IOException
	{
		final PersonClient personClient = new PersonClient( "braunp", "secret" );
		final WebApiResponse<PersonModel> persons = personClient.loadAllPersons( );
		assertEquals( 200, persons.getLastStatusCode( ) );
	}
}
