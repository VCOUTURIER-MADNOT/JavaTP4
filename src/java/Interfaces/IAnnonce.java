/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interfaces;

import Classes.Conversation;
import Classes.Message;
import Classes.Utilisateur;
import Exceptions.BannedUserException;
import Exceptions.IncompatibleUserLevelException;
import Exceptions.UserAlreadyBannedException;
import NotifyLists.ConversationNotifyList;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author valentin
 */
public interface IAnnonce extends Remote{
    public Utilisateur getAuteur() throws RemoteException;
    public String getLoginAuteur() throws RemoteException;
    public ConversationNotifyList getConversations()throws RemoteException;
    public String getContenu() throws RemoteException;
    public Conversation getConversation(String _login) throws RemoteException;
    public Message getMessage(Conversation _conversation, int _idMessage, String _login) throws RemoteException;
    public void addMessage(String _login, String _contenu) throws BannedUserException, IncompatibleUserLevelException, RemoteException;
    public void supprimerMessage(Conversation _conversation, int _idMessage, String _login) throws IncompatibleUserLevelException, RemoteException;
    public void supprimerConversation(Conversation _conversation, String _login) throws IncompatibleUserLevelException, RemoteException;
    public void bannir(String _loginBan, boolean banni, String _login) throws UserAlreadyBannedException, IncompatibleUserLevelException, RemoteException;
}
