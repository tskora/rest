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
		String hql = "FROM Meeting WHERE id ="+id;
		Query query = connector.getSession().createQuery(hql);
		return (Meeting) query.list().get(0);
	}

	public void createMeeting(Meeting meeting) {
		Session session = connector.getSession();
		session.beginTransaction();
        session.save(meeting);
        session.getTransaction().commit();
	}

}
