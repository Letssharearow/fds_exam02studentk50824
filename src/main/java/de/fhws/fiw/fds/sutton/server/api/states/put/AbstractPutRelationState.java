package de.fhws.fiw.fds.sutton.server.api.states.put;

import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

public abstract class AbstractPutRelationState<T extends AbstractModel> extends AbstractPutState<T>
{
	protected long primaryId;

	public AbstractPutRelationState( final AbstractPutRelationStateBuilder builder )
	{
		super( builder );
		this.primaryId = builder.parentId;
	}

	public static abstract class AbstractPutRelationStateBuilder<T extends AbstractModel>
		extends AbstractPutStateBuilder<T>
	{
		protected long parentId;

		public AbstractPutRelationStateBuilder setParentId( final long parentId )
		{
			this.parentId = parentId;
			return this;
		}
	}
}
