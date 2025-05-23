<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="Academico" scope="page" class="aca.admision.AdmAcademico" />
<jsp:useBean id="Solicitud" scope="page" class="aca.admision.AdmSolicitud" />
<jsp:useBean id="EstudioUtil" scope="page" class="aca.admision.AdmEstudioUtil" />
<jsp:useBean id="Recomienda" scope="page" class="aca.admision.AdmRecomienda" />
<jsp:useBean id="RecomiendaU" scope="page" class="aca.admision.AdmRecomiendaUtil" />
<jsp:useBean id="Salud" scope="page" class="aca.admision.AdmSalud" />
<jsp:useBean id="SaludU" scope="page" class="aca.admision.AdmSaludUtil" />
<jsp:useBean id="Padres" scope="page" class="aca.admision.AdmPadres" />
<jsp:useBean id="PadresU" scope="page" class="aca.admision.AdmPadresUtil" />
<jsp:useBean id="Tutor" scope="page" class="aca.admision.AdmTutor" />
<jsp:useBean id="TutorU" scope="page" class="aca.admision.AdmTutorUtil" />

<%@ page import= "aca.admision.AdmEstudio"%>

<head>
	<style>
		.tabla td{
			border:1px gray;
		}
	</style>
</head>
<%
	String folio 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
	String estado 		= request.getParameter("estado")==null ? "" : request.getParameter("estado");
	
	Academico.mapeaRegId(conEnoc, folio);
	Solicitud.mapeaRegId(conEnoc, folio);
	SaludU.mapeaRegId(conEnoc, folio);
	Padres = PadresU.mapeaRegId(conEnoc, folio);
	TutorU.mapeaRegId(conEnoc, folio);	
	
	String colorMal = "#FF8181";
	String color23 	= "#FAFF8E";
	String strColor = "";
%>
<body>
<div class="container-fluid">
<h2><spring:message code="aca.ProcesoDeSolicitud"/><small> ( <%=Solicitud.getNombre()+" "+Solicitud.getApellidoPaterno() %> )</small></h2>
<div class="alert alert-info">
	<a class="btn btn-primary" href="mostrarProceso?Folio=<%=folio %>"><spring:message code="aca.Regresar"/></a>
