package de.fhws.fiw.fds.exam02.api.query;

import de.fhws.fiw.fds.exam02.strings.studentStrings.StudentUri;
import de.fhws.fiw.fds.exam02.database.DaoFactory;
import de.fhws.fiw.fds.exam02.models.Student;
import de.fhws.fiw.fds.sutton.server.api.queries.AbstractQuery;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;

public class QueryAllStudents extends AbstractQuery<Student>
{

	public QueryAllStudents(int pageNumber)
	{
		super();
		setPagingBehavior(new PagingPageParameter(pageNumber, StudentUri.PATH_ELEMENT));
	}

	@Override protected CollectionModelResult<Student> doExecuteQuery()
	{

		return DaoFactory.getInstance().getStudentDao().loadStudents();
	}
}
