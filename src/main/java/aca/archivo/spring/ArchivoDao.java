package aca.archivo.spring;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.emp.spring.EmpleadoDao;

@Component
public class ArchivoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired	
	private ArchDocAlumDao archDocAlumDao;
	
	@Autowired	
	private ArchPermisosDao archPermisosDao;
	
	@Autowired	
	private EmpleadoDao empleadoDao;
	
	@Autowired	
	private ArchGrupoDocumentoDao archGrupoDocumentoDao;
	
	@Autowired	
	private ArchGrupoPlanDao archGrupoPlanDao;

	@Autowired	
	private ArchDocStatusDao archDocStatusDao;	
	

	public String autorizaAlumno(String codigoPersonal, String planId){
		
		boolean documentosCompletos = false;
		
		String autorizado 		= "Not authorized"; 
		List<String> lisGrupos 						= archGrupoPlanDao.gruposDelPlan(planId);
		HashMap<String, ArchDocStatus> mapaValidos 	= archDocStatusDao.mapaEstadosValidos();
		for(String grupo : lisGrupos) {
			
			List<ArchGrupoDocumento> lisDoc		= archGrupoDocumentoDao.lisPorGrupo(grupo, "");
			int cont = 0;
			for (ArchGrupoDocumento doc : lisDoc) {
				if (archDocAlumDao.existeReg(codigoPersonal, doc.getDocumentoId())){
					ArchDocAlum docAlumno = archDocAlumDao.mapeaRegId(codigoPersonal, doc.getDocumentoId());
					if (mapaValidos.containsKey(docAlumno.getIdDocumento()+"-"+docAlumno.getIdStatus())){
					 cont++;
					}
				}
			}
			if (lisDoc.size()==cont){ 
				documentosCompletos = true;
				autorizado = "Authorized Group: "+grupo;
			}

		}
		
		if(!documentosCompletos) {
			if(archPermisosDao.tienePermiso(codigoPersonal)){
				documentosCompletos = true;
				String folio 	= archPermisosDao.getFolioPermiso(codigoPersonal);			
				ArchPermisos permiso 		= archPermisosDao.mapeaRegId(codigoPersonal, folio);
				String usuario	= empleadoDao.getNombreEmpleado(permiso.getUsuarioAlta(), "NOMBRE");
				autorizado = "Authorized DSE permit :"+usuario+"("+permiso.getFechaIni()+" to "+permiso.getFechaLim()+")";
			}
		}
		
		return autorizado;	
	}

	public boolean autorizaDocumentosAlumno(String codigoPersonal, String planId){
		boolean documentosCompletos = false;
		
		List<String> lisGrupos 						= archGrupoPlanDao.gruposDelPlan(planId);
		HashMap<String, ArchDocStatus> mapaValidos 	= archDocStatusDao.mapaEstadosValidos();
		for(String grupo : lisGrupos) {
			
			List<ArchGrupoDocumento> lisDoc		= archGrupoDocumentoDao.lisPorGrupo(grupo, "");
			int cont = 0;
			for (ArchGrupoDocumento doc : lisDoc) {
				 if (archDocAlumDao.existeReg(codigoPersonal, doc.getDocumentoId())){
					 ArchDocAlum docAlumno = archDocAlumDao.mapeaRegId(codigoPersonal, doc.getDocumentoId());
					 if (mapaValidos.containsKey(docAlumno.getIdDocumento()+"-"+docAlumno.getIdStatus())){
						 cont++;
					 }
				 }
			}
			if (lisDoc.size()==cont) documentosCompletos = true;

		}
		
		if(!documentosCompletos) {
			if(archPermisosDao.tienePermiso(codigoPersonal)){
				documentosCompletos = true;
			}
		}
		
		return documentosCompletos;
	}	
	
	public String getAutorizado(String codigoPersonal, String planId){
		
		boolean documentosCompletos = false;
		String resultado 			= "N";
		
		List<String> lisGrupos 						= archGrupoPlanDao.gruposDelPlan(planId);
		HashMap<String, ArchDocStatus> mapaValidos 	= archDocStatusDao.mapaEstadosValidos();
		for(String grupo : lisGrupos) {
			
			List<ArchGrupoDocumento> lisDoc		= archGrupoDocumentoDao.lisPorGrupo(grupo, "");
			int cont = 0;
			for (ArchGrupoDocumento doc : lisDoc) {
				 if (archDocAlumDao.existeReg(codigoPersonal, doc.getDocumentoId())){
					 ArchDocAlum docAlumno = archDocAlumDao.mapeaRegId(codigoPersonal, doc.getDocumentoId());
					 if (mapaValidos.containsKey(docAlumno.getIdDocumento()+"-"+docAlumno.getIdStatus())){
						 cont++;
					 }
				 }
			}
			if (lisDoc.size()==cont) {
				documentosCompletos = true;
				resultado = "S";
			}
		}
		
		if(!documentosCompletos) {
			if(archPermisosDao.tienePermiso(codigoPersonal)){
				documentosCompletos = true;
				resultado = "P";
			}
		}
		
		return resultado;
	}
	
}