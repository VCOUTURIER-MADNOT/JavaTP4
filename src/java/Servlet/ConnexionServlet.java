/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Servlet;

import Exceptions.ImpossibleConnexionException;
import Interfaces.IGestionnaireUtilisateurs;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Valentin
 */
public class ConnexionServlet extends HttpServlet {

    private static final String URL = "/WEB-INF/index.jsp";
    private IGestionnaireUtilisateurs GU;
    
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {        
        super.service(request, response);
        
        Registry registry = LocateRegistry.getRegistry(12345);
        try {            
            this.GU = (IGestionnaireUtilisateurs) registry.lookup("Serveur/GestionnaireUtilisateurs");
        } catch (RemoteException ex) {
            Logger.getLogger(ConnexionServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(ConnexionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");        
        this.getServletContext().getRequestDispatcher(URL).forward( request, response );
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.creerSession(request);
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        try {
            this.GU.connect(login, password);
            request.setAttribute("res", true);
            this.creerSession(request);
        } catch (RemoteException ex) {
            Logger.getLogger(ConnexionServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ImpossibleConnexionException ex) {
            request.setAttribute("res", false);
        }
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void creerSession(HttpServletRequest request)
    {
        request.setAttribute("Resultat", "Connexion réussie!");
        boolean res = (request.getAttribute("res")==null) ? false : (boolean) request.getAttribute("res");
        HttpSession session = request.getSession(false);
        if(res)
        {
            session = request.getSession(true);
            HashMap<String, String> infos;
            try {
                infos = GU.getInformations((String) request.getParameter("login"));
                for(String key : infos.keySet())
                {
                    session.setAttribute(key, infos.get(key));
                }
            } catch (RemoteException ex) {
                Logger.getLogger(ConnexionServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
