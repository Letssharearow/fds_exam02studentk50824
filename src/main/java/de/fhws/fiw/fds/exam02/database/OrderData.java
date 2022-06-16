package de.fhws.fiw.fds.exam02.database;

import de.fhws.fiw.fds.exam02.models.StudentTrip;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

public interface OrderData<T extends AbstractModel>
{

	CollectionModelResult<T> order(CollectionModelResult<T> result);
}
