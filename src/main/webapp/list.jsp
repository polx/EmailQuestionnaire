<%@ page import="net.hoplahup.emailquestionnaire.QuestionnaireWebApp" %>
<%@ page import="net.hoplahup.emailquestionnaire.QuestionnaireDir" %>
<%@ page import="org.jdom2.Element" %>
<%@ page import="org.jdom2.Document" %>
<%@ page import="java.util.*" %>
<%--
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    QuestionnaireWebApp webApp = (QuestionnaireWebApp) config.getServletContext().getAttribute(QuestionnaireWebApp.class.getName());
    String name= request.getParameter("quest");
    QuestionnaireDir quest = webApp.getQuestionnaireDir(name);
    Map<String,Map<String,Integer>> values = new HashMap<String,Map<String,Integer>>();
    Map<String,String> excludes= new HashMap<String,String>();
    if(request.getParameter("exclude")!=null) {
        try {
            List<String> s = new LinkedList<String>();
            for(String t : request.getParameterValues("exclude")) {
                String[] x = t.split(":|=");
                excludes.put(x[0],x[1]);
            }
            %><!-- excludes <%=excludes%> --><%
        } catch (Exception ex) { ex.printStackTrace(); }
    }
%>
<html>
<head>
    <title>Questionnaire Results <%=name%></title>
    <style type="text/css">
        table
        {
            /* font-family: "Lucida Sans Unicode", "Lucida Grande", Sans-Serif;
            font-size: 12px;*/
            margin: 45px;
            /* width: 480px; */
            text-align: left;
            border-collapse: collapse;
            border: 1px solid #aaa;
        }
        table th
        {
            padding: 12px 17px 12px 17px;
            font-weight: normal;
            font-size: 14px;
            border-bottom: 1px dashed #aaa;
        }
        table td
        {
            padding: 7px 17px 7px 17px;
        }
        table tbody tr:hover td
        {
            background: #d0dafd;
        }
    </style>
</head>
<body>
<h1>Questionnaire Results <%=name%></h1>
<!-- Deletion? <%
    String toDelete = request.getParameter("delete");%><%=toDelete%><%
    %><%=toDelete%> --><%
    if(toDelete!=null) {
        toDelete = toDelete.trim();
        if(toDelete.length()>0) {
            %><p><%
            String result = quest.trashFile(toDelete, webApp);
            %><%=result%></p><%
        }
    }
%>
<p><%=quest.countAllDocuments()%> answers.</p>

<table border="1" cellpadding="5">
    <thead><tr><th>file</th><th>session</th>
        <% Element fields = quest.parseFields().getRootElement();
            List<String> fieldNames = new LinkedList<String>();
            for(Element field: fields.getChildren()) {
                fieldNames.add(field.getTextTrim());
                %><th><%=field.getTextTrim()%></th><%
            }
    %>
    <th>browser</th></tr></thead>
    <tbody>
    <% Iterator<Document> docs = quest.listAllDocuments();
        throughDocs:while(docs.hasNext()) {
            Element root = docs.next().getRootElement();
            // test if excluded
            for(String key: excludes.keySet()) {
                Element elt = root.getChild(key);
                %><%
                if(elt!=null && excludes.get(key).equals(elt.getTextTrim())) {
                    %><!-- excluded --><%
                    continue throughDocs;
                }
            }
            %><tr>
                <td><%=root.getAttributeValue("file")%>
                    (<a href="list.jsp?quest=<%=quest.getQuestionnaireId()%>&delete=<%=root.getAttributeValue("file")%>">x</a>)
                </td><td style="font-size:x-small"><%=root.getAttributeValue("session")%></td>
            <% for(String fieldName: fieldNames) {
                Element valE = root.getChild(fieldName);
                String value = null;
                if(valE!=null) value = valE.getTextTrim();
                if(value==null) value="";

                Map<String,Integer> valuesInThisColumn = values.get(fieldName);
                if(valuesInThisColumn==null) {
                    valuesInThisColumn = new TreeMap<String,Integer>();
                    values.put(fieldName, valuesInThisColumn);
                }
                if(value.length()>0 && !value.contains(" ")) {
                    Integer count = valuesInThisColumn.get(value);
                    if(count==null) count = 0;
                    valuesInThisColumn.put(value, count+1);
                }

                %><td><%=value%></td><%
            }
            %><td><%=root.getAttributeValue("userAgent")%></td></tr><%
        }
        %><tr style="background-color: aliceblue"><td></td><td></td><%
        for(String fieldName: fieldNames) {%><td><h3><%=fieldName%></h3><table><%
            if(values.containsKey(fieldName))
                for(Map.Entry<String, Integer> s: values.get(fieldName).entrySet()) {%><tr><td><%=s.getKey()%></td><td><%=s.getValue()%></td></tr>
<%}
        %></table></td><% }
    %></tr><%
    %>
    </tbody>
</table>
<hr/>
</body>
</html>
