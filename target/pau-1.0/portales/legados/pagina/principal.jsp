<%@page import="java.util.Date"%>
<%@page import="java.util.HashMap"%>
<%@ include file= "../../../con_enoc.jsp" %>
<%@ include file= "../id.jsp" %>
<%@ include file= "../../../seguro.jsp" %>
<%@ include file="../../../body.jsp"%>
<%@ include file= "../../../idioma.jsp" %>
<jsp:useBean id="alumno" scope="page" class="aca.alumno.AlumPersonal"/>
<jsp:useBean id="academico" scope="page" class="aca.alumno.AlumAcademico"/>
<jsp:useBean id="AcademicoU" scope="page" class="aca.alumno.AcademicoUtil"/>
<jsp:useBean id="AlumUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="plan" scope="page" class="aca.alumno.AlumPlan"/>
<jsp:useBean id="CompEventoU" scope="page" class="aca.cultural.CompEventoUtil"/>
<jsp:useBean  id="CompImage" class="aca.cultural.CompEventoImagenUtil" scope="page" />
<jsp:useBean  id="CompRegistroU" class="aca.cultural.CompRegistroUtil" scope="page" />
<jsp:useBean  id="CompAsaRegU" class="aca.cultural.CompAsambleaRegistroUtil" scope="page" />
<jsp:useBean  id="CompAsambleaU" class="aca.cultural.CompAsambleaUtil" scope="page" />
<jsp:useBean  id="CompAsamblea" class="aca.cultural.CompAsamblea" scope="page" />

<link rel="stylesheet" href="../../../bootstrap/css/bootstrap.min.css" type="text/css" media="screen" />
<link rel="stylesheet" href="../bootstrap new/css/bootstrap.css" />
<link rel="stylesheet" href="../bootstrap-fileupload.min.css" />

<%
		String direccion 		= request.getRequestURL().toString();
		boolean virtual 		= false;
		if(direccion.contains("academico")){
			virtual = true;
		}
		
		if(!session.getAttribute("codigoAlumno").equals(session.getAttribute("codigoPersonal")) && !session.getAttribute("codigoPersonal").equals("9800308")){
			virtual=false;
		}
		// CONSULTA INFORMACION DE INGRESO Y FINANCIERA PARA SUBIR A SESIÓN DATOS QUE SE USARAN EN LA PAGINA DE menu.jsp
		
		//Codigo del usuario que se firmó
		String codigoPersonal 	= (String) session.getAttribute("codigoPersonal");
		// Codigo del alumno
		String matricula 	= (String) session.getAttribute("codigoAlumno");
		String colorPortal 	= (String) session.getAttribute("colorPortal")==null?"":(String)session.getAttribute("colorPortal");
		String cargaId 		= aca.carga.CargaUtil.getMejorCarga(conEnoc,matricula);
		
		alumno = AlumUtil.mapeaRegId(conEnoc,matricula);	
		academico = AcademicoU.mapeaRegId(conEnoc,matricula);
		plan.mapeaRegId(conEnoc,matricula);
		String fechaHoy 	= aca.util.Fecha.getHoy();
		String escala 		= plan.getEscala();
		String modalidad	= aca.alumno.AcademicoUtil.getModalidadId(conEnoc, matricula);
		String facultadAlumno								 = CompRegistroU.facultadAlumno(conEnoc, matricula);
		String carreraAlumno								 = CompRegistroU.carreraAlumno(conEnoc, matricula);
		boolean facsa 										 = false;
		int cantidadA;
		ArrayList<aca.cultural.CompAsamblea> asambleas;
		HashMap<String, aca.cultural.CompAsambleaRegistro> mapAsambleaId;
		if(facultadAlumno.equals("103")){
			facsa 		 	= true;
			cantidadA 		= Integer.parseInt(aca.cultural.CompAsambleaUtil.getCantidadAsambleasFacsa(conEnoc, matricula));
			mapAsambleaId 	= CompAsaRegU.getMatAsambleaFacsa(conEnoc);
			asambleas  		= CompAsambleaU.getListAsambleaPorFacultadFacsa(conEnoc, matricula, "ORDER BY ASAMBLEA_ID");
		}else{
			cantidadA 		= Integer.parseInt(aca.cultural.CompAsambleaUtil.getCantidadAsambleas(conEnoc, matricula));
			mapAsambleaId 	= CompAsaRegU.getMatAsamblea(conEnoc);
			asambleas  		= CompAsambleaU.getListAsambleaPorFacultad(conEnoc, matricula, "ORDER BY ASAMBLEA_ID");
		}
		
		ArrayList<aca.cultural.CompEventoImagen> imagenes 				= CompImage.imagenesParaManana(conEnoc, "ORDER BY EVENTO_ID");
		ArrayList<aca.cultural.CompRegistro> acreditados  				= CompRegistroU.acreditados(conEnoc, matricula, "ORDER BY EVENTO_ID");
		ArrayList<aca.cultural.CompRegistro> reservados 				= CompRegistroU.reservados(conEnoc, matricula, "ORDER BY FECHA");
		
		java.util.HashMap<String,aca.cultural.CompEvento> mapEvento		= aca.cultural.CompEventoUtil.getMapEvento(conEnoc, "ORDER BY FECHA");
		
		double cantidad = 0;
		if(cantidadA > 0){
			cantidad = 100/cantidadA;
		}	
