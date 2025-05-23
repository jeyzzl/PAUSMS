package aca.util;

public class NumberToLetter {
	private int flag;
	public int numero;
	public String importe_parcial;
	public String num;
	public String num_letra;
	public String num_letras;
	public String num_letram;
	public String num_letradm;
	public String num_letracm;
	public String num_letramm;
	public String num_letradmm;
	
	public NumberToLetter(){
		numero = 0;
		flag=0;
	}
	
	public NumberToLetter(int n){
		numero = n;
		flag=0;
	}

	
	private String unidad(int numero){
		
		switch (numero){
		case 9:
				num = "nueve";
				break;
		case 8:
				num = "ocho";
				break;
		case 7:
				num = "siete";
				break;
		case 6:
				num = "seis";
				break;
		case 5:
				num = "cinco";
				break;
		case 4:
				num = "cuatro";
				break;
		case 3:
				num = "tres";
				break;
		case 2:
				num = "dos";
				break;
		case 1:
				if (flag == 0)
					num = "uno";
				else 
					num = "un";
				break;
		case 0:
				num = "";
				break;
		}
		return num;
	}
	
	private String decena(int numero){
	
		if (numero >= 90 && numero <= 99)
		{
			num_letra = "noventa ";
			if (numero > 90)
				num_letra = num_letra.concat("y ").concat(unidad(numero - 90));
		}
		else if (numero >= 80 && numero <= 89)
		{
			num_letra = "ochenta ";
			if (numero > 80)
				num_letra = num_letra.concat("y ").concat(unidad(numero - 80));
		}
		else if (numero >= 70 && numero <= 79)
		{
			num_letra = "setenta ";
			if (numero > 70)
				num_letra = num_letra.concat("y ").concat(unidad(numero - 70));
		}
		else if (numero >= 60 && numero <= 69)
		{
			num_letra = "sesenta ";
			if (numero > 60)
				num_letra = num_letra.concat("y ").concat(unidad(numero - 60));
		}
		else if (numero >= 50 && numero <= 59)
		{
			num_letra = "cincuenta ";
			if (numero > 50)
				num_letra = num_letra.concat("y ").concat(unidad(numero - 50));
		}
		else if (numero >= 40 && numero <= 49)
		{
			num_letra = "cuarenta ";
			if (numero > 40)
				num_letra = num_letra.concat("y ").concat(unidad(numero - 40));
		}
		else if (numero >= 30 && numero <= 39)
		{
			num_letra = "treinta ";
			if (numero > 30)
				num_letra = num_letra.concat("y ").concat(unidad(numero - 30));
		}
		else if (numero >= 20 && numero <= 29)
		{
			if (numero == 20)
				num_letra = "veinte ";
			else
				num_letra = "veinti".concat(unidad(numero - 20));
		}
		else if (numero >= 10 && numero <= 19)
		{
			switch (numero){
			case 10:

				num_letra = "diez ";
				break;

			case 11:

				num_letra = "once ";
				break;

			case 12:

				num_letra = "doce ";
				break;

			case 13:

				num_letra = "trece ";
				break;

			case 14:

				num_letra = "catorce ";
				break;

			case 15:
			
				num_letra = "quince ";
				break;
			
			case 16:
			
				num_letra = "dieciseis ";
				break;
			
			case 17:
			
				num_letra = "diecisiete ";
				break;
			
			case 18:
			
				num_letra = "dieciocho ";
				break;
			
			case 19:
			
				num_letra = "diecinueve ";
				break;
			
			}	
		}
		else
			num_letra = unidad(numero);

	return num_letra;
	}	

	private String centena(int numero){
		if (numero >= 100)
		{
			if (numero >= 900 && numero <= 999)
			{
				num_letra = "novecientos ";
				if (numero > 900)
					num_letra = num_letra.concat(decena(numero - 900));
			}
			else if (numero >= 800 && numero <= 899)
			{
				num_letra = "ochocientos ";
				if (numero > 800)
					num_letra = num_letra.concat(decena(numero - 800));
			}
			else if (numero >= 700 && numero <= 799)
			{
				num_letra = "setecientos ";
				if (numero > 700)
					num_letra = num_letra.concat(decena(numero - 700));
			}
			else if (numero >= 600 && numero <= 699)
			{
				num_letra = "seiscientos ";
				if (numero > 600)
					num_letra = num_letra.concat(decena(numero - 600));
			}
			else if (numero >= 500 && numero <= 599)
			{
				num_letra = "quinientos ";
				if (numero > 500)
					num_letra = num_letra.concat(decena(numero - 500));
			}
			else if (numero >= 400 && numero <= 499)
			{
				num_letra = "cuatrocientos ";
				if (numero > 400)
					num_letra = num_letra.concat(decena(numero - 400));
			}
			else if (numero >= 300 && numero <= 399)
			{
				num_letra = "trescientos ";
				if (numero > 300)
					num_letra = num_letra.concat(decena(numero - 300));
			}
			else if (numero >= 200 && numero <= 299)
			{
				num_letra = "doscientos ";
				if (numero > 200)
					num_letra = num_letra.concat(decena(numero - 200));
			}
			else if (numero >= 100 && numero <= 199)
			{
				if (numero == 100)
					num_letra = "cien ";
				else
					num_letra = "ciento ".concat(decena(numero - 100));
			}
		}
		else
			num_letra = decena(numero);
		
		return num_letra;	
	}	

