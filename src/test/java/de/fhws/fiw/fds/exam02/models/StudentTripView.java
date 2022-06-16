package de.fhws.fiw.fds.exam02.models;

import com.owlike.genson.annotation.JsonConverter;

import javax.ws.rs.core.Link;

import de.fhws.fiw.fds.sutton.server.api.converter.JsonServerLinkConverter;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;
import de.fhws.fiw.fds.sutton.utils.JsonDateTimeConverter;
import de.fhws.fiw.fds.sutton.utils.UriHelper;
import de.fhws.fiw.fds.sutton.utils.XmlDateTimeConverter;
import org.glassfish.jersey.linking.InjectLink;

import javax.inject.Inject;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

@XmlRootElement @XmlAccessorType(XmlAccessType.FIELD) public class StudentTripView extends AbstractModel
	implements Serializable
{
	private Link studentsLink;
	private String name;
	private long id;

	@XmlJavaTypeAdapter(XmlDateTimeConverter.class) private LocalDate start; //without time
	@XmlJavaTypeAdapter(XmlDateTimeConverter.class) private LocalDate end;
	private String partnerUniversity;
	private String city;
	private String country;

	private Set<Long> studentIds;

	public StudentTripView(String name, long id, LocalDate start, LocalDate end, String partnerUniversity, String city,
		String country, Set<Long> studentIds)
	{
		this.name = name;
		this.id = id;
		this.start = start;
		this.end = end;
		this.partnerUniversity = partnerUniversity;
		this.city = city;
		this.country = country;
		this.studentIds = studentIds;
	}

	public StudentTripView()
	{
	}

	@Override public long getId()
	{
		return id;
	}

	@Override public void setId(long id)
	{
		this.id = id;
	}

	@JsonConverter(JsonServerLinkConverter.class) public Link getStudentsLink()
	{
		return studentsLink;
	}

	@JsonConverter(JsonServerLinkConverter.class)

	public void setStudentsLink(Link studentsLink)
	{
		this.studentsLink = studentsLink;
		long id = UriHelper.getLastPathElementAsId(this.studentsLink);
		this.getStudentIds().add(id);
	}

	public Set<Long> getStudentIds()
	{
		return studentIds;
	}

	public void setStudentIds(Set<Long> studentIds)
	{
		this.studentIds = studentIds;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
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
	} //TODO: make names consistent "start" and "end"

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
