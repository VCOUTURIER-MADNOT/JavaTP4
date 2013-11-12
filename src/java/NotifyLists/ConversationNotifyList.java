/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package NotifyLists;

import Classes.Conversation;
import Classes.Message;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author Valentin
 */
public class ConversationNotifyList extends NotifyList<Conversation>{

    private int idAnnonce;
    
    public ConversationNotifyList(int _idAnnonce) {
        SAXBuilder sxb = new SAXBuilder();
        this.xmlUrl = "Conversations.xml";
        try
        {
            this.document = sxb.build(new File(this.xmlUrl));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        this.racine = this.document.getRootElement();
        this.idAnnonce = _idAnnonce;
        
        for (Element eConversation : this.racine.getChildren("Conversation"))
        {
            if(Integer.valueOf(eConversation.getChildText("idAnnonce")) ==  this.idAnnonce)
                this.add((Conversation) this.getObjectFromElement(eConversation), false);
        }
    }

    @Override
    public void addToXML(Conversation _o) {
        Element eConversation = this.getElementFromObject(_o);
        if(eConversation != null)
        {
            this.racine.addContent(eConversation);
        }
    }

    @Override
    public void removeFromXML(Object _o) {
        if(_o instanceof Conversation)
        {
            Conversation c = (Conversation) _o;
            this.racine.removeContent(this.getElementFromId(String.valueOf(c.getId())));
        }
    }

    @Override
    protected Element getElementFromId(String _id) {
        List<Element> conversations = this.racine.getChildren("Conversation");
        Iterator<Element> iterator = conversations.iterator();
        Element eConversation = null;
        while(iterator.hasNext())
        {
            eConversation = iterator.next();
            if (Integer.valueOf(eConversation.getAttributeValue("Id")) == Integer.valueOf(_id))
            {	
                return eConversation;
            }
        }

        return null;
    }

    @Override
    protected Object getObjectFromElement(Element _e) {
        Conversation c = null;
        if(Integer.valueOf(_e.getChildText("idAnnonce")) == this.idAnnonce)
        {
            c = new Conversation(Integer.valueOf(_e.getAttributeValue("Id")), _e.getChildText("LoginInter"), this.idAnnonce);
            for(Element eMessage : _e.getChildren("Message"))
            {
                Message m = new Message(Integer.valueOf(eMessage.getChildText("Id")), eMessage.getChildText("Login"), eMessage.getChildText("Contenu"));
                c.add(m);
            }
        }
        
        return c;
    }

    @Override
    protected Element getElementFromObject(Object _o) {
        Element eConversation = null;
        
        if (_o instanceof Conversation)
        {
            Conversation c = (Conversation) _o;
            eConversation = c.toElement();
        }
        
        return eConversation;
    }
    
}
