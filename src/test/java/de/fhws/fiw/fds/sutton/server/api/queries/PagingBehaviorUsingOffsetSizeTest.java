package de.fhws.fiw.fds.sutton.server.api.queries;

import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.suttondemo.models.Person;
import org.junit.Test;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.List;

import static de.fhws.fiw.fds.sutton.server.api.queries.PersonCollectionModels.createPersonCollection;
import static de.fhws.fiw.fds.sutton.server.api.queries.PersonCollectionModels.createPersonCollectionModel;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PagingBehaviorUsingOffsetSizeTest
{
	protected PagingBehaviorUsingOffsetSize<Person> pagingUnderTest;

	@Test
	public void testHasNextLink_with_next_link_offset_zero( ) throws Exception
	{
		this.pagingUnderTest = new PagingBehaviorUsingOffsetSize<>( 0, 10 );
		assertTrue( this.pagingUnderTest.hasNextLink( createPersonCollectionModel( 11 ) ) );
	}

	@Test
	public void testHasNextLink_with_next_link_offset_neq_zero( ) throws Exception
	{
		this.pagingUnderTest = new PagingBehaviorUsingOffsetSize<>( 1, 10 );
		assertTrue( this.pagingUnderTest.hasNextLink( createPersonCollectionModel( 12 ) ) );
	}

	@Test
	public void testHasNextLink_no_next_link_offset_zero( ) throws Exception
	{
		this.pagingUnderTest = new PagingBehaviorUsingOffsetSize<>( 0, 10 );
		assertFalse( this.pagingUnderTest.hasNextLink( createPersonCollectionModel( 10 ) ) );
	}

	@Test
	public void testHasNextLink_no_next_link_offset_neq_zero( ) throws Exception
	{
		this.pagingUnderTest = new PagingBehaviorUsingOffsetSize<>( 1, 11 );
		assertFalse( this.pagingUnderTest.hasNextLink( createPersonCollectionModel( 10 ) ) );
	}

	@Test
	public void testHasPrevLink_with_prev_link_offset_zero( ) throws Exception
	{
		this.pagingUnderTest = new PagingBehaviorUsingOffsetSize<>( 0, 11 );
		assertFalse( this.pagingUnderTest.hasPrevLink( ) );
	}

	@Test
	public void testHasPrevLink_no_prev_link_offset_gt_zero( ) throws Exception
	{
		this.pagingUnderTest = new PagingBehaviorUsingOffsetSize<>( 0, 11 );
		assertFalse( this.pagingUnderTest.hasPrevLink( ) );
	}

	@Test
	public void testPage_get_part_of_the_result_offset_eq_0( ) throws Exception
	{
		this.pagingUnderTest = new PagingBehaviorUsingOffsetSize<>( 0, 10 );
		final List<Person> result = this.pagingUnderTest.page( createPersonCollection( 20 ) );
		assertEquals( 10, result.size( ) );
	}

	@Test
	public void testPage_get_more_of_the_result_offset_eq_0( ) throws Exception
	{
		this.pagingUnderTest = new PagingBehaviorUsingOffsetSize<>( 0, 20 );
		final List<Person> result = this.pagingUnderTest.page( createPersonCollection( 10 ) );
		assertEquals( 10, result.size( ) );
	}

	@Test
	public void testPage_get_with_offset_larger_than_result( ) throws Exception
	{
		this.pagingUnderTest = new PagingBehaviorUsingOffsetSize<>( 10, 5 );
		final List<Person> result = this.pagingUnderTest.page( createPersonCollection( 10 ) );
		assertEquals( 5, result.size( ) );
	}

	@Test
	public void testPage_get_with_offset_larger_than_result_and_size_larger_than_allowed( ) throws Exception
	{
		this.pagingUnderTest = new PagingBehaviorUsingOffsetSize<>( 10, 15 );
		final List<Person> result = this.pagingUnderTest.page( createPersonCollection( 10 ) );
		assertEquals( 10, result.size( ) );
	}

	@Test
	public void test_getSelfUri( ) throws Exception
	{
		this.pagingUnderTest = new PagingBehaviorUsingOffsetSize<>( 0, 20 );
		final String requestedUri = "http://localhost:8080/exam/api/persons?offset=0&size=20";
		final UriInfo mockUriInfo = mock( UriInfo.class );
		when( mockUriInfo.getRequestUriBuilder( ) ).thenReturn( UriBuilder.fromUri( requestedUri ) );
		assertEquals( requestedUri, this.pagingUnderTest.getSelfUri( mockUriInfo ).toString( ) );
	}

	@Test
	public void test_getNextUri( ) throws Exception
	{
		this.pagingUnderTest = new PagingBehaviorUsingOffsetSize<>( 0, 20 );
		final String requestedUri = "http://localhost:8080/exam/api/persons?offset=20&size=20";
		final CollectionModelResult<Person> modelResult = createPersonCollectionModel( 100 );
		final UriInfo mockUriInfo = mock( UriInfo.class );
		when( mockUriInfo.getRequestUriBuilder( ) ).thenReturn( UriBuilder.fromUri( requestedUri ) );
		assertEquals( requestedUri, this.pagingUnderTest.getNextUri( mockUriInfo, modelResult ).toString( ) );
	}
}
