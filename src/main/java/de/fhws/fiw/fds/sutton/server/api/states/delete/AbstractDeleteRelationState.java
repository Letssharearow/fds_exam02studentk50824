package de.fhws.fiw.fds.sutton.server.api.states.delete;

import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

public abstract class AbstractDeleteRelationState<T extends AbstractModel> extends AbstractDeleteState<T>
{
	protected long primaryId;

	public AbstractDeleteRelationState( final AbstractDeleteRelationStateBuilder builder )
	{
		super( builder );
		this.primaryId = builder.parentId;
	}

	public static abstract class AbstractDeleteRelationStateBuilder extends AbstractDeleteStateBuilder
	{
		protected long parentId;

		public AbstractDeleteRelationStateBuilder setParentId( final long parentId )
		{
			this.parentId = parentId;
			return this;
		}
	}
}
