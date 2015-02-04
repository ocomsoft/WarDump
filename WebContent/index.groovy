if (!session) {
	session = request.getSession(true)
}

// If asked to invalidate - then remove it.
if (params.newSession) {
	session.invalidate()
	session = request.getSession(true)
}

def dumpValue (parentElement, prop, value, level) {	
	if (value instanceof Map) {
		parentElement.td {
			dumpMap(delegate, prop, value, level+1)
		}
	}
	else if (value instanceof Collection) {
		parentElement.td {
			dumpCollection(delegate, prop, value, level+1)
		}
	}
	else {
		parentElement.td  value.toString() 
	}
}
def dumpMap (parentElement, String name, Map data, Integer level) {
	def hElement = "h${level}" 
	parentElement.div {
		a (id:name) {
			"$hElement" (name)
		}
		table (class:"table table-bordered") {
			thead (class:"") {
				tr {
					th (class:"col-md-2", "Parameter")					
					th "Value"
				}
			}
			tbody {
				data.each {prop, value ->
					tr {
						td prop
						dumpValue (delegate, prop, value, level)
					}
				}
			}
		}
	}
}
		
def dumpCollection (parentElement, String name, Collection data, Integer level) {
	def hElement = "h${level}"
	parentElement.div {
		a (id:name) {
			"$hElement" (name)
		}
		table (class:"table table-bordered") {
			thead (class:"") {
				tr {
					th (class:"col-md-2", "Index")
					th "Value"
				}
			}
			tbody {
				data.eachWithIndex {value, index ->
					tr {
						td index			
						dumpValue (delegate, index, value, level)						
					}
				}
			}
		}
	}
}

html.html {
	head {
		title "Hello from Server "
		link  (href:"//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css", rel:"stylesheet")
	}
	body {
		h1 "Hello ${context.getInitParameter('Version')}!"
		
		h2 "Session"
		p "Session ID: ${session.id}"
		a (href:request.requestURI+"?newSession=true", "New Session")			
		
		div (class:"row") {
			div (class:"col-sm-2") {
				div (class:"list-group") {
					a (class:"list-group-item", href:"#Request Dump", "Request Map")
					a (class:"list-group-item", href:"#Session Dump", "Session Map")
					a (class:"list-group-item", href:"#Parameters Dump", "Parameters Map")
					a (class:"list-group-item", href:"#Parameters Dump", "Parameters Map")
					a (class:"list-group-item", href:"#Headers Dump", "Headers Map")
					a (class:"list-group-item", href:"#Context Dump", "Context Map")
					a (class:"list-group-item", href:"#Environment Vars", "Environment Vars")
				}
			}			
			div (class:"col-sm-10") {
				dumpMap (delegate, "Request Dump", request.properties, 1)
				
				dumpMap (delegate, "Session Dump", session.properties, 1)
				
				dumpMap (delegate, "Parameters Dump", params, 1)
				
				dumpMap (delegate, "Headers Dump", headers, 1)
				
				dumpMap (delegate, "Context Dump", context.properties, 1)
				
				dumpMap (delegate, "Environment Vars", System.getenv(), 1)
			}
		}
		
		
	}
}