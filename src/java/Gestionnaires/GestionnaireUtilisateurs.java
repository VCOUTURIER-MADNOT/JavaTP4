/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Gestionnaires;

import Classes.Utilisateur;
import Exceptions.IncompatibleUserLevelException;
import Exceptions.LoginAlreadyUsedException;
import NotifyLists.UtilisateurNotifyList;

/**
 *
 * @author Valentin
 */
public class GestionnaireUtilisateurs {
    
    private static UtilisateurNotifyList unl = new UtilisateurNotifyList();
    
    public static void inscrire(String _login, String _password, String _nom) throws LoginAlreadyUsedException
    {
        if(getUtilisateur(_login)!= null)
        {
            unl.add(new Utilisateur(_login, _password, _nom, 1));
        }
        else
        {
            throw new LoginAlreadyUsedException();
        }
    }
    
    public static void desinscrire(String _login, String _loginAdmin) throws IncompatibleUserLevelException
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
}
