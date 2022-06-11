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

@XmlRootElement @XmlAccessorType(XmlAccessType.FIELD) public class StudentTrip2 extends AbstractModel
	implements Serializable
{
	private String name;
	private String partnerUniversity;

	@XmlJavaTypeAdapter(XmlDateTimeConverter.class) private LocalDate start;
	@XmlJavaTypeAdapter(XmlDateTimeConverter.class) private LocalDate end;
	private String emailAddress;

	@JsonConverter(JsonDateTimeConverter.class) public LocalDate getStart()
	{
		return start;
	}

	@JsonConverter(JsonDateTimeConverter.class) public void setStart(LocalDate start)
	{
		this.start = start;
	}

	@JsonConverter(JsonDateTimeConverter.class) public LocalDate getEnd()
	{
		return end;
	}

	@JsonConverter(JsonDateTimeConverter.class) public void setEnd(LocalDate end)
	{
		this.end = end;
	}

	public StudentTrip2(String name, String partnerUniversity, LocalDate start, LocalDate end, String emailAddress)
	{
		this.name = name;
		this.partnerUniversity = partnerUniversity;
		this.start = start;
		this.end = end;
		this.emailAddress = emailAddress;
	}

	public StudentTrip2()
	{
	}

	public String getName()
	{
		return name;
	}

	public void setName(final String name)
	{
		this.name = name;
	}

	public String getPartnerUniversity()
	{
		return partnerUniversity;
	}

	public void setPartnerUniversity(final String partnerUniversity)
	{
		this.partnerUniversity = partnerUniversity;
	}

	public String getEmailAddress()
	{
		return emailAddress;
	}

	public void setEmailAddress(final String emailAddress)
	{
		this.emailAddress = emailAddress;
	}

}
