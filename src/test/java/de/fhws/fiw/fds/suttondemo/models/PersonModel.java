package de.fhws.fiw.fds.suttondemo.models;

import com.owlike.genson.annotation.JsonConverter;
import de.fhws.fiw.fds.sutton.client.Link;
import de.fhws.fiw.fds.sutton.utils.JsonDateTimeConverter;

import java.time.LocalDate;

public class PersonModel extends AbstractModel
{
	private String firstName;
	private String lastName;

	private LocalDate birthDate;
	private String emailAddress;

	private Link self;
	private Link location;

	public PersonModel( )
	{
	}

	public String getFirstName( )
	{
		return firstName;
	}

	public void setFirstName( final String firstName )
	{
		this.firstName = firstName;
	}

	public String getLastName( )
	{
		return lastName;
	}

	public void setLastName( final String lastName )
	{
		this.lastName = lastName;
	}

	@JsonConverter( JsonDateTimeConverter.class )
	public LocalDate getBirthDate( )
	{
		return birthDate;
	}
	
	@JsonConverter( JsonDateTimeConverter.class )
	public void setBirthDate( final LocalDate birthDate )
	{
		this.birthDate = birthDate;
	}

	public String getEmailAddress( )
	{
		return emailAddress;
	}

	public void setEmailAddress( final String emailAddress )
	{
		this.emailAddress = emailAddress;
	}

	public Link getSelf( )
	{
		return self;
	}

	public void setSelf( final Link self )
	{
		this.self = self;
	}

	public Link getLocation( )
	{
		return location;
	}

	public void setLocation( final Link location )
	{
		this.location = location;
	}
}
