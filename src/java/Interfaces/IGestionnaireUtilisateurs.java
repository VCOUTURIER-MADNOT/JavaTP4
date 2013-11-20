/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interfaces;

import Exceptions.ImpossibleConnexionException;
import Exceptions.IncompatibleUserLevelException;
import Exceptions.LoginAlreadyUsedException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

/**
 *
 * @author valentin
 */
public interface IGestionnaireUtilisateurs extends Remote{
    public void inscrire(String _login, String _password, String _nom) throws LoginAlreadyUsedException, RemoteException;
    public void desinscrire(String _login, String _loginAdmin) throws IncompatibleUserLevelException, RemoteException;
    public boolean connect(String _login, String _password) throws ImpossibleConnexionException, RemoteException;
    public HashMap<String, String> getInformations(String _login) throws RemoteException;
}
