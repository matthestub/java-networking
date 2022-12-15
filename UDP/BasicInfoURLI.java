import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class BasicInfoURLI {

    public static void main(String[] args) {

        try {
            //absolut URI path na podstawie którego można stworzyć URL
            URI uri = new URI("http://username:password@myserwer.com:5000/catalogues/phones?os=android#samsung");

            System.out.println("Scheme: "+uri.getScheme());
            System.out.println("Scheme specific part: "+uri.getSchemeSpecificPart());
            System.out.println("Authority: "+uri.getAuthority());
            System.out.println("User info: "+uri.getUserInfo());
            System.out.println("Host: "+uri.getHost());
            System.out.println("Port: "+uri.getPort());
            System.out.println("Path: "+uri.getPath());
            System.out.println("Query: "+uri.getQuery());
            System.out.println("Fragment: "+uri.getFragment());

            URL url = uri.toURL(); //syntax musi się idealnie zgadzać aby możliwa była konwersja
            System.out.println(url);


            URI baseURI = new URI("http://username:password@mynewserwer.com:5000"); //zmiana w bazowej części będzie widoczna we wszystkich URI które
            //stworzone są z wykorzystaniem tej instancji. Jedna zmiana - wiele efektów,

            URI uri1 = new URI("/devices/tablets?model=v6#oppo");
            URI uri2 = new URI("/tvs/uhd4k");

            URI resolvedURI1 = baseURI.resolve(uri1);
            URI resolvedURI2 = baseURI.resolve(uri2);

            System.out.println(resolvedURI1);
            System.out.println(resolvedURI2);

            URI relativeURI1 = baseURI.relativize(resolvedURI1);
            System.out.println(relativeURI1);

            System.out.println("**************");

            URI poorURI = new URI("www.hallo.com"); //czegoś takiego nie da się konwertować na URL
            //wyniki będą wyświetlone mimo że większość pól jest null
            System.out.println("Scheme: "+poorURI.getScheme());
            System.out.println("Scheme specific part: "+poorURI.getSchemeSpecificPart());
            System.out.println("Authority: "+poorURI.getAuthority());
            System.out.println("User info: "+poorURI.getUserInfo());
            System.out.println("Host: "+poorURI.getHost());
            System.out.println("Port: "+poorURI.getPort());
            System.out.println("Path: "+poorURI.getPath());
            System.out.println("Query: "+poorURI.getQuery());
            System.out.println("Fragment: "+poorURI.getFragment());

        } catch (URISyntaxException e) {
            System.out.println("URI exc: "+e.getMessage());
        } catch (MalformedURLException e) {
            System.out.println("Bad URL syntax: "+e.getMessage());
        }
    }

}
