package de.fhws.fiw.fds.exam02.testCases;

import de.fhws.fiw.fds.exam02.api.*;
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

	public AbstractWebApiResponse<StudentView> getGetAllStudentsState(AbstractClient<StudentView> client)

	{
		try
		{
			return client.loadAllObjectsByUrl(studentURL);
		}
		catch (IOException e)
		{
			fail(e.getMessage());
		}
		return null;
	}

	public AbstractWebApiResponse<StudentView> getGetSingleStudentState(AbstractClient<StudentView> client)

	{
		AbstractWebApiResponse<StudentView> returnValue = null;
		try
		{
			AbstractWebApiResponse<StudentView> postResponse = client.postObject(studentURL, getEmptyStudent());
			String getUrl = postResponse.getLink("getStudent");
			returnValue = client.loadObjectByURL(getUrl);
			client.deleteObject(returnValue.getLink("deleteStudent"));
		}
		catch (IOException e)
		{
			fail(e.getMessage());
		}
		return returnValue;
	}

	@Test public void testGetAllStudentsHypermediaGetSingleStudentLink()
	{
		final AbstractClient<StudentView> client = new WebApiClientStudent();
		AbstractWebApiResponse<StudentView> response;
		response = getGetAllStudentsState(client);
		checkHypermediaLinks(response, "getStudent", "http://localhost:8080/exam02/api/Students/{id}",
			"application/json");
	}

	private void checkHypermediaLinks(AbstractWebApiResponse<StudentView> response, String getStudent, String link,
		String type)
	{
		Map<String, Map<String, String>> links = response.getLinks();
		Map<String, String> linkMap = links.get(getStudent);
		assertNotNull(linkMap);
		assertFalse(linkMap.isEmpty());
		checkLinkAndType(linkMap, link, type);
	}

	@Test public void testGetSingleStudentsHypermediaDelete()
	{
		final AbstractClient<StudentView> client = new WebApiClientStudent();
		AbstractWebApiResponse<StudentView> response;
		response = getGetSingleStudentState(client);
		checkHypermediaLinks(response, "deleteStudent",
			"http://localhost:8080/exam02/api/Students/" + response.getResponseData().stream().findFirst().get()
				.getId(), "application/json");
	}

	@Test public void testGetSingleStudentsHypermediaPut()
	{
		final AbstractClient<StudentView> client = new WebApiClientStudent();
		AbstractWebApiResponse<StudentView> response;
		response = getGetSingleStudentState(client);
		checkHypermediaLinks(response, "updateStudent",
			"http://localhost:8080/exam02/api/Students/" + response.getResponseData().stream().findFirst().get()
				.getId(), "application/json");
	}

	@Test public void testGetSingleStudentsHypermediaSelf()
	{
		final AbstractClient<StudentView> client = new WebApiClientStudent();
		AbstractWebApiResponse<StudentView> response;
		response = getGetSingleStudentState(client);

		checkHypermediaLinks(response, "self",
			"http://localhost:8080/exam02/api/Students/" + response.getResponseData().stream().findFirst().get()
				.getId(), null);
	}

	@Test public void testGetSingleStudentsHypermediaGetAllStudents()
	{
		final AbstractClient<StudentView> client = new WebApiClientStudent();
		AbstractWebApiResponse<StudentView> response;
		response = getGetSingleStudentState(client);
		checkHypermediaLinks(response, "getAllStudents", "http://localhost:8080/exam02/api/Students",
			"application/json");
	}

	public void checkLinkAndType(Map<String, String> link_get, String link, String type)
	{
		assertEquals(link, link_get.get("link"));
		assertEquals(type, link_get.get("type"));
	}

}
