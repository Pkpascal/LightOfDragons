module project.lightofdragons {
  requires javafx.controls;
  requires javafx.fxml;
  requires junit;
  requires org.testng;


  opens project.lightofdragons to javafx.fxml;
  exports project.lightofdragons;
}