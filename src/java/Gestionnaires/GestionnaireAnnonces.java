/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Gestionnaires;

import Classes.Annonce;
import Classes.Conversation;
import Exceptions.IncompatibleUserLevelException;
import Interfaces.IGestionnaireAnnonces;
import NotifyLists.AnnonceNotifyList;
import NotifyLists.TypeNotifyList;
import Serveur.ServeurRMI;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Valentin
 */
public class GestionnaireAnnonces extends UnicastRemoteObject implements IGestionnaireAnnonces{
    
    private static AnnonceNotifyList anl = new AnnonceNotifyList();
    private static TypeNotifyList tnl = new TypeNotifyList();
    
    public GestionnaireAnnonces() throws RemoteException
    {
        super();
    }
    
    public void ajouterAnnonce(String _contenu, String _login, String _type) throws IncompatibleUserLevelException, RemoteException
    {
        if(GestionnaireUtilisateurs.getUtilisateur(_login).getUserLevel() < 2)
            throw new IncompatibleUserLevelException();
        
        Annonce a = new Annonce(_login, _contenu, _type);
        anl.add(a);
        try {
            ServeurRMI.registry.bind("Serveur/Annonce"+a.getId(), a);
        } catch (AlreadyBoundException ex) {
            Logger.getLogger(GestionnaireAnnonces.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AccessException ex) {
            Logger.getLogger(GestionnaireAnnonces.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void ajouterType(String _type, String _login) throws IncompatibleUserLevelException
    {
        if(!GestionnaireUtilisateurs.getUtilisateur(_login).isAdmin())
            throw new IncompatibleUserLevelException();
        
        tnl.add(_type);
    }
    
    public void supprimerType(String _type, String _login) throws IncompatibleUserLevelException
    {
        if(!GestionnaireUtilisateurs.getUtilisateur(_login).isAdmin())
            throw new IncompatibleUserLevelException();
        
        tnl.remove(_type);
    }
    
    public static boolean isTypeValide(String _type)
    {
        return tnl.contains(_type);
    }
    
    public void supprimerAnnonce(int _idAnnonce, String _login) throws IncompatibleUserLevelException, RemoteException
    {
        if(!getAnnonce(_idAnnonce).isAuthorized(_login))
            throw new IncompatibleUserLevelException();
        
        Annonce a = getAnnonce(_idAnnonce);
        for(Conversation c : a.getConversations())
        {
            a.supprimerConversation(c, _login);
        }
        a.getConversations().clear();
        anl.remove(a);
        try {
            ServeurRMI.registry.unbind("Serveur/Annonce"+a.getId());
        } catch (NotBoundException ex) {
            Logger.getLogger(GestionnaireAnnonces.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AccessException ex) {
            Logger.getLogger(GestionnaireAnnonces.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void supprimerAnnonces(String _loginAuteur, String _login) throws IncompatibleUserLevelException, RemoteException
    {
        if(!GestionnaireUtilisateurs.getUtilisateur(_login).isAdmin())
            throw new IncompatibleUserLevelException();
        
        for(int i : getAnnoncesFromUser(_loginAuteur))
        {
            this.supprimerAnnonce(i, _login);
        }
    }
    
    public static Annonce getAnnonce(int _id)
    {
        for(Annonce a : getAnnonces())
        {
            if (a.getId() == _id)
            {
                return a;
            }
        }
        
        return null;
    }
    
    public static ArrayList<Integer> getAnnoncesFromUser(String _login) throws RemoteException
    {
        ArrayList<Integer> liste = new ArrayList<>();
        for(Annonce a : getAnnonces())
        {
            if (a.getAuteur().equals(_login))
            {
                liste.add(a.getId());
            }
        }
        
        return liste;
    }
    
    public static ArrayList<Annonce> getAnnonces()
    {
        return anl;
    }
}
