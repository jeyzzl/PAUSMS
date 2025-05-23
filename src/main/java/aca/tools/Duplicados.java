package aca.tools;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

import aca.alumno.AlumPersonal;

public class Duplicados {
	
	public void BuscaDuplicados() throws SQLException{
		Statement st 		= null;
		ResultSet rs 		= null;
		try{
		
			Connection Conn = null;						
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Conn = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.20:1521:ora1", "enoc", "caminacondios");			
			st 		= Conn.createStatement();
			String comando		= "";
			
			aca.alumno.AlumUtil alumU = new aca.alumno.AlumUtil();
			ArrayList<AlumPersonal> lisPersonal	= new ArrayList<AlumPersonal>();
			
			char cNomb			= '0';
			char cPat			= '0';		
			String sNomb		= "";
			String sPat			= "";
			String sMat			= "";
			int iSuma			= 0;
			int iCont			= 0;
			int iTamanio		= 0;		
			
			String nombre		= "";
			String paterno		= "";
			String materno		= "";			
			int k=0, i=0, row= 0, duplicado=0;
			
			lisPersonal = alumU.getListAll(Conn, "ORDER BY APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE, CODIGO_PERSONAL");
			System.out.println("Inicia busqueda");
			while (lisPersonal.size()>=1){	
				
				aca.alumno.AlumPersonal alumno = (aca.alumno.AlumPersonal)lisPersonal.get(row);
				//System.out.println("Buscando:"+alumno.getCodigoPersonal());
				nombre		= alumno.getNombre().toUpperCase();
				paterno		= alumno.getApellidoPaterno().toUpperCase();
				materno		= alumno.getApellidoMaterno().toUpperCase();			
				
				cNomb 		= nombre.charAt(0);
				cPat 		= paterno.charAt(0);
				duplicado 	= 0;
				
				if (cNomb != '0' & cPat != '0'){
					
					for (k=1;k<lisPersonal.size();k++){
						aca.alumno.AlumPersonal alumno2 = (aca.alumno.AlumPersonal)lisPersonal.get(k);
						
						sNomb = alumno2.getNombre();
						sPat = alumno2.getApellidoPaterno();
						sMat = alumno2.getApellidoMaterno();						
						
						iSuma = 0;						
						// Verifica la similitud del nombre
						for(i = 0; i < nombre.length() & i < sNomb.length();i++){						
							if(nombre.charAt(i) == sNomb.charAt(i)){
								iSuma++;
							}
						}
						if(nombre.length() < sNomb.length())
							iTamanio = sNomb.length();
						else
							iTamanio = nombre.length();
						if(((100*iSuma)/iTamanio)>=95)
							iCont++;
							
						// Verifica la similitud del apellido paterno
						iSuma = 0;
						for(i = 0; i < paterno.length() & i < sPat.length();i++){
							if(paterno.charAt(i) == sPat.charAt(i)){
								iSuma++;
							}
						}
						if(paterno.length() < sPat.length())
							iTamanio = sPat.length();
						else
							iTamanio = paterno.length();
						if(((100*iSuma)/iTamanio)>=95)
							iCont++;
						
						// Verifica la similitud del apellido materno
							
						iSuma = 0;
						for(i = 0; i < materno.length() & i < sMat.length();i++){
							if(materno.charAt(i) == sMat.charAt(i)){
								iSuma++;
							}
						}
						if(materno.length() < sMat.length())
							iTamanio = sMat.length();
						else
							iTamanio = materno.length();
						if(((100*iSuma)/iTamanio)>=95)
							iCont++;
							
						if(iCont >= 3){
							// numero duplicado..!
								if (duplicado==0){								
									comando = "INSERT INTO ENOC.ALUM_DUPLICADO (CODIGO_PERSONAL, CODIGO1, CODIGO2, CODIGO3, CODIGO4)" + 
									"VALUES('"+alumno.getCodigoPersonal()+"','"+alumno2.getCodigoPersonal()+"','x','x','x')";
								}else if (duplicado == 1){
									comando = "UPDATE ENOC.ALUM_DUPLICADO SET CODIGO2 = '"+alumno2.getCodigoPersonal()+"' " + 
											"WHERE CODIGO_PERSONAL = '"+alumno.getCodigoPersonal()+"'";
								}else if (duplicado == 2){
									comando = "UPDATE ENOC.ALUM_DUPLICADO SET CODIGO3 = '"+alumno2.getCodigoPersonal()+"' " + 
											"WHERE CODIGO_PERSONAL = '"+alumno.getCodigoPersonal()+"'";
								}else if (duplicado == 3){
									comando = "UPDATE ENOC.ALUM_DUPLICADO SET CODIGO4 = '"+alumno2.getCodigoPersonal()+"' " + 
											"WHERE CODIGO_PERSONAL = '"+alumno.getCodigoPersonal()+"'";
								}else{
									System.out.println("Encontre 5:"+alumno.getCodigoPersonal());
								}
								
								if (st.executeUpdate(comando)==1){
									duplicado++;
								}								
							lisPersonal.remove(k);
							k--;
						}
						iCont = 0;					
					}
				}else{
					System.out.println("Si tenian un 0 el nombre y el paterno de: "+alumno.getCodigoPersonal());
				}
				lisPersonal.remove(0);
				
				if ( (lisPersonal.size() % 100) == 0 ){
					System.out.println("Tamaxo del listor:"+lisPersonal.size());
				}
				
			} //termina while
						
			Conn.commit();
			Conn.close();
			
		}catch(Exception e){
			System.out.println(e);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	}
	
	public void validaCodigo() throws Exception{
		Statement stmt 	= null;
		ResultSet rs 	= null;
		ResultSet rs2 	= null;
		PreparedStatement ps =null;
		try{
			System.out.println("Conectando..!");
			Connection Conn = null;						
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Conn = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.20:1521:ora1", "enoc", "caminacondios");
			stmt 	= Conn.createStatement();
			rs 	= null;
			rs2 	= null;
			
			String nombre 	= "";
			String codigo1	= "";			
			String codigo2	= "";
			String res1		= "";
			String res2		= "";
			int rowTotal	= 0;
			
			ps= Conn.prepareStatement("SELECT ENOC.ALUM_NOMBRE(CODIGO) AS NOMBRE,CURSO_ID, CODIGO FROM ENOC.TEMPORAL"); 
			rs = ps.executeQuery();
			while (rs.next()){ //  && rowTotal<50	
				
				rowTotal++; res1 = ""; res2= "";
				nombre 	= rs.getString("NOMBRE"); 
				codigo1 = rs.getString("CURSO_ID").substring(0,7);
				codigo2 = rs.getString("CODIGO");
				
				//ANALIZA PRIMER CODIGO
				
				rs2 = stmt.executeQuery("SELECT CODIGO_PERSONAL FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = '"+codigo1+"'"); 
				if (rs2.next()) res1+= ",1"; else res1+=",0";
				
				rs2 = stmt.executeQuery("SELECT CODIGO_PERSONAL FROM ENOC.ALUM_UBICACION WHERE CODIGO_PERSONAL = '"+codigo1+"'"); 
				if (rs2.next()) res1+= ",1"; else res1+=",0";
				
				rs2 = stmt.executeQuery("SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ACADEMICO WHERE CODIGO_PERSONAL = '"+codigo1+"'"); 
				if (rs2.next()) res1+= ",1"; else res1+=",0";
				
				rs2 = stmt.executeQuery("SELECT CODIGO_PERSONAL FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = '"+codigo1+"'"); 
				if (rs2.next()) res1+= ",1"; else res1+=",0";
				
				rs2 = stmt.executeQuery("SELECT MATRICULA FROM ENOC.ARCH_DOCALUM WHERE MATRICULA = '"+codigo1+"'"); 
				if (rs2.next()) res1+= ",1"; else res1+=",0";
				
				rs2 = stmt.executeQuery("SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE CODIGO_PERSONAL = '"+codigo1+"'"); 
				if (rs2.next()) res1+= ",1"; else res1+=",0";
				
				rs2 = stmt.executeQuery("SELECT CODIGO_PERSONAL FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = '"+codigo1+"'");
				if (rs2.next()) res1+= ",1"; else res1+=",0";
				
				rs2 = stmt.executeQuery("SELECT CODIGO_PERSONAL FROM ENOC.KRDX_ALUMNO_EVAL WHERE CODIGO_PERSONAL = '"+codigo1+"'"); 
				if (rs2.next()) res1+= ",1"; else res1+=",0";
				
				rs2 = stmt.executeQuery("SELECT CODIGO_PERSONAL FROM ENOC.KRDX_CURSO_IMP WHERE CODIGO_PERSONAL = '"+codigo1+"'"); 
				if (rs2.next()) res1+= ",1"; else res1+=",0";
				
				rs2 = stmt.executeQuery("SELECT CODIGO_PERSONAL FROM ENOC.KRDX_CURSO_ACT WHERE CODIGO_PERSONAL = '"+codigo1+"'"); 
				if (rs2.next()) res1+= ",1"; else res1+=",0";				
				
				// ANALIZA SEGUNDO CODIGO
				
				rs2 = stmt.executeQuery("SELECT CODIGO_PERSONAL FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = '"+codigo2+"'"); 
				if (rs2.next()) res2+= ",1"; else res2+=",0";
				
				rs2 = stmt.executeQuery("SELECT CODIGO_PERSONAL FROM ENOC.ALUM_UBICACION WHERE CODIGO_PERSONAL = '"+codigo2+"'"); 
				if (rs2.next()) res2+= ",1"; else res2+=",0";
				
				rs2 = stmt.executeQuery("SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ACADEMICO WHERE CODIGO_PERSONAL = '"+codigo2+"'"); 
				if (rs2.next()) res2+= ",1"; else res2+=",0";
				
				rs2 = stmt.executeQuery("SELECT CODIGO_PERSONAL FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = '"+codigo2+"'"); 
				if (rs2.next()) res2+= ",1"; else res2+=",0";
				
				rs2 = stmt.executeQuery("SELECT MATRICULA FROM ENOC.ARCH_DOCALUM WHERE MATRICULA = '"+codigo2+"'"); 
				if (rs2.next()) res2+= ",1"; else res2+=",0";
				
				rs2 = stmt.executeQuery("SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE CODIGO_PERSONAL = '"+codigo2+"'"); 
				if (rs2.next()) res2+= ",1"; else res2+=",0";
				
				rs2 = stmt.executeQuery("SELECT CODIGO_PERSONAL FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = '"+codigo2+"'");
				if (rs2.next()) res2+= ",1"; else res2+=",0";
				
				rs2 = stmt.executeQuery("SELECT CODIGO_PERSONAL FROM ENOC.KRDX_ALUMNO_EVAL WHERE CODIGO_PERSONAL = '"+codigo2+"'"); 
				if (rs2.next()) res2+= ",1"; else res2+=",0";
				
				rs2 = stmt.executeQuery("SELECT CODIGO_PERSONAL FROM ENOC.KRDX_CURSO_IMP WHERE CODIGO_PERSONAL = '"+codigo2+"'"); 
				if (rs2.next()) res2+= ",1"; else res2+=",0";
				
				rs2 = stmt.executeQuery("SELECT CODIGO_PERSONAL FROM ENOC.KRDX_CURSO_ACT WHERE CODIGO_PERSONAL = '"+codigo2+"'"); 
				if (rs2.next()) res2+= ",1"; else res2+=",0";				
				
				System.out.println(nombre+","+codigo1+res1+","+codigo2+res2);
			}
				
			Conn.commit();
			Conn.close();			
			System.out.println("Fin -> "+rowTotal);			
		}catch(Exception e){
			System.out.println(e);
		}finally{
			if (rs2!=null) rs2.close();
			try { rs.close(); } catch (Exception ignore) { }
			try { stmt.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
	
	public static void main(String[] args) {
		try{
			Duplicados dup = new Duplicados();
			dup.BuscaDuplicados();
		}catch(Exception e){
			System.out.println(e);			
		}		
	}

}