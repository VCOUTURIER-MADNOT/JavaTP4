/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Classes;

import Interfaces.JDOMSerializable;
import org.jdom2.Element;

/**
 *
 * @author Valentin
 */
public class Message implements JDOMSerializable{
    
    private static int  currentID = 0;
    private int         id;
    private String      expediteur;
    private String      contenu;
    private int         conversationId;
    
    public Message(String _login, String _contenu)
    {
        this.id = Message.currentID++;
        this.expediteur = _login;
        this.contenu = _contenu;
        this.conversationId = -1;
    }

    public int getId() {
        return id;
    }

    public String getExpediteur() {
        return expediteur;
    }

    public String getContenu() {
        return contenu;
    }

    public int getConversationId() {
        return conversationId;
    }

    @Override
    public Element toElement() {
        Element eMessage = new Element("Message");
        
        Element eLogin = new Element("Login", this.getExpediteur());
        Element eContenu = new Element("Contenu", this.getContenu());
        
        eMessage.addContent(eLogin);
        eMessage.addContent(eContenu);
        
        return eMessage;
    }
    
    
}
