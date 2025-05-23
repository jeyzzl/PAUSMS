package aca.web.trabajo;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.trabajo.spring.TrabDepartamento;
import aca.trabajo.spring.TrabCategoria;
import aca.trabajo.spring.TrabCategoriaDao;
import aca.trabajo.spring.TrabDepartamentoDao;
import aca.trabajo.spring.TrabAlum;
import aca.trabajo.spring.TrabAlumDao;
import aca.vista.spring.Maestros;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContTrabajoDepartamento {

	@Autowired
	private TrabDepartamentoDao trabDepartamentoDao;
	
	@Autowired
	private TrabCategoriaDao trabCategoriaDao;

	@Autowired
	private MaestrosDao maestrosDao;
	
	@Autowired
	private TrabAlumDao trabAlumDao;

	@RequestMapping("/trabajo/departamento/departamentos")
	public String trabajoDepartamentosDepartamento(HttpServletRequest request, Model model) {

		List<TrabDepartamento> lisDepartamentos = (List<TrabDepartamento>) trabDepartamentoDao.lisTodos(" ORDER BY DEPT_ID");
		HashMap<String, String> mapaCatPorDept = trabCategoriaDao.mapaTotalCategorias();
		
		model.addAttribute("lisDepartamentos", lisDepartamentos);
		model.addAttribute("mapaCatPorDept", mapaCatPorDept);

		return "trabajo/departamento/departamentos";
	}

	@RequestMapping("/trabajo/departamento/editarDepartamento")
	public String trabajoDepartamentosEditarDepartamento(HttpServletRequest request, Model model) {
		String deptId = request.getParameter("DeptId") == null ? "0" : request.getParameter("DeptId");
		
		TrabDepartamento departamento = new TrabDepartamento();
		if (trabDepartamentoDao.existeReg(deptId)) {
			departamento = trabDepartamentoDao.mapeaRegId(deptId);
		} else {
			departamento.setDeptId(trabDepartamentoDao.maximoReg());
		}
		
		List<Maestros> lisMaestros	= maestrosDao.lisPorEstado("'A','J'"," ORDER BY APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");
		
		model.addAttribute("departamento", departamento);
		model.addAttribute("lisMaestros", lisMaestros);
		
		return "trabajo/departamento/editarDepartamento";
	}

	@RequestMapping("/trabajo/departamento/grabarDepartamento")
	public String trabajoDepartamentosGrabarDepartamento(HttpServletRequest request, Model model) {

		String deptId 			= request.getParameter("DeptId") == null ? "0" : request.getParameter("DeptId");
		String nombre 			= request.getParameter("Nombre") == null ? "-": request.getParameter("Nombre");
		String detalles 		= request.getParameter("Detalles") == null ? "-" : request.getParameter("Detalles");
		String estado 			= request.getParameter("Estado") == null ? "-": request.getParameter("Estado");
		String codigoPersonal 	= request.getParameter("CodigoPersonal") == null ? "-" : request.getParameter("CodigoPersonal");
		String mensaje 			= "0";
		
//		System.out.println(deptId);
		
		TrabDepartamento departamento = new TrabDepartamento();
		departamento.setDeptId(deptId);
		departamento.setNombre(nombre);
		departamento.setDetalles(detalles);
		departamento.setEstado(estado);
		departamento.setCodigo_personal(codigoPersonal);
				
		if (trabDepartamentoDao.existeReg(deptId)) {
			if (trabDepartamentoDao.updateReg(departamento)) {
				mensaje = "Updated";
			} else {
				mensaje = "Error Updating";
			}
		} else {
			departamento.setDeptId(trabDepartamentoDao.maximoReg());
			if (trabDepartamentoDao.insertReg(departamento)) {
				mensaje = "Saved";
			} else {
				mensaje = "Error Saving";
			}
		}
		return "redirect:/trabajo/departamento/editarDepartamento?DeptId=" + departamento.getDeptId() + "&Mensaje=" + mensaje;
	}

	@RequestMapping("/trabajo/departamento/borrarDepartamento")
	public String trabajoDepartamentosBorrarDepartamento(HttpServletRequest request, Model model) {

		String deptId = request.getParameter("DeptId") == null ? "0" : request.getParameter("DeptId");
		if (trabDepartamentoDao.existeReg(deptId)) {
			trabDepartamentoDao.deleteReg(deptId);
		}

		return "redirect:/trabajo/departamento/departamentos";
	}
	
	@RequestMapping("/trabajo/departamento/categorias")
	public String trabajoDepartamentosCategorias(HttpServletRequest request, Model model) {
		String deptId = request.getParameter("DeptId")==null?"0":request.getParameter("DeptId");

		List<TrabCategoria> lisCategorias = (List<TrabCategoria>) trabCategoriaDao.lisPorDepartamento(deptId, "ORDER BY CAT_ID");
		HashMap<String,String> mapaPorCat = trabAlumDao.mapaPorCat(""); 
		
		model.addAttribute("deptId", deptId);
		model.addAttribute("lisCategorias", lisCategorias);
		model.addAttribute("mapaPorCat", mapaPorCat);

		return "trabajo/departamento/categorias";
	}
	
	@RequestMapping("/trabajo/departamento/editarCategoria")
	public String trabajoDepartamentosEditarCategoria(HttpServletRequest request, Model model) {
		String deptId = request.getParameter("DeptId") == null ? "0" : request.getParameter("DeptId");
		String catId = request.getParameter("CatId") == null ? "0" : request.getParameter("CatId");
		String mensaje = request.getParameter("Mensaje") == null ? "0" : request.getParameter("Mensaje");
		
		TrabCategoria categoria  = new TrabCategoria();
		
		if(trabCategoriaDao.existeReg(catId)) {
			categoria = (TrabCategoria)trabCategoriaDao.mapeaRegId(catId);
		}else {
			categoria.setCategoriaId(trabCategoriaDao.maximoReg());
		}
		
		model.addAttribute("deptId", deptId);
		model.addAttribute("categoria", categoria);
		model.addAttribute("mensaje", mensaje);
		
		return "trabajo/departamento/editarCategoria";
	}
	
	@RequestMapping("/trabajo/departamento/grabarCategoria")
	public String trabajoDepartamentosGrabarCategoria(HttpServletRequest request, Model model) {
		String deptId 			= request.getParameter("DeptId") == null ? "0" : request.getParameter("DeptId");
		String catId 			= request.getParameter("CatId") == null ? "0" : request.getParameter("CatId");
		String nombre 			= request.getParameter("Nombre") == null ? "-": request.getParameter("Nombre");
		String estado 			= request.getParameter("Estado") == null ? "-": request.getParameter("Estado");
		String mensaje 			= "0";

		TrabCategoria categoria = new TrabCategoria();
		categoria.setDeptId(deptId);
		categoria.setCategoriaId(catId);
		categoria.setNombreCategoria(nombre);
		categoria.setEstado(estado);
				
		if (trabCategoriaDao.existeReg(catId)) {			
			if (trabCategoriaDao.updateReg(categoria)) {
				mensaje = "Updated";
			} else {
				mensaje = "Error Updating";
			}
		} else {
			categoria.setCategoriaId(trabCategoriaDao.maximoReg());			
			if (trabCategoriaDao.insertReg(categoria)) {
				mensaje = "Saved";
			} else {
				mensaje = "Error Saving";
			}
		}
		return "redirect:/trabajo/departamento/editarCategoria?DeptId="+categoria.getDeptId()+"&CatId="+categoria.getCategoriaId()+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/trabajo/departamento/borrarCategoria")
	public String trabajoDepartamentosBorrarCategoria(HttpServletRequest request, Model model) {
		String deptId = request.getParameter("DeptId") == null ? "0" : request.getParameter("DeptId");
		String catId = request.getParameter("CatId") == null ? "0" : request.getParameter("CatId");
		
		if (trabCategoriaDao.existeReg(catId)) {
			trabCategoriaDao.deleteReg(catId);
		}

		return "redirect:/trabajo/departamento/categorias?DeptId="+deptId;
	}

}