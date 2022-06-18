package de.fhws.fiw.fds.exam02.testClasses;

import de.fhws.fiw.fds.exam02.api.AbstractWebApiResponse;
import de.fhws.fiw.fds.exam02.api.WebApiClientStudent;
import de.fhws.fiw.fds.exam02.models.StudentView;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class TestStudentPagination
{
	private final String studentURL = "http://localhost:8080/exam02/api/Students";

	private final List<AbstractWebApiResponse<StudentView>> responses = new ArrayList<>();

	@Before public void createData()
	{
		WebApiClientStudent clientStudent = new WebApiClientStudent();
		try
		{

			for (int i = 0; i < 45; i++)
			{
				responses.add(clientStudent.postObject(studentURL, getStudentView()));
			}
		}
		catch (IOException e)
		{
			fail(e.getMessage());
		}
	}

	private StudentView getStudentView()
	{
		String randomFirstName = getRandomString();
		String randomLastname = getRandomString();
		return new StudentView(Math.random() > 0.5 ? randomFirstName : "juli", randomLastname, "course", 4, 5120051,
			"email");
	}

	private String getRandomString()
	{
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 5;
		Random random = new Random();

		return random.ints(leftLimit, rightLimit + 1).limit(targetStringLength)
			.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
	}

	@After public void removeData()
	{
		WebApiClientStudent clientStudent = new WebApiClientStudent();

		responses.forEach(response -> {
			try
			{
				clientStudent.deleteObject(response.getLink("getStudent"));
			}
			catch (IOException e)
			{
				fail(e.getMessage());
			}
		});
	}

	@Test public void testPageAmount10()
	{
		WebApiClientStudent clientStudent = new WebApiClientStudent();
		try
		{
			AbstractWebApiResponse<StudentView> getResponse = clientStudent.loadAllObjectsByUrl(studentURL);
			assertEquals(10, getResponse.getResponseData().size());
			getResponse = clientStudent.loadAllObjectsByUrl(getResponse.getLink("next"));
			assertEquals(10, getResponse.getResponseData().size());
			getResponse = clientStudent.loadAllObjectsByUrl(getResponse.getLink("next"));
			assertEquals(10, getResponse.getResponseData().size());
			getResponse = clientStudent.loadAllObjectsByUrl(getResponse.getLink("next"));
			assertEquals(10, getResponse.getResponseData().size());
			getResponse = clientStudent.loadAllObjectsByUrl(getResponse.getLink("prev"));
			assertEquals(10, getResponse.getResponseData().size());
			getResponse = clientStudent.loadAllObjectsByUrl(getResponse.getLink("prev"));
			assertEquals(10, getResponse.getResponseData().size());
			getResponse = clientStudent.loadAllObjectsByUrl(getResponse.getLink("next"));
			assertEquals(10, getResponse.getResponseData().size());
		}
		catch (IOException e)
		{
			fail(e.getMessage());
		}

	}

	@Test public void testDefaultPage()
	{
		WebApiClientStudent clientStudent = new WebApiClientStudent();

		try
		{

			AbstractWebApiResponse<StudentView> getResponse = clientStudent.loadAllObjectsByUrl(studentURL);
			assertEquals("http://localhost:8080/exam02/api/Students?page=1", getResponse.getLink("self"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Test public void testPageLinkOnFirstPage()
	{
		WebApiClientStudent clientStudent = new WebApiClientStudent();

		try
		{

			AbstractWebApiResponse<StudentView> getResponse = clientStudent.loadAllObjectsByUrl(studentURL);
			assertNotNull(getResponse.getLinks().get("next"));
			assertNull(getResponse.getLinks().get("prev"));
			assertNotNull(getResponse.getLinks().get("self"));

			assertEquals("http://localhost:8080/exam02/api/Students?page=1", getResponse.getLink("self"));
			assertEquals("http://localhost:8080/exam02/api/Students?page=2", getResponse.getLink("next"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Test public void testPageLinkNumberOnAnyPage()
	{
		WebApiClientStudent clientStudent = new WebApiClientStudent();

		try
		{

			AbstractWebApiResponse<StudentView> getResponse = clientStudent.loadAllObjectsByUrl(studentURL);

			getResponse = clientStudent.loadAllObjectsByUrl(getResponse.getLink("next"));
			assertEquals("http://localhost:8080/exam02/api/Students?page=1", getResponse.getLink("prev"));
			assertEquals("http://localhost:8080/exam02/api/Students?page=2", getResponse.getLink("self"));
			assertEquals("http://localhost:8080/exam02/api/Students?page=3", getResponse.getLink("next"));

			getResponse = clientStudent.loadAllObjectsByUrl(getResponse.getLink("next"));
			assertEquals("http://localhost:8080/exam02/api/Students?page=2", getResponse.getLink("prev"));
			assertEquals("http://localhost:8080/exam02/api/Students?page=3", getResponse.getLink("self"));
			assertEquals("http://localhost:8080/exam02/api/Students?page=4", getResponse.getLink("next"));

			getResponse = clientStudent.loadAllObjectsByUrl(getResponse.getLink("next"));
			assertEquals("http://localhost:8080/exam02/api/Students?page=3", getResponse.getLink("prev"));
			assertEquals("http://localhost:8080/exam02/api/Students?page=4", getResponse.getLink("self"));
			assertEquals("http://localhost:8080/exam02/api/Students?page=5", getResponse.getLink("next"));

			getResponse = clientStudent.loadAllObjectsByUrl(getResponse.getLink("prev"));
			assertEquals("http://localhost:8080/exam02/api/Students?page=2", getResponse.getLink("prev"));
			assertEquals("http://localhost:8080/exam02/api/Students?page=3", getResponse.getLink("self"));
			assertEquals("http://localhost:8080/exam02/api/Students?page=4", getResponse.getLink("next"));

			getResponse = clientStudent.loadAllObjectsByUrl(getResponse.getLink("prev"));
			assertEquals("http://localhost:8080/exam02/api/Students?page=1", getResponse.getLink("prev"));
			assertEquals("http://localhost:8080/exam02/api/Students?page=2", getResponse.getLink("self"));
			assertEquals("http://localhost:8080/exam02/api/Students?page=3", getResponse.getLink("next"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Test public void testPageLinkNextOnLastPage()
	{
		//TODO
	}
	//TODO: only one page exists, next and prev? I need assert that I am in a fresh environment
}
