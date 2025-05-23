<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">	
	<!-- bootstrap -->	
	<link rel="stylesheet" href="../../bootstrap/css/bootstrap.min.css" type="text/css" media="screen" />
	<link rel="stylesheet" href="../../bootstrap/css/bootstrap-responsive.min.css" type="text/css" media="screen" />
	<script src='../../bootstrap/js/bootstrap.min.js' type='text/javascript'></script>	
	<script src="../../js/jquery-1.9.1.min.js"></script>	
	<style type="text/css">
		#content{
			margin: 20px 20px 0 20px;
		}	  	
	</style>
</head>
<%
	String codigo 				= (String)session.getAttribute("codigoPersonal");
%>
<div class="container-fluid">
	<h2>Metrics</h2>
	<div class="alert alert-info">
		&nbsp;
	</div>
	<table>
	<tr><td>1</td><td><a href="../../monitoraca/metrics/jvm.memory.max" target="_blank">jvm.memory.max</a></td></tr>
	<tr><td>2</td><td><a href="../../monitoraca/metrics/jdbc.connections.max" target="_blank">jdbc.connections.max</a></td></tr>
	<tr><td>3</td><td><a href="../../monitoraca/metrics/jdbc.connections.min" target="_blank">jdbc.connections.min</a></td></tr>
	<tr><td>4</td><td><a href="../../monitoraca/metrics/jvm.threads.states" target="_blank">jvm.threads.states</a></td></tr>
	<tr><td>5</td><td><a href="../../monitoraca/metrics/jvm.gc.memory.promoted" target="_blank">jvm.gc.memory.promoted</a></td></tr>
	<tr><td>6</td><td><a href="../../monitoraca/metrics/http.server.requests" target="_blank">http.server.requests</a></td></tr>
	<tr><td>7</td><td><a href="../../monitoraca/metrics/jvm.memory.used" target="_blank">jvm.memory.used</a></td></tr>
	<tr><td>8</td><td><a href="../../monitoraca/metrics/jvm.gc.max.data.size" target="_blank">jvm.gc.max.data.size</a></td></tr>
	<tr><td>9</td><td><a href="../../monitoraca/metrics/jvm.gc.pause" target="_blank">jvm.gc.pause</a></td></tr>
	<tr><td>10</td><td><a href="../../monitoraca/metrics/jvm.memory.committed" target="_blank">jvm.memory.committed</a></td></tr>
	<tr><td>11</td><td><a href="../../monitoraca/metrics/system.cpu.count" target="_blank">system.cpu.count</a></td></tr>
	<tr><td>12</td><td><a href="../../monitoraca/metrics/logback.events" target="_blank">logback.events</a></td></tr>
	<tr><td>13</td><td><a href="../../monitoraca/metrics/tomcat.global.sent" target="_blank">tomcat.global.sent</a></td></tr>
	<tr><td>14</td><td><a href="../../monitoraca/metrics/jvm.buffer.memory.used" target="_blank">jvm.buffer.memory.used</a></td></tr>
	<tr><td>15</td><td><a href="../../monitoraca/metrics/tomcat.sessions.created" target="_blank">tomcat.sessions.created</a></td></tr>
	<tr><td>16</td><td><a href="../../monitoraca/metrics/jvm.threads.daemon" target="_blank">jvm.threads.daemon</a></td></tr>
	<tr><td>17</td><td><a href="../../monitoraca/metrics/system.cpu.usage" target="_blank">system.cpu.usage</a></td></tr>
	<tr><td>18</td><td><a href="../../monitoraca/metrics/jvm.gc.memory.allocated" target="_blank">jvm.gc.memory.allocated</a></td></tr>
	<tr><td>19</td><td><a href="../../monitoraca/metrics/tomcat.global.request.max" target="_blank">tomcat.global.request.max</a></td></tr>	
	<tr><td>22</td><td><a href="../../monitoraca/metrics/tomcat.global.request" target="_blank">tomcat.global.request</a></td></tr>
	<tr><td>23</td><td><a href="../../monitoraca/metrics/tomcat.sessions.expired" target="_blank">tomcat.sessions.expired</a></td></tr>
	<tr><td>24</td><td><a href="../../monitoraca/metrics/hikaricp.connections" target="_blank">hikaricp.connections</a></td></tr>
	<tr><td>25</td><td><a href="../../monitoraca/metrics/jvm.threads.live" target="_blank">jvm.threads.live</a></td></tr>
	<tr><td>26</td><td><a href="../../monitoraca/metrics/jvm.threads.peak" target="_blank">jvm.threads.peak</a></td></tr>
	<tr><td>27</td><td><a href="../../monitoraca/metrics/tomcat.global.received" target="_blank">tomcat.global.received</a></td></tr>
	<tr><td>28</td><td><a href="../../monitoraca/metrics/jdbc.connections.active" target="_blank">jdbc.connections.active</a></td></tr>	
	<tr><td>30</td><td><a href="../../monitoraca/metrics/process.uptime" target="_blank">process.uptime</a></td></tr>
	<tr><td>31</td><td><a href="../../monitoraca/metrics/tomcat.sessions.rejected" target="_blank">tomcat.sessions.rejected</a></td></tr>
	<tr><td>32</td><td><a href="../../monitoraca/metrics/process.cpu.usage" target="_blank">process.cpu.usage</a></td></tr>
	<tr><td>33</td><td><a href="../../monitoraca/metrics/tomcat.threads.config.max" target="_blank">tomcat.threads.config.max</a></td></tr>
	<tr><td>34</td><td><a href="../../monitoraca/metrics/jvm.classes.loaded" target="_blank">jvm.classes.loaded</a></td></tr>
	<tr><td>35</td><td><a href="../../monitoraca/metrics/jdbc.connections.max" target="_blank">jdbc.connections.max</a></td></tr>
	<tr><td>36</td><td><a href="../../monitoraca/metrics/jdbc.connections.min" target="_blank">jdbc.connections.min</a></td></tr>
	<tr><td>37</td><td><a href="../../monitoraca/metrics/jvm.classes.unloaded" target="_blank">jvm.classes.unloaded</a></td></tr>
	<tr><td>38</td><td><a href="../../monitoraca/metrics/tomcat.global.error" target="_blank">tomcat.global.error</a></td></tr>
	<tr><td>39</td><td><a href="../../monitoraca/metrics/tomcat.sessions.active.current" target="_blank">tomcat.sessions.active.current</a></td></tr>
	<tr><td>40</td><td><a href="../../monitoraca/metrics/tomcat.sessions.alive.max" target="_blank">tomcat.sessions.alive.max</a></td></tr>
	<tr><td>41</td><td><a href="../../monitoraca/metrics/jvm.gc.live.data.size" target="_blank">jvm.gc.live.data.size</a></td></tr>	
	<tr><td>43</td><td><a href="../../monitoraca/metrics/tomcat.threads.current" target="_blank">tomcat.threads.current</a></td></tr>
	<tr><td>44</td><td><a href="../../monitoraca/metrics/hikaricp.connections.timeout" target="_blank">hikaricp.connections.timeout</a></td></tr>
	<tr><td>45</td><td><a href="../../monitoraca/metrics/jvm.buffer.count" target="_blank">jvm.buffer.count</a></td></tr>
	<tr><td>46</td><td><a href="../../monitoraca/metrics/jvm.buffer.total.capacity" target="_blank">jvm.buffer.total.capacity</a></td></tr>
	<tr><td>47</td><td><a href="../../monitoraca/metrics/tomcat.sessions.active.max" target="_blank">tomcat.sessions.active.max</a></td></tr>	
	<tr><td>49</td><td><a href="../../monitoraca/metrics/tomcat.threads.busy" target="_blank">tomcat.threads.busy</a></td></tr>
	<tr><td>50</td><td><a href="../../monitoraca/metrics/process.start.time" target="_blank">process.start.time</a></td></tr>
	</table>
</div>
</html>