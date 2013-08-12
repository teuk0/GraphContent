<%@page import="com.smartcontent.graphstore.api.MineContent"%>
<%@page import="com.smartcontent.graphstore.api.MineKeyWord"%>
<%@page import="com.smartcontent.testfront.StoreManager"%>
<%@page import="com.smartcontent.graphstore.api.MineStorage"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Smart Content</title>
    </head>
    <body>
        <h1>Smart Content</h1>
        <h2>Data</h2>
        <p>
            <%
                MineStorage ms = StoreManager.getStore();
                //ms.createContentType(StoreManager.typeName);
                int i=0;
                //MineKeyWord m1=ms.getKeyWord("energ");
                //MineKeyWord m2=ms.getKeyWord("franc");
                //m2.getRelatedKeyWords();
                try{
                for(MineKeyWord m :ms.getType(StoreManager.typeName).getKeyWords()){
                    //out.println(m.getStem()+" "+m.getNumberRelContent()+"<br>");
                    out.println("<a class=link href=keyword.jsp?keystem="+m.getStem()+">"+m.getStem().replace("_", " ") +"</a><br>");
                    //m.getRelatedKeyWords();
                }
                //out.println(""+m1.getNumberRelContent()+m1.getRelatedKeyWords());
                }catch(UnsatisfiedLinkError u){
                          out.println(u.getMessage());
                }catch(Exception e){
                    out.println(e.getMessage());
                }
               //ms.linkKeyWords();
            %>
        </p>
        <h2>Contents</h2>
        <p>
        <%
            for(MineContent mc:ms.getType(StoreManager.typeName).getContents()){
                out.println("<a class=link href=content.jsp?contentname="+mc.getName()+">"+mc.getName().replace("_", " ") +"</a><br>");
            }
        %>
        </p>
        <h2>Add Content</h2>
        <form action="addContent.jsp" method=post>
            <input type =text name = "title"/>
            <input type=textfield name ="content"/>
            <input id="buttonsubmit" type =submit name=launch />
        </form>
        
        
        
        
    </body>
</html>
