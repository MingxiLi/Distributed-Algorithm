package process4;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ProcessMain {
    public static void main(String[] args){
        try {
            ProcessRMI process4 = new Process();
            LocateRegistry.createRegistry(1099);
            Naming.bind("Process4", process4);
            System.out.println("Process4 is waiting for client...");

            Scanner scanner = new Scanner(System.in);
            while(true) {
                try {
                    String command = scanner.nextLine();
                    switch (command) {
                        case "start":
                            try {
                                ProcessRMI process1 = (ProcessRMI) Naming.lookup("Process1");
                                process1.register(process4, 4);
                                System.out.println("Process " + process4.getAddressID() + " is registered in Process 1");
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
                                process4.sendTid();
                            }catch (RemoteException e){

                            }
                            break;
                        case "sendmax":
                            try {
                                process4.sendMax();
                            }catch (RemoteException e){

                            }
                            break;
                        case "updatestatus":
                            try {
                                process4.updateStatus();
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