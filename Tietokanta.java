import java.sql.*;
import java.util.*;

public class Tietokanta {
    public static void main(String[] args) throws SQLException{
        Connection db = DriverManager.getConnection("jdbc:sqlite:kurssit.db");
        Statement s = db.createStatement();

        Scanner userInput = new Scanner(System.in);
        System.out.println("Valitse toiminto: ");
        String syote = userInput.nextLine();
        System.out.println("Anna vuosi: ");
        int vuosi = Integer.parseInt(userInput.nextLine());
        System.out.println("Anna opintopistemäärä: ");

        try {
            ResultSet r = s.executeQuery("SELECT * FROM kurssit");
        } catch (Exception e) {
            System.out.println("Kysely ei onnistunut");
        }


        private void haeVuodenOpintopisteet() {
            System.out.println("Opintopisteiden määrä: " + 1);
        }
    }

}