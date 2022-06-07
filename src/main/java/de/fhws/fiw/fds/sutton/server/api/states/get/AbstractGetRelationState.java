package de.fhws.fiw.fds.sutton.server.api.states.get;

import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

public abstract class AbstractGetRelationState<T extends AbstractModel> extends AbstractGetState<T>
{
	protected long primaryId;

	public AbstractGetRelationState( final AbstractGetRelationStateBuilder builder )
	{
		super( builder );
		this.primaryId = builder.parentId;
	}

	public static abstract class AbstractGetRelationStateBuilder extends AbstractGetStateBuilder
	{
		protected long parentId;

		public AbstractGetStateBuilder setParentId( final long parentId )
		{
			this.parentId = parentId;
			return this;
		}
	}
}
