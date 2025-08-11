import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.Comparator;
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
        for(int a = 0; a < requ.size()-1; a+=2){
            List<Integer> es = new ArrayList<>(Arrays.asList(requ.get(a), requ.get(a+1)));
            requisitos.add(es);
		}
        // Acomodar los requisitos de mayor a menor segun los hosts.
		requisitos.sort(Comparator.comparingInt((List<Integer> list) -> list.get(1)).reversed());
		System.out.println(requisitos);

		// rangos de octetos para usarlos en varios calculos.
		List<Integer> octeto_2 = IntStream.range(9, 17).boxed().collect(Collectors.toList());// 9-16
		List<Integer> octeto_3 = IntStream.range(17, 25).boxed().collect(Collectors.toList());// 17-24
		List<Integer> octeto_4 = IntStream.range(25, 33).boxed().collect(Collectors.toList());// 25-32

		// Encontrar 2^n >= #host con cada requisito.
		for(int a = 0; a < requisitos.size(); a++){
			int req_subred = requisitos.get(a).get(0);
			int req_host = requisitos.get(a).get(1);
			int n = 0;
			int potencia;
			while(true){
				potencia = (int) Math.pow(2, n);
				if(potencia >= req_host){
					System.out.println(potencia);
					
					break;
				}else{
					n += 1;
				}
			}

			// Encontrar el nuevo prefijo y octeto de cambio(desde el 2, el primer octeto no se ocupa).
			int nuevo_prefijo = 32 - n;
			int octeto_cambio = 0;
			if(octeto_2.contains(nuevo_prefijo)){
				octeto_cambio = 2;
			}else if(octeto_3.contains(nuevo_prefijo)){
				octeto_cambio = 3;
			}else if(octeto_4.contains(nuevo_prefijo)){
				octeto_cambio = 4;
			}

			// Saltos
			int saltos = 0;
			if(octeto_cambio == 2){
				int op2 = n - 16;
				saltos = (int) Math.pow(2, op2);
			}else if(octeto_cambio == 3){
				int op3 = n - 8;
				saltos = (int) Math.pow(2, op3);
			}else if(octeto_cambio == 4){
				saltos = potencia;
			}
			
			

			// nueva mascara ocupando los rangos
			List<Integer> nueva_mascara = new ArrayList<>();;
			if(octeto_cambio == 2){
				nueva_mascara = new ArrayList<>(Arrays.asList(255, 256-saltos, 0, 0)); 
			}else if(octeto_cambio == 3){
				nueva_mascara = new ArrayList<>(Arrays.asList(255, 255, 256-saltos, 0));
			}else if(octeto_cambio == 4){
				if(n > 2){
					nueva_mascara = new ArrayList<>(Arrays.asList(255, 255, 255, 256-saltos));
				}else{
					saltos = 4;
					nuevo_prefijo = 30;
					nueva_mascara = new ArrayList<>(Arrays.asList(255, 255, 255, 252));
				}
				
			}
			System.out.println("Octeto de cambio: " + octeto_cambio);
			System.out.println(nueva_mascara+"/"+nuevo_prefijo);
			
			// mostrar los saltos en el octeto de cambio.
			// System.out.println(ip);
			if(octeto_cambio == 2){
				for(int i = 0; i < req_subred; i++){
					System.out.println(ip);
					ip.set(1, ip.get(1) + saltos);
					
				}
			}else if(octeto_cambio == 3){
				for(int i = 0; i < req_subred; i++){
					System.out.println(ip);
					ip.set(2, ip.get(2) + saltos);
					
				}
			}else if(octeto_cambio == 4){
				for(int i = 0; i < req_subred; i++){
					
					if(ip.get(3) < 256){
						System.out.println(ip);
						
						ip.set(3, ip.get(3) + saltos);
						
					}else{
						ip.set(3, 0);
						int suma = ip.get(2) + 1;
						ip.set(2, suma);
						
						//System.out.println(ip);
						
					}
				}
			}
		}









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
			System.out.println("0-salir\n1-calcular vlsm\n");
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
			}
		}while(sel != 0);
		System.out.println("Gracias por usar la calculadora");
	}
}
