package aca.web.taller;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumPersonalDao;
import aca.bec.spring.BecAcceso;
import aca.bec.spring.BecAccesoDao;
import aca.bec.spring.BecAcuerdo;
import aca.bec.spring.BecAcuerdoDao;
import aca.bec.spring.BecTipo;
import aca.bec.spring.BecTipoDao;
import aca.catalogo.spring.CatTipoAlumnoDao;
import aca.financiero.spring.ContCcosto;
import aca.financiero.spring.ContCcostoDao;
import aca.financiero.spring.ContEjercicio;
import aca.financiero.spring.ContEjercicioDao;
import aca.vista.spring.InscritosDao;
import aca.vista.spring.UsuariosDao;

@Controller
public class ContTallerAcuerdo {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	private ContEjercicioDao conEjercicioDao;
	
	@Autowired
	private ContCcostoDao conCcostoDao;
	
	@Autowired
	private BecTipoDao becTipoDao;
	
	@Autowired
	private BecAccesoDao becAccesoDao;
	
	@Autowired
	private InscritosDao inscritosDao;
	
	@Autowired
	private AccesoDao accesoDao;
	
	@Autowired
	private BecAcuerdoDao becAcuerdoDao;
	
	@Autowired
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired
	private AlumAcademicoDao alumAcademicoDao;
	
	@Autowired
	private UsuariosDao usuariosDao;
	
