<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="alumno" scope="page" class="aca.alumno.AlumPersonal"/>
<jsp:useBean id="AlumUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="FedVoto" scope="page" class="aca.federacion.FedVoto"/>
<jsp:useBean id="FedVotoU" scope="page" class="aca.federacion.FedVotoUtil"/>
<jsp:useBean id="FedEvento" scope="page" class="aca.federacion.FedEvento"/>
<jsp:useBean id="FedEventoUtil" scope="page" class="aca.federacion.FedEventoUtil"/>
<jsp:useBean id="AlumnoEstado" scope="page" class="aca.alumno.AlumEstado"/>

<link href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css" rel="stylesheet">
<script src = "../..//js/jquery-1.7.1.min.js"></script> 
<script>
	function votar(){		
			document.forma.Accion.value = "1";
			document.forma.submit();
		/*
			if (confirm("¡No has votado por todos los puestos! ¿Deseas grabar tu votación incompleta?")){
				document.forma.Accion.value = "1";
				document.forma.submit();
			}
		*/		
	}
</script>

<style>
	body{
		padding: 0 20px;
	}
	
	.img-container{
		width: 180px;
		margin: 0 20px 0 0 !important;
		float: left;
	}
	
	input[type=radio]{
		margin: 2px 10px 0 0 !important;
	}
	
	.candidatos{
		overflow: hidden;
		margin-bottom: 20px;
	}
	
	
	img{
		width: 100%;
		border: 3px solid gray;
		display: block;
	}
	
	.graficas{
		width: 50%;
		padding: 30px;
		float: left;
		border: 1px solid #BDBDBD;
	}
</style>

