package de.fhws.fiw.fds.exam02.models;

import com.owlike.genson.annotation.JsonConverter;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;
import de.fhws.fiw.fds.sutton.utils.JsonDateTimeConverter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

public class StudentTrip extends AbstractModel implements Serializable
{
	String name;
	Collection<Student> students;
	LocalDate start; //without time
	LocalDate end;
	String partnerUniversity;
	String city;
	String country;

	public StudentTrip(String name, Collection<Student> students, LocalDate start, LocalDate end,
		String partnerUniversity, String city, String country)
	{
		this.name = name;
		this.students = students;
		this.start = start;
		this.end = end;
		this.partnerUniversity = partnerUniversity;
		this.city = city;
		this.country = country;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Collection<Student> getStudents()
	{
		return students;
	}

	public void setStudents(Collection<Student> students)
	{
		this.students = students;
	}

	@JsonConverter(JsonDateTimeConverter.class)

	public LocalDate getStart()
	{
		return start;
	}

	@JsonConverter(JsonDateTimeConverter.class)

	public void setStart(LocalDate start)
	{
		this.start = start;
	}

	@JsonConverter(JsonDateTimeConverter.class)

	public LocalDate getEnd()
	{
		return end;
	}

	@JsonConverter(JsonDateTimeConverter.class)

	public void setEnd(LocalDate end)
	{
		this.end = end;
	}

	public String getPartnerUniversity()
	{
		return partnerUniversity;
	}

	public void setPartnerUniversity(String partnerUniversity)
	{
		this.partnerUniversity = partnerUniversity;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getCountry()
	{
		return country;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}
}