%>
<style>
	body{
		background:white;
	}
	.puestosAlum td, .puestosAlum th{
		background: white !important;
	}
	
	.puestosAlum th{
		color: black !important;
		border: 1px solid #DCDEDE !important;
	}
	
</style>
<div class="alert alert-info">
<h2 align = "center">
	¡<%	if(alumno.getSexo().equals("M")) out.print("Bienvenido");else out.print("Bienvenida");%> <%=alumno.getNombre()%> a la reservación de LEGADOS!
</h2>
	<ul>
	<%if(!reservados.isEmpty()){ %>
	<li class="toclevel-1 tocsection-1"><a href="#Reservados"> <span class="toctext">¿Qué eventos tengo reservados?</span></a></li>
	<%}else{} %>
	<%if(!acreditados.isEmpty()){ %>
	<li class="toclevel-1 tocsection-2"><a href="#Acreditados"> <span class="toctext">¿Y mis legados que tengo acreditados?</span></a>
	<%}else{} %>
	</ul>
	<font size="3px">ASAMBLEA:</font>
		&nbsp;&nbsp;&nbsp;<div style='width:10px;height:10px;background:#3A9727;display:inline-block;'></div> <font size="3px">General</font>
		&nbsp;&nbsp;&nbsp;<div style='width:10px;height:10px;background:#0055cc;display:inline-block;'></div> <font size="3px"><spring:message code="aca.Facultad"/></font>
		&nbsp;&nbsp;&nbsp;<div style='width:10px;height:10px;background:#99CCFF;display:inline-block;'></div> <font size="3px"><spring:message code="aca.Carrera"/></font>
		&nbsp;&nbsp;&nbsp;<div style='width:10px;height:10px;background:#FF9933;display:inline-block;'></div> <font size="3px"><spring:message code="aca.Tutor"/>es</font>
		&nbsp;&nbsp;&nbsp;<div style='width:10px;height:10px;background:red;display:inline-block;'></div> <font size="3px">NO FUISTE</font>
