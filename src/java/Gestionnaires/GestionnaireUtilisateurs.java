/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Gestionnaires;

import Classes.Utilisateur;
import Exceptions.ImpossibleConnexionException;
import Exceptions.IncompatibleUserLevelException;
import Exceptions.LoginAlreadyUsedException;
import Interfaces.IGestionnaireUtilisateurs;
import NotifyLists.UtilisateurNotifyList;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Valentin
 */
public class GestionnaireUtilisateurs extends UnicastRemoteObject implements IGestionnaireUtilisateurs{
    
    private static UtilisateurNotifyList unl = new UtilisateurNotifyList();
    
    public GestionnaireUtilisateurs() throws RemoteException
    {
        super();
    }
    
    public void inscrire(String _login, String _password, String _nom) throws LoginAlreadyUsedException, RemoteException
    {
        if(getUtilisateur(_login) == null)
        {
            unl.add(new Utilisateur(_login, _password, _nom, 2));
        }
        else
        {
            throw new LoginAlreadyUsedException();
        }
    }
    
    public void desinscrire(String _login, String _loginAdmin) throws IncompatibleUserLevelException, RemoteException
    {
        if(getUtilisateur(_loginAdmin).isAdmin())
        {
            unl.remove(getUtilisateur(_login));
        }
        else
        {
            throw new IncompatibleUserLevelException();
        }
    }
    
    public static Utilisateur getUtilisateur(String _login)
    {
        int i = 0;
        Utilisateur utilisateur = null;
        while(i < unl.size() && utilisateur == null)
        {
                if (unl.get(i).getLogin().equals(_login))
                {
                        System.out.println(i);
                        utilisateur = unl.get(i);
                }
                i++;
        }

        return utilisateur;
    }
    
    public boolean connect(String _login, String _password) throws ImpossibleConnexionException, RemoteException
    {
        Utilisateur u = getUtilisateur(_login);
        if(u == null || !(u.getPassword().equals(_password)))
            throw new ImpossibleConnexionException();
       
        return true;
    }
    
    public HashMap<String, String> getInformations(String _login) throws RemoteException
    {
        Utilisateur u = getUtilisateur(_login);
        HashMap<String, String> infos = new HashMap<String, String>();
        infos.put("Nom", u.getNom());
        infos.put("Login", u.getLogin());
        
        return infos;
    }
}
