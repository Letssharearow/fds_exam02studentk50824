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

package de.fhws.fiw.fds.sutton.server.database.results;

import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

import java.util.Collection;
import java.util.LinkedList;

public class CollectionModelResult<T extends AbstractModel> extends AbstractResult
{
	protected Collection<T> result;

	protected int totalNumberOfResult;

	public CollectionModelResult( )
	{
		this.result = new LinkedList<>( );
		this.totalNumberOfResult = 0;
	}

	public CollectionModelResult( final Collection<T> result )
	{
		this.result = result != null ? result : new LinkedList<>( );
		this.totalNumberOfResult = result.size( );
	}

	@Override
	public boolean isEmpty( )
	{
		return this.result.isEmpty( );
	}

	public Collection<T> getResult( )
	{
		return this.result;
	}

	public int getTotalNumberOfResult( )
	{
		return this.totalNumberOfResult;
	}

	public void setTotalNumberOfResult( final int totalNumberOfResult )
	{
		this.totalNumberOfResult = totalNumberOfResult;
	}
}
