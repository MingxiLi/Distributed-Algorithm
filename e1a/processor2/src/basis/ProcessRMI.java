package basis;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

/**
 * Define the interface of processor
 *
 * @author Mingxi Li
 * @author
 */

public interface ProcessRMI extends Remote{

    /**
     * Receive message. The processor deliver it or put it in the buffer
     *
     * @param message
     * @throws RemoteException
     */
    public void receiveMessage(Message message) throws RemoteException;

    /**
     * Send a message.
     *
     * @param destination the receiver processor
     * @param content the message content
     * @throws RemoteException
     */
    public void broadcast(String content) throws RemoteException;

    public void register(ProcessRMI processor, int id) throws RemoteException;

    public int getId() throws RemoteException;

    public HashMap<Integer, Integer> getVector_clock() throws RemoteException;

    public HashMap<Integer, ProcessRMI> getProcessList() throws RemoteException;
}
