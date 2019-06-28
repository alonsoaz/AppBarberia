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
"aNombre",
"bApellido",
"cTelefono",
"dDni",
"eEmail",
"fDirecion",
"password"
})
public class ClienteBeans implements Serializable
{

@JsonProperty("aNombre")
private String aNombre;
@JsonProperty("bApellido")
private String bApellido;
@JsonProperty("cTelefono")
private String cTelefono;
@JsonProperty("dDni")
private String dDni;
@JsonProperty("eEmail")
private String eEmail;
@JsonProperty("fDirecion")
private String fDirecion;
@JsonProperty("password")
private String password;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();
private final static long serialVersionUID = 5439642700780738832L;

/**
* No args constructor for use in serialization
* 
*/
public ClienteBeans() {
}

/**
* 
* @param cTelefono
* @param aNombre
* @param password
* @param eEmail
* @param fDirecion
* @param bApellido
* @param dDni
*/
public ClienteBeans(String aNombre, String bApellido, String cTelefono, String dDni, String eEmail, String fDirecion, String password) {
super();
this.aNombre = aNombre;
this.bApellido = bApellido;
this.cTelefono = cTelefono;
this.dDni = dDni;
this.eEmail = eEmail;
this.fDirecion = fDirecion;
this.password = password;
}

@JsonProperty("aNombre")
public String getANombre() {
return aNombre;
}

@JsonProperty("aNombre")
public void setANombre(String aNombre) {
this.aNombre = aNombre;
}

public ClienteBeans withANombre(String aNombre) {
this.aNombre = aNombre;
return this;
}

@JsonProperty("bApellido")
public String getBApellido() {
return bApellido;
}

@JsonProperty("bApellido")
public void setBApellido(String bApellido) {
this.bApellido = bApellido;
}

public ClienteBeans withBApellido(String bApellido) {
this.bApellido = bApellido;
return this;
}

@JsonProperty("cTelefono")
public String getCTelefono() {
return cTelefono;
}

@JsonProperty("cTelefono")
public void setCTelefono(String cTelefono) {
this.cTelefono = cTelefono;
}

public ClienteBeans withCTelefono(String cTelefono) {
this.cTelefono = cTelefono;
return this;
}

@JsonProperty("dDni")
public String getDDni() {
return dDni;
}

@JsonProperty("dDni")
public void setDDni(String dDni) {
this.dDni = dDni;
}

public ClienteBeans withDDni(String dDni) {
this.dDni = dDni;
return this;
}

@JsonProperty("eEmail")
public String getEEmail() {
return eEmail;
}

@JsonProperty("eEmail")
public void setEEmail(String eEmail) {
this.eEmail = eEmail;
}

public ClienteBeans withEEmail(String eEmail) {
this.eEmail = eEmail;
return this;
}

@JsonProperty("fDirecion")
public String getFDirecion() {
return fDirecion;
}

@JsonProperty("fDirecion")
public void setFDirecion(String fDirecion) {
this.fDirecion = fDirecion;
}

public ClienteBeans withFDirecion(String fDirecion) {
this.fDirecion = fDirecion;
return this;
}

@JsonProperty("password")
public String getPassword() {
return password;
}

@JsonProperty("password")
public void setPassword(String password) {
this.password = password;
}

public ClienteBeans withPassword(String password) {
this.password = password;
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

public ClienteBeans withAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
return this;
}

}