	private String miles(int numero){
		if (numero >= 1000 && numero <2000){
			num_letram = ("mil ").concat(centena(numero%1000));
		}
		if (numero >= 2000 && numero <10000){
			flag=1;
			num_letram = unidad(numero/1000).concat(" mil ").concat(centena(numero%1000));
		}
		if (numero < 1000)
			num_letram = centena(numero);
		
		return num_letram;
	}		

	private String decmiles(int numero){
		if (numero == 10000)
			num_letradm = "diez mil";
		if (numero > 10000 && numero <20000){
			flag=1;
			num_letradm = decena(numero/1000).concat("mil ").concat(centena(numero%1000));		
		}
		if (numero >= 20000 && numero <100000){
			flag=1;
			num_letradm = decena(numero/1000).concat(" mil ").concat(miles(numero%1000));		
		}
		
		
		if (numero < 10000)
			num_letradm = miles(numero);
		
		return num_letradm;
	}		

	private String cienmiles(int numero){
		if (numero == 100000)
			num_letracm = "cien mil";
		if (numero >= 100000 && numero <1000000){
			flag=1;
			num_letracm = centena(numero/1000).concat(" mil ").concat(centena(numero%1000));		
		}
		if (numero < 100000)
			num_letracm = decmiles(numero);
		return num_letracm;
	}		

	private String millon(int numero){
		if (numero >= 1000000 && numero <2000000){
			flag=1;
			num_letramm = ("Un millon ").concat(cienmiles(numero%1000000));
		}
		if (numero >= 2000000 && numero <10000000){
			flag=1;
			num_letramm = unidad(numero/1000000).concat(" millones ").concat(cienmiles(numero%1000000));
		}
		if (numero < 1000000)
			num_letramm = cienmiles(numero);
		
		return num_letramm;
	}		
	
	private String decmillon(int numero){
		if (numero == 10000000)
			num_letradmm = "diez millones";
		if (numero > 10000000 && numero <20000000){
			flag=1;
			num_letradmm = decena(numero/1000000).concat("millones ").concat(cienmiles(numero%1000000));		
		}
		if (numero >= 20000000 && numero <100000000){
			flag=1;
			num_letradmm = decena(numero/1000000).concat(" milllones ").concat(millon(numero%1000000));		
		}
		
		
		if (numero < 10000000)
			num_letradmm = millon(numero);
		
		return num_letradmm;
	}
	
	public static String convertirLetras(int numero){
		NumberToLetter n2t = new NumberToLetter();
		String num_letras = n2t.decmillon(numero);
		if(numero == 0)
			num_letras = "cero";
		return num_letras;
	} 	
	
	public static String convertirLetras2(String numero){
		String titulo = "";
		
		switch(Integer.parseInt(numero)){
			case 1:  titulo = "PRIMER"; 		break;
			case 2:  titulo = "SEGUNDO"; 		break;
			case 3:  titulo = "TERCER"; 		break;
			case 4:  titulo = "CUARTO";			break;
			case 5:  titulo = "QUINTO";			break;
			case 6:  titulo = "SEXTO"; 			break;
			case 7:	 titulo = "SEPTIMO";		break;
			case 8:	 titulo = "OCTAVO";			break;
			case 9:	 titulo = "NOVENO";			break;
			case 10: titulo = "DECIMO";			break;
			case 11: titulo = "UNDECIMO";		break;
			case 12: titulo = "DUODECIMO";		break;
			case 13: titulo = "DECIMO TERCERO";	break;
			case 14: titulo = "DECIMO CUARTO";	break;
			case 15: titulo = "DECIMO QUINTO";	break;
			case 16: titulo = "DECIMO SEXTO";	break;
			case 17: titulo = "DECIMO SEPTIMO";	break;
			case 18: titulo = "DECIMO OCTAVO";	break;
			case 19: titulo = "DECIMO NOVENO";	break;
			case 20: titulo = "VIGESIMO";		break;
		}
		return titulo;
	} 
	
	public static String convertirYear(int numero){
		String year = "-";		
		switch (numero){
			case 2018: year = "dos mil diesiocho";	break;
			case 2019: year = "dos mil diesinueve";	break;
			case 2020: year = "dos mil veinte";	break;
			case 2021: year = "dos mil veintiuno"; break;
			case 2022: year = "dos mil veintidós"; break;
			case 2023: year = "dos mil veintitrés"; break;
			case 2024: year = "dos mil veinticuatro"; break;
			case 2025: year = "dos mil veinticinco"; break;		
			case 2026: year = "dos mil veintiseis"; break;		
			case 2027: year = "dos mil veintisiete"; break;
		}
		return year;
	}
	
    public static boolean isNumeric(String cadena){
    	try {
    		Integer.parseInt(cadena);
    		return true;
    	} catch (NumberFormatException nfe){
    		return false;
    	}
    }	
}