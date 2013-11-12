/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Classes;

import Exceptions.BannedUserException;
import Exceptions.IncompatibleUserLevelException;
import Exceptions.UserAlreadyBannedException;
import Gestionnaires.GestionnaireUtilisateurs;
import java.util.ArrayList;
import NotifyLists.ConversationNotifyList;
import org.jdom2.Element;

/**
 *
 * @author Valentin
 */
public class Annonce{
    
    private static int                currentID = 0;
    private int                       id;
    private Utilisateur               auteur;
    private String                    contenu;
    private ConversationNotifyList    conversations;
    private ArrayList<String>         bannis;
    private String                    type;
    
    public Annonce(String _login, String _contenu, String _type)
    {
        this.id = Annonce.currentID++;
        this.auteur = GestionnaireUtilisateurs.getUtilisateur(_login);
        this.contenu = _contenu;
        this.conversations = new ConversationNotifyList(this.getId());
        this.bannis = new ArrayList<>();
        //TO-DO
        //Vérifier si le type est bien dans la liste statique de type du gestionnaire d'annonces.
    }
    
    //Constructeur nécessaire à la reconstruction des objets à partir du XML
    public Annonce(int _id, String _login, String _contenu, String _type)
    {
        this.id = _id;
        if(currentID <= this.id)
        {
            currentID = this.id + 1;
        }
        this.auteur = GestionnaireUtilisateurs.getUtilisateur(_login);
        this.contenu = _contenu;
        this.conversations = new ConversationNotifyList(this.getId());
        this.bannis = new ArrayList<>();
    }
    
    public int getId()
    {
        return this.id;
    }
    
    public Utilisateur getAuteur()
    {
        return this.auteur;
    }

    public ConversationNotifyList getConversations() {
        return conversations;
    }
    
    public String getLoginAuteur()
    {
        return this.auteur.getLogin();
    }
    
    public String getContenu()
    {
        return this.contenu;
    }
    
    public void setContenu(String _contenu)
    {
        this.contenu = _contenu;
    }
    
    public boolean isBan(String _login)
    {
        return this.bannis.contains(_login);
    }
    
    public boolean isAuthorized(String _login)
    {
        return _login.equals(this.getAuteur()) || GestionnaireUtilisateurs.getUtilisateur(_login).isAdmin();
    }
    
    public Conversation getConversation(String _login)
    {
        for(Conversation c : this.conversations)
        {
            if(c.getIntermediaire().equals(_login))
            {
                return c;
            }
        }
        return null;
    }
    
    public void addMessage(String _login, String _contenu) throws BannedUserException, IncompatibleUserLevelException
    {
        if(GestionnaireUtilisateurs.getUtilisateur(_login).getUserLevel() < 2)
            throw new IncompatibleUserLevelException();
        
        if(this.isBan(_login))
        {
            throw new BannedUserException();
        }
        else
        {
            Message m = new Message(_login, _contenu);
            Conversation c = this.getConversation(_login);
            if(c == null)
            {
                c = new Conversation(_login, this.getId());
            }
            
            c.add(m);
        }
    }
    
    public void bannir(String _loginBan, boolean banni, String _login) throws UserAlreadyBannedException, IncompatibleUserLevelException
    {
        if(!_login.equals(this.getAuteur()) || !GestionnaireUtilisateurs.getUtilisateur(_login).isAdmin())
            throw new IncompatibleUserLevelException();
        
        if(banni)
        {
            if(this.bannis.contains(_loginBan))
            {
                throw new UserAlreadyBannedException();
            }
            else
            {
                this.bannis.add(_loginBan);
            }
        }
        else if(this.bannis.contains(_loginBan))
        {
            this.bannis.remove(_loginBan);
        }
    }

    public void setConversations(ConversationNotifyList _conversations) {
        this.conversations = _conversations;
    }

    public Element toElement() {
        Element eAnnonce     = new Element("Annonce");
        
        Element eId         = new Element("Id", String.valueOf(this.getId()));
        Element eAuteur     = new Element("Auteur", this.getLoginAuteur());
        Element eContenu    = new Element("Contenu", this.getContenu());
        Element eReponses   = new Element("Reponses");
        Element eBannis     = new Element("Bannis");
        
        for(Conversation c : this.conversations)
        {
            Element eIdConv     = new Element("IdConv", String.valueOf(c.getId()));
            eReponses.addContent(eIdConv);
        }
        
        for(String login : this.bannis)
        {
            Element eLogin     = new Element("Login", login);
            eReponses.addContent(eLogin);
        }
        
        eAnnonce.setAttribute("type", this.type);
        
        eAnnonce.addContent(eId);
        eAnnonce.addContent(eAuteur);
        eAnnonce.addContent(eContenu);
        eAnnonce.addContent(eReponses);
        eAnnonce.addContent(eBannis);
        
        return eAnnonce;
    }
}
