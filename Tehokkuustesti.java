import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class Tehokkuustesti {
  
  public static void main(String[] args) {
    Connection connection = null;
    try {
      connection = DriverManager.getConnection("jdbc:sqlite:tehokkuustesti.db");
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);

      Scanner input = new Scanner(System.in);
      while (true) {
        try {
          System.out.println("Toiminto: ");
          int toiminto = Integer.parseInt(input.nextLine());

          if (toiminto == 10) {
            System.exit(0);
            input.close();
          } else if (toiminto == 1) {
            luoTietokanta(statement);
          } else if (toiminto == 2) {
            lisaaMiljoonaRivia(connection);
          } else if (toiminto == 3) {
            suoritaTuhatKyselya(connection);
          } else if (toiminto == 4) {
            lisaaMiljoonaRiviaIndeksinKanssa(connection);
          } else if (toiminto == 5) {
            suoritaIndeksointiJaTuhatKyselya(connection);
          } else if (toiminto == 9) {
            poistaTietokanta(statement);
          }

        } catch (NumberFormatException e) {
          System.out.println(e.getMessage());
        }
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }
  


  private static void suoritaIndeksointiJaTuhatKyselya(Connection connection) {
    String kesto = "Indeksointi ja tuhat kyselyä kestää ";
    long loppu = 0;
    long alku = System.nanoTime();
    
  
    try {
      PreparedStatement index = connection.prepareStatement(
      "CREATE INDEX idx_vuosi ON Elokuvat (vuosi)"
      );
      index.executeUpdate();
      for (int i=0; i<1000; i++) {
        PreparedStatement p = connection.prepareStatement(
          "SELECT COUNT(*) määrä FROM Elokuvat WHERE vuosi=?;"
        );
        int randomYear = randomYear(1900, 2000);
        p.setInt(1, randomYear);
        p.executeQuery();
        // System.out.println("Vuonna " + randomYear + " julkaistujen elokuvien määrä on " + r.getString("määrä"));
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    loppu = System.nanoTime();

    System.out.println(kesto + (loppu-alku)/1000000 + "ms");
  }

  private static void lisaaMiljoonaRiviaIndeksinKanssa(Connection connection) {
    String kesto = "Miljoonan rivin lisääminen indeksin kanssa kestää ";
  long loppu = 0;
  long alku = System.nanoTime();

  try {
    PreparedStatement start = connection.prepareStatement("BEGIN");
    PreparedStatement end = connection.prepareStatement("END;");
    PreparedStatement index = connection.prepareStatement(
    "CREATE INDEX idx_vuosi ON Elokuvat (vuosi)"
    );
    start.executeUpdate();
    index.executeUpdate();
    for (int i=0; i<1000000; i++) {
      PreparedStatement p = connection.prepareStatement(
        "INSERT INTO Elokuvat (nimi, vuosi) VALUES(?, ?)"
      );
      
        String randomString = randomString();
        int randomYear = randomYear(1900,2000);
        p.setString(1, randomString);
        p.setInt(2, randomYear);
        p.executeUpdate();
    }
    end.executeUpdate();
    loppu = System.nanoTime();
    
  } catch (SQLException e) {
    System.out.println(e.getMessage());
  }

  System.out.println(kesto + (loppu-alku)/1000000 + "ms");
  }

  private static void poistaTietokanta(Statement statement) {
    try {
      statement.execute("DROP TABLE Elokuvat;");
      System.out.println("Elokuvat-taulu poistettu!");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  private static void suoritaTuhatKyselya(Connection connection) {
    String kesto = "Tuhat kyselyä kestää ";
    long loppu = 0;
    long alku = System.nanoTime();
  
    try {
      for (int i=0; i<1000; i++) {
        PreparedStatement p = connection.prepareStatement(
          "SELECT COUNT(*) määrä FROM Elokuvat WHERE vuosi=?;"
        );
        int randomYear = randomYear(1900, 2000);
        p.setInt(1, randomYear);
        p.executeQuery();
        // System.out.println("Vuonna " + randomYear + " julkaistujen elokuvien määrä on " + r.getString("määrä"));
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    loppu = System.nanoTime();

    System.out.println(kesto + (loppu-alku)/1000000 + "ms");
  }

private static void lisaaMiljoonaRivia(Connection connection) {
  String kesto = "Miljoonan rivin lisääminen kestää ";
  long alku = 0;
  long loppu = 0;
  
  try {
    PreparedStatement start = connection.prepareStatement("BEGIN");
    PreparedStatement end = connection.prepareStatement("END;");
    alku = System.nanoTime();
    start.executeUpdate();
    for (int i=0; i<1000000; i++) {
      PreparedStatement p = connection.prepareStatement(
        "INSERT INTO Elokuvat (nimi, vuosi) VALUES(?, ?)"
      );
      
        String randomString = randomString();
        int randomYear = randomYear(1900,2000);
        p.setString(1, randomString);
        p.setInt(2, randomYear);
        p.executeUpdate();
    }
    end.executeUpdate();
    loppu = System.nanoTime();
    
  } catch (SQLException e) {
    System.out.println(e.getMessage());
  }

  System.out.println(kesto + (loppu-alku)/1000000 + "ms");
}

  private static int randomYear(int min, int max) {
    return min + (int)(Math.random() * ((max-min)+1));
  }

  private static String randomString() {
    int leftLimit = 97;
    int rightLimit = 122;
    int targetStringLength = 15;
    Random random = new Random();

    String generatedString = random.ints(leftLimit, rightLimit + 1)
      .limit(targetStringLength)
      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
      .toString();
    return generatedString;
  }

  private static void luoTietokanta(Statement statement) {
  try {
    statement.execute("CREATE TABLE Elokuvat (id INTEGER PRIMARY KEY, nimi TEXT NOT NULL, vuosi INTEGER);");
    System.out.println("Elokuvat-taulu luotu!");
  } catch (SQLException e) {
    System.out.println(e.getMessage());
  }
  }
}
