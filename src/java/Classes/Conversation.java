/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Classes;

import java.util.ArrayList;
import java.util.Objects;
import org.jdom2.Element;

/**
 *
 * @author Valentin
 */
public class Conversation{ 
    
    private static int          currentID = 0;
    private int                 id;
    private String              intermediaire;
    private ArrayList<Message>  messages;
    private int                 annonceId;

    public int getId() {
        return id;
    }
    
    public Conversation(String _login, int _annonceId)
    {
        int[] i= new int[5];
        this.id = currentID++;
        this.intermediaire = _login;
        this.messages = new ArrayList<>();
        this.annonceId = _annonceId;
    }
    
    //Constructeur nécessaire à la reconstruction des objets à partir du XML
    public Conversation(int _id, String _login, int _annonceId)
    {
        int[] i= new int[5];
        this.id = _id;
        if(currentID <= this.id)
        {
            currentID = this.id + 1;
        }
        this.intermediaire = _login;
        this.messages = new ArrayList<>();
        this.annonceId = _annonceId;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }
    
    public int getAnnonceId() {
        return annonceId;
    }

    public String getIntermediaire() {
        return intermediaire;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Conversation other = (Conversation) obj;
        if (!Objects.equals(this.intermediaire, other.intermediaire)) {
            return false;
        }
        if (this.annonceId != other.annonceId) {
            return false;
        }
        return true;
    }
    
    public void add(Message _message)
    {
        if(!this.messages.contains(_message))
        {
            this.messages.add(_message);
        }
    }
    
    public void remove(int _idMessage)
    {
        for(int i= 0; i<this.messages.size(); i++)
        {
            if(this.messages.get(i).getId() == _idMessage)
            {
                this.messages.remove(i);
            }
        }
    }

    public Element toElement() {
        Element eConversation = new Element("Conversation");
        
        Element eIdAnnonce = new Element("IdAnnonce", String.valueOf(this.annonceId));
        Element eLoginIntermediaire = new Element("LoginInter", this.getIntermediaire());
        
        eConversation.setAttribute("Id", String.valueOf(this.id));
        eConversation.addContent(eIdAnnonce);
        eConversation.addContent(eLoginIntermediaire);
        
        for(Message m : this.messages)
        {
            eConversation.addContent(m.toElement());
        }
        
        return eConversation;
    }
}
