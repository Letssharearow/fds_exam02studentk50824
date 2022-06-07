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

package de.fhws.fiw.fds.sutton.server.database.results;

public abstract class AbstractResult
{
	protected boolean hasError;

	protected int errorCode;

	protected String errorMessage;

	protected long databaseExecutionTimeInMs;

	protected AbstractResult()
	{
		this.hasError = false;
	}

	public abstract boolean isEmpty();

	public final void setTimes(final long startTime, final long stopTime)
	{
		this.databaseExecutionTimeInMs = stopTime - startTime;
	}

	public final long getDuration()
	{
		return this.databaseExecutionTimeInMs;
	}

	public final boolean hasError()
	{
		return this.hasError;
	}

	public final void setError()
	{
		this.hasError = true;
	}

	public final void setError(final int errorCode, final String errorMessage)
	{
		this.hasError = true;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
}
