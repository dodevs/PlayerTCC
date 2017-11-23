package main2;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ImageViewInTableView extends Application {

    public Parent createContent() {

        /* layout */
        BorderPane layout = new BorderPane();

        /* layout -> center */
        TableView<CustomImage> tableview = new TableView<CustomImage>();

        /* layout -> center -> tableview */

        /* initialize two CustomImage objects and add them to the observable list */
        ObservableList<CustomImage> imgList = FXCollections.observableArrayList();
        CustomImage item_1 = new CustomImage(new ImageView(new Image("C:\\Users\\thiago.felicio\\Desktop\\Login2\\a.png")));
        CustomImage item_2 = new CustomImage(new ImageView(new Image("C:\\Users\\thiago.felicio\\Desktop\\Login2\\Aurora-Boreal3_RG-Local.jpg")));
        imgList.addAll(item_1, item_2);

        /* initialize and specify table column */
        TableColumn<CustomImage, ImageView> firstColumn = new TableColumn<CustomImage, ImageView>("Images");
        firstColumn.setCellValueFactory(new PropertyValueFactory<CustomImage, ImageView>("image"));
        firstColumn.setPrefWidth(60);

        /* add column to the tableview and set its items */
        tableview.getColumns().add(firstColumn);
        tableview.setItems(imgList);

        /* add TableView to the layout */
        layout.setCenter(tableview);
        return layout;
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(createContent()));
        stage.setWidth(200);
        stage.setHeight(200);
        stage.show();
    }

    public static void main(String args[]) {
        launch(args);
    }
}