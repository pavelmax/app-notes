package model.service;


import model.entity.Note;
import model.service.MapperIF.NoteMapper;
import org.junit.*;
import utils.HibernateConfig;

import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.*;

public class NoteMapperImplTest {
    private static NoteMapper noteMapper;

    @BeforeClass
    public static void up() {
         noteMapper = new NoteMapperImpl();

         for (int i = 0; i < 4; ++i) {
             Note note = new Note();
             note.setCreateAt(new Date());
             note.setText("Text " + (i + 1));
             noteMapper.add(note);
         }
    }

    @AfterClass
    public static void downClass() {
        HibernateConfig.destroySessionFactory();
    }


    @Test
    public void testAdd() {
        Note note = new Note();
        note.setText("Text..");
        note.setCreateAt(new Date());

        noteMapper.add(note);
    }

    @Test
    public void testGetNotesIsNotEmpty() {
        List<Note> notes = noteMapper.getNotes();
        assertFalse("error", notes.isEmpty());

    }



}