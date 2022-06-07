package de.fhws.fiw.fds.sutton.client;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LinkTest
{
	@Test
	public void test_parse_link( )
	{
		final String linkHeader = "<http://localhost:8080/api>; rel=\"self\"; type=\"*.*\"";
		final Link link = Link.parseFromHttpHeader( linkHeader );
		assertEquals( "http://localhost:8080/api", link.getUrl( ) );
		assertEquals( "self", link.getRelationType( ) );
		assertEquals( "*.*", link.getMediaType( ) );
	}

}
