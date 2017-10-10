package model.service;

import javafx.collections.ObservableList;
import model.entity.Note;
import model.service.MapperIF.NoteMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import utils.HibernateConfig;

/**
 * Сервис, который каждые [timeCheck] проверяет заметки. Обновляет, если они есть
 *
 *
 */
public class CheckDataTask implements Runnable {
    private final static Logger log = LogManager.getLogger(CheckDataTask.class);

    private NoteMapper noteMapper;
    private int timeCheck = 2000;
    private int numberNotes = 0;
    private ObservableList<Note> items;

    public CheckDataTask(ObservableList<Note> noteObservableList) {
        items = noteObservableList;
        noteMapper = new NoteMapperImpl();
    }

    public void run() {

        while (!Thread.currentThread().isInterrupted()) {
            int currentNumberNotes = getCountNotes();
            if (currentNumberNotes != numberNotes) {
                items.setAll(noteMapper.getNotes());

                numberNotes = currentNumberNotes;
            }

            try {
                Thread.sleep(timeCheck);
            } catch (InterruptedException ex) {
                log.error(ex);
                Thread.currentThread().interrupt();
            }
        }
    }

    private int getCountNotes() {

        Session session = HibernateConfig.getSessionFactory().openSession();
        int amount = ((Long) session.createQuery("select count(*) from Note").uniqueResult()).intValue();
        session.close();
        return amount;
    }

}
