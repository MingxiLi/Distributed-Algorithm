package basis;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Map;
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
                                ProcessRMI process1 = (ProcessRMI) Naming.lookup("rmi://localhost:1/Process1");
                                process1.register(process3, 3);
                                System.out.println("Process " + process3.getId() + " is registered in Process 1");

                                ProcessRMI process2 = (ProcessRMI) Naming.lookup("rmi://localhost:2/Process2");
                                process2.register(process3, 3);
                                System.out.println("Process " + process3.getId() + " is registered in Process 2");
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            } catch (NotBoundException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "broadcast":
                            try {
                                process3.broadcast("broadcast from process3");
                            }catch (RemoteException e){

                            }
                            break;
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
