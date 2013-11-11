/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Classes;

/**
 *
 * @author Valentin
 */
public class Message {
    
    private static int  currentID = 0;
    private int         id;
    private Utilisateur expediteur;
    private String      contenu;
    private int         conversationId;
    
    public Message(Utilisateur _expediteur, String _contenu, int _conversationId)
    {
        this.id = Message.currentID++;
        this.expediteur = _expediteur;
        this.contenu = _contenu;
        this.conversationId = _conversationId;
    }
}
