package de.fhws.fiw.fds.sutton.server.api.caching;

import javax.ws.rs.core.EntityTag;
import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.MessageDigest;

public class EtagGenerator
{
	public static EntityTag createEntityTag( final Serializable object )
	{
		return new EntityTag( createEtag( object ) );
	}

	public static String createEtag( final Serializable object )
	{
		if ( object == null )
		{
			throw new NullPointerException( "Cannot create Etag for null object" );
		}

		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;
		try
		{
			baos = new ByteArrayOutputStream( );
			oos = new ObjectOutputStream( baos );
			oos.writeObject( object );
			final MessageDigest md = MessageDigest.getInstance( "MD5" );
			return DatatypeConverter.printHexBinary( md.digest( baos.toByteArray( ) ) );
		}
		catch ( final Exception e )
		{
			e.printStackTrace( );
			return null;
		}
		finally
		{
			try
			{
				oos.close( );
				baos.close( );
			}
			catch ( final IOException e )
			{
				// not handled.
			}
		}
	}
}
