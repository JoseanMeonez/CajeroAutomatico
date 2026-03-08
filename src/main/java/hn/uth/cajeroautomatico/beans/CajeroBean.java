package hn.uth.cajeroautomatico.beans;

import hn.uth.cajeroautomatico.models.Cliente;
import hn.uth.cajeroautomatico.util.CargaDatos;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class CajeroBean implements Serializable {

    private List<Cliente> clientes = new ArrayList<>();
    private String numeroCuenta;
    private String pin;
    private double monto;
    private String mensaje;

    @PostConstruct
    public void init() {
        clientes = CargaDatos.cargarClientes();
    }

    private Cliente buscarCliente(String numeroCuenta) {
        for (Cliente c : clientes) {
            if (c.getNumeroCuenta().equals(numeroCuenta)) {
                return c;
            }
        }
        return null;
    }

    private boolean validarPin(Cliente cliente, String pin) {
        return cliente.getPin().equals(pin);
    }


    public void realizarDeposito() {
        FacesContext context = FacesContext.getCurrentInstance();

        Cliente cliente = buscarCliente(numeroCuenta);
        if (cliente == null || !validarPin(cliente, pin)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "PIN invalido", null));
            return;
        }

        if (monto <= 0) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Monto invalido", null));
            return;
        }

        cliente.setSaldo(cliente.getSaldo() + monto);
        CargaDatos.guardarClientes(clientes);
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Operacion exitosa - Nuevo saldo: L. " + String.format("%.2f", cliente.getSaldo()), null));
    }


    public void realizarRetiro() {
        // TODO: implementar logica de retiraki
    }

    public void consultarSaldo() {
        FacesContext context = FacesContext.getCurrentInstance();

        Cliente cliente = buscarCliente(numeroCuenta);
        if (cliente == null || !validarPin(cliente, pin)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "PIN invalido", null));
            return;
        }

        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Saldo actual: L. " + String.format("%.2f", cliente.getSaldo()), null));
    }

    // Getters y Setters

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
