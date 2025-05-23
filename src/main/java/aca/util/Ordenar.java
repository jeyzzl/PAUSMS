package aca.util;

import java.util.Comparator;
import java.io.File;

import aca.objeto.Horario;

public class Ordenar{
//ORDENAR ARCHIVOS AL SUBIR PARA ARCHIVO DIGITAL
    public OrdenarArchivosEnLote getOrdenarArchivosEnLote(){
        return new OrdenarArchivosEnLote();
    }
	class OrdenarArchivosEnLote implements Comparator<File>{
		@Override
		public int compare(File file1, File file2) {
			if(file1!=null&&file2!=null){
				int num1 = Integer.parseInt(file1.getName().substring(7).split("\\.")[0]);
				int num2 = Integer.parseInt(file2.getName().substring(7).split("\\.")[0]);
				if(num1 > num2) return 1;
		        else if(num1 < num2) return -1;
		        else return 0; 
			}
			return 0;
		}
	}
	
//ORDENAR LA LISTA DE HORARIO POR DIA Y HORA DE PERIODO
	public OrdenarHorarios getOrdenarHorarios(){
        return new OrdenarHorarios();
    }
	class OrdenarHorarios implements Comparator<Horario>{
		@Override
		public int compare(Horario horario1, Horario horario2) {
			if(horario1!=null && horario2!=null){
				int num1 = Integer.parseInt(horario1.getInicio().substring(1));
				int num2 = Integer.parseInt(horario2.getInicio().substring(1));
				if(num1 > num2) return 1;
		        else if(num1 < num2) return -1;
		        else return 0;
			}
			return 0;
		}
	}
}