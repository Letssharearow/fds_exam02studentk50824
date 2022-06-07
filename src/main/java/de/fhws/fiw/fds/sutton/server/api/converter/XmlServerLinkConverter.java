package de.fhws.fiw.fds.sutton.server.api.converter;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class XmlServerLinkConverter extends XmlAdapter<ServerLink, Link>
{
	@Override
	public Link unmarshal( final ServerLink v ) throws Exception
	{
		if ( v != null )
		{
			return Link.fromUri( v.getHref( ) ).build( );
		}
		else
		{
			return null;
		}
	}

	@Override
	public ServerLink marshal( final Link v ) throws Exception
	{
		if ( v != null )
		{
			return new ServerLink( v.getUri( ).toASCIIString( ), v.getRel( ), v.getType( ) );
		}
		else
		{
			return null;
		}
	}
}
