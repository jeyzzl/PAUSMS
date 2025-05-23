package aca.web.administrar;

import java.io.IOException;
import java.text.Format;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import aca.catalogo.spring.CatEstado;
import aca.catalogo.spring.CatEstadoDao;
import aca.catalogo.spring.CatPais;
import aca.catalogo.spring.CatPaisDao;
import aca.sep.spring.SepAlumno;
import aca.sep.spring.SepAlumnoDao;

@Controller
public class ContAdministrarSe {
	
	@Autowired
	SepAlumnoDao sepAlumnoDao;
	
	@Autowired
	CatPaisDao catPaisDao;
	
	@Autowired
	CatEstadoDao catEstadoDao;
	
	@RequestMapping("/administrar/se/estadistica")
	public String administrarSeEstadistica(HttpServletRequest request, Model modelo){
		List<aca.Mapa> lista = sepAlumnoDao.listFechaSubio();
		
		boolean borro = request.getParameter("Borro") == null ? false : true;
		
		modelo.addAttribute("lista", lista);
		modelo.addAttribute("borro", borro);
		
		return "administrar/se/estadistica";
	}

	@RequestMapping("/administrar/se/cargarArchivo")
	public String administrarSeCargarArchivo(HttpServletRequest request, Model modelo){
		
		return "administrar/se/cargarArchivo";
	}

	@RequestMapping("/administrar/se/borrarRegistros")
	public String administrarSeBorrarRegistros(HttpServletRequest request){
		String fecha = request.getParameter("Fecha");
		
		String[]array = fecha.split("/");
		
		fecha = array[2]+"/"+array[1]+"/"+array[0];
		
		sepAlumnoDao.deleteRegPorFecha(fecha);
		
		return "redirect:/administrar/se/estadistica?Borro=Si";
	}

	@RequestMapping("/administrar/se/leerExcel")
	public String administrarSeLeerExcel(@RequestParam("archivo") MultipartFile archivo, HttpServletRequest request ) throws IOException{		
		int grabados	= 0;
		int errores 	= 0;
		
		Workbook workbook = new XSSFWorkbook(archivo.getInputStream());
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = firstSheet.iterator();
	    
	    ArrayList<SepAlumno> listaAlumnos 	= new ArrayList<SepAlumno>();
	       
	    String datos = "";   
	    DataFormatter formatter = new DataFormatter();
	    
	    while (iterator.hasNext()) {
	    	SepAlumno alumno = new SepAlumno();
	    	   
	    	Row nextRow = (Row) iterator.next();
	    	Iterator<Cell> cellIterator = nextRow.cellIterator();
	    	
	    	while(cellIterator.hasNext()) {
	    		Cell cell = (Cell) cellIterator.next();	    		
	    		String contenidoCelda = formatter.formatCellValue(cell);
	    		datos += contenidoCelda+"#";
	    	}	
	    	
	    	String[]array 	= datos.split("#");     
	    	String fecha 	= array[7];
	    	//System.out.println("Fecha:"+fecha);
	    	String[]arrayFecha = fecha.split("/");	    	
	    	String year = arrayFecha[2];
	    	if (year.length()==2) {
	    		year = "20"+year;
	    	}	    	
	    	
	    	fecha = arrayFecha[1]+"/"+arrayFecha[0]+"/"+year;	    	
	    	alumno.setPlantel(array[0]);
	    	alumno.setPlanSep(array[1]);
	    	alumno.setCiclo(array[2]);
	    	alumno.setCurp(array[3]);
	    	alumno.setNombre(array[4]);
	    	alumno.setPaterno(array[5]);
	    	alumno.setMaterno(array[6]);
	    	alumno.setFecha(fecha);
	    	alumno.setCodigoPersonal(array[8]);
	    	alumno.setPlanUm(array[9]);
	    	alumno.setGenero(array[10]);
	    	alumno.setEdad(array[11]);
	       	alumno.setGrado(array[12]);
	       	alumno.setPaisId(array[13]);
	       	alumno.setEstadoId(array[14]);
	       	alumno.setPrepaLugar(array[15]);
	       	alumno.setUsado(array[16]);
	       	/*
	       	System.out.println(alumno.getPlantel()+":"+alumno.getPlanSep()+":"+alumno.getCiclo()+":"+alumno.getCurp()+":"+alumno.getNombre()+":"+alumno.getPaterno()+":"+alumno.getMaterno()+":"+
	       	alumno.getFecha()+":"+alumno.getCodigoPersonal()+":"+alumno.getPlanUm()+":"+alumno.getGenero()+":"+
	       	alumno.getEdad()+":"+alumno.getGrado()+":"+alumno.getPaisId()+":"+alumno.getEstadoId()+":"+alumno.getPrepaLugar()+":"+
	       	alumno.getUsado());	       	
	       */
	       	listaAlumnos.add(alumno);
	       	datos = "";
       }
       for(SepAlumno alumno : listaAlumnos) {   
    	  alumno.setFolio(sepAlumnoDao.maximoReg());
    	  if (sepAlumnoDao.insertReg(alumno)) {
    		  grabados++;
    	  }else {
    		  errores++;
    	  }
       }
       
       workbook.close();

       return "redirect:/administrar/se/cargarArchivo?Grabados="+grabados+"&Errores="+errores;
	}
	
	@RequestMapping("/administrar/se/lista")
	public String administrarSeLista(HttpServletRequest request, Model modelo){
		
		String fecha = request.getParameter("Fecha")==null?aca.util.Fecha.getHoy():request.getParameter("Fecha");
		
		List<SepAlumno> lisAlumnos 			= sepAlumnoDao.lisPorFecha(fecha, " ORDER BY PATERNO, MATERNO, NOMBRE");
		HashMap<String, CatPais> mapaPaises = catPaisDao.getMapAll();
		HashMap<String, CatEstado> mapaEstados = catEstadoDao.getMapAll();
		
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapaPaises", mapaPaises);
		modelo.addAttribute("mapaEstados", mapaEstados);
		
		return "administrar/se/lista";
	}

}
