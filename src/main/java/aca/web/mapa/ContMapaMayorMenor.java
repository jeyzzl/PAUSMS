package aca.web.mapa;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.pdf.qrcode.Mode;

import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.plan.spring.MapaMayorMenor;
import aca.plan.spring.MapaMayorMenorDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContMapaMayorMenor{

    @Autowired	
	private MapaPlanDao mapaPlanDao;
	
    @Autowired	
	private CatFacultadDao catFacultadDao;

    @Autowired	
	private MaestrosDao maestrosDao;

	@Autowired	
	private CatCarreraDao catCarreraDao;

    @Autowired
	private MapaMayorMenorDao mapaMayorMenorDao;

    @RequestMapping("/mapa/mayor_menor/facultad")
	public String mapaMayorMenorFacultad(HttpServletRequest request, Model modelo){	
		
		List<CatFacultad> lisFacultades			= catFacultadDao.getListAll("ORDER BY 1");
		HashMap<String,String> mapaDirectores	= maestrosDao.mapaDirectores();
		HashMap<String,String> mapaPlanes		= mapaPlanDao.mapaPlanesPorFacultad();
		
		modelo.addAttribute("lisFacultades", lisFacultades);		
		modelo.addAttribute("mapaDirectores", mapaDirectores);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		
		return "mapa/mayor_menor/facultad";
	}
	
    @RequestMapping("/mapa/mayor_menor/listado")
    public String mapaMayorMenorListado(HttpServletRequest request, Model modelo){
        String facultad			= request.getParameter("Facultad")==null?"0":request.getParameter("Facultad");
        String filtroEstado		= request.getParameter("FiltroEstado")==null?"T":request.getParameter("FiltroEstado");
        String facultadNombre	= catFacultadDao.getNombreFacultad(facultad);

		HttpSession sesion = ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			if (!facultad.equals("0")){
				sesion.setAttribute("fac",facultad);
			}else{
				facultad = (String)sesion.getAttribute("fac");
			}
		}
        if (filtroEstado.equals("T")) filtroEstado = "'A','V','I'"; else filtroEstado = "'"+filtroEstado+"'";
		List<MapaPlan> lisPlanes						= mapaPlanDao.lisPorFacultadyEstado(facultad, filtroEstado, "ORDER BY ENOC.CARRERA_NIVEL(CARRERA_ID),2,1");
		HashMap<String, CatCarrera> mapCarrera			= catCarreraDao.getMapAll("");

        modelo.addAttribute("facultadNombre", facultadNombre);
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("mapCarrera", mapCarrera);		

        return "mapa/mayor_menor/listado";
    }

    @RequestMapping("/mapa/mayor_menor/mayores")
    public String mapaMayorMenorMayores(HttpServletRequest request, Model modelo){	
        String facultad			= request.getParameter("Facultad")==null?"0":request.getParameter("Facultad");
		String planId			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");	

        MapaPlan plan = new MapaPlan();
		if (mapaPlanDao.existeReg(planId)) {
			plan = mapaPlanDao.mapeaRegId(planId);
		}

        String nombrePlan = plan.getNombrePlan();

        HashMap<String,String> mapMayoresActivos = mapaMayorMenorDao.mapaMayoresActivos(" ORDER BY COUNT(*) DESC");

        List<MapaMayorMenor> lisMayores = mapaMayorMenorDao.getListaMayores(planId, " ORDER BY NOMBRE");

        modelo.addAttribute("facultad", facultad);
        modelo.addAttribute("nombrePlan", nombrePlan);
        modelo.addAttribute("lisMayores", lisMayores);
        modelo.addAttribute("mapMayoresActivos", mapMayoresActivos);

        return "mapa/mayor_menor/mayores";
    }

    @RequestMapping("/mapa/mayor_menor/menores")
    public String mapaMayorMenorMenores(HttpServletRequest request, Model modelo){		
        String facultad			= request.getParameter("Facultad")==null?"0":request.getParameter("Facultad");
		String planId			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");	

        MapaPlan plan = new MapaPlan();
		if (mapaPlanDao.existeReg(planId)) {
			plan = mapaPlanDao.mapeaRegId(planId);
		}

        String nombrePlan = plan.getNombrePlan();

        HashMap<String,String> mapMenoresActivos = mapaMayorMenorDao.mapaMenoresActivos(" ORDER BY COUNT(*) DESC");

        List<MapaMayorMenor> lisMenores = mapaMayorMenorDao.getListaMenores(planId, " ORDER BY NOMBRE");

        modelo.addAttribute("facultad", facultad);
        modelo.addAttribute("nombrePlan", nombrePlan);
        modelo.addAttribute("lisMenores", lisMenores);
        modelo.addAttribute("mapMenoresActivos", mapMenoresActivos);
        
        return "mapa/mayor_menor/menores";
    }

    @RequestMapping("/mapa/mayor_menor/accion")
    public String mapaMayoresMenoresAccion(HttpServletRequest request, Model modelo){
        String planId   = request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
        String folio    = request.getParameter("Folio")==null?"0":request.getParameter("Folio");
        String tipo     = request.getParameter("Tipo")==null?"MA":request.getParameter("Tipo");

        MapaMayorMenor mm = new MapaMayorMenor();
        if(mapaMayorMenorDao.existeReg(planId, folio)){
            mm = mapaMayorMenorDao.mapeaRegId(planId, folio);
        }else{
            mm.setFolio(mapaMayorMenorDao.maximoReg()); 
            mm.setPlanId(planId);
            mm.setTipo(tipo);
        }

        modelo.addAttribute("mayorMenor", mm);

        return "mapa/mayor_menor/accion";
    }

    @RequestMapping("/mapa/mayor_menor/grabar")
	public String mapaMayoresMenoresGrabar(HttpServletRequest request, Model model){	
        String facultad     = request.getParameter("Facultad")==null?"0":request.getParameter("Facultad");
        String planId       = request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
        String folio        = request.getParameter("Folio")==null?"0":request.getParameter("Folio");
        String tipo         = request.getParameter("Tipo")==null?"MA":request.getParameter("Tipo");
        String nombre       = request.getParameter("Nombre")==null?"":request.getParameter("Nombre");
        String porDefault   = request.getParameter("PorDefault")==null?"N":request.getParameter("PorDefault");
        String mensaje      = "";

        MapaMayorMenor mm = new MapaMayorMenor();
        mm.setPlanId(planId);
        mm.setNombre(nombre);
        mm.setTipo(tipo);
        mm.setPorDefecto(porDefault);

        if(mapaMayorMenorDao.existeReg(planId, folio)){
            mm.setFolio(folio);
            if(mapaMayorMenorDao.updateReg(mm)){
                mensaje = "1";
            }else{
                mensaje = "0";
            }  
        }else{
            mm.setFolio(folio);
            if(mapaMayorMenorDao.insertReg(mm)){
                mensaje = "1";
            }else{
                mensaje = "0";
            }
        }

        return "redirect:/mapa/mayor_menor/accion?Facultad="+facultad+"&PlanId="+planId+"&Folio="+folio+"&Tipo="+tipo+"&Mensaje="+mensaje;
    }

    @RequestMapping("/mapa/mayor_menor/borrarMayor")
	public String mapaMayoresMenoresBorrarMayor(HttpServletRequest request, Model model){
		String facultad	= request.getParameter("Facultad")==null?"0":request.getParameter("Facultad");
		String planId 	= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
        String folio    = request.getParameter("Folio")==null?"0":request.getParameter("Folio");

		if (mapaMayorMenorDao.existeReg(planId, folio)) {
			mapaMayorMenorDao.deleteReg(planId, folio);
		}
		
		return "redirect:/mapa/mayor_menor/mayores?Facultad="+facultad+"&PlanId="+planId;
	}

    @RequestMapping("/mapa/mayor_menor/borrarMenor")
	public String mapaMayoresMenoresBorrarMenor(HttpServletRequest request, Model model){
		String facultad	= request.getParameter("Facultad")==null?"0":request.getParameter("Facultad");
		String planId 	= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
        String folio    = request.getParameter("Folio")==null?"0":request.getParameter("Folio");

		if (mapaMayorMenorDao.existeReg(planId, folio)) {
			mapaMayorMenorDao.deleteReg(planId, folio);
		}
		
		return "redirect:/mapa/mayor_menor/menores?Facultad="+facultad+"&PlanId="+planId;
	}

}