package myserver;

import org.springframework.web.bind.annotation.*; 
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.servlet.http.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Date;
import java.text.SimpleDateFormat;

import org.json.JSONObject;
import org.json.JSONArray;

import java.io.*; 
import java.net.*;

@RestController
public class UserController {

@RequestMapping(value = "/searchPatient", method = RequestMethod.POST) // <-- setup the endpoint URL at /hello with the HTTP POST method
	public ResponseEntity<String> searchPatient(@RequestBody String payload, HttpServletRequest request) {
		
		JSONObject payloadObj = new JSONObject(payload);
		int id = payloadObj.getInt("id"); 

		HttpHeaders responseHeaders = new HttpHeaders(); 
    	responseHeaders.set("Content-Type", "application/json");
		Connection conn = null;
		JSONArray patientArray = new JSONArray();

	    try {
	    	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Phd?useUnicode=true&characterEncoding=UTF-8", "root", "snowleopard");
			String query = "SELECT * FROM Phd.Patient WHERE Patient.ID = ?";
			PreparedStatement stmt = null;
	        stmt = conn.prepareStatement(query);
	        stmt.setInt(1, id);
	        ResultSet rs = stmt.executeQuery();
	        
	        while (rs.next()) {
	            int idNum = rs.getInt("Patient.id");
	            String fName = rs.getString("Patient.FName");
	            String lName = rs.getString("Patient.LName");
	            String gender = rs.getString("Patient.Gender");
	            Date dob = rs.getDate("Patient.DOB");
	            String street = rs.getString("Patient.Address_Street");
	            String city = rs.getString("Patient.Address_City");
	            String state = rs.getString("Patient.Address_State");
	            String phoneNum = rs.getString("Patient.PhoneNum");

	            JSONObject obj = new JSONObject();
	            obj.put("ID", idNum);
	            obj.put("Name", fName + ' ' + lName);
	            obj.put("Gender", gender);
	            obj.put("Date of Birth", dob);
	            obj.put("Address", street + ' ' + city + ' ' + state);
	            obj.put("Phone Number", phoneNum);
	            patientArray.put(obj);
	        
	    } 
	}catch (Exception e ) {
	    	return new ResponseEntity("{\"message\":\"Error. Try Again\"}", responseHeaders, HttpStatus.BAD_REQUEST);
	    } finally {
	    	try {
	    		if (conn != null) { conn.close(); }
	    	}catch(SQLException se) {
	    		return new ResponseEntity("{\"message\":\"Error. Try Again\"}", responseHeaders, HttpStatus.BAD_REQUEST);
	    	}
	        
	    }
		return new ResponseEntity(patientArray.toString(), responseHeaders, HttpStatus.OK);
	}

@RequestMapping(value = "/addPatient", method = RequestMethod.POST) // <-- setup the endpoint URL at /hello with the HTTP POST method
	public ResponseEntity<String> addPatient(@RequestBody String payload, HttpServletRequest request) {
		
		JSONObject payloadObj = new JSONObject(payload);
		int id = payloadObj.getInt("id"); 
		String fName = payloadObj.getString("FName");
		String lName = payloadObj.getString("LName");
		String gender = payloadObj.getString("Gender");
		String dob = payloadObj.getString("DOB");
		String street = payloadObj.getString("Address_Street");
		String city = payloadObj.getString("Address_City");
		String state = payloadObj.getString("Address_State");
		String phoneNum = payloadObj.getString("PhoneNum");

		HttpHeaders responseHeaders = new HttpHeaders(); 
    	responseHeaders.set("Content-Type", "application/json");
		Connection conn = null;

	    try {
	    	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Phd?useUnicode=true&characterEncoding=UTF-8", "root", "snowleopard");
	    	String query = "INSERT INTO Phd.Patient VALUES (?,?,?,?,?,?,?,?,?)";
			PreparedStatement stmt = null;
	        stmt = conn.prepareStatement(query);
	        stmt.setInt(1, id);
	        stmt.setString(2, fName);
	        stmt.setString(3, lName);
	        stmt.setString(4, gender);
	        Date date=Date.valueOf(dob);
	        stmt.setDate(5, date);
	        stmt.setString(6, street);
	        stmt.setString(7, city);
	        stmt.setString(8, state);
	        stmt.setString(9, phoneNum);
	        stmt.executeUpdate();

	}catch (Exception e ) {
	    	return new ResponseEntity(e, responseHeaders, HttpStatus.BAD_REQUEST);
	    } finally {
	    	try {
	    		if (conn != null) { conn.close(); }
	    	}catch(SQLException se) {
	    		return new ResponseEntity(se, responseHeaders, HttpStatus.BAD_REQUEST);

	    	}
	        
	    }
		return new ResponseEntity("{\"message\":\"Success\"}", responseHeaders, HttpStatus.OK);
	}

@RequestMapping(value = "/searchHospital", method = RequestMethod.POST) // <-- setup the endpoint URL at /hello with the HTTP POST method
	public ResponseEntity<String> searchHospital(@RequestBody String payload, HttpServletRequest request) {
		
		JSONObject payloadObj = new JSONObject(payload);
		int id = payloadObj.getInt("id"); 

		HttpHeaders responseHeaders = new HttpHeaders(); 
    	responseHeaders.set("Content-Type", "application/json");
		Connection conn = null;
		JSONArray hospitalArray = new JSONArray();

	    try {
	    	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Phd?useUnicode=true&characterEncoding=UTF-8", "root", "snowleopard");
			String query = "SELECT * FROM Phd.Hospital WHERE Hospital.ID = ?";
			PreparedStatement stmt = null;
	        stmt = conn.prepareStatement(query);
	        stmt.setInt(1, id);
	        ResultSet rs = stmt.executeQuery();
	        
	        while (rs.next()) {
	            int idNum = rs.getInt("Hospital.ID");
	            String name = rs.getString("Hospital.Name");
	            String facility = rs.getString("Hospital.FacilityType");
	            String street = rs.getString("Hospital.Address_Street");
	            String city = rs.getString("Hospital.Address_City");
	            String state = rs.getString("Hospital.Address_State");

	            JSONObject obj = new JSONObject();
	            obj.put("ID", idNum);
	            obj.put("Name", name);
	            obj.put("Facility Type", facility);
	            obj.put("Address", street + ' ' + city + ' ' + state);
	            hospitalArray.put(obj);
	        
	    } 
	}catch (Exception e ) {
	    	return new ResponseEntity(e, responseHeaders, HttpStatus.BAD_REQUEST);
	    } finally {
	    	try {
	    		if (conn != null) { conn.close(); }
	    	}catch(SQLException se) {
	    		return new ResponseEntity(se, responseHeaders, HttpStatus.BAD_REQUEST);
	    	}
	        
	    }
		return new ResponseEntity(hospitalArray.toString(), responseHeaders, HttpStatus.OK);
	}

@RequestMapping(value = "/addHospital", method = RequestMethod.POST) // <-- setup the endpoint URL at /hello with the HTTP POST method
	public ResponseEntity<String> addHospital(@RequestBody String payload, HttpServletRequest request) {
		
		JSONObject payloadObj = new JSONObject(payload);
		int id = payloadObj.getInt("id"); 
		String name = payloadObj.getString("Name");
		String facility = payloadObj.getString("Facility");
		String street = payloadObj.getString("Address_Street");
		String city = payloadObj.getString("Address_City");
		String state = payloadObj.getString("Address_State");

		HttpHeaders responseHeaders = new HttpHeaders(); 
    	responseHeaders.set("Content-Type", "application/json");
		Connection conn = null;

	    try {
	    	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Phd?useUnicode=true&characterEncoding=UTF-8", "root", "snowleopard");
	    	String query = "INSERT INTO Phd.Hospital VALUES (?,?,?,?,?,?)";
			PreparedStatement stmt = null;
	        stmt = conn.prepareStatement(query);
	        stmt.setInt(1, id);
	        stmt.setString(2, name);
	        stmt.setString(3, facility);
	        stmt.setString(4, street);
	        stmt.setString(5, city);
	        stmt.setString(6, state);
	        stmt.executeUpdate();

	}catch (Exception e ) {
	    	return new ResponseEntity(e, responseHeaders, HttpStatus.BAD_REQUEST);
	    } finally {
	    	try {
	    		if (conn != null) { conn.close(); }
	    	}catch(SQLException se) {
	    		return new ResponseEntity(se, responseHeaders, HttpStatus.BAD_REQUEST);

	    	}
	        
	    }
		return new ResponseEntity("{\"message\":\"Success\"}", responseHeaders, HttpStatus.OK);
	}

@RequestMapping(value = "/searchDoctor", method = RequestMethod.POST) // <-- setup the endpoint URL at /hello with the HTTP POST method
	public ResponseEntity<String> searchDoctor(@RequestBody String payload, HttpServletRequest request) {
		
		JSONObject payloadObj = new JSONObject(payload);
		int id = payloadObj.getInt("id"); 

		HttpHeaders responseHeaders = new HttpHeaders(); 
    	responseHeaders.set("Content-Type", "application/json");
		Connection conn = null;
		JSONArray doctorArray = new JSONArray();

	    try {
	    	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Phd?useUnicode=true&characterEncoding=UTF-8", "root", "snowleopard");
			String query = "SELECT * FROM Phd.Doctor WHERE Doctor.ID = ?";
			PreparedStatement stmt = null;
	        stmt = conn.prepareStatement(query);
	        stmt.setInt(1, id);
	        ResultSet rs = stmt.executeQuery();
	        
	        while (rs.next()) {
	            int idNum = rs.getInt("Doctor.id");
	            String fName = rs.getString("Doctor.FName");
	            String lName = rs.getString("Doctor.LName");
	            String specialty = rs.getString("Doctor.Specialty");
	            String phoneNum = rs.getString("Doctor.PhoneNum");

	            JSONObject obj = new JSONObject();
	            obj.put("ID", idNum);
	            obj.put("Name", fName + ' ' + lName);
	            obj.put("Specialty", specialty);
	            obj.put("Phone Number", phoneNum);
	            doctorArray.put(obj);
	        
	    } 
	}catch (Exception e ) {
	    	return new ResponseEntity("{\"message\":\"Error. Try Again\"}", responseHeaders, HttpStatus.BAD_REQUEST);
	    } finally {
	    	try {
	    		if (conn != null) { conn.close(); }
	    	}catch(SQLException se) {
	    		return new ResponseEntity("{\"message\":\"Error. Try Again\"}", responseHeaders, HttpStatus.BAD_REQUEST);
	    	}
	        
	    }
		return new ResponseEntity(doctorArray.toString(), responseHeaders, HttpStatus.OK);
	}


@RequestMapping(value = "/addDoctor", method = RequestMethod.POST) // <-- setup the endpoint URL at /hello with the HTTP POST method
	public ResponseEntity<String> addDoctor(@RequestBody String payload, HttpServletRequest request) {
		
		JSONObject payloadObj = new JSONObject(payload);
		int id = payloadObj.getInt("id"); 
		String fName = payloadObj.getString("FName");
		String lName = payloadObj.getString("LName");
		String specialty = payloadObj.getString("Specialty");
		String phoneNum = payloadObj.getString("PhoneNum");

		HttpHeaders responseHeaders = new HttpHeaders(); 
    	responseHeaders.set("Content-Type", "application/json");
		Connection conn = null;

	    try {
	    	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Phd?useUnicode=true&characterEncoding=UTF-8", "root", "snowleopard");
	    	String query = "INSERT INTO Phd.Doctor VALUES (?,?,?,?,?)";
			PreparedStatement stmt = null;
	        stmt = conn.prepareStatement(query);
	        stmt.setInt(1, id);
	        stmt.setString(2, fName);
	        stmt.setString(3, lName);
	        stmt.setString(4, specialty);
	        stmt.setString(5, phoneNum);
	        stmt.executeUpdate();

	}catch (Exception e ) {
	    	return new ResponseEntity(e, responseHeaders, HttpStatus.BAD_REQUEST);
	    } finally {
	    	try {
	    		if (conn != null) { conn.close(); }
	    	}catch(SQLException se) {
	    		return new ResponseEntity(se, responseHeaders, HttpStatus.BAD_REQUEST);

	    	}
	        
	    }
		return new ResponseEntity("{\"message\":\"Success\"}", responseHeaders, HttpStatus.OK);
	}












@RequestMapping(value = "/searchPatientAttendance", method = RequestMethod.POST) // <-- setup the endpoint URL at /hello with the HTTP POST method
	public ResponseEntity<String> searchPatientAttendance(@RequestBody String payload, HttpServletRequest request) {
		
		JSONObject payloadObj = new JSONObject(payload);
		String fName = payloadObj.getString("fName"); 
		String lName = payloadObj.getString("lName");

		HttpHeaders responseHeaders = new HttpHeaders(); 
    	responseHeaders.set("Content-Type", "application/json");
		Connection conn = null;
		JSONArray patientArray = new JSONArray();

	    try {
	    	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Phd?useUnicode=true&characterEncoding=UTF-8", "root", "snowleopard");
			String query = "select * from PatientAttending as pa inner join Hospital as h on pa.HID = h.ID inner join Patient as p on pa.PID = p.ID where FName LIKE ? AND LName LIKE ? order by h.Name";
			PreparedStatement stmt = null;
	        stmt = conn.prepareStatement(query);
	        stmt.setString(1, fName+'%');
	        stmt.setString(2, lName+'%');
	        ResultSet rs = stmt.executeQuery();
	        
	        while (rs.next()) {
	            int idNum = rs.getInt("p.ID");
	            int hosID = rs.getInt("h.ID");
	            fName = rs.getString("p.FName");
	            lName = rs.getString("p.LName");
	            String hosName = rs.getString("h.Name");
	            String facility = rs.getString("h.FacilityType");

	            JSONObject obj = new JSONObject();
	            obj.put("Name", fName + ' ' + lName);
	            obj.put("Facility Type", facility);
	            obj.put("Hospital ID", hosID);
	            obj.put("Hospital Name", hosName);
	            obj.put("Patient ID", idNum);

	            patientArray.put(obj);
	        
	    } 
	}catch (Exception e ) {
	    	return new ResponseEntity(e, responseHeaders, HttpStatus.BAD_REQUEST);
	    } finally {
	    	try {
	    		if (conn != null) { conn.close(); }
	    	}catch(SQLException se) {
	    		return new ResponseEntity(se, responseHeaders, HttpStatus.BAD_REQUEST);
	    	}
	        
	    }
		return new ResponseEntity(patientArray.toString(), responseHeaders, HttpStatus.OK);
	}

@RequestMapping(value = "/searchHospitalAttendance", method = RequestMethod.POST) // <-- setup the endpoint URL at /hello with the HTTP POST method
	public ResponseEntity<String> searchHospitalAttendance(@RequestBody String payload, HttpServletRequest request) {
		
		JSONObject payloadObj = new JSONObject(payload);
		String name = payloadObj.getString("Name"); 

		HttpHeaders responseHeaders = new HttpHeaders(); 
    	responseHeaders.set("Content-Type", "application/json");
		Connection conn = null;
		JSONArray patientArray = new JSONArray();

	    try {
	    	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Phd?useUnicode=true&characterEncoding=UTF-8", "root", "snowleopard");
			String query = "select * from PatientAttending as pa inner join Hospital as h on pa.HID = h.ID inner join Patient as p on pa.PID = p.ID where h.Name LIKE ? order by h.Name";
			PreparedStatement stmt = null;
	        stmt = conn.prepareStatement(query);
	        stmt.setString(1, name+'%');
	        ResultSet rs = stmt.executeQuery();
	        
	        while (rs.next()) {
	            int idNum = rs.getInt("p.ID");
	            int hosID = rs.getInt("h.ID");
	            String fName = rs.getString("p.FName");
	            String lName = rs.getString("p.LName");
	            String hosName = rs.getString("h.Name");
	            String facility = rs.getString("h.FacilityType");

	            JSONObject obj = new JSONObject();

	            obj.put("Name", fName + ' ' + lName);
	            obj.put("Hospital Name", hosName);
	            obj.put("Facility Type", facility);
	            obj.put("Hospital ID", hosID);
	            obj.put("Patient ID", idNum);

	            patientArray.put(obj);
	        
	    } 
	}catch (Exception e ) {
	    	return new ResponseEntity(e, responseHeaders, HttpStatus.BAD_REQUEST);
	    } finally {
	    	try {
	    		if (conn != null) { conn.close(); }
	    	}catch(SQLException se) {
	    		return new ResponseEntity(se, responseHeaders, HttpStatus.BAD_REQUEST);
	    	}
	        
	    }
		return new ResponseEntity(patientArray.toString(), responseHeaders, HttpStatus.OK);
	}


@RequestMapping(value = "/sortHospital", method = RequestMethod.POST) // <-- setup the endpoint URL at /hello with the HTTP POST method
	public ResponseEntity<String> sortHospital(@RequestBody String payload, HttpServletRequest request) {
		
		JSONObject payloadObj = new JSONObject(payload);

		HttpHeaders responseHeaders = new HttpHeaders(); 
    	responseHeaders.set("Content-Type", "application/json");
		Connection conn = null;
		JSONArray patientArray = new JSONArray();

	    try {
	    	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Phd?useUnicode=true&characterEncoding=UTF-8", "root", "snowleopard");
			String query = "SELECT Hospital.name, HID, COUNT(*) as count FROM Hospital, Phd.PatientAttending WHERE Hospital.ID=PatientAttending.HID GROUP BY HID ORDER BY COUNT(*) DESC LIMIT 15";
			PreparedStatement stmt = null;
	        stmt = conn.prepareStatement(query);
	        ResultSet rs = stmt.executeQuery();
	        
	        while (rs.next()) {
	            String hospital = rs.getString("name");
	            String count = rs.getString("count");
	            JSONObject obj = new JSONObject();
	
	            obj.put("Hospital Name", hospital);
	            obj.put("Count", count);

	            patientArray.put(obj);
	        
	    } 
	}catch (Exception e ) {
	    	return new ResponseEntity(e, responseHeaders, HttpStatus.BAD_REQUEST);
	    } finally {
	    	try {
	    		if (conn != null) { conn.close(); }
	    	}catch(SQLException se) {
	    		return new ResponseEntity(se, responseHeaders, HttpStatus.BAD_REQUEST);
	    	}
	        
	    }
		return new ResponseEntity(patientArray.toString(), responseHeaders, HttpStatus.OK);
	}
















@RequestMapping(value = "/locateHospital", method = RequestMethod.POST) // <-- setup the endpoint URL at /hello with the HTTP POST method
	public ResponseEntity<String> locateHospital(@RequestBody String payload, HttpServletRequest request) {
		
		JSONObject payloadObj = new JSONObject(payload);
		String fname = payloadObj.getString("fName"); 
		String lname = payloadObj.getString("lName");

		HttpHeaders responseHeaders = new HttpHeaders(); 
    	responseHeaders.set("Content-Type", "application/json");
		Connection conn = null;
		JSONArray doctorArray = new JSONArray();

	    try {
	    	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Phd?useUnicode=true&characterEncoding=UTF-8", "root", "snowleopard");
			String query = "select doc.ID, doc.FName, doc.LName,  doc.specialty, h.ID, h.Name from DoctorOwns as d inner join Hospital as h on d.HospitalID = h.ID inner join Doctor as doc on d.DoctorID = doc.ID where doc.FName LIKE ? AND doc.LName LIKE ?";
			PreparedStatement stmt = null;
	        stmt = conn.prepareStatement(query);
	        stmt.setString(1, fname+'%');
	        stmt.setString(2, lname+'%');
	        ResultSet rs = stmt.executeQuery();
	        
	        while (rs.next()) {
	            int idNum = rs.getInt("doc.ID");
	            int hosID = rs.getInt("h.ID");
	            String fName = rs.getString("doc.FName");
	            String lName = rs.getString("doc.LName");
	            String specialty = rs.getString("doc.Specialty");
	            String hosName = rs.getString("h.Name");

	            JSONObject obj = new JSONObject();
	            
	            obj.put("Doctor Name", fName + ' ' + lName);
	            obj.put("Hospital Name", hosName);
	            obj.put("Hospital ID", hosID);
	            obj.put("Doctor ID", idNum);
	            obj.put("Doctor Specialty", specialty);


	            doctorArray.put(obj);
	        
	    } 
	}catch (Exception e ) {
	    	return new ResponseEntity(e, responseHeaders, HttpStatus.BAD_REQUEST);
	    } finally {
	    	try {
	    		if (conn != null) { conn.close(); }
	    	}catch(SQLException se) {
	    		return new ResponseEntity(se, responseHeaders, HttpStatus.BAD_REQUEST);
	    	}
	        
	    }
		return new ResponseEntity(doctorArray.toString(), responseHeaders, HttpStatus.OK);
	}

@RequestMapping(value = "/locateHospitalSpecialty", method = RequestMethod.POST) // <-- setup the endpoint URL at /hello with the HTTP POST method
	public ResponseEntity<String> locateHospitalSpecialty(@RequestBody String payload, HttpServletRequest request) {
		
		JSONObject payloadObj = new JSONObject(payload);
		String specialty = payloadObj.getString("specialty");

		HttpHeaders responseHeaders = new HttpHeaders(); 
    	responseHeaders.set("Content-Type", "application/json");
		Connection conn = null;
		JSONArray doctorArray = new JSONArray();

	    try {
	    	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Phd?useUnicode=true&characterEncoding=UTF-8", "root", "snowleopard");
			String query = "select * from DoctorOwns as d inner join Hospital as h on d.HospitalID = h.ID inner join Doctor as doc on d.DoctorID = doc.ID where doc.specialty LIKE ?";
			PreparedStatement stmt = null;
	        stmt = conn.prepareStatement(query);
	        stmt.setString(1, specialty+'%');
	        ResultSet rs = stmt.executeQuery();
	        
	        while (rs.next()) {
	            int idNum = rs.getInt("doc.ID");
	            int hosID = rs.getInt("h.ID");
	            String fName = rs.getString("doc.FName");
	            String lName = rs.getString("doc.LName");
	            specialty = rs.getString("doc.Specialty");
	            String hosName = rs.getString("h.Name");

	            JSONObject obj = new JSONObject();
	            
	            obj.put("Doctor Name", fName + ' ' + lName);
	            obj.put("Hospital Name", hosName);
	            obj.put("Hospital ID", hosID);
	            obj.put("Doctor ID", idNum);
	            obj.put("Doctor Specialty", specialty);


	            doctorArray.put(obj);
	        
	    } 
	}catch (Exception e ) {
	    	return new ResponseEntity(e, responseHeaders, HttpStatus.BAD_REQUEST);
	    } finally {
	    	try {
	    		if (conn != null) { conn.close(); }
	    	}catch(SQLException se) {
	    		return new ResponseEntity(se, responseHeaders, HttpStatus.BAD_REQUEST);
	    	}
	        
	    }
		return new ResponseEntity(doctorArray.toString(), responseHeaders, HttpStatus.OK);
	}


