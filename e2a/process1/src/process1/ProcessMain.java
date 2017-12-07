package process1;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ProcessMain {
    public static void main(String[] args){
        try {
            ProcessRMI process1 = new Process();
            //LocateRegistry.createRegistry(1099);
            Naming.bind("Process1", process1);
            System.out.println("Process1 is waiting for client...");

            Scanner scanner = new Scanner(System.in);
            while(true) {
                try {
                    String command = scanner.nextLine();
                    switch (command) {
                        case "start":
                            try {
                                ProcessRMI process2 = (ProcessRMI) Naming.lookup("rmi://localhost:2/Process2");
                                process2.register(process1, 1);
                                System.out.println("Process " + process1.getId() + " is registered in Process 2");
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
                                process1.sendTid();
                            }catch (RemoteException e){

                            }
                            break;
                        case "sendmax":
                            try {
                                process1.sendMax();
                            }catch (RemoteException e){

                            }
                            break;
                        case "updatestatus":
                            try {
                                process1.updateStatus();
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