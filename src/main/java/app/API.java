package app;

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

    public static void parse(Response response) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true); // required!
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        factory.setXIncludeAware(false);
        factory.setExpandEntityReferences(false);

        Document doc = factory.newDocumentBuilder().parse(response.body().byteStream());

        // Only matches the inner MARC records, not the outer SRW <record> wrappers
        NodeList records = doc.getElementsByTagNameNS(MARC_NS, "record");

        for (int i = 0; i < records.getLength(); i++) {
            Element record = (Element) records.item(i);

            String isbn      = getFirst(record, "020", "a");
            String author    = clean(getFirst(record, "100", "a"));
            String title     = clean(getFirst(record, "245", "a"));
            String publisher = clean(getFirst(record, "264", "b"));
            String year      = clean(getFirst(record, "264", "c"));
            String controlId = getControlField(record, "001");

            System.out.println(title + " by " + author + " (" + publisher + ", " + year + ") ISBN " + isbn);

            // For repeatable fields, loop over all of them
            for (Element f : getDataFields(record, "035")) {
                System.out.println("  035: " + getSubfields(f, "a"));
            }
        }
    }

    public static void main(String[] args){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://ca01.alma.exlibrisgroup.com/view/sru/01UTORONTO_INST?version=1.2&operation=searchRetrieve&recordSchema=marcxml&query=alma.title=%22catcher%20in%20the%20rye%22%20and%20alma.creator=salinger&maximumRecords=10")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                parse(response);
            } else {
                System.out.println("Request failed with code " + response.code());
            }
        } catch (IOException e) {
            // A network problem (no connection, timeout, ...) — handle it gracefully.
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
    }




}
