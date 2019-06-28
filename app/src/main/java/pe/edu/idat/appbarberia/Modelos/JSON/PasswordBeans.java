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
"apass",
"bNewPass"
})
public class PasswordBeans implements Serializable
{

@JsonProperty("apass")
private String apass;
@JsonProperty("bNewPass")
private String bNewPass;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();
private final static long serialVersionUID = -628444199621391940L;

/**
* No args constructor for use in serialization
* 
*/
public PasswordBeans() {
}

/**
* 
* @param apass
* @param bNewPass
*/
public PasswordBeans(String apass, String bNewPass) {
super();
this.apass = apass;
this.bNewPass = bNewPass;
}

@JsonProperty("apass")
public String getApass() {
return apass;
}

@JsonProperty("apass")
public void setApass(String apass) {
this.apass = apass;
}

public PasswordBeans withApass(String apass) {
this.apass = apass;
return this;
}

@JsonProperty("bNewPass")
public String getBNewPass() {
return bNewPass;
}

@JsonProperty("bNewPass")
public void setBNewPass(String bNewPass) {
this.bNewPass = bNewPass;
}

public PasswordBeans withBNewPass(String bNewPass) {
this.bNewPass = bNewPass;
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

public PasswordBeans withAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
return this;
}

}