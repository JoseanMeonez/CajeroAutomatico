package hn.uth.cajeroautomatico.beans;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named("ClientesBean")
@ViewScoped
public class ClientesBean implements Serializable {
    private String nombre;
    private String apellido;

    public void guardar() {
        //AQUI DEBERIA DE IR LA LOGICA QUE GUARDA EL REGISTRO

        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Operación exitosa",
                "El registro se ha guardado exitosamente!"));
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}
