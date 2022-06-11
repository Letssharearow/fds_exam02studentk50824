///*
// * Copyright 2021 University of Applied Sciences Würzburg-Schweinfurt, Germany
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *    http://www.apache.org/licenses/LICENSE-2.0
// *
// *  Unless required by applicable law or agreed to in writing, software
// *  distributed under the License is distributed on an "AS IS" BASIS,
// *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// *  See the License for the specific language governing permissions and
// *  limitations under the License.
// */
//
//package de.fhws.fiw.fds.exam02.testClasses;
//
//import de.fhws.fiw.fds.exam02.api.WebApiClient;
//import de.fhws.fiw.fds.exam02.api.WebApiResponse;
//import de.fhws.fiw.fds.exam02.models.StudentTripView;
//import de.fhws.fiw.fds.exam02.models.StudentView;
//import org.junit.Test;
//
//import java.io.IOException;
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.Optional;
//
//import static org.junit.Assert.*;
//
//public class TestServlet
//{
//
//	//GET
//	@Test public void load_studentTrip_by_id_status200() throws IOException
//	{
//		final WebApiClient client = new WebApiClient();
//		StudentTripView studentTripViewPost = new StudentTripView("template", 0L,
//			(Arrays.asList(new StudentView("Julian", "Sehne", "BIN", 4, 00, ""))), LocalDate.of(1960, 2, 9),
//			LocalDate.of(1861, 3, 10), "partnerUni", "city", "country");
//		long id = client.loadStudentTripByURL(client.postStudentTrip(studentTripViewPost).getLocation())
//			.getResponseData().stream().findFirst().get().getId();
//
//		final WebApiResponse response = client.loadStudentTripById(id);
//
//		assertEquals(200, response.getLastStatusCode());
//		assertEquals(1, response.getResponseData().size());
//
//		final Optional<StudentTripView> result = response.getResponseData().stream().findFirst();
//		assertTrue(result.isPresent());
//		final StudentTripView studentTrip = result.get();
//
//		assertEquals("template", studentTrip.getName());
//		assertEquals(LocalDate.of(1960, 2, 9), studentTrip.getStart());
//		assertEquals(LocalDate.of(1861, 3, 10), studentTrip.getEnd());
//		assertEquals("partnerUni", studentTrip.getPartnerUniversity());
//		assertEquals("country", studentTrip.getCountry());
//		assertEquals("city", studentTrip.getCity());
//
//		Optional<StudentView> studentView = studentTrip.getStudents().stream().findFirst();
//		assertTrue(studentView.isPresent());
//		StudentView studentViewJulian = studentView.get();
//
//		assertEquals("Julian", studentViewJulian.getFirstName());
//		assertEquals("Sehne", studentViewJulian.getLastName());
//		assertEquals("BIN", studentViewJulian.getCourse());
//		assertEquals(4, studentViewJulian.getSemester());
//		assertEquals(4, studentViewJulian.getImmatriculationNumber());
//		assertEquals("4", studentViewJulian.getEmail());
//
//		assertEquals(204, client.deleteStudentTrip(studentTrip.getId()).getLastStatusCode());
//	}
//
//	@Test public void load_all_studentTrips_status200() throws IOException
//	{
//		final WebApiClient client = new WebApiClient();
//		StudentTripView studentTripViewPost = new StudentTripView("template", 0L,
//			(Arrays.asList(new StudentView("Julian", "Sehne", "BIN", 4, 00, ""))), LocalDate.of(1960, 2, 9),
//			LocalDate.of(1861, 3, 10), "partnerUni", "city", "country");
//		long studentTrip1Id = client.loadStudentTripByURL(client.postStudentTrip(studentTripViewPost).getLocation())
//			.getResponseData().stream().findFirst().get().getId();
//		long studentTrip2Id = client.loadStudentTripByURL(client.postStudentTrip(studentTripViewPost).getLocation())
//			.getResponseData().stream().findFirst().get().getId();
//
//		final WebApiResponse response = client.loadAllStudentTrips();
//		assertEquals(200, response.getLastStatusCode());
//
//		final Collection<StudentTripView> result = response.getResponseData();
//		assertFalse(result.isEmpty());
//		final StudentTripView studentTrip = result.stream().findFirst().get();
//
//		assertEquals("template", studentTrip.getName());
//		assertEquals(LocalDate.of(1960, 2, 9), studentTrip.getStart());
//		assertEquals(LocalDate.of(1861, 3, 10), studentTrip.getEnd());
//		assertEquals("partnerUni", studentTrip.getPartnerUniversity());
//		assertEquals("country", studentTrip.getCountry());
//		assertEquals("city", studentTrip.getCity());
//
//		Optional<StudentView> studentView = studentTrip.getStudents().stream().findFirst();
//		assertTrue(studentView.isPresent());
//		StudentView studentViewJulian = studentView.get();
//
//		assertEquals("Julian", studentViewJulian.getFirstName());
//		assertEquals("Sehne", studentViewJulian.getLastName());
//		assertEquals("BIN", studentViewJulian.getCourse());
//		assertEquals(4, studentViewJulian.getSemester());
//		assertEquals(4, studentViewJulian.getImmatriculationNumber());
//		assertEquals("4", studentViewJulian.getEmail());
//
//		assertEquals(204, client.deleteStudentTrip(studentTrip1Id).getLastStatusCode());
//		assertEquals(204, client.deleteStudentTrip(studentTrip2Id).getLastStatusCode());
//	}
//
//	@Test public void load_all_studentTrips_By_Name_Semester_Type_status200() throws IOException
//	{
//		final WebApiClient client = new WebApiClient();
//		long studentTrip1Id = client.loadStudentTripByURL(client.postStudentTrip(
//				new StudentTripView("duplicateNameForTesting", 0L, "we try to create an API for studentTrips",
//					(Collections.singletonList(new StudentView("Julian", "Sehne", "BIN", 4))),
//					(Collections.singletonList(new SupervisorView("Peter", "Braun", "Prof.", "peter.braun@fhws.de"))),
//					"2022ss", "duplicateTypeForTesting")).getLocation()).getResponseData().stream().findFirst().get()
//			.getId();
//
//		long studentTrip2Id = client.loadStudentTripByURL(client.postStudentTrip(
//				new StudentTripView("duplicateNameForTesting", 0L, "we try to create an API for studentTrips",
//					(Collections.singletonList(new StudentView("Julian", "Sehne", "BIN", 4))),
//					(Collections.singletonList(new SupervisorView("Peter", "Braun", "Prof.", "peter.braun@fhws.de"))),
//					"0000ss", "programming studentTrip")).getLocation()).getResponseData().stream().findFirst().get()
//			.getId();
//
//		long studentTrip3Id = client.loadStudentTripByURL(client.postStudentTrip(
//				new StudentTripView("template", "we try to create an API for studentTrips",
//					(Collections.singletonList(new StudentView("Julian", "Sehne", "BIN", 4))),
//					(Collections.singletonList(new SupervisorView("Peter", "Braun", "Prof.", "peter.braun@fhws.de"))),
//					"0000ss", "duplicateTypeForTesting")).getLocation()).getResponseData().stream().findFirst().get()
//			.getId();
//
//		final WebApiResponse responseName = client.loadAllStudentTripsByNameTypeAndSemester("duplicateNameForTesting",
//			"", "");
//		assertEquals(200, responseName.getLastStatusCode());
//		assertEquals(2, responseName.getResponseData().size());
//
//		final WebApiResponse responseType = client.loadAllStudentTripsByNameTypeAndSemester("",
//			"duplicateTypeForTesting", "");
//		assertEquals(200, responseType.getLastStatusCode());
//		assertEquals(2, responseType.getResponseData().size());
//
//		final WebApiResponse responseSemester = client.loadAllStudentTripsByNameTypeAndSemester("", "", "0000ss");
//		assertEquals(200, responseSemester.getLastStatusCode());
//		assertEquals(2, responseSemester.getResponseData().size());
//
//		assertEquals(204, client.deleteStudentTrip(studentTrip1Id).getLastStatusCode());
//		assertEquals(204, client.deleteStudentTrip(studentTrip2Id).getLastStatusCode());
//		assertEquals(204, client.deleteStudentTrip(studentTrip3Id).getLastStatusCode());
//	}
//
//	@Test public void load_all_studentTrips_By_Name_Semester_Type_status400()
//	{
//		final WebApiClient client = new WebApiClient();
//		long studentTrip1Id = 0;
//		try
//		{
//			studentTrip1Id = client.loadStudentTripByURL(client.postStudentTrip(
//					new StudentTripView("duplicateNameForTesting", "we try to create an API for studentTrips",
//						(Collections.singletonList(new StudentView("Julian", "Sehne", "BIN", 4))),
//						(Collections.singletonList(new SupervisorView("Peter", "Braun", "Prof.", "peter.braun@fhws.de"))),
//						"2022ss", "duplicateTypeForTesting")).getLocation()).getResponseData().stream().findFirst().get()
//				.getId();
//
//			long studentTrip2Id = client.loadStudentTripByURL(client.postStudentTrip(
//					new StudentTripView("duplicateNameForTesting", "we try to create an API for studentTrips",
//						(Collections.singletonList(new StudentView("Julian", "Sehne", "BIN", 4))),
//						(Collections.singletonList(new SupervisorView("Peter", "Braun", "Prof.", "peter.braun@fhws.de"))),
//						"0000ss", "programming studentTrip")).getLocation()).getResponseData().stream().findFirst().get()
//				.getId();
//
//			long studentTrip3Id = client.loadStudentTripByURL(client.postStudentTrip(
//					new StudentTripView("template", "we try to create an API for studentTrips",
//						(Collections.singletonList(new StudentView("Julian", "Sehne", "BIN", 4))),
//						(Collections.singletonList(new SupervisorView("Peter", "Braun", "Prof.", "peter.braun@fhws.de"))),
//						"0000ss", "duplicateTypeForTesting")).getLocation()).getResponseData().stream().findFirst().get()
//				.getId();
//
//			assertEquals(400, client.loadAllStudentTripsByNameTypeAndSemester("", "", "0000sw").getLastStatusCode());
//			assertEquals(400, client.loadAllStudentTripsByNameTypeAndSemester("", "", "a000ss").getLastStatusCode());
//			assertEquals(400, client.loadAllStudentTripsByNameTypeAndSemester("", "", "0000SW").getLastStatusCode());
//			assertEquals(400, client.loadAllStudentTripsByNameTypeAndSemester("", "", "0000SS").getLastStatusCode());
//			assertEquals(400, client.loadAllStudentTripsByNameTypeAndSemester("", "", "20ss").getLastStatusCode());
//
//			assertEquals(204, client.deleteStudentTrip(studentTrip1Id).getLastStatusCode());
//			assertEquals(204, client.deleteStudentTrip(studentTrip2Id).getLastStatusCode());
//			assertEquals(204, client.deleteStudentTrip(studentTrip3Id).getLastStatusCode());
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
//	}
//
//	@Test public void load_studentTrip_by_id_status404()
//	{
//		final WebApiClient client = new WebApiClient();
//		StudentTripView studentTripViewPost = new StudentTripView("template",
//			"we try to create an API for studentTrips", (Arrays.asList(new StudentView("Julian", "Sehne", "BIN", 4))),
//			(Arrays.asList(new SupervisorView("Peter", "Braun", "Prof.", "peter.braun@fhws.de"))), "2022ss",
//			"programming studentTrip");
//		long id = 0;
//		try
//		{
//			id = client.loadStudentTripByURL(client.postStudentTrip(studentTripViewPost).getLocation())
//				.getResponseData().stream().findFirst().get().getId();
//			final WebApiResponse response = client.loadStudentTripById(0L);
//			assertEquals(404, response.getLastStatusCode());
//			assertEquals(204, client.deleteStudentTrip(id).getLastStatusCode());
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
//
//	}
//
//	//POST
//	@Test public void post_studentTrip_status201()
//	{
//		StudentTripView studentTripPost = new StudentTripView("testStudentTrip", "",
//			Arrays.asList(new StudentView("Paul", "Nummer", "BEC", 2)),
//			Arrays.asList(new SupervisorView("Steffen", "Heinzl", "Prof", "steffen.heinzl@fhws.de")), "1999ws", "type");
//		final WebApiClient client = new WebApiClient();
//		WebApiResponse responsePost = null;
//		try
//		{
//			responsePost = client.postStudentTrip(studentTripPost);
//			assertEquals(201, responsePost.getLastStatusCode());
//			String location = responsePost.getLocation();
//			WebApiResponse responseGet = client.loadStudentTripByURL(location);
//
//			final Optional<StudentTripView> result = responseGet.getResponseData().stream().findFirst();
//			assertTrue(result.isPresent());
//			final StudentTripView studentTripGet = result.get();
//
//			assertEquals("testStudentTrip", studentTripGet.getName());
//			assertEquals("type", studentTripGet.getType());
//			assertEquals("1999ws", studentTripGet.getSemester());
//
//			Optional<StudentView> studentView = studentTripGet.getStudents().stream().findFirst();
//			assertTrue(studentView.isPresent());
//			StudentView studentViewJulian = studentView.get();
//
//			assertEquals("Paul", studentViewJulian.getFirstName());
//			assertEquals("Nummer", studentViewJulian.getLastName());
//			assertEquals("BEC", studentViewJulian.getCourse());
//			assertEquals(2, studentViewJulian.getSemester());
//
//			Optional<SupervisorView> superVisorView = studentTripGet.getSupervisors().stream().findFirst();
//			assertTrue(superVisorView.isPresent());
//			SupervisorView superVisorViewPresent = superVisorView.get();
//
//			assertEquals("Steffen", superVisorViewPresent.getFirstName());
//			assertEquals("Heinzl", superVisorViewPresent.getLastName());
//			assertEquals("Prof", superVisorViewPresent.getTitle());
//			assertEquals("steffen.heinzl@fhws.de", superVisorViewPresent.getEmail());
//
//			assertEquals(204, client.deleteStudentTrip(studentTripGet.getId()).getLastStatusCode());
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
//	}
//
//	@Test public void post_studentTrip_status400()
//	{
//		StudentTripView correctStudentTrip = new StudentTripView("testStudentTrip", "",
//			Arrays.asList(new StudentView("Paul", "Nummer", "BEC", 2)),
//			Arrays.asList(new SupervisorView("Steffen", "Heinzl", "Prof", "steffen.heinzl@fhws.de")), "1999ws", "type");
//		final WebApiClient client = new WebApiClient();
//		WebApiResponse responsePost = null;
//		try
//		{
//			//Verify this StudentTrip is postable
//			responsePost = client.postStudentTrip(correctStudentTrip);
//			assertEquals(201, responsePost.getLastStatusCode());
//
//			String location = responsePost.getLocation();
//			WebApiResponse responseGet = client.loadStudentTripByURL(location);
//			final Optional<StudentTripView> result = responseGet.getResponseData().stream().findFirst();
//			assertTrue(result.isPresent());
//			final StudentTripView studentTripGet = result.get();
//			assertEquals(204, client.deleteStudentTrip(studentTripGet.getId()).getLastStatusCode());
//
//			//verify these changes make it unpostable
//			StudentTripView incorrectStudentTrip = correctStudentTrip;
//
//			incorrectStudentTrip.setName("");
//			responsePost = client.postStudentTrip(incorrectStudentTrip);
//			assertEquals(400, responsePost.getLastStatusCode());
//			incorrectStudentTrip.setName(studentTripGet.getName());
//
//			responsePost = client.postStudentTrip(incorrectStudentTrip);
//			assertEquals(201, responsePost.getLastStatusCode());
//			assertEquals(204, client.deleteStudentTripByURL(responsePost.getLocation()).getLastStatusCode());
//
//			incorrectStudentTrip.setSemester("20ss");
//			responsePost = client.postStudentTrip(incorrectStudentTrip);
//			assertEquals(400, responsePost.getLastStatusCode());
//			incorrectStudentTrip.setSemester(studentTripGet.getSemester());
//
//			responsePost = client.postStudentTrip(incorrectStudentTrip);
//			assertEquals(201, responsePost.getLastStatusCode());
//			assertEquals(204, client.deleteStudentTripByURL(responsePost.getLocation()).getLastStatusCode());
//
//			incorrectStudentTrip.setSemester("2022sw");
//			responsePost = client.postStudentTrip(incorrectStudentTrip);
//			assertEquals(400, responsePost.getLastStatusCode());
//			incorrectStudentTrip.setSemester(studentTripGet.getSemester());
//
//			responsePost = client.postStudentTrip(incorrectStudentTrip);
//			assertEquals(201, responsePost.getLastStatusCode());
//			assertEquals(204, client.deleteStudentTripByURL(responsePost.getLocation()).getLastStatusCode());
//
//			incorrectStudentTrip.setSemester("2022ww");
//			responsePost = client.postStudentTrip(incorrectStudentTrip);
//			assertEquals(400, responsePost.getLastStatusCode());
//			incorrectStudentTrip.setSemester(studentTripGet.getSemester());
//
//			responsePost = client.postStudentTrip(incorrectStudentTrip);
//			assertEquals(201, responsePost.getLastStatusCode());
//			assertEquals(204, client.deleteStudentTripByURL(responsePost.getLocation()).getLastStatusCode());
//
//			final int semester = studentTripGet.getStudents().stream().findFirst().get().getSemester();
//			incorrectStudentTrip.getStudents().forEach(studentView -> studentView.setSemester(8));//only 1-7 allowed
//			responsePost = client.postStudentTrip(incorrectStudentTrip);
//			assertEquals(400, responsePost.getLastStatusCode());
//			incorrectStudentTrip.getStudents().forEach(studentView -> studentView.setSemester(semester));
//
//			responsePost = client.postStudentTrip(incorrectStudentTrip);
//			assertEquals(201, responsePost.getLastStatusCode());
//			assertEquals(204, client.deleteStudentTripByURL(responsePost.getLocation()).getLastStatusCode());
//
//			incorrectStudentTrip.getStudents().forEach(studentView -> studentView.setSemester(0));//only 1-7 allowed
//			responsePost = client.postStudentTrip(incorrectStudentTrip);
//			assertEquals(400, responsePost.getLastStatusCode());
//			incorrectStudentTrip.getStudents().forEach(studentView -> studentView.setSemester(semester));
//
//			responsePost = client.postStudentTrip(incorrectStudentTrip);
//			assertEquals(201, responsePost.getLastStatusCode());
//			assertEquals(204, client.deleteStudentTripByURL(responsePost.getLocation()).getLastStatusCode());
//
//			final String course = studentTripGet.getStudents().stream().findFirst().get().getCourse();
//
//			incorrectStudentTrip.getStudents()
//				.forEach(studentView -> studentView.setCourse("babi"));//only 3 capital letters allowed
//			responsePost = client.postStudentTrip(incorrectStudentTrip);
//			assertEquals(400, responsePost.getLastStatusCode());
//			incorrectStudentTrip.getStudents().forEach(studentView -> studentView.setCourse(course));
//
//			responsePost = client.postStudentTrip(incorrectStudentTrip);
//			assertEquals(201, responsePost.getLastStatusCode());
//			assertEquals(204, client.deleteStudentTripByURL(responsePost.getLocation()).getLastStatusCode());
//
//			incorrectStudentTrip.getStudents()
//				.forEach(studentView -> studentView.setCourse("EINI"));//only 3 capital letters allowed
//			responsePost = client.postStudentTrip(incorrectStudentTrip);
//			assertEquals(400, responsePost.getLastStatusCode());
//			incorrectStudentTrip.getStudents().forEach(studentView -> studentView.setCourse(course));
//
//			responsePost = client.postStudentTrip(incorrectStudentTrip);
//			assertEquals(201, responsePost.getLastStatusCode());
//			assertEquals(204, client.deleteStudentTripByURL(responsePost.getLocation()).getLastStatusCode());
//
//			incorrectStudentTrip.getSupervisors()
//				.forEach(supervisorView -> supervisorView.setEmail("wrongEmail.de")); //'@' and '.' are mandatory
//			responsePost = client.postStudentTrip(incorrectStudentTrip);
//			assertEquals(400, responsePost.getLastStatusCode());
//			final String email = studentTripGet.getSupervisors().stream().findFirst().get().getEmail();
//			incorrectStudentTrip.getSupervisors().forEach(superVisorView -> superVisorView.setEmail(email));
//
//			responsePost = client.postStudentTrip(incorrectStudentTrip);
//			assertEquals(201, responsePost.getLastStatusCode());
//			assertEquals(204, client.deleteStudentTripByURL(responsePost.getLocation()).getLastStatusCode());
//
//			incorrectStudentTrip.getSupervisors()
//				.forEach(supervisorView -> supervisorView.setEmail("a@ae")); //'@' and '.' are mandatory
//			responsePost = client.postStudentTrip(incorrectStudentTrip);
//			assertEquals(400, responsePost.getLastStatusCode());
//			incorrectStudentTrip.getSupervisors().forEach(superVisorView -> superVisorView.setEmail(email));
//
//			responsePost = client.postStudentTrip(incorrectStudentTrip);
//			assertEquals(201, responsePost.getLastStatusCode());
//			assertEquals(204, client.deleteStudentTripByURL(responsePost.getLocation()).getLastStatusCode());
//
//			incorrectStudentTrip.getSupervisors()
//				.forEach(supervisorView -> supervisorView.setEmail("z@.uu")); //'@' and '.' are mandatory
//			responsePost = client.postStudentTrip(incorrectStudentTrip);
//			assertEquals(400, responsePost.getLastStatusCode());
//			incorrectStudentTrip.getSupervisors().forEach(superVisorView -> superVisorView.setEmail(email));
//
//			responsePost = client.postStudentTrip(incorrectStudentTrip);
//			assertEquals(201, responsePost.getLastStatusCode());
//			assertEquals(204, client.deleteStudentTripByURL(responsePost.getLocation()).getLastStatusCode());
//
//			incorrectStudentTrip.getSupervisors()
//				.forEach(supervisorView -> supervisorView.setEmail("@t.de")); //'@' and '.' are mandatory
//			responsePost = client.postStudentTrip(incorrectStudentTrip);
//			assertEquals(400, responsePost.getLastStatusCode());
//			incorrectStudentTrip.getSupervisors().forEach(superVisorView -> superVisorView.setEmail(email));
//
//			responsePost = client.postStudentTrip(incorrectStudentTrip);
//			assertEquals(201, responsePost.getLastStatusCode());
//			assertEquals(204, client.deleteStudentTripByURL(responsePost.getLocation()).getLastStatusCode());
//
//			incorrectStudentTrip.getSupervisors()
//				.forEach(supervisorView -> supervisorView.setEmail("b@bb.")); //'@' and '.' are mandatory
//			responsePost = client.postStudentTrip(incorrectStudentTrip);
//			assertEquals(400, responsePost.getLastStatusCode());
//			incorrectStudentTrip.getSupervisors().forEach(superVisorView -> superVisorView.setEmail(email));
//
//			responsePost = client.postStudentTrip(incorrectStudentTrip);
//			assertEquals(201, responsePost.getLastStatusCode());
//			assertEquals(204, client.deleteStudentTripByURL(responsePost.getLocation()).getLastStatusCode());
//
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
//
//	}
//
//	//DELETE
//	@Test public void delete_studentTrip_status204()
//	{
//		final WebApiClient client = new WebApiClient();
//		StudentTripView studentTripViewPost = new StudentTripView("template",
//			"we try to create an API for studentTrips", (Arrays.asList(new StudentView("Julian", "Sehne", "BIN", 4))),
//			(Arrays.asList(new SupervisorView("Peter", "Braun", "Prof.", "peter.braun@fhws.de"))), "2022ss",
//			"programming studentTrip");
//		long id = 0;
//		try
//		{
//			id = client.loadStudentTripByURL(client.postStudentTrip(studentTripViewPost).getLocation())
//				.getResponseData().stream().findFirst().get().getId();
//			final WebApiResponse response = client.deleteStudentTrip(id);
//			assertEquals(204, response.getLastStatusCode());
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
//
//	}
//
//	@Test public void delete_studentTrip_status404()
//	{
//		final WebApiClient client = new WebApiClient();
//		StudentTripView studentTripViewPost = new StudentTripView("template",
//			"we try to create an API for studentTrips", (Arrays.asList(new StudentView("Julian", "Sehne", "BIN", 4))),
//			(Arrays.asList(new SupervisorView("Peter", "Braun", "Prof.", "peter.braun@fhws.de"))), "2022ss",
//			"programming studentTrip");
//		studentTripViewPost.setId(187);
//		long id = 0;
//		try
//		{
//			id = client.loadStudentTripByURL(client.postStudentTrip(studentTripViewPost).getLocation())
//				.getResponseData().stream().findFirst().get().getId();
//			final WebApiResponse response = client.deleteStudentTrip(0L);
//			assertEquals(404, response.getLastStatusCode());
//			assertEquals(204, client.deleteStudentTrip(id).getLastStatusCode());
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
//
//	}
//
//	//PUT
//	@Test public void put_studentTrip_status204()
//	{
//		final WebApiClient client = new WebApiClient();
//		StudentTripView studentTripViewPost = new StudentTripView("template",
//			"we try to create an API for studentTrips", (Arrays.asList(new StudentView("Julian", "Sehne", "BIN", 4))),
//			(Arrays.asList(new SupervisorView("Peter", "Braun", "Prof.", "peter.braun@fhws.de"))), "2022ss",
//			"programming studentTrip");
//		long studentTripId = 0;
//		try
//		{
//			studentTripId = client.loadStudentTripByURL(client.postStudentTrip(studentTripViewPost).getLocation())
//				.getResponseData().stream().findFirst().get().getId();
//			StudentTripView studentTripViewPut = new StudentTripView("newName", "newDescription",
//				(Arrays.asList(new StudentView("newStudentName", "newStudentLastName", "BAB", 7))), (Arrays.asList(
//				new SupervisorView("newSupervisorName", "newSupervisorLastName", "newTitle", "newEmail@test.de"))),
//				"0000ss", "newType");
//
//			studentTripViewPut.setId(studentTripId);
//
//			final WebApiResponse responsePut = client.putStudentTrip(studentTripViewPut, studentTripId);
//			assertEquals(204, responsePut.getLastStatusCode());
//
//			WebApiResponse responseGet = client.loadStudentTripById(studentTripId);
//
//			final Optional<StudentTripView> result = responseGet.getResponseData().stream().findFirst();
//			assertTrue(result.isPresent());
//			final StudentTripView studentTripGet = result.get();
//
//			assertEquals("newName", studentTripGet.getName());
//			assertEquals("newType", studentTripGet.getType());
//			assertEquals("0000ss", studentTripGet.getSemester());
//
//			Optional<StudentView> studentView = studentTripGet.getStudents().stream().findFirst();
//			assertTrue(studentView.isPresent());
//			StudentView studentViewJulian = studentView.get();
//
//			assertEquals("newStudentName", studentViewJulian.getFirstName());
//			assertEquals("newStudentLastName", studentViewJulian.getLastName());
//			assertEquals("BAB", studentViewJulian.getCourse());
//			assertEquals(7, studentViewJulian.getSemester());
//
//			Optional<SupervisorView> superVisorView = studentTripGet.getSupervisors().stream().findFirst();
//			assertTrue(superVisorView.isPresent());
//			SupervisorView superVisorViewPresent = superVisorView.get();
//
//			assertEquals("newSupervisorName", superVisorViewPresent.getFirstName());
//			assertEquals("newSupervisorLastName", superVisorViewPresent.getLastName());
//			assertEquals("newTitle", superVisorViewPresent.getTitle());
//			assertEquals("newEmail@test.de", superVisorViewPresent.getEmail());
//
//			assertEquals(204, client.deleteStudentTrip(studentTripGet.getId()).getLastStatusCode());
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
//
//	}
//
//	@Test public void put_studentTrip_status404()
//	{
//		final WebApiClient client = new WebApiClient();
//		StudentTripView studentTripViewPost = new StudentTripView("template",
//			"we try to create an API for studentTrips", (Arrays.asList(new StudentView("Julian", "Sehne", "BIN", 4))),
//			(Arrays.asList(new SupervisorView("Peter", "Braun", "Prof.", "peter.braun@fhws.de"))), "2022ss",
//			"programming studentTrip");
//		long studentTripId = 0;
//		try
//		{
//			studentTripId = client.loadStudentTripByURL(client.postStudentTrip(studentTripViewPost).getLocation())
//				.getResponseData().stream().findFirst().get().getId();
//			StudentTripView studentTripViewPut = new StudentTripView("newName", "newDescription",
//				(Arrays.asList(new StudentView("newStudentName", "newStudentLastName", "newCourse", 7))),
//				(Arrays.asList(
//					new SupervisorView("newSupervisorName", "newSupervisorLastName", "newTitle", "newEmail"))),
//				"newSemester", "newType");
//
//			studentTripViewPut.setId(0L);
//
//			final WebApiResponse responsePut = client.putStudentTrip(studentTripViewPut, 0L);
//			assertEquals(404, responsePut.getLastStatusCode());
//
//			WebApiResponse responseGet = client.loadStudentTripById(studentTripId);
//
//			final Optional<StudentTripView> result = responseGet.getResponseData().stream().findFirst();
//			assertTrue(result.isPresent());
//			final StudentTripView studentTripGet = result.get();
//
//			assertEquals("template", studentTripGet.getName());
//			assertEquals("programming studentTrip", studentTripGet.getType());
//			assertEquals("2022ss", studentTripGet.getSemester());
//
//			Optional<StudentView> studentView = studentTripGet.getStudents().stream().findFirst();
//			assertTrue(studentView.isPresent());
//			StudentView studentViewJulian = studentView.get();
//
//			assertEquals("Julian", studentViewJulian.getFirstName());
//			assertEquals("Sehne", studentViewJulian.getLastName());
//			assertEquals("BIN", studentViewJulian.getCourse());
//			assertEquals(4, studentViewJulian.getSemester());
//
//			Optional<SupervisorView> superVisorView = studentTripGet.getSupervisors().stream().findFirst();
//			assertTrue(superVisorView.isPresent());
//			SupervisorView superVisorViewPresent = superVisorView.get();
//
//			assertEquals("Peter", superVisorViewPresent.getFirstName());
//			assertEquals("Braun", superVisorViewPresent.getLastName());
//			assertEquals("Prof.", superVisorViewPresent.getTitle());
//			assertEquals("peter.braun@fhws.de", superVisorViewPresent.getEmail());
//
//			assertEquals(204, client.deleteStudentTrip(studentTripGet.getId()).getLastStatusCode());
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
//
//	}
//
//	@Test public void put_studentTrip_status400()
//	{
//		final WebApiClient client = new WebApiClient();
//		StudentTripView studentTripViewPost = new StudentTripView("template",
//			"we try to create an API for studentTrips", (Arrays.asList(new StudentView("Julian", "Sehne", "BIN", 4))),
//			(Arrays.asList(new SupervisorView("Peter", "Braun", "Prof.", "peter.braun@fhws.de"))), "2022ss",
//			"programming studentTrip");
//		long studentTripId = 0;
//		try
//		{
//			studentTripId = client.loadStudentTripByURL(client.postStudentTrip(studentTripViewPost).getLocation())
//				.getResponseData().stream().findFirst().get().getId();
//
//			studentTripViewPost.setId(20);
//
//			WebApiResponse responsePut = client.putStudentTrip(studentTripViewPost, 1L);
//			assertEquals(400, responsePut.getLastStatusCode());
//			studentTripViewPost.setId(studentTripId);
//
//			responsePut = client.putStudentTrip(studentTripViewPost, studentTripId);
//			assertEquals(204, responsePut.getLastStatusCode());
//
//			WebApiResponse responseGet = client.loadStudentTripById(studentTripId);
//			{
//				final Optional<StudentTripView> result = responseGet.getResponseData().stream().findFirst();
//				assertTrue(result.isPresent());
//				final StudentTripView studentTripGet = result.get();
//
//				//verify these changes make it unputtable
//				studentTripViewPost.setName("");
//				responsePut = client.putStudentTrip(studentTripViewPost, studentTripId);
//				assertEquals(400, responsePut.getLastStatusCode());
//				studentTripViewPost.setName(studentTripGet.getName());
//
//				responsePut = client.putStudentTrip(studentTripViewPost, studentTripId);
//				assertEquals(204, responsePut.getLastStatusCode());
//
//				studentTripViewPost.setSemester("20ss");
//				responsePut = client.putStudentTrip(studentTripViewPost, studentTripId);
//				assertEquals(400, responsePut.getLastStatusCode());
//				studentTripViewPost.setSemester(studentTripGet.getSemester());
//
//				responsePut = client.putStudentTrip(studentTripViewPost, studentTripId);
//				assertEquals(204, responsePut.getLastStatusCode());
//
//				studentTripViewPost.setSemester("2022sw");
//				responsePut = client.putStudentTrip(studentTripViewPost, studentTripId);
//				assertEquals(400, responsePut.getLastStatusCode());
//				studentTripViewPost.setSemester(studentTripGet.getSemester());
//
//				responsePut = client.putStudentTrip(studentTripViewPost, studentTripId);
//				assertEquals(204, responsePut.getLastStatusCode());
//
//				studentTripViewPost.setSemester("2022ww");
//				responsePut = client.putStudentTrip(studentTripViewPost, studentTripId);
//				assertEquals(400, responsePut.getLastStatusCode());
//				studentTripViewPost.setSemester(studentTripGet.getSemester());
//
//				responsePut = client.putStudentTrip(studentTripViewPost, studentTripId);
//				assertEquals(204, responsePut.getLastStatusCode());
//
//				final int semester = studentTripGet.getStudents().stream().findFirst().get().getSemester();
//				studentTripViewPost.getStudents().forEach(studentView -> studentView.setSemester(8));//only 1-7 allowed
//				responsePut = client.putStudentTrip(studentTripViewPost, studentTripId);
//				assertEquals(400, responsePut.getLastStatusCode());
//				studentTripViewPost.getStudents().forEach(studentView -> studentView.setSemester(semester));
//
//				responsePut = client.putStudentTrip(studentTripViewPost, studentTripId);
//				assertEquals(204, responsePut.getLastStatusCode());
//
//				studentTripViewPost.getStudents().forEach(studentView -> studentView.setSemester(0));//only 1-7 allowed
//				responsePut = client.putStudentTrip(studentTripViewPost, studentTripId);
//				assertEquals(400, responsePut.getLastStatusCode());
//				studentTripViewPost.getStudents().forEach(studentView -> studentView.setSemester(semester));
//
//				responsePut = client.putStudentTrip(studentTripViewPost, studentTripId);
//				assertEquals(204, responsePut.getLastStatusCode());
//
//				final String course = studentTripGet.getStudents().stream().findFirst().get().getCourse();
//
//				studentTripViewPost.getStudents()
//					.forEach(studentView -> studentView.setCourse("babi"));//only 3 capital letters allowed
//				responsePut = client.putStudentTrip(studentTripViewPost, studentTripId);
//				assertEquals(400, responsePut.getLastStatusCode());
//				studentTripViewPost.getStudents().forEach(studentView -> studentView.setCourse(course));
//
//				responsePut = client.putStudentTrip(studentTripViewPost, studentTripId);
//				assertEquals(204, responsePut.getLastStatusCode());
//
//				studentTripViewPost.getStudents()
//					.forEach(studentView -> studentView.setCourse("EINI"));//only 3 capital letters allowed
//				responsePut = client.putStudentTrip(studentTripViewPost, studentTripId);
//				assertEquals(400, responsePut.getLastStatusCode());
//				studentTripViewPost.getStudents().forEach(studentView -> studentView.setCourse(course));
//
//				responsePut = client.putStudentTrip(studentTripViewPost, studentTripId);
//				assertEquals(204, responsePut.getLastStatusCode());
//
//				studentTripViewPost.getSupervisors()
//					.forEach(supervisorView -> supervisorView.setEmail("wrongEmail.de")); //'@' and '.' are mandatory
//				responsePut = client.putStudentTrip(studentTripViewPost, studentTripId);
//				assertEquals(400, responsePut.getLastStatusCode());
//				final String email = studentTripGet.getSupervisors().stream().findFirst().get().getEmail();
//				studentTripViewPost.getSupervisors().forEach(superVisorView -> superVisorView.setEmail(email));
//
//				responsePut = client.putStudentTrip(studentTripViewPost, studentTripId);
//				assertEquals(204, responsePut.getLastStatusCode());
//
//				studentTripViewPost.getSupervisors()
//					.forEach(supervisorView -> supervisorView.setEmail("a@ae")); //'@' and '.' are mandatory
//				responsePut = client.putStudentTrip(studentTripViewPost, studentTripId);
//				assertEquals(400, responsePut.getLastStatusCode());
//				studentTripViewPost.getSupervisors().forEach(superVisorView -> superVisorView.setEmail(email));
//
//				responsePut = client.putStudentTrip(studentTripViewPost, studentTripId);
//				assertEquals(204, responsePut.getLastStatusCode());
//
//				studentTripViewPost.getSupervisors()
//					.forEach(supervisorView -> supervisorView.setEmail("z@.uu")); //'@' and '.' are mandatory
//				responsePut = client.putStudentTrip(studentTripViewPost, studentTripId);
//				assertEquals(400, responsePut.getLastStatusCode());
//				studentTripViewPost.getSupervisors().forEach(superVisorView -> superVisorView.setEmail(email));
//
//				responsePut = client.putStudentTrip(studentTripViewPost, studentTripId);
//				assertEquals(204, responsePut.getLastStatusCode());
//
//				studentTripViewPost.getSupervisors()
//					.forEach(supervisorView -> supervisorView.setEmail("@t.de")); //'@' and '.' are mandatory
//				responsePut = client.putStudentTrip(studentTripViewPost, studentTripId);
//				assertEquals(400, responsePut.getLastStatusCode());
//				studentTripViewPost.getSupervisors().forEach(superVisorView -> superVisorView.setEmail(email));
//
//				responsePut = client.putStudentTrip(studentTripViewPost, studentTripId);
//				assertEquals(204, responsePut.getLastStatusCode());
//
//				studentTripViewPost.getSupervisors()
//					.forEach(supervisorView -> supervisorView.setEmail("b@bb.")); //'@' and '.' are mandatory
//				responsePut = client.putStudentTrip(studentTripViewPost, studentTripId);
//				assertEquals(400, responsePut.getLastStatusCode());
//				studentTripViewPost.getSupervisors().forEach(superVisorView -> superVisorView.setEmail(email));
//
//				responsePut = client.putStudentTrip(studentTripViewPost, studentTripId);
//				assertEquals(204, responsePut.getLastStatusCode());
//
//			}
//			{
//				final Optional<StudentTripView> result = responseGet.getResponseData().stream().findFirst();
//				assertTrue(result.isPresent());
//				final StudentTripView studentTripGet = result.get();
//
//				assertEquals("template", studentTripGet.getName());
//				assertEquals("programming studentTrip", studentTripGet.getType());
//				assertEquals("2022ss", studentTripGet.getSemester());
//
//				Optional<StudentView> studentView = studentTripGet.getStudents().stream().findFirst();
//				assertTrue(studentView.isPresent());
//				StudentView studentViewJulian = studentView.get();
//
//				assertEquals("Julian", studentViewJulian.getFirstName());
//				assertEquals("Sehne", studentViewJulian.getLastName());
//				assertEquals("BIN", studentViewJulian.getCourse());
//				assertEquals(4, studentViewJulian.getSemester());
//
//				Optional<SupervisorView> superVisorView = studentTripGet.getSupervisors().stream().findFirst();
//				assertTrue(superVisorView.isPresent());
//				SupervisorView superVisorViewPresent = superVisorView.get();
//
//				assertEquals("Peter", superVisorViewPresent.getFirstName());
//				assertEquals("Braun", superVisorViewPresent.getLastName());
//				assertEquals("Prof.", superVisorViewPresent.getTitle());
//				assertEquals("peter.braun@fhws.de", superVisorViewPresent.getEmail());
//
//				assertEquals(204, client.deleteStudentTrip(studentTripGet.getId()).getLastStatusCode());
//			}
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
//
//	}
//}
