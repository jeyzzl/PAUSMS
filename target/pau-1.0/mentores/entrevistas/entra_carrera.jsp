<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="CarreraU" scope="page" class="aca.catalogo.CarreraUtil"/>

<%	
	String  sFacultad		= request.getParameter("facultad_id");
    String  sModulo		    = request.getParameter("moduloId"); 
	String 	sNombreFac		= request.getParameter("nombre_facultad");
    String  sCarpeta    	= request.getParameter("carpeta");

	ArrayList	lisCarrera		= new ArrayList();
	lisCarrera		= CarreraU.getLista(conEnoc, sFacultad, "Order by 2, 5");
%>

<table style="width:62%" align="center" id="noayuda">
  <tr>     <td>&nbsp;</td>  </tr>
  <tr>     <td>&nbsp;</td>  </tr>
  <tr> 
    <td><div align="center"><font size="4"><strong><%=sNombreFac%> School</strong></font></div></td>
  </tr>  
  <tr>     <td>&nbsp;</td>  </tr>
  <tr> 
    <th><div align="center"><font size="2"><strong>Select a degree</strong></font></div></th>
  </tr>
</table>
<table style="width:508" height="24"  align="center" class="tabla">
<%  for(int i=0; i<lisCarrera.size(); i++){
		aca.catalogo.CatCarrera  Carrera = (aca.catalogo.CatCarrera) lisCarrera.get(i);
%>
  <tr> 
    <td width="458"><li> <a href="../entrevistas/entra_mentor.jsp?carrera_id=<%=Carrera.getCarreraId()%>&nombre_carrera=<%=Carrera.getNombreCarrera()%>&moduloId=<%=sModulo%>&carpeta=<%=sCarpeta%>&facultad_id=<%=sFacultad%>&nombre_facultad=<%=sNombreFac%>"><%=Carrera.getNombreCarrera()%></a></td>
  </tr>
  <%  }%>
</table>
<table style="width:62%" align="center">
	<tr> <td width="603"><div align="right"><a href="../../menu_opcion.jsp?moduloId=<%=sModulo%>&carpeta=<%=sCarpeta%>"><strong><em>Start</em></strong></a></div></td></tr>
</table>
<%	lisCarrera= null;	%>
<%@ include file= "../../cierra_enoc.jsp" %> 