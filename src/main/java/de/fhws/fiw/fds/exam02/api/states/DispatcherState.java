package de.fhws.fiw.fds.exam02.api.states;

import de.fhws.fiw.fds.exam02.strings.studentTripStrings.StudentTripRelTypes;
import de.fhws.fiw.fds.exam02.strings.studentTripStrings.StudentTripUri;
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
		addLink(StudentTripUri.REL_PATH, StudentTripRelTypes.CREATE_STUDENT_TRIP, MediaType.APPLICATION_JSON);
		addLink(StudentTripUri.REL_PATH, StudentTripRelTypes.GET_ALL_STUDENT_TRIPS, MediaType.APPLICATION_JSON);
	}

	public static class Builder extends AbstractDispatcherStateBuilder
	{
		@Override public AbstractState build()
		{
			return new DispatcherState(this);
		}
	}
}
