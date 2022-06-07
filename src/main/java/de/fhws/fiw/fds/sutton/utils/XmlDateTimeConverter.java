package de.fhws.fiw.fds.sutton.utils;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class XmlDateTimeConverter extends XmlAdapter<String, LocalDate>
{
	@Override
	public LocalDate unmarshal( final String v ) throws Exception
	{
		final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
		return LocalDate.parse( v, formatter );
	}

	@Override
	public String marshal( final LocalDate v ) throws Exception
	{
		return v.format( DateTimeFormatter.ISO_LOCAL_DATE );
	}
}
