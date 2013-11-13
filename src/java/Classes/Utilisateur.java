/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Classes;

import java.util.Objects;
import org.jdom2.Element;

/**
 *
 * @author Valentin
 */
public class Utilisateur{

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

    public Element toElement() {
        Element eUtilisateur = new Element("Utilisateur");
        
        Element eLogin = new Element("Login");
        eLogin.setText(this.getLogin());
        Element ePassword = new Element("Password");
        ePassword.setText(this.getPassword());
        Element eNom = new Element("Nom");
        eNom.setText(this.getNom());
        Element eUsrLvl = new Element ("UserLevel");
        eUsrLvl.setText(String.valueOf(this.getUserLevel()));
        
        eUtilisateur.addContent(eLogin);
        eUtilisateur.addContent(ePassword);
        eUtilisateur.addContent(eNom);
        eUtilisateur.addContent(eUsrLvl);
        
        return eUtilisateur;        
    }
    
}
