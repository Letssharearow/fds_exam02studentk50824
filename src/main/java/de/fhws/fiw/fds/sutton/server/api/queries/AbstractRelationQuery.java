package de.fhws.fiw.fds.sutton.server.api.queries;

import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

public abstract class AbstractRelationQuery<T extends AbstractModel> extends AbstractQuery<T>
{
	protected long primaryId;

	protected boolean showAll;

	protected AbstractRelationQuery( final long primaryId, final boolean showAll )
	{
		this.primaryId = primaryId;
		this.showAll = showAll;
	}

	public AbstractRelationQuery setPagingBehavior( final PagingBehavior pagingBehavior )
	{
		super.setPagingBehavior( pagingBehavior );
		return this;
	}

	public boolean isShowAll( )
	{
		return showAll;
	}
}
