package de.fhws.fiw.fds.exam02.api.states;

import de.fhws.fiw.fds.exam02.database.DaoFactory;
import de.fhws.fiw.fds.exam02.models.Student;
import de.fhws.fiw.fds.sutton.server.api.queries.AbstractQuery;
import de.fhws.fiw.fds.sutton.server.database.DatabaseException;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;

import java.util.Set;

public class QueryAllStudents extends AbstractQuery<Student>
{

	public QueryAllStudents()
	{
		super();
	}

	@Override protected CollectionModelResult<Student> doExecuteQuery() throws DatabaseException
	{

		return DaoFactory.getInstance().getStudentDao().readByPredicate(student -> true);
	}
}
