package de.fhws.fiw.fds.exam02.api.states;

import de.fhws.fiw.fds.exam02.StudentTrips.StudentTripRelTypes;
import de.fhws.fiw.fds.exam02.StudentTrips.StudentTripUri;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetDispatcherState;

import javax.ws.rs.core.MediaType;

public class DispatcherState extends AbstractGetDispatcherState
{
	protected DispatcherState(AbstractDispatcherStateBuilder builder)
	{
		super(builder);
	}

	@Override protected void defineTransitionLinks()
	{
		addLink(StudentTripUri.REL_PATH, StudentTripRelTypes.CREATE_STUDENTTRIP, MediaType.APPLICATION_JSON);
		addLink(StudentTripUri.REL_PATH, StudentTripRelTypes.GET_ALL_STUDENTTRIPS, MediaType.APPLICATION_JSON);
	}

	public static class Builder extends AbstractDispatcherStateBuilder
	{
		@Override public AbstractState build()
		{
			return new DispatcherState(this);
		}
	}
}
