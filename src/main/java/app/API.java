package app;

import entity.Book;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class API {
    private static final String MARC_NS = "http://www.loc.gov/MARC21/slim";
    private static final String BASE_LINK =
      "https://ca01.alma.exlibrisgroup.com/view/sru/01UTORONTO_INST?version=1.2&operation=searchRetrieve&recordSchema=marcxml&maximumRecords=50&query=";


    public static List<Book> getByTitle(String title, int numRecords) {
        String url = BASE_LINK + "alma.title=\"" + title + "\"";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return parse(response, numRecords);


            } else {
                System.out.println("Request failed with code " + response.code());
            }
        } catch (IOException e) {
            // A network problem (no connection, timeout, ...) — handle it gracefully.
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Book> getByTopic(String topic, int numRecords) {
        String url = BASE_LINK + "alma.subjects=\"" + topic + "\"";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return parse(response, numRecords);


            } else {
                System.out.println("Request failed with code " + response.code());
            }
        } catch (IOException e) {
            // A network problem (no connection, timeout, ...) — handle it gracefully.
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




    // --------------------------------------- HELPERS ---------------------------------------
    /**
     * Return the list of books from an API response of length at most numRecords
     * @param response the response from the API call
     * @param numRecords the maximum number of records to return
     * @return the List of Books from the response
     * @throws Exception If something went wrong
     */
    public static List<Book> parse(Response response, int numRecords) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true); // required!
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        factory.setXIncludeAware(false);
        factory.setExpandEntityReferences(false);

        Document doc = factory.newDocumentBuilder().parse(response.body().byteStream());

        // Only matches the inner MARC records, not the outer SRW <record> wrappers
        NodeList records = doc.getElementsByTagNameNS(MARC_NS, "record");

        List<Book> result = new ArrayList<>();

        for (int i = 0; i < records.getLength(); i++) {
            Element record = (Element) records.item(i);


            String mmsId = getControlField(record, "001");
            String author    = clean(getFirst(record, "100", "a"));
            String title     = clean(getFirst(record, "245", "a"));
            String callNumber = clean(getFirst(record, "AVA", "d"));

            String library = clean(getFirst(record, "AVA", "b"));
            String location = clean(getFirst(record, "AVA", "j"));
            String available = clean(getFirst(record, "AVA", "e"));

            if(library.equals("ROBARTS") && location.equals("STACKS")) {
                result.add(new Book(mmsId, title, author, callNumber));
            }

//            System.out.println("author: " + author);
//            System.out.println("title: " + title);
//            System.out.println("call number: " + callNumber);
//            System.out.println("mms ID: " + mmsId);
//            System.out.println("library: " + library);
//            System.out.println("location: " + location);
//            System.out.println("available: " + available);
//            System.out.println("-----------------------------------------------------------------");
        }
        return result.size() > numRecords ? result.subList(0, numRecords) : result;
    }

    // All <datafield> elements in a record with the given tag
    static List<Element> getDataFields(Element record, String tag) {
        List<Element> result = new ArrayList<>();
        NodeList fields = record.getElementsByTagNameNS(MARC_NS, "datafield");
        for (int i = 0; i < fields.getLength(); i++) {
            Element field = (Element) fields.item(i);
            if (tag.equals(field.getAttribute("tag"))) {
                result.add(field);
            }
        }
        return result;
    }

    // All subfield values with the given code inside one datafield
    static List<String> getSubfields(Element dataField, String code) {
        List<String> result = new ArrayList<>();
        NodeList subs = dataField.getElementsByTagNameNS(MARC_NS, "subfield");
        for (int i = 0; i < subs.getLength(); i++) {
            Element sub = (Element) subs.item(i);
            if (code.equals(sub.getAttribute("code"))) {
                result.add(sub.getTextContent().trim());
            }
        }
        return result;
    }

    // Convenience: first subfield value for tag + code, or null
    static String getFirst(Element record, String tag, String code) {
        for (Element field : getDataFields(record, tag)) {
            List<String> values = getSubfields(field, code);
            if (!values.isEmpty()) return values.get(0);
        }
        return null;
    }

    // controlfields (001, 005, 008) are a different element with no subfields
    static String getControlField(Element record, String tag) {
        NodeList fields = record.getElementsByTagNameNS(MARC_NS, "controlfield");
        for (int i = 0; i < fields.getLength(); i++) {
            Element f = (Element) fields.item(i);
            if (tag.equals(f.getAttribute("tag"))) return f.getTextContent().trim();
        }
        return null;
    }

    // Strip trailing MARC punctuation like " /", " :", ","
    static String clean(String s) {

        return s == null ? null : s.replaceAll("[\\s/:;,.]+$", "");
    }




}
