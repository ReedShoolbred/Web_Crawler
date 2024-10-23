import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class WebCrawlerTest {

    @Test
    public void getSubURLsTest() throws IOException, URISyntaxException {
        String str0 = "https://www.google.com\"";
        ArrayList<String> URLList0 = new ArrayList<>(WebCrawler.findSubURLs(str0));
        assertEquals("https://www.google.com\"", URLList0.getFirst());
    }
}
