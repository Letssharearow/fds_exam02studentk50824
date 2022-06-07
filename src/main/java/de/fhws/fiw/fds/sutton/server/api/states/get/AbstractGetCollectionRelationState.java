package de.fhws.fiw.fds.sutton.server.api.states.get;

import de.fhws.fiw.fds.sutton.server.api.queries.AbstractRelationQuery;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

public abstract class AbstractGetCollectionRelationState<T extends AbstractModel> extends AbstractGetCollectionState<T>
{
	protected long primaryId;

	protected AbstractRelationQuery<T> query;

	public AbstractGetCollectionRelationState( final AbstractGetCollectionRelationStateBuilder builder )
	{
		super( builder );
		this.primaryId = builder.parentId;
		this.query = builder.query;
		super.query = this.query;
	}

	public static abstract class AbstractGetCollectionRelationStateBuilder<T extends AbstractModel>
		extends AbstractGetCollectionStateBuilder<T>
	{
		protected long parentId;

		protected AbstractRelationQuery<T> query;

		public AbstractGetCollectionRelationStateBuilder setParentId( final long parentId )
		{
			this.parentId = parentId;
			return this;
		}

		public AbstractGetCollectionRelationStateBuilder setQuery( final AbstractRelationQuery<T> query )
		{
			this.query = query;
			return this;
		}
	}
}
