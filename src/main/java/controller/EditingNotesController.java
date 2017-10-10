package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import jfxtras.scene.control.LocalDateTimeTextField;
import model.entity.Note;
import model.service.NoteMapperImpl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.function.UnaryOperator;

public class EditingNotesController {
    @FXML
    private TextArea textArea;
    @FXML
    private DatePicker datePicker;

    private ObservableList<Note> items;
    private Alert alert;

    @FXML
    public void initialize() {
        alert = new Alert(Alert.AlertType.WARNING);
        datePicker.setValue(LocalDate.now());
        textArea.setTextFormatter(new TextFormatter<Object>(new UnaryOperator<TextFormatter.Change>() {
            @Override
            public TextFormatter.Change apply(TextFormatter.Change change) {
                return (change.getControlNewText().length() > 100) ? null : change;
            }
        }));
    }

    public void setItems(ObservableList<Note> items) {
        this.items = items;
    }

    public void addNoteAction(ActionEvent actionEvent) {

        if (textArea.getLength() > 100 || datePicker.getValue() == null) {
            alert.setHeaderText("Некорректные данные");
            alert.setContentText("Длина заметки <= 100. Дата должна быть указана");
            alert.show();
            return;
        }

        final Note note = new Note();
        note.setText(textArea.getText());
        Date date = Date.from(datePicker.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        note.setCreateAt(date);

        new Thread(new Runnable() {
            @Override
            public void run() {
                NoteMapperImpl noteMapper = new NoteMapperImpl();
                noteMapper.add(note);
                items.add(note);
            }
        }).start();
    }
}
