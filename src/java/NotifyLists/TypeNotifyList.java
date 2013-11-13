/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package NotifyLists;

import Classes.Annonce;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author Valentin
 */
public class TypeNotifyList extends NotifyList<String>{

    public TypeNotifyList()
    {
        SAXBuilder sxb = new SAXBuilder();
        this.xmlUrl = "Types.xml";
        try
        {
            this.document = sxb.build(new File(this.xmlUrl));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        this.racine = this.document.getRootElement();
        
        for (Element eType : this.racine.getChildren("Type"))
        {
            this.add((String) this.getObjectFromElement(eType), false);
        }        
    }
    
    @Override
    public void addToXML(String _o) {
        Element eType = this.getElementFromObject(_o);
        if(eType != null)
        {
            this.racine.addContent(eType);
        }
    }

    @Override
    public void removeFromXML(Object _o) {
        if(_o instanceof String)
        {
            String s = (String) _o;
            this.racine.removeContent(this.getElementFromId(s));
        } 
    }

    @Override
    protected Element getElementFromId(String _id) {
        List<Element> types = this.racine.getChildren("Type");
        Iterator<Element> iterator = types.iterator();
        Element eType = null;
        while(iterator.hasNext())
        {
            eType = iterator.next();
            if (eType.getText().equals(_id))
            {	
                return eType;
            }
        }

        return null;
    }

    @Override
    protected Element getElementFromObject(Object _o) {
        Element eType = null;
        if (_o instanceof String)
        {
            String s = (String) _o;
            eType = new Element("Type");
            eType.setText(s);
        }
        
        return eType;
    }

    @Override
    protected Object getObjectFromElement(Element _e) {
        return _e.getText();
    }
    
}
