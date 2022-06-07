/*
 * Copyright 2019 University of Applied Sciences Würzburg-Schweinfurt, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.fhws.fiw.fds.sutton.server.api.caching;

import javax.ws.rs.core.CacheControl;

public class CachingUtils
{
	public static CacheControl create2SecondsPublicCaching( )
	{
		final CacheControl cacheControl = new CacheControl( );
		cacheControl.setPrivate( false );
		cacheControl.setMaxAge( 2 );

		return cacheControl;
	}

	public static CacheControl create30SecondsPublicCaching( )
	{
		final CacheControl cacheControl = new CacheControl( );
		cacheControl.setPrivate( false );
		cacheControl.setMaxAge( 30 );

		return cacheControl;
	}

	public static CacheControl create60SecondsPublicCaching( )
	{
		final CacheControl cacheControl = new CacheControl( );
		cacheControl.setPrivate( false );
		cacheControl.setMaxAge( 60 );

		return cacheControl;
	}

	public static CacheControl create30SecondsPrivateCaching( )
	{
		final CacheControl cacheControl = new CacheControl( );
		cacheControl.setPrivate( true );
		cacheControl.setMaxAge( 30 );

		return cacheControl;
	}
}
