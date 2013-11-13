import Exceptions.BannedUserException;
import Exceptions.IncompatibleUserLevelException;
import Interfaces.IAnnonce;
import Interfaces.IGestionnaireAnnonces;
import Interfaces.IGestionnaireUtilisateurs;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
 
/**
 * Client permettant d'interroger la personne sur le serveur distant.
 * @author Cyril Rabat
 * @version 08/10/2013
 */
public class ClientRMI {
 
    /**
     * Methode principale.
     * @param args inutilise
     */
    public static void main(String[] args){
    	IGestionnaireUtilisateurs GU = null;
        IGestionnaireAnnonces GA = null;
        IAnnonce A1 = null;
        try {
            Registry registry = LocateRegistry.getRegistry(12345);
            GU = (IGestionnaireUtilisateurs) registry.lookup("Serveur/GestionnaireUtilisateurs");
            GA = (IGestionnaireAnnonces) registry.lookup("Serveur/GestionnaireAnnonces");
            A1 = (IAnnonce) registry.lookup("Serveur/Annonce0");
            A1.addMessage("Bidule", "Comment ça va ?");
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IncompatibleUserLevelException ex) {
            Logger.getLogger(ClientRMI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BannedUserException ex) {
            Logger.getLogger(ClientRMI.class.getName()).log(Level.SEVERE, null, ex);
        }		
    }
 
}