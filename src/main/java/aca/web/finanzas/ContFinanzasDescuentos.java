package aca.web.finanzas;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import aca.alumno.spring.AlumDescuento;
import aca.alumno.spring.AlumDescuentoDao;
import aca.alumno.spring.AlumPersonalDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatDescuento;
import aca.catalogo.spring.CatDescuentoDao;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;

@Controller
public class ContFinanzasDescuentos {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired	
	private CatPeriodoDao catPeriodoDao;
	
	@Autowired	
	private CargaDao cargaDao;
	
	@Autowired
	AlumDescuentoDao alumDescuentoDao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	@Autowired
	CatDescuentoDao catDescuentoDao;
	
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@ResponseBody
	@RequestMapping("/finanzas/descuentos/getNombreAlumno")
	public String finanzasDescuentosGetNombreAlumno(HttpServletRequest request){
		//enviarConEnoc(request,"Error-aca.ControllerFinanzas|finanzasDescuentosGetNombreAlumno");
		String matricula 	= request.getParameter("matricula")==null?"-":request.getParameter("matricula");		
		String nombre 		= alumPersonalDao.getNombreAlumno(matricula, "NOMBRE");		
		if(nombre.equals("0000000"))nombre = "Numero de Matrícula No Válido";
		
		return nombre;
	}
	
	@RequestMapping("/finanzas/descuentos/guardar")
	public String finanzasDescuentosGuardar(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ControllerFinanzas|finanzasDescuentosGuardar");
		return "finanzas/descuentos/guardar";
	}
	
	@RequestMapping("/finanzas/descuentos/accion")
	public String finanzasDescuentosAccion(HttpServletRequest request, Model modelo){
		
		String codigoPersonal 	= "0";
		String codigoAlumno		= request.getParameter("CodigoAlumno") ==null?"0":request.getParameter("CodigoAlumno");
		String descuentoId 		= request.getParameter("DescuentoId") ==null?"0":request.getParameter("DescuentoId");
		String cargaId 			= request.getParameter("CargaId") ==null?"0":request.getParameter("CargaId");
		String accion 		 	= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoPersonal		= (String)sesion.getAttribute("codigoPersonal");
        }        
		AlumDescuento alumDescuento = new AlumDescuento();
		boolean existe = false;
		String alumnoNombre = "-";
		if(accion.equals("2")){		
			alumDescuento 	= alumDescuentoDao.mapeaRegId(codigoAlumno, cargaId, descuentoId);
			alumnoNombre 	=alumPersonalDao.getNombreAlumno(alumDescuento.getCodigoPersonal(), "NOMBRE"); 
			existe= true;
		}
		
		List<CatDescuento> lisDescuentos = catDescuentoDao.getDescuentoUsuario(codigoPersonal, "ORDER BY DESCUENTO_ID");
		
		modelo.addAttribute("existe", existe);
		modelo.addAttribute("alumnoNombre", alumnoNombre);
		modelo.addAttribute("alumDescuento", alumDescuento);
		
		modelo.addAttribute("lisDescuentos", lisDescuentos);
		
		return "finanzas/descuentos/accion";
	}
	
	@RequestMapping("/finanzas/descuentos/alumnos")
	public String finanzasDescuentosAlumnos(HttpServletRequest request, Model modelo){	
		
		String periodoId 		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String cargaId 			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");		
		String cargaSesion		= "0";
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	if (!periodoId.equals("0")){
        		sesion.setAttribute("periodo", periodoId);        	
        	}else {
        		periodoId = (String)sesion.getAttribute("periodo"); 
        	}
        	cargaSesion 		= (String)sesion.getAttribute("cargaId"); 
        	if (cargaId.equals("0")) cargaId = cargaSesion;
        }		
		List<CatPeriodo> lisPeriodos	= catPeriodoDao.getListAll(" ORDER BY PERIODO_ID DESC");
		boolean existePeriodo = false;
		for (CatPeriodo periodo : lisPeriodos) {
			if (periodo.getPeriodoId().equals(periodoId)) existePeriodo = true;
		}
		if (existePeriodo==false && lisPeriodos.size()>=1) periodoId = lisPeriodos.get(0).getPeriodoId();
		
		List<Carga> lisCargas 			= cargaDao.getListCargaPeriodo(periodoId, "ORDER BY CARGA_ID");
		boolean existeCarga 	= false;
		for (Carga carga : lisCargas) {
			if (carga.getCargaId().equals(cargaId)) existeCarga = true;
		}
		if (existeCarga && sesion!=null ) {
			sesion.setAttribute("cargaId", cargaId);
		}else if (lisCargas.size() >= 1){
			cargaId = lisCargas.get(0).getCargaId();
		}
		
		List<AlumDescuento> lisDescuentos	= alumDescuentoDao.getAllCarga(cargaId, "ORDER BY DESCUENTO_ID");
		
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisDescuentos", lisDescuentos);
		
		return "finanzas/descuentos/alumnos";
	}
	
	@RequestMapping("/finanzas/descuentos/borrar")
	public String finanzasDescuentosBorrar(HttpServletRequest request){
		
		String codigoAlumno = request.getParameter("CodigoAlumno") ==null?"0":request.getParameter("CodigoAlumno");
		String descuentoId 	= request.getParameter("DescuentoId") ==null?"0":request.getParameter("DescuentoId");
		String cargaId 		= request.getParameter("CargaId") ==null?"0":request.getParameter("CargaId");
		String mensaje 		= "-";
		
		if( alumDescuentoDao.existeReg( codigoAlumno, cargaId, descuentoId)){
			if(alumDescuentoDao.deleteReg(codigoAlumno, cargaId, descuentoId)){
				mensaje = "<div class='alert alert-success'>Registro Eliminado</div>";				
			}else{
				mensaje = "<div class='alert alert-danger'>Ocurrió un error al eliminar el registro</div>";
			}
		}		
		return "redirect:/finanzas/descuentos/alumnos?Mensaje="+mensaje;
	}
	
	@RequestMapping("/finanzas/descuentos/contratoPDF")
	public String finanzasDescuentosContratoPDF(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ControllerFinanzas|finanzasDescuentosContratoPDF");
		return "finanzas/descuentos/contratoPDF";
	}
	
}