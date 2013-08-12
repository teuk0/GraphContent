<%-- 
    Document   : keyword
    Created on : 20 févr. 2013, 16:45:01
    Author     : Teuk
--%>

<%@page import="com.smartcontent.graphstore.api.MineKeyWord"%>
<%@page import="com.smartcontent.graphstore.api.MineStorage"%>
<%@page import="com.smartcontent.testfront.StoreManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>KeyWord</h1>
            <%
        MineStorage ms = StoreManager.getStore();
        request.setCharacterEncoding("UTF-8");
        MineKeyWord thiskeyword = ms.getKeyWord(request.getParameter("keystem"));
        long t = System.currentTimeMillis();
        
  
        out.println("<h2>"+thiskeyword.getStem()+" "+thiskeyword.getNumberRelContent()+"</h2><br>");
        for(MineKeyWord mkw:thiskeyword.getRelatedKeyWords()){
            out.println("<a class=link href=keyword.jsp?keystem="+mkw.getStem()+">"+mkw.getStem().replace("_", " ") +"</a><br>");
        }
          
    %>
    <br>
        <a href="index.jsp">Back to menu</a>
    </body>
</html>
