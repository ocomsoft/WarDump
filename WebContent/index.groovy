if (!session) {
	session = request.getSession(true)
}

// If asked to invalidate - then remove it.
if (params.newSession) {
	session.invalidate()
	session = request.getSession(true)
}

def dumpMap (parentElement, String name, Map data) {
	parentElement.div {
		h2 name
		table {
			thead {
				tr {
					td "Parameter"
					td "Value"
				}
			}
			tbody {
				data.each {prop, value ->
					tr {
						td prop
						td value
					}
				}
			}
		}
	}
}
		


html.html {
	head {
		title "Hello from Server "
	}
	body {
		h1 "Hello ${context.getInitParameter('Version')}!"
		
		h2 "Session"
		p "Session ID: ${session.id}"
		a (href:request.requestURI+"?newSession=true", "New Session")
		
		dumpMap (delegate, "Request Dump", request.properties)
		
		dumpMap (delegate, "Session Dump", session.properties)
		
		dumpMap (delegate, "Parameters Dump", params)
		
		dumpMap (delegate, "Headers Dump", headers)
		
		dumpMap (delegate, "Context Dump", context.properties)
		
	}
}