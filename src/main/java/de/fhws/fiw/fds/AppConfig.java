package de.fhws.fiw.fds;

import de.fhws.fiw.fds.sutton.utils.JsonDateTimeConverter;
import org.glassfish.jersey.server.ResourceConfig;

public class AppConfig extends ResourceConfig
{
	public AppConfig()
	{
		register(new JsonDateTimeConverter());
	}
}
