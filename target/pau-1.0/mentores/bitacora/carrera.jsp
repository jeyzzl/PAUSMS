<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="CarreraU" scope="page" class="aca.catalogo.CarreraUtil"/>
<%
   	String sFacultad	= "X";
   	String codigoPersonal	= (String) session.getAttribute("codigoPersonal");
   	ArrayList lisCarrera	= new ArrayList();
   	
  	
 	lisCarrera			= CarreraU.getListAll(conEnoc, "ORDER BY ENOC.FACULTAD_NOMBRE(FACULTAD_ID),4");
%>
<body>
<form id="forma" name="forma" action="carrera" method="post" id="noayuda">
<div class="container-fluid">
	<h1>School and Degree</h1>
	<div class="alert alert-info"></div>
<table style="width:60%"></table>
<%	for (int i= 0; i<lisCarrera.size(); i++){
		aca.catalogo.CatCarrera Carrera	= (aca.catalogo.CatCarrera) lisCarrera.get(i);
		if(!sFacultad.equals(Carrera.getFacultadId())){
			sFacultad = Carrera.getFacultadId();
%>
<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
	  <tr> 
	    <td class="titulo"><h3><%=aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc, sFacultad)%></h3></th>
	  </tr>
  </thead>
  <tr>
  	<th>Degree Name</th>
  </tr>
<%
		} //fin del if facultad
%>
  <tr class="button" onclick="location.href='mentores?carreraId=<%=Carrera.getCarreraId()%>'">
    <td width="400">
    	<%=Carrera.getNombreCarrera()%>
    </td>
  </tr>
<%	
	} //fin del for facultad
	lisCarrera	= null;
%>
</table>
</div>
</form>
</body>
<%@ include file= "../../cierra_enoc.jsp" %> 