</div>
<%
	if(cantidad>0){
%>
<div class="progress progress-striped active">
<%	
	String asambleaId 	= "";
	String tipo 		= "";
	String color 		= "";
	for(aca.cultural.CompAsamblea asambleaA : asambleas ){
		if(facsa==false){
			if(mapAsambleaId.containsKey(asambleaA.getAsambleaId()+","+asambleaA.getFacultadId()+","+matricula)){
				asambleaId = mapAsambleaId.get(asambleaA.getAsambleaId()+","+asambleaA.getFacultadId()+","+matricula).getAsambleaId();
				tipo = aca.cultural.CompAsambleaUtil.getTipoAsamblea(conEnoc, asambleaId, "104");
				if(tipo.equals("1")){
					color = "progress-bar-success";
				}else if(tipo.equals("2")){
					color = "";
				}else if(tipo.equals("3")){
					color = "progress-bar-info";
				}else {
					color = "progress-bar-warning";
				}
				%>
				<div class="progress-bar <%=color %>" style="width: <%=cantidad%>%" title="<%=asambleaA.getFecha() %>"></div>
				<%		
			}else{
				%>
				<div class="progress-bar progress-bar-danger" style="width: <%=cantidad%>%" title="<%=asambleaA.getFecha() %>"></div>
			<%}
		}else{
			if(mapAsambleaId.containsKey(asambleaA.getAsambleaId()+","+asambleaA.getFacultadId()+","+matricula+","+asambleaA.getCarreraId())){
				asambleaId = mapAsambleaId.get(asambleaA.getAsambleaId()+","+asambleaA.getFacultadId()+","+matricula+","+asambleaA.getCarreraId()).getAsambleaId();
				tipo = aca.cultural.CompAsambleaUtil.getTipoAsambleaFacsa(conEnoc, asambleaId, facultadAlumno, carreraAlumno );
				if(tipo.equals("1")){
					color = "progress-bar-success";
				}else if(tipo.equals("2")){
					color = "";
				}else if(tipo.equals("3")){
					color = "progress-bar-info";
				}else {
					color = "progress-bar-warning";
				}
				%>
				<div class="progress-bar <%=color %>" style="width: <%=cantidad%>%" title="<%=asambleaA.getFecha() %>"></div>
				<%		
			}else{
				%>
				<div class="progress-bar progress-bar-danger" style="width: <%=cantidad%>%" title="<%=asambleaA.getFecha() %>"></div>
			<%}
		}
	}
%>
</div>
<%}else{} %>
	<h2 align ="center">Eventos: </h2>
  	<link rel="stylesheet" href="../css/style.css" />
<%		
		int con = 1;
		for(aca.cultural.CompEventoImagen imagen: imagenes){ 
			con++;
		%>
    		<img onclick="location.href='informacion?eventoId=<%=imagen.getEventoId() %>'"src="../../../fotoEvento?eventoId=<%=imagen.getEventoId() %>&imagenId=<%=imagen.getImagenId() %>"
    		style="width:30%;"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    		<%if(con%3 == 1){%>
    		<br><br><br><br>
    		<%} %>
<%		}%>	
<br>
<br>
<br>
<br>
<%if(!reservados.isEmpty()){ %>
<table class="table table-condensed table-bordered" width="100%">
		<tbody>	
			<tr>
			<th colspan="7"><h3><span class="mw-headline" id="Reservados">Eventos Reservados</span></h3></th>
			
			</tr>
			<tr>
				<th>#</th>
				<th>Cancelar</th>
				<th><spring:message code="aca.Nombre"/> del evento</th>
				<th>Lugar</th>
				<th>Fecha y Hora</th>
			</tr>
		</tbody>
<%		
		int cont = 0;
		String hora = "";
		String fecha = "";
		for(aca.cultural.CompRegistro evento: reservados){
			cont++;
			String lugar 			= "";
			String eventoNombre		= "";
			String eventoId			= "";
			if(mapEvento.containsKey(evento.getEventoId())){
				aca.cultural.CompEvento event = mapEvento.get( evento.getEventoId() ); 
				lugar 					= event.getLugar();
				eventoNombre 			= event.getEventoNombre();
				eventoId 				= event.getEventoId();
				fecha 					= event.getFecha().substring(0, 10);
				hora					= event.getFecha().substring(11, 16);				
			}
%>
			<tr>
				<td width="2%"><%=cont %></td>
				
				<%if(evento.getEstado().equals("R")){ %>
				<td width="6%"><a href="javascript:Borrar('<%=eventoId%>');" class=" btn btn-sm btn-danger "><i class="fas fa-trash-alt icon-white"></i> Ya no quiero ir (>.<) </a></td>
				<%}else{%>
				<td width="6%"></td>	
			    <%} %>
				<td width="15%"><%=eventoNombre %></td>
				<td width="15%"><%=lugar %></td>
				<td width="1%"><%=fecha %> A las <%=hora %></td>
			</tr>
<%
		}
%>
	</table>
	<%}else{ } %>
