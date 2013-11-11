/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Classes;

import Exceptions.BannedUserException;
import Exceptions.UserAlreadyBannedException;
import Interfaces.JDOMSerializable;
import java.util.ArrayList;
import NotifyLists.ConversationNotifyList;
import org.jdom2.Element;

/**
 *
 * @author Valentin
 */
public class Annonce implements JDOMSerializable{
    
    private static int                currentID = 0;
    private int                       id;
    private Utilisateur               auteur;
    private String                    contenu;
    private ConversationNotifyList    conversations;
    private ArrayList<String>         bannis;
    private String                    type;
    
    public Annonce(Utilisateur _auteur, String _contenu, String _type)
    {
        this.id = Annonce.currentID++;
        this.auteur = _auteur;
        this.contenu = _contenu;
        this.conversations = new ConversationNotifyList();
        this.bannis = new ArrayList<>();
        //TO-DO
        //Vérifier si le type est bien dans la liste statique de type du gestionnaire d'annonces.
    }
    
    public int getId()
    {
        return this.id;
    }
    
    public Utilisateur getAuteur()
    {
        return this.auteur;
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
    
    public void addMessage(String _login, String _contenu) throws BannedUserException
    {
        if(this.isBan(_login))
        {
            throw new BannedUserException();
        }
        else
        {
            this.conversations.add(new Message(_login, _contenu));
        }
    }
    
    public void bannir(String _login, boolean banni) throws UserAlreadyBannedException
    {
        // TO-DO
        // Vérifier que le login existe
        if(banni)
        {
            if(this.bannis.contains(_login))
            {
                throw new UserAlreadyBannedException();
            }
            else
            {
                this.bannis.add(_login);
            }
        }
        else if(this.bannis.contains(_login))
        {
            this.bannis.remove(_login);
        }
    }

    @Override
    public Element toElement() {
        Element eAnnonce     = new Element("Annonce");
        
        Element eId         = new Element("Id", String.valueOf(this.getId()));
        Element eAuteur     = new Element("Auteur", this.getLoginAuteur());
        Element eReponses   = new Element("Reponses");
        Element eBannis     = new Element("Bannis");
        
        for(Conversation c : this.conversations.getConversations())
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
        eAnnonce.addContent(eReponses);
        eAnnonce.addContent(eBannis);
        
        return eAnnonce;
    }
}
