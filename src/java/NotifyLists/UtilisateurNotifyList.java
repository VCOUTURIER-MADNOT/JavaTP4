/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package NotifyLists;

import Classes.Annonce;
import Classes.Utilisateur;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author Valentin
 */
public class UtilisateurNotifyList extends NotifyList<Utilisateur>{

    public UtilisateurNotifyList() {
        SAXBuilder sxb = new SAXBuilder();
        this.xmlUrl = "Utilisateurs.xml";
        try
        {
            this.document = sxb.build(new File(this.xmlUrl));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        this.racine = this.document.getRootElement();
        
        for (Element eUtilisateur : this.racine.getChildren("Utilisateur"))
        {
            this.add((Utilisateur) this.getObjectFromElement(eUtilisateur), false);
        }
    }
    
    @Override
    public void addToXML(Utilisateur _o) {
        Element eUtilisateur = this.getElementFromObject(_o);
        if(eUtilisateur != null)
        {
            this.racine.addContent(eUtilisateur);
        }
    }

    @Override
    public void removeFromXML(Object _o) {
        if(_o instanceof Utilisateur)
        {
            Utilisateur u = (Utilisateur) _o;
            this.racine.removeContent(this.getElementFromId(String.valueOf(u.getLogin())));
        }   
    }

    @Override
    protected Element getElementFromId(String _id) {
        List<Element> utilisateurs = this.racine.getChildren("Annonce");
        Iterator<Element> iterator = utilisateurs.iterator();
        Element eUtilisateur = null;
        while(iterator.hasNext())
        {
            eUtilisateur = iterator.next();
            if (eUtilisateur.getChildText("Login").equals(_id))
            {	
                return eUtilisateur;
            }
        }

        return null;
    }

    @Override
    protected Element getElementFromObject(Object _o) {
        Element eUtilisateur = null;
        if (_o instanceof Utilisateur)
        {
            Utilisateur u = (Utilisateur) _o;
            eUtilisateur = u.toElement();
        }
        
        return eUtilisateur;
    }

    @Override
    protected Object getObjectFromElement(Element _e) {
        Utilisateur u = null;
        
        u = new Utilisateur(_e.getChildText("Login"), _e.getChildText("Password"), _e.getChildText("Nom"), Integer.valueOf(_e.getChildText("UserLevel")));
        
        return u;
    }
    
}
