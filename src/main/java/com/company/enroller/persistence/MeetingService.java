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
		Session session = connector.getSession();
		session.beginTransaction();
        session.save(meeting);
        session.getTransaction().commit();
	}
	
	public void deleteMeeting(Meeting meeting) {
		Session session = connector.getSession();
		session.beginTransaction();
        session.delete(meeting);
        session.getTransaction().commit();
	}
	
	public void updateMeeting(Meeting oldMeeting, Meeting meetingUpdate) {	
		Session session = connector.getSession();
		session.beginTransaction();
        session.delete(oldMeeting);
        session.save(meetingUpdate);
        session.getTransaction().commit();
	}

}
