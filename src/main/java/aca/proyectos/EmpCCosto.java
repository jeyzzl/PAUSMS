package aca.proyectos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import jakarta.servlet.http.HttpServletRequest;

public class EmpCCosto {

	Connection con;
	Vector va;
	Vector vb;
	HashSet hs;
    Date fecha = new Date();
    
	public EmpCCosto(Connection conEnoc) {
		// TODO Auto-generated constructor stub
		con=conEnoc;
	}
	
public String guardar(HttpServletRequest request)throws SQLException, ParseException{
		
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                
        SimpleDateFormat sdfb = new SimpleDateFormat("yyyy");
        System.out.println(sdfb.format(fecha));
		String[] cuentas = request.getParameterValues("cuentasEmpleado");
		String[] empleado = request.getParameterValues("empleadono");
		
		
		PreparedStatement psta=con.prepareStatement("DELETE FROM  DANIEL.POR_JEFES " +
                "WHERE CODIGO_PERSONAL='"+empleado[0]+"' AND EJERCICIO_ID='001-"+sdfb.format(fecha)+"'");
		int eliminados = psta.executeUpdate();
		psta.close();
		psta = con.prepareStatement("SELECT MAX(ECC.ID_EMPLEADO_CC) " +
                "FROM MATEO.CCP_EMPLEADO_CCOSTO ECC " +
                "WHERE ECC.CLAVEEMPLEADO='"+empleado[0]+"'");
		ResultSet rsa = psta.executeQuery();
		
		int maximo = 0; 
		if(rsa.next()){	
			maximo = rsa.getInt(1);
		}
		rsa.close();
		psta.close();
		String outFinal ="";
		System.out.println(fecha + "---------"+ maximo);
                int salidatotal=0;
		try{
			if(cuentas.length > 0){
				String ccostosFin = "";
				for(int i=0;i<cuentas.length;i++){
					ccostosFin+=cuentas[i]+"-";
				}
					maximo++;
					psta = con.prepareStatement("INSERT INTO DANIEL.POR_JEFES (CODIGO_PERSONAL, EJERCICIO_ID, DEPARTAMENTOS)" +
							" VALUES(?,?,?)");
					psta.setString(1,empleado[0]);
					psta.setString(2,"001-"+sdfb.format(fecha)+"");
					psta.setString(3,ccostosFin);
					System.out.println(maximo);
					int salidapst = psta.executeUpdate() ;
					System.out.println(salidapst);
					salidatotal += salidapst;
					outFinal = " " + salidatotal ;
					psta.close();
				
			}
		}catch(Exception e){
			System.out.println("insertar " + e);
		}
		return "iNGRESADOS>>" +outFinal + "<BR> eliminados >> " + eliminados;
	}
	
	public Vector cuentasGenerales()throws SQLException, ParseException{
        
        SimpleDateFormat sdfb = new SimpleDateFormat("yyyy");
        
		va = new Vector();
                System.out.println(sdfb.format(fecha));
	
		try{
			PreparedStatement pst = con.prepareStatement("SELECT * " +
                    "FROM MATEO.CONT_CCOSTO " +
                    "WHERE ID_EJERCICIO='001-"+sdfb.format(fecha)+"' AND ID_CCOSTO LIKE '1.%' AND DETALLE='S' ORDER BY NOMBRE");
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				va.addElement(rs.getString(2)+","+rs.getString(3));
			}
			rs.close();
			pst.close();
		
		}catch(SQLException e){
			System.out.println("error en cuentas generales " + e);
		}
		
		return va;
	}


	public void cuentaPersonal(String noempleado){
       
        SimpleDateFormat sdfb = new SimpleDateFormat("yyyy");     
         System.out.println(sdfb.format(fecha));
		vb = new Vector();
		hs = new HashSet();
		
		String oldSQL = "SELECT ID_CCOSTO FROM MATEO.CCP_EMPLEADO_CCOSTO ECC " +
				"WHERE ECC.ID_EJERCICIO='001-"+sdfb.format(fecha)+"' AND ECC.CLAVEEMPLEADO='"+noempleado+"'";
		
		String sql = "SELECT CODIGO_PERSONAL,EJERCICIO_ID,DEPARTAMENTOS,ESTADO FROM DANIEL.POR_JEFES WHERE EJERCICIO_ID=? AND CODIGO_PERSONAL=?";
		
		try{
		PreparedStatement psta = con.prepareStatement(sql);
		psta.setString(1, "001-"+sdfb.format(fecha));
		psta.setString(2, noempleado);
		
		PreparedStatement pstb = con.prepareStatement("SELECT CCO. ID_CCOSTO , CCO.NOMBRE " +
				"FROM MATEO.CONT_CCOSTO CCO WHERE CCO.ID_CCOSTO=? AND CCO.ID_EJERCICIO=?");
		
		ResultSet rsa = psta.executeQuery();
		
		
		while(rsa.next()){
			
			StringTokenizer stk = new StringTokenizer(rsa.getString("DEPARTAMENTOS"),"-");
			while (stk.hasMoreElements()) {
				String stkOut = stk.nextToken();
				pstb.setString(1, stkOut.trim());
				pstb.setString(2, "001-" + sdfb.format(fecha));
				System.out.println(stkOut);
				ResultSet rsb = pstb.executeQuery();
				if (rsb.next()) {
					String salida = rsb.getString(1) + "," + rsb.getString(2);
					vb.addElement(salida);
					hs.add(salida);
				}
				rsb.close();
			}
		}
		pstb.close();
		rsa.close();
		psta.close();
		
	}catch(SQLException sqle ){
		System.out.println("Error en cuentaPersonal " + sqle);
	}
		
	}
	
	public List<String> empleados(){
		List<String> salida = new ArrayList<String>();
		try{
			PreparedStatement pst = con.prepareStatement("SELECT * FROM ARON.EMPLEADO WHERE STATUS='A' ORDER BY APPATERNO,APMATERNO ");
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				salida.add(rs.getString("clave") +":" +rs.getString("appaterno") +" " +rs.getString("apmaterno") +" " +rs.getString("nombre") );
			}
			rs.close();
			pst.close();
			
		}catch(SQLException sqle ){
			
		}
		return salida;
	}
	
	
	/**
	 * @return Returns the hs.
	 */
	public HashSet getHs() {
		return hs;
	}
	/**
	 * @return Returns the vb.
	 */
	public Vector getVb() {
		return vb;
	}

}