	@RequestMapping(value = "/shift", method = RequestMethod.POST) // <-- setup the endpoint URL at /hello with the HTTP POST method
	public ResponseEntity<String> shift(@RequestBody String payload, HttpServletRequest request) {
		
		JSONObject payloadObj = new JSONObject(payload);
		String shift = payloadObj.getString("shift");

		HttpHeaders responseHeaders = new HttpHeaders(); 
    	responseHeaders.set("Content-Type", "application/json");
		Connection conn = null;
		JSONArray doctorArray = new JSONArray();

	    try {
	    	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Phd?useUnicode=true&characterEncoding=UTF-8", "root", "snowleopard");
			String query = "select * from Doctor as doc, DoctorWorkHours as dwh, WorkHours as wh where dwh.shift LIKE ? and wh.shift = dwh.shift and doc.ID = dwh.DoctorID";
			PreparedStatement stmt = null;
	        stmt = conn.prepareStatement(query);
	        stmt.setString(1, shift);
	        ResultSet rs = stmt.executeQuery();
	        
	        while (rs.next()) {
	            int idNum = rs.getInt("doc.ID");
	            String fName = rs.getString("doc.FName");
	            String lName = rs.getString("doc.LName");
	            String specialty = rs.getString("doc.Specialty");
	            String hours = rs.getString("wh.Hours");
	            String phoneNum = rs.getString("doc.phoneNum");

	            JSONObject obj = new JSONObject();
	            
	            obj.put("Doctor Name", fName + ' ' + lName);
	            obj.put("Doctor ID", idNum);
	            obj.put("Doctor Specialty", specialty);
	            obj.put("Hours", hours);
	            obj.put("Phone Number", phoneNum);


	            doctorArray.put(obj);
	        
	    } 
	}catch (Exception e ) {
	    	return new ResponseEntity(e, responseHeaders, HttpStatus.BAD_REQUEST);
	    } finally {
	    	try {
	    		if (conn != null) { conn.close(); }
	    	}catch(SQLException se) {
	    		return new ResponseEntity(se, responseHeaders, HttpStatus.BAD_REQUEST);
	    	}
	        
	    }
		return new ResponseEntity(doctorArray.toString(), responseHeaders, HttpStatus.OK);
	}
	@RequestMapping(value = "/treated", method = RequestMethod.POST) // <-- setup the endpoint URL at /hello with the HTTP POST method
	public ResponseEntity<String> treated(@RequestBody String payload, HttpServletRequest request) {
		
		JSONObject payloadObj = new JSONObject(payload);
		String doctorID = payloadObj.getString("doctorID");

		HttpHeaders responseHeaders = new HttpHeaders(); 
    	responseHeaders.set("Content-Type", "application/json");
		Connection conn = null;
		JSONArray patientArray = new JSONArray();

	    try {
	    	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Phd?useUnicode=true&characterEncoding=UTF-8", "root", "snowleopard");
			String query = "select d.idPatient, doc.specialty, h.FName, h.LName from DoctorHealed as d inner join Patient as h on d.idPatient = h.ID inner join Doctor as doc on d.idDoctor = doc.ID where d.idDoctor LIKE ?";
			PreparedStatement stmt = null;
	        stmt = conn.prepareStatement(query);
	        stmt.setString(1, doctorID);
	        ResultSet rs = stmt.executeQuery();
	        
	        while (rs.next()) {
	            String fName = rs.getString("FName");
	            String lName = rs.getString("LName");
	            String specialty = rs.getString("specialty");
	            JSONObject obj = new JSONObject();
	
	            obj.put("Patient Name", fName + " " + lName);
	            obj.put("Medicine Area", specialty);

	            patientArray.put(obj);
	        
	    } 
	}catch (Exception e ) {
	    	return new ResponseEntity(e, responseHeaders, HttpStatus.BAD_REQUEST);
	    } finally {
	    	try {
	    		if (conn != null) { conn.close(); }
	    	}catch(SQLException se) {
	    		return new ResponseEntity(se, responseHeaders, HttpStatus.BAD_REQUEST);
	    	}
	        
	    }
		return new ResponseEntity(patientArray.toString(), responseHeaders, HttpStatus.OK);
	}












@RequestMapping(value = "/term", method = RequestMethod.POST) // <-- setup the endpoint URL at /hello with the HTTP POST method
	public ResponseEntity<String> term(@RequestBody String payload, HttpServletRequest request) {
		
		JSONObject payloadObj = new JSONObject(payload);
		String shift = payloadObj.getString("term");

		HttpHeaders responseHeaders = new HttpHeaders(); 
    	responseHeaders.set("Content-Type", "application/json");
		Connection conn = null;
		JSONArray doctorArray = new JSONArray();

	    try {
	    	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Phd?useUnicode=true&characterEncoding=UTF-8", "root", "snowleopard");
			String query = "select term, Name, marketing from Medication, Hospital where term Like ? AND Hospital.ID = Medication.carriedBy";
			PreparedStatement stmt = null;
	        stmt = conn.prepareStatement(query);
	        stmt.setString(1, shift+"%");
	        ResultSet rs = stmt.executeQuery();
	        
	        while (rs.next()) {

	            String term = rs.getString("term");
	            String hospital = rs.getString("Name");
	            String marketing = rs.getString("marketing");
	            JSONObject obj = new JSONObject();
	            
	            obj.put("Term", term);
	            obj.put("Hospital", hospital);
	            obj.put("Marketing", marketing);

	            doctorArray.put(obj);
	        
	    } 
	}catch (Exception e ) {
	    	return new ResponseEntity(e, responseHeaders, HttpStatus.BAD_REQUEST);
	    } finally {
	    	try {
	    		if (conn != null) { conn.close(); }
	    	}catch(SQLException se) {
	    		return new ResponseEntity(se, responseHeaders, HttpStatus.BAD_REQUEST);
	    	}
	        
	    }
		return new ResponseEntity(doctorArray.toString(), responseHeaders, HttpStatus.OK);
	}




@RequestMapping(value = "/marketing", method = RequestMethod.POST) // <-- setup the endpoint URL at /hello with the HTTP POST method
	public ResponseEntity<String> marketing(@RequestBody String payload, HttpServletRequest request) {
		
		JSONObject payloadObj = new JSONObject(payload);
		String shift = payloadObj.getString("marketing");

		HttpHeaders responseHeaders = new HttpHeaders(); 
    	responseHeaders.set("Content-Type", "application/json");
		Connection conn = null;
		JSONArray doctorArray = new JSONArray();

	    try {
	    	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Phd?useUnicode=true&characterEncoding=UTF-8", "root", "snowleopard");
			String query = "select term, Name, marketing from Medication, Hospital where marketing Like ? AND Hospital.ID = Medication.carriedBy";
			PreparedStatement stmt = null;
	        stmt = conn.prepareStatement(query);
	        stmt.setString(1, shift+"%");
	        ResultSet rs = stmt.executeQuery();
	        
	        while (rs.next()) {

	            String term = rs.getString("term");
	            String hospital = rs.getString("Name");
	            String marketing = rs.getString("marketing");
	            JSONObject obj = new JSONObject();
	            
	            obj.put("Term", term);
	            obj.put("Hospital", hospital);
	            obj.put("Marketing", marketing);

	            doctorArray.put(obj);
	        
	    } 
	}catch (Exception e ) {
	    	return new ResponseEntity(e, responseHeaders, HttpStatus.BAD_REQUEST);
	    } finally {
	    	try {
	    		if (conn != null) { conn.close(); }
	    	}catch(SQLException se) {
	    		return new ResponseEntity(se, responseHeaders, HttpStatus.BAD_REQUEST);
	    	}
	        
	    }
		return new ResponseEntity(doctorArray.toString(), responseHeaders, HttpStatus.OK);
	}

}






