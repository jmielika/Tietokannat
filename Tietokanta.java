import java.sql.*;
import java.text.DecimalFormat;
import java.util.*;

public class Tietokanta {

  public static void main(String[] args) {

    Connection connection = null;
    try {
      connection = DriverManager.getConnection("jdbc:sqlite:kurssit.db");
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);

      Scanner input = new Scanner(System.in);
      while(true) {
        try {
        System.out.println("Toiminto: ");
        int toiminto = Integer.parseInt(input.nextLine());

        if (toiminto == 5) {
          System.exit(0);
        } else if (toiminto == 1) {
          haeVuodenOpintopisteet(connection);
        } else if (toiminto == 2) {
          haeOpiskelijanSuoritukset(connection);
        } else if (toiminto == 3) {
          laskeKurssinKeskiarvo(connection);
        } else if (toiminto == 4) {
          listaaTopEnitenPisteitaOpettajat(connection);
        } else {
          System.out.println("toimintoa ei ole! yritä uudelleen");
        }

        } catch (NumberFormatException e) {
          System.out.println("toimintoa ei ole! yritä uudelleen");
        }
      
    }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    
    }
     
    private static void listaaTopEnitenPisteitaOpettajat(Connection c) {
      Scanner i = new Scanner(System.in);
      System.out.println("Anna opettajien määrä: ");
      try {
        int top = Integer.parseInt(i.nextLine());

        PreparedStatement p = c.prepareStatement("SELECT O.nimi nimi, SUM(K.laajuus) opMaara FROM Opettajat O, Kurssit K, Suoritukset S WHERE K.id=S.kurssi_id AND O.id=K.opettaja_id GROUP BY O.id ORDER BY opMaara DESC LIMIT ?");
        p.setString(1,""+top);
  
        ResultSet r = p.executeQuery();
        if (r.next()) {
          System.out.format("%8s %15s", "opettaja", "op");
          System.out.println();
          while (r.next()){
            System.out.format("%17s %6d", r.getString("nimi"), r.getInt("opMaara"));
            System.out.println();
          }
        }
      } catch (NumberFormatException | SQLException e) {
        System.out.println("Opettajien määrä pitää antaa numeroina. Valitse uusi");
      }
      
    }

    private static void laskeKurssinKeskiarvo(Connection c) throws SQLException {
      Scanner i = new Scanner(System.in);
      System.out.println("Anna kurssin nimi: ");
      String nimi = i.nextLine().toLowerCase();

      PreparedStatement p = c.prepareStatement("SELECT AVG(S.arvosana) keskiarvo, COUNT(*) määrä FROM Kurssit K, Suoritukset S WHERE K.id=S.kurssi_id AND LOWER(K.nimi)=?");
      p.setString(1,nimi);
  
      ResultSet r = p.executeQuery();
      
      if (r.getInt("määrä") > 0) {
        DecimalFormat df = new DecimalFormat("#.##");

        System.out.println("Keskiarvo: " + df.format(r.getDouble("keskiarvo")));
      } else {
        System.out.println("Kurssia ei löytynyt");
      }

    }

    private static void haeOpiskelijanSuoritukset(Connection c) throws SQLException {
      Scanner i = new Scanner(System.in);
      System.out.println("Anna opiskelijan nimi: ");
      String nimi = i.nextLine();
  
      PreparedStatement p = c.prepareStatement("SELECT K.nimi kurssi, K.laajuus op, S.paivays päiväys, S.arvosana arvosana FROM Kurssit K, Suoritukset S, Opiskelijat O WHERE K.id=S.kurssi_id AND O.id=S.opiskelija_id AND O.nimi=? ORDER BY päiväys");
      p.setString(1,nimi);
  
      ResultSet r = p.executeQuery();
      
      if (r.next()) {
        System.out.format("%5s %3s %7s %12s", "kurssi", "op", "päiväys", "arvosana");
        System.out.println();
        while (r.next()){
        System.out.format("%7s %2d %10s %2d", r.getString("kurssi"), r.getInt("op"), r.getString("päiväys"), r.getInt("arvosana"));
        System.out.println();
        }
      } else {
        System.out.println("Opiskelijaa ei löytynyt");
      }
    }

    private static void haeVuodenOpintopisteet(Connection c) throws SQLException {
      Scanner i = new Scanner(System.in);
      System.out.println("Anna vuosi: ");
      String vuosi = i.nextLine();
      vuosi += "-__-__";
  
    PreparedStatement p = c.prepareStatement("SELECT SUM(K.laajuus) opintopisteet FROM Kurssit K, Suoritukset S WHERE K.id=S.kurssi_id AND S.paivays LIKE ?");
    p.setString(1,vuosi);
  
    ResultSet r = p.executeQuery();
    if (r.next()) {
        System.out.println("Opintopisteiden määrä: "+r.getInt("opintopisteet"));
    } else {
        System.out.println("Ei suorituksia vuonna " + vuosi.substring(0,3));
    }
  }
}