<%
	
	String matricula 	= (String) session.getAttribute("codigoPersonal");
	alumno = AlumUtil.mapeaRegId(conEnoc,matricula);	
		
	ArrayList<aca.federacion.FedEvento> eventos = FedEventoUtil.getListEventosActuales(conEnoc, "");
	
	String eventoId = "5";
	FedEvento.mapeaRegId(conEnoc, eventoId);
	String [] presidente 	= FedEvento.getPresidente().split("-");
	String [] desarrollo 	= FedEvento.getDesarrollo().split("-");
	String [] financiero 	= FedEvento.getFinanciero().split("-");
	String [] ejecutivo 	= FedEvento.getEjecutivo().split("-");
	String [] secretario 	= FedEvento.getSecretario().split("-");
	
	
	
	String accion = request.getParameter("Accion")==null?"":request.getParameter("Accion");
	if(accion.equals("1")){
		FedVoto.setEventoId(eventoId);
		FedVoto.setCodigoPersonal(matricula);
		FedVoto.setPresidente(request.getParameter("presidente"));
		FedVoto.setDesarrollo(request.getParameter("desarrollo"));
		FedVoto.setFinanciero(request.getParameter("financiero"));
		FedVoto.setEjecutivo(request.getParameter("ejecutivo"));
		FedVoto.setSecretario(request.getParameter("secretario"));
		FedVoto.setFecha( aca.util.Fecha.getHoy() );
		
		if( FedVotoU.insertReg(conEnoc, FedVoto) ){
			
		}		
	}
	
	if( aca.federacion.FedVotoUtil.existeReg(conEnoc, eventoId, matricula) == false && 
		(aca.vista.InscritosUtil.inscrito(conEnoc, matricula) || 
		aca.alumno.EstadoUtil.getAlumnoInscrito(conEnoc, matricula, "15161B"))){
%>
	
	<h2>Vota por tus candidatos</h2>

	<hr />
	
<form action="" method="post" name="forma">
	<input type="hidden" name="Accion" />	
	
	<div class="alert alert-info">
		Presidente
	</div>
	
	<div class="candidatos">
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen?mat=<%=presidente[0] %>"  width="30" />
				<input type="radio" name="presidente" id="presidente" value="<%=presidente[0] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, presidente[0]) %>
			</label>
		</div>
		
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen?mat=<%=presidente[1] %>" alt="" />				
				<input type="radio" name="presidente" id="presidente" value="<%=presidente[1] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, presidente[1]) %>
			</label>
		</div>
		
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen?mat=<%=presidente[2] %>" alt="" />
				
				<input type="radio" name="presidente" id="presidente" value="<%=presidente[2] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, presidente[2]) %>
			</label>
		</div>
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen?mat=<%=presidente[3] %>" alt="" />				
				<input type="radio" name="presidente" id="presidente" value="<%=presidente[3] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, presidente[3]) %>
			</label>
		</div>
	</div>
	<div class="candidatos">
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen?mat=<%=presidente[4] %>" alt="" />				
				<input type="radio" name="presidente" id="presidente" value="<%=presidente[4] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, presidente[4]) %>
			</label>
		</div>
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen?mat=<%=presidente[5] %>" alt="" />				
				<input type="radio" name="presidente" id="presidente" value="<%=presidente[5] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, presidente[5]) %>
			</label>
		</div>
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen?mat=<%=presidente[6] %>" alt="" />				
				<input type="radio" name="presidente" id="presidente" value="<%=presidente[6] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, presidente[6]) %>
			</label>
		</div>
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen?mat=<%=presidente[7] %>" alt="" />
				<input type="radio" name="presidente" id="presidente" value="<%=presidente[7] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, presidente[7]) %>
			</label>
		</div>		
	</div>
		
	<div class="alert alert-info">
		Vicepresidente de desarrollo
	</div>
	
	<div class="candidatos">
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen?mat=<%=desarrollo[0] %>" alt="" />				
				<input type="radio" name="desarrollo" id="desarrollo" value="<%=desarrollo[0] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, desarrollo[0]) %>
			</label>
		</div>
		
		<div class="img-container radio">
			<label> 
				<img src="https://academico.um.edu.mx/academico/imagen?mat=<%=desarrollo[1] %>" alt="" />				
				<input type="radio" name="desarrollo" id="desarrollo" value="<%=desarrollo[1] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, desarrollo[1]) %>
			</label>
		</div>
		
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen?mat=<%=desarrollo[2] %>" alt="" />				
				<input type="radio" name="desarrollo" id="desarrollo" value="<%=desarrollo[2] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, desarrollo[2]) %>
			</label>
		</div>
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen?mat=<%=desarrollo[3] %>" alt="" />				
				<input type="radio" name="desarrollo" id="desarrollo" value="<%=desarrollo[3] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, desarrollo[3]) %>
			</label>
		</div>
	</div>
	<div class="candidatos">
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen?mat=<%=desarrollo[4] %>" alt="" />				
				<input type="radio" name="desarrollo" id="desarrollo" value="<%=desarrollo[4] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, desarrollo[4]) %>
			</label>
		</div>
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen?mat=<%=desarrollo[5] %>" alt="" />				
				<input type="radio" name="desarrollo" id="desarrollo" value="<%=desarrollo[5] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, desarrollo[5]) %>
			</label>
		</div>
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen?mat=<%=desarrollo[6] %>" alt="" />				
				<input type="radio" name="desarrollo" id="desarrollo" value="<%=desarrollo[6] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, desarrollo[6]) %>
			</label>
		</div>
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen?mat=<%=desarrollo[7] %>" alt="" />				
				<input type="radio" name="desarrollo" id="desarrollo" value="<%=desarrollo[7] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, desarrollo[7]) %>
			</label>
		</div>				
	</div>	
	
	
	
	<div class="alert alert-info">
		Vicepresidente financiero
	</div>
	
	<div class="candidatos">
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen?mat=<%=financiero[0] %>" alt="" />
				<input type="radio" name="financiero" id="financiero" value="<%=financiero[0] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, financiero[0]) %>
			</label>
		</div>
		
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen?mat=<%=financiero[1] %>" alt="" />
				<input type="radio" name="financiero" id="financiero" value="<%=financiero[1] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, financiero[1]) %>
			</label>
		</div>
		
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen?mat=<%=financiero[2] %>" alt="" />
				<input type="radio" name="financiero" id="financiero" value="<%=financiero[2] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, financiero[2]) %>
			</label>
		</div>
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen?mat=<%=financiero[3] %>" alt="" />
				<input type="radio" name="financiero" id="financiero" value="<%=financiero[3] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, financiero[3]) %>
			</label>
		</div>
	</div>	
	<div class="candidatos">
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen?mat=<%=financiero[4] %>" alt="" />
				<input type="radio" name="financiero" id="financiero" value="<%=financiero[4] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, financiero[4]) %>
			</label>
		</div>
		
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen?mat=<%=financiero[5] %>" alt="" />
				<input type="radio" name="financiero" id="financiero" value="<%=financiero[5] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, financiero[5]) %>
			</label>
		</div>
		
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen?mat=<%=financiero[6] %>" alt="" />
				<input type="radio" name="financiero" id="financiero" value="<%=financiero[6] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, financiero[6]) %>
			</label>
		</div>
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen?mat=<%=financiero[7] %>" alt="" />
				<input type="radio" name="financiero" id="financiero" value="<%=financiero[7] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, financiero[7]) %>
			</label>
		</div>
	</div>
		
	<div class="alert alert-info">
		Vicepresidente ejecutivo
	</div>
	
	<div class="candidatos">
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen?mat=<%=ejecutivo[0] %>" alt=""  />
				<input type="radio" name="ejecutivo" id="ejecutivo" value="<%=ejecutivo[0] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, ejecutivo[0]) %>
			</label>
		</div>
		
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen?mat=<%=ejecutivo[1] %>" alt="" />
				<input type="radio" name="ejecutivo" id="ejecutivo" value="<%=ejecutivo[1] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, ejecutivo[1]) %>
			</label>
		</div>
		
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen?mat=<%=ejecutivo[2] %>" alt="" />
				<input type="radio" name="ejecutivo" id="ejecutivo" value="<%=ejecutivo[2] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, ejecutivo[2]) %>
			</label>
		</div>
		
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen?mat=<%=ejecutivo[3] %>" alt=""   />
				<input type="radio" name="ejecutivo" id="ejecutivo" value="<%=ejecutivo[3] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, ejecutivo[3]) %>
			</label>
		</div>
	</div>	
	<div class="candidatos">
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen?mat=<%=ejecutivo[4] %>" alt=""  />
				<input type="radio" name="ejecutivo" id="ejecutivo" value="<%=ejecutivo[4] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, ejecutivo[4]) %>
			</label>
		</div>
		
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen?mat=<%=ejecutivo[5] %>" alt="" />
				<input type="radio" name="ejecutivo" id="ejecutivo" value="<%=ejecutivo[5] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, ejecutivo[5]) %>
			</label>
		</div>
		
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen?mat=<%=ejecutivo[6] %>" alt="" />
				<input type="radio" name="ejecutivo" id="ejecutivo" value="<%=ejecutivo[6] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, ejecutivo[6]) %>
			</label>
		</div>
		
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen?mat=<%=ejecutivo[7] %>" alt=""   />
				<input type="radio" name="ejecutivo" id="ejecutivo" value="<%=ejecutivo[7] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, ejecutivo[7]) %>
			</label>
		</div>
	</div>	
	
	
	<div class="alert alert-info">
		Secretario
	</div>
	
	<div class="candidatos">
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen?mat=<%=secretario[0] %>" alt="" />
				<input type="radio" name="secretario" id="secretario" value="<%=secretario[0] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, secretario[0]) %>
			</label>
		</div>
		
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen?mat=<%=secretario[1] %>" alt="" />
				<input type="radio" name="secretario" id="secretario" value="<%=secretario[1] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, secretario[1]) %>
			</label>
		</div>
		
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen?mat=<%=secretario[2] %>" alt="" />
				<input type="radio" name="secretario" id="secretario" value="<%=secretario[2] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, secretario[2]) %>
			</label>
		</div>
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen?mat=<%=secretario[3] %>" alt="" />
				<input type="radio" name="secretario" id="secretario" value="<%=secretario[3] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, secretario[3]) %>
			</label>
		</div>
	</div>	
	<div class="candidatos">
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen?mat=<%=secretario[4] %>" alt="" />
				<input type="radio" name="secretario" id="secretario" value="<%=secretario[4] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, secretario[4]) %>
			</label>
		</div>
		
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen?mat=<%=secretario[5] %>" alt="" />
				<input type="radio" name="secretario" id="secretario" value="<%=secretario[5] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, secretario[5]) %>
			</label>
		</div>
		
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen?mat=<%=secretario[6] %>" alt="" />
				<input type="radio" name="secretario" id="secretario" value="<%=secretario[6] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, secretario[6]) %>
			</label>
		</div>
		<div class="img-container radio">
			<label>
				<img src="https://academico.um.edu.mx/academico/imagen?mat=<%=secretario[7] %>" alt="" />
				<input type="radio" name="secretario" id="secretario" value="<%=secretario[7] %>">&nbsp;&nbsp;&nbsp;
				<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, secretario[7]) %>
			</label>
		</div>
	</div>	
	
	<div class="alert alert-info">
		<a href="javascript:votar();" class="btn btn-primary btn-lg"><i class="glyphicon glyphicon-ok"></i> Votar</a>
	</div>

