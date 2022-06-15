package de.fhws.fiw.fds.exam02.api.states;

import de.fhws.fiw.fds.exam02.database.DaoFactory;
import de.fhws.fiw.fds.exam02.models.Student;
import de.fhws.fiw.fds.exam02.models.StudentTrip;
import de.fhws.fiw.fds.sutton.server.api.queries.AbstractQuery;
import de.fhws.fiw.fds.sutton.server.database.DatabaseException;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.Set;

public class QueryPageParameterStudent extends AbstractQuery<Student>
{
	private final Set<Long> studentsInStudentTrip;

	public QueryPageParameterStudent(Set<Long> studentsInStudentTrip)
	{
		super();
		this.studentsInStudentTrip = studentsInStudentTrip;
	}

	@Override protected CollectionModelResult<Student> doExecuteQuery() throws DatabaseException
	{
		if (this.studentsInStudentTrip == null)
		{
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return DaoFactory.getInstance().getStudentDao().readStudentsById(studentsInStudentTrip);
	}
}
