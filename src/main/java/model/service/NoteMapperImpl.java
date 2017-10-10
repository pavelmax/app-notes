package model.service;

import model.entity.Note;
import model.service.MapperIF.NoteMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import utils.HibernateConfig;

import java.util.List;

public class NoteMapperImpl implements NoteMapper {
    private final static Logger log = LogManager.getLogger(NoteMapperImpl.class);

    /**
     * Добавить заметку
     * @param note
     */
    public void add(Note note) {
        Session session = null;

        try {
            session = HibernateConfig.getSessionFactory().openSession();
            session.beginTransaction();

            session.save(note);
            session.getTransaction().commit();

            log.info(String.format("note: %s added", note.toString()));
        } catch (HibernateException ex) {
            log.error(ex);
            session.getTransaction().rollback();

        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

    }

    /**
     * Получить все заметки
     * @return
     */
    public List<Note> getNotes() {
        Session session = HibernateConfig.getSessionFactory().openSession();
        List<Note> notes = (List<Note>) session.createQuery("from Note").list();
        session.close();
        return notes;
    }


}
