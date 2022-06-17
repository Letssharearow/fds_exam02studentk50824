package de.fhws.fiw.fds.exam02.testClasses;

import de.fhws.fiw.fds.exam02.api.*;
import de.fhws.fiw.fds.exam02.api.AbstractClient;
import de.fhws.fiw.fds.exam02.models.StudentView;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.*;

public class TestStudentHypermedia
{
	private final String studentURL = "http://localhost:8080/exam02/api/Students";

	public StudentView getEmptyStudent()
	{
		return new StudentView("firstName", "lastName", "course", 4, 5120051, "email");
	}

	public AbstractWebApiResponse<StudentView> getDispatcherState(AbstractClient<StudentView> client) throws IOException
	{
		return client.getDispatcher();
	}

	public AbstractWebApiResponse<StudentView> getGetAllStudentsState(AbstractClient<StudentView> client)
		throws IOException
	{
		return client.loadAllObjectsByUrl(studentURL);
	}

	public AbstractWebApiResponse<StudentView> getGetSingleStudentState(AbstractClient<StudentView> client)
		throws IOException
	{
		String postUrl = studentURL;
		AbstractWebApiResponse<StudentView> postResponse = client.postObject(postUrl, getEmptyStudent());
		String getUrl = postResponse.getLink("getStudent");
		AbstractWebApiResponse<StudentView> returnValue = client.loadObjectByURL(getUrl);
		client.deleteObject(returnValue.getLink("deleteStudent"));
		return returnValue;
	}

	@Test public void testGetAllStudentsHypermeidaGetSingleStudentLink()
	{
		final AbstractClient<StudentView> client = new WebApiClientStudent();
		AbstractWebApiResponse<StudentView> response = null;
		try
		{
			response = getGetSingleStudentState(client);
		}
		catch (IOException e)
		{
			fail(e.getMessage());
		}
		try
		{
			response = getGetAllStudentsState(client);
		}
		catch (IOException e)
		{
			fail(e.getMessage());
		}
		Map<String, Map<String, String>> links = response.getLinks();

		Map<String, String> link = links.get("getStudent");
		assertNotNull(link);
		assertFalse(link.isEmpty());
		checkLinkAndType(link, "http://localhost:8080/exam02/api/Students/{id}", "application/json");
	}

	@Test public void testGetSingleStudentsHypermeidaDelete()
	{
		final AbstractClient<StudentView> client = new WebApiClientStudent();
		AbstractWebApiResponse<StudentView> response = null;
		try
		{
			response = getGetSingleStudentState(client);
		}
		catch (IOException e)
		{
			fail(e.getMessage());
		}
		Map<String, Map<String, String>> links = response.getLinks();

		Map<String, String> link = links.get("deleteStudent");
		assertNotNull(link);
		assertFalse(link.isEmpty());
		checkLinkAndType(link,
			"http://localhost:8080/exam02/api/Students/" + response.getResponseData().stream().findFirst().get()
				.getId(), "application/json");
	}

	@Test public void testGetSingleStudentsHypermeidaPut()
	{
		final AbstractClient<StudentView> client = new WebApiClientStudent();
		AbstractWebApiResponse<StudentView> response = null;
		try
		{
			response = getGetSingleStudentState(client);
		}
		catch (IOException e)
		{
			fail(e.getMessage());
		}
		Map<String, Map<String, String>> links = response.getLinks();

		Map<String, String> link = links.get("updateStudent");
		assertNotNull(link);
		assertFalse(link.isEmpty());
		checkLinkAndType(link,
			"http://localhost:8080/exam02/api/Students/" + response.getResponseData().stream().findFirst().get()
				.getId(), "application/json");
	}

	@Test public void testGetSingleStudentsHypermeidaSelf()
	{
		final AbstractClient<StudentView> client = new WebApiClientStudent();
		AbstractWebApiResponse<StudentView> response = null;
		try
		{
			response = getGetSingleStudentState(client);
		}
		catch (IOException e)
		{
			fail(e.getMessage());
		}
		Map<String, Map<String, String>> links = response.getLinks();

		Map<String, String> link = links.get("self");
		assertNotNull(link);
		assertFalse(link.isEmpty());
		assertEquals(
			"http://localhost:8080/exam02/api/Students/" + response.getResponseData().stream().findFirst().get()
				.getId(), link.get("link"));
	}

	@Test public void testGetSingleStudentsHypermeidaGetAllStudents()
	{
		final AbstractClient<StudentView> client = new WebApiClientStudent();
		AbstractWebApiResponse<StudentView> response = null;
		try
		{
			response = getGetSingleStudentState(client);
		}
		catch (IOException e)
		{
			fail(e.getMessage());
		}
		Map<String, Map<String, String>> links = response.getLinks();

		Map<String, String> link = links.get("getAllStudents");
		assertNotNull(link);
		assertFalse(link.isEmpty());
		checkLinkAndType(link, "http://localhost:8080/exam02/api/Students", "application/json");
	}

	public void checkLinkAndType(Map<String, String> link_get, String link, String type)
	{
		assertEquals(link, link_get.get("link"));
		assertEquals(type, link_get.get("type"));
	}

}
