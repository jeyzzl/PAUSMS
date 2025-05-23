//Bean del Kardex del Alumno( Formato del Kardex: determina que materias valen para el kardex del alumno).
package  aca.kardex;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class KrdxAlumnoActivUtil{
	
	public void setPromedioEstrategia(Connection conn) throws SQLException
	{
		Statement st1 				= conn.createStatement();
		ResultSet rs1 				= null;
		String comando1				= "";
		Statement st2 				= conn.createStatement();
		ResultSet rs2 				= null;
		String comando2				= "";
		Statement st3 				= conn.createStatement();
		ResultSet rs3 				= null;
		String comando3				= "";
		Statement st4 				= conn.createStatement();
		ResultSet rs4 				= null;
		String comando4				= "";
		Statement st5 				= conn.createStatement();
		ResultSet rs5 				= null;
		String comando5				= "";
		Statement st6 				= conn.createStatement();
		ResultSet rs6 				= null;
		String comando6				= "";
		String sCursoCargaId		= "";
		String sCodigoPersonal		= "";
		String sTipo				= "";
		
		int nNumEvaluaciones		= 0;
		float fSuma					= 0;
		float fValorEvaluacion		= 0;
		float fNotaEvaluacion		= 0;
		float fSumaValAct			= 0;
		
		try{
			ArrayList<String> lisVal				= new ArrayList<String>();
			//Query para sacar los curso_carga_id que tienen alguna actividad
			comando1 = "SELECT DISTINCT(CURSO_CARGA_ID) CURSO_CARGA_ID" +
					" FROM ENOC.CARGA_GRUPO_ACTIVIDAD" + 
					" ORDER BY 1";
			rs1 = st1.executeQuery(comando1);
			//While por cada curso_carga_id
			while(rs1.next())
			{
				sCursoCargaId = rs1.getString("CURSO_CARGA_ID");
				//Query para sacar la matricula de cada alumno que tienen calificacion en alguna actividad
				comando2 = "SELECT DISTINCT(CODIGO_PERSONAL) CODIGO_PERSONAL" +
						" FROM ENOC.KRDX_ALUMNO_ACTIV" + 
						" WHERE CURSO_CARGA_ID = '"+sCursoCargaId+"'";
				rs2 = st2.executeQuery(comando2);
				//Query para sacar la cantidad de Evaluaciones que existen en cierta estrategia
				comando3 = "SELECT MAX(EVALUACION_ID) EVALUACION_ID" +
				   		   " FROM ENOC.CARGA_GRUPO_ACTIVIDAD" + 
				   		   " WHERE CURSO_CARGA_ID = '"+sCursoCargaId+"'";
				rs3 = st3.executeQuery(comando3);
				if(rs3.next())
					nNumEvaluaciones = Integer.parseInt(rs3.getString("EVALUACION_ID"));
				//While por cada alumno del curso_carga_id sexalado
				while(rs2.next())
				{
					sCodigoPersonal = rs2.getString("CODIGO_PERSONAL");
					if(sCodigoPersonal.equals("100406"))
						System.err.println("Es tu matricula");
					//For por el total de Evaluaciones en el cruso_carga_id sexalado
						for(int i = 1; i <= nNumEvaluaciones; i++)
						{
							//Query para sacar la nota de cada actividad
							comando4 = "SELECT NOTA, ACTIVIDAD_ID" +
									" FROM ENOC.KRDX_ALUMNO_ACTIV" + 
									" WHERE ACTIVIDAD_ID IN (SELECT ACTIVIDAD_ID" +
														   " FROM ENOC.CARGA_GRUPO_ACTIVIDAD" + 
														   " WHERE CURSO_CARGA_ID = '"+sCursoCargaId+"'" +
														   " AND EVALUACION_ID = "+i+")" +
									" AND CODIGO_PERSONAL = '"+sCodigoPersonal+"'" +
									" ORDER BY ACTIVIDAD_ID";
							rs4 = st4.executeQuery(comando4);
							System.err.println("Este es el comando de tus notas");
							System.out.println(comando4);
							//Query para sacar el valor de cada actividad
							comando5 = "SELECT ACTIVIDAD_ID, VALOR " +
									   " FROM ENOC.CARGA_GRUPO_ACTIVIDAD" + 
									   " WHERE CURSO_CARGA_ID = '"+sCursoCargaId+"'" +
									   " AND EVALUACION_ID = " + i +
									   " ORDER BY ACTIVIDAD_ID";
							rs5 = st5.executeQuery(comando5);
							System.err.println("El comando 5 de "+sCodigoPersonal+" es: "+comando5);
							//While que guarda en un listor el id de actividad con su respectivo valor
							lisVal.clear();
							while(rs5.next())
							{
								lisVal.add(rs5.getString("ACTIVIDAD_ID"));
								lisVal.add(rs5.getString("VALOR"));
							}
							fSuma = 0;
							fSumaValAct = 0;
							//While para sacar los promedios de cada actividad por cada alumno y sumarlos
							while(rs4.next())
							{// NECESITO HACER LAS OPERACIONES POR CADA NOTA DE ACTIVIDADA Y COMPROBAR SI ES EL VALOR DE LA NOTA CORRESPONDIENTE
								String temp = rs4.getString("ACTIVIDAD_ID");
								System.out.println("--------for(int j = 0; j < "+lisVal.size()+"; j+=2)--------");
								for(int j = 0; j < lisVal.size(); j+=2)
								{
									System.out.println("if(temp.equals((String)"+lisVal.get(j)+"))");
									if(temp.equals((String)lisVal.get(j)))
									{
										System.out.println("fSuma("+(((Integer.parseInt(rs4.getString("NOTA")))*(Float.parseFloat((String)lisVal.get(j+1))))/100)+") = "+fSuma+" + (("+Integer.parseInt(rs4.getString("NOTA"))+"*"+Float.parseFloat((String)lisVal.get(j+1))+")/100);");
										fSuma = fSuma + (((Integer.parseInt(rs4.getString("NOTA")))*(Float.parseFloat((String)lisVal.get(j+1))))/100);
										fSumaValAct += Float.parseFloat((String)lisVal.get(j+1));
									}
								}
							}
							fSuma = (100*fSuma)/fSumaValAct;
							System.err.println("fSuma("+fSuma+") = (100*"+fSuma+")/"+fSumaValAct+";");
							//Query para sacar el valor de la actividad
							comando6 = "SELECT VALOR, TIPO" +
									   " FROM ENOC.CARGA_GRUPO_EVALUACION" + 
									   " WHERE CURSO_CARGA_ID = '"+sCursoCargaId+"'" +
									   " AND EVALUACION_ID = "+i;
							rs6 = st6.executeQuery(comando6);
							if(rs6.next()){
								fValorEvaluacion = rs6.getFloat("VALOR");
								sTipo			 = rs6.getString("TIPO");
							}
							System.out.println(fValorEvaluacion);
							//Se hace la operacion para sacar la nota de cada Estrategia por cada alumno
							System.out.println(sCodigoPersonal);
							System.out.println("El tipo de evaluacion es: --"+sTipo.trim()+"--");
							System.out.println("La suma antes de la operacion es: "+fSuma);
							if(sTipo.trim().equals("%"))
								fNotaEvaluacion = fSuma;
							else
								fNotaEvaluacion = (fValorEvaluacion * fSuma) / 100;
							System.out.println("La suma despues de la operacion es: "+fNotaEvaluacion);
							//fNotaEvaluacion = (fValorEvaluacion * fSuma) / 100;
							System.out.println(fNotaEvaluacion +0.5);
							fNotaEvaluacion = (int)(fNotaEvaluacion +0.5);
							System.out.println(comando6);
							System.out.println("Evalucion "+i+" = "+"("+fValorEvaluacion+" * "+fSuma+") / 100 = "+fNotaEvaluacion);
							//Inicializamos BEAN de la tabla para no repetir su contenido
							KrdxAlumnoEval kae = new KrdxAlumnoEval();
							kae.setCodigoPersonal(sCodigoPersonal);
							kae.setCursoCargaId(sCursoCargaId);
							kae.setEvaluacionId(String.valueOf(i));
							if(kae.existeReg(conn))
							{
								
								System.out.println("kae.mapeaRegId(conn,"+sCodigoPersonal+","+sCursoCargaId+","+String.valueOf(i)+");");
								System.out.println("kae.setNota("+String.valueOf(fNotaEvaluacion)+");");
								kae.mapeaRegId(conn,sCodigoPersonal,sCursoCargaId,String.valueOf(i));
								kae.setNota(String.valueOf(fNotaEvaluacion));
								if(!kae.updateReg(conn))
									System.out.println("No se hizo el update de: "+sCodigoPersonal+" en "+sCursoCargaId+" de la Evaluacion "+i+" con "+fNotaEvaluacion+"%");
							}
							else
							{
								System.err.println("No existe el registro");
								kae.setNota(String.valueOf(fNotaEvaluacion));
								kae.insertReg(conn);
							}
						}
				}
			}
		}
		catch(Exception ex)
		{
			System.out.println("Error - aca.kardex.KrdxAlumnoActivUtil|setPromedioEstrategia|:"+ex);
		}finally{
			st1.close();
			rs1.close();
			st2.close();
			rs2.close();
			st3.close();
			rs3.close();
			st4.close();
			rs4.close();
			st5.close();
			rs5.close();
			st6.close();
			rs6.close();
		}
		
	}
	
	public boolean setPromEstrID(Connection conn, String sCursoCargaId, String evaluacionId) throws SQLException{
		Statement st2 				= conn.createStatement();
		ResultSet rs2 				= null;
		String comando2				= "";
		Statement st4 				= conn.createStatement();
		ResultSet rs4 				= null;
		String comando4				= "";
		Statement st5 				= conn.createStatement();
		ResultSet rs5 				= null;
		String comando5				= "";
		Statement st6 				= conn.createStatement();
		ResultSet rs6 				= null;
		String comando6				= "";
		String sCodigoPersonal		= "";
		String sTipo				= "";
		
		float fSuma					= 0;
		float fValorEvaluacion		= 0;
		float fNotaEvaluacion		= 0;
		
		boolean ok 					= true;
		
		try{				
			ArrayList<String> lisVal				= new ArrayList<String>();			

			//Query para sacar la matricula de cada alumno que tienen calificacion en alguna actividad
			comando2 = "SELECT DISTINCT(CODIGO_PERSONAL) CODIGO_PERSONAL" +
					" FROM ENOC.KRDX_ALUMNO_ACTIV" + 
					" WHERE CURSO_CARGA_ID = '"+sCursoCargaId+"'";
			rs2 = st2.executeQuery(comando2);
			//While por cada alumno del curso_carga_id sexalado
			while(rs2.next()){
				sCodigoPersonal = rs2.getString("CODIGO_PERSONAL");
				
				//Query para sacar la nota de cada actividad
				comando4 = "SELECT COALESCE(NOTA,0) AS NOTA, ACTIVIDAD_ID" +
						" FROM ENOC.KRDX_ALUMNO_ACTIV" + 
						" WHERE ACTIVIDAD_ID IN (SELECT ACTIVIDAD_ID" +
											   " FROM ENOC.CARGA_GRUPO_ACTIVIDAD" + 
											   " WHERE CURSO_CARGA_ID = '"+sCursoCargaId+"'" +
											   " AND EVALUACION_ID = "+evaluacionId+")" +
						" AND CODIGO_PERSONAL = '"+sCodigoPersonal+"'" +
						" ORDER BY ACTIVIDAD_ID";
				rs4 = st4.executeQuery(comando4);
				
				
				//Query para sacar el valor de cada actividad
				comando5 = " SELECT ACTIVIDAD_ID, COALESCE(VALOR,0) AS VALOR " +
						   " FROM ENOC.CARGA_GRUPO_ACTIVIDAD" + 
						   " WHERE CURSO_CARGA_ID = '"+sCursoCargaId+"'" +
						   " AND EVALUACION_ID = " + evaluacionId +
						   " ORDER BY ACTIVIDAD_ID";
				rs5 = st5.executeQuery(comando5);
				
				//While que guarda en un listor el id de actividad con su respectivo valor
				lisVal.clear();
				while(rs5.next()){
					lisVal.add(rs5.getString("ACTIVIDAD_ID"));
					lisVal.add(rs5.getString("VALOR"));
				}
				fSuma = 0;								
				//While para sacar los promedios de cada actividad por cada alumno y sumarlos
				while(rs4.next())
				{// NECESITO HACER LAS OPERACIONES POR CADA NOTA DE ACTIVIDADA Y COMPROBAR SI ES EL VALOR DE LA NOTA CORRESPONDIENTE
					String temp = rs4.getString("ACTIVIDAD_ID");
					
					for(int j = 0; j < lisVal.size(); j+=2)
					{
						
						if(temp.equals((String)lisVal.get(j)))
						{
							
							fSuma = fSuma + (((Integer.parseInt(rs4.getString("NOTA")))*(Float.parseFloat((String)lisVal.get(j+1))))/100);
						}						
					}
				}
				//fSuma = (100*fSuma)/fSumaValAct;
				
				//Query para sacar el valor de la actividad
				comando6 = "SELECT COALESCE(VALOR,0) AS VALOR, TIPO" +
						   " FROM ENOC.CARGA_GRUPO_EVALUACION" + 
						   " WHERE CURSO_CARGA_ID = '"+sCursoCargaId+"'" +
						   " AND EVALUACION_ID = "+evaluacionId;
				rs6 = st6.executeQuery(comando6);
				if(rs6.next()){
					fValorEvaluacion = rs6.getFloat("VALOR");
					sTipo			 = rs6.getString("TIPO");
				}
				
				//Se hace la operacion para sacar la nota de cada Estrategia por cada alumno
				
				
				
				if(sTipo.trim().equals("%"))
					fNotaEvaluacion = fSuma;
				else
					fNotaEvaluacion = (fValorEvaluacion * fSuma) / 100;
				
				//fNotaEvaluacion = (fValorEvaluacion * fSuma) / 100;
				
				fNotaEvaluacion = (int)(fNotaEvaluacion +0.5);
				
				
				//Inicializamos BEAN de la tabla para no repetir su contenido
				KrdxAlumnoEval kae = new KrdxAlumnoEval();
				kae.setCodigoPersonal(sCodigoPersonal);
				kae.setCursoCargaId(sCursoCargaId);
				kae.setEvaluacionId(String.valueOf(evaluacionId));
				if(kae.existeReg(conn)){
					kae.mapeaRegId(conn,sCodigoPersonal,sCursoCargaId,String.valueOf(evaluacionId));
					kae.setNota(String.valueOf(fNotaEvaluacion));
					if(!kae.updateReg(conn))
						ok = false;
				}else{
					kae.setNota(String.valueOf(fNotaEvaluacion));
					if(!kae.insertReg(conn))
						ok = false;
				}
			
			}
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxAlumnoActivUtil|setPromEstrID|:"+ex);
			ok = false;
		}finally{
			st2.close();
			rs2.close();
			st4.close();
			rs4.close();
			st5.close();
			rs5.close();
			st6.close();
			rs6.close();			
		}
		
		return ok;
	}
	
	public HashMap<String, KrdxAlumnoActiv> mapActividadesMateria(Connection conn, String cursoCargaId) throws SQLException{
		
		HashMap<String, KrdxAlumnoActiv> mapNotas = new HashMap<String, KrdxAlumnoActiv>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String llave 		= "";
		
		try{
			comando = 	"SELECT " +
					"CODIGO_PERSONAL, ACTIVIDAD_ID, CURSO_CARGA_ID, NOTA, ACTIVIDAD_E42"+						
		    		" FROM ENOC.KRDX_ALUMNO_ACTIV"+ 
		    		" WHERE CURSO_CARGA_ID = '"+cursoCargaId+"'";			
			rs = st.executeQuery(comando);
			while(rs.next()){
				KrdxAlumnoActiv actividad = new KrdxAlumnoActiv();
				actividad.mapeaReg(rs);
				llave = rs.getString("CODIGO_PERSONAL")+rs.getString("ACTIVIDAD_ID");
				mapNotas.put(llave,actividad);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxAlumnoActivUtil|MapNotasMateria|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapNotas;
	}
	
	public HashMap<String, String> mapNotasMateria(Connection conn, String cursoCargaId) throws SQLException{
		
		HashMap<String, String> mapNotas = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = 	"SELECT CODIGO_PERSONAL, ACTIVIDAD_ID, COALESCE(NOTA,0) AS NOTA"+				
		    		" FROM ENOC.KRDX_ALUMNO_ACTIV"+ 
		    		" WHERE CURSO_CARGA_ID = '"+cursoCargaId+"'";			
			rs = st.executeQuery(comando);
			while(rs.next()){				
				mapNotas.put(rs.getString("CODIGO_PERSONAL")+rs.getString("ACTIVIDAD_ID"), rs.getString("NOTA"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxAlumnoActivUtil|MapNotasMateria|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapNotas;
	}
	
	public HashMap<String, String> mapaNumActividadesEvaluadas(Connection conn, String cargaId) throws SQLException{
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT CURSO_CARGA_ID, COUNT(DISTINCT(ACTIVIDAD_ID)) AS TOTAL FROM ENOC.KRDX_ALUMNO_ACTIV"
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) = '"+cargaId+"'"
					+ " GROUP BY CURSO_CARGA_ID";
			rs = st.executeQuery(comando);
			while(rs.next()){				
				mapa.put(rs.getString("CURSO_CARGA_ID"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxAlumnoActivUtil|mapaNumActividadesEvaluadas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapa;
	}
	
}