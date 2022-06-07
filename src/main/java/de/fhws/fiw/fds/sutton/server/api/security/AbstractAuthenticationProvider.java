package de.fhws.fiw.fds.sutton.server.api.security;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAuthorizedException;
import java.util.Arrays;
import java.util.Optional;

public abstract class AbstractAuthenticationProvider
{
	public final User accessControl( final HttpServletRequest request, final String... roles )
	{
		final User requestingUser = BasicAuthHelper.readUserFromHttpHeader( request );
		return authorizeUser( requestingUser, roles );
	}

	protected abstract Optional<User> loadUserFromDatabase( String name );

	private User authorizeUser( final User requestingUser, final String... roles )
	{
		final Optional<User> databaseUser = loadUserFromDatabase( requestingUser.getName( ) );

		if ( databaseUser.isPresent( ) == false )
		{
			throw new NotAuthorizedException( "" );
		}
		else if ( isBothPasswordsMatch( databaseUser.get( ), requestingUser ) )
		{
			final User theUser = databaseUser.get( );
			checkRoles( theUser, roles );
			return theUser.cloneWithoutSecret( );
		}
		else
		{
			throw new NotAuthorizedException( "" );
		}
	}

	private boolean isBothPasswordsMatch( final User databaseUser, final User requestingUser )
	{
		return databaseUser.getSecret( ).equals( requestingUser.getSecret( ) );
	}

	private void checkRoles( final User user, final String... roles )
	{
		if ( roles.length > 0 )
		{
			if ( Arrays.stream( roles ).noneMatch( role -> user.getRole( ).equalsIgnoreCase( role ) ) )
			{
				throw new ForbiddenException( "" );
			}
		}
	}
}
