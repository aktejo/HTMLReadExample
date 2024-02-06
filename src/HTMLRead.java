import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/*

project: build a "link-puller"
in terms of screen parsing, how can we find links?
    through a href
how can we find where the link ends?
    through </a>?

*****
build gui:
    enter a website
    enter a search term
    display all links on website that mention that search term
*****

 */

public class HTMLRead {

    public static void main(String[] args) {
        HTMLRead html = new HTMLRead();
    }

    public HTMLRead() {
        try {
            URL url = new URL("https://www.milton.edu/athletics/");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream())
            );
            String line;
            String searchTerm = "tennis";
            while ((line = reader.readLine()) != null) {
                if (line.contains("href")) {
                    if (line.contains(searchTerm)) {
                        int index = line.indexOf("href");
                        String differentString = line.substring((index + 6));
                        int nextQuote = differentString.indexOf(">");
                        String link = differentString.substring(0, (nextQuote-1));
                        System.out.println("index " + index + ": " + link);
                    }
                }
            }

            reader.close();

        } catch(Exception e) {
            System.out.println(e);
        }
    }

}
