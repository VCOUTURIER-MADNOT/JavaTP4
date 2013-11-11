/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Classes;

import java.util.ArrayList;
import NotifyLists.ConversationNotifyList;

/**
 *
 * @author Valentin
 */
public class Annonce {
    
    private static int                currentID = 0;
    private int                       id;
    private Utilisateur               auteur;
    private String                    contenu;
    private ConversationNotifyList    conversations;
    private ArrayList<Utilisateur>    bannis;
    
    public Annonce(Utilisateur _auteur, String _contenu)
    {
        this.id = Annonce.currentID++;
        this.auteur = _auteur;
        this.contenu = _contenu;
        this.conversations = new ConversationNotifyList();
        this.bannis = new ArrayList<Utilisateur>();
    }
}