	@Autowired
	private CatTipoAlumnoDao catTipoAlumnoDao;
	
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	} 
	
	@RequestMapping("/taller/acuerdo/getNombreAlumno")
	@ResponseBody
	public String tallerAcuerdoGetNombreAlumno(HttpServletRequest request){
		String matricula 	= request.getParameter("matricula")==null?"0":request.getParameter("matricula");
		String nombreAlumno = alumPersonalDao.getNombreAlumno(matricula, "NOMBRE");
		return nombreAlumno;
	}	
	
	@RequestMapping("/taller/acuerdo/acuerdo")
	public String tallerAcuerdoAcuerdo(HttpServletRequest request, Model modelo){
		
		String codigoPersonal 	= "0";
		String usuarioNombre	= "-";
		String vigente 			= request.getParameter("Vigente")==null?"S":request.getParameter("Vigente");
		boolean esAdmin			= false;
		boolean mostrar 		= false;
		HttpSession sesion		= ((HttpServletRequest)request).getSession(); 
        if (sesion!=null){
        	codigoPersonal		= (String)sesion.getAttribute("codigoPersonal");
        	usuarioNombre		= usuariosDao.getNombreUsuario(codigoPersonal, "NOMBRE");
        	esAdmin				= accesoDao.getBecas(codigoPersonal);
        }
		List<ContEjercicio> lisEjercicios 		= conEjercicioDao.lisTodos(" ORDER BY ID_EJERCICIO DESC");		
		String ejercicioId 						= request.getParameter("EjercicioId")==null?lisEjercicios.get(0).getIdEjercicio():request.getParameter("EjercicioId");
		List<BecTipo> lisTipos 					= becTipoDao.getListAcuerdo(ejercicioId, " 'O','M','P','E' ", "");  
		List<BecAcceso> lisAccesos 				= becAccesoDao.getListUsuario(codigoPersonal,"");
		HashMap<String,String> mapaInscritos 	= inscritosDao.getMapaInscritos();
		HashMap<String,String> mapaAlumnos	 	= alumPersonalDao.mapaAlumnosConAcuerdo(ejercicioId);		
		String tipoTemp			= "0";
		for(BecTipo tipo: lisTipos){			
			for(BecAcceso acces: lisAccesos){
				if( tipo.getDepartamentos().contains("-"+acces.getIdCcosto()) ){
					tipoTemp = tipo.getTipo();
					break;
				}
			}
		}		
		String tipoId 			= request.getParameter("Tipo")==null?tipoTemp:request.getParameter("Tipo");
		String estado 			= request.getParameter("Estado")==null?"A":request.getParameter("Estado");
		
		List<BecAcuerdo>lisAcuerdos 			= becAcuerdoDao.lisTipoyEstado(ejercicioId, tipoId, estado, vigente, " ORDER BY ESTADO, CODIGO_PERSONAL");
		
		modelo.addAttribute("usuarioNombre", usuarioNombre);
		modelo.addAttribute("ejercicioId", ejercicioId);
		modelo.addAttribute("esAdmin", esAdmin);
		modelo.addAttribute("tipoTemp", tipoTemp);
		modelo.addAttribute("mostrar", mostrar);
		modelo.addAttribute("lisEjercicios", lisEjercicios);
		modelo.addAttribute("lisTipos", lisTipos);
		modelo.addAttribute("lisAccesos", lisAccesos);
		modelo.addAttribute("lisAcuerdos", lisAcuerdos);
		modelo.addAttribute("mapaInscritos", mapaInscritos);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		
		return "taller/acuerdo/acuerdo";
	}
	
	@RequestMapping("/taller/acuerdo/editar")
	public String tallerAcuerdoEditar(HttpServletRequest request, Model modelo){
		
		String ejercicioId 		= request.getParameter("EjercicioId")==null?"0":request.getParameter("EjercicioId");
		String tipo 			= request.getParameter("Tipo")==null?"0":request.getParameter("Tipo");		
		String codigoAlumno		= request.getParameter("CodigoAlumno")==null?"0":request.getParameter("CodigoAlumno");
		String folio 			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		
		String codigoPersonal 	= "0";
		String usuarioNombre	= "-";
		boolean esAdmin			= false;
		String acceso			= "-";
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession(); 
        if (sesion!=null){
        	codigoPersonal		= (String)sesion.getAttribute("codigoPersonal");
        	usuarioNombre		= usuariosDao.getNombreUsuario(codigoPersonal, "NOMBRE");
        	esAdmin				= accesoDao.getBecas(codigoPersonal);
        	acceso 				= becAccesoDao.getUsuarioCentrosCosto(ejercicioId, codigoPersonal);
        }      
    	
    	BecTipo becTipo = new BecTipo();
    	if (becTipoDao.existeReg(ejercicioId, tipo)) {
    		becTipo = becTipoDao.mapeaRegId(ejercicioId, tipo);
    	}
    	
    	BecAcuerdo becAcuerdo = new BecAcuerdo();
    	if (becAcuerdoDao.existeReg(codigoAlumno, folio)) {
    		becAcuerdo =  becAcuerdoDao.mapeaRegId(folio, codigoAlumno);
    	}
    	
        HashMap<String,ContCcosto> mapaDeptos 	= conCcostoDao.mapaCentrosCosto(ejercicioId); 
		modelo.addAttribute("usuarioNombre", usuarioNombre);
		modelo.addAttribute("esAdmin", esAdmin);		
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("becTipo", becTipo);
		modelo.addAttribute("becAcuerdo", becAcuerdo);
		modelo.addAttribute("mapaDeptos", mapaDeptos);
		
		return "taller/acuerdo/editar";
	}
	
	@RequestMapping("/taller/acuerdo/grabarAcuerdo")
	public String tallerAcuerdoGrabarAcuerdo(HttpServletRequest request){
		
		String codigoPersonal 	= "0";
		String ejercicioId		= request.getParameter("EjercicioId")==null?"0":request.getParameter("EjercicioId");		
		String tipo				= request.getParameter("Tipo")==null?"0":request.getParameter("Tipo");
		String codigoAlumno 	= request.getParameter("CodigoAlumno")==null?"0":request.getParameter("CodigoAlumno");
		String folio 			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");		
		String mensaje			= "-";
		HttpSession sesion		= ((HttpServletRequest)request).getSession(); 
        if (sesion!=null){
        	codigoPersonal		= (String)sesion.getAttribute("codigoPersonal");      	
        }
        
        //TRAER EL TIPO DE LA BECA BASICA
    	String tipoBasica 		= becTipoDao.getTipo(ejercicioId, "B");    	
    	//TRAER EL TIPO DE LA BECA BASICA INSTITUCIONAL
    	String tipoInstitucional= becTipoDao.getTipo(ejercicioId, "I");    	
    	//TRAER EL TIPO DE LA BECA ADICIONAL
    	String tipoAdicional 	= becTipoDao.getTipo(ejercicioId, "A");
    	
    	BecTipo becTipo 		= new BecTipo();
    	if (becTipoDao.existeReg(ejercicioId, tipo)){
    		becTipo 			= becTipoDao.mapeaRegId(ejercicioId, tipo);
    	}
    	
		BecAcuerdo becAcuerdo 	= new BecAcuerdo();		
		if(alumPersonalDao.existeReg(codigoAlumno)){
			
			becAcuerdo.setCodigoPersonal(codigoAlumno);			
			becAcuerdo.setHoras("0");		
			
			List<BecAcuerdo> lisAcuerdos = becAcuerdoDao.getAcuerdosVigentesAlumno(ejercicioId, becAcuerdo.getCodigoPersonal(), tipoBasica+","+tipoAdicional+","+tipoInstitucional, " AND FOLIO NOT IN("+folio+")");
			
			// Consulta el tipo de alumno
			String tipoAlumno 	= alumAcademicoDao.getTipoAlumnoId(codigoAlumno);
			String tipoNombre 	= catTipoAlumnoDao.getNombreTipo(tipoAlumno);
			
			if(lisAcuerdos.size() != 0){
				mensaje = "<div class='alert alert-danger'>Este Alumno Ya tiene un Acuerdo</div>";
			}else if( becTipo.getTipoAlumno()==null || becTipo.getTipoAlumno().equals("null") ){
				mensaje = "<div class='alert alert-danger'>A este tipo de beca no se le ha dado de alta los tipos de alumnos</div>";
			}else if( !becTipo.getTipoAlumno().contains("-"+tipoAlumno)){
				mensaje = "<div class='alert alert-danger'>Este alumno de tipo <strong>"+tipoNombre+" ("+tipoAlumno+")</strong> no puede tener este acuerdo (tipos permitidos: "+becTipo.getTipoAlumno().replaceAll("-"," ").trim().replaceAll(" ",", ")+") </div>";
			}else{				
				becAcuerdo.setIdEjercicio(ejercicioId);
				becAcuerdo.setTipo(tipo);
				becAcuerdo.setFecha(aca.util.Fecha.getHoy());
				becAcuerdo.setPromesa(request.getParameter("Promesa"));
				becAcuerdo.setMatricula(request.getParameter("Matricula"));
				becAcuerdo.setEnsenanza(request.getParameter("Ensenanza"));
				becAcuerdo.setInternado(request.getParameter("Internado"));
				becAcuerdo.setValor(request.getParameter("Valor"));
				becAcuerdo.setVigencia(request.getParameter("Vigencia"));
				becAcuerdo.setEstado(request.getParameter("Estado"));
				becAcuerdo.setTipoadicional("X");
				becAcuerdo.setIdCcosto(request.getParameter("Ccosto"));
				becAcuerdo.setUsuario(codigoPersonal);
				
				if(becAcuerdoDao.existeReg(codigoAlumno, folio)){								
					becAcuerdo.setFolio(folio);
					if(becAcuerdoDao.updateReg(becAcuerdo)){						
						mensaje = "<div class='alert alert-success'>Se Modificó Correctamente</div>";
					}else{
						mensaje = "<div class='alert alert-danger'>Ocurrió un Error al Modificar</div>";
					}
				}else{
					if(folio.equals("0")){
						folio = becAcuerdoDao.maximoReg(becAcuerdo.getCodigoPersonal());				
					}
					becAcuerdo.setFolio(folio);
					if(becAcuerdoDao.insertReg(becAcuerdo)){						
						mensaje = "<div class='alert alert-success'>Se Guardó Correctamente</div>";
					}else{
						mensaje = "<div class='alert alert-danger'>Ocurrió un Error al Guardar</div>";
						folio = "0";
					}					
				}
			}
		}else{
			mensaje = "<div class='alert alert-danger'>La Matricula "+codigoAlumno+" No Existe</div>";
		}
		
		return "redirect:/taller/acuerdo/editar?EjercicioId="+ejercicioId+"&Tipo="+tipo+"&Folio="+folio+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/taller/acuerdo/borrarAcuerdo")
	public String tallerAcuerdoBorrarAcuerdo(HttpServletRequest request){
		
		String ejercicioId		= request.getParameter("EjercicioId")==null?"0":request.getParameter("EjercicioId");
		String tipo				= request.getParameter("Tipo")==null?"0":request.getParameter("Tipo");
		String codigoAlumno 	= request.getParameter("CodigoAlumno")==null?"0":request.getParameter("CodigoAlumno");
		String folio 			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String mensaje			= "-";
		
		if (becAcuerdoDao.existeReg(codigoAlumno, folio)) {			
			if(becAcuerdoDao.deleteReg(folio, codigoAlumno)){
	    		mensaje = "<div class='alert alert-success'>Se Eliminó Correctamente</div>";	    		
	    	}else{
	    		mensaje = "<div class='alert alert-danger'>Ocurrió un Error al Eliminar</div>";	    		
	    	}
		}else {
			System.out.println("No existe...!");
		}
		
		return "redirect:/taller/acuerdo/acuerdo?EjercicioId="+ejercicioId+"&Tipo="+tipo+"&Mensaje="+mensaje;
	}	
	
	@RequestMapping("/taller/acuerdo/addAcuerdo")
	public String tallerAcuerdoAddAcuerdo(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContTallerAcuerdo|tallerAcuerdoAddAcuerdo:");
		return "taller/acuerdo/addAcuerdo";
	}		
	
}
