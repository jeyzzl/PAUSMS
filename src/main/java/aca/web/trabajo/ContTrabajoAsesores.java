package aca.web.trabajo;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.emp.spring.EmpMaestroDao;
import aca.trabajo.spring.*;
import aca.vista.spring.Maestros;
import aca.vista.spring.MaestrosDao;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ContTrabajoAsesores {

    private final TrabAlumDao trabAlumDao;

    @Autowired
    TrabDepartamentoDao trabDepartamentoDao;

    @Autowired
    TrabAsesorDao trabAsesorDao;

    @Autowired
    EmpMaestroDao empMaestroDao;

    @Autowired
    MaestrosDao maestrosDao;

    ContTrabajoAsesores(TrabAlumDao trabAlumDao) {
        this.trabAlumDao = trabAlumDao;
    }

    @RequestMapping("/trabajo/asesores/listado")
	public String trabajoAsesoresListado(HttpServletRequest request, Model model) {
        String deptId = request.getParameter("DeptId")==null?"0":request.getParameter("DeptId");

		List<TrabDepartamento> lisDepartamentos = (List<TrabDepartamento>) trabDepartamentoDao.lisTodos(" ORDER BY DEPT_ID");

        // if((deptId.equals("0") || deptId == null ) && !lisDepartamentos.isEmpty()){
        //     deptId = lisDepartamentos.get(0).getDeptId();
        // }

        List<TrabAsesor> lisAsesoresPorDepartamento = null;

		if(deptId.equals("0") || deptId == null){
			lisAsesoresPorDepartamento = trabAsesorDao.lisActivos("");
		}else{
			lisAsesoresPorDepartamento = trabAsesorDao.lisPorDepartamento(deptId, " ORDER BY CODIGO_PERSONAL");
		}

        HashMap<String,String> mapaNombres = maestrosDao.mapMaestroNombre("NOMBRE");
		
		model.addAttribute("lisDepartamentos", lisDepartamentos);
        model.addAttribute("lisAsesoresPorDepartamento", lisAsesoresPorDepartamento);
        model.addAttribute("mapaNombres", mapaNombres);
        model.addAttribute("deptId", deptId);

		return "trabajo/asesores/listado";
	}

    @RequestMapping("/trabajo/asesores/editarAsesor")
	public String trabajoAsesoresEditarAsesor(HttpServletRequest request, Model model) {
		String codigoPersonal   = request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal");
        String deptId           = request.getParameter("DeptId")==null?"0":request.getParameter("DeptId");
		
		TrabAsesor asesor = new TrabAsesor();
		if (trabAsesorDao.existeReg(codigoPersonal)) {
			asesor = trabAsesorDao.mapeaRegId(codigoPersonal);
		}else{
			asesor.setCodigoPersonal(codigoPersonal);
			asesor.setDeptId(deptId);
		}

        TrabDepartamento departamento = trabDepartamentoDao.mapeaRegId(deptId);

        String nombreAsesor = maestrosDao.getNombreMaestro(codigoPersonal, "");
		
		List<Maestros> lisMaestros	= maestrosDao.lisMaestros("");
		
		model.addAttribute("asesor", asesor);
        model.addAttribute("departamento", departamento);
        model.addAttribute("nombreAsesor", nombreAsesor);
		model.addAttribute("lisMaestros", lisMaestros);
		
		return "trabajo/asesores/editarAsesor";
	}

	@RequestMapping("/trabajo/asesores/grabar")
	public String trabajoAsesoresGrabar(HttpServletRequest request, Model model) {

		String codigoPersonal 			= request.getParameter("CodigoPersonal") == null ? "0" : request.getParameter("CodigoPersonal");
		String deptId 			        = request.getParameter("DeptId") == null ? "0": request.getParameter("DeptId");
		String fecha 		            = aca.util.Fecha.getFechayHora();
		String status 			        = request.getParameter("Status") == null ? "-": request.getParameter("Status");
		String mensaje 			        = "0";
		
		TrabAsesor asesor = new TrabAsesor();
        asesor.setCodigoPersonal(codigoPersonal);
        asesor.setDeptId(deptId);
        asesor.setFecha(fecha);
        asesor.setStatus(status);

		System.out.println(deptId);
		System.out.println(fecha);
				
		if (trabAsesorDao.existeReg(codigoPersonal)) {
			if (trabAsesorDao.updateReg(asesor)) {
				mensaje = "Updated";
			} else {
				mensaje = "Error Updating";
			}
		} else {
			if (trabAsesorDao.insertReg(asesor)) {
				mensaje = "Saved";
			} else {
				mensaje = "Error Saving";
			}
		}
		return "redirect:/trabajo/asesores/editarAsesor?CodigoPersonal=" + asesor.getCodigoPersonal() + "&Mensaje=" + mensaje;
	}

    @RequestMapping("/trabajo/asesores/borrar")
	public String trabajoAsesoresBorrar(HttpServletRequest request, Model model) {

		String codigoPersonal = request.getParameter("CodigoPersonal") == null ? "0" : request.getParameter("CodigoPersonal");
		if (trabAsesorDao.existeReg(codigoPersonal)) {
			trabAsesorDao.deleteReg(codigoPersonal);
		}

		return "redirect:/trabajo/asesores/listado";
	}
    
}
