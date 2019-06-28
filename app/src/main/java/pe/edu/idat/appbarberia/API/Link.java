package pe.edu.idat.appbarberia.API;

public class Link {

    public String urlLocal;
    public String login;
    public String registro;
    public String getCliente;
    public String actualizar;
    public String cambiarPassword;
    public String listarBarberos;
    public String getBarbero;
    public String listarServicios;
    public String getServicio;
    public String consultaDisponibilidad;
    public String registraReserva;
    public String listarReservas;
    public String getReserva;
    public String payReserva;
    public String delReserva;
    public String listProductos;
    public String regDetVenta;
    public String showVentaXpagar;
    public String delVenta;
    public String payVenta;
    public String showDVxPagar;
    public String getDV;
    public String delDV;
    public String payDetVenta;
    public String showVentaPagada;
    public String showDetVentaPagada;


    public Link(Sesion sesion) {
        this.urlLocal = "http://"+sesion.getUrl()+":8083";
        this.login = urlLocal+"/barberia/cliente/android/login";
        this.registro = urlLocal+"/barberia/cliente/android/register";
        this.getCliente = urlLocal+"/barberia/cliente/android/get/"+sesion.getIdCliente();
        this.actualizar = urlLocal+"/barberia/cliente/android/update/"+sesion.getIdCliente();
        this.cambiarPassword = urlLocal+"/barberia/cliente/android/changepass/"+sesion.getIdCliente();
        this.listarBarberos = urlLocal+"/barberia/barbero/android/allbarberos";
        this.getBarbero = urlLocal+"/barberia/barbero/android/"+sesion.getIdBarbero();
        this.listarServicios = urlLocal+"/barberia/servicio/android/allservicios";
        this.getServicio = urlLocal+"/barberia/servicio/android/"+sesion.getIdServicio();
        this.consultaDisponibilidad = urlLocal+"/barberia/reserva/"+sesion.getIdCliente()+"/android/barbero/"+sesion.getIdBarbero()+"/consulta/"+sesion.getIdServicio();
        this.registraReserva = urlLocal+"/barberia/reserva/"+sesion.getIdCliente()+"/android/barbero/"+sesion.getIdBarbero()+"/registro/"+sesion.getIdServicio();
        this.listarReservas = urlLocal+"/barberia/reserva/"+sesion.getIdCliente()+"/android/allreservas";
        this.getReserva = urlLocal+"/barberia/reserva/"+sesion.getIdCliente()+"/android/"+sesion.getIdReserva();
        this.payReserva = urlLocal+"/barberia/reserva/"+sesion.getIdCliente()+"/android/"+sesion.getIdReserva()+"/pay";
        this.delReserva = urlLocal+"/barberia/reserva/"+sesion.getIdCliente()+"/android/"+sesion.getIdReserva()+"/delete";
        this.listProductos = urlLocal+"/barberia/producto/android/allproductos";
        this.regDetVenta = urlLocal+"/barberia/compras/"+sesion.getIdCliente()+"/android/producto/";
        this.showVentaXpagar = urlLocal+"/barberia/compras/"+sesion.getIdCliente()+"/android/carrito";
        this.delVenta = urlLocal+"/barberia/compras/"+sesion.getIdCliente()+"/android/carrito/delete";
        this.payVenta = urlLocal+"/barberia/compras/"+sesion.getIdCliente()+"/android/carrito/pagar";
        this.showDVxPagar = urlLocal+"/barberia/compras/"+sesion.getIdCliente()+"/android/carrito/detalle";
        this.getDV = urlLocal+"/barberia/compras/"+sesion.getIdCliente()+"/android/carrito/detalle/";
        this.delDV = urlLocal+"/barberia/compras/"+sesion.getIdCliente()+"/android/carrito/detalle/delete/";
        this.payDetVenta = urlLocal+"/barberia/compras/"+sesion.getIdCliente()+"/android/carrito/detalle/pagar/";
        this.showVentaPagada = urlLocal+"/barberia/compras/"+sesion.getIdCliente()+"/android/allcompras";
        this.showDetVentaPagada = urlLocal+"/barberia/compras/"+sesion.getIdCliente()+"/android/allcompras/detalle/";
    }

