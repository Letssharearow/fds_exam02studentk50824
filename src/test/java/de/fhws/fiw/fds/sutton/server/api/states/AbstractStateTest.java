package de.fhws.fiw.fds.sutton.server.api.states;

import org.junit.Test;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

public class AbstractStateTest
{
	@Test
	public void testExecute_buildInternal_is_called_no_exception( ) throws Exception
	{
		final AbstractState stateUnderTest = new DemoStateReturns200Ok( );
		final Response response = stateUnderTest.execute( );
		assertEquals( 200, response.getStatus( ) );
	}

	@Test( expected = WebApplicationException.class )
	public void testExecute_buildInternal_is_called_web_exception( ) throws Exception
	{
		final AbstractState stateUnderTest = new DemoStateThrowsWebException( );
		stateUnderTest.execute( );
	}

	@Test
	public void testExecute_buildInternal_is_called_illegal_argument_exception( ) throws Exception
	{
		final AbstractState stateUnderTest = new DemoStateThrowsIllegalArgumentException( );
		final Response response = stateUnderTest.execute( );
		assertEquals( 500, response.getStatus( ) );
	}
}
