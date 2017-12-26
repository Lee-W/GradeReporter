import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import java.io.IOException;

public class NCKUAuthenticator implements GradePageAuthenticator {
    private final static String MAIN_URL = "http://140.116.165.71:8888/ncku/";
    private final static String LOGIN_URL = MAIN_URL + "qrys02.asp";
    private final static String LOGOUT_URL = MAIN_URL + "logouts.asp";

    private Response loggedInResponse;
    private String studentID;
    private String password;

    public NCKUAuthenticator(String studentID, String password) {
        setStudentID(studentID);
        setPassword(password);
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void login() {
        try {
            loggedInResponse = Jsoup.connect(LOGIN_URL)
                    .method(Connection.Method.GET)
                    .execute();

            Jsoup.connect(LOGIN_URL)
                    .data("ID", studentID)
                    .data("PWD", password)
                    .cookies(loggedInResponse.cookies())
                    .post();
        } catch (IOException e) {

        }
    }

    public void logout() {
        try {
            Jsoup.connect(LOGOUT_URL)
                    .data("ID", studentID)
                    .data("PWD", password)
                    .cookies(loggedInResponse.cookies())
                    .post();
        } catch (IOException e) {

        }

    }

    public Response getLoggedInResponse() {
         return loggedInResponse;
    }
}
