package de.fhws.fiw.fds.sutton.utils;

import com.owlike.genson.Context;
import com.owlike.genson.Converter;
import com.owlike.genson.stream.ObjectReader;
import com.owlike.genson.stream.ObjectWriter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class JsonDateTimeConverter implements Converter<LocalDate>
{
	@Override
	public void serialize( final LocalDate convert, final ObjectWriter objectWriter, final Context context )
		throws Exception
	{
		objectWriter.writeString( convert.format( DateTimeFormatter.ISO_LOCAL_DATE ) );
	}

	@Override public LocalDate deserialize( final ObjectReader objectReader, final Context context )
		throws Exception
	{
		final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
		return LocalDate.parse( objectReader.valueAsString( ), formatter );
	}
}
