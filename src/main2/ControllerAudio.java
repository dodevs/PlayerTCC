package main2;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;
import com.google.common.io.Files;
import com.jfoenix.controls.*;
import com.jfoenix.transitions.JFXFillTransition;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import RestAPI.retrofitGson.App;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;

import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.MapChangeListener.Change;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import java.util.*;
import javafx.util.Duration;
import java.io.*;
import java.nio.*;
import java.nio.file.Paths;

public class ControllerAudio {
	/*****************************************
	 * * JAVAFX DECLARATIONS * *
	 ******************************************/
	@FXML
	private Button menu;
	@FXML
	public AnchorPane pane, navList;
	@FXML
	public Label mus, playTime, rock, MusicLast, CantorLast;
	@FXML
	private JFXSlider slider;
	@FXML
	private MediaView media;
	@FXML
	public ImageView image, AlbumLast;
	@FXML
	public TextField txtS, link;
	@FXML
	public StackPane stack;
	@FXML
	private Button play2, aleatorio, last2, Youtube_Button;
	@FXML
	public Pane pane1, pan2, pane3, pane5, pane6, PaneLast, paneLabel;
	@FXML
	private GridPane Grid;
	@FXML
	WebView webview;
	@FXML
	Button lupa, coe, x;
	@FXML
	Circle cir2;
	@FXML
	private TextField way;
	@FXML
	ComboBox isvideo, format;
	int index = 0;

	public static List<String> cl = new ArrayList<String>();
	public static String al;
	public static List<String> ml = new ArrayList<String>();

	/*****************************************
	 * * JFHOENIX DECLARATIONS * *
	 ******************************************/
	@FXML
	private JFXHamburger menu2;
	@FXML
	private JFXButton play, stop, converter, selecionar;
	@FXML
	private JFXTextField SearchY, login;
	@FXML
	private JFXPasswordField senha;
	@FXML
	private JFXListView<Label> list;
	@FXML
	private JFXSpinner spinner;
	@FXML
	private JFXProgressBar progress;
	@FXML
	TableView table;
	HamburgerSlideCloseTransition burgerTask;
	/*****************************************
	 * * DECLARATIONS GLOBAIS * *
	 ******************************************/
	Duration duration;
	String path2;
	AudioClip clip;
	Media mediaa;
	MediaPlayer mediaPlayer;
	TranslateTransition closeNav;
	int contador = 0;
	boolean tocando = true;
	int IndexTable = 0;

	boolean t = true;

	ContextMenu cm = new ContextMenu();
	List<File> arquivos = new ArrayList<File>();
	List<File> arquivos2 = new ArrayList<File>();
	List<File> arquivosI = new ArrayList<File>();
	List<Label> labels = new ArrayList<Label>();
	List<ImageView> IMAGENS = new ArrayList<ImageView>();
	List<Image> imagem = new ArrayList<Image>();
	List<Label> listlbl = new ArrayList<Label>();
	List<Button> buttons = new ArrayList<Button>();

	static List<String> Title = new ArrayList<String>();
	static List<String> Url = new ArrayList<String>();
	static List<String> Id = new ArrayList<String>();

	/*****************************************
	 * * Methods FXML * *
	 ******************************************/
	public static String album;
	public Thread last;

	@FXML
	void salvarem(ActionEvent event) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Select Some Directories");
		directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		String dir = directoryChooser.showDialog(null).toString();
		way.setText(dir);

	}

	void setInvisible() {
		burgerTask.setRate(burgerTask.getRate() * -1);
		burgerTask.play();

		pane1.setVisible(false);
		pan2.setVisible(false);
		pane3.setVisible(false);
		pane6.setVisible(false);
	}

	@FXML
	void down(ActionEvent event) {
		setInvisible();
		pane6.setVisible(true);
		volte();

	}

	Thread thread = null;

	@FXML
	void selecionar(ActionEvent event) throws UnsupportedEncodingException {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Video Files", "*.mp4", "*.webm"),
				new ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"), new ExtensionFilter("All Files", "*.*"));
		File selectedFile = fileChooser.showOpenDialog(null);
		if (selectedFile != null) {
			String path = selectedFile.getPath();
			thread = new Thread(new Convert(path));
			thread.start();

		}
		Thread t2 = new Thread() {
			@Override
			public void run() {
				while (thread.isAlive()) {
					try {
						thread.join();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				Platform.runLater(() -> {

					JFXSnackbar snack = new JFXSnackbar(pane);
					snack.show("Acabou", 3000);

					snack.getStyleClass().add("jfx-snackbar");
					JFXFillTransition transition = new JFXFillTransition();
					transition.setDuration(Duration.millis(5000));
					transition.setRegion(pane);
					transition.setFromValue(Color.RED);
					transition.setToValue(Color.TRANSPARENT);
					transition.play();

				});
			}
		};
		t2.start();

	}

	@FXML
	void converter(ActionEvent event) {
		String Path_Script = null;
		try {

			Path_Script = System.getenv("ProgramFiles(x86)");
			Path_Script = Path_Script + "\\Player Diti\\teste.py";

			/*
			 * String t = System.getenv("ProgramFiles(x86)"); t =
			 * t+"\\Player Diti\\path.txt";
			 * 
			 * FileReader arq = new FileReader(t); BufferedReader lerArq = new
			 * BufferedReader(arq);
			 * 
			 * String linha = lerArq.readLine(); System.out.println(linha);
			 */
			if (Path_Script != null) {
				Thread t3 = new Thread(new Download(Path_Script, link.getText(), way.getText(),
						isvideo.getSelectionModel().getSelectedIndex(), format.getSelectionModel().getSelectedIndex()));
				t3.start();
			} else {
				/*
				 * FileChooser fileChooser = new FileChooser();
				 * fileChooser.setTitle("Open Resource File");
				 * fileChooser.getExtensionFilters().addAll( new
				 * ExtensionFilter("Python Files", "*.py")); File selectedFile =
				 * fileChooser.showOpenDialog(null); if (selectedFile != null) {
				 * Path_Script = selectedFile.getPath();
				 * 
				 * String t = System.getenv("ProgramFiles(x86)"); t =
				 * t+"\\Player Diti\\path.txt"; FileWriter out = new
				 * FileWriter(t); out.write(Path_Script); out.close();
				 */

			}

		} catch (Exception e) {
		}

	}

	String il = "";

	@FXML
	void last(ActionEvent event) throws InterruptedException {

		il = arquivos.get(table.getSelectionModel().getSelectedIndex()).getPath();
		il = Files.getNameWithoutExtension(il);
		last = new Thread(new App(il));
		last.start();

		pane1.setVisible(false);
		PaneLast.setVisible(true);

		synchronized (last) {
			try {
				System.out.println("Aguardando o b completar...");
				last.wait();

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Thread t2 = new Thread() {
				@Override
				public void run() {
					while (last.isAlive()) {
						try {
							last.join();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					Platform.runLater(() -> {
						MusicLast.setText(il);
						CantorLast.setText(cl.get(0));

						Image imagem = new Image(al);
						AlbumLast = new ImageView(imagem);
						AlbumLast.setX(200);
						AlbumLast.setY(200);
						PaneLast.getChildren().add(AlbumLast);

					});
				}
			};
			t2.start();
			t2.sleep(1000);

		}

	}

	void initTxt() {
		double sceneWidth = pane.getWidth();
		double msgWidth = mus.getLayoutBounds().getWidth();
		KeyValue initKeyValue = new KeyValue(mus.translateXProperty(), 1500);
		KeyFrame initFrame = new KeyFrame(Duration.ZERO, initKeyValue);

		KeyValue endKeyValue = new KeyValue(mus.translateXProperty(), 0.0 * msgWidth);
		KeyFrame endFrame = new KeyFrame(Duration.seconds(6), endKeyValue);

		Timeline timeline = new Timeline(initFrame, endFrame);

		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();

	}

	@FXML
	void music(ActionEvent event) {
		setInvisible();
		pane1.setVisible(true);
		volte();

	}

	/** Method to **/
	@FXML
	void clickV(ActionEvent event) {
		setInvisible();
		pan2.setVisible(true);
		volte();

	}

	/** Methods for remove WebView when it is Visible **/
	@FXML
	void x(ActionEvent event) {
		pane.getChildren().remove(webview);
		Youtube_Button.setVisible(false);

	}

	/** Method to login **/
	@FXML
	void logar(ActionEvent event) {
		String login2 = login.getText();
		String senha2 = senha.getText();

		if (login2.equals("admin")) {
			if (senha2.equals("123")) {
				pane3.setVisible(false);
			}
		}
	}

	/** Methods to call Youtube Search **/
	Thread t1;

	@FXML
	void search(ActionEvent event) throws InterruptedException {
		Grid.getChildren().clear();
		imagem.clear();
		Id.clear();
		Title.clear();
		Url.clear();
		labels.clear();
		IMAGENS.clear();

		t1 = new Thread(new Search(SearchY.getText()));
		t1.start();

		spinner.setVisible(true);
		Thread t2 = new Thread() {
			@Override
			public void run() {
				while (t1.isAlive()) {
					try {
						t1.join();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				Platform.runLater(() -> {
					for (int i = 0; i < 3; i++) {

						labels.add(new Label(Title.get(i)));
						imagem.add(new Image(Url.get(i)));
						IMAGENS.add(new ImageView(imagem.get(i)));
						buttons.add(new Button("Compartilhar"));
						buttons.get(i).setMinWidth(100);
						Grid.add(buttons.get(i), 2, i);
						Grid.add(IMAGENS.get(i), 0, i);
						Grid.add(labels.get(i), 1, i);
						buttons.get(i).setOnAction(action2(i));
						IMAGENS.get(i).setOnMouseClicked(action(i));

					}
				});
				spinner.setVisible(false);
			}

		};
		t2.start();

	}

	// Set action on ImageView after search Video of Youtube
	private EventHandler<? super MouseEvent> action(int i) {

		EventHandler<MouseEvent> action = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				webview = new WebView();
				WebEngine webEngine = webview.getEngine();
				//webview.getEngine().load("https://www.youtube.com/embed/" + Id.get(i) + "?autoplay=1"
				webEngine.setUserAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");
				webEngine.load("https://m.youtube.com/watch?v=" + Id.get(i)
				//webEngine.load("https://www.youtube.com/embed/" + Id.get(i) + "?autoplay=1"
				);
				Youtube_Button.setVisible(true);
				pane.getChildren().add(webview);

			}
		};

		return action;
	}

	// Set action on ImageView after search Video of Youtube
	private EventHandler<ActionEvent> action2(int i) {

		EventHandler<ActionEvent> action2 = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				java.awt.datatransfer.Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				String text = "https://www.youtube.com/watch?v=" + Id.get(i);
				StringSelection selection = new StringSelection(text);
				clipboard.setContents(selection, null);
			}
		};

		return action2;
	}

	@FXML
	void menu2(MouseEvent event) {
		TranslateTransition openNav = new TranslateTransition(new Duration(350), navList);
		openNav.setToX(0);
		closeNav = new TranslateTransition(new Duration(350), navList);
		if (navList.getTranslateX() != 0) {
			openNav.play();
		} else {
			closeNav.setToX(-(navList.getWidth()));
			closeNav.play();
		}
	}

	@FXML
	void open(ActionEvent event) throws UnsupportedTagException, InvalidDataException, IOException {
		initPlay();
		initSlider();

	}

	void initSlider() {

		mediaPlayer.setOnReady(() -> {
			duration = mediaPlayer.getMedia().getDuration();
			updateValues();
		});

		slider.valueProperty().addListener((Observable ov) -> {
			if (slider.isValueChanging()) {
				// multiply duration by percentage calculated by slider position
				if (duration != null) {
					mediaPlayer.seek(duration.multiply(slider.getValue() / 100.0));
				}
				updateValues();

			}
		});

		mediaPlayer.currentTimeProperty()
				.addListener((ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) -> {
					updateValues2();
				});

		// Time slider
		slider.setMinWidth(50);
		slider.setMaxWidth(Double.MAX_VALUE);
		slider.valueProperty().addListener((Observable ov) -> {
			if (slider.isValueChanging()) {
				// multiply duration by percentage calculated by slider position
				if (duration != null) {
					mediaPlayer.seek(duration.multiply(slider.getValue() / 100.0));
				}
				updateValues2();
			}
		});

	}

	@FXML
	void click(ActionEvent event) {
		if (t == true) {
			SepiaTone sepiaTone = new SepiaTone();
			sepiaTone.setLevel(0.7);
			aleatorio.setEffect(sepiaTone);
			t = false;
		} else {
			aleatorio.setEffect(null);
			t = true;
		}

	}

	@FXML
	void stop(ActionEvent event) {
		mediaPlayer.stop();
		mus.setText("");
		pane.getChildren().remove(media);

	}

	@FXML
	void ante(ActionEvent event) throws UnsupportedTagException, InvalidDataException, IOException {
		mediaPlayer.stop();
		table.getSelectionModel().select(IndexTable-1);
		table.scrollTo(IndexTable-1);

		String name = arquivos.get(IndexTable - 1).getName();
		String path = arquivos.get(IndexTable - 1).getAbsolutePath();
		System.out.println(name);
		System.out.println(path);
		IndexTable -= 1;
		path = path.replace("\\", "/");
		File file = new File(path);

		String AUDIO_URL = file.toURI().toString();

		String texto = AUDIO_URL;
		Mp3File song = new Mp3File(path);
		if (song.hasId3v2Tag()) {
			ID3v2 id3v2tag = song.getId3v2Tag();
			Image a = new Image(new ByteArrayInputStream(id3v2tag.getAlbumImage()));
			image.setImage(a);
		}

		mus.setText(name);

		String procurarPor = "mp4";
		if (texto.toLowerCase().contains(procurarPor.toLowerCase()) == false) {

			initTxt();
			mediaa = new Media(AUDIO_URL);
			mediaPlayer = new MediaPlayer(mediaa);
			media = new MediaView(mediaPlayer);
			mediaPlayer.play();

			initSlider();

		}
	}

	@FXML
	void prox(ActionEvent event) throws UnsupportedTagException, InvalidDataException, IOException {
		mediaPlayer.stop();
		
		table.getSelectionModel().select(IndexTable+1);
		table.scrollTo(IndexTable+1);

		String name = arquivos.get(IndexTable + 1).getName();
		String path = arquivos.get(IndexTable + 1).getAbsolutePath();
		System.out.println(name);
		System.out.println(path);

		path = path.replace("\\", "/");
		File file = new File(path);

		String AUDIO_URL = file.toURI().toString();
		IndexTable += 1;
		
		String texto = AUDIO_URL;
		Mp3File song = new Mp3File(path);
		if (song.hasId3v2Tag()) {
			ID3v2 id3v2tag = song.getId3v2Tag();
			Image a = new Image(new ByteArrayInputStream(id3v2tag.getAlbumImage()));
			image.setImage(a);
		}

		mus.setText(name);

		String procurarPor = "mp4";
		if (texto.toLowerCase().contains(procurarPor.toLowerCase()) == false) {

			initTxt();
			mediaa = new Media(AUDIO_URL);
			mediaPlayer = new MediaPlayer(mediaa);
			media = new MediaView(mediaPlayer);
			mediaPlayer.play();

			initSlider();

		}

	}

	void music() {

		Status status = mediaPlayer.getStatus();
		if (status.PLAYING != null) {
			System.out.println(mediaPlayer.getStatus());

		} else {

			mediaPlayer.stop();
			String name = arquivos.get(table.getSelectionModel().getSelectedIndex()).getName();
			String path = arquivos.get(table.getSelectionModel().getSelectedIndex() + 1).getAbsolutePath();
			path = path.replace("\\", "/");
			File file = new File(path);

			String AUDIO_URL = file.toURI().toString();

			String texto = AUDIO_URL;

			String procurarPor = "mp4";
			if (texto.toLowerCase().contains(procurarPor.toLowerCase()) == false) {

				mus.setText(name);
				initTxt();
				mediaa = new Media(AUDIO_URL);
				mediaPlayer = new MediaPlayer(mediaa);
				media = new MediaView(mediaPlayer);
				mediaPlayer.play();
				table.getSelectionModel().select(table.getSelectionModel().getSelectedIndex() + 1);
				table.scrollTo(table.getSelectionModel().getSelectedIndex() + 0.5);
				initSlider();

			}

		}
	}

	String Path_Dir;

	@FXML
	void im(ActionEvent event) throws UnsupportedTagException, InvalidDataException, IOException {

		// list.getItems().clear(); listlbl.clear(); arquivos.clear();

		Path_Dir = System.getProperty("user.home");
		Path_Dir = Path_Dir + "\\documents\\dir.txt";

		PrintWriter writer = new PrintWriter(Path_Dir);

		// File f = new File(Path_Dir);
		// BufferedWriter writer = new BufferedWriter(new FileWriter(f));
		String path = "";
		for (int b = 0; b != arquivos.size(); b++) {
			path = path + ";" + arquivos.get(b).getAbsolutePath();
		}

		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Audio e/ou Video Files", "*.mp3", "*.mp4"));
		List<File> list2 = fileChooser.showOpenMultipleDialog(null);
		if (list2 != null) {
			int i = arquivos.size();
			String title = "";
			String album = "";
			String artist = "";
			String year = "";
			for (File file : list2) {
				arquivos.add(file);
				path = path + ";" + file.getAbsolutePath();

				listlbl.add(new Label(file.getName()));
				list.getItems().add(listlbl.get(i));
				i++;

				ID3v1 id3v1Tag = null;
				try {
					Mp3File mp3file = new Mp3File(file.getAbsolutePath());

					if (mp3file.hasId3v1Tag()) {
						id3v1Tag = mp3file.getId3v1Tag();
						if (id3v1Tag.getArtist().equals("")) {
							artist = "";
						} else {
							System.out.println("Artist: " + id3v1Tag.getArtist());
							artist = id3v1Tag.getArtist();
						}
						if (id3v1Tag.getAlbum().equals("")) {
							album = "";
						} else {
							System.out.println("Album: " + id3v1Tag.getAlbum());
							album = id3v1Tag.getAlbum();
						}
						if (id3v1Tag.getTitle().equals("")) {
							title = "";
						} else {
							System.out.println("Title: " + id3v1Tag.getTitle());
							title = id3v1Tag.getTitle();
						}

					}

					data = FXCollections.observableArrayList(new Person(file.getName(), album, artist, title));
					album = "";
					artist = "";
					title = "";
					year = "";
					table.getItems().addAll(data);

				} catch (Exception e) {
					System.out.println(e);
				}

			}

			writer.println(path);
			writer.close();

		}
	}

	String path;

	/*****************************************
	 * * INICIAR FXML * *
	 ******************************************/
	String key;
	Object value;
	int z = 0;

	String album2 = null, artist = null, title = null, d;
	Circle cir3;
	@FXML
	void initialize() throws IOException {
		
		
		cir3 = new Circle(150, 150, 150);
		cir3.setLayoutX(35);
		cir3.setLayoutY(59);
		Image profile2 = new Image("http://i.imgur.com/zIMDPTS.jpg");
		cir3.setFill(new ImagePattern(profile2));
		pane1.getChildren().add(cir3);

		TableColumn evento = new TableColumn("Nome");

		evento.setMinWidth(100);
		evento.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));

		TableColumn lastNameCol = new TableColumn("Album");
		lastNameCol.setMinWidth(100);
		lastNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));

		TableColumn emailCol = new TableColumn("Ano");
		emailCol.setMinWidth(100);
		emailCol.setCellValueFactory(new PropertyValueFactory<Person, String>("email"));

		TableColumn hora = new TableColumn("Artista");
		hora.setMinWidth(100);
		hora.setCellValueFactory(new PropertyValueFactory<Person, String>("hora"));

		table.getColumns().addAll(evento, lastNameCol, hora, emailCol);

		isvideo.getItems().add("Somente Video");
		isvideo.getItems().add("Somente Audio");
		isvideo.getItems().add("Audio e Video");

		format.getItems().add("mp4");
		format.getItems().add("mp3");
		format.getItems().add("webm");

		cir2 = new Circle(50, 50, 50);
		Image profile = new Image("http://i.imgur.com/zIMDPTS.jpg");
		cir2.setFill(new ImagePattern(profile));
		navList.getChildren().add(cir2);

		pane3.setVisible(false);
		initTran();

		table.getStyleClass().add("mylistview");
		burgerTask = new HamburgerSlideCloseTransition(menu2);
		burgerTask.setRate(-1);
		menu2.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
			burgerTask.setRate(burgerTask.getRate() * -1);
			burgerTask.play();
		});
		menu2.getStyleClass().add("jfx-hamburger-icon");
		String title = "";
		String album = "";
		String artist = "";
		String year = "";
		try {
			// Path_Dir = System.getenv("ProgramFiles(x86)");
			// Path_Dir = Path_Dir + "\\Player Diti\\Dir.txt";

			Path_Dir = System.getProperty("user.home");
			Path_Dir = Path_Dir + "\\documents\\dir.txt";

			File f = new File(Path_Dir);
			BufferedReader br = new BufferedReader(new FileReader(f));
			String frutas[] = br.readLine().split(";");
			for (int i = 1; i != frutas.length; i++) {
				File file = new File(frutas[i]);
				arquivos.add(file);
				System.out.println(frutas[i]);

				listlbl.add(new Label(file.getName()));
				list.getItems().add(listlbl.get(i - 1));

				ID3v1 id3v1Tag = null;
				try {
					Mp3File mp3file = new Mp3File(file.getAbsolutePath());

					if (mp3file.hasId3v1Tag()) {
						id3v1Tag = mp3file.getId3v1Tag();
						if (id3v1Tag.getArtist().equals("")) {
							artist = "";
						} else {
							System.out.println("Artist: " + id3v1Tag.getArtist());
							artist = id3v1Tag.getArtist();
						}
						if (id3v1Tag.getAlbum().equals("")) {
							album = "";
						} else {
							System.out.println("Album: " + id3v1Tag.getAlbum());
							album = id3v1Tag.getAlbum();
						}
						if (id3v1Tag.getTitle().equals("")) {
							title = "";
						} else {
							System.out.println("Title: " + id3v1Tag.getTitle());
							title = id3v1Tag.getTitle();
						}

					}
				} catch (UnsupportedTagException | InvalidDataException | IOException e) {
					System.out.println("Não tenho");

				}

				data = FXCollections.observableArrayList(new Person(file.getName(), album, artist, title));
				album = "";
				artist = "";
				title = "";
				year = "";
				table.getItems().addAll(data);

			}

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	ObservableList<Person> data = null;

	/*****************************************
	 * * Methods * *
	 ******************************************/

	void initList() {

		table.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (e.getButton() == MouseButton.SECONDARY) {
					cm.show(table, e.getScreenX(), e.getScreenY());
				} else {
					System.out.println("No right click");
				}
			}
		});

		cm.addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.SECONDARY) {
					System.out.println("consuming right release button in cm filter");
					event.consume();
				}
			}
		});

		cm.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String menu = ((MenuItem) event.getTarget()).getText();

				if (menu == "Renomear") {
					// LAYOUT DO DIALOGO

					JFXDialog dialog = new JFXDialog();
					JFXDialogLayout content = new JFXDialogLayout();
					content.setHeading(new javafx.scene.text.Text("Renomear"));

					TextField renomear = new TextField();
					content.setBody(renomear);

					JFXButton okei = new JFXButton("Done");
					okei.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							String a = renomear.getText();
							System.out.println(a);
							
							
							try {
								Mp3File song = new Mp3File(arquivos.get(IndexTable).getAbsolutePath());
								System.out.println(arquivos.get(IndexTable).getAbsolutePath());
								ID3v1 id3v1Tag = song.getId3v1Tag();
								id3v1Tag.setComment(a);
								
								
							} catch (UnsupportedTagException | InvalidDataException | IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
							
							dialog.close();
						}
					});
					content.setActions(okei);

					//
					stack.setVisible(true);
					dialog.setContent(content);
					dialog.show(stack);

					

				}

			}
		});

		MenuItem menuItem1 = new MenuItem("Renomear");
		MenuItem menuItem2 = new MenuItem("Remover");
		MenuItem menuItem3 = new MenuItem("Adicionar Playlist");

		cm.getItems().addAll(menuItem1, menuItem2, menuItem3);

	}

	void initTran() {
		JFXSnackbar snack = new JFXSnackbar(pane);
		snack.show("Hello", 3000);

		snack.getStyleClass().add("jfx-snackbar");
		JFXFillTransition transition = new JFXFillTransition();
		transition.setDuration(Duration.millis(5000));
		transition.setRegion(pane);
		transition.setFromValue(Color.RED);
		transition.setToValue(Color.TRANSPARENT);
		transition.play();
		initList();
	}

	@SuppressWarnings("deprecation")
	void updateValues() {
		Platform.runLater(() -> {
			Duration currentTime = mediaPlayer.getCurrentTime();
			playTime.setText(formatTime(currentTime, duration));
			slider.setDisable(duration.isUnknown());
			if (!slider.isDisabled() && duration.greaterThan(Duration.ZERO) && !slider.isValueChanging()) {
				slider.setValue(currentTime.divide(duration).toMillis() * 100.0);
			}
		});
	}

	private static String formatTime(Duration elapsed, Duration duration) {
		int intElapsed = (int) Math.floor(elapsed.toSeconds());
		int elapsedHours = intElapsed / (60 * 60);
		if (elapsedHours > 0) {
			intElapsed -= elapsedHours * 60 * 60;
		}
		int elapsedMinutes = intElapsed / 60;
		int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 - elapsedMinutes * 60;

		if (duration.greaterThan(Duration.ZERO)) {
			int intDuration = (int) Math.floor(duration.toSeconds());
			int durationHours = intDuration / (60 * 60);
			if (durationHours > 0) {
				intDuration -= durationHours * 60 * 60;
			}
			int durationMinutes = intDuration / 60;
			int durationSeconds = intDuration - durationHours * 60 * 60 - durationMinutes * 60;
			if (durationHours > 0) {
				return String.format("%d:%02d:%02d/%d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds,
						durationHours, durationMinutes, durationSeconds);
			} else {
				return String.format("%02d:%02d/%02d:%02d", elapsedMinutes, elapsedSeconds, durationMinutes,
						durationSeconds);
			}
		} else {
			if (elapsedHours > 0) {
				return String.format("%d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds);
			} else {
				return String.format("%02d:%02d", elapsedMinutes, elapsedSeconds);
			}
		}
	}

	private void volte() {
		closeNav.setToX(-(navList.getWidth()));
		closeNav.play();
	}

	@SuppressWarnings("deprecation")
	protected void updateValues2() {
		Platform.runLater(() -> {
			Duration currentTime = mediaPlayer.getCurrentTime();
			playTime.setText(formatTime(currentTime, duration));
			slider.setDisable(duration.isUnknown());
			if (!slider.isDisabled() && duration.greaterThan(Duration.ZERO) && !slider.isValueChanging()) {
				slider.setValue(currentTime.divide(duration).toMillis() * 100.0);
			}
		});
	}

	void initPlay() {
		Status status = null;
		
		RotateTransition rt = new RotateTransition(Duration.millis(10000), cir3);
		rt.setByAngle(360);
		rt.setCycleCount(Animation.INDEFINITE);
		rt.setInterpolator(Interpolator.LINEAR);
		rt.play();
		
		try {
			status = mediaPlayer.getStatus();
		} catch (Exception e) {

		}
		if (status == status.PLAYING) {
			mediaPlayer.pause();
		} else if (status == status.PAUSED) {
			mediaPlayer.seek(duration.multiply(slider.getValue() / 100.0));
			mediaPlayer.play();
		} else if (status == status.STOPPED || status == null) {

			try {
				mediaPlayer.seek(mediaPlayer.getStartTime());

			} catch (Exception e) {
				System.out.println("ue kk");
			}
			IndexTable = table.getSelectionModel().getSelectedIndex();
			String name = arquivos.get(IndexTable).getName();
			String path = arquivos.get(IndexTable).getAbsolutePath();
			path = path.replace("\\", "/");
			File file = new File(path);

			String AUDIO_URL = file.toURI().toString();

			String texto = AUDIO_URL;
			try {
				Mp3File song = new Mp3File(path);
				if (song.hasId3v2Tag()) {
					ID3v2 id3v2tag = song.getId3v2Tag();
					Image a = new Image(new ByteArrayInputStream(id3v2tag.getAlbumImage()));
					//image.setImage(a);
					cir3.setFill(new ImagePattern(a));
					
				}
			} catch (Exception e) {

			}

			String procurarPor = "mp4";
			if (texto.toLowerCase().contains(procurarPor.toLowerCase()) == false) {

				mus.setText(name);
				initTxt();
				mediaa = new Media(AUDIO_URL);
				mediaPlayer = new MediaPlayer(mediaa);
				media = new MediaView(mediaPlayer);
				mediaPlayer.play();
				play.setText("Pause");

			} else {
				initVideo(path);
			}

		} else {

		}

	}

	void initVideo(String path) {
		File f = new File(path);
		mediaa = new Media(f.toURI().toString());
		mediaPlayer = new MediaPlayer(mediaa);
		media = new MediaView(mediaPlayer);
		DoubleProperty width = media.fitWidthProperty();
		DoubleProperty height = media.fitHeightProperty();
		width.bind(Bindings.selectDouble(media.sceneProperty(), "width"));
		height.bind(Bindings.selectDouble(media.sceneProperty(), "height"));
		media.setPreserveRatio(true);
		mediaPlayer.play();
		pane.getChildren().add(media);
	}

	public class Person {
		private final SimpleStringProperty Evento;
		private final SimpleStringProperty Data;
		private final SimpleStringProperty Nome;
		private final SimpleStringProperty Hora;

		private Person(String fName, String lName, String hora, String email) {
			this.Evento = new SimpleStringProperty(fName);
			this.Data = new SimpleStringProperty(lName);
			this.Hora = new SimpleStringProperty(hora);
			this.Nome = new SimpleStringProperty(email);
		}

		public String getFirstName() {
			return Evento.get();
		}

		public void setFirstName(String fName) {
			Evento.set(fName);
		}

		public String getLastName() {
			return Data.get();
		}

		public void setLastName(String fName) {
			Data.set(fName);
		}

		public String getEmail() {
			return Nome.get();
		}

		public void setEmail(String fName) {
			Nome.set(fName);
		}

		public String getHora() {
			return Hora.get();
		}

		public void setHora(String hora) {
			Hora.set(hora);
		}

	}

}
