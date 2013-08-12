<%-- 
    Document   : content
    Created on : 18 oct. 2012, 21:44:00
    Author     : Teuk
--%>

<%@page import="com.smartcontent.graphstore.api.MineKeyWord"%>
<%@page import="com.smartcontent.graphstore.api.MineContent"%>
<%@page import="com.smartcontent.testfront.StoreManager"%>
<%@page import="com.smartcontent.graphstore.api.MineStorage"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <%
        MineStorage ms = StoreManager.getStore();
        request.setCharacterEncoding("UTF-8");
        MineContent thisContent = ms.getContent(request.getParameter("contentname"));
        long t = System.currentTimeMillis();
    %>
    <body>
        <h2>Content</h2>
        <p>
        <%
            out.println(thisContent.getName()+":"+thisContent.getLang()+"<br>");
            out.println(thisContent.getContent());
            out.println(System.currentTimeMillis()-t);
        %>
        
        </p>
        <h2>Closest Content</h2>
        <p>
        <%
        
            for(MineContent cmc:thisContent.getClosestContent()){
                out.println("<a class=link href=content.jsp?contentname="+cmc.getName().replace(" ", "_") +">"+cmc.getName().replace("_", " ")+"</a><br>");
            }
            out.println(System.currentTimeMillis()-t);
        %>
        </p>
        
                <h2>Infered Closest Content</h2>
        <p>
        <%
        
            for(MineContent cmc:thisContent.getClosestInferedContent()){
                out.println("<a class=link href=content.jsp?contentname="+cmc.getName().replace(" ", "_") +">"+cmc.getName().replace("_", " ")+"</a><br>");
            }
            out.println(System.currentTimeMillis()-t);
        %>
        </p>
        <h2>Related KeyWords</h2>
        <p>
            <%
            for(MineKeyWord mkw:thisContent.getKeyWords()){
                out.println("<a class=link href=keyword.jsp?keystem="+mkw.getStem()+">"+mkw.getStem().replace("_", " ") +"</a><br>");
                
            }
            out.println(System.currentTimeMillis()-t);
            %>
        </p>
        <h2>Infered KeyWords</h2>
        <p>
          <%
            for(MineKeyWord mkw:thisContent.getInferedKeyWords()){
                out.println("<a class=link href=keyword.jsp?keystem="+mkw.getStem()+">"+mkw.getStem().replace("_", " ") +"</a><br>");
                
            }
            out.println(System.currentTimeMillis()-t);
            %>  
        </p>
        <a href="index.jsp">Back to menu</a>
        
    </body>
</html>
