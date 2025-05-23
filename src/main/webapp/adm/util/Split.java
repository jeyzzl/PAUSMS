package adm.util;

public class Split {
	
	public static void main(String[] args) {		
		try{
			String correos = "etorres@um.edu.mx";
			String[] to = null;			
			String[] arreglo = correos.split(",");
			System.out.println( arreglo.length );
			for(int i=0;i<arreglo.length;i++){
				System.out.println("Valores:"+arreglo[i]);
			}	
		}catch(Exception e){
			System.out.println(e);
		}

	}

}
