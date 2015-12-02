<!--
-->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Danke</title><!-- Merci / -->
</head>
<body>
<h1 align="center" style="font-family: Papyrus,serif"><br/>
    <br/>

    Danke
    <% String pass = request.getParameter("password");
        if (pass!=null && pass.length()>0) { %>
    f&uuml;r ihr Passwort <br/><br/>
    <% if(pass.length()==1) {
           %>Es ist eine Buchstabe lang.<br/>
    Es kann also in ~60 Versuchen gefunden werden.
    <%
       } else if(pass.length()==2) {%>Es ist zwei Buchstaben lang.<br/>
        Es kann also in ~3600 Versuchen gefunden werden.<%}
           else if (pass.length()==3) {%>Es ist drei Buchstaben lang.<br/>
    Es kann also in ~216000 Versuchen gefunden werden.
    <%}
       else {
        char c1= '*', c2='*';
        int lastS = 0;
        for(int i=2, l=pass.length(); i<l; i++) {
            char c= pass.charAt(i);
            if(Character.isLetter(c)) {
                if(c1=='*') {
                    c1 = c;
                    lastS = i;
                }
                else if (c2=='*' && lastS+1<i) c2=c;
            }
        }
        if(c1!='*' && c2!='*') {
    %>Es hat die Buchstaben <%=c1%> und <%=c2%>.<%
                } else if(c1!='*') {
    %>Es hat die Buchstabe <%=c1%>.<%
                } else {
    %>Es ist ziemlich wild und ist <%=pass.length()%> Buchstaben lang.<%
                }
            }
    }%>
</h1>
</body>
</html>