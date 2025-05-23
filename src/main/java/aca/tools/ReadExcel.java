/* Solamente funciona para archivos de excel con extención xls*/
package aca.tools;
import java.util.*;
import jxl.*;
import java.io.*; 

public class ReadExcel {
	
	private void leerArchivoExcel(String archivoDestino) { 

		try {
			File file = new File (archivoDestino);
			FileInputStream inputStream = new FileInputStream(file);
			Workbook archivoExcel = Workbook.getWorkbook(inputStream);
			//Workbook archivoExcel = Workbook.getWorkbook(new File(archivoDestino)); 
			//System.out.println("Número de Hojas\t"+ archivoExcel.getNumberOfSheets());
			ArrayList<String> matricula = new ArrayList<String>();
			
			// Recorre cada hoja
			for (int sheetNo = 0; sheetNo < archivoExcel.getNumberOfSheets(); sheetNo++){ 
				Sheet hoja = archivoExcel.getSheet(sheetNo); 
				int numColumnas = hoja.getColumns(); 
				int numFilas = hoja.getRows(); 
				String data; 
			//	System.out.println("Nombre de la Hoja\t"+ archivoExcel.getSheet(sheetNo).getName());
				
				// Recorre cada fila de la hoja
				for (int fila = 0; fila < numFilas; fila++) {  
					// Recorre cada columna de la fila
					for (int columna = 0; columna < numColumnas; columna++){
						data = hoja.getCell(columna, fila).getContents(); 
						System.out.print(data + " ");
					} 
					System.out.println("\n"); 
				}
			/*	for (int columna = 0; columna < numColumnas; columna++){
					for (int fila = 0; fila < numFilas; fila++) {
						data = hoja.getCell(columna, fila).getContents(); 
						if(!matricula.contains(data)){
							matricula.add(data);
						}
						
						//System.out.print(data + " ");
					}
					//System.out.println("\n");
				}*/
				System.out.println(matricula);
			} 
		}catch (FileNotFoundException e) {
		    e.printStackTrace();
		}catch (Exception e) { 
			e.printStackTrace(); 
		}
	} 

	public static void main(String arg[]) { 
		ReadExcel excel = new ReadExcel(); 
		excel.leerArchivoExcel("C:/Users/Alvin/Desktop/prueba.xls"); 
	}
	
}
