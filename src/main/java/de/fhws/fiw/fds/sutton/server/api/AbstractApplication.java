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

package de.fhws.fiw.fds.sutton.server.api;

import com.owlike.genson.GensonBuilder;
import com.owlike.genson.ext.jaxrs.GensonJaxRSFeature;
import org.apache.catalina.filters.CorsFilter;
import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import java.util.Set;

public abstract class AbstractApplication extends ResourceConfig
{
	public AbstractApplication( )
	{
		super( );
		registerClasses( getServiceClasses( ) );
		packages( "org.glassfish.jersey.examples.linking" );
		register( DeclarativeLinkingFeature.class );
		register( MultiPartFeature.class );
		register( CorsFilter.class );
		register( new GensonJaxRSFeature( ).use(
			new GensonBuilder( ).setSkipNull( true )
								.useFields( false )
								.useIndentation( true )
								.create( ) ) );
	}

	protected abstract Set<Class<?>> getServiceClasses( );
}
