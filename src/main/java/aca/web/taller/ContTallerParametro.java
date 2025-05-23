package aca.web.taller;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.bec.spring.BecAcuerdo;
import aca.bec.spring.BecAcuerdoDao;
import aca.bec.spring.BecInforme;
import aca.bec.spring.BecInformeAlumno;
import aca.bec.spring.BecInformeAlumnoDao;
import aca.bec.spring.BecInformeDao;
import aca.bec.spring.BecParametro;
import aca.bec.spring.BecParametroDao;
import aca.bec.spring.BecPuestoAlumno;
import aca.bec.spring.BecPuestoAlumnoDao;
import aca.bec.spring.BecTipo;
import aca.bec.spring.BecTipoDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.financiero.spring.ContCcosto;
import aca.financiero.spring.ContCcostoDao;
import aca.financiero.spring.ContEjercicio;
import aca.financiero.spring.ContEjercicioDao;
import aca.financiero.spring.FesCcobroDao;
import aca.financiero.spring.SunPlusFuncionDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;

@Controller
public class ContTallerParametro {
	
	@Autowired
	private BecParametroDao becParametroDao;	
	
	
	@RequestMapping("/taller/parametro/parametro")
	public String tallerParametroParametro(HttpServletRequest request, Model modelo){
		
		BecParametro becParametro	= new BecParametro();	
		becParametro 				= becParametroDao.mapeaRegId();
		
		modelo.addAttribute("becParametro", becParametro);
		
		return "taller/parametro/parametro";
	}
	
	@RequestMapping("/taller/parametro/grabar")
	public String tallerParametroGrabar(HttpServletRequest request){
		BecParametro becParametro	= new BecParametro();
		
		String prepInicio 			= request.getParameter("PrepaInicio")==null?aca.util.Fecha.getHoy():request.getParameter("PrepaInicio");
		String prepFinal 			= request.getParameter("PrepaFinal")==null?aca.util.Fecha.getHoy():request.getParameter("PrepaFinal");
		String pregInicio 			= request.getParameter("PregradoInicio")==null?aca.util.Fecha.getHoy():request.getParameter("PregradoInicio");
		String pregFinal 			= request.getParameter("PregradoFinal")==null?aca.util.Fecha.getHoy():request.getParameter("PregradoFinal");
		
		SimpleDateFormat sdf 		= new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date datePrepIni  = sdf.parse(prepInicio, new ParsePosition(0));
		java.util.Date datePrepFin  = sdf.parse(prepFinal, new ParsePosition(0));
		
		String mensaje = "-";
		
		/* Si la fecha inicial de prepa es posterior a la final */
		if(datePrepIni.after(datePrepFin)){
			mensaje = "3";
		}
		
		java.util.Date datePregIni  = sdf.parse(pregInicio, new ParsePosition(0));
		java.util.Date datePregFin  = sdf.parse(pregFinal, new ParsePosition(0));
		
		/* Si la fecha inicial de pregrado es posterior a la final */
		if(datePregIni.after(datePregFin)){
			mensaje = "4";
		}
		if(!mensaje.equals("3") && !mensaje.equals("4")){
			becParametro.setPrepaInicio(prepInicio);
			becParametro.setPrepaFinal(prepFinal);
			becParametro.setPregradoInicio(pregInicio);
			becParametro.setPregradoFinal(pregFinal);
			if(becParametroDao.updateReg(becParametro)){				
				mensaje = "1";
			}else{
				mensaje= "2";
			} 
		}
		
		return "redirect:/taller/parametro/parametro?Mensaje="+mensaje;
	}	
	
}