<br>
<%if(!acreditados.isEmpty()){
	if(acreditados.size()==4){%>
	<div class="alert alert-info"><h3>Ya tiene los legados necesarios para el semestre. ¡FELICIDADES!</h3></div>
	<%}else if((acreditados.size()>=6)&&(acreditados.size()<=9)){%>
	<div class="alert"><h3> Ya te vas culturizando un poco más. Te admiro</h3></div>
	<%}else if(acreditados.size()>=10){%>
	<div class="alert alert-success">
	<center>
		<h3>¡¡Déjale legados a OTROS!! Eres un ejemplo a seguir.</h3>
		<hr>
			<img  src="http://www.logrosxbox.com/logrodesbloqueado/estiloone/1000/Ir+a+legados%2C+modo+leyenda.gif" alt="Generador de logros" />
		</center>
	</div>
	<%} %>
<table class="table table-condensed table-bordered" width="100%">
		<tbody>	
			<tr>
			<th colspan="5"><h3><span class="mw-headline" id="Acreditados">Eventos Acreditados</span></h3></th>
			</tr>
			<tr>
				<th>#</th>
				<th><spring:message code="aca.Nombre"/> del evento</th>
				<th>Lugar</th>
				<th><spring:message code="aca.Fecha"/></th>
			</tr>
		</tbody>
<%		
		int cont2 = 0;
		for(aca.cultural.CompRegistro acred: acreditados){
			cont2++;
			String lugar 			= "";
			String eventoNombre		= "";
			String fecha			= "";
			String eventoId			= "";
			if(mapEvento.containsKey(acred.getEventoId())){
				aca.cultural.CompEvento event = mapEvento.get( acred.getEventoId() ); 
				lugar 					= event.getLugar();
				eventoNombre 			= event.getEventoNombre();
				fecha 					= event.getFecha().substring(0, 10);
				eventoId 				= event.getEventoId();
			}
%>
			<tr>
				<td width="2%"><%=cont2 %></td>				
				<td width="20%"><%=eventoNombre %></td>
				<td width="20%"><%=lugar %></td>
				<td width="1%"><%=fecha %></td>
			</tr>
<%
		}
%>
	</table>
	<%}else{ }%>
	<br>
<script src="../js/jquery-1.7.1.min.js"></script>
<script src="../jquery.isotope.min.js"></script>
<script src="../js/fake-element.js"></script>
<script>
  $.Isotope.prototype._masonryResizeChanged = function() {
    return true;
  };

  $.Isotope.prototype._masonryReset = function() {
    // layout-specific props
    this.masonry = {};
    this._getSegments();
    var i = this.masonry.cols;
    this.masonry.colYs = [];
    while (i--) {
      this.masonry.colYs.push( 0 );
    }
  
    if ( this.options.masonry.cornerStampSelector ) {
      var $cornerStamp = this.element.find( this.options.masonry.cornerStampSelector ),
          stampWidth = $cornerStamp.outerWidth(true) - ( this.element.width() % this.masonry.columnWidth ),
          cornerCols = Math.ceil( stampWidth / this.masonry.columnWidth ),
          cornerStampHeight = $cornerStamp.outerHeight(true);
      for ( i = Math.max( this.masonry.cols - cornerCols, cornerCols ); i < this.masonry.cols; i++ ) {
        this.masonry.colYs[i] = cornerStampHeight;
      }
    }
  };


  $(function(){
    
    var $container = $('#container');

    $container.find('.element').each(function(){
        var $this = $(this),
            number = parseInt( $this.find('.number').text(), 10 );
        if ( number % 7 % 2 === 1 ) {
          $this.addClass('width2');
        }
        if ( number % 3 === 0 ) {
          $this.addClass('height2');
        }
      });
    
    $container.isotope({
      itemSelector : '.element',
      masonry : {
        columnWidth : 120,
        cornerStampSelector: '.corner-stamp'
      },
      getSortData : {
        symbol : function( $elem ) {
          return $elem.attr('data-symbol');
        },
        category : function( $elem ) {
          return $elem.attr('data-category');
        },
        number : function( $elem ) {
          return parseInt( $elem.find('.number').text(), 10 );
        },
        weight : function( $elem ) {
          return parseFloat( $elem.find('.weight').text().replace( /[\(\)]/g, '') );
        },
        name : function ( $elem ) {
          return $elem.find('.name').text();
        }
      }
    });
    
      
  });
</script>
<script type="text/javascript">
function Borrar(eventoId) {
	if (confirm("¿Está seguro de cancelar el evento?") == true) {
		document.location.href = "informacion?Accion=4&eventoId="+ eventoId;
	}
}

</script>
<script src="../bootstrap-fileupload.min.js"></script>
<%@ include file= "../../../cierra_enoc.jsp" %>