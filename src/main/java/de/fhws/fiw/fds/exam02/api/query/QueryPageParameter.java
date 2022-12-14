package de.fhws.fiw.fds.exam02.api.query;

import de.fhws.fiw.fds.exam02.strings.studentTripStrings.StudentTripUri;
import de.fhws.fiw.fds.exam02.database.DaoFactory;
import de.fhws.fiw.fds.exam02.models.StudentTrip;
import de.fhws.fiw.fds.sutton.server.api.queries.AbstractQuery;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;

public class QueryPageParameter extends AbstractQuery<StudentTrip>
{
	private final String name;
	private final String city;
	private final String country;
	private final String start;
	private final String end;

	public QueryPageParameter(int pageNumber, String name, String city, String country, String start, String end)
	{
		super();
		this.name = name;
		this.city = city;
		this.country = country;
		this.start = start;
		this.end = end;
		setPagingBehavior(new PagingPageParameter(pageNumber, StudentTripUri.PATH_ELEMENT));
	}

	@Override protected CollectionModelResult<StudentTrip> doExecuteQuery()
	{
		return DaoFactory.getInstance().getStudentTripDao().readByNameCityCountryDate(name, city, country, start, end);
	}
}
