package br.com.kelvingcr.emailautosend;

import br.com.kelvingcr.emailautosend.objects.ObjectSendEmail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private ImageView imageView1, imageView2;

    @FXML
    private ListView<String> listView = new ListView<String>();
    ObservableList<String> items = FXCollections.observableArrayList ();

    @FXML
    private TextField txtFieldEmailDestino, txtUsername, txtPassword, txtNomeEmpresa, txtAssuntoEmail;

    @FXML
    private Button btnAdicionar, btnRemover;

    @FXML
    private TextArea txtAssunto;


    public void initialize(){

        File file = new File("imgs/Rectangle 1.png");
        Image image = new Image(file.toURI().toString());
        imageView1.setImage(image);

        File file2 = new File("imgs/2579674.png");
        Image image2 = new Image(file2.toURI().toString());
        imageView2.setImage(image2);
    }

    public void onBtnAdicionar(){
        if(!txtFieldEmailDestino.getText().isEmpty()){
            items.add(txtFieldEmailDestino.getText());
            listView.setItems(items);
        }
    }

    public void onBtnRemover(){
        int index = listView.getSelectionModel().getSelectedIndex();
        if(index >= 0){
            listView.getItems().remove(index);
        }
    }


    public void onBtnEnviar() throws InterruptedException {

        if(txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty() || txtNomeEmpresa.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alerta!");
            alert.setContentText("O nome de usuario e senha inválidos");
            alert.setResizable(false);
            alert.show();
            return;
        }

        if(txtAssuntoEmail.getText().isEmpty()){
            txtAssuntoEmail.setText("<Sem assunto>");
        }

        if(listView.getItems().size() > 0){

            Runnable runnable = new Runnable() {
                @Override
                public void run() {

                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(txtAssunto.getText());

                    for(String email : listView.getItems()){
                        ObjectSendEmail objetoEnviaEmail = new ObjectSendEmail(
                                txtUsername.getText(),
                                txtPassword.getText(),
                                email,
                                txtNomeEmpresa.getText(),
                                txtAssuntoEmail.getText(),
                                stringBuilder.toString());

                        objetoEnviaEmail.enviarEmail(true);
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } // da um tempo
                    }
                }
            };

            Thread threadNota = new Thread(runnable);
            threadNota.start();
        }
    }



}