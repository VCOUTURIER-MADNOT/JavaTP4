/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package NotifyLists;

import Classes.Annonce;
import Exceptions.UserAlreadyBannedException;
import Gestionnaires.GestionnaireAnnonces;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author Valentin
 */
public class AnnonceNotifyList extends NotifyList<Annonce>{

    public AnnonceNotifyList() {
        SAXBuilder sxb = new SAXBuilder();
        this.xmlUrl = "Annonce.xml";
        try
        {
            this.document = sxb.build(new File(this.xmlUrl));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        this.racine = this.document.getRootElement();
        
        for (Element eAnnonce : this.racine.getChildren("Annonce"))
        {
            this.add((Annonce) this.getObjectFromElement(eAnnonce), false);
        }
    }
    
    @Override
    public void addToXML(Annonce _o) {
        Element eAnnonce = this.getElementFromObject(_o);
        if(eAnnonce != null)
        {
            this.racine.addContent(eAnnonce);
        }
    }

    @Override
    public void removeFromXML(Object _o) {
        if(_o instanceof Annonce)
        {
            Annonce a = (Annonce) _o;
            this.racine.removeContent(this.getElementFromId(String.valueOf(a.getId())));
        }       
    }

    @Override
    protected Element getElementFromId(String _id) {
        List<Element> annonce = this.racine.getChildren("Annonce");
        Iterator<Element> iterator = annonce.iterator();
        Element eAnnonce = null;
        while(iterator.hasNext())
        {
            eAnnonce = iterator.next();
            if (Integer.valueOf(eAnnonce.getChildText("Id")) == Integer.valueOf(_id))
            {	
                return eAnnonce;
            }
        }

        return null;
    }

    @Override
    protected Element getElementFromObject(Object _o) {
        Element eAnnonce = null;
        if (_o instanceof Annonce)
        {
            Annonce a = (Annonce) _o;
            eAnnonce = a.toElement();
        }
        
        return eAnnonce;
    }

    @Override
    protected Object getObjectFromElement(Element _e) {
        Annonce a = null;
        if(GestionnaireAnnonces.isTypeValide(_e.getAttributeValue("type")))
        {
            a = new Annonce(Integer.valueOf(_e.getChildText("Id")), _e.getChildText("Auteur"), _e.getChildText("Contenu"), _e.getAttributeValue("type"));
            
            //Génération de la liste des bannis
            Element eBannis = _e.getChild("Bannis");
            for(Element eLogin : eBannis.getChildren("Login"))
            {
                try {
                    a.bannir(eLogin.getText(), true);
                } catch (UserAlreadyBannedException ex) {
                    Logger.getLogger(AnnonceNotifyList.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            //Génération de la liste de conversations
            ConversationNotifyList cnl = new ConversationNotifyList(Integer.valueOf(_e.getChildText("Id")));
            a.setConversations(cnl);
        }
        
        return a;
    }
    
    
}
