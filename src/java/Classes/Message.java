/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Classes;

import org.jdom2.Element;

/**
 *
 * @author Valentin
 */
public class Message{
    
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
    
    //Constructeur nécessaire à la reconstruction des objets à partir du XML
    public Message(int _id, String _login, String _contenu)
    {
        if(currentID <= this.id)
        {
            currentID = this.id + 1;
        }
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

    public Element toElement() {
        Element eMessage = new Element("Message");
        
        Element eId = new Element("Id", String.valueOf(this.getId()));
        Element eLogin = new Element("Login", this.getExpediteur());
        Element eContenu = new Element("Contenu", this.getContenu());
        
        eMessage.addContent(eId);
        eMessage.addContent(eLogin);
        eMessage.addContent(eContenu);
        
        return eMessage;
    }
    
    
}
