import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.logging.Logger;

public class NCKUGradeCrawler implements GradeCrawler {
    private final static Logger LOGGER = Logger.getLogger(NCKUGradeCrawler.class.getName());

    private final static String MAIN_URL = "http://140.116.165.71:8888/ncku/";
    private final static String INDEX_URL = MAIN_URL + "qrys05.asp";

    private Response loggedInResponse;

    public NCKUGradeCrawler(Response loggedInResponse) {
        setLoggedInResponse(loggedInResponse);
    }

    public void setLoggedInResponse(Response loggedInResponse) {
        this.loggedInResponse = loggedInResponse;
    }

    @Override
    public Document crawlGradePages() {
        try {
            Document document = Jsoup.connect(INDEX_URL)
                    .cookies(loggedInResponse.cookies())
                    .post();
            return document;
        } catch (IOException e) {
            LOGGER.warning("Cannot crawler NCKU grade page");
        }

        return null;
    }
}
