package com.company.enroller.persistence;

import java.util.Collection;

import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.company.enroller.model.Participant;

@Component("participantService")
public class ParticipantService {

	DatabaseConnector connector;

	public ParticipantService() {
		connector = DatabaseConnector.getInstance();
	}

	public Collection<Participant> getAll() {
		return connector.getSession().createCriteria(Participant.class).list();
	}

	public Participant findByLogin(String login) {
		return (Participant) connector.getSession().get(Participant.class, login);
	}

	public void createParticipant(Participant participant) {
		Session session = connector.getSession();
		session.beginTransaction();
        session.save(participant);
        session.getTransaction().commit();
	}
	
	public void deleteParticipant(Participant participant) {
		Session session = connector.getSession();
		session.beginTransaction();
        session.delete(participant);
        session.getTransaction().commit();
	}

	public void updateParticipant(Participant oldParticipant, Participant participantUpdate) {	
		Session session = connector.getSession();
		session.beginTransaction();
        session.delete(oldParticipant);
        session.save(participantUpdate);
        session.getTransaction().commit();
	}

}