</div>
	<table style="width:100%">
		<tr>
			<td>
				<table class="tabla">
					<tr><td align="center" style='background-color:<%=color23 %>;'><b>&nbsp;Introduced valid characters&nbsp;</b></td></tr>
					<tr><td align="center" style='background-color:<%=colorMal %>;'><b>&nbsp;Did not introduce anything&nbsp;</b></td></tr>
				</table>
			</td>
		</tr>
	</table>
	<br>
	<table style=width:80% class="tabla" border="1">
		<tr>
			<th colspan="3">
				Personal Data
			</th>
		</tr>
		<tr class="tr2">
			<td><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Name:&nbsp;&nbsp;&nbsp;</b> <%=Solicitud.getNombre()+" "+Solicitud.getApellidoPaterno()+" "+(Solicitud.getApellidoMaterno()==null ? "" : Solicitud.getApellidoMaterno()) %></td>
		</tr>
	<%  String ciudad   	 = aca.catalogo.CiudadUtil.getNombreCiudad(conEnoc,Solicitud.getPaisId(),Solicitud.getEstadoId(),Solicitud.getCiudadId());
		String estadoId		 = aca.catalogo.EstadoUtil.getNombreEstado(conEnoc, Solicitud.getPaisId(),Solicitud.getEstadoId());
		String paisId		 = aca.catalogo.PaisUtil.getNombrePais(conEnoc,Solicitud.getPaisId());
		String nacionalidad  = aca.catalogo.PaisUtil.getNacionalidad(conEnoc, Solicitud.getPaisId()==null ? "" : Solicitud.getPaisId());
		String edad		 	 = Solicitud.getEdad(conEnoc);
		String religion		 = aca.catalogo.CatReligionDao.getNombreReligion(conEnoc, Solicitud.getReligionId());

		if((ciudad==null||ciudad.equals("vac�o")) || (estadoId==null||estadoId.equals("vac�o")) || (paisId==null||paisId.equals("vac�o"))){
			strColor = "style='background-color:"+colorMal+";'";
		}
	%>
		<tr class="tr2" <%=strColor %>>
			<td><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Place of birth:&nbsp;&nbsp;&nbsp;</b> 
				<%=(Solicitud.getPaisId()!=null&&!Solicitud.getPaisId().equals("153")) || ciudad==null ? "" : ciudad+"," %>
				<%=(Solicitud.getPaisId()!=null&&!Solicitud.getPaisId().equals("153")) || estadoId==null ? "" : estadoId+","%>
				<%=paisId==null ? "" : paisId%>
			</td>
		</tr>
	<%
		strColor = "";
		if(nacionalidad==null || nacionalidad.equals("Not found")){
			strColor = "style='background-color:"+colorMal+";'";
		}
	%>
		<tr class="tr2" <%=strColor %>>
			<td><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Nationality:&nbsp;&nbsp;&nbsp;</b> <%= nacionalidad==null?"":nacionalidad %></td>
		</tr>
	<%
		strColor = "";
		if(Solicitud.getFechaNac()==null){
			strColor = "style='background-color:"+colorMal+";'";
		}
	%>
		<tr class="tr2" <%=strColor %>>
			<td><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Date of birth:&nbsp;&nbsp;&nbsp;</b> <%=Solicitud.getFechaNac()==null?"":Solicitud.getFechaNac()%></td>
		</tr>
		<tr class="tr2">
			<td><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Age:&nbsp;&nbsp;&nbsp;</b> <%= edad==null?"":edad%></td>
		</tr>
	<%	String estadoCivil = "-";
		if(Solicitud.getEstadoCivil()!= null){
			if(Solicitud.getEstadoCivil().equals("C")){
				if(Solicitud.getGenero().equals("M"))estadoCivil="Married";
				else estadoCivil="Married";
			}
			else if(Solicitud.getEstadoCivil().equals("S")){
				if(Solicitud.getGenero().equals("M"))estadoCivil="Single";
				else estadoCivil="Single";
			}
			else if(Solicitud.getEstadoCivil().equals("V")){
				if(Solicitud.getGenero().equals("M"))estadoCivil="Widowed";
				else estadoCivil="Widowed";
			}
			else if(Solicitud.getEstadoCivil().equals("D")){
				if(Solicitud.getGenero().equals("M"))estadoCivil="Divorced";
				else estadoCivil="Divorced";
			}
		}
		strColor = "";
		if(estadoCivil==null || estadoCivil.trim().equals("")){
			strColor = "style='background-color:"+colorMal+";'";
		}
	%>
		<tr class="tr2" <%=strColor %>>
			<td><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Civil status:&nbsp;&nbsp;&nbsp;</b> <%=estadoCivil%></td>
		</tr>
		<tr class="tr2">
			<td><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Gender:&nbsp;&nbsp;&nbsp;</b> <%=Solicitud.getGenero()%></td>
		</tr>
	<%
		strColor = "";
		if(religion==null || religion.equals("empty")){
			strColor = "style='background-color:"+colorMal+";'";
		}
	%>
		<tr class="tr2" <%=strColor %>>
			<td><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Denomination:&nbsp;&nbsp;&nbsp;</b> <%= religion==null?"":religion%></td>
		</tr>
	<%	String bautizado = "-";
		if(Solicitud.getBautizado()!=null){
			if(Solicitud.getBautizado().equals("S")) bautizado = "Yes";
			else if(Solicitud.getBautizado().equals("N")) bautizado = "No";
		}
		if(!bautizado.equals("-")){%>
			<tr class="tr2">
				<td><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Baptized:&nbsp;&nbsp;&nbsp;</b> <%=bautizado%></td>
			</tr>
	<%	}
		strColor = "";
		if(Tutor.getCalle()==null || Tutor.getCalle().trim().equals("")){
			strColor = "style='background-color:"+colorMal+";'";
		}
		else if(!Character.isLetter(Tutor.getCalle().trim().charAt(0))){
			strColor = "style='background-color:"+color23+";'";
		}
	%>
		<tr class="tr2" <%=strColor %>>
			<td><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Address:&nbsp;&nbsp;&nbsp;</b> <%=Tutor.getCalle()==null?"":Tutor.getCalle()%> #<%=Tutor.getNumero()==null?"":Tutor.getNumero()%> <%=Tutor.getColonia()==null?"":Tutor.getColonia()%></td>
		</tr>
		<% 	
		String cdUb = Tutor.getPaisId().equals("153")?aca.catalogo.CiudadUtil.getNombreCiudad(conEnoc, Tutor.getPaisId(), Tutor.getEstadoId(),Tutor.getCiudadId()):Tutor.getCiudad();
		String edoUb = Tutor.getPaisId().equals("153")?aca.catalogo.EstadoUtil.getNombreEstado(conEnoc, Tutor.getPaisId(),Tutor.getEstadoId()):Tutor.getEstado();
		String paisUb = aca.catalogo.PaisUtil.getNombrePais(conEnoc, Tutor.getPaisId());
		
		strColor = "";
		if((cdUb==null||cdUb.trim().equals("")||cdUb.equals("vac�o")) || (edoUb==null||edoUb.trim().equals("")||edoUb.equals("vac�o")) || (paisUb==null||paisUb.equals("vac�o"))){
			strColor = "style='background-color:"+colorMal+";'";
		}
	%>
		<tr class="tr2" <%=strColor %>>
			<td><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Location:&nbsp;&nbsp;&nbsp;</b> <%=cdUb == null?"":cdUb+""%>
											<%=edoUb==null?"":edoUb+""%>
											<%=paisUb==null?"":paisUb%>
			</td>
		</tr>
	<% 	strColor = "";
		if(Tutor.getCodigoPostal()==null || Tutor.getCodigoPostal().trim().equals("")){
			strColor = "style='background-color:"+colorMal+";'";
		}
		else if(!Character.isDigit(Tutor.getCodigoPostal().trim().charAt(0))){
			strColor = "style='background-color:"+color23+";'";
		}
	%>
		<tr class="tr2" <%=strColor %>>
			<td><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ZIP Code:&nbsp;&nbsp;&nbsp;</b> <%=Tutor.getCodigoPostal()==null?"":Tutor.getCodigoPostal()%></td>
		</tr>
	<% 	strColor = "";
		if(Tutor.getTelefono()==null || Tutor.getTelefono().trim().equals("")){
			strColor = "style='background-color:"+colorMal+";'";
		}
		else{
			String tel = Tutor.getTelefono().trim();
			if(tel.length()>=10){
				for(int i=0; i<tel.length(); i++){
					if(!Character.isDigit(tel.charAt(i)) || tel.charAt(i)=='-' || tel.charAt(i)==' '){
						strColor = "style='background-color:"+color23+";'";
					}
				}
			}
			else strColor = "style='background-color:"+color23+";'";
		}
	%>
		<tr class="tr2" <%=strColor %>>
			<td><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Telephone:&nbsp;&nbsp;&nbsp;</b> <%=Tutor.getTelefono()==null?"":Tutor.getTelefono()%></td>
		</tr>
	<% 	strColor = "";
		if(Solicitud.getEmail()==null || Solicitud.getEmail().trim().equals("")){
			strColor = "style='background-color:"+colorMal+";'";
		}
		else{
			String correo = Solicitud.getEmail().trim();
			for(int i=0; i<correo.length(); i++){
				if(!correo.contains("@") || !correo.contains(".")){
					strColor = "style='background-color:"+color23+";'";
				}
			}
		}
	%>
		<tr class="tr2" <%=strColor %>>
			<td><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Email:&nbsp;&nbsp;&nbsp;</b> <%=Solicitud.getEmail()==null?"":Solicitud.getEmail()%></td>
		</tr>
	</table>
	<br>
	<table style=width:80% class="tabla" border="1">
		<tr>
			<th colspan="3">
				Academic Data
			</th>
		</tr>
	<% 	String nombreCarrera = aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc, Academico.getCarreraId());
	 	strColor = "";
		if(nombreCarrera==null || nombreCarrera.trim().equals("") || nombreCarrera.equals("null")){
			strColor = "style='background-color:"+colorMal+";'";
		}
	%>
		<tr class="tr2" <%=strColor %>>
			<td><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Selected Course:&nbsp;&nbsp;&nbsp;</b> <%= nombreCarrera==null||nombreCarrera.equals("null")?"":nombreCarrera%></td>
		</tr>
	<% 	strColor = "";
		if(Academico.getFecha()==null || Academico.getFecha().trim().equals("")){
			strColor = "style='background-color:"+colorMal+";'";
		}
	%>
		<tr class="tr2" <%=strColor %>>
			<td><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Year and Cycle:&nbsp;&nbsp;&nbsp;</b> <%=Academico.getFecha()==null?"":Academico.getFecha()%></td>
		</tr>
	</table>
	<br>
	<table  style=width:80% class="tabla" border="1">
		<tr>
			<th colspan="5">
				Academic background
			</th>
		</tr>
		<tr align="center">
		    <td width="2%"><font color="blue"><b>#</b></font></td>
		    <td width="8%"><font color="blue"><b>Award</b></font></td>
		    <td width="9%"><font color="blue"><b>Institution</b></font></td>
		    <td width="8%"><font color="blue"><b>Country</b></font></td>
		    <td width="7%"><font color="blue"><b>Complete</b></font></td>
		</tr>
	<% 	ArrayList<aca.admision.AdmEstudio> lista = EstudioUtil.getListAFolio(conEnoc, folio, "ORDER BY FOLIO, ID");
		for(int i=0; i<lista.size(); i++){
			AdmEstudio tmp = (AdmEstudio)lista.get(i);
			strColor = "";
			if(tmp.getTitulo()==null||tmp.getTitulo().trim().equals("") || tmp.getInstitucion()==null||tmp.getInstitucion().trim().equals("")){
				strColor = "style='background-color:"+colorMal+";'";
			}
			else if(!Character.isLetter(tmp.getTitulo().trim().charAt(0)) || !Character.isLetter(tmp.getInstitucion().trim().charAt(0))){
				strColor = "style='background-color:"+color23+";'";
			}%>
			<tr class="tr2" align="center" <%=strColor %>>
			    <td width="2%"><%=tmp.getId()==null?"":tmp.getId()%></td>
			    <td width="8%"><%=tmp.getTitulo()==null?"":tmp.getTitulo()%></td>
			    <td width="9%"><%=tmp.getInstitucion()==null?"":tmp.getInstitucion()%></td>
			    <td width="7%"><%=aca.catalogo.PaisUtil.getNombrePais(conEnoc, tmp.getPaisId())%></td>
			    <td width="7%"><%=tmp.getCompleto()==null?"":tmp.getCompleto().equals("S")?"Yes":"No" %></td>
			</tr>
	<%	}%>
	</table>
	<br>
	<table style=width:80% class="tabla" border="1">
		<tr class="tr2">
			<th colspan="3">
				Health Data
			</th>
		</tr>
		<tr class="tr2">
			<td width="50%"><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Chronic Ailment:&nbsp;&nbsp;&nbsp;</b><%= Salud.getEnfermedad()==null?"":Salud.getEnfermedad().equals("S")?"Yes":"No" %></td>
		<%	strColor = "";
			if(Salud.getEnfermedad().equals("S") && !Character.isLetter(Salud.getEnfermedadDato().trim().charAt(0))){
				strColor = "style='background-color:"+color23+";'";
			}
		%>
			<td width="50%" <%=strColor %>><%=Salud.getEnfermedadDato() %></td>
		</tr>
		<tr class="tr2">
			<td width="50%"><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Physical Impediment:&nbsp;&nbsp;&nbsp;</b><%= Salud.getImpedimento()==null?"":Salud.getImpedimento().equals("S")?"Yes":"No" %></td>
			<td width="50%"><%= Salud.getImpedimentoDato() %></td>
		</tr>
	</table>
	<br>
	<table style=width:80% class="tabla" border="1">
		<tr class="tr2">
			<th colspan="4">
				Parental Data
			</th>
		</tr>
		<tr align="center">
			<td><font color="blue"><b><spring:message code="aca.Nombre"/></b></font></td>
			<td><font color="blue"><b><spring:message code="aca.Religion"/></b></font></td>
			<td><font color="blue"><b>Nationality</b></font></td>
			<td><font color="blue"><b>Occupation</b></font></td>
		</tr>
	<%  String padreReligion = aca.catalogo.CatReligionDao.getNombreReligion(conEnoc,Padres.getPadreReligion());
		String padreNac		 = aca.catalogo.PaisUtil.getNacionalidad(conEnoc,Padres.getPadreNacionalidad());
		String madreReligion = aca.catalogo.CatReligionDao.getNombreReligion(conEnoc,Padres.getMadreReligion());
		String madreNac		 = aca.catalogo.PaisUtil.getNacionalidad(conEnoc,Padres.getMadreNacionalidad());
		
		strColor = "";
		if(Padres.getPadreNombre()==null||Padres.getPadreNombre().trim().equals("") || Padres.getPadreOcupacion()==null||Padres.getPadreOcupacion().trim().equals("")){
			strColor = "style='background-color:"+colorMal+";'";
		}
		else if(!Character.isLetter(Padres.getPadreNombre().trim().charAt(0)) || !Character.isLetter(Padres.getPadreOcupacion().trim().charAt(0))){
			strColor = "style='background-color:"+color23+";'";
		}
	%>
		<tr class="tr2" <%=strColor %>>
			<td><%= (Padres.getPadreNombre()==null?"":Padres.getPadreNombre())+" "+(Padres.getPadreApellido()==null?"":Padres.getPadreApellido())%></td>
			<td><%= padreReligion==null?"":padreReligion %></td>
			<td><%= padreNac ==null?"":padreNac%></td>
			<td><%= Padres.getPadreOcupacion()==null?"":Padres.getPadreOcupacion()%></td>
		</tr>
	<%	strColor = "";
		if(Padres.getMadreNombre()==null||Padres.getMadreNombre().trim().equals("") || Padres.getMadreOcupacion()==null||Padres.getMadreOcupacion().trim().equals("")){
			strColor = "style='background-color:"+colorMal+";'";
		}
		else if(!Character.isLetter(Padres.getMadreNombre().trim().charAt(0)) || !Character.isLetter(Padres.getMadreOcupacion().trim().charAt(0))){
			strColor = "style='background-color:"+color23+";'";
		}
	%>
		<tr class="tr2" <%=strColor %>>
			<td><%= Padres.getMadreNombre()+" "+Padres.getMadreApellido()%></td>
			<td><%= madreReligion==null?"":madreReligion %></td>
			<td><%= madreNac==null?"":madreNac%></td>
			<td><%= Padres.getMadreOcupacion()==null?"":Padres.getMadreOcupacion()%></td>
		</tr>
	</table>
	<br>
		<table style=width:80% class="tabla" border="1">
		<tr class="tr2">
			<th colspan="4">
				Sponsorship
			</th>
		</tr>
		<%  String tutor = "";
		    
		 	if(Tutor.getTutor()!=null){
				if(Tutor.getTutor().equals("0")){
					tutor = "Father";
				}else if(Tutor.getTutor().equals("1")){
					tutor = "Mother";
				}else if(Tutor.getTutor().equals("2")){
					tutor = "Personal Funds";
				}else if(Tutor.getTutor().equals("3")){
					tutor = "Mentor";
				}
		 	}
		%>
		<tr class="tr2">
			<td><%= tutor %></td>
		<% 	if(tutor.equals("Tutor")){
				strColor = "";
				if(Tutor.getNombre()==null || Tutor.getNombre().trim().equals("")){
					strColor = "style='background-color:"+colorMal+";'";
				}
				else if(!Character.isLetter(Tutor.getNombre().trim().charAt(0))){
					strColor = "style='background-color:"+color23+";'";
				} %>
				<td <%=strColor %>><%=Tutor.getNombre()==null?"":Tutor.getNombre()%></td>
		<% 	} %>
		</tr>
	</table>
