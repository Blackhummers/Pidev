package javafxtest;

import com.esprit.Entity.Categorie;
import com.esprit.Entity.Commentaire;
import com.esprit.Entity.Likerecette;
import com.esprit.Entity.Recette;
import com.esprit.Entity.User;
import com.esprit.Service.CategorieService;
import com.esprit.Service.CommentaireService;
import com.esprit.Service.LikerecetteService;
import com.esprit.Service.RecetteService;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DetailRecetteController implements Initializable {

    @FXML
    private Label nom;
    @FXML
    private TextArea preparation;
    @FXML
    private Label besoin;
    @FXML
    private Label ingredient;
    @FXML
    private Label duree;
    @FXML
    private ImageView imagev;
    @FXML
    private Label jaime;
    @FXML
    private Label jaimepas;
    @FXML
    private AnchorPane anchorpane;
    @FXML
    private TextField contenu;
    @FXML
    private Button j;
    @FXML
    private Button jp;
    
    @FXML
    private TableView<Commentaire> tableCommentaire;
    @FXML
    private TableColumn<Commentaire,String> contenue;
    Recette u;
    User user;
    @FXML
    private Label categorie;
    
    public DetailRecetteController(Recette u) {
        this.u = u;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        RecetteService us = new RecetteService();
        //Recette u=us.AfficherRecetteId();

        nom.setText(u.getNom());
        preparation.setText(u.getPreparation());
        ingredient.setText(u.getBN());
        besoin.setText(u.getBesoin());
        duree.setText(Integer.toString(u.getDuree()));
        jaime.setText(Integer.toString(u.getLikee()));
        jaimepas.setText(Integer.toString(u.getDislike()));
        // String path = "C:/xampp/htdocs/PiDev/web/images/";
        Image img = new Image("http://localhost/PiDev/web/images/" + u.nom_image,700,700, false, false);
        imagev.setImage(img);
//        categorie.setText(u.getId_categorie().getNom().toString());
        //////
        tableCommentaire.setVisible(false);

    }

    @FXML
    private void back(ActionEvent event) {
        makeFadeOutRecette();
    }

    private void makeFadeOutRecette() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(anchorpane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished(new EventHandlerImplRecette());
        fadeTransition.play();
    }

    private void loadNextScrenRecette() {
        try {
            Parent secondView = FXMLLoader.load(getClass().getResource("recette.fxml"));
            //secondView = (StackPane) 
            Scene newScene = new Scene(secondView);
            Stage curStage = (Stage) anchorpane.getScene().getWindow();
            curStage.setScene(newScene);
        } catch (IOException ex) {
        }
    }



    private class EventHandlerImplRecette implements EventHandler<javafx.event.ActionEvent> {

        public EventHandlerImplRecette() {
        }

        @Override
        public void handle(javafx.event.ActionEvent event) {
            loadNextScrenRecette();
        }
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setScene(home_page_scene);
        app_stage.show();
    }

    Likerecette like = new Likerecette();

    @FXML
    private void jaime(ActionEvent event) {

        LikerecetteService es = new LikerecetteService();
        RecetteService rs = new RecetteService();
        //Recette r = new Recette();
        int likee=like.getValeur();
        
        
        j.setVisible(false);
        jp.setVisible(true);
       // rs.updateLike(new Recette(likee));
        /*
        if (like.getValeur() == 0) 
        {
            like.setValeur(1);
            like.setRecette_id(u);
            //like.setUser_id(user);
            es.ajouteLike(like);
        }
*/
    }

    @FXML
    private void jaimepas(ActionEvent event) {
        
                
        jp.setVisible(false);        
        j.setVisible(true);
    }

    Commentaire r = new Commentaire();

    @FXML
    private void ajoutCommentaire(ActionEvent event) {
        CommentaireService es = new CommentaireService();
        r.setContenu(contenu.getText());
        r.setRecette_id(u);
        es.createCommentair(r);
        contenu.clear();
    }
    
    @FXML
    private void autreCommentaire(ActionEvent event) throws IOException {
                tableCommentaire.setVisible(true);

        CommentaireService service = new CommentaireService();
     ObservableList<Commentaire> listCommentaire =
             FXCollections.observableArrayList(service.AfficherCommentaire(u.getId()));

   contenue.setCellValueFactory(new PropertyValueFactory("contenu"));
       tableCommentaire.setItems(listCommentaire);
    }
    
        @FXML
    private void retour(ActionEvent event) {
       tableCommentaire.setVisible(false);    
    }
}
