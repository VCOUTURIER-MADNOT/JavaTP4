/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interfaces;

import Exceptions.IncompatibleUserLevelException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author valentin
 */
public interface IGestionnaireAnnonces extends Remote{
    public void ajouterAnnonce(String _contenu, String _login, String _type) throws IncompatibleUserLevelException, RemoteException;
    public void supprimerAnnonce(int _idAnnonce, String _login) throws IncompatibleUserLevelException, RemoteException;
    public void ajouterType(String _type, String _login) throws IncompatibleUserLevelException, RemoteException;
    public void supprimerType(String _type, String _login) throws IncompatibleUserLevelException, RemoteException;
    public String getAnnoncesHTMLResume() throws RemoteException;
}
