

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class Process extends UnicastRemoteObject implements ProcessRMI {

    public int addressID = 2;
    public int id;
    public int tid = id;
    public int ntid;
    public int nntid;
    public boolean isActive = true;
    protected int largestID = 0;

    protected HashMap<Integer, ProcessRMI> processes = new HashMap<>();

    protected Class<? extends Process> processClass;

    public Process(Class<? extends Process> processorClass)throws RemoteException {
        this.processClass = processorClass;
    }

    public Process() throws RemoteException{
        super();
    }

    // Receive ntid
    public void receiveNtid(Message message) throws RemoteException{
        if (isActive){
            ntid = message.content;
            System.out.println("Process" + message.destination + " receives " + message.content + " from Process" + message.sourece);
        }
        else {
            tid = message.content;
            System.out.println("Process" + message.destination + " receives " + message.content + " from Process" + message.sourece);
            sendTid();
        }
        if (message.content == id){
            System.out.println("Election is over");
            System.out.println("Process" + addressID + " with id " + id + " is selected as the leader.");
            System.exit(0);
        }

    }

    // Receive nntid
    public void receiveNNtid(Message message) throws RemoteException{
        if (isActive){
            nntid = message.content;
        }
        else {
            tid = message.content;
            sendTid();
        }
        if (message.content == id){
            System.out.println("Election is over");
            System.out.println("Process" + addressID + " with id " + id + " is selected as the leader.");
            System.exit(0);
        }
    }

    public void sendTid() throws RemoteException{
        for (Map.Entry<Integer, ProcessRMI> pair : processes.entrySet()){
            Message message = new Message(addressID, pair.getKey(), tid);
            System.out.println("Process" + message.sourece + " sends " + message.content + " to Process" + message.destination);
            processes.get(message.destination).receiveNtid(message);
        }
    }

    public void sendMax() throws RemoteException{
        for (Map.Entry<Integer, ProcessRMI> pair : processes.entrySet()){
            Message message = new Message(addressID, pair.getKey(), tid);
            System.out.println("Process" + message.sourece + " sends " + message.content + " to Process" + message.destination);
            processes.get(message.destination).receiveNNtid(message);
        }
    }

    public int max(int a, int b){
        if (a > b){
            return a;
        }
        else
            return b;
    }

    public void register(ProcessRMI process, int addressID) throws RemoteException{
        synchronized (processes){
            processes.put(addressID, process);
            System.out.println("A new processor connected to the Network, it's id is " + id);
        }
    }

    public void updateStatus() throws RemoteException{
        if ((ntid >= tid) && (ntid >= nntid)){
            tid = ntid;
        }
        else
            isActive = false;
    }

    public boolean getStatus() throws RemoteException{
        return isActive;
    }

    public int getId() throws RemoteException{
        return id;
    }

    public int getTid() throws RemoteException{
        return tid;
    }

    public int getAddressID() throws RemoteException{
        return addressID;
    }

    public int getLargestID() throws RemoteException{
        return largestID;
    }
}