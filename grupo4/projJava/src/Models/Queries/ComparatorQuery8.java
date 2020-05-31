package Models.Queries;
import java.io.Serializable;
import java.util.Comparator;


public class ComparatorQuery8 implements Comparator<ParQuery8>, Serializable {

    public int compare (ParQuery8 s1 , ParQuery8 s2){
        if (s1.getQuant() > s2.getQuant())
            return -1;
        else if (s2.getQuant() > s1.getQuant())
            return 1;
        return s1.getCliente().compareTo(s2.getCliente());
    }
}