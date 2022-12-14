package de.fhws.fiw.fds.exam02.api.query;

import de.fhws.fiw.fds.exam02.strings.studentStrings.StudentUri;
import de.fhws.fiw.fds.exam02.database.DaoFactory;
import de.fhws.fiw.fds.exam02.models.Student;
import de.fhws.fiw.fds.sutton.server.api.queries.AbstractQuery;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.Set;

public class QueryStudent extends AbstractQuery<Student>
{
	private final Set<Long> studentsInStudentTrip;

	public QueryStudent(Set<Long> studentsInStudentTrip, int pageNumber)
	{
		super();
		this.studentsInStudentTrip = studentsInStudentTrip;
		setPagingBehavior(new PagingPageParameter(pageNumber, StudentUri.PATH_ELEMENT));
	}

	@Override protected CollectionModelResult<Student> doExecuteQuery()
	{
		if (this.studentsInStudentTrip == null)
		{
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return DaoFactory.getInstance().getStudentDao().readStudentsById(studentsInStudentTrip);
	}
}
