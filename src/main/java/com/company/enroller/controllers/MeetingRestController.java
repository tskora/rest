package com.company.enroller.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;
import com.company.enroller.persistence.MeetingService;
import com.company.enroller.persistence.ParticipantService;

@RestController
@RequestMapping("/meetings")
public class MeetingRestController {

	@Autowired
	MeetingService meetingService;
	
	@Autowired
	ParticipantService participantService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> getMeetings() {
		Collection<Meeting> meetings = meetingService.getAll();
		return new ResponseEntity<Collection<Meeting>>(meetings, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getMeetingById(@PathVariable("id") long id) {
		Meeting meeting = meetingService.findById(id);
		if (meeting == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<?> registerMeeting(@RequestBody Meeting meeting) {
		if (meetingService.findById(meeting.getId()) != null) {
			return new ResponseEntity(
					"Unable to create. A meeting with id " + meeting.getId() + " already exist.",
					HttpStatus.CONFLICT);
		}
		meetingService.createMeeting(meeting);
		return new ResponseEntity(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteMeetingById(@PathVariable("id") long id) {
		Meeting meeting = meetingService.findById(id);
		if (meeting == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		meetingService.deleteMeeting(meeting);
		return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateMeetingById(@PathVariable("id") long id,
			@RequestBody Meeting meetingUpdate) {
		Meeting oldMeeting = meetingService.findById(id);
		if (oldMeeting == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		meetingUpdate.setId(id);
		meetingService.updateMeeting(meetingUpdate);
		return new ResponseEntity<Meeting>(meetingUpdate, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{meetingId}/participants/{participantId}", method = RequestMethod.PUT)
	public ResponseEntity<?> addParticipantToMeeting(
			@PathVariable("meetingId") long id,
			@PathVariable("participantId") String login){
		
		Participant participant = participantService.findByLogin(login);
		Meeting meeting = meetingService.findById(id);
		if (participant == null || meeting == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		meetingService.addParticipantToMeeting(participant,meeting);
		return new ResponseEntity<Participant>(participant, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{meetingId}/participants", method = RequestMethod.GET)
	public ResponseEntity<?> getParticipantsFromMeeting(@PathVariable("meetingId") long id) {
		Meeting meeting = meetingService.findById(id);
		if (meeting == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		Collection<Participant> participants = meetingService.getMeetingParticipants(id);
		return new ResponseEntity<Collection<Participant>>(participants, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{meetingId}/participants/{participantId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteParticipantFromMeeting(
			@PathVariable("meetingId") long id,
			@PathVariable("participantId") String login){
		
		Participant participant = participantService.findByLogin(login);
		Meeting meeting = meetingService.findById(id);
		if (participant == null || meeting == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		meetingService.deleteParticipantFromMeeting(participant,meeting);
		return new ResponseEntity<Participant>(participant, HttpStatus.OK);
	}

}
