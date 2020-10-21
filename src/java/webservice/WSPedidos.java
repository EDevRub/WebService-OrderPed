package webservice;

import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.Oneway;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import model.*;

/**
 *
 * @author Edunaldo
 */
@WebService(serviceName = "WSPedidos")
public class WSPedidos {

    private List<Pedido> listaPedidosPorFecha;
    private List<Pedido> listaPedidosTotales;
    private List<Pedido> listaPedidosDeHoy;
    private List<Persona> listaTodasPersonas;
    private List<Historial_Pedidos> listaDePedidos;
    private List<Historial_Pedidos> listaDePedidosFiltrada;
    private List<PersonaSelect> listaDePersonal;
    private Data pd = new Data();
    private Persona per;

    @WebMethod(operationName = "todosLosPedidos")
    public List<Pedido> todosLosPedidos() {
        try {
            listaPedidosTotales = pd.getPedidosTotales();
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaPedidosTotales;
    }

    @WebMethod(operationName = "pedidosPorFecha")
    public List<Pedido> pedidosPorFecha(@WebParam(name = "fecha_Filtro") String fecha_Filtro) {
        try {
            listaPedidosPorFecha = pd.getPedidosPorFecha(fecha_Filtro);
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaPedidosPorFecha;
    }

    @WebMethod(operationName = "agregarPedido")
    public String agregarPedido(@WebParam(name = "rut_Persona") String rut_Persona, @WebParam(name = "nombre_Persona") String nombre_Persona) {
        String mensaje = "Error";
        Pedido pe = new Pedido();
        pe.setRut_persona(rut_Persona);
        pe.setNom_persona(nombre_Persona);
        try {
            pd.registroPedido(pe);
            mensaje = "Pedido Agregado";
            return mensaje;
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mensaje;
    }

    @WebMethod(operationName = "numTotalDePersonas")
    public int numTotalDePersonas() {
        int retorno = 0;
        try {
            retorno = pd.getNumTotalDePersonas();
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    @WebMethod(operationName = "pedidosDeHoy")
    public List<Pedido> pedidosDeHoy() {
        try {
            listaPedidosDeHoy = pd.getPedidosDeHoy();
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaPedidosDeHoy;
    }

    @WebMethod(operationName = "cambiarEstadoPedidoAListo")
    public String cambiarEstadoPedidoAListo(@WebParam(name = "idPedido") int idPedido) {
        String mensaje = "Error al cambiar estado a listo";
        try {
            pd.actualizarEstadoPedidoAListo(idPedido);
            mensaje = "Estado cambiado a listo";
            return mensaje;
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mensaje;
    }

    @WebMethod(operationName = "actualizarPedidoAEntregado")
    public String actualizarPedidoAEntregado(@WebParam(name = "id") int id) {
        String mensaje = "Error al cambiar estado a Entregado";
        try {
            pd.actualizarEstadoPedidoAEntregado(id);
            mensaje = "Estado cambiado a Entregado";
            return mensaje;
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mensaje;
    }

    @WebMethod(operationName = "numCantPedidosHoy")
    public int numCantPedidosHoy() {
        int n = 0;
        try {
            n = pd.getNumCantidadPedidosHoy();
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    @WebMethod(operationName = "ingresar")
    public boolean ingresar(@WebParam(name = "rut") String rut) {
        per = new Persona();
        per.setRut(rut);
        boolean ex = false;
        try {
            ex = pd.ingresar(per);
        } catch (SQLException ex1) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex1);
        }
        return ex;
    }

    @WebMethod(operationName = "todasLasPersonas")
    public List<Persona> todasLasPersonas() {

        try {
            listaTodasPersonas = pd.getTotalPersonas();
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaTodasPersonas;
    }

    @WebMethod(operationName = "id_CargoPersona")
    public int id_CargoPersona(@WebParam(name = "rut") String rut) {
        per = new Persona();
        per.setRut(rut);
        int id = 0;
        try {
            id = pd.getCargoPersona(per);
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    @WebMethod(operationName = "horaRegPed")
    public int horaRegPed(@WebParam(name = "rut") String rut) {
        int hPed = 00;
        try {
            hPed = pd.horaRegistroPedido(rut);
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hPed;
    }

    @WebMethod(operationName = "minutoRegPed")
    public int minutoRegPed(@WebParam(name = "rut") String rut) {
        int mPed = 00;

        try {
            mPed = pd.minutoRegistroPedido(rut);
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mPed;
    }

    @WebMethod(operationName = "segundoRegPed")
    public int segundoRegPed(@WebParam(name = "rut") String rut) {
        int sPed = 00;

        try {
            sPed = pd.segundoRegistroPedido(rut);
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sPed;
    }

    @WebMethod(operationName = "horaRegistroPedido")
    public String horaRegistroPedido(@WebParam(name = "rut") String rut) {
        return "";
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "cambiarEstadoPedido")
    public String cambiarEstadoPedido(@WebParam(name = "id") int id) {
        return "";
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getHoraRegistro")
    public String getHoraRegistro(@WebParam(name = "idPed") int idPed) {
        String horareg = "";
        try {
            horareg = pd.getHoraRegistro(idPed);
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return horareg;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "registrarHistorialPedidos")
    public String registrarHistorialPedidos(@WebParam(name = "numPed") int numPed, @WebParam(name = "rutPersona") String rutPersona, @WebParam(name = "nomPersona") String nomPersona, @WebParam(name = "horaDeEspera") int horaDeEspera, @WebParam(name = "minDeEspera") int minDeEspera, @WebParam(name = "segDeEspera") int segDeEspera) {
        String mensaje = "Error Registro Historial Pedido";
        Historial_Pedidos hp = new Historial_Pedidos();
        hp.setNum_ped(numPed);
        hp.setRut_persona(rutPersona);
        hp.setNom_persona(nomPersona);
        hp.setHora_de_espera(horaDeEspera);
        hp.setMin_de_espera(minDeEspera);
        hp.setSeg_de_espera(segDeEspera);

        try {
            pd.registrar_Historial_Pedidos(hp);
            mensaje = "Registro Historial Correcto";
            return mensaje;
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mensaje;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "registrarPersonal")
    public String registrarPersonal(@WebParam(name = "rut") String rut, @WebParam(name = "nombre") String nombre, @WebParam(name = "idCargo") int idCargo) {
        String mensaje = "Error en registro de personal";
        Persona p = new Persona();
        p.setRut(rut);
        p.setNombre(nombre);
        p.setId_Cargo(idCargo);

        try {
            pd.registro_de_personal(p);
            mensaje = "Personal Registrado";
            return mensaje;
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mensaje;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getHistorialPedidos")
    public List<Historial_Pedidos> getHistorialPedidos() {
        try {
            listaDePedidos = pd.getHistorialTotalDePedidos();
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaDePedidos;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getSegDeEspera")
    public int getSegDeEspera(@WebParam(name = "numPed") int numPed) {
        int segDeEspera = 0;
        try {
            segDeEspera = pd.getSegDeEspera(numPed);
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return segDeEspera;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getPromedioDeSegundos")
    public int getPromedioDeSegundos() {
        int promedioSeg = 0;
        try {
            promedioSeg = pd.getPromedioDeSegundos();
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return promedioSeg;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getPromedioDeMinutos")
    public int getPromedioDeMinutos() {
        int promedioMin = 0;
        try {
            promedioMin = pd.getPromedioDeMinutos();
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return promedioMin;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getPromedioDeHoras")
    public int getPromedioDeHoras() {
        int promedioHoras = 0;
        try {
            promedioHoras = pd.getPromedioDeHoras();
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return promedioHoras;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getNumTotalPedidos")
    public int getNumTotalPedidos() {
        int numTotalPed = 0;
        try {
            numTotalPed = pd.getTotalDePedidos();
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return numTotalPed;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getHistorialPedidosFiltrados")
    public List<Historial_Pedidos> getHistorialPedidosFiltrados(@WebParam(name = "fecha1") String fecha1, @WebParam(name = "fecha2") String fecha2) {
        try {
            listaDePedidosFiltrada = pd.getListaHistorialFiltrada(fecha1, fecha2);
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaDePedidosFiltrada;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getPromHoraFiltrada")
    public int getPromHoraFiltrada(@WebParam(name = "fecha1") String fecha1, @WebParam(name = "fecha2") String fecha2) {
        int promHoraFiltrada = 0;
        try {
            promHoraFiltrada = pd.getPromedioDeHoraFiltrados(fecha1, fecha2);
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return promHoraFiltrada;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getPromMinFiltrado")
    public int getPromMinFiltrado(@WebParam(name = "fecha1") String fecha1, @WebParam(name = "fecha2") String fecha2) {
        int promMinFiltrado = 0;
        try {
            promMinFiltrado = pd.getPromedioDeMinutosFiltrados(fecha1, fecha2);
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return promMinFiltrado;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getPromSegFiltrado")
    public int getPromSegFiltrado(@WebParam(name = "fecha1") String fecha1, @WebParam(name = "fecha2") String fecha2) {
        int promSegFiltrado = 0;
        try {
            promSegFiltrado = pd.getPromedioDeSegundosFiltrados(fecha1, fecha2);
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return promSegFiltrado;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getTotalDePedidosFiltrado")
    public int getTotalDePedidosFiltrado(@WebParam(name = "fecha1") String fecha1, @WebParam(name = "fecha2") String fecha2) {
        int totalPedFiltrados = 0;
        try {
            totalPedFiltrados = pd.getTotalDePedidosFiltrados(fecha1, fecha2);
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return totalPedFiltrados;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "cantPedBuenosSEG")
    public int cantPedBuenosSEG(@WebParam(name = "tiempo") int tiempo) {
        int cantPedBuenosSEG = 0;
        try {
            cantPedBuenosSEG = pd.cantDePedBuenosSEG(tiempo);
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cantPedBuenosSEG;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "cantPedMalosSeg")
    public int cantPedMalosSeg(@WebParam(name = "tiempo") int tiempo) {
        int cantPedMalosSEG = 0;
        try {
            cantPedMalosSEG = pd.cantDePedMalosSEG(tiempo);
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cantPedMalosSEG;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "cantPedBuenosMIN")
    public int cantPedBuenosMIN(@WebParam(name = "tiempo") int tiempo) {
        int cantPedBuenosMIN = 0;
        try {
            cantPedBuenosMIN = pd.cantDePedBuenosMIN(tiempo);
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cantPedBuenosMIN;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "cantPedMalosMIN")
    public int cantPedMalosMIN(@WebParam(name = "tiempo") int tiempo) {
        int cantPedMalosMIN = 0;
        try {
            cantPedMalosMIN = pd.cantDePedMalosMIN(tiempo);
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cantPedMalosMIN;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "cantPedBuenosSEGFiltrados")
    public int cantPedBuenosSEGFiltrados(@WebParam(name = "fecha1") String fecha1, @WebParam(name = "fecha2") String fecha2, @WebParam(name = "tiempo") int tiempo) {
        int cantPedBuenosSegFilt = 0;
        try {
            cantPedBuenosSegFilt = pd.cantDePedBuenosSEGFiltrado(tiempo, fecha1, fecha2);
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cantPedBuenosSegFilt;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "cantPedMalosSEGFiltrados")
    public int cantPedMalosSEGFiltrados(@WebParam(name = "fecha1") String fecha1, @WebParam(name = "fecha2") String fecha2, @WebParam(name = "tiempo") int tiempo) {
        int cantPedMalosSegFIL = 0;
        try {
            cantPedMalosSegFIL = pd.cantDePedMalosSEGFiltrado(tiempo, fecha1, fecha2);
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cantPedMalosSegFIL;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "cantPedBuenosMINFiltrados")
    public int cantPedBuenosMINFiltrados(@WebParam(name = "fecha1") String fecha1, @WebParam(name = "fecha2") String fecha2) {
        int cantPedBuenosMINFILT = 0;
        try {
            cantPedBuenosMINFILT = pd.cantDePedBuenosMINFiltrado(fecha1, fecha2);
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cantPedBuenosMINFILT;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "cantPedMalosMINFiltrados")
    public int cantPedMalosMINFiltrados(@WebParam(name = "fecha1") String fecha1, @WebParam(name = "fecha2") String fecha2) {
        int cantPedMalosMINFIL = 0;
        try {
            cantPedMalosMINFIL = pd.cantDePedMalosMINFiltrado(fecha1, fecha2);
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cantPedMalosMINFIL;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getMinDeEspera")
    public int getMinDeEspera(@WebParam(name = "numPed") int numPed) {
        int minDeEspera = 0;
        try {
            minDeEspera = pd.getMinDeEspera(numPed);
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return minDeEspera;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getHoraDeEspera")
    public int getHoraDeEspera(@WebParam(name = "numPed") int numPed) {
        int horasDeEspera = 0;
        try {
            horasDeEspera = pd.getHoraDeEspera(numPed);
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return horasDeEspera;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getEstadoPedidoCliente")
    public String getEstadoPedidoCliente(@WebParam(name = "rutCliente") String rutCliente) {
        String rutCL = "";
        try {
            rutCL = pd.getEstadoPedidoCliente(rutCliente);
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rutCL;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "ingresoDeCliente")
    public boolean ingresoDeCliente(@WebParam(name = "rutCliente") String rutCliente) {
        boolean ex = false;
        try {
            ex = pd.ingresoDeCliente(rutCliente);
        } catch (SQLException ex1) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex1);
        }
        return ex;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "nombreCliente")
    public String nombreCliente(@WebParam(name = "rutCliente") String rutCliente) {
        String nombre = "";
        try {
            nombre = pd.getNombreCliente(rutCliente);
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nombre;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "numPedido")
    public int numPedido(@WebParam(name = "rutCliente") String rutCliente) {
        int numPedido = 0;
        try {
            numPedido = pd.numPedido(rutCliente);
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return numPedido;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "rutClienteID")
    public String rutClienteID(@WebParam(name = "numPed") int numPed) {
        String rutClienteid = "";
        try {
            rutClienteid = pd.rutClienteID(numPed);
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rutClienteid;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "nomClienteID")
    public String nomClienteID(@WebParam(name = "numPed") int numPed) {
        String nomCliente = "";
        try {
            nomCliente = pd.nombreClienteID(numPed);
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nomCliente;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "tiempo_estimado_espera")
    public String tiempo_estimado_espera(@WebParam(name = "minutos") int minutos) {
        String mensaje = "Error de registro de tiempo";
        try {
            pd.tiempo_estimado_pedidos(minutos);
            mensaje = "Exito de registro de tiempo";
            return mensaje;
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mensaje;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getTiempoEstimado")
    public int getTiempoEstimado(@WebParam(name = "numPedido") int numPedido) {
        int tiempoEstimado = 0;
        try {
            tiempoEstimado = pd.getTiempoEstimado(numPedido);
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tiempoEstimado;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "tiempoEstimadoActual")
    public int tiempoEstimadoActual() {
        int tEstimadoActual = 0;
        try {
            tEstimadoActual = pd.tiempoEstimadoActual();
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tEstimadoActual;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getPersonalDeTrabajo")
    public List<PersonaSelect> getPersonalDeTrabajo() {
        try {
            listaDePersonal = pd.getPersonalDeTrabajo();
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaDePersonal;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "cambiarPersonal")
    public String cambiarPersonal(@WebParam(name = "rutPersonal") String rutPersonal) {
        String mensaje = "Error cambio personal de trabajo";
        try {
            pd.cambiarPersonal(rutPersonal);
            mensaje = "personal de trabajo cambiado";
            return mensaje;
        } catch (SQLException ex) {
            Logger.getLogger(WSPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mensaje;
    }

}
