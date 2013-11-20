<%-- 
    Document   : annonces
    Created on : 19 nov. 2013, 12:21:40
    Author     : Valentin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Annonces</title>
    </head>
    <body>
        <h1>Annonces!</h1>
        <%
            out.print(session.getAttribute("Annonces"));
        %>
    </body>
</html>
