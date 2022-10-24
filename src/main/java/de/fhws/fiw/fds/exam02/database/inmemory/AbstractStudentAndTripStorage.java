package de.fhws.fiw.fds.exam02.database.inmemory;

import de.fhws.fiw.fds.exam02.database.StudentDao;
import de.fhws.fiw.fds.exam02.database.StudentTripDao;
import de.fhws.fiw.fds.exam02.database.StudentTripStudentDao;

public class AbstractStudentAndTripStorage
{
	private final StudentTripDao studentTripDao;
	private final StudentDao studentDao;
	private final StudentTripStudentDao studentTripStudentDao;

	public AbstractStudentAndTripStorage()
	{
		this.studentTripDao = new StudentTripInMemoryStorage();
		this.studentDao = new StudentInMemoryStorage();
		this.studentTripStudentDao = new StudentTripStudentInmemoryStorage();
	}

	public StudentTripDao getStudentTripDao()
	{
		return this.studentTripDao;
	}

	public StudentDao getStudentDao()
	{
		return this.studentDao;
	}

	public StudentTripStudentDao getStudentTripStudentDao()
	{
		return this.studentTripStudentDao;
	}
}
