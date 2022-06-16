package de.fhws.fiw.fds.exam02.database.inmemory;

import de.fhws.fiw.fds.exam02.database.DaoFactory;
import de.fhws.fiw.fds.exam02.database.OrderData;
import de.fhws.fiw.fds.exam02.database.StudentDao;
import de.fhws.fiw.fds.exam02.models.Student;
import de.fhws.fiw.fds.sutton.server.database.inmemory.AbstractInMemoryStorage;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;

import java.util.Set;

public class StudentInMemoryStorage extends AbstractInMemoryStorage<Student> implements StudentDao, OrderData<Student>
{
	public StudentInMemoryStorage()
	{
		super();
		populateData();
	}

	//TODO: remove when done
	private void populateData()
	{
		create(new Student("firstName", "lastName", "course", 4, 5120051, "email"));
	}

	@Override public CollectionModelResult<Student> readStudentsById(Set<Long> studentsInStudentTrip)
	{
		return readByPredicate(student -> studentsInStudentTrip.contains(student.getId()));
	}

	@Override public NoContentResult delete(final long id)
	{
		this.storage.remove(id);
		removeStudentFromTrips(id);
		return new NoContentResult();
	}

	@Override public void removeStudentFromTrips(long id)
	{
		DaoFactory.getInstance().getStudentTripDao().readByPredicate(studentTrip -> {
			if (studentTrip.getStudentIds().contains(id))
			{
				studentTrip.getStudentIds().remove(id);
			}
			return false;
		});
	}

	@Override public CollectionModelResult<Student> order(CollectionModelResult<Student> result)
	{
		return null; //TODO
	}
}
