<%-- 
    Document   : addContent
    Created on : 16 oct. 2012, 22:17:37
    Author     : Teuk
--%>

<%@page import="com.smartcontent.testfront.StoreManager"%>
<%@page import="com.smartcontent.graphstore.api.MineContentType"%>
<%@page import="com.smartcontent.graphstore.impl.MineStorageImpl"%>
<%@page import="com.smartcontent.graphstore.api.MineStorage"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
MineStorage ms = StoreManager.getStore();
//ms.createContentType("con");
MineContentType ct = ms.getType(StoreManager.typeName);
request.setCharacterEncoding("UTF-8");
ct.createContent(((String)request.getParameter("title")).replace(" ", "_"), "fr", ((String)request.getParameter("content")));

request.getRequestDispatcher("index.jsp").forward(request, response);
%>
