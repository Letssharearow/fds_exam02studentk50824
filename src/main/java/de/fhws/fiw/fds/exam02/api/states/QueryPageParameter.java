package de.fhws.fiw.fds.exam02.api.states;

import de.fhws.fiw.fds.exam02.database.DaoFactory;
import de.fhws.fiw.fds.exam02.models.StudentTrip;
import de.fhws.fiw.fds.sutton.server.api.queries.AbstractQuery;
import de.fhws.fiw.fds.sutton.server.database.DatabaseException;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;

public class QueryPageParameter extends AbstractQuery<StudentTrip>
{
	private final String searchWords;

	public QueryPageParameter(int pageNumber, String searchWords)
	{
		super();
		this.searchWords = searchWords;
		setPagingBehavior(new PagingPageParameter(pageNumber));
	}

	@Override protected CollectionModelResult<StudentTrip> doExecuteQuery() throws DatabaseException
	{
		return DaoFactory.getInstance().getStudentTripDao().readBySearchParam(searchWords); //TODO
	}
}