<%	if(!Academico.getCarreraId().equals("10501") && Solicitud.getPaisId()!=null && !Solicitud.getPaisId().equals("108")){ %>
		<br>
		<table style=width:80% class="tabla" border="1">
			<tr>
				<th colspan="5">
					Referee
				</th>
			</tr>
			<tr align="center">
			    <td width="2%"><b><spring:message code="aca.Numero"/></b></td>
			    <td width="8%"><b><spring:message code="aca.Nombre"/></b></td>
			    <td width="9%"><b>Email</b></td>
			    <td width="8%"><b>Phone number</b></td>
			</tr>
		<% 	ArrayList<aca.admision.AdmRecomienda> listaR = RecomiendaU.getListFolio(conEnoc, folio, "ORDER BY RECOMENDACION_ID");	
			for(int i=0; i<listaR.size(); i++){
				aca.admision.AdmRecomienda tmp = listaR.get(i);
				
				strColor = "";
				if(tmp.getNombre()==null || tmp.getNombre().trim().equals("")){
					strColor = "style='background-color:"+colorMal+";'";
				}
				else if(!Character.isLetter(tmp.getNombre().trim().charAt(0))){
					strColor = "style='background-color:"+color23+";'";
				}
				
				if(tmp.getEmail()!=null || !tmp.getEmail().trim().equals("")){
					String [] arr = tmp.getEmail().split("@");
					if(arr.length>1){
						if(!arr[1].contains(".")){
							strColor = "style='background-color:"+color23+";'";
						}
					}
					else{
						strColor = "style='background-color:"+color23+";'";
					}
				}
				else{
					strColor = "style='background-color:"+colorMal+";'";
				}
		%>
				<tr class="tr2" align="center" <%=strColor %>>
				    <td width="2%"><%=tmp.getRecomendacionId()==null?"":tmp.getRecomendacionId() %></td>
				    <td width="10%" align="left"><%=tmp.getNombre()==null?"":tmp.getNombre() %></td>
				    <td width="7%" align="left"><%=tmp.getEmail()==null?"":tmp.getEmail() %></td>
				    <td width="7%" align="left"><%=tmp.getTelefono()==null?"":tmp.getTelefono() %></td>
				</tr>
		<%	}
	} %>
	</table>
	</div>
</body>
<%@ include file= "../../cierra_enoc.jsp"%>