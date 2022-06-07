package de.fhws.fiw.fds.sutton.server.database;

import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

import java.util.function.Predicate;

public interface IDatabaseRelationAccessObject<T extends AbstractModel>
{
	NoContentResult create( final long primaryId, final T secondary );

	NoContentResult update( final long primaryId, final T secondary );

	NoContentResult deleteRelation( final long primaryId, final long secondaryId );

	NoContentResult deleteRelationsFromPrimary( final long primaryId );

	NoContentResult deleteRelationsToSecondary( final long secondaryId );

	SingleModelResult<T> readById( final long primaryId, final long secondaryId );

	CollectionModelResult<T> readByPredicate( final long primaryId, final Predicate<T> predicate );

	CollectionModelResult<T> readAllByPredicate( final long primaryId, final Predicate<T> predicate );
}
