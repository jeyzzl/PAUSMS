<%@ include file= "../../con_enoc.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import="java.util.*" %>
<%! 
	private String[] separarApellidos(String apellido){
		StringTokenizer tk = new StringTokenizer(apellido," ");
		String aNombre[] = new String[tk.countTokens()];	
		String apellidoP="",apellidoM="";
		String dev[] = new String[2];
		dev[0]="";dev[1]="";
		int i=0;
		boolean salir = false;		
		while (tk.hasMoreTokens()) aNombre[i++] = tk.nextToken().toUpperCase(); //Llenamos el array de nombres y apellidos
		if (i>1){
			//Buscamos apellido materno
			i-=2;		
			if ((aNombre[i].equals("LA")||aNombre[i].equals("LAS")||aNombre[i].equals("LOS")) && aNombre[i-1].equals("DE")){
				apellidoM = aNombre[i-1]+" "+aNombre[i]+" "+aNombre[i+1];
				i-=2; //Posicionamos i para buscar apellido paterno
			}else if (aNombre[i].equals("DEL")){
				apellidoM = aNombre[i]+" "+aNombre[i+1];
				i--; //Posicionamos i para buscar apellido paterno
			}else{
				apellidoM = aNombre[i+1];
			}
			//Creamos el array a devolver	
			int j;
			for (j=0;j<=i;j++) dev[0]+=aNombre[j]+" ";
			dev[1] = apellidoM;		
		}else{
			dev[0] = aNombre[0];
			dev[1] = " ";
		}
		return dev;
	}
%>
<!-- inicio de estructura -->
<%	
	Statement stmt 	= conEnoc.createStatement();
	ResultSet rs 	= null;
	String comando 	= "";
	Statement stmt2	= conEnoc.createStatement();
	
	comando	=	"select * from ENOC.emp_datos where cotejado IN ('S','N')"; 
	rs = stmt.executeQuery( comando );	
	while(rs.next() ){ 
		String nomina = rs.getString("id_empleado");
		String apellidos =  rs.getString("apellidos");
		String datoApellido[] = separarApellidos(apellidos);
		System.out.print("Nómina: "+nomina);
		stmt2.execute("UPDATE ENOC.EMP_DATOS SET appaterno='"+datoApellido[0]+"', apmaterno='"+datoApellido[1]+"' where ID_EMPLEADO='"+nomina+"'"); 
		System.out.println(" - OK!");
	}
%>
ok.. terminado.
<!-- fin de estructura -->
<%
	if (rs!=null){ rs.close();}
	if (stmt!=null){ stmt.close();}
%>
<%@ include file= "../../cierra_enoc.jsp" %>