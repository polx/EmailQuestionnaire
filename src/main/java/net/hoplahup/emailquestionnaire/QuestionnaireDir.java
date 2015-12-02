package net.hoplahup.emailquestionnaire;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.io.FileUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class QuestionnaireDir {

    public QuestionnaireDir(File file) {
        this.dir = file;
    }

    File dir;
    Document fieldNamesDoc = null;
    DateFormat df;
    XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());

    public static String FIELDS_FILENAME = "fieldNames.xml";

    public Document parseFields() throws IOException, JDOMException{
        File file = new File(dir, FIELDS_FILENAME);
        df = new SimpleDateFormat("yyyy-MM-dd_HH-mm__ss-SSS");
        if(file.isFile()) {
            fieldNamesDoc = new SAXBuilder().build(file);
            return fieldNamesDoc;
        } else {
            Element root = new Element("fieldNames");
            fieldNamesDoc = new Document(root);
            return fieldNamesDoc;
        }
    }

    public void writeFields() throws IOException {
        FileOutputStream fOut = new FileOutputStream(new File(dir, FIELDS_FILENAME));
        outputter.output(fieldNamesDoc, fOut);
        fOut.flush(); fOut.close();
    }

    public void updateFieldNames(Enumeration names) {
        while(names.hasMoreElements()) {
            String name = (String) names.nextElement();
            Element found = null;
            for(Element child : fieldNamesDoc.getRootElement().getChildren()) {
                if(name.equals(child.getTextTrim())) {
                    found = child; break;
                }
            }
            if(found==null) {
                Element n = new Element("name"); n.addContent(name);
                fieldNamesDoc.getRootElement().addContent(n);
            }
        }
    }

    public void receiveData(Map parameterMap, String sessionId, String userAgent) throws IOException {
        Element root = new Element("data");
        for(Object entry: parameterMap.entrySet()) {
            Map.Entry m = (Map.Entry) entry;
            String name = (String) m.getKey();
            String[] values = (String[]) m.getValue();
            for(String value: values) {
                Element c = new Element(name);
                c.setText(value);
                root.addContent(c);
            }
        }

        File file= new File(dir, df.format(new Date()) + ".xml");
        root.setAttribute("session", sessionId);
        root.setAttribute("file", file.getName());
        root.setAttribute("userAgent", userAgent);
        System.out.println("Outptting to " + file + ".");
        FileOutputStream fOut = new FileOutputStream(file);
        outputter.output(root, fOut);
        fOut.flush(); fOut.close();
    }

    public String getThankYouPath() throws IOException, JDOMException {
        if(fieldNamesDoc==null) parseFields();
        String thankYouPath = fieldNamesDoc.getRootElement().getAttributeValue("thankYou");
        if(thankYouPath==null) thankYouPath = "/thankyou.jsp";
        return thankYouPath;
    }

    public String getQuestionnaireId() {
        return dir.getName();
    }


    private List<String> getDocNames(boolean sort) {
        String[] docs = dir.list();
        List<String> docNames = new ArrayList<String>((List<String>) Arrays.asList(docs));
        docNames.remove("fieldNames.xml");
        docNames.remove("fieldNames.xml~");
        if(sort) Collections.sort(docNames);
        return docNames;
    }

    public int countAllDocuments() {
        return getDocNames(false).size();
    }

    public Iterator<Document> listAllDocuments() {
        final Iterator<String> docNamesIt = getDocNames(true).iterator();
        return new Iterator<Document>() {
            public boolean hasNext() {
                return docNamesIt.hasNext();
            }

            public Document next() {
                String name = docNamesIt.next();
                try {
                    Document doc = new SAXBuilder().build(new File(dir, name));
                    if(doc.getRootElement().getAttribute("file")==null)
                            doc.getRootElement().setAttribute("file", name);
                    return doc;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            public void remove() {throw new IllegalStateException();}
        };
    }

    public String trashFile(String toDelete, QuestionnaireWebApp webApp) throws IOException {
        File file = new File(dir, toDelete);
        if(file.isFile()) {
            File trashFile = webApp.getTrashFile(file);
            FileUtils.moveFile(file, trashFile);
            System.out.println("Moving file " + file + " to " + trashFile);
            return "File " + file + " moved to trash.";
        } else
            return "No file " + file + ".";
    }
}
