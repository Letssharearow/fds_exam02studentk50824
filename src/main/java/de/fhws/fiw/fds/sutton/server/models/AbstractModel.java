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

package de.fhws.fiw.fds.sutton.server.models;

import com.owlike.genson.annotation.JsonIgnore;

import java.io.Serializable;

public abstract class AbstractModel implements Serializable, Cloneable
{
	protected long id;

	private long primaryId;

	public long getId( )
	{
		return this.id;
	}

	public void setId( final long id )
	{
		this.id = id;
	}

	@JsonIgnore
	public long getPrimaryId( )
	{
		return primaryId;
	}

	@JsonIgnore
	public void setPrimaryId( final long primaryId )
	{
		this.primaryId = primaryId;
	}

	@Override public Object clone( ) throws CloneNotSupportedException
	{
		return super.clone( );
	}
}
