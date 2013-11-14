/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Classes;

import Exceptions.BannedUserException;
import Exceptions.IncompatibleUserLevelException;
import Exceptions.UserAlreadyBannedException;
import Gestionnaires.GestionnaireAnnonces;
import Gestionnaires.GestionnaireUtilisateurs;
import Interfaces.IAnnonce;
import java.util.ArrayList;
import NotifyLists.ConversationNotifyList;
import Serveur.ServeurRMI;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Element;

/**
 *
 * @author Valentin
 */
public class Annonce extends UnicastRemoteObject implements IAnnonce{
    
    private static int                currentID = 0;
    private int                       id;
    private Utilisateur               auteur;
    private String                    contenu;
    private ConversationNotifyList    conversations;
    private ArrayList<String>         bannis;
    private String                    type;
    
    public Annonce(String _login, String _contenu, String _type) throws RemoteException
    {
        this.id = Annonce.currentID++;
        this.auteur = GestionnaireUtilisateurs.getUtilisateur(_login);
        this.contenu = _contenu;
        this.conversations = new ConversationNotifyList(this.getId());
        this.bannis = new ArrayList<>();
        if(GestionnaireAnnonces.isTypeValide(_type))
        {
            this.type = _type;
        }
        else
        {
            this.type = "inconnu";
        }
        this.bindRMI();
    }
    
    //Constructeur nécessaire à la reconstruction des objets à partir du XML
    public Annonce(int _id, String _login, String _contenu, String _type) throws RemoteException
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
        if(GestionnaireAnnonces.isTypeValide(_type))
        {
            this.type = _type;
        }
        else
        {
            this.type = "inconnu";
        }
        this.bindRMI();
    }
    
    private void bindRMI()
    {
        try {
            ServeurRMI.registry.bind("Serveur/Annonce"+this.getId(), this);
        } catch (RemoteException ex) {
            Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AlreadyBoundException ex) {
            Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int getId()
    {
        return this.id;
    }
    
    public Utilisateur getAuteur() throws RemoteException
    {
        return this.auteur;
    }

    public ConversationNotifyList getConversations() throws RemoteException{
        return conversations;
    }
    
    public String getLoginAuteur() throws RemoteException
    {
        return this.auteur.getLogin();
    }
    
    public String getContenu() throws RemoteException
    {
        return this.contenu;
    }
    
    public void setContenu(String _contenu)
    {
        GestionnaireAnnonces.getAnnonces().reload();
        this.contenu = _contenu;
        GestionnaireAnnonces.getAnnonces().saveXml();
    }
    
    public boolean isBan(String _login)
    {
        return this.bannis.contains(_login);
    }
    
    public boolean isAuthorized(String _login) throws RemoteException
    {
        return _login.equals(this.getAuteur()) || GestionnaireUtilisateurs.getUtilisateur(_login).isAdmin();
    }
    
    public Conversation getConversation(String _login) throws RemoteException
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
    
    public Message getMessage(Conversation _conversation, int _idMessage, String _login)
    {
        ArrayList<Message> messages = _conversation.getMessages();
        for(int i = 0 ; i < messages.size(); i++)
        {
            if(messages.get(i).getId() == _idMessage)
            {
                return messages.get(i);
            }
        }
        
        return null;
    }
    
    public void addMessage(String _login, String _contenu) throws BannedUserException, IncompatibleUserLevelException, RemoteException
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
            else
            {
                this.conversations.remove(c);
            }
            
            c.add(m);
            this.conversations.add(c);
            GestionnaireAnnonces.getAnnonces().remove(this);
            GestionnaireAnnonces.getAnnonces().add(this);
        }
    }
    
    public void supprimerMessage(Conversation _conversation, int _idMessage, String _login) throws IncompatibleUserLevelException, RemoteException
    {
        if(!this.isAuthorized(_login))
            throw new IncompatibleUserLevelException();
        
        Message m = this.getMessage(_conversation, _idMessage, _login);
        if(m != null)
        {
            GestionnaireAnnonces.getAnnonces().reload();
            _conversation.getMessages().remove(m);
            GestionnaireAnnonces.getAnnonces().saveXml();
        }
        
    }
    
    public void supprimerConversation(Conversation _conversation, String _login) throws IncompatibleUserLevelException, RemoteException
    {
        if(!_login.equals(this.getAuteur()) || !GestionnaireUtilisateurs.getUtilisateur(_login).isAdmin())
            throw new IncompatibleUserLevelException();
        
        _conversation.getMessages().clear();
        GestionnaireAnnonces.getAnnonces().reload();
        this.conversations.remove(_conversation);
        GestionnaireAnnonces.getAnnonces().saveXml();
    }
    
    public void bannir(String _loginBan, boolean banni, String _login) throws UserAlreadyBannedException, IncompatibleUserLevelException, RemoteException
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

    public Element toElement() throws RemoteException {
        Element eAnnonce     = new Element("Annonce");
        
        Element eId         = new Element("Id");
        eId.setText(String.valueOf(this.getId()));
        Element eAuteur     = new Element("Auteur");
        eAuteur.setText(this.getLoginAuteur());
        Element eContenu    = new Element("Contenu");
        eContenu.setText(this.getContenu());
        Element eReponses   = new Element("Reponses");
        Element eBannis     = new Element("Bannis");
        
        for(Conversation c : this.conversations)
        {
            Element eIdConv     = new Element("IdConv");
            eIdConv.setText(String.valueOf(c.getId()));
            eReponses.addContent(eIdConv);
        }
        
        for(String login : this.bannis)
        {
            Element eLogin     = new Element("Login");
            eLogin.setText(login);
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
