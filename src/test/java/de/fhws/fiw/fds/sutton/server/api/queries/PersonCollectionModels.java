package de.fhws.fiw.fds.sutton.server.api.queries;

import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.suttondemo.models.Person;

import java.util.Collection;
import java.util.Collections;

public class PersonCollectionModels
{
	public static CollectionModelResult<Person> createPersonCollectionModel( final int numberOfElements )
	{
		final Collection<Person> result = Collections.nCopies( numberOfElements, new Person( ) );
		return new CollectionModelResult<>( result );
	}

	public static Collection<Person> createPersonCollection( final int numberOfElements )
	{
		return Collections.nCopies( numberOfElements, new Person( ) );
	}

}
