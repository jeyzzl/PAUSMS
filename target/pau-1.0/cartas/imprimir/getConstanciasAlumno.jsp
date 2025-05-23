<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@ include file= "../../con_enoc.jsp"%>
<jsp:useBean id="AlumConstanciaL" scope="page" class="aca.alumno.AlumConstanciaUtil"/>
<jsp:useBean id="AlumConstancia" scope="page" class="aca.alumno.AlumConstancia"/>
<jsp:useBean id="extranjero" scope="page" class="aca.leg.LegExtranjero"/>
<jsp:useBean id="extranjeroU" scope="page" class="aca.leg.LegExtranjeroUtil"/>
<jsp:useBean id="alumU" scope="page" class="aca.alumno.AlumUtil"/>

<%
	String codigoPersonal 	= request.getParameter("codigoPersonal");
	String usuario 			= (String) session.getAttribute("codigoPersonal");
	
	/* VARIABLES EXTRAS */
	int nacionalidad		= (Integer.parseInt(aca.alumno.AlumUtil.getNacionalidad(conEnoc, codigoPersonal)));
	boolean esNuevo			= (boolean) request.getAttribute(""); aca.alumno.AlumUtil.esNuevoIngreso(conEnoc, codigoPersonal, aca.alumno.PlanUtil.getPlanActual(conEnoc,codigoPersonal));
	boolean	tieneFM3 		= false;
	
	java.text.SimpleDateFormat sdf 	= new java.text.SimpleDateFormat("dd/MM/yyyy");	
	java.util.Date fechaHoy 		= sdf.parse(aca.util.Fecha.getHoy());
	java.util.Date fechaFM3 		= sdf.parse(aca.leg.LegExtdocUtil.getFechaVenceFM3(conEnoc, codigoPersonal));
    if (fechaHoy.before(fechaFM3)) { tieneFM3 = true; }
    
    boolean cambioCarrera	= false;
    String carreraFM3		= "";
    String carreraAlum 		= alumU.getCarreraIdCodigo(conEnoc,codigoPersonal);
    extranjero.setCodigo(codigoPersonal);
    if(extranjeroU.existeReg(conEnoc, codigoPersonal)){
    	extranjeroU.mapeaRegId(conEnoc, codigoPersonal);
    	carreraFM3 = extranjero.getCarrera();
    	if(!carreraAlum.equals(carreraFM3)){
        	cambioCarrera=true;
        }   	
    }   
	
	ArrayList<aca.alumno.AlumConstancia> constancias = AlumConstanciaL.getListUsuario(conEnoc, usuario, " ORDER BY CONSTANCIA_ID ");


	for(aca.alumno.AlumConstancia constancia: constancias){
		
		/* CONDICIONADA */
		if(constancia.getTipo().equals("CONDICIONADA")){
			if(nacionalidad != 91){ 
				 if(esNuevo || tieneFM3 == false || cambioCarrera){
					 %><option value="<%=constancia.getConstanciaId() %>"><%=constancia.getConstanciaNombre() %></option><%
				 }
			}
			
			
		/* ESTUDIOS */
		}else if(constancia.getTipo().equals("ESTUDIOS")){
			if (nacionalidad == 91 || tieneFM3 && cambioCarrera==false){
				%><option value="<%=constancia.getConstanciaId() %>"><%=constancia.getConstanciaNombre() %></option><%				
			}
			
			
		/* NINGUNO */
		}else if(constancia.getTipo().equals("NINGUNO")){
			%><option value="<%=constancia.getConstanciaId() %>"><%=constancia.getConstanciaNombre() %></option><%
		}
		
		
	}
%>

<%@ include file= "../../cierra_enoc.jsp" %>