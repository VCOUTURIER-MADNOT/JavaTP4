/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Classes;

import java.util.Objects;

/**
 *
 * @author Valentin
 */
public class Utilisateur {

    private String  login;
    private String  password;
    private String  nom;
    private int     userLevel;

    public Utilisateur(String _login, String _password, String _nom, int _userLevel)
    {
        this.login = _login;
        this.password = _password;
        this.nom = _nom;
        this.userLevel = _userLevel;
    }

    public String getLogin() {
        return login;
    }

    public String getNom() {
        return nom;
    }

    public String getPassword() {
        return password;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }    

    public boolean isAdmin()
    {
        return this.getUserLevel() == 3;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Utilisateur other = (Utilisateur) obj;
        if (!Objects.equals(this.login, other.login)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "Utilisateur{" + "login=" + login + ", password=" + password + ", nom=" + nom + ", userLevel=" + userLevel + '}';
    }
    
}


