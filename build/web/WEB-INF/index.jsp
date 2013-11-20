<%-- 
    Document   : index
    Created on : 18 nov. 2013, 22:48:18
    Author     : Valentin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Connexion</title>
    </head>
    <body>
        <fieldset>
            <legend> Connection </legend>
            <%
                String conn = new String();
                
                if(session.getAttribute("Nom") == null)
                {
                    conn = "<form name='formu' method='POST' action='connexion'>\n";
                    conn +="\tLogin: <input type='text' name='login'>\n";
                    conn +="\tMot de passe: <input type='password' name='password'>\n";
                    conn +="\t<input type='submit' name='Envoyer' value='Envoyer'>";
                    conn +="</form>";
                }
                else
                {
                    conn = "<form name='formu' method='POST' action='deconnexion'>\n";
                    conn +="\t<p>Bienvenue " + session.getAttribute("Nom") + "</p>\n";
                    conn +="\t<input type='Submit' name='Envoyer' value='Déconnection'>";
                    conn +="</form>"; 
                }
                out.print(conn);
                
                if(request.getSession(false) == null)
                    System.out.println(" 6 ");
            %>
        </fieldset>
        <ul>
            <li><a href="annonces">Articles</a></li>
        </ul>
    </body>
</html>
