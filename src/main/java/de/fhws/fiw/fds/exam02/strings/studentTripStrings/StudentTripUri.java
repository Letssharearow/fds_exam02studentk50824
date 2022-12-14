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

package de.fhws.fiw.fds.exam02.strings.studentTripStrings;

import de.fhws.fiw.fds.suttondemo.Start;

public interface StudentTripUri
{

	String PATH_ELEMENT = "StudentTrips";
	String REL_PATH = Start.CONTEXT_PATH + "/api/" + PATH_ELEMENT;
	String REL_PATH_ID = REL_PATH + "/{id}";
	String SEARCH_NAME = REL_PATH + "?name=Name";
	String SEARCH_CITY = REL_PATH + "?city=City";
	String SEARCH_COUNTRY = REL_PATH + "?country=Country";
	String SEARCH_DATE = REL_PATH + "?start=1900-01-22&end=2022-12-05";

}
