package basis;

import java.io.Serializable;

public class Message implements Serializable{
    public int sourece;
    public int destination;
    public VectorClock vector_clock;
    public String content;

    public Message(int sourece, int destination, VectorClock vector_clock, String content){
        this.sourece = sourece;
        this.destination = destination;
        this.vector_clock = vector_clock;
        this.content = content;
    }
}
