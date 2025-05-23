package aca.web.diploma;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import aca.alumno.spring.AlumPersonalDao;
import aca.diploma.spring.DipAlumno;
import aca.diploma.spring.DipAlumnoDao;
import aca.diploma.spring.DipCurso;
import aca.diploma.spring.DipCursoDao;
import aca.valida.spring.ValDocumento;
import aca.valida.spring.ValDocumentoDao;
import net.glxn.qrgen.javase.QRCode;

@Controller
public class ContDiplomaDiploma {
	
	@Autowired
	private DipCursoDao dipCursoDao;
	
	@Autowired
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired
	DipAlumnoDao dipAlumnoDao;
	
	@Autowired
	ValDocumentoDao valDocumentoDao;	 
	
	@Autowired
	ServletContext context;
	
	@RequestMapping("/diploma/diploma/listado")
	public String diplomaDiplomaListado(HttpServletRequest request, Model modelo){
		
		List<DipCurso> lisDiplomas 				= dipCursoDao.lisTodos(" ORDER BY DIPLOMA_ID");
		HashMap<String,String> mapaAlumnos	 	= dipAlumnoDao.mapaAlumnos();
		HashMap<String,String> mapaPublicados 	= valDocumentoDao.mapaPorFolio();
		
		modelo.addAttribute("lisDiplomas", lisDiplomas);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaPublicados", mapaPublicados);
		
		return "diploma/diploma/listado";
	}
	
	@RequestMapping("/diploma/diploma/editar")
	public String diplomaDiplomaEditar(HttpServletRequest request, Model modelo){
		
		String diplomaId	= request.getParameter("DiplomaId")==null?"0":request.getParameter("DiplomaId");
		String nombre = "";

		DipCurso dipCurso = new DipCurso();
		if (dipCursoDao.existeReg(diplomaId)){
			dipCurso = dipCursoDao.mapeaRegId(diplomaId);
		}
		
		modelo.addAttribute("dipCurso", dipCurso);
		
		return "diploma/diploma/editar";
	}
	
	@RequestMapping("/diploma/diploma/grabar")
	public String diplomaDiplomaGrabar(HttpServletRequest request, Model modelo){
		
		String diplomaId		= request.getParameter("DiplomaId")==null?"0":request.getParameter("DiplomaId");
		String institucion 		= request.getParameter("Institucion")==null?"-":request.getParameter("Institucion");
		String curso 			= request.getParameter("Curso")==null?"-":request.getParameter("Curso");
		String tema 			= request.getParameter("Tema")==null?"-":request.getParameter("Tema");
		String horas 			= request.getParameter("Horas")==null?"-":request.getParameter("Horas");
		String periodo 			= request.getParameter("Periodo")==null?"-":request.getParameter("Periodo");
		String fecha 			= request.getParameter("Fecha")==null?"-":request.getParameter("Fecha");
		String firmaUno			= request.getParameter("FirmaUno")==null?"-":request.getParameter("FirmaUno");
		String firmaDos			= request.getParameter("FirmaDos")==null?"-":request.getParameter("FirmaDos");
		
		String mensaje			= "-";
		
		DipCurso dipCurso 		= new DipCurso();
		dipCurso.setDiplomaId(diplomaId);
		dipCurso.setInstitucion(institucion);
		dipCurso.setCurso(curso);
		dipCurso.setTema(tema);
		dipCurso.setHoras(horas);
		dipCurso.setPeriodo(periodo);
		dipCurso.setFecha(fecha);
		dipCurso.setFirmaUno(firmaUno);
		dipCurso.setFirmaDos(firmaDos);
		
		if (dipCursoDao.existeReg(diplomaId)) {
			if (dipCursoDao.updateReg(dipCurso)) {
				mensaje = "¡Modificado!";
			}
		}else {
			diplomaId = dipCursoDao.maxReg();
			dipCurso.setDiplomaId(diplomaId);
			if (dipCursoDao.insertReg(dipCurso)) {
				mensaje = "¡Grabado!";
			}
		}
		
		return "redirect:/diploma/diploma/editar?DiplomaId="+diplomaId+"&Mensaje="+mensaje;
	}	
	
