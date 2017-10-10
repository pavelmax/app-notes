package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import model.entity.Note;
import model.service.CheckDataTask;
import utils.Loader;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class MainNotesController extends Window {

    @FXML
    private TableColumn columnDate;
    @FXML
    private TableColumn columnNote;
    @FXML
    private TableView tableView;

    private ObservableList<Note> items;


    public MainNotesController() {
        items = FXCollections.observableArrayList();
        CheckDataTask backgroundCheckDataTask = new CheckDataTask(items);
        Thread thread = new Thread(backgroundCheckDataTask);
        thread.setDaemon(true);
        thread.start();
    }
    
    public void initialize() {
        tableView.setItems(items);
        columnNote.setCellValueFactory(new PropertyValueFactory<Note, String>("text"));
        columnDate.setCellValueFactory(new PropertyValueFactory<Note, Date>("createAt"));
        columnDate.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                return new TableCell<Note, Date>() {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("d.M.y");
                    @Override
                    protected void updateItem(Date item, boolean empty) {
                        super.updateItem(item, empty);

                        if (!empty) {
                            setFont(Font.font(null, FontWeight.BOLD, 15));
                            setText(dateFormat.format(item));
                        }
                    }
                };
            }
        });
    }


    public void editingNoteAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = Loader.loader("/view/EditingNotesView.fxml");
        Parent root = (Parent) loader.load();
        EditingNotesController controller = (EditingNotesController) loader.getController();
        controller.setItems(items);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(  ((Node) actionEvent.getSource() ).getScene().getWindow() );
        stage.setScene(new Scene(root));
        stage.show();
    }
}
