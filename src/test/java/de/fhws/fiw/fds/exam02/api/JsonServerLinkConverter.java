package de.fhws.fiw.fds.exam02.api;

import com.owlike.genson.Context;
import com.owlike.genson.Converter;
import com.owlike.genson.stream.ObjectReader;
import com.owlike.genson.stream.ObjectWriter;

import javax.ws.rs.core.Link;

public class JsonServerLinkConverter implements Converter<Link>
{
	public JsonServerLinkConverter()
	{
	}

	@Override public void serialize(final Link link, final ObjectWriter objectWriter, final Context context)
		throws Exception
	{
		objectWriter.writeName(link.getTitle());
		objectWriter.beginObject();
		objectWriter.writeString("href", this.replaceCharacters(link.getUri().toASCIIString()));
		objectWriter.writeString("rel", link.getRel());
		if (link.getType() != null && !link.getType().isEmpty())
		{
			objectWriter.writeString("type", link.getType());
		}

		objectWriter.endObject();
	}

	@Override public Link deserialize(final ObjectReader objectReader, final Context context) throws Exception
	{
		Link returnValue = null;
		objectReader.beginObject();

		Link.Builder builder = null;
		while (objectReader.hasNext())
		{
			objectReader.next();
			if ("href".equals(objectReader.name()))
			{
				final String link = objectReader.valueAsString();
				if (builder != null)
					builder = builder.link(link);
				else
				{
					builder = Link.fromUri(link);
				}
			}
			else if ("rel".equals(objectReader.name()))
			{
				final String rel = objectReader.valueAsString();
				if (builder != null)
					builder = builder.rel(rel);
				else
				{
					builder = Link.fromUri(rel);
				}
			}
			else if ("type".equals(objectReader.name()))
			{
				final String type = objectReader.valueAsString();
				if (builder != null)
					builder = builder.type(type);
				else
				{
					builder = Link.fromUri(type);
				}
			}
		}

		objectReader.endObject();
		assert builder != null;
		return builder.build();
	}

	private String replaceCharacters(final String body)
	{
		return body.replace("%3F", "?").replaceAll("%7B", "{").replaceAll("%7D", "}");
	}
}

