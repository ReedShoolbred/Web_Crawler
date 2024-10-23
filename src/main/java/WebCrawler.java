import java.io.IOException;
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
            String nextURLString = pendingURLs.removeFirst();

            //create URL object from nextURLString
            try {
                java.net.URL currentURL = new java.net.URI(nextURLString).toURL();
                System.out.println("Crawling URL: " + nextURLString);
                pendingURLs.addAll(findSubURLs(currentURL));
                traversedURLs.add(nextURLString);
            } catch (java.net.MalformedURLException | java.net.URISyntaxException ex) {
                System.out.println("Invalid URL" + ex.getMessage());
            } catch (IOException ioe) {
                System.out.println("IOException" + ioe.getMessage());
            }


        }
    }

    private static ArrayList<String> findSubURLs(java.net.URL url) throws IOException {
        //scan file for substrings starting w/ "http:" and ending w/ double quotes (")

        //subURLs will store all subURLs found in url
        ArrayList<String> subURLs = new ArrayList<>();

        Scanner input = new Scanner(url.openStream());
        int current = 0;
        while (input.hasNextLine()) {
            String line = input.nextLine();
            //get index of next instance of "http"
            current = line.indexOf("http", current);
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

