/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Gestionnaires;

import Classes.Annonce;
import Classes.Conversation;
import Exceptions.IncompatibleUserLevelException;
import NotifyLists.AnnonceNotifyList;
import NotifyLists.TypeNotifyList;
import java.util.ArrayList;

/**
 *
 * @author Valentin
 */
public class GestionnaireAnnonces {
    
    private static AnnonceNotifyList anl = new AnnonceNotifyList();
    private static TypeNotifyList tnl = new TypeNotifyList();
    
    public static void ajouterAnnonce(String _contenu, String _login, String _type) throws IncompatibleUserLevelException
    {
        if(GestionnaireUtilisateurs.getUtilisateur(_login).getUserLevel() < 2)
            throw new IncompatibleUserLevelException();
        
        anl.add(new Annonce(_login, _contenu, _type));
    }
    
    public static void ajouterType(String _type, String _login) throws IncompatibleUserLevelException
    {
        if(GestionnaireUtilisateurs.getUtilisateur(_login).isAdmin())
            throw new IncompatibleUserLevelException();
        
        tnl.add(_type);
    }
    
    public static void supprimerType(String _type, String _login) throws IncompatibleUserLevelException
    {
        if(GestionnaireUtilisateurs.getUtilisateur(_login).isAdmin())
            throw new IncompatibleUserLevelException();
        
        tnl.remove(_type);
    }
    
    public static boolean isTypeValide(String _type)
    {
        return tnl.contains(_type);
    }
    
    public static void supprimerAnnonce(int _idAnnonce, String _login) throws IncompatibleUserLevelException
    {
        if(!getAnnonce(_idAnnonce).isAuthorized(_login))
            throw new IncompatibleUserLevelException();
        
        Annonce a = getAnnonce(_idAnnonce);
        for(Conversation c : a.getConversations())
        {
            c.getMessages().clear();
        }
        a.getConversations().clear();
        anl.remove(a);
    }
    
    public static void supprimerAnnonces(String _loginAuteur, String _login) throws IncompatibleUserLevelException
    {
        if(!GestionnaireUtilisateurs.getUtilisateur(_login).isAdmin())
            throw new IncompatibleUserLevelException();
        
        for(int i : getAnnoncesFromUser(_loginAuteur))
        {
            supprimerAnnonce(i, _login);
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
    
    public static ArrayList<Integer> getAnnoncesFromUser(String _login)
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
