package de.fhws.fiw.fds.exam02.models;

import com.owlike.genson.annotation.JsonConverter;
import de.fhws.fiw.fds.sutton.utils.JsonDateTimeConverter;

import java.time.LocalDate;
import java.util.Collection;

public class StudentTripView
{
	String name;

	Long id;
	Collection<StudentView> students;
	LocalDate start; //without time
	LocalDate end;
	String partnerUniversity;
	String city;
	String country;

	public StudentTripView(String name, Long id, Collection<StudentView> students, LocalDate start, LocalDate end,
		String partnerUniversity, String city, String country)
	{
		this.name = name;
		this.id = id;
		this.students = students;
		this.start = start;
		this.end = end;
		this.partnerUniversity = partnerUniversity;
		this.city = city;
		this.country = country;
	}

	public StudentTripView()
	{
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Collection<StudentView> getStudents()
	{
		return students;
	}

	public void setStudents(Collection<StudentView> students)
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

