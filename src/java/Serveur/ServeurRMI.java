package Serveur;

import Gestionnaires.GestionnaireAnnonces;
import Gestionnaires.GestionnaireUtilisateurs;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

 
/**
 * Serveur qui cree une personne et la met a disposition des clients.
 * @author Cyril Rabat
 * @version 08/10/2013
 */
public class ServeurRMI {
 
	public static Registry registry;
    /**
     * Methode principale.
     * @param args inutilise
     */
    public static void main(String[] args) {
		try {
			registry = LocateRegistry.createRegistry(12345);
			GestionnaireUtilisateurs GU = new GestionnaireUtilisateurs();
                        GestionnaireAnnonces GA = new GestionnaireAnnonces();
			registry.bind("Serveur/GestionnaireUtilisateurs", GU);
                        registry.bind("Serveur/GestionnaireAnnonces", GA);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			// TODO Auto-generated catch block
			
		}
		System.out.println("Serveur d�marr� !");
    }
 
}