	@RequestMapping("/diploma/diploma/borrarDiploma")
	public String diplomaDiplomaBorrarDiploma(HttpServletRequest request, Model modelo){
		
		String diplomaId		= request.getParameter("DiplomaId")==null?"0":request.getParameter("DiplomaId");		
		String mensaje			= "-";
		if (dipCursoDao.existeReg(diplomaId)) {
			if (dipCursoDao.deleteReg(diplomaId)) {
				mensaje = "¡Eliminado!";
			}
		}		
		return "redirect:/diploma/diploma/listado?DiplomaId="+diplomaId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/diploma/diploma/diplomaPdf")
	public String diplomaDiplomaDiplomaPdf(HttpServletRequest request, Model modelo){
		
		String diplomaId 			= request.getParameter("DiplomaId")==null?"0":request.getParameter("DiplomaId");
		String codigoPersonal 		= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal");
		DipAlumno dipAlumno 		= new DipAlumno();
		DipCurso dipCurso 			= new DipCurso();
		
		if(dipAlumnoDao.existeReg(diplomaId, codigoPersonal)) {
			dipAlumno = dipAlumnoDao.mapeaRegId(diplomaId, codigoPersonal);
		}
		
		if (dipCursoDao.existeReg(dipAlumno.getDiplomaId())) {
			dipCurso = dipCursoDao.mapeaRegId(dipAlumno.getDiplomaId());
		}
		
		modelo.addAttribute("dipCurso", dipCurso);
		modelo.addAttribute("dipAlumno", dipAlumno);
		
		return "diploma/diploma/diplomaPdf";
		
	}
	
	@RequestMapping("/diploma/diploma/alumnos")
	public String diplomaDiplomaAlumnos(HttpServletRequest request, Model modelo){
		
		String diplomaId 		= request.getParameter("DiplomaId") == null ? "0" : request.getParameter("DiplomaId");
		String codigos	 		= request.getParameter("Codigos") == null ? "0" : request.getParameter("Codigos");
		String matricula		= "-";
		String nombre 			= "-";
		String curso			= dipCursoDao.mapeaRegId(diplomaId).getCurso();				
				
		DipAlumno alumno = new DipAlumno();
		if(!codigos.equals("0")) {
			codigos = codigos.replaceAll("\n", "-");
			codigos = codigos.replaceAll("\r", "");
	
			String array[] = codigos.split("-");
			for(String texto : array) {
				
				alumno.setDiplomaId(diplomaId);
				matricula 	= texto;			
				nombre 		= "-";
				if (texto.contains(",")) {
					matricula 	= texto.split(",")[0];
					nombre 		= texto.split(",")[1];
				}
				alumno.setCodigoPersonal(matricula);				
				if (alumPersonalDao.existeReg(matricula)){			
					nombre = alumPersonalDao.getNombreAlumno(matricula, "NOMBRE");
				}
				alumno.setNombre(nombre);
				if(dipAlumnoDao.existeReg(diplomaId, matricula)){
					dipAlumnoDao.updateReg(alumno);
				}else {
					dipAlumnoDao.insertReg(alumno);
				}
			}
		}
		
		List<DipAlumno> lisAlumnos 				= dipAlumnoDao.lisPorDiploma(diplomaId, " ORDER BY NOMBRE");
		HashMap<String,String> mapaValidados 	= valDocumentoDao.mapaPorTipo("Diploma");
		
		modelo.addAttribute("curso", curso);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapaValidados", mapaValidados);
		
		return "diploma/diploma/alumnos";
	}

	@RequestMapping("/diploma/diploma/grabarDatos")
	public String diplomaDiplomaGrabarDatos(HttpServletRequest request, Model modelo){
		
		String diplomaId		= request.getParameter("DiplomaId")==null?"0":request.getParameter("DiplomaId");
		String codigoPersonal 	= "0";		
		String nombre			= "-";
		int grabados 			= 0;
		
		List<DipAlumno> lisAlumnos = dipAlumnoDao.lisPorDiploma(diplomaId, " ORDER BY NOMBRE");		
		for (DipAlumno alumno : lisAlumnos ){
			codigoPersonal 	= request.getParameter("Codigo"+alumno.getCodigoPersonal())==null?"0":request.getParameter("Codigo"+alumno.getCodigoPersonal());
			nombre 			= request.getParameter("Nombre"+alumno.getCodigoPersonal())==null?"0":request.getParameter("Nombre"+alumno.getCodigoPersonal());		
			alumno.setCodigoPersonal(codigoPersonal);
			alumno.setNombre(nombre);			
			if (dipAlumnoDao.existeReg(diplomaId, codigoPersonal)){
				if (dipAlumnoDao.updateReg(alumno)){
					grabados++;
				}		
			}						
		}
		
		return "redirect:/diploma/diploma/alumnos?DiplomaId="+diplomaId;
	}
	
	@RequestMapping("/diploma/diploma/borrarAlumno")
	public String diplomaDiplomaBorrarAlumno(HttpServletRequest request, Model modelo){
		
		String diplomaId		= request.getParameter("DiplomaId")==null?"0":request.getParameter("DiplomaId");		
		String codigoPersonal 	= request.getParameter("CodigoPersonal")==null?"-":request.getParameter("CodigoPersonal");
		String mensaje			= "-";	
		
		if (dipAlumnoDao.existeReg(diplomaId, codigoPersonal)) {
			if (dipAlumnoDao.deleteReg(diplomaId, codigoPersonal)) {
				mensaje = "¡Eliminado!";
			}
		}
		
		return "redirect:/diploma/diploma/alumnos?DiplomaId="+diplomaId+"&Mensaje="+mensaje;
	}
	
	@Transactional
	@RequestMapping("/diploma/diploma/publicar")
	public String diplomaDiplomaPublicar(HttpServletRequest request, Model modelo){
		
		String diplomaId 		= request.getParameter("DiplomaId") == null ? "0" : request.getParameter("DiplomaId");
		
		List<DipAlumno> lisAlumnos 				= dipAlumnoDao.lisPorDiploma(diplomaId, " ORDER BY NOMBRE");
		ValDocumento valDocumento = new ValDocumento();
		for (DipAlumno alumno : lisAlumnos) {			
			String clave = aca.util.Encriptar.getSHA256("Diploma"+alumno.getDiplomaId()+alumno.getCodigoPersonal());
			
			String textoQR = "https://academico.um.edu.mx/academico/validaDocumento?Id="+clave;			
			byte[] imagenQr = QRCode.from(textoQR).withSize(120, 120).stream().toByteArray();
			if ( dipAlumnoDao.updateImagen(alumno.getDiplomaId(), alumno.getCodigoPersonal(), imagenQr) ){
				valDocumento.setTipo("Diploma");
				valDocumento.setFolio(diplomaId);
				valDocumento.setCodigoPersonal(alumno.getCodigoPersonal());
				valDocumento.setClave(clave);
				if (valDocumentoDao.existeReg(clave)) {
					valDocumentoDao.updateReg(valDocumento);
				}else {
					valDocumentoDao.insertReg(valDocumento);
				}
			}
			//System.out.println("Datos:"+alumno.getDiplomaId()+":"+alumno.getCodigoPersonal()+":"+clave);
			
		}
		
		return "redirect:/diploma/diploma/alumnos?DiplomaId="+diplomaId;
	}	
		
	
}