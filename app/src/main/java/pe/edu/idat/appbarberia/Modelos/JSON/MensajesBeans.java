package pe.edu.idat.appbarberia.Modelos.JSON;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"mensaje"
})
public class MensajesBeans implements Serializable
{

@JsonProperty("mensaje")
private String mensaje;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();
private final static long serialVersionUID = 6353273872088799621L;

/**
* No args constructor for use in serialization
* 
*/
public MensajesBeans() {
}

/**
* 
* @param mensaje
*/
public MensajesBeans(String mensaje) {
super();
this.mensaje = mensaje;
}

@JsonProperty("mensaje")
public String getMensaje() {
return mensaje;
}

@JsonProperty("mensaje")
public void setMensaje(String mensaje) {
this.mensaje = mensaje;
}

public MensajesBeans withMensaje(String mensaje) {
this.mensaje = mensaje;
return this;
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

public MensajesBeans withAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
return this;
}

@Override
public String toString() {
	return "[" + mensaje + "]";
}

}