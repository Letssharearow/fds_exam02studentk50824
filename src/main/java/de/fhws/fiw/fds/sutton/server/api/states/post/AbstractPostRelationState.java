package de.fhws.fiw.fds.sutton.server.api.states.post;

import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

public abstract class AbstractPostRelationState<T extends AbstractModel> extends AbstractPostState<T>
{
	protected long primaryId;

	public AbstractPostRelationState( final AbstractPostRelationStateBuilder builder )
	{
		super( builder );
		this.primaryId = builder.parentId;
	}

	public static abstract class AbstractPostRelationStateBuilder<T extends AbstractModel>
		extends AbstractPostStateBuilder<T>
	{
		protected long parentId;

		public AbstractPostRelationStateBuilder setParentId( final long parentId )
		{
			this.parentId = parentId;
			return this;
		}
	}
}
