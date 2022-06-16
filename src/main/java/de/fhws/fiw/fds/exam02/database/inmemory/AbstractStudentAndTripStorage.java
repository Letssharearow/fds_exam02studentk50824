package de.fhws.fiw.fds.exam02.database.inmemory;

import de.fhws.fiw.fds.exam02.database.DaoFactory;
import de.fhws.fiw.fds.exam02.database.StudentDao;
import de.fhws.fiw.fds.exam02.database.StudentTripDao;
import de.fhws.fiw.fds.exam02.models.Student;
import de.fhws.fiw.fds.exam02.models.StudentTrip;
import de.fhws.fiw.fds.sutton.server.database.inmemory.AbstractInMemoryStorage;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;

import java.util.Set;
import java.util.function.Predicate;

public class AbstractStudentAndTripStorage
{
	private final StudentTripDao studentTripDao;
	private final StudentDao studentDao;

	public AbstractStudentAndTripStorage()
	{
		this.studentTripDao = new StudentTripInMemoryStorage();
		this.studentDao = new StudentInMemoryStorage();
	}

	public StudentTripDao getStudentTripDao()
	{
		return this.studentTripDao;
	}

	public StudentDao getStudentDao()
	{
		return this.studentDao;
	}
}
