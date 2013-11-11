/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Classes;

import java.util.ArrayList;

/**
 *
 * @author Valentin
 */
public class Conversation { 
    
    private static int currentID = 0;
    private int id;
    private Utilisateur intermediaire;
    private ArrayList<Message> messages;
    
    public Conversation(Utilisateur _intermediare)
    {
        this.id = Conversation.currentID++;
        this.intermediaire = _intermediare;
        this.messages = new ArrayList<>();
    }
    
}
