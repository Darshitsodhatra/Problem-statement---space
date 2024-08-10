import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class App extends Application 
{

    private TableView<App> table;
   public void finalreport(Stage primaryStage) {
        primaryStage.setTitle("Plant Disease Report");

        // Title
        Text title = new Text("Plant Disease Report");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // TableView for displaying the report
        table = new TableView<>();
        

        // Setting up columns
        TableColumn<App, String> plantNameColumn = new TableColumn<>("Plant Name");
        plantNameColumn.setMinWidth(150);
        plantNameColumn.setCellValueFactory(new PropertyValueFactory<>("plantName"));

        TableColumn<App, String> diseaseColumn = new TableColumn<>("Disease");
        diseaseColumn.setMinWidth(150);
        diseaseColumn.setCellValueFactory(new PropertyValueFactory<>("disease"));

        TableColumn<App, String> symptomsColumn = new TableColumn<>("Symptoms");
        symptomsColumn.setMinWidth(200);
        symptomsColumn.setCellValueFactory(new PropertyValueFactory<>("symptoms"));

        TableColumn<App, String> treatmentColumn = new TableColumn<>("Treatment");
        treatmentColumn.setMinWidth(200);
        treatmentColumn.setCellValueFactory(new PropertyValueFactory<>("treatment"));

        table.getColumns().addAll(plantNameColumn, diseaseColumn, symptomsColumn, treatmentColumn);

        // Layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(title, table);

        Scene scene = new Scene(layout, 800, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
        
    
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login Page");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Welcome to plant disease tracking system");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userIdLabel = new Label("User ID:");
        grid.add(userIdLabel, 0, 1);

        TextField userIdTextField = new TextField();
        grid.add(userIdTextField, 1, 1);

        Label usernameLabel = new Label("Username:");
        grid.add(usernameLabel, 0, 2);

        TextField usernameTextField = new TextField();
        grid.add(usernameTextField, 1, 2);

        Label emailLabel = new Label("Email:");
        grid.add(emailLabel, 0, 3);

        TextField emailTextField = new TextField();
        grid.add(emailTextField, 1, 3);

        Label passwordLabel = new Label("Password:");
        grid.add(passwordLabel, 0, 4);

        PasswordField passwordField = new PasswordField();
        grid.add(passwordField, 1, 4);

        Button loginBtn = new Button("Login");
        loginBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.BOTTOM_RIGHT);
        vbox.getChildren().add(loginBtn);
        grid.add(vbox, 1, 6);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 7);

        loginBtn.setOnAction(e -> {
            
            String userId = userIdTextField.getText();
            String username = usernameTextField.getText();
            String email = emailTextField.getText();
            String password = passwordField.getText();

            
            System.out.println("User ID: " + userId);
            System.out.println("Username: " + username);
            System.out.println("Email: " + email);
            System.out.println("Password: " + password);
            userRegister(userId, username, password, email);
            
            actiontarget.setFill(Color.GREEN);
            actiontarget.setText("Login successful!");

            showPlantInfoPage(primaryStage);
        });

        Scene scene = new Scene(grid, 400, 375);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void showPlantInfoPage(Stage primaryStage) {
        GridPane plantGrid = new GridPane();
        plantGrid.setAlignment(Pos.CENTER);
        plantGrid.setHgap(10);
        plantGrid.setVgap(10);
        plantGrid.setPadding(new Insets(25, 25, 25, 25));

        Text plantTitle = new Text("Enter Plant Information");
plantTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
plantGrid.add(plantTitle, 0, 0, 3, 1);


Label IdLabel = new Label("ID: ");
plantGrid.add(IdLabel, 0, 1);

TextField IdTextField = new TextField();
plantGrid.add(IdTextField, 1, 1, 2, 1);


Label plantName = new Label("Plant Name :");
plantGrid.add(plantName, 0, 2);

TextField pname = new TextField();
plantGrid.add(pname, 1, 2, 2, 1);


Label disLabel = new Label("Disease Name :");
plantGrid.add(disLabel, 0, 3);

TextField plantdisTextField = new TextField();
plantGrid.add(plantdisTextField, 1, 3, 2, 1);


Label latlabel = new Label("Latitude :");
plantGrid.add(latlabel, 0, 4);

TextField ltTextField = new TextField();
plantGrid.add(ltTextField, 1, 4, 2, 1);


Label longLabel = new Label("Longitude :");
plantGrid.add(longLabel, 0, 5);

TextField lTextField = new TextField();
plantGrid.add(lTextField, 1, 5, 2, 1);


Button submitBtn = new Button("Generate Report");
plantGrid.add(submitBtn, 2, 6);


GridPane.setColumnSpan(plantTitle, 3);
GridPane.setMargin(submitBtn, new Insets(10, 0, 0, 0));
        submitBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.BOTTOM_RIGHT);
        vbox.getChildren().add(submitBtn);
        plantGrid.add(vbox, 1, 6);

        submitBtn.setOnAction(e -> {
            
            String Id = IdTextField.getText();
            String plantname = pname.getText();
            String latt = ltTextField.getText();
            String dis = plantdisTextField.getText();
            String lonng = lTextField.getText();

            System.out.println("ID: " + Id);
            System.out.println("Plant Name : " + plantname);
            System.out.println("Diseas name : " + dis);
            System.out.println("latitude : "+latt);
            System.out.println("Longitude : " + lonng);
            reportDiseas(Id, plantname, dis, latt, lonng);
            finalreport(primaryStage);
        });

        Scene plantScene = new Scene(plantGrid, 400, 375);
        primaryStage.setScene(plantScene);
        primaryStage.show();
    }

    public static void main(String[] args) 
    {   
        launch(args);
    }
    public static void userRegister(String id,String name,String pass,String em)
  {
    try
    {
       String url = "jdbc:mysql://localhost:3306/hackout";
       String username = "root";
       String password = "bhaikadata123@";
       Connection con = DriverManager.getConnection(url, username, password);
       
       if(con.isClosed())
       {
        System.out.println("Connection closed!");
       }
       else
       {
        System.out.println("Connection created");
       }
        // String q = "";
      
        
        
        String in = "insert into users(user_id,username,password,email) values(?,?,?,?)";
        PreparedStatement p = con.prepareStatement(in);

        p.setString(1, id);
        p.setString(2, name);
        p.setString(3, pass);
        p.setString(4, em);
       
        p.executeUpdate();

       Statement s = con.createStatement();
        // s.executeUpdate(q);
        // System.out.println("Table created");
       con.close();
    } 
    catch(Exception e)
    {
        e.printStackTrace();
    }
  }
  public static void reportDiseas(String id,String name,String diseasname,String lat,String longg)
  {
    try
    {
       String url = "jdbc:mysql://localhost:3306/hackout";
       String username = "root";
       String password = "bhaikadata123@";
       Connection con = DriverManager.getConnection(url, username, password);
    String q = "insert into disease_reports(user_id,plant_name,disease_name,latitude,longitude) values(?,?,?,?,?)";
    PreparedStatement p = con.prepareStatement(q);

    p.setString(1, id);
    p.setString(2, name);
    p.setString(3, diseasname);
    p.setString(4, lat);
    p.setString(5, longg);
   
    p.executeUpdate();
    con.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
}


  

  

