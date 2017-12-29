import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class NCKUGradeParser implements GradeParser {
    private final static String MAIN_URL = "http://140.116.165.71:8888/ncku/";
    private final static String INDEX_URL = MAIN_URL + "qrys05.asp";

    private Response loggedInResponse;

    private ArrayList<String> availableSemesters = new ArrayList<String>();
    private JSONObject rawData;
    private JSONObject overallSummary = new JSONObject();
    private JSONObject allSemesters = new JSONObject();

    public NCKUGradeParser(Response loggedInResponse) {
        setLoggedInResponse(loggedInResponse);
    }

    public void setLoggedInResponse(Response loggedInResponse) {
        this.loggedInResponse = loggedInResponse;
    }

    @Override
    public JSONObject parse(Document document) {
        parseAvailableSemesters(document);
        parseOverallSummary(document);
        parseAllSemesters();

        rawData = new JSONObject();
        rawData.put("courses", allSemesters);
        rawData.put("summary", overallSummary);
        return rawData;
    }

    @Override
    public JSONObject getSemesterData() {
        return allSemesters;
    }

    @Override
    public JSONObject getRawData() {
        return rawData;
    }

    private void parseAvailableSemesters(Document document) {
        Elements elements = document.select("input[value]");
        for (Element e: elements) {
            String semester = e.attr("value");
            availableSemesters.add(semester);
        }
    }

    private void parseOverallSummary(Document document) {
        Element tableElement = document.select("table").last();

        ArrayList<String> title = new ArrayList<>();
        ArrayList<String> content = new ArrayList<>();

        Element titleTrElement = tableElement.select("tr").first().nextElementSibling();
        Elements titleTdElements = titleTrElement.select("b");
        for (Element e: titleTdElements) {
            title.add(e.text().trim());
        }

        Element contentTrElement = tableElement.select("tr").last();
        Elements contentTdElements = contentTrElement.select("td div");
        for (Element e: contentTdElements) {
            content.add(e.text().trim());
        }
        for (int i=2; i < content.size()-1; i++) {
            overallSummary.put(title.get(i), content.get(i));
        }
    }

    private void parseAllSemesters() {
        Document pageDocument;
        for (String semester: availableSemesters) {
            String encodedSemester = fixSemesterEncoding(semester);
            pageDocument = crawlSemesterPage(encodedSemester);
            allSemesters.put(semester, parseSemester(pageDocument));
        }
    }

    private String fixSemesterEncoding(String semester) {
        String semesterWithEncode = semester.substring(0, semester.length()-1);
        semesterWithEncode += "¤";
        semesterWithEncode += semester.contains("上") ? "W" : "U";
        return semesterWithEncode;
    }

    private Document crawlSemesterPage(String semester) {
        try {
            Document document = Jsoup.connect(INDEX_URL)
                    .data("submit1", semester)
                    .cookies(loggedInResponse.cookies())
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .postDataCharset("cp1252")
                    .post();
            return document;
        } catch (IOException e) {

        }
        return null;
    }

    private JSONArray parseSemester(Document document) {
        Elements tableElements = document.select("table");
        Element tableElement = tableElements.get(3);
        Elements trElements = tableElement.select("tr");
        Elements tdElements;

        ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();
        for (Element trElement: trElements.subList(1, trElements.size()-2)) {
            tdElements = trElement.select("td");

            ArrayList<String> row = new ArrayList<String>();

            // Parse from the second element to third last element
            for (Element tdElement: tdElements) {
                String text = tdElement.text().trim();
                if (!text.equals("　")) {
                    row.add(text);
                }
            }
            rows.add(row);

        }
        return tableToJson(rows);
    }

    private JSONArray tableToJson(ArrayList<ArrayList<String>> table) {
        JSONArray jsonArray = new JSONArray();

        ArrayList<String> title = table.get(0);
        for (int i=1; i < table.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            for (int j=0; j < table.get(i).size(); j++) {
                jsonObject.put(title.get(j), table.get(i).get(j));
            }
            jsonArray.put(jsonObject);
        }
        return jsonArray;

    }
}
