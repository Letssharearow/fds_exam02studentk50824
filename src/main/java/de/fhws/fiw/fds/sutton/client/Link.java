/*
 * Copyright 2019 University of Applied Sciences Würzburg-Schweinfurt, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.fhws.fiw.fds.sutton.client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Link
{
	private String url;

	private String mediaType;

	private String relationType;

	public Link( )
	{
	}

	public Link( final String url, final String mediaType, final String relationType )
	{
		this.url = url;
		this.mediaType = mediaType;
		this.relationType = relationType;
	}

	public static Link parseFromHttpHeader( final String header )
	{
		final String[] elements = header.split( ";" );
		final String href = elements[ 0 ];
		final String rel = elements.length > 1 ? elements[ 1 ] : "";
		final String type = elements.length > 2 ? elements[ 2 ] : "type=\"*/*\"";
		return new Link( parseHref( href ), parseType( type ), parseRel( rel ) );
	}

	private static String parseHref( final String headerElement )
	{
		return parse( headerElement, "<([^>]*)>" );
	}

	private static String parse( final String headerElement, final String patternExpression )
	{
		final Pattern pattern = Pattern.compile( patternExpression );
		final Matcher matcher = pattern.matcher( headerElement.trim( ) );
		return matcher.find( ) ? matcher.group( 1 ) : null;
	}

	private static String parseRel( final String headerElement )
	{
		return parse( headerElement, "^rel=\"(.+)\"$" );
	}

	private static String parseType( final String headerElement )
	{
		return parse( headerElement, "^type=\"(.+)\"$" );
	}

	public String getUrl( )
	{
		return this.url;
	}

	public void setUrl( final String url )
	{
		this.url = url;
	}

	public String getMediaType( )
	{
		return this.mediaType;
	}

	public void setMediaType( final String mediaType )
	{
		this.mediaType = mediaType;
	}

	public String getRelationType( )
	{
		return this.relationType;
	}

	public void setRelationType( final String relationType )
	{
		this.relationType = relationType;
	}

	@Override public String toString( )
	{
		return "NorburyLink{" +
			"url='" + this.url + '\'' +
			", mediaType='" + this.mediaType + '\'' +
			", relationType='" + this.relationType + '\'' +
			'}';
	}

}
