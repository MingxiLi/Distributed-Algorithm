package process3;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ProcessMain {
    public static void main(String[] args){
        try {
            ProcessRMI process3 = new Process();
            LocateRegistry.createRegistry(3);
            Naming.bind("rmi://localhost:3/Process3", process3);
            System.out.println("Process3 is waiting for client...");

            Scanner scanner = new Scanner(System.in);
            while(true) {
                try {
                    String command = scanner.nextLine();
                    switch (command) {
                        case "start":
                            try {
                                ProcessRMI process4 = (ProcessRMI) Naming.lookup("rmi://localhost:4/Process4");
                                process4.register(process3, 3);
                                System.out.println("Process " + process3.getAddressID() + " is registered in Process 4");
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            } catch (NotBoundException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "sendtid":
                            try {
                                process3.sendTid();
                            }catch (RemoteException e){

                            }
                            break;
                        case "sendmax":
                            try {
                                process3.sendMax();
                            }catch (RemoteException e){

                            }
                            break;
                        case "updatestatus":
                            try {
                                process3.updateStatus();
                            }catch (RemoteException e){

                            }
                        default:
                            System.out.println("Wrong Command");
                            break;
                    }
                } catch (NoSuchElementException e) {

                }
            }
        }catch (RemoteException e){
            e.printStackTrace();
        }catch (AlreadyBoundException e){
            e.printStackTrace();
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        if (System.getSecurityManager() == null){
            System.setSecurityManager(new RMISecurityManager());
        }
    }
}