package basis;

import java.io.Serializable;
import java.util.HashMap;

public class VectorClock implements Serializable{
    private HashMap<Integer, Integer> vector;

    public VectorClock(){
        vector = new HashMap<>();
    }

    public VectorClock(VectorClock vectorClock)
    {
        vector = new HashMap<>(vectorClock.vector);
    }

    synchronized public void reset(){
        vector.clear();
    }

    synchronized public void iniVectorClock(int id){
        vector.put(id, 0);
    }

    synchronized public int get(int index){
        try {
            return vector.get(index).intValue();
        } catch (NullPointerException e){
            return 0;
        }
    }

    synchronized public void increase(int index){
        vector.put(index, get(index) + 1);
    }

    synchronized public void decrease(int index){
        vector.put(index, get(index) - 1);
    }

    synchronized public boolean greaterEqual(VectorClock another_vector_clock){
        for (int key: another_vector_clock.vector.keySet()){
            if (get(key) < another_vector_clock.get(key)){
                return false;
            }
        }
        return true;
    }

    synchronized public HashMap<Integer, Integer> getVector(){
        return vector;
    }
}