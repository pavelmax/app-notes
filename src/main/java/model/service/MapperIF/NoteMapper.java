package model.service.MapperIF;

import model.entity.Note;

import java.util.List;

public interface NoteMapper {

    void add(Note note);
    List<Note> getNotes();

}