    public String getRegDetVenta() {
        return regDetVenta;
    }

    public void setRegDetVenta(String regDetVenta) {
        this.regDetVenta = regDetVenta;
    }

    public String getShowVentaXpagar() {
        return showVentaXpagar;
    }

    public void setShowVentaXpagar(String showVentaXpagar) {
        this.showVentaXpagar = showVentaXpagar;
    }

    public String getDelVenta() {
        return delVenta;
    }

    public void setDelVenta(String delVenta) {
        this.delVenta = delVenta;
    }

    public String getPayVenta() {
        return payVenta;
    }

    public void setPayVenta(String payVenta) {
        this.payVenta = payVenta;
    }

    public String getShowDVxPagar() {
        return showDVxPagar;
    }

    public void setShowDVxPagar(String showDVxPagar) {
        this.showDVxPagar = showDVxPagar;
    }

    public String getGetDV() {
        return getDV;
    }

    public void setGetDV(String getDV) {
        this.getDV = getDV;
    }

    public String getDelDV() {
        return delDV;
    }

    public void setDelDV(String delDV) {
        this.delDV = delDV;
    }

    public String getPayDetVenta() {
        return payDetVenta;
    }

    public void setPayDetVenta(String payDetVenta) {
        this.payDetVenta = payDetVenta;
    }

    public String getShowVentaPagada() {
        return showVentaPagada;
    }

    public void setShowVentaPagada(String showVentaPagada) {
        this.showVentaPagada = showVentaPagada;
    }

    public String getShowDetVentaPagada() {
        return showDetVentaPagada;
    }

    public void setShowDetVentaPagada(String showDetVentaPagada) {
        this.showDetVentaPagada = showDetVentaPagada;
    }

    public String getListProductos() {
        return listProductos;
    }

    public void setListProductos(String listProductos) {
        this.listProductos = listProductos;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getUrlLocal() {
        return urlLocal;
    }

    public void setUrlLocal(String urlLocal) {
        this.urlLocal = urlLocal;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public String getGetCliente() {
        return getCliente;
    }

    public void setGetCliente(String getCliente) {
        this.getCliente = getCliente;
    }

    public String getActualizar() {
        return actualizar;
    }

    public void setActualizar(String actualizar) {
        this.actualizar = actualizar;
    }

    public String getCambiarPassword() {
        return cambiarPassword;
    }

    public void setCambiarPassword(String cambiarPassword) {
        this.cambiarPassword = cambiarPassword;
    }

    public String getListarBarberos() {
        return listarBarberos;
    }

    public void setListarBarberos(String listarBarberos) {
        this.listarBarberos = listarBarberos;
    }

    public String getGetBarbero() {
        return getBarbero;
    }

    public void setGetBarbero(String getBarbero) {
        this.getBarbero = getBarbero;
    }

    public String getListarServicios() {
        return listarServicios;
    }

    public void setListarServicios(String listarServicios) {
        this.listarServicios = listarServicios;
    }

    public String getGetServicio() {
        return getServicio;
    }

    public void setGetServicio(String getServicio) {
        this.getServicio = getServicio;
    }

    public String getConsultaDisponibilidad() {
        return consultaDisponibilidad;
    }

    public void setConsultaDisponibilidad(String consultaDisponibilidad) {
        this.consultaDisponibilidad = consultaDisponibilidad;
    }

    public String getRegistraReserva() {
        return registraReserva;
    }

    public void setRegistraReserva(String registraReserva) {
        this.registraReserva = registraReserva;
    }

    public String getListarReservas() {
        return listarReservas;
    }

    public void setListarReservas(String listarReservas) {
        this.listarReservas = listarReservas;
    }

    public String getGetReserva() {
        return getReserva;
    }

    public void setGetReserva(String getReserva) {
        this.getReserva = getReserva;
    }

    public String getPayReserva() {
        return payReserva;
    }

    public void setPayReserva(String payReserva) {
        this.payReserva = payReserva;
    }

    public String getDelReserva() {
        return delReserva;
    }

    public void setDelReserva(String delReserva) {
        this.delReserva = delReserva;
    }
}
