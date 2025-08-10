import java.util.ArrayList;
import java.util.List;
public class prueba{
    public static void main (String[] args){
        String ip = "6,30/2,3";
		ip += ".";
		List<Integer> ip_ordenada = new ArrayList<>();
		String flota = "";
        for (int a = 0; a < ip.length(); a++){
            char cosa = ip.charAt(a);
			String s = Character.toString(cosa);
			if(s.equals(".") || s.equals("/") || s.equals(",")){
				int flota_2 = Integer.parseInt(flota);
				ip_ordenada.add(flota_2);
				flota = "";
				continue;
			}else{
				flota += cosa;
			}
		}
		System.out.println(ip_ordenada);
        
    }
}
