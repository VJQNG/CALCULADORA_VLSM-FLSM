import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class RedRequisitos{
	String ip;
	int prefijo;
	String requisitos;
	List<Integer> ip_ordenada;
	List<Integer> req_or;

	public RedRequisitos(String ip, int prefijo, String requisitos){
		this.ip = ip;
		this.prefijo = prefijo;
		this.requisitos = requisitos;
	}
	public int m_prefijo(){
		return prefijo;
	}

	public List<Integer> m_requisitos(){
		req_or = ordenar_ip_req(requisitos);
		return req_or;
	}

	public List<Integer> m_ip(){
		ip_ordenada = ordenar_ip_req(ip);
		return ip_ordenada;
	}
	
	public List<Integer> ordenar_ip_req(String ip_t){
		// acomodar la ip o los requisitos en una lista.
        ip_t += ".";
        List<Integer> ip_ordenada = new ArrayList<>();
        String flota = "";
        for (int a = 0; a < ip_t.length(); a++){
            char cosa = ip_t.charAt(a);
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
		return ip_ordenada;
	}

	
}


class ventana_vlsm{
	public void calcular(RedRequisitos req){
		// Obtener requisitos acomodandolos en listas de listas por separado.
		List<Integer> ip = req.m_ip();
		int prefijo = req.m_prefijo();
		List<Integer> requ = req.m_requisitos();
		List<List<Integer>> requisitos = new ArrayList<>();
        for(int a = 0; a < requ.size(); a+=2){
            List<Integer> es = new ArrayList<>(Arrays.asList(requ.get(a), requ.get(a+1)));
            requisitos.add(es);
		}
        System.out.println(requisitos);
		System.out.println("");
	}
}

class ventana_flsm{
	public void calcular(RedRequisitos requerimientos){
		System.out.println("flsmiubgd");
	}
}


public class main{
	public static void main(String[] args){
		Scanner entra = new Scanner(System.in);
		//RedRequisitos p1 = new RedRequisitos("192.168.32.0", 24, "6,30/3,6/1,2");
		String ip;
		int prefijo;
		String requisitos;
		int sel;
		do{
			System.out.println("0-salir\n1-calcular vlsm\n2-calcular flsm");
			sel = entra.nextInt();
			switch (sel){
				case 1:
					System.out.println("Ingresando datos...");
					System.out.println("IP(A.B.C.D): ");
					ip = entra.next();
					entra.nextLine();
					System.out.println("PREFIJO(#): ");
					prefijo = entra.nextInt();
					entra.nextLine();
					System.out.println("REQUISITOS(#subredes,#host/#subred,#host): ");
					requisitos = entra.next();
					RedRequisitos VLSM = new RedRequisitos(ip, prefijo, requisitos);
					ventana_vlsm operar = new ventana_vlsm();
					operar.calcular(VLSM);
					
					
					break;
				case 2:
					System.out.println("FLSM");
					System.out.println("Ingresando datos...");
					System.out.println("IP(A.B.C.D): ");
					ip = entra.next();
					entra.nextLine();
					System.out.println("PREFIJO(#): ");
					prefijo = entra.nextInt();
					entra.nextLine();
					System.out.println("REQUISITOS(#subredes): ");
					requisitos = entra.next();
					RedRequisitos FLSM = new RedRequisitos(ip, prefijo, requisitos);
					
					break;
			}
			

		}while(sel != 0);
	}
}
