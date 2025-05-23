package aca.web.utilerias;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.unav.Columna;
import aca.unav.ColumnaUmDao;
import aca.unav.ColumnaUnavDao;
import aca.unav.Tabla;
import aca.unav.TablaUmDao;
import aca.unav.TablaUnavDao;

@Controller
public class ContUtileriasBrowser {
	
	@Autowired
	TablaUnavDao tablaUnavDao;

	@Autowired
	TablaUmDao tablaUmDao;
	
	@Autowired
	ColumnaUmDao columnaUmDao;
	
	@Autowired
	ColumnaUnavDao columnaUnavDao;
	
	@RequestMapping("utilerias/browser/browser")
	public String utileriasBrowserBrowser(HttpServletRequest request){		
			
		return "utilerias/browser/browser";
	}
	
	@RequestMapping("utilerias/browser/newTablas")
	public String utileriasBrowserNewTablas(HttpServletRequest request){
//		List<Tabla> lisUnav = tablaUnavDao.lisPorUsuario("ENOC", " ORDER BY TABLE_NAME");
//		List<Tabla> lisUm 	= tablaUmDao.lisPorUsuario("ENOC", " ORDER BY TABLE_NAME");
//
//		int row=0;
//		for (Tabla tablaUm : lisUm) {
//			boolean existe = false;
//			for (Tabla tablaUnav : lisUnav) {
//				if (tablaUnav.getTableName().equals(tablaUm.getTableName())) {
//					existe = true;
//				}
//			}
//			if (existe==false) {
//				row++;
//				if (tablaUm.getTableName().contains("$")==false ) System.out.println(row+":"+tablaUm.getTableName());
//			}
//		}
			
		return "redirect:/utilerias/browser/browser";
	}
	
	@RequestMapping("utilerias/browser/newColumnas")
	public String utileriasBrowserNewColumnas(HttpServletRequest request){
//		List<Columna> lisColUm 			= columnaUmDao.lisPorUsuario("ENOC", " ORDER BY TABLE_NAME, COLUMN_NAME");
//		HashMap<String,Columna> mapUnav = columnaUnavDao.mapaPorUsuario("ENOC");
//
//		for (Columna colUm : lisColUm) {
//			String comando ="";
//			if (mapUnav.containsKey(colUm.getOwner()+colUm.getTableName()+colUm.getColumnName())==false){
//				comando = "ALTER TABLE ENOC."+colUm.getTableName()+" ADD "+colUm.getColumnName()+" ";
//				if (colUm.getDataType().contains("NUMBER") || colUm.getDataType().contains("DECIMAL"))
//					comando = comando +colUm.getDataType()+"("+colUm.getDataPrecision()+","+colUm.getDataScale()+")";
//				else if (colUm.getDataType().contains("TIMESTAMP"))
//					comando = comando+colUm.getDataType();
//				else if (colUm.getDataType().contains("DATE"))
//					comando = comando + "DATE";
//				else if (colUm.getDataType().contains("CLOB"))
//					comando = comando + "CLOB";
//				else if (colUm.getDataType().contains("BLOB"))
//					comando = comando + "BLOB";
//				else
//					comando = comando +colUm.getDataType()+"("+colUm.getDataLength()+")";
//
//				if (colUm.getDataDefault()!=null) comando = comando + " DEFAULT "+colUm.getDataDefault();
//				if (colUm.getNullable().equals("N") && colUm.getDataDefault()==null){
//					if (colUm.getDataType().contains("VARCHAR2") || colUm.getDataType().contains("CHAR") || colUm.getDataType().contains("CLOB")) comando = comando +" DEFAULT 'X'";
//					if (colUm.getDataType().contains("NUMBER") || colUm.getDataType().contains("INTEGER") || colUm.getDataType().contains("INT")) comando = comando +" DEFAULT 0";
//					if (colUm.getDataType().contains("DATE") || colUm.getDataType().contains("TIMESTAMP")) comando = comando +" DEFAULT SYSDATE";
//				}
//				if (colUm.getNullable().equals("N")) comando = comando + " NOT NULL";
//				comando = comando +";";
//
//				System.out.println(comando);
//			}
//		}
			
		return "redirect:/utilerias/browser/browser";
	}
	
}