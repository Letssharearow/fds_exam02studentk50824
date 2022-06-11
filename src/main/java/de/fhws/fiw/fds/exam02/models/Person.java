/*
 * Copyright 2021 University of Applied Sciences Würzburg-Schweinfurt, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package de.fhws.fiw.fds.exam02.models;

import com.owlike.genson.annotation.JsonConverter;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;
import de.fhws.fiw.fds.sutton.utils.JsonDateTimeConverter;
import de.fhws.fiw.fds.sutton.utils.XmlDateTimeConverter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;

@XmlRootElement @XmlAccessorType(XmlAccessType.FIELD) public class Person extends AbstractModel implements Serializable
{
	private String firstName;
	private String lastName;

	@XmlJavaTypeAdapter(XmlDateTimeConverter.class) private LocalDate birthDate;
	private String emailAddress;

	public Person()
	{
	}

	public Person(final String firstname, final String lastname, final String emailAddress, final LocalDate birthdate)
	{
		this.firstName = firstname;
		this.lastName = lastname;
		this.birthDate = birthdate;
		this.emailAddress = emailAddress;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(final String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(final String lastName)
	{
		this.lastName = lastName;
	}

	@JsonConverter(JsonDateTimeConverter.class) public LocalDate getBirthDate()
	{
		return birthDate;
	}

	@JsonConverter(JsonDateTimeConverter.class) public void setBirthDate(final LocalDate birthDate)
	{
		this.birthDate = birthDate;
	}

	public String getEmailAddress()
	{
		return emailAddress;
	}

	public void setEmailAddress(final String emailAddress)
	{
		this.emailAddress = emailAddress;
	}

	@Override public String toString()
	{
		return "Person{" + "id=" + id + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\''
			+ ", birthDate=" + birthDate + ", emailAddress='" + emailAddress + '\'' + '}';
	}
}
