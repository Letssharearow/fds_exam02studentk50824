package de.fhws.fiw.fds.sutton.server.api.security;

import org.apache.commons.codec.binary.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.HttpHeaders;
import java.nio.charset.Charset;

public class BasicAuthHelper
{
	public static User readUserFromHttpHeader( final HttpServletRequest request )
	{
		final String authHeader = request != null ? request.getHeader( HttpHeaders.AUTHORIZATION ) : null;

		if ( authHeader != null )
		{
			if ( authHeader.toLowerCase( ).startsWith( "basic " ) )
			{
				final String withoutBasic = authHeader.replaceFirst( "(?i)basic ", "" );
				final String userColonPass = decodeBase64( withoutBasic );
				final String[] asArray = userColonPass.split( ":", 2 );

				if ( asArray.length == 2 )
				{
					final String id = asArray[ 0 ];
					final String secret = asArray[ 1 ];

					return new User( id, secret );
				}
			}
		}

		throw new NotAuthorizedException( "" );
	}

	private static String decodeBase64( final String value )
	{
		return new String( Base64.decodeBase64( value ), Charset.forName( "UTF-8" ) );
	}
}
