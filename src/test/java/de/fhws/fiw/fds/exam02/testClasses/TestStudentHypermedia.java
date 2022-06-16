package de.fhws.fiw.fds.exam02.testClasses;

import com.owlike.genson.Genson;
import de.fhws.fiw.fds.exam02.api.*;
import de.fhws.fiw.fds.exam02.api.AbstractClient;
import de.fhws.fiw.fds.exam02.models.StudentView;
import de.fhws.fiw.fds.exam02.models.StudentView;
import junit.framework.TestFailure;
import okhttp3.Response;
import org.junit.Test;
import org.junit.internal.runners.statements.Fail;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
		String postUrl = getDispatcherState(client).getLink("createStudent");
		AbstractWebApiResponse<StudentView> postResponse = client.postObject(postUrl, getEmptyStudent());
		String getUrl = postResponse.getLink("getStudent");
		AbstractWebApiResponse<StudentView> returnValue = client.loadObjectByURL(getUrl);
		client.deleteObject(returnValue.getLink("deleteStudent"));
		return returnValue;
	}

	@Test public void testGetAllStudentsHypermeidaGetSingleStudentripLink()
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

	@Test public void testGetAllStudentsHypermeidaSearchByNameLink()
	{
		final AbstractClient<StudentView> client = new WebApiClientStudent();
		AbstractWebApiResponse<StudentView> response = null;
		try
		{
			response = getGetAllStudentsState(client);
		}
		catch (IOException e)
		{
			fail(e.getMessage());
		}
		Map<String, Map<String, String>> links = response.getLinks();

		Map<String, String> link = links.get("searchStudentByName");
		assertNotNull(link);
		assertFalse(link.isEmpty());
		checkLinkAndType(link, "http://localhost:8080/exam02/api/Students?name=Name", "application/json");
	}

	@Test public void testGetAllStudentsHypermeidaSearchByCityLink()
	{
		final AbstractClient<StudentView> client = new WebApiClientStudent();
		AbstractWebApiResponse<StudentView> response = null;
		try
		{
			response = getGetAllStudentsState(client);
		}
		catch (IOException e)
		{
			fail(e.getMessage());
		}
		Map<String, Map<String, String>> links = response.getLinks();

		Map<String, String> link = links.get("searchStudentByCity");
		assertNotNull(link);
		assertFalse(link.isEmpty());
		checkLinkAndType(link, "http://localhost:8080/exam02/api/Students?city=City", "application/json");
	}

	@Test public void testGetAllStudentsHypermeidaSearchByCountryLink()
	{
		final AbstractClient<StudentView> client = new WebApiClientStudent();
		AbstractWebApiResponse<StudentView> response = null;
		try
		{
			response = getGetAllStudentsState(client);
		}
		catch (IOException e)
		{
			fail(e.getMessage());

		}
		Map<String, Map<String, String>> links = response.getLinks();

		Map<String, String> link = links.get("searchStudentByCountry");
		assertNotNull(link);
		assertFalse(link.isEmpty());
		checkLinkAndType(link, "http://localhost:8080/exam02/api/Students?country=Country", "application/json");
	}

	@Test public void testGetAllStudentsHypermeidaSearchByDateLink()
	{
		final AbstractClient<StudentView> client = new WebApiClientStudent();
		AbstractWebApiResponse<StudentView> response = null;
		try
		{
			response = getGetAllStudentsState(client);
		}
		catch (IOException e)
		{
			fail(e.getMessage());
		}
		Map<String, Map<String, String>> links = response.getLinks();

		Map<String, String> link = links.get("searchStudentByDate");
		assertNotNull(link);
		assertFalse(link.isEmpty());
		checkLinkAndType(link, "http://localhost:8080/exam02/api/Students?start=1900-01-22&end=2022-12-05",
			"application/json");
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