</form>
	
<%}else{%>

<%
	java.util.HashMap<String, String> presidentes 	= aca.federacion.FedVotoUtil.mapPresidente(conEnoc, eventoId);
	java.util.HashMap<String, String> desarrollos 	= aca.federacion.FedVotoUtil.mapDesarrollo(conEnoc, eventoId);
	java.util.HashMap<String, String> financieros 	= aca.federacion.FedVotoUtil.mapFinanciero(conEnoc, eventoId);
	java.util.HashMap<String, String> ejecutivos 	= aca.federacion.FedVotoUtil.mapEjecutivo(conEnoc, eventoId);
	java.util.HashMap<String, String> secretarios 	= aca.federacion.FedVotoUtil.mapSecretario(conEnoc, eventoId);
%>
	
	<h2>Resultados de la votación</h2>

	<hr />

	<div class="alert alert-info">Personas que han votado: <%= aca.federacion.FedVotoUtil.participantes(conEnoc, eventoId) %></div>
	
	<script src="highcharts/highcharts.js"></script>
	<script src="highcharts/exporting.js"></script>

	<div class="graficas" id="presidente" style="min-width: 310px; height: 400px; margin: 0 auto; border-bottom:0; border-right:0;"></div>
	
	<script>
		jQuery(function () {
			jQuery('#presidente').highcharts({
	            chart: {
	                type: 'column'
	            },
	            title: {
	                text: 'Presidente'
	            },
	            
	            xAxis: {
	                categories: [
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, presidente[0]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, presidente[1]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, presidente[2]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, presidente[3]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, presidente[4]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, presidente[5]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, presidente[6]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, presidente[7]) %>',
	                ]
	            },
	            yAxis: {
	                min: 0,
	                title: {
	                    text: 'Votaciones'
	                }
	            },
	            tooltip: {
	                headerFormat: '',
	                pointFormat: '<tr>' +
	                    '<td style="padding:0"><b>{point.y:.1f}</b></td></tr>',
	                footerFormat: '</table>',
	                shared: true,
	                useHTML: true
	            },
	            plotOptions: {
	                column: {
	                    pointPadding: 0.2,
	                    borderWidth: 0
	                }
	            },
	            series: [{
	                name: 'Presidente',
	                data: [<%=presidentes.get(presidente[0]) %>, <%=presidentes.get(presidente[1]) %>, <%=presidentes.get(presidente[2]) %>, <%=presidentes.get(presidente[3]) %>,
	                       <%=presidentes.get(presidente[4]) %>,<%=presidentes.get(presidente[5]) %>,<%=presidentes.get(presidente[6]) %>,<%=presidentes.get(presidente[7]) %>]
	    
	            }]
	        });
	    });
	</script>
	
	
	<div class="graficas" id="desarrollo" style="min-width: 310px; height: 400px; margin: 0 auto; border-bottom:0; "></div>
	
	<script>
		jQuery(function () {
			jQuery('#desarrollo').highcharts({
	            chart: {
	                type: 'column'
	            },
	            title: {
	                text: 'Vicepresidente de desarrollo'
	            },
	            
	            xAxis: {
	                categories: [
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, desarrollo[0]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, desarrollo[1]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, desarrollo[2]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, desarrollo[3]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, desarrollo[4]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, desarrollo[5]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, desarrollo[6]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, desarrollo[7]) %>',
	                ]
	            },
	            yAxis: {
	                min: 0,
	                title: {
	                    text: 'Votaciones'
	                }
	            },
	            tooltip: {
	                headerFormat: '',
	                pointFormat: '<tr>' +
	                    '<td style="padding:0"><b>{point.y:.1f}</b></td></tr>',
	                footerFormat: '</table>',
	                shared: true,
	                useHTML: true
	            },
	            plotOptions: {
	                column: {
	                    pointPadding: 0.2,
	                    borderWidth: 0
	                }
	            },
	            series: [{
	                name: 'Vicepresidente de desarrollo',
	                data: [<%=desarrollos.get(desarrollo[0]) %>, <%=desarrollos.get(desarrollo[1]) %>, <%=desarrollos.get(desarrollo[2]) %>, <%=desarrollos.get(desarrollo[3]) %>,
	                       <%=desarrollos.get(desarrollo[4]) %>,<%=desarrollos.get(desarrollo[5]) %>,<%=desarrollos.get(desarrollo[6]) %>,<%=desarrollos.get(desarrollo[7]) %>]
	    
	            }]
	        });
	    });
	</script>
	
	
	<div class="graficas" id="financiero" style="min-width: 310px; height: 400px; margin: 0 auto; border-bottom:0; border-right:0;"></div>
	
	<script>
		jQuery(function () {
			jQuery('#financiero').highcharts({
	            chart: {
	                type: 'column'
	            },
	            title: {
	                text: 'Vicepresidente financiero'
	            },
	            
	            xAxis: {
	                categories: [
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, financiero[0]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, financiero[1]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, financiero[2]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, financiero[3]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, financiero[4]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, financiero[5]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, financiero[6]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, financiero[7]) %>',
	                ]
	            },
	            yAxis: {
	                min: 0,
	                title: {
	                    text: 'Votaciones'
	                }
	            },
	            tooltip: {
	                headerFormat: '',
	                pointFormat: '<tr>' +
	                    '<td style="padding:0"><b>{point.y:.1f}</b></td></tr>',
	                footerFormat: '</table>',
	                shared: true,
	                useHTML: true
	            },
	            plotOptions: {
	                column: {
	                    pointPadding: 0.2,
	                    borderWidth: 0
	                }
	            },
	            series: [{
	                name: 'Vicepresidente financiero',
	                data: [<%=financieros.get(financiero[0]) %>, <%=financieros.get(financiero[1]) %>, <%=financieros.get(financiero[2]) %>, <%=financieros.get(financiero[3]) %>,
	                       <%=financieros.get(financiero[4]) %>,<%=financieros.get(financiero[5]) %>,<%=financieros.get(financiero[6]) %>,<%=financieros.get(financiero[7]) %>]
	    
	            }]
	        });
	    });
		
	</script>
	
	
	
	<div class="graficas" id="ejecutivo" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
	
	<script>
		jQuery(function () {
			jQuery('#ejecutivo').highcharts({
	            chart: {
	                type: 'column'
	            },
	            title: {
	                text: 'Vicepresidente ejecutivo'
	            },
	            
	            xAxis: {
	                categories: [
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, ejecutivo[0]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, ejecutivo[1]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, ejecutivo[2]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, ejecutivo[3]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, ejecutivo[4]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, ejecutivo[5]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, ejecutivo[6]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, ejecutivo[7]) %>',
	                ]
	            },
	            yAxis: {
	                min: 0,
	                title: {
	                    text: 'Votaciones'
	                }
	            },
	            tooltip: {
	                headerFormat: '',
	                pointFormat: '<tr>' +
	                    '<td style="padding:0"><b>{point.y:.1f}</b></td></tr>',
	                footerFormat: '</table>',
	                shared: true,
	                useHTML: true
	            },
	            plotOptions: {
	                column: {
	                    pointPadding: 0.2,
	                    borderWidth: 0
	                }
	            },
	            series: [{
	                name: 'Vicepresidente ejecutivo',
	                data: [<%=ejecutivos.get(ejecutivo[0]) %>, <%=ejecutivos.get(ejecutivo[1]) %>, <%=ejecutivos.get(ejecutivo[2]) %>, <%=ejecutivos.get(ejecutivo[3]) %>,
	                       <%=ejecutivos.get(ejecutivo[4]) %>,<%=ejecutivos.get(ejecutivo[5]) %>,<%=ejecutivos.get(ejecutivo[6]) %>,<%=ejecutivos.get(ejecutivo[7]) %>]
	    
	            }]
	        });
	    });
	</script>
	
	
	<div class="graficas" id="secretario" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
	
	<script>
		jQuery(function () {
			jQuery('#secretario').highcharts({
	            chart: {
	                type: 'column'
	            },
	            title: {
	                text: 'Secretario'
	            },
	            
	            xAxis: {
	                categories: [
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, secretario[0]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, secretario[1]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, secretario[2]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, secretario[3]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, secretario[4]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, secretario[5]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, secretario[6]) %>',
	                    '<%=aca.alumno.AlumUtil.getNombreMuyCorto(conEnoc, secretario[7]) %>',
	                ]
	            },
	            yAxis: {
	                min: 0,
	                title: {
	                    text: 'Votaciones'
	                }
	            },
	            tooltip: {
	                headerFormat: '',
	                pointFormat: '<tr>' +
	                    '<td style="padding:0"><b>{point.y:.1f}</b></td></tr>',
	                footerFormat: '</table>',
	                shared: true,
	                useHTML: true
	            },
	            plotOptions: {
	                column: {
	                    pointPadding: 0.2,
	                    borderWidth: 0
	                }
	            },
	            series: [{
	                name: 'Secretario',
	                data: [<%=secretarios.get(secretario[0]) %>, <%=secretarios.get(secretario[1]) %>, <%=secretarios.get(secretario[2]) %>, <%=secretarios.get(secretario[3])%>,
	                       <%=secretarios.get(secretario[4]) %>,<%=secretarios.get(secretario[5]) %>,<%=secretarios.get(secretario[6]) %>,<%=secretarios.get(secretario[7]) %>]
	    
	            }]
	        });
	    });
	</script>

<%} %>

<%@ include file= "../../cierra_enoc.jsp" %>