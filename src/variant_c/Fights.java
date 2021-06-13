package variant_c;

import java.util.List;
import java.util.concurrent.RecursiveTask;

public class Fights extends RecursiveTask<Monk> {

    private final int start;
    private final int end;
    List<Monk> monks;

    public Fights(int start, int end, List<Monk> monks) {
        this.start = start;
        this.end = end;
        this.monks = monks;

    }
    @Override
    protected Monk compute() {
        if (end - start < 3) {
            //System.out.println(start + " " + end);
            //System.out.println(start + " " + monks.get(start).power);

            return maxPower(monks.get(start), monks.get(start+1));
        }

        int middle = (start + end) / 2;

        Fights left = new Fights(start, middle, monks);
        Fights right = new Fights(middle+1, end, monks);

        left.fork();
        right.fork();
        Monk q = (Monk)right.join();
        Monk w = (Monk) left.join();

        //System.out.println(q.power + " + " + w.power);
        return maxPower(q, w);
    }
    public Monk maxPower(Monk monk1, Monk monk2) {

        if(monk1.power > monk2.power) {
            synchronized (monk1){
                if(monk1.temple != monk2.temple) {
                    monk1.addVictory();
                }
                //System.out.println(monk1.temple + " & " + monk2.temple + " + " + monk1.countOfVictory);
            }
            return monk1;
        }
        else{
            synchronized (monk2){
                if(monk1.temple != monk2.temple) {
                    monk2.addVictory();
                }
                ///System.out.println(monk1.temple + " & " + monk2.temple + " + " + monk1.countOfVictory);
            }
            return monk2;
        }
    }
}