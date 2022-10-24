package de.fhws.fiw.fds.exam02.api.states.post;

import de.fhws.fiw.fds.exam02.database.DaoFactory;
import de.fhws.fiw.fds.exam02.models.Student;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.post.AbstractPostRelationState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;

public class PostNewStudentOfTrip extends AbstractPostRelationState<Student>
{
	public PostNewStudentOfTrip(AbstractPostRelationStateBuilder builder)
	{
		super(builder);
	}

	@Override protected void authorizeRequest()
	{

	}

	@Override protected NoContentResult saveModel()
	{
		return DaoFactory.getInstance().getStudentTripStudentDao().create(this.primaryId, this.modelToStore);
	}

	@Override protected void defineTransitionLinks()
	{

	}

	public static class Builder extends AbstractPostRelationStateBuilder<Student>
	{
		@Override public AbstractState build()
		{
			return new PostNewStudentOfTrip(this);
		}
	}
}
