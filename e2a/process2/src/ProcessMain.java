

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ProcessMain {
    public static void main(String[] args){
        try {
            ProcessRMI process2 = new Process();
            LocateRegistry.createRegistry(2);
            Naming.bind("rmi://localhost:2/Process2", process2);
            System.out.println("Process2 is waiting for client...");

            Scanner scanner = new Scanner(System.in);
            while(true) {
                try {
                    String command = scanner.nextLine();
                    switch (command) {
                        case "start":
                            try {
                                ProcessRMI process3 = (ProcessRMI) Naming.lookup("rmi://localhost:3/Process3");
                                process3.register(process2, 2);
                                System.out.println("Process " + process2.getId() + " is registered in Process 3");
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
                                process2.sendTid();
                            }catch (RemoteException e){

                            }
                            break;
                        case "sendmax":
                            try {
                                process2.sendMax();
                            }catch (RemoteException e){

                            }
                            break;
                        case "updatestatus":
                            try {
                                process2.updateStatus();
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
    }
}