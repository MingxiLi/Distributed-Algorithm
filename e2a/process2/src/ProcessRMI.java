
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Define the interface of processor
 *
 * @author Mingxi Li
 * @author
 */

public interface ProcessRMI extends Remote{

    public void receiveNtid(Message message) throws RemoteException;

    public void receiveNNtid(Message message) throws RemoteException;

    public void sendTid() throws RemoteException;

    public void sendMax() throws RemoteException;

    public void updateStatus() throws RemoteException;

    public int getId() throws RemoteException;

    public int getTid() throws RemoteException;

    public int getAddressID() throws RemoteException;

    public boolean getStatus() throws RemoteException;

    public void register(ProcessRMI process, int addressID) throws RemoteException;

    public int getLargestID() throws RemoteException;
}
