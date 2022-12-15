import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ReadingURL {

    public static void main(String[] args) {



        try {
            URL exampleURL = new URL("https://example.org/");
            /*
            Sposób 1 - wykorzystanie metody openStream() - znaczne uproszczenie ponieważ pod tą metodą kryje się
            wywołanie operacji:
            - URLConnection urlConnection = exampleURL.getConnection();
            - urlConnection.connect();
            pokazane w sposobie nr 2


            */

//            BufferedReader urlReader = new BufferedReader(new InputStreamReader(exampleURL.openStream()));
//
//            String input = "";
//
//            while (input != null) {
//                input = urlReader.readLine();
//                System.out.println(input);
//            }
//            urlReader.close();

            //Sposób 2

            URLConnection urlConnection = exampleURL.openConnection(); // instancja tylko do konfiguracji połączenia
            //a nie do samego ustanowienia połączenia. Gdy używam klasy URLConnection to URL do którego się łącze nie musi
            //być web resource oraz nie musi używać scheme typu http. Ale istnieje wiele metod ukierunkowanych na zastosowanie
            //do http, takie jak w klasie HttpURLCOnnection. Klasa URLConnection jest bardziej ogólna - generyczna.
            urlConnection.setDoOutput(true); //jedno z możliwych ustawień
            //defaultowo mogę jedynie odczytywać zawartość strony. Jeśli chcę zapisywać do strony to muszę aktywować
            //takie ustawienie. Zasada jest taka że ustawienia mogę modyfikować do momentu wywołania metody connect()
            // po jej wywołaniu modyfikacja ustawień spowoduje wyjątek (lub error ?)
            urlConnection.connect(); // ustanowienie połączenie. Wprowadzanie zmian przestaje być możliwe. Make socket, creating handshake

            BufferedReader URLReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String input = "";
            while (input != null) {
                input = URLReader.readLine();
                System.out.println(input);
            }
            URLReader.close();
        } catch (MalformedURLException e) {
            System.out.println("Bad URL syntax: "+e.getMessage());
        } catch (IOException e) {
            System.out.println("Sth wrong with buffer: "+e.getMessage());
        }

    }

}
