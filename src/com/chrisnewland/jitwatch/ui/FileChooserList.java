package com.chrisnewland.jitwatch.ui;

import java.io.File;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FileChooserList extends VBox
{
    private Stage stage;

    private ListView<Label> fileList;

    public FileChooserList(Stage stage, String title, List<String> items)
    {
        this.stage = stage;

        HBox hbox = new HBox();

        fileList = new ListView<Label>();
       
        for (String item : items)
        {
            fileList.getItems().add(new Label(item));
        }

        Button btnOpenFileDialog = new Button("Add File");
        btnOpenFileDialog.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e)
            {
                chooseFile();
            }
        });
        
        Button btnOpenFolderDialog = new Button("Add Folder");
        btnOpenFolderDialog.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e)
            {
                chooseFolder();
            }
        });

        Button btnRemove = new Button("Remove");
        btnRemove.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e)
            {
                Label selected = fileList.getSelectionModel().getSelectedItem();

                if (selected != null)
                {
                    fileList.getItems().remove(selected);
                }
            }
        });

        VBox vboxButtons = new VBox();
        vboxButtons.setPadding(new Insets(10,10,10,10));
        vboxButtons.setSpacing(10);

        vboxButtons.getChildren().add(btnOpenFileDialog);
        vboxButtons.getChildren().add(btnOpenFolderDialog);
        vboxButtons.getChildren().add(btnRemove);
        
        hbox.getChildren().add(fileList);
        hbox.getChildren().add(vboxButtons);

        fileList.prefWidthProperty().bind(this.widthProperty().multiply(0.75));
        vboxButtons.prefWidthProperty().bind(this.widthProperty().multiply(0.25));
        
        Label titleLabel = new Label(title);

        getChildren().add(titleLabel);
        getChildren().add(hbox);

        setSpacing(10);
    }

    private void chooseFile()
    {
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose File");
        String curDir = System.getProperty("user.dir");
        File dirFile = new File(curDir);
        fc.setInitialDirectory(dirFile);

        File result = fc.showOpenDialog(stage);

        if (result != null)
        {
            fileList.getItems().add(new Label(result.getAbsolutePath()));
        }
    }
    
    private void chooseFolder()
    {
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("Choose File");
        String curDir = System.getProperty("user.dir");
        File dirFile = new File(curDir);
        dc.setInitialDirectory(dirFile);

        File result = dc.showDialog(stage);

        if (result != null)
        {
            fileList.getItems().add(new Label(result.getAbsolutePath()));
        }
    }

    public String getFiles()
    {
        StringBuilder sb = new StringBuilder();

        for (Label label : fileList.getItems())
        {
            sb.append(label.getText()).append("\n");
        }

        return sb.toString();
    }
}
