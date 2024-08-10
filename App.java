import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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


public class App extends Application 
{
    public class MapService {

        public void plotMarkers(List<PlantReport> reports, WebEngine webEngine) {
            StringBuilder jsMarkers = new StringBuilder();
    
            for (PlantReport report : reports) {
                double latitude = report.getLatitude();
                double longitude = report.getLongitude();
                jsMarkers.append("new mapboxgl.Marker().setLngLat([")
                         .append(longitude)
                         .append(", ")
                         .append(latitude)
                         .append("]).addTo(map);\n");
            }
    
            String htmlContent = "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "    <script src=\"https://api.mapbox.com/mapbox-gl-js/v2.5.0/mapbox-gl.js\"></script>\n" +
                    "    <link href=\"https://api.mapbox.com/mapbox-gl-js/v2.5.0/mapbox-gl.css\" rel=\"stylesheet\" />\n" +
                    "    <script>\n" +
                    "        mapboxgl.accessToken = 'YOUR_MAPBOX_ACCESS_TOKEN';\n" +
                    "        function initMap() {\n" +
                    "            var map = new mapboxgl.Map({\n" +
                    "                container: 'map',\n" +
                    "                style: 'mapbox://styles/mapbox/streets-v11',\n" +
                    "                center: [-74.5, 40],\n" +
                    "                zoom: 3\n" +
                    "            });\n" +
                    jsMarkers +
                    "        }\n" +
                    "    </script>\n" +
                    "</head>\n" +
                    "<body onload=\"initMap()\">\n" +
                    "    <div id=\"map\" style=\"height: 100%; width: 100%;\"></div>\n" +
                    "</body>\n" +
                    "</html>";
    
            webEngine.loadContent(htmlContent);
        }
    }
    public class PlantReport {
        private int id;
        private String plantName;
        private String diseaseName;
        private double latitude;
        private double longitude;
    
        // Constructor, getters, and setters
    
        public PlantReport(int id, String plantName, String diseaseName, double latitude, double longitude) {
            this.id = id;
            this.plantName = plantName;
            this.diseaseName = diseaseName;
            this.latitude = latitude;
            this.longitude = longitude;
        }
    
        public int getId() {
            return id;
        }
    
        public String getPlantName() {
            return plantName;
        }
    
        public String getDiseaseName() {
            return diseaseName;
        }
    
        public double getLatitude() {
            return latitude;
        }
    
        public double getLongitude() {
            return longitude;
        }
    }
    public class PlantReportDAO {
        public List<PlantReport> getAllReports() {
            List<PlantReport> reports = new ArrayList<>();
            String query = "SELECT * FROM disease_reports";
            String url = "jdbc:mysql://localhost:3306/hackout";
       String username = "root";
       String password = "bhaikadata123@";
       
            try {
                Connection con = DriverManager.getConnection(url, username, password);
                 Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery(query); 
                 try{
    
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String plantName = rs.getString("plant_type");
                    String diseaseName = rs.getString("disease_id");
                    double latitude = rs.getDouble("latitude");
                    double longitude = rs.getDouble("longitude");
    
                    reports.add(new PlantReport(id, plantName, diseaseName, latitude, longitude));
                }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return reports;
        }
    }
    public void reportfinal(Stage primaryStage) {
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        // Get plant reports from database
        PlantReportDAO reportDAO = new PlantReportDAO();
        List<PlantReport> reports = reportDAO.getAllReports();

        // Plot the markers on the map
        MapService mapService = new MapService();
        mapService.plotMarkers(reports, webEngine);

        try
        {
            Scene scene = new Scene(webView, 800, 600);
            primaryStage.setTitle("Plant Disease Map");
        primaryStage.setScene(scene);
        primaryStage.show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login Page");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Welcome");
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


Label userIdLabel = new Label("User ID:");
plantGrid.add(userIdLabel, 0, 1);

TextField userIdTextField = new TextField();
plantGrid.add(userIdTextField, 1, 1, 2, 1);


Label plantType = new Label("Plant Type:");
plantGrid.add(plantType, 0, 2);

TextField typeTextField = new TextField();
plantGrid.add(typeTextField, 1, 2, 2, 1);


Label disLabel = new Label("Disease ID:");
plantGrid.add(disLabel, 0, 3);

TextField plantdisTextField = new TextField();
plantGrid.add(plantdisTextField, 1, 3, 2, 1);


Label treatlabel = new Label("Treatment Used:");
plantGrid.add(treatlabel, 0, 4);

TextField trtTextField = new TextField();
plantGrid.add(trtTextField, 1, 4, 2, 1);


Label locationLabel = new Label("Location:");
plantGrid.add(locationLabel, 0, 5);

TextField locationTextField = new TextField();
plantGrid.add(locationTextField, 1, 5, 2, 1);


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
            
            String userId = userIdTextField.getText();
            String plantype = typeTextField.getText();
            String treat = trtTextField.getText();
            String dis = plantdisTextField.getText();
            String location = locationTextField.getText();

            System.out.println("User ID: " + userId);
            System.out.println("Plant Type: " + plantype);
            System.out.println("Diseas : " + dis);
            System.out.println("TreatMent : "+treat);
            System.out.println("Location: " + location);
            reportDiseas(userId, plantype, dis, location, treat);
            reportfinal(primaryStage);
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
  public static void reportDiseas(String userid,String type,String diseasid,String loc,String treatment)
  {
    try
    {
       String url = "jdbc:mysql://localhost:3306/hackout";
       String username = "root";
       String password = "bhaikadata123@";
       Connection con = DriverManager.getConnection(url, username, password);
    String q = "insert into disease_reports(user_id,plant_type,disease_id,location,treatment_used) values(?,?,?,?,?)";
    PreparedStatement p = con.prepareStatement(q);

    p.setString(1, userid);
    p.setString(2, type);
    p.setString(3, diseasid);
    p.setString(4, loc);
    p.setString(5, treatment);
   
    p.executeUpdate();
    con.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }



  }

  

