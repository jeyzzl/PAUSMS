package aca.proyectos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

public class EmpLogro {
	private Connection c;
	private String error;
	
	public EmpLogro(Connection con) {
		c=con;
	}
	       
	
	public List<String> getPuestoEmpleado(String empleado, String ejercicio){
		List<String> salida = new ArrayList();		
		try{
			PreparedStatement pst = c.prepareStatement("select epo.id_ccosto from aron.empleado_puestos_op epo "
					+ "join aron.empleado ae on ae.id=epo.empleado_id and ae.clave=? "
					+ "where epo.status='A' "
					+ "and epo.id_ejercicio=?");
			pst.setString(1, empleado);
			pst.setString(2, ejercicio);
			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
				salida.add(rs.getString("id_ccosto"));
			}
			
			rs.close();
			pst.close();
			
		}catch(SQLException sqle){
			System.err.println("getPuestoEmpleado "+sqle);
		}
		return salida;
	}
	
	public List<String> getJefePuesto(String puesto, String ejercicio){
		List<String> salida = new ArrayList();
		
		try{
			PreparedStatement pst = c.prepareStatement("select cf.ccosto_id, ae.clave from aron.cat_jefes cf "
					+ " join aron.empleado ae on ae.id=cf.jefe_id "
					+ " where cf.ejercicio_id=? and cf.ccosto_id=? and cf.status='A' "
					+ "");
			pst.setString(1, ejercicio);
			pst.setString(2, puesto);

			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
				salida.add(rs.getString("clave"));
			}
			
			rs.close();
			pst.close();
			
		}catch(SQLException sqle){
			System.err.println("getJefePuesto "+sqle);
		}
		
		return salida;
	}
	
	public List<String> getJefexDepto(String jefe, String ejercicio){
		List<String> salida = new ArrayList();
		
		try{
			PreparedStatement pst = c.prepareStatement("select cf.ccosto_id, ae.clave from aron.cat_jefes cf "
					+ " join aron.empleado ae on ae.id=cf.jefe_id and ae.clave=? "
					+ " where cf.ejercicio_id=?  and cf.status='A'");
			pst.setString(1, jefe );
			pst.setString(2, ejercicio);
			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
				salida.add(rs.getString("ccosto_id"));
			}
			
			rs.close();
			pst.close();
			
		}catch(SQLException sqle){
			System.err.println("getJefePuesto "+sqle);
		}
		
		return salida;
	}
	
	public List<String> getEmpleadosxPuesto(String puesto, String ejercicio){
		List<String> salida = new ArrayList();
		
		try{
			PreparedStatement pst = c.prepareStatement("select epo.id_ccosto, ae.clave from aron.empleado_puestos_op epo "
					+ "join aron.empleado ae on ae.id=epo.empleado_id "
					+ "where epo.status='A' "
					+ "and epo.id_ejercicio=? and epo.id_ccosto=?");
			pst.setString(1, ejercicio);
			pst.setString(2, puesto);
			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
				salida.add(rs.getString("clave"));
			}
			
			rs.close();
			pst.close();
			
		}catch(SQLException sqle){
			System.err.println("getEmpleadosxPuesto "+sqle);
		}
		return salida;
	}
	
	public List<String> getEmpleadosxPuesto(String puesto, String ejercicio, String codigopersonal){
		List<String> salida = new ArrayList();
		
		try{
			PreparedStatement pst = c.prepareStatement("select epo.id_ccosto, ae.clave from aron.empleado_puestos_op epo "
					+ "join aron.empleado ae on ae.id=epo.empleado_id "
					+ "where epo.status='A' "
					+ "and epo.id_ejercicio=? and epo.id_ccosto=? ");
			pst.setString(1, ejercicio);
			pst.setString(2, puesto);
			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
				salida.add(rs.getString("clave"));
			}
			
//			if(!salida.contains(codigopersonal)){
//				salida = new ArrayList<String>();
//			}
//			
			
			rs.close();
			pst.close();
			
		}catch(SQLException sqle){
			System.err.println("getEmpleadosxPuesto "+sqle);
		}
		return salida;
	}
	
	
	public Map<String, String> getJefes(String id_ejercicio){
		Map<String, String> salida = new HashMap();
		try{
			PreparedStatement pst = c.prepareStatement("select cf.ccosto_id, ae.clave from aron.cat_jefes cf "
					+ " join aron.empleado ae on ae.id=cf.jefe_id "
					+ " where cf.ejercicio_id=? and cf.status='A'");
			pst.setString(1, id_ejercicio);
			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
				salida.put(rs.getString("ccosto_id"),rs.getString("clave"));
			}
			rs.close();
			pst.close();
			
		}catch(SQLException sqle){
			System.err.println(sqle);
		}
		return salida;
	}
	
	
	
	public Map<String, List<String>> getEmpleados(String id_ejercicio){
		Map<String, List<String>> salida = new HashMap();
		try{
			PreparedStatement pstA = c.prepareStatement("select distinct(id_ccosto) from aron.empleado_puestos_op where id_ejercicio=? and status='A'");
			PreparedStatement pstB = c.prepareStatement("select ep.empleado_id, ae.clave from aron.empleado_puestos_op ep "
					+ " join aron.empleado ae on ae.id=ep.empleado_id "
					+ " where ep.id_ejercicio=? and ep.status='A' and ep.id_ccosto=?");
			pstA.setString(1, id_ejercicio);
			
			ResultSet rs = pstA.executeQuery();
			
			while(rs.next()){
				
				pstB.setString(1, id_ejercicio);
				pstB.setString(1, rs.getString("id_ccosto"));
				ResultSet rsb = pstB.executeQuery();
				List<String> idemp = new ArrayList();
				while(rsb.next()){
					idemp.add(rs.getString("clave"));
				}
				if(!idemp.isEmpty()){
					salida.put(rs.getString("id_ccosto"), idemp);
				}
				rsb.close();
			}
			rs.close();
			pstA.close();
			pstB.close();
			
		}catch(SQLException sqle){
			System.err.println(sqle);
		}
		return salida;
	}
	
	    public Logro getLogro(int id) {
	        Logro dme = null;
	        try {
	        	
	        	PreparedStatement pst = c.prepareStatement("SELECT ID, ID_METAINST, FECHA_CREADA, to_char(FECHA_FINAL,'dd/mm/yyyy') as FECHA_FINAL, DESCRIPCION, INDICADOR, CODIGOPERSONAL, DEPTO, ESTADO "
	                    + "FROM DANIEL.EMP_LOGROS WHERE ID=?");
	        	
	            pst.setInt(1, id);
	            ResultSet rs = pst.executeQuery();
	                if (rs.next()) {
	                    
	                    dme = new Logro();
	                    dme.setId(rs.getInt("id"));
	                    dme.setId_metainst(rs.getInt("id_metainst"));
	                    dme.setFecha_creada(rs.getString("fecha_creada"));
	                    dme.setFecha_final(rs.getString("fecha_final"));
	                    dme.setDescripcion(rs.getString("descripcion"));
	                    dme.setIndicador(rs.getString("indicador"));
	                    dme.setCodigopersonal(rs.getString("codigopersonal"));
	                    dme.setDepto(rs.getString("depto"));
	                    dme.setEstado(rs.getString("estado"));
	                    
	                    
	                    
	                }
	                rs.close();
	            pst.close();
	        } catch (SQLException sqle) {
	            System.err.println("Error en PreparedStatement getProyecto " + sqle);
	        }
	        return dme;
	    }
	    
	    public List<Logro> listLogros(int id_meta, String codigopersonal, String depto) {
	        List<Logro> salida = new ArrayList<Logro>();
	        
	        try {
	        	String sql = "SELECT ID, ID_METAINST, FECHA_CREADA, to_char(FECHA_FINAL,'dd/mm/yyyy') as FECHA_FINAL, DESCRIPCION, INDICADOR, CODIGOPERSONAL, DEPTO, ESTADO, COMENTARIO "
	                    + "FROM DANIEL.EMP_LOGROS WHERE ESTADO='A' ";
	        	if(id_meta!=0){
	        		sql += " AND ID_METAINST="+id_meta+" ";
	        	}
	        	if(!codigopersonal.equals("")){
	        		sql += " AND CODIGOPERSONAL='" +codigopersonal +"' ";
	        	}
	        	if(!depto.equals("")){
	        		sql += " AND DEPTO='"+depto+"'";
	        	}
	        	
	        	PreparedStatement pst = c.prepareStatement(sql);
	        	
	            ResultSet rs = pst.executeQuery();
	                
	                while (rs.next()) {
	                	Logro dme ;
	                    dme = new Logro();
	                    dme.setId(rs.getInt("id"));
	                    dme.setId_metainst(rs.getInt("id_metainst"));
	                    dme.setFecha_creada(rs.getString("fecha_creada"));
	                    dme.setFecha_final(rs.getString("fecha_final"));
	                    dme.setDescripcion(rs.getString("descripcion"));
	                    dme.setIndicador(rs.getString("indicador"));
	                    dme.setCodigopersonal(rs.getString("codigopersonal"));
	                    dme.setDepto(rs.getString("depto"));
	                    dme.setEstado(rs.getString("estado"));
	                    dme.setComentario(rs.getString("comentario"));
	                    
	                    salida.add(dme);
	                }
	                rs.close();
	            pst.close();
	            
	            
	        } catch (SQLException sqle) {
	            System.err.println("Error en PreparedStatement listLogros " + sqle);
	            setError(sqle.toString());
	        }
	        return salida;
	    }
	    
	    
	    public List<Logro> listLogrosDepto(String depto, String codigoPersonalJefe) {
	        List<Logro> salida = new ArrayList<Logro>();
	        
	        try {
	        	String sql = "SELECT ID, ID_METAINST, FECHA_CREADA, to_char(FECHA_FINAL,'dd/mm/yyyy') as FECHA_FINAL, DESCRIPCION, INDICADOR, CODIGOPERSONAL, DEPTO, ESTADO, COMENTARIO "
	                    + " FROM DANIEL.EMP_LOGROS WHERE ESTADO='A' AND DEPTO='"+depto+"'"
	                   	+ " AND INSTR((SELECT DEPARTAMENTOS FROM DANIEL.POR_JEFES WHERE CODIGO_PERSONAL = '"+codigoPersonalJefe+"'),DEPTO) > 0"
	                    + " ORDER BY CODIGOPERSONAL ";
	        	
	        	
	        	PreparedStatement pst = c.prepareStatement(sql);
	        	
	            ResultSet rs = pst.executeQuery();
	                
	                while (rs.next()) {
	                	Logro dme ;
	                    dme = new Logro();
	                    dme.setId(rs.getInt("id"));
	                    dme.setId_metainst(rs.getInt("id_metainst"));
	                    dme.setFecha_creada(rs.getString("fecha_creada"));
	                    dme.setFecha_final(rs.getString("fecha_final"));
	                    dme.setDescripcion(rs.getString("descripcion"));
	                    dme.setIndicador(rs.getString("indicador"));
	                    dme.setCodigopersonal(rs.getString("codigopersonal"));
	                    dme.setDepto(rs.getString("depto"));
	                    dme.setEstado(rs.getString("estado"));
	                    dme.setComentario(rs.getString("comentario"));
	                    
	                    salida.add(dme);
	                }
	                rs.close();
	            pst.close();
	            
	            
	        } catch (SQLException sqle) {
	            System.err.println("Error en PreparedStatement listLogros " + sqle);
	            setError(sqle.toString());
	        }
	        return salida;
	    }
	    
	    
	    public List<Logro> listLogrosMeta(String metaId, String codigoPersonalJefe) {
	        List<Logro> salida = new ArrayList<Logro>();
	        
	        try {
	        	String sql = "SELECT ID, ID_METAINST, FECHA_CREADA, to_char(FECHA_FINAL,'dd/mm/yyyy') as FECHA_FINAL, DESCRIPCION, INDICADOR, CODIGOPERSONAL, DEPTO, ESTADO, COMENTARIO "
	                    + " FROM DANIEL.EMP_LOGROS WHERE ESTADO='A' AND ID_METAINST='"+metaId+"' "
	                    + " AND INSTR((SELECT DEPARTAMENTOS FROM DANIEL.POR_JEFES WHERE CODIGO_PERSONAL = '"+codigoPersonalJefe+"'),DEPTO) > 0"
	                    + " ORDER BY CODIGOPERSONAL ";
	        	
	        	
	        	PreparedStatement pst = c.prepareStatement(sql);
	        	
	            ResultSet rs = pst.executeQuery();
	                
	                while (rs.next()) {
	                	Logro dme ;
	                    dme = new Logro();
	                    dme.setId(rs.getInt("id"));
	                    dme.setId_metainst(rs.getInt("id_metainst"));
	                    dme.setFecha_creada(rs.getString("fecha_creada"));
	                    dme.setFecha_final(rs.getString("fecha_final"));
	                    dme.setDescripcion(rs.getString("descripcion"));
	                    dme.setIndicador(rs.getString("indicador"));
	                    dme.setCodigopersonal(rs.getString("codigopersonal"));
	                    dme.setDepto(rs.getString("depto"));
	                    dme.setEstado(rs.getString("estado"));
	                    dme.setComentario(rs.getString("comentario"));
	                    
	                    salida.add(dme);
	                }
	                rs.close();
	            pst.close();
	            
	            
	        } catch (SQLException sqle) {
	            System.err.println("Error en PreparedStatement listLogros " + sqle);
	            setError(sqle.toString());
	        }
	        return salida;
	    }
	    
	    
	    public ArrayList<String> listLogrosJefe(String codigoPersonalJefe) {
	    	ArrayList<String> list = new ArrayList<String>();
	        
	        try {
	        	String sql = "SELECT DISTINCT( CODIGOPERSONAL ) AS CODIGOPERSONAL"
	        			+ " FROM DANIEL.EMP_LOGROS WHERE INSTR((SELECT DEPARTAMENTOS FROM DANIEL.POR_JEFES WHERE CODIGO_PERSONAL = '"+codigoPersonalJefe+"'),DEPTO) > 0 ";
	        	
	        	PreparedStatement pst = c.prepareStatement(sql);
	            ResultSet rs = pst.executeQuery();
	                
	                while (rs.next()) {
	                    list.add(rs.getString("codigopersonal"));
	                }
	                rs.close();
	            pst.close();
	            
	            
	        } catch (SQLException sqle) {
	            System.err.println("Error en PreparedStatement listLogrosJefe " + sqle);
	            setError(sqle.toString());
	        }
	        return list;
	    }
	    
	    
	public List<EvaluacionBase> jefes(){
	    	List<EvaluacionBase> salida = new ArrayList<EvaluacionBase>();
	    	try{
	    		PreparedStatement pst = c.prepareStatement("select ee.CLAVEEMPLEADO ,ee.CLAVEJEFE ," +
	    				"ae.NOMBRE || ' ' || ae.APPATERNO || ' ' || ae.APMATERNO as nombrejefe,  " +
	    				"ee.CCOSTO ,ee.AUTOEVALUACION ,ee.JEFEEVALUA ," +
	    				"ee.EVALUAJEFE from aron.empleado_evalua ee " +
	    				"inner join aron.empleado ae on ae.clave= ee.clavejefe");
	    		ResultSet rs = pst.executeQuery();
	    		while(rs.next()){
	    			EvaluacionBase eb = new EvaluacionBase();
	    			eb.setAutoevaluacion(rs.getString("AUTOEVALUACION"));
	    			eb.setCcosto(rs.getString("CCOSTO"));
	    			eb.setClaveempleado(rs.getString("CLAVEEMPLEADO"));
	    			eb.setClavejefe(rs.getString("CLAVEJEFE"));
	    			eb.setEvaluajefe(rs.getString("EVALUAJEFE"));
	    			eb.setJefeevalua(rs.getString("JEFEEVALUA"));
	    			eb.setNombreJefe(rs.getString("nombrejefe"));
	    			
	    			salida.add(eb);
	    		}
	    		rs.close();
	    		pst.close();
	    	}catch(SQLException sqle){
	    		System.out.println("jefes error " + sqle );
	    	}
	    	return salida;
	    }
	    
	    public ArrayList<String> mijefe(String codigoPersonalempleado) {
	    	ArrayList<String> list = new ArrayList<String>();
	        
	        try {
	        	String sql = "SELECT AE.CLAVE,  " +
	        			"(AE.NOMBRE || ' ' || AE.APPATERNO || ' ' || AE.APMATERNO) nombre " +
	        			"FROM ARON.EMPLEADO AE, ARON.CAT_JEFES CF WHERE CF.EJERCICIO_ID='001-2014' AND CF.CCOSTO_ID IN" +
	        			"(SELECT ID_CCOSTO FROM ARON.EMPLEADO_PUESTOS where STATUS='A' AND EMPLEADO_ID in " +
	        			"( select id from ARON.EMPLEADO where clave=? )) AND AE.ID=CF.JEFE_ID ";
	        	
	        	PreparedStatement pst = c.prepareStatement(sql);
	        	
	        	pst.setString(1, codigoPersonalempleado);
	        	
	            ResultSet rs = pst.executeQuery();
	                
	                while (rs.next()) {
	                    list.add(rs.getString("nombre"));
	                }
	                rs.close();
	            pst.close();
	            
	            
	        } catch (SQLException sqle) {
	            System.err.println("Error en PreparedStatement soyJefeDe " + sqle);
	            setError(sqle.toString());
	        }
	        return list;
	    }
	    
	    public ArrayList<String> soyJefeDe(String codigoPersonalJefe) {
	    	ArrayList<String> list = new ArrayList<String>();
	        
	        try {
	        	String sql = "SELECT CODIGO_PERSONAL AS CODIGOPERSONAL"
	        			+ " FROM DANIEL.POR_JEFES WHERE JEFEDELJEFE=? ";
	        	
	        	PreparedStatement pst = c.prepareStatement(sql);
	        	
	        	pst.setString(1, codigoPersonalJefe);
	        	
	            ResultSet rs = pst.executeQuery();
	                
	                while (rs.next()) {
	                    list.add(rs.getString("codigopersonal"));
	                }
	                rs.close();
	            pst.close();
	            
	            
	        } catch (SQLException sqle) {
	            System.err.println("Error en PreparedStatement soyJefeDe " + sqle);
	            setError(sqle.toString());
	        }
	        return list;
	    }
	    
	    
	    
	    
	    public int addEmpLogro(HttpServletRequest request) {
	        boolean pasa = true;
	        if (request.getParameter("id_metainst").equals("")) {
	            pasa = false;
	        }
	        if (request.getParameter("fecha_final").equals("")) {
	            pasa = false;
	        }
	        if (request.getParameter("indicador").equals("")) {
	            pasa = false;
	        }
	        if (request.getParameter("descripcion").equals("")) {
	            pasa = false;
	        }
	        
	        int salida=0;
	        if (pasa) {
	            try {
	            	
	            	PreparedStatement pst = c.prepareStatement("INSERT INTO DANIEL.EMP_LOGROS "
	                        + "(ID_METAINST, FECHA_FINAL, DESCRIPCION, INDICADOR, CODIGOPERSONAL, DEPTO)"
	            			+ "VALUES(?,to_date(?,'dd/mm/yyyy'),?,?,?,?)");
	            	
	                pst.setInt(1, Integer.valueOf(request.getParameter("id_metainst")));
	                pst.setString(2, request.getParameter("fecha_final"));
	                pst.setString(3, request.getParameter("descripcion"));
	                pst.setString(4, request.getParameter("indicador"));
	                pst.setString(5, request.getParameter("codigopersonal"));
	                pst.setString(6, request.getParameter("depto"));
	                salida = pst.executeUpdate();
	                pst.close();
	            } catch (SQLException sqle) {
	                System.err.println("Error en addEmpLogro " + sqle);
	                setError(sqle.toString());
	                
	            }
	        }else{
	        	setError("uno o mas datos hacen falta llenar del formulario");
	        }
	        return salida;
	    }
	    
	    public int eliminaEmpLogro(HttpServletRequest request) {
	        boolean pasa = true;
	        
	        
	        if (request.getParameter("idlogro").equals("")) {
	            pasa = false;
	        }
	        
	        int salida=0;
	        if (pasa) {
	            try {
	            	
	            	OPActividad opa = new OPActividad(c);
	            	
	            	PreparedStatement pstb = c.prepareStatement("SELECT * FROM DANIEL.DPTO_ACTIVIDADES WHERE ID_PROYECTO=? ");
	            	pstb.setInt(1, Integer.valueOf((String)request.getParameter("idlogro")));
	            	ResultSet rsb = pstb.executeQuery();
	            	while(rsb.next()){
	            		
	            		opa.eliminaActividad(rsb.getInt("id"));
	            	}
	            	rsb.close();
	            	pstb.close();
	            	
	            	PreparedStatement pst = c.prepareStatement("UPDATE DANIEL.EMP_LOGROS SET "
	                        + "FECHA_CREADA=now(),ESTADO='I'  "
	            			+" WHERE ID=?");
	                pst.setInt(1, Integer.valueOf((String) request.getParameter("idlogro")));
	                salida = pst.executeUpdate();
	                pst.close();
	            } catch (SQLException sqle) {
	                System.err.println("Error en eliminaEmpLogro " + sqle);
	                setError(sqle.toString());
	                
	            }
	        }else{
	        	setError("uno o mas datos hacen falta llenar del formulario");
	        }
	        return salida;
	    }
	    
	    public int editaEmpLogro(HttpServletRequest request) {
	        boolean pasa = true;
	        
	        if (request.getParameter("id").equals("")) {
	            pasa = false;
	        }
	        if (request.getParameter("id_metainst").equals("")) {
	            pasa = false;
	        }
	        if (request.getParameter("fecha_final").equals("")) {
	            pasa = false;
	        }
	        if (request.getParameter("indicador").equals("")) {
	            pasa = false;
	        }
	        if (request.getParameter("descripcion").equals("")) {
	            pasa = false;
	        }
	        
	        int salida=0;
	        if (pasa) {
	            try {
	            	
	            	PreparedStatement pst = c.prepareStatement("UPDATE DANIEL.EMP_LOGROS SET "
	                        + "ID_METAINST=?, FECHA_FINAL=TO_DATE(?,'DD/MM/YYYY'), DESCRIPCION=?, INDICADOR=?, CODIGOPERSONAL=?, DEPTO=?, FECHA_CREADA=now() "
	            			+" WHERE ID=?");
	            	
	                pst.setInt(1, Integer.valueOf(request.getParameter("id_metainst")));
	                pst.setString(2, request.getParameter("fecha_final"));
	                pst.setString(3, request.getParameter("descripcion"));
	                pst.setString(4, request.getParameter("indicador"));
	                pst.setString(5, request.getParameter("codigopersonal"));
	                pst.setString(6, request.getParameter("depto"));
	                pst.setInt(7, Integer.valueOf((String) request.getParameter("id")));
	                salida = pst.executeUpdate();
	                pst.close();
	            } catch (SQLException sqle) {
	                System.err.println("Error en editaEmpLogro " + sqle);
	                setError(sqle.toString());
	                
	            }
	        }else{
	        	setError("uno o mas datos hacen falta llenar del formulario");
	        }
	        return salida;
	    }
	    
	    public List<String> deptosByJefes(String clave, String id_ejercicio) {
	    	ArrayList<String> salida = new ArrayList<String>();

	        try {
	        	
	        	PreparedStatement pst = c.prepareStatement(""
	                    + "SELECT "
	                    + "     J.CCOSTO_ID "
	                    + "FROM "
	                    + "     ARON.CAT_JEFES J, "
	                    + "     ARON.EMPLEADO E "
	                    + "WHERE "
	                    + "     E.CLAVE=? "
	                    + "     AND J.JEFE_ID=E.ID "
	                    + "     AND J.EJERCICIO_ID=?");

	            pst.setString(1, clave);
	            pst.setString(2, id_ejercicio);

	            ResultSet rs = pst.executeQuery();
	                while (rs.next()) {
	                    salida.add(rs.getString("ccosto_id"));
	                }
	                rs.close();
	           
	            pst.close();
	        } catch (SQLException sqle) {
	            System.err.println("Error en deptosByJefes " + sqle);
	        }

	        return salida;
	    }
	    
	    public List<String> deptosEmpleado(String codigopersonal, String ejercicio_id) {
	    	ArrayList<String> salida = new ArrayList<String>();

	        try {
	        	
	        	PreparedStatement pst = c.prepareStatement(""
	                    + "SELECT "
	                    + "     EP.ID_CCOSTO "
	                    + "FROM "
	                    + "     ARON.EMPLEADO_PUESTOS EP, "
	                    + "     ARON.EMPLEADO E "
	                    + "WHERE "
	                    + "     E.CLAVE=? "
	                    + "     AND EP.EMPLEADO_ID=E.ID "
	                    + "     AND EP.ID_EJERCICIO=?");

	            pst.setString(1, codigopersonal);
	            pst.setString(2, ejercicio_id);

	            ResultSet rs = pst.executeQuery();
	                while (rs.next()) {
	                    salida.add(rs.getString("id_ccosto"));
	                }
	                rs.close();
	            pst.close();
	        } catch (SQLException sqle) {
	            System.err.println("Error en deptosEmpleado " + sqle);
	        }

	        return salida;
	    }
	    
	    public List<String> deptos(String ejercicio_id) {
	    	ArrayList<String> salida = new ArrayList<String>();

	        try {
	        	
	        	PreparedStatement pst = c.prepareStatement(""
	                    + "SELECT "
	                    + "     DISTINCT(ID_CCOSTO) "
	                    + "FROM "
	                    + "     ARON.EMPLEADO_PUESTOS  "
	                    + "WHERE "
	                    + "     ID_EJERCICIO=?" 
	                    + "     AND STATUS='A'  AND (ID_CCOSTO LIKE '1.%' OR ID_CCOSTO LIKE '2.%')" +
	                    " ORDER BY ID_CCOSTO");

	            pst.setString(1, ejercicio_id);

	            ResultSet rs = pst.executeQuery();
	                while (rs.next()) {
	                    salida.add(rs.getString("id_ccosto"));
	                }
	                rs.close();
	            pst.close();
	        } catch (SQLException sqle) {
	            System.err.println("Error en deptos " + sqle);
	        }

	        return salida;
	    }

	    public String getCcostoNombre( String ccosto, String id_ejercicio) {
	        String salida = "";

	        try {
	        	
	        	PreparedStatement pst = c.prepareStatement("SELECT nombre FROM MATEO.CONT_CCOSTO WHERE  id_ejercicio=?  AND ID_CCOSTO=?");
	        	
	            pst.setString(1, id_ejercicio);
	            pst.setString(2, ccosto);
	            ResultSet rs = pst.executeQuery();
	                while (rs.next()) {
	                    salida = rs.getString("nombre");
	                }
	                rs.close();
	            
	            pst.close();
	        } catch (SQLException sqle) {
	            System.err.println("Error en getCcostoNombre " + sqle);

	        }

	        return salida;
	    }
	    
	    
	    
	    public void cambiaProyectos(HttpServletRequest request) {
	        boolean pasa = true;
	        if (request.getParameter("TKL_id_meta").equals("")) {
	            pasa = false;
	        }
	        if (request.getParameter("descripcion").equals("")) {
	            pasa = false;
	        }
	        if (pasa) {
	            try {
	            	
	            	PreparedStatement pst = c.prepareStatement("UPDATE DANIEL.DPTO_PROYECTOS SET "
	                        + "FECHA_INICIO=?, FECHA_FINAL=?, DESCRIPCION=?, ESTADO=? FECHA_CREACION=now() WHERE ID=?");
	            	
	                pst.setString(1, request.getParameter("fecha_inicio"));
	                pst.setString(2, request.getParameter("fecha_final"));
	                pst.setString(3, request.getParameter("descripcion"));
	                pst.setString(4, "A");
	                pst.setInt(5, Integer.valueOf((String)request.getParameter("id")));
	                pst.executeUpdate();
	                pst.close();
	            } catch (SQLException sqle) {
	                System.err.println("Error en cambiaProyectos " + sqle);
	            }
	        }
	    }

		public String getError() {
			return error;
		}

		public void setError(String error) {
			this.error = error;
		}
		
		
		public void editaComentario(HttpServletRequest request) {
	        boolean pasa = true;
	        
	        
	        if (pasa) {
	            try {
	            	
	            	PreparedStatement pst = c.prepareStatement("UPDATE DANIEL.EMP_LOGROS SET "
	                        + "COMENTARIO = ? WHERE ID=?");
	            	
	                pst.setString(1, request.getParameter("comentario"+request.getParameter("id")));
	                pst.setInt(2, Integer.valueOf((String)request.getParameter("id")));
	                
	                pst.executeUpdate();
	                pst.close();
	            } catch (SQLException sqle) {
	                System.err.println("Error en cambiaProyectos " + sqle);
	            }
	        }
	    }
		
		

}
