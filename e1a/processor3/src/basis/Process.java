package basis;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Process extends UnicastRemoteObject implements ProcessRMI {

    public int id = 3;

    public VectorClock vector_clock = new VectorClock();

    private ArrayList<Message> buffer = new ArrayList<Message>();


    protected HashMap<Integer, ProcessRMI> processes = new HashMap<>();

    protected Class<? extends Process> processClass;



    public Process(Class<? extends Process> processorClass)throws RemoteException {
        this.processClass = processorClass;
    }

    public Process() throws RemoteException{
        vector_clock.iniVectorClock(id);
    }




    public void deliverMessage(Message message){
        System.out.println("Delivered message from Process" + message.sourece + " to Process" + message.destination
                + ", saying " + message.content + ". The vector clock is (");
        for (Map.Entry<Integer, Integer> v : message.vector_clock.getVector().entrySet()) {
            System.out.print(v.getValue());
        }
        System.out.println(").");
        System.out.println();
    }

    public void receiveMessage(Message message) throws RemoteException{
        synchronized (vector_clock){
            vector_clock.increase(message.sourece);

            if (vector_clock.greaterEqual(message.vector_clock)){
                deliverMessage(message);

                boolean Update = true;
                while (Update){
                    Update = false;
                    for (int i = 0; i < buffer.size(); i++){
                        Message message_buffer = buffer.get(i);
                        vector_clock.increase(message_buffer.sourece);
                        if (vector_clock.greaterEqual(message_buffer.vector_clock)){
                            Update = true;
                            deliverMessage(message_buffer);
                            buffer.remove(i);
                            i--;
                        }else {
                            vector_clock.decrease(message_buffer.sourece);
                        }
                    }
                }
            }
            else {
                System.out.println("Message from Process" + message.sourece + " to Process" + message.destination
                        + ", saying " + message.content + ". The vector clock is (");
                for (Map.Entry<Integer, Integer> v : message.vector_clock.getVector().entrySet()) {
                    System.out.print(v.getValue());
                }
                System.out.print(").");
                System.out.println(" is put in buffer");
                vector_clock.decrease(message.sourece);
                buffer.add(message);
            }
        }
    }

    public void broadcast(String content) throws RemoteException {
        synchronized (vector_clock) {
            vector_clock.increase(id);
            for (Map.Entry<Integer, ProcessRMI> pair : processes.entrySet()) {
                if (!pair.getKey().equals(id)) {
                    Message message = new Message(id, pair.getKey(), vector_clock, content);
                    try {
                        System.out.print("Broadcast message from Process" + message.sourece + " to Process" + message.destination
                                + ", saying " + message.content + ". The vector clock is (");
                        for (Map.Entry<Integer, Integer> v : getVector_clock().entrySet()) {
                            System.out.print(v.getValue());
                        }
                        System.out.print(").");
                        System.out.println();
                        processes.get(message.destination).receiveMessage(message);
                    } catch (RemoteException e) {
                        System.out.println("Unable to send message " + message);
                    }
                }
            }
        }
    }



    /**
     * When a new processor is generated, register it into the network
     * @return
     * @throws RuntimeException
     */
    public void register(ProcessRMI process, int id) throws RuntimeException{
        synchronized (processes){
            processes.put(id, process);
            vector_clock.iniVectorClock(id);
            System.out.println("A new processor connected to the Network, it's id is " + id);
        }
    }

    public int getId() throws RemoteException{
        return id;
    }

    public HashMap<Integer, ProcessRMI> getProcessList() throws RemoteException{
        return processes;
    }
    
    public HashMap<Integer, Integer> getVector_clock() throws RemoteException{
        return vector_clock.getVector();
    }
}