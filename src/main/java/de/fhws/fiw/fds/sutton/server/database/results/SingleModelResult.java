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

public class SingleModelResult<T extends AbstractModel> extends AbstractResult
{
	protected T result;

	protected boolean found;

	public SingleModelResult( )
	{
		this.found = false;
	}

	public SingleModelResult( final T result )
	{
		this.result = result;
		this.found = result != null;
	}

	public T getResult( )
	{
		return this.result;
	}

	@Override
	public boolean isEmpty( )
	{
		return !this.found;
	}
}
