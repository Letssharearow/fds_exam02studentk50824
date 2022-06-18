package de.fhws.fiw.fds.exam02.database.inmemory;

import de.fhws.fiw.fds.exam02.database.StudentDao;
import de.fhws.fiw.fds.exam02.database.StudentTripDao;

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
