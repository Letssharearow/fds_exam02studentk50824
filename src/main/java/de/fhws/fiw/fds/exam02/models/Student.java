package de.fhws.fiw.fds.exam02.models;

import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

import java.io.Serializable;

public class Student extends AbstractModel implements Serializable
{
	protected String firstName;
	protected String lastName;
	private String course;
	private int semester;
	double immatriculationNumber;
	private String email;

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getCourse()
	{
		return course;
	}

	public void setCourse(String course)
	{
		this.course = course;
	}

	public int getSemester()
	{
		return semester;
	}

	public void setSemester(int semester)
	{
		this.semester = semester;
	}

	public double getImmatriculationNumber()
	{
		return immatriculationNumber;
	}

	public void setImmatriculationNumber(double immatriculationNumber)
	{
		this.immatriculationNumber = immatriculationNumber;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public Student(String firstName, String lastName, String course, int semester, double immatriculationNumber,
		String email)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.course = course;
		this.semester = semester;
		this.immatriculationNumber = immatriculationNumber;
		this.email = email;
	}

	public Student()
	{
	}
}
