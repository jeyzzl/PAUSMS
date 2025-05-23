// Clase para la vista CARGA_ACADEMICA
package  aca.vista;

import java.sql.*;

public class Alumno{
	private String codigoPersonal;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombreLegal;
	private String cotejado;
	private String fNacimiento;
	private String sexo;
	private String estadoCivil;
	private String religionId;	
	private String paisId;
	private String estadoId;	
	private String ciudadId;
	private String nacionalidad;
	private String curp;	
	private String modalidadId;
	private String clasFin;
	private String residenciaId;
	private String planId;
	private String carreraId;
	private String correo;
	private String telefono;
	
	public Alumno(){
		codigoPersonal	= "";
		nombre			= "";
		apellidoPaterno	= "";
		apellidoMaterno	= "";
		nombreLegal		= "";
		cotejado		= "";
		fNacimiento		= "";
		sexo			= "";
		estadoCivil		= "";
		religionId		= "";
		paisId			= "";
		estadoId		= "";	
		ciudadId		= "";
		nacionalidad	= "";
		curp			= "";		
		modalidadId		= "";
		clasFin			= "";
		residenciaId	= "";
		planId			= "";
		carreraId		= "";
		correo			= "";
		telefono		= "";
	}
	
	public String getCodigoPersonal(){
		return codigoPersonal;
	}
		
	public String getNombre(){
		return nombre;
	}	
		
	public String getApellidoPaterno(){
		return apellidoPaterno;
	}	
		
	public String getApellidoMaterno(){
		return apellidoMaterno;
	}	
		
	public String getNombreLegal(){
		return nombreLegal;
	}
	
	public String getCotejado(){
		return cotejado;
	}	
		
	public String getFNacimiento(){
		return fNacimiento;
	}	
		
	public String getSexo(){
		return sexo;
	}
	
	public String getEstadoCivil(){
		return estadoCivil;
	}
		
	public String getReligionId(){
		return religionId;
	}
	
	public String getPaisId(){
		return paisId;
	}
	
	public String getEstadoId(){
		return estadoId;
	}
	
	public String getCiudadId(){
		return ciudadId;
	}
	
	public String getNacionalidad(){
		return nacionalidad;
	}
	
	public String getCurp(){
		return curp;
	}	
	
	public String getModalidadId(){
		return modalidadId;
	}
	
	public String getClasFin(){
		return clasFin;
	}
	
	public String getResidenciaId(){
		return residenciaId;
	}
	
	public String getPlanId(){
		return planId;
	}
	
	public String getCarreraId(){
		return carreraId;
	}
	
	public String getCorreo(){
		return correo;
	}
	
	public String getTelefono(){
		return telefono;
	}
		
	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal 		= rs.getString("CODIGO_PERSONAL");
		nombre				= rs.getString("NOMBRE");
		apellidoPaterno		= rs.getString("APELLIDO_PATERNO");
		apellidoMaterno		= rs.getString("APELLIDO_MATERNO");
		nombreLegal 		= rs.getString("NOMBRE_LEGAL");
		cotejado			= rs.getString("COTEJADO");
		fNacimiento 		= rs.getString("F_NACIMIENTO");
		sexo 				= rs.getString("SEXO");
		estadoCivil			= rs.getString("ESTADO_CIVIL");
		religionId 			= rs.getString("RELIGION_ID");		
		paisId 				= rs.getString("PAIS_ID");
		estadoId 			= rs.getString("ESTADO_ID");
		ciudadId 			= rs.getString("CIUDAD_ID");
		nacionalidad 		= rs.getString("NACIONALIDAD");
		curp				= rs.getString("CURP");		
		modalidadId 		= rs.getString("MODALIDAD_ID");
		clasFin 			= rs.getString("CLAS_FIN");
		residenciaId		= rs.getString("RESIDENCIA_ID");
		planId 				= rs.getString("PLAN_ID");
		carreraId			= rs.getString("CARRERA_ID");
		correo				= rs.getString("CORREO");
		telefono			= rs.getString("TELEFONO");
	}
	
	public void mapeaRegId( Connection conn, String codigoPersonal ) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
 		try{
			ps = conn.prepareStatement("SELECT "+
				"CODIGO_PERSONAL, NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
				"TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
				"SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
				"CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,PLAN_ID,CARRERA_ID,CORREO, TELEFONO "+
				"FROM ENOC.ALUMNO "+
				"WHERE CODIGO_PERSONAL = ? ");
			ps.setString(1, codigoPersonal);		
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
 		}catch(Exception ex){
			System.out.println("Error - aca.vista.Alumno|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}	
}