package process1;

import java.io.Serializable;

public class Message implements Serializable{
    public int sourece;
    public int destination;
    public int content;

    public Message(int sourece, int destination, int content){
        this.sourece = sourece;
        this.destination = destination;
        this.content = content;
    }
}
