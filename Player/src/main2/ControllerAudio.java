package main2;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;
import com.google.common.io.Files;

import java.util.Iterator;
import java.util.Properties;
import com.jfoenix.controls.*;
import com.jfoenix.transitions.JFXFillTransition;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;

import RestAPI.retrofitGson.App;
import javafx.animation.KeyFrame;

import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	* 										 * 
	*		  JAVAFX DECLARATIONS			 *
	* 										 *
	******************************************/	
	@FXML
    private Button menu;	
    @FXML
    public AnchorPane pane  ,  navList;    
    @FXML
    public Label mus , playTime , rock;
    @FXML
    private JFXSlider slider;
	@FXML
    private MediaView media;
	@FXML
	public ImageView image;
    @FXML
	public TextField txtS, link;
	@FXML
	public StackPane stack;  	
	@FXML
    private Button play2 , aleatorio, last2;
    @FXML
    public Pane pane1 , pan2 , pane3, pane5, pane6;
    @FXML
    private GridPane Grid;
    @FXML
    WebView webview;
    @FXML
    Button lupa , coe , x;
    @FXML
    Circle cir2;


    /*****************************************
	* 										 * 
	*		  JPHOENIX DECLARATIONS			 *
	* 										 *
	******************************************/	
    @FXML
    private JFXHamburger menu2;
	@FXML
	private JFXButton play , stop, converter, selecionar;
	@FXML
	private JFXTextField SearchY  ,  login;
	@FXML
	private JFXPasswordField senha;
	@FXML
	private JFXListView<Label> list;
	@FXML
	private JFXSpinner spinner;
	@FXML
	private JFXProgressBar progress;
	/*****************************************
	* 										 * 
	*		  DECLARATIONS GLOBAIS			 *
	* 										 *
	******************************************/
	Duration duration;
    String path2;    
    AudioClip clip;
    Media mediaa;
    MediaPlayer mediaPlayer;
    TranslateTransition closeNav;
    int contador = 0;
    
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
	* 										 * 
	*		  		Methods FXML			 *
	* 										 *
	******************************************/
    public static String album;
    public Thread last;
    
    @FXML
    void down(ActionEvent event) {
    	pan2.setVisible(false);
    	pane3.setVisible(false);
    	pane5.setVisible(false);
    	pane6.setVisible(true);

    }
    @FXML
    void selecionar(ActionEvent event) {
    	 FileChooser fileChooser = new FileChooser();
    	 fileChooser.setTitle("Open Resource File");
    	 fileChooser.getExtensionFilters().addAll(
    	         new ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),
    	         new ExtensionFilter("Video Files", "*.mp4", "*.webm"),
    	         new ExtensionFilter("All Files", "*.*"));
    	 File selectedFile = fileChooser.showOpenDialog(null);
    	 if (selectedFile != null) {
    		String path =  selectedFile.getPath();
    		Thread thread = new Thread(new Convert(path));
    		thread.start();
    		
    	 }
    }
    
    @FXML
    void converter(ActionEvent event) {
    	Thread t3 = new Thread(new Download(link.getText()));
        t3.start();

    }
    
    @FXML
    void last(ActionEvent event) throws InterruptedException {
    	String i = "";
    	
    	
    	i = arquivos.get(list.getSelectionModel().getSelectedIndex()).getPath();
    	i = Files.getNameWithoutExtension(i);
    	last = new Thread(new App(i));
    	last.start();
    	
    	
     synchronized(last){
         try{
             System.out.println("Aguardando o b completar...");
             last.wait();
             
         }catch(InterruptedException e){
             e.printStackTrace();
         }         
         Thread t2 = new Thread() {
             @Override
             public void run() {
         while(last.isAlive()){
             try {                    	
					last.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
              }
              Platform.runLater(() -> {
                Image imagem = new Image(album);
               	image = new ImageView(imagem);
               	pane1.getChildren().add(image); 
                   		
             		  }
             		); 
             }
         };
     t2.start();
     t2.sleep(1000);
         
     }
    	
     
    }
    
    
    void initTxt(){
    double sceneWidth = pane.getWidth();
    double msgWidth = mus.getLayoutBounds().getWidth();
    KeyValue initKeyValue = new KeyValue(mus.translateXProperty(), 1500);
    KeyFrame initFrame = new KeyFrame(Duration.ZERO, initKeyValue);

    KeyValue endKeyValue = new KeyValue(mus.translateXProperty(), 0.0
        * msgWidth);
    KeyFrame endFrame = new KeyFrame(Duration.seconds(6), endKeyValue);

    Timeline timeline = new Timeline(initFrame, endFrame);

    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();

    }
    
    @FXML
    void music(ActionEvent event) {
    	last2.setVisible(true);
    	pan2.setVisible(false);
    	pane1.setVisible(true);
    	volte();

    } 
    /** Method to  **/
    @FXML
    void clickV(ActionEvent event) {
    	pane1.setVisible(false);
    	pan2.setVisible(true);
    	volte();
    	
    }
    /** Methods for remove WebView when it is Visible **/
    @FXML
    void x(ActionEvent event) {
    	pane.getChildren().remove(webview);

    }
    /** Method to login **/
    @FXML
    void logar(ActionEvent event) {
    	String login2 = login.getText();
    	String senha2 = senha.getText();
    	
    	if(login2.equals("admin")){
    		if(senha2.equals("123")){
    			pane3.setVisible(false);
    		}
    	}
    }
    /** Methods to call Youtube Search **/
    Thread t1;
    @FXML
    void search(ActionEvent event) throws InterruptedException {
    	Grid.getChildren().clear();	imagem.clear();	Id.clear();	Title.clear();
    	Url.clear(); labels.clear(); IMAGENS.clear();      
    	
    	t1 = new Thread(new Search(SearchY.getText()));
    	t1.start();
    	
    	spinner.setVisible(true);
    	 Thread t2 = new Thread() {
             @Override
             public void run() {
                    while(t1.isAlive()){
                    try {                    	
						t1.join();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
                     }
                     Platform.runLater(() -> {
                    			  for(int i = 0; i<3 ; i++){
                          			
                                  	labels.add(new Label(Title.get(i)));
                                  	imagem.add(new Image(Url.get(i)));
                                  	IMAGENS.add(new ImageView(imagem.get(i)));
                                  	buttons.add(new Button("Compartilhar"));
                                  	buttons.get(i).setMinWidth(100);                                  	
                                  	Grid.add(buttons.get(i),2,i);
                                  	Grid.add(IMAGENS.get(i),0,i);
                                  	Grid.add(labels.get(i),1,i);
                                  	buttons.get(i).setOnAction(action2(i));
                                    IMAGENS.get(i).setOnMouseClicked(action(i));                                    
                                    
                          		}
                    		  }
                    		);                     
                     spinner.setVisible(false);             
             }

      };
      t2.start();
 
    }
    	
    //Set action on ImageView after search Video of Youtube
    private EventHandler<? super MouseEvent> action(int i) {
    	
    	EventHandler<MouseEvent> action = new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {            	
            	webview = new WebView();
                webview.getEngine().load(
                  "https://www.youtube.com/embed/"+Id.get(i) + "?autoplay=1"
                	//	"https://www.youtube.com/watch?v=EkHTsc9PU2A"
                );

                pane.getChildren().add(webview);
               
            }
       	};   	
    	
		return action;
	}
    
    //Set action on ImageView after search Video of Youtube
    private EventHandler<ActionEvent> action2(int i) {
    	
    	EventHandler<ActionEvent> action2 = new EventHandler<ActionEvent>(){
    		@Override public void handle(ActionEvent e) {
    	       
    			java.awt.datatransfer.Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    	    	  String text = "https://www.youtube.com/watch?v="+Id.get(i);
    	    	  StringSelection selection = new StringSelection(text);
    	    	  clipboard.setContents(selection, null);
    	    }
    	};	
    	
		return action2;
	}
    

    @FXML
    void menu2(MouseEvent event) {
 	   TranslateTransition openNav=new TranslateTransition(new Duration(350), navList);
        openNav.setToX(0);
        closeNav=new TranslateTransition(new Duration(350), navList);
 	   if(navList.getTranslateX()!=0){
            openNav.play();
        }else{
            closeNav.setToX(-(navList.getWidth()));
            closeNav.play();
        }
    }
    
     @FXML
     void open(ActionEvent event) throws FileNotFoundException {
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

    	 mediaPlayer.currentTimeProperty().addListener((ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) -> {
             updateValues2();
            });

    	 // Time slider
    	    slider.setMinWidth(50);
    	    slider.setMaxWidth(Double.MAX_VALUE);
    	    slider.valueProperty().addListener((Observable ov) -> {
                if (slider.isValueChanging()) {
                    // multiply duration by percentage calculated by slider position
                    if(duration!=null) {
                        mediaPlayer.seek(duration.multiply(slider.getValue() / 100.0));
                    }
                    updateValues2();
                }
            });
    	 
     }

     @FXML
     void click(ActionEvent event){
     	if(t == true){
     	 SepiaTone sepiaTone = new SepiaTone();
     	 sepiaTone.setLevel(0.7);
     	aleatorio.setEffect(sepiaTone);
     	t = false;
     	}else{
     		aleatorio.setEffect(null);
     		t = true;
     	}

     }
     @FXML
     void stop(ActionEvent event){
     	mediaPlayer.stop();
     	mus.setText("");

         }
     @FXML
     void ante(ActionEvent event){
    	mediaPlayer.stop();
      	String name = arquivos.get(list.getSelectionModel().getSelectedIndex()).getName();
        String path = arquivos.get(list.getSelectionModel().getSelectedIndex()-1).getAbsolutePath();
        path = path.replace("\\","/");
        File file = new File(path);
         
     	String AUDIO_URL = file.toURI().toString();

     	String texto = AUDIO_URL;

         String procurarPor = "mp4";
 		if(texto.toLowerCase().contains(procurarPor.toLowerCase()) == false){

     	mus.setText(name);
        initTxt();
     	mediaa = new Media(AUDIO_URL);
     	mediaPlayer = new MediaPlayer(mediaa);
     	media = new MediaView(mediaPlayer);
     	mediaPlayer.play();
     	list.getSelectionModel().select(list.getSelectionModel().getSelectedIndex()-1);
  		list.scrollTo(list.getSelectionModel().getSelectedIndex()-1);
  		initSlider();
         }
     }

     @FXML
     void prox(ActionEvent event) throws UnsupportedEncodingException  {
     	mediaPlayer.stop();

   	 
     	String name = arquivos.get(list.getSelectionModel().getSelectedIndex()).getName();
        String path = arquivos.get(list.getSelectionModel().getSelectedIndex()+1).getAbsolutePath();
        path = path.replace("\\","/");
        File file = new File(path);
        
    	String AUDIO_URL = file.toURI().toString();

    	String texto = AUDIO_URL;

        String procurarPor = "mp4";
		if(texto.toLowerCase().contains(procurarPor.toLowerCase()) == false){

    	mus.setText(name);
        initTxt();
    	mediaa = new Media(AUDIO_URL);
    	mediaPlayer = new MediaPlayer(mediaa);
    	media = new MediaView(mediaPlayer);
    	mediaPlayer.play();
    	list.getSelectionModel().select(list.getSelectionModel().getSelectedIndex()+1);
 		list.scrollTo(list.getSelectionModel().getSelectedIndex()+1);
 		initSlider();
 		
 		
		}
     	/*
     	String name = arquivos.get(list.getSelectionModel().getSelectedIndex()+1);
     	
     	File AUDIO_URL = new File(path);
     	mus.setText(name);
     	mediaa = new Media(AUDIO_URL.toURI().toString());
     	mediaPlayer = new MediaPlayer(mediaa);
     	media = new MediaView(mediaPlayer);
     	mediaPlayer.setAutoPlay(true);
 		play.setText("Pause");
 		list.getSelectionModel().select(list.getSelectionModel().getSelectedIndex()+1);
 		list.scrollTo(list.getSelectionModel().getSelectedIndex()+1);
*/
         }
     void music() {
    	 
    	 Status status = mediaPlayer.getStatus();
      	if(status.PLAYING != null) {
    		 System.out.println(mediaPlayer.getStatus());
    		 
    	 }else {
    		 
    	 
    	 
    	 
    	 mediaPlayer.stop();
      	String name = arquivos.get(list.getSelectionModel().getSelectedIndex()).getName();
         String path = arquivos.get(list.getSelectionModel().getSelectedIndex()+1).getAbsolutePath();
         path = path.replace("\\","/");
         File file = new File(path);
         
     	String AUDIO_URL = file.toURI().toString();

     	String texto = AUDIO_URL;

         String procurarPor = "mp4";
 		if(texto.toLowerCase().contains(procurarPor.toLowerCase()) == false){

     	mus.setText(name);
         initTxt();
     	mediaa = new Media(AUDIO_URL);
     	mediaPlayer = new MediaPlayer(mediaa);
     	media = new MediaView(mediaPlayer);
     	mediaPlayer.play();
     	list.getSelectionModel().select(list.getSelectionModel().getSelectedIndex()+1);
  		list.scrollTo(list.getSelectionModel().getSelectedIndex()+1);
  		initSlider();
    	 
 		}
    	 
     }
     }

     @FXML
     void im (ActionEvent event) throws IOException{

     	 DirectoryChooser directoryChooser = new DirectoryChooser();
         directoryChooser.setTitle("Select Some Directories");
         directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
         String dir = directoryChooser.showDialog(null).toString();
         
	     	 
      	try{     		
      		
            list.getItems().clear();
            listlbl.clear();
            arquivos.clear();
      		
      		
      		 arquivos = java.nio.file.Files
    				.walk(java.nio.file.Paths.get(dir))
    				.filter(java.nio.file.Files::isRegularFile).map(java.nio.file.Path::toFile).collect(java.util.stream.Collectors.toList());
      		
      		
      		Iterator<File> arquivos2 = arquivos.iterator(); 
      		
      		int ia = 0;
      		while (arquivos2.hasNext()) {
    			if (arquivos.get(ia).getName().toString().endsWith(".mp3")) {
    				
    				listlbl.add(new Label(arquivos.get(ia).getName()));
    	     		list.getItems().add(listlbl.get(ia));
    				
    			}
    			
    			ia += 1;
    		}
      		
      		/*
    		while (arquivos.size() > ia) {
    			if (arquivos.get(ia).getName().toString().endsWith(".mp3")) {
    				
    				listlbl.add(new Label(arquivos.get(ia).getName()));
    	     		list.getItems().add(listlbl.get(ia));
    				
    			}else {
    				arquivos.remove(ia);
    				
    			}
    			ia += 1;
    		}
/*
     	for(int c = 0; c != arquivos.size(); c++){
       	 String extension = "";

       	 int i = arquivos.get(c).getName().lastIndexOf('.');
       	 if (i >= 0) {
       	     extension = arquivos.get(c).getName().substring(i+1);
       	 }
       	     if(extension.equals("mp3") || extension.equals("mp4") ) {
               	 System.out.println("Ok");
               	 
               }else if(extension.equals("mov") || extension.equals("jpg") ){
               	 arquivos.remove(c);

                }else {
               	 arquivos.remove(c);

                }
	 
       	contador++;
       	 
       }
     	 
     	 */
     	
     	}catch(Exception e){
     		System.out.println(e);
     	}
     	 
         
         File f = new File(getClass().getResource("/main2/dir.txt").getFile());
         BufferedWriter writer = new BufferedWriter(new FileWriter(f));
         writer.write(dir);
         writer.flush();
         writer.close();
        

     }
    String path;
    /*****************************************
	* 										 * 
	*		  		INICIAR FXML			 *
	* 										 *
	******************************************/
    @FXML
    void initialize() throws IOException {
    	cir2 = new Circle(50,50,50);
    	Image profile = new Image("http://i.imgur.com/zIMDPTS.jpg");
    	cir2.setFill(new ImagePattern(profile));
    	pane5.getChildren().add(cir2);
    	
    	
    	
    	pane3.setVisible(true);
    	initTran();

    	list.getStyleClass().add("mylistview");
    	HamburgerSlideCloseTransition burgerTask = new HamburgerSlideCloseTransition(menu2);
    	burgerTask.setRate(-1);
    	menu2.addEventHandler(MouseEvent.MOUSE_PRESSED, (e)->{    		
    	    burgerTask.setRate(burgerTask.getRate()*-1);
    	    burgerTask.play();
    	});
    	menu2.getStyleClass().add("jfx-hamburger-icon");
    	
    try{
    	 File f = new File(getClass().getResource("/main2/dir.txt").getFile());
    	 BufferedReader br = new BufferedReader(new FileReader(f));
    	 path = br.readLine();
    	 
    	 list.getItems().clear();
         listlbl.clear();
         arquivos.clear();
   		
   		
   		 arquivos = java.nio.file.Files
 				.walk(java.nio.file.Paths.get(path))
 				.filter(java.nio.file.Files::isRegularFile).map(java.nio.file.Path::toFile).collect(java.util.stream.Collectors.toList());
   		
   		
   		Iterator<File> arquivos2 = arquivos.iterator(); 
   		
   		int ia = 0;
   		while (arquivos2.hasNext()) {
 			if (arquivos.get(ia).getName().toString().endsWith(".mp3")) {
 				
 				listlbl.add(new Label(arquivos.get(ia).getName()));
 	     		list.getItems().add(listlbl.get(ia));
 				
 			} else {
 				arquivos.remove(ia);
 				
 			}
 			
 			ia += 1;
 		}
   		
    	 
    	 /*
    	 File diretorio = new File (path);
    	 for(File item : diretorio.listFiles() ){
    		 arquivos.add(item);
    	 }
         for(int c = 0; c != arquivos.size()+1; c++){
        	 String extension = "";

        	 int i = arquivos.get(c).getName().lastIndexOf('.');
        	 if (i >= 0) {
        	     extension = arquivos.get(c).getName().substring(i+1);
        	     System.out.println(extension);
        	 }
        	     
        	     if(extension.equals("mp3") || extension.equals("mp4") ) {
                	 System.out.println("Ok");
                	 
                } else {
                	 arquivos.remove(c); 
                 }     
        	 
        	  
	 
        	contador++;
        	 
        }

*/  
         for(int c = 0; c != arquivos.size(); c++){
     		listlbl.add(new Label(arquivos.get(c).getName()));
     		listlbl.get(c).setGraphic(new ImageView(new Image(new FileInputStream("C:\\Users\\Fabio\\workspace\\Player\\src\\images\\lupa.png"))));
     		list.getItems().add(listlbl.get(c));
         }
    	 br.close();

        }catch (Exception e){
        	
        	
        	
        	
        	
        	
        	
        	System.out.println(e);
        }
    }
    
    
    
    
    
    /*****************************************
	* 										 * 
	*		  		Methods 				 *
	* 										 *
	******************************************/
    void initList(){
    	
    	list.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (e.getButton() == MouseButton.SECONDARY) {
                    cm.show(list, e.getScreenX(), e.getScreenY());
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
               String menu = ((MenuItem)event.getTarget()).getText();
           
               if(menu == "Renomear"){
            	  //LAYOUT DO DIALOGO

                 JFXDialog dialog = new JFXDialog();
            	 JFXDialogLayout content = new JFXDialogLayout();
            	 content.setHeading(new javafx.scene.text.Text("Renomear"));
            	 
            	 TextField renomear = new TextField();
            	 content.setBody(renomear);
            	 
            	 JFXButton okei = new JFXButton("Done");
            	 okei.setOnAction(new EventHandler<ActionEvent>() {
            		 @Override
            		 public void handle(ActionEvent event) {
            			 dialog.close();
            		 }
            	 });            	 
            	 content.setActions(okei);
            	 
            	 
            	 
            	 
            	 //
            	stack.setVisible(true);
               	dialog.setContent(content);              
          		dialog.show(stack);
          				
              	String a = dialog.toString();
              	System.out.println("a");
               	int name = list.getSelectionModel().getSelectedIndex();
               	
               	
               }
               
             }
         });

         MenuItem menuItem1 = new MenuItem("Renomear");
         MenuItem menuItem2 = new MenuItem("Remover");
         MenuItem menuItem3 = new MenuItem("Adicionar Playlist");

         cm.getItems().addAll(menuItem1, menuItem2, menuItem3);
    	
    }
    
    void initTran(){
	    JFXSnackbar snack = new JFXSnackbar(pane);
		snack.show("Hello",3000);
	
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
                  if (!slider.isDisabled()
                          && duration.greaterThan(Duration.ZERO)
                          && !slider.isValueChanging()) {
                      slider.setValue(currentTime.divide(duration).toMillis()
                              * 100.0);
                  }
                   });
	 }
    private static String formatTime(Duration elapsed, Duration duration) {
	   int intElapsed = (int)Math.floor(elapsed.toSeconds());
	   int elapsedHours = intElapsed / (60 * 60);
	   if (elapsedHours > 0) {
	       intElapsed -= elapsedHours * 60 * 60;
	   }
	   int elapsedMinutes = intElapsed / 60;
	   int elapsedSeconds = intElapsed - elapsedHours * 60 * 60
	                           - elapsedMinutes * 60;

	   if (duration.greaterThan(Duration.ZERO)) {
	      int intDuration = (int)Math.floor(duration.toSeconds());
	      int durationHours = intDuration / (60 * 60);
	      if (durationHours > 0) {
	         intDuration -= durationHours * 60 * 60;
	      }
	      int durationMinutes = intDuration / 60;
	      int durationSeconds = intDuration - durationHours * 60 * 60 -
	          durationMinutes * 60;
	      if (durationHours > 0) {
	         return String.format("%d:%02d:%02d/%d:%02d:%02d",
	            elapsedHours, elapsedMinutes, elapsedSeconds,
	            durationHours, durationMinutes, durationSeconds);
	      } else {
	          return String.format("%02d:%02d/%02d:%02d",
	            elapsedMinutes, elapsedSeconds,durationMinutes,
	                durationSeconds);
	      }
	      } else {
	          if (elapsedHours > 0) {
	             return String.format("%d:%02d:%02d", elapsedHours,
	                    elapsedMinutes, elapsedSeconds);
	            } else {
	                return String.format("%02d:%02d",elapsedMinutes,
	                    elapsedSeconds);
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
    boolean tocando = true;
    void initPlay(){
    	if(tocando == true) {
    		try {
    		
    		mediaPlayer.seek(mediaPlayer.getStartTime());
    		
    		} catch(Exception e) {
    			System.out.println("ue kk");
    		}
    		tocando = false;
    		
    	
    	
    	
    	String name = arquivos.get(list.getSelectionModel().getSelectedIndex()).getName();
        String path = arquivos.get(list.getSelectionModel().getSelectedIndex()).getAbsolutePath();
        path = path.replace("\\","/");
        File file = new File(path);
        
    	String AUDIO_URL = file.toURI().toString();

    	String texto = AUDIO_URL;

        String procurarPor = "mp4";
		if(texto.toLowerCase().contains(procurarPor.toLowerCase()) == false){

    	mus.setText(name);
        initTxt();
    	mediaa = new Media(AUDIO_URL);
    	mediaPlayer = new MediaPlayer(mediaa);
    	media = new MediaView(mediaPlayer);
    	mediaPlayer.play();
    	play.setText("Pause");
        try {
        File imagens = new File("C:\\Users\\thiago.felicio\\Desktop\\Download\\Temp");
        
        for(File item : imagens.listFiles() ){
   		 arquivosI.add(item);
   	 } 
        String //texto2
        procurarPor2;
        procurarPor2 = "[\\^]?*+-. *"+mus.getText();


        for(int c = 0; c != arquivosI.size(); c++){
        if(arquivosI.get(c).getName().toLowerCase().contains(procurarPor2.toLowerCase()) != true){
             System.out.println("toaqui");
             path2 = arquivosI.get(c).getPath();
        File file2 = new File(path2);           //Pega a imagem da pasta, se estiver com o mesmo nome aparece
        Image image2 = new Image(file2.toURI().toString());         // imagem de album;
        image.setImage(image2);
             break;
        }

        }
        }catch(Exception e) {
        	System.out.println("opa");
        }

    }else{
    	initVideo(path);
    }
		} else {
    		tocando = true;
    		mediaPlayer.pause();
    		
    		
    		
    		
    		
    	}
    	
    	
    	
    	
    	}
    
    void initVideo(String path){
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
    }
