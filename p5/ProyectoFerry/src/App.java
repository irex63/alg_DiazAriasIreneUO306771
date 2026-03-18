import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App {
    public static void main(String[] args){
    
        List<Integer> vehicles=new ArrayList<>(Arrays.asList(4, 3 ,5 ,2 ,2));
        int L=10;
        Ferry ferry= new Ferry(L, vehicles);
        //ferry.run();

    }
}
