import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;

public class WebCrawler {

    public static void crawl(String startingURL) {
        //set up ArrayLists to store traversed and pending URLs
        ArrayList<String> traversedURLs = new ArrayList<>();
        ArrayList<String> pendingURLs = new ArrayList<>();
        pendingURLs.add(startingURL);


        while (!pendingURLs.isEmpty() && traversedURLs.size() < 50) {
            //remove first pending URL
            String currentURL = pendingURLs.removeFirst();

            //create URL object from nextURLString
            try {
                System.out.println("Crawling URL: " + currentURL);
                pendingURLs.addAll(findSubURLs(currentURL));
                traversedURLs.add(currentURL);
            } catch (java.net.MalformedURLException | URISyntaxException ex) {
                System.out.println("Invalid URL" + ex.getMessage());
            } catch (IOException ioe) {
                System.out.println("IOException" + ioe.getMessage());
            }


        }
    }

    static ArrayList<String> findSubURLs(String url) throws IOException, URISyntaxException {
        //scan file for substrings starting w/ "http:" and ending w/ double quotes (")

        //subURLs will store all subURLs found in url
        ArrayList<String> subURLs = new ArrayList<>();

        //convert url to a URL object
        java.net.URL currentURL = new java.net.URI(url).toURL();

        //create scanner to get input from currentURL
        Scanner input = new Scanner(currentURL.openStream());

        int current = 0;
        while (input.hasNextLine()) {
            String line = input.nextLine();
            //get index of next instance of "http"
            current = line.indexOf("http", current);
            System.out.println("Current Index: " + current);
            while (current >= 0) {
                //get the index of the quotation marks following the URL
                int endIndex = line.indexOf("\"", current);
                //URL must be invalid if endIndex <= 0;
                if (endIndex > 0) {
                    //add the URL to subURLs
                    subURLs.add(line.substring(current, endIndex));
                    //set current to the index of the character after the quotation mark
                    current = endIndex + 1;
                }
                else current = -1;
            }

        }
        return subURLs;
    }
}

