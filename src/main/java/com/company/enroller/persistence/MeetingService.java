package com.company.enroller.persistence;

import java.util.Collection;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;

@Component("meetingService")
public class MeetingService {

	DatabaseConnector connector;

	public MeetingService() {
		connector = DatabaseConnector.getInstance();
	}

	public Collection<Meeting> getAll() {
		return connector.getSession().createCriteria(Meeting.class).list();
	}

	public Meeting findById(long id) {
		return (Meeting) connector.getSession().get(Meeting.class, id);
	}

	public void createMeeting(Meeting meeting) {
		Transaction transaction = connector.getSession().beginTransaction();
		connector.getSession().save(meeting);
		transaction.commit();
	}
	
	public void deleteMeeting(Meeting meeting) {
		Transaction transaction = connector.getSession().beginTransaction();
		connector.getSession().delete(meeting);
		transaction.commit();
	}
	
	public void updateMeeting(Meeting meetingUpdate) {	
        Transaction transaction = connector.getSession().beginTransaction();
		connector.getSession().merge(meetingUpdate);
		transaction.commit();
	}

	public void addParticipantToMeeting(Participant participant, Meeting meeting) {
		Transaction transaction = connector.getSession().beginTransaction();
		meeting.addParticipant(participant);
		connector.getSession().update(meeting);
		transaction.commit();
	}

	public Collection<Participant> getMeetingParticipants(long id) {
		return findById(id).getParticipants();
	}

	public void deleteParticipantFromMeeting(Participant participant, Meeting meeting) {
		Transaction transaction = connector.getSession().beginTransaction();
		meeting.removeParticipant(participant);
		connector.getSession().update(meeting);
		transaction.commit();
	}

}
