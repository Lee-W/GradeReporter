import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.logging.Logger;

public class NCKUGradeCrawler implements GradeCrawler {
    private final static Logger LOGGER = Logger.getLogger(NCKUGradeCrawler.class.getName());

    private final static String MAIN_URL = "http://140.116.165.71:8888/ncku/";
    private final static String LOGIN_URL = MAIN_URL + "qrys02.asp";
    private final static String LOGOUT_URL = MAIN_URL + "logouts.asp";
    private final static String INDEX_URL = MAIN_URL + "qrys05.asp";
    private final static String ENCODING = "big5";

    private String studentID;
    private String password;

    public NCKUGradeCrawler(String studentID, String password) {
        setStudentID(studentID);
        setPassword(password);
    }

    @Override
    public Document crawlGradePages() {
        try {
            Response loginForm = Jsoup.connect(LOGIN_URL)
                    .method(Connection.Method.GET)
                    .execute();

            Document document = Jsoup.connect(INDEX_URL)
                    .data("ID", studentID)
                    .data("PWD", password)
                    .cookies(loginForm.cookies())
                    .post();

            return document;
        } catch (IOException e) {
            LOGGER.warning("Cannot crawler NCKU grade page");
        }

        return null;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
