/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import views.FrmSelectEmpresa;
import views.FrmClientes;

/**
 *
 * @author Jhoantan Grueso Perea
 * @since 02/07/2022
 * @version 1.0
 */
public class Empresa {

    //Atributos
    private int id;
    private String nombre;
    private String NIT;
    private String telefono;
    private String direccion;
    private String email;
    private String actEconomica;
    private ArrayList<Empleado> empleados;
    private ArrayList<FrmClientes> clientes;

    //Contructor
    public Empresa(int id, String NIT, String nombre, String telefono, String direccion, 
            String email, String actEconomica) {
        this.id = id;
        this.NIT = NIT;
        this.nombre = nombre;        
        this.telefono = telefono;
        this.direccion = direccion;
        this.email = email;
        this.actEconomica = actEconomica;
        empleados = new ArrayList<>();
        clientes = new ArrayList<>();
    }   
    
    //Getter y Setter o modificadores *****************************
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id 
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * 
     * @return  nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * 
     * @param nombre 
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * 
     * @return NIT
     */
    public String getNIT() {
        return NIT;
    }

    /**
     * 
     * @param NIT 
     */
    public void setNIT(String NIT) {
        this.NIT = NIT;
    }

    /**
     * 
     * @return telefono
     */
    public String getTelfono() {
        return telefono;
    }

    /**
     * 
     * @param telfono 
     */
    public void setTelfono(String telfono) {
        this.telefono = telfono;
    }

    /**
     * 
     * @return direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * 
     * @param direccion 
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * 
     * @return mail
     */
    public String getEmail() {
        return email;
    }

    /**
     * 
     * @param email 
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 
     * @return Actividad Economica
     */
    public String getActEconomica() {
        return actEconomica;
    }

    /**
     * 
     * @param actEconomica 
     */
    public void setActEconomica(String actEconomica) {
        this.actEconomica = actEconomica;
    }

    /**
     * 
     * @return empleados
     */
    public ArrayList<Empleado> getEmpleados() {
        return empleados;
    }

    /**
     * 
     * @param empleados     
     */
    public void setEmpleado(ArrayList<Empleado> empleados) {
        this.empleados = empleados;
    }

    /**
     * 
     * @return clientes
     */
    public ArrayList<FrmClientes> getClientes() {
        return clientes;
    }

    /**
     * 
     * @param clientes     
     */
    public void setCliente(ArrayList<FrmClientes> clientes) {
        this.clientes = clientes;
    }
    
    
    //Metodos ***********************************
    
    /**
     * 
     * @return listarEmpresas
     * Metodo para listar cliestes desde la BD.
     */
    public static ArrayList<Empresa> obtenerEmpresas() {            
        
        ArrayList<Empresa> listarEmpresas = new ArrayList<>();
        
        try {
            PreparedStatement query = ConexionBD.obtener().prepareStatement(""
                    + "SELECT * from empresa ");
            
            ResultSet res = query.executeQuery();            
            
            while (res.next()){                
                
                listarEmpresas.add( new  Empresa(res.getInt("id_empresa"), 
                        res.getString("nit"), res.getString("nombre"), 
                        res.getString("telefono"), res.getString("direccion"), 
                        res.getString("email"), res.getString("actEconomica")));             
            }                                    
            return listarEmpresas;
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
        return listarEmpresas;
    }   
    
    /***
     * 
     * @return buscarNit
     * Metodo que retorna una lista con datos o vacia, dependiendo del NIT ingresado 
     */
    public static ArrayList<Empresa> buscarEmpresa(){
        
        ArrayList<Empresa> buscarNit = new ArrayList<>();
        String buscar = FrmSelectEmpresa.txtBuscar.getText();
                    
        try{
            PreparedStatement query = ConexionBD.obtener().prepareStatement(""
                    + "SELECT * FROM empresa WHERE nit LIKE '%" + buscar + "%'");            
            
            ResultSet res = query.executeQuery();
                        
            while(res.next()){
                buscarNit.add(new Empresa(res.getInt("id_empresa"), 
                        res.getString("nit"), res.getString("nombre"), 
                        res.getString("telefono"), res.getString("direccion"), 
                        res.getString("email"), res.getString("actEconomica")));
            }
            
            return buscarNit;
        }catch (SQLException e){  
            System.out.println(e.getMessage());
        }
        return buscarNit;
    }
    
    /**
     * 
     * @return listarClientes
     * Metodo para listar cliestes desde la BD, dependiendo de la empresa seleccionada
     */
    public ArrayList<Cliente> obtenerClientes() {
        
        ArrayList<Cliente> listarClientes = new ArrayList<>();
        
        try {
            PreparedStatement query = ConexionBD.obtener().prepareStatement(""
                    + "SELECT DISTINCT "
                    + "p.id_persona, p.tid, p.documento, p.nombres, p.apellidos, "
                    + "p.fechaNac, p.genero, TIMESTAMPDIFF(YEAR, p.fechaNac, CURRENT_DATE()) AS edad, "
                    + "c.id_cliente, c.telefono, c.direccion, c.email, c.id_empresa_reg "
                    + " FROM persona p "
                    + " INNER JOIN cliente c ON p.id_persona = c.id_persona "
                    + " INNER JOIN empresa_cliente ec ON c.id_empresa_reg = ec.id_empresa "
                    + " LEFT JOIN empresa e ON ec.id_empresa = e.id_empresa "                    
                    + " WHERE e.id_empresa = ? ");
            
            query.setInt(1, getId());                        
            ResultSet res = query.executeQuery();            
            
            while (res.next()){                
                
                listarClientes.add(new Cliente(res.getInt("id_persona"), res.getString("tid"), 
                        res.getString("documento"), res.getString("nombres"), res.getString("apellidos"), 
                        res.getDate("fechaNac"), res.getString("genero"), res.getInt("edad"), res.getInt("id_cliente"),
                        res.getString("telefono"), res.getString("direccion"), res.getString("email"), res.getInt("id_empresa_reg")));                
            }                        
            
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
        return listarClientes;
    }
    
    
    /**
     * 
     * @return buscarDocumento
     * Metodo que retorna una lista con datos o vacia, dependiendo del número de
     * documento ingresado desde el formulario clientes.
     */
    public ArrayList<Cliente> buscarCliente(Empresa empresa){
        
        ArrayList<Cliente> buscarDocumento = new ArrayList<>();
        String buscar = FrmClientes.txtBuscar.getText();
                    
        try{
            PreparedStatement query = ConexionBD.obtener().prepareStatement(""
                    + "SELECT "
                    + "p.id_persona, p.tid, p.documento, p.nombres, p.apellidos, "
                    + "p.fechaNac, p.genero, TIMESTAMPDIFF(YEAR, p.fechaNac, CURRENT_DATE()) AS edad, "
                    + "c.id_cliente, c.telefono, c.direccion, c.email, c.id_empresa_reg "
                    + " FROM persona p "
                    + " LEFT OUTER JOIN cliente c ON p.id_persona = c.id_persona "
                    + " JOIN empresa_cliente ec ON c.id_empresa_reg = ec.id_empresa "
                    + " WHERE p.documento LIKE '%" + buscar + "%' ");                        

            //query.setInt(1, empresa.getId());        
            ResultSet res = query.executeQuery();
                        
            if(res.next()){
                buscarDocumento.add(new Cliente(res.getInt("id_persona"), res.getString("tid"), 
                        res.getString("documento"), res.getString("nombres"), res.getString("apellidos"), 
                        res.getDate("fechaNac"), res.getString("genero"), res.getInt("edad"), res.getInt("id_cliente"), 
                        res.getString("telefono"), res.getString("direccion"), res.getString("email"), res.getInt("id_empresa_reg")));
            }
            
            return buscarDocumento;
        }catch (SQLException e){  
            System.out.println(e.getMessage());
        }
        return buscarDocumento;
    }
           
    /**
     * 
     * @param cliente
     * @return true or false
     * Metodo que qermite agregar un cliente desde el formulario cliente
     */
    public boolean agregarCliente(Cliente cliente, Empresa empresa) {
        try {            
            String query = ("INSERT INTO persona(tid, documento, nombres, apellidos, "
                    + "fechaNac, genero) VALUES (?, ?, ?, ?, ?, ?)");
            PreparedStatement stPersona = ConexionBD.obtener().prepareStatement
                                        (query, Statement.RETURN_GENERATED_KEYS);            
            stPersona.setString(1, cliente.getTID());
            stPersona.setString(2, cliente.getDocumento());
            stPersona.setString(3, cliente.getNombre());
            stPersona.setString(4, cliente.getApellidos());            
            stPersona.setDate(5, (java.sql.Date) cliente.getFechaNacimiento());
            stPersona.setString(6, cliente.getGenero());
            int filaInsertada = stPersona.executeUpdate();
            if (filaInsertada > 0) {
                ResultSet llavePrimariaPersona = stPersona.getGeneratedKeys();
                if (llavePrimariaPersona.next()) {
                    int idPersona = llavePrimariaPersona.getInt(1);
                    query = ("INSERT INTO cliente(telefono, direccion, email, id_empresa_reg, id_persona) VALUES ( ?, ?, ?, ?, ?)");
                    PreparedStatement stCliente = ConexionBD.obtener().prepareStatement
                                                (query, Statement.RETURN_GENERATED_KEYS);
                    stCliente.setString(1, cliente.getTelefono());
                    stCliente.setString(2, cliente.getDireccion());
                    stCliente.setString(3, cliente.getEmail());                    
                    stCliente.setInt(4, empresa.getId());
                    stCliente.setInt(5, idPersona);
                    filaInsertada = stCliente.executeUpdate();
                    if (filaInsertada > 0) {
                        ResultSet llavePrimariaCliente = stCliente.getGeneratedKeys();                        
                        if (llavePrimariaCliente.next()) {
                            int idCliente = llavePrimariaCliente.getInt(1);                            
                            query = "INSERT INTO empresa_cliente(id_empresa, id_cliente) VALUES (?, ?)";
                            PreparedStatement stEmpresa_Cliente = ConexionBD.obtener().prepareStatement(query);
                            stEmpresa_Cliente.setInt(1, getId());
                            stEmpresa_Cliente.setInt(2, idCliente);
                            filaInsertada = stEmpresa_Cliente.executeUpdate();
                            if (filaInsertada > 0) {
                                return true;
                            }
                        }
                    }
                }
            }
            return false;            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
        
    /**
     * 
     * @param indiceCliente     
     * @return true or false
     * Metodo que permite eliminar un clientes a la lista
     */
    public boolean eliminarCliente(int idCliente, Cliente cliente, Empresa empresa) {
        try {
            String query = "DELETE FROM empresa_cliente WHERE id_empresa = ? AND id_cliente = ?";
            PreparedStatement stEmpresa_Cliente = ConexionBD.obtener().prepareStatement(query);
            stEmpresa_Cliente.setInt(1, getId());
            stEmpresa_Cliente.setInt(2, idCliente);           
            
            String queryCliente = ("DELETE FROM cliente WHERE id_cliente = ? AND id_empresa_reg = ?");
            PreparedStatement stCliente = ConexionBD.obtener().prepareStatement(queryCliente);                                          
            stCliente.setInt(1, cliente.getIdCliente());                                    
            stCliente.setInt(2, empresa.getId());            
                        
            int filaEliminadaEmpresa_Cliente = stEmpresa_Cliente.executeUpdate();
            int filaEliminadaCliente = stCliente.executeUpdate();
            return filaEliminadaEmpresa_Cliente > 0 || filaEliminadaCliente > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    /**
     * 
     * @param indiceCliente
     * @param cliente
     * @return true or false
     * Metodo que permite modificar un cliente dedes la tabla, una vez sea seleccionado.
     */
    public boolean modificarCliente(Cliente cliente, Empresa empresa) {
        try {
            String comparador_empresaCliente = "";            
            //Verifica si existe un registro con la empresa seleccionada
            String queryEmpresa_Cliente = "SELECT * FROM empresa_cliente WHERE id_empresa = ? AND id_cliente = ?;";
            PreparedStatement stEmpresa_Cliente = ConexionBD.obtener().prepareStatement(queryEmpresa_Cliente);
            stEmpresa_Cliente.setInt(1, empresa.getId());
            stEmpresa_Cliente.setInt(2, cliente.getIdCliente());
            ResultSet res = stEmpresa_Cliente.executeQuery();
            
            if (res.next()) {
                comparador_empresaCliente = res.getString("id_empresa_cliente");                
            }
            
            String comparador = "";
            //Verifica si existe un registro con la empresa seleccionada
            String queryClientes = "SELECT * FROM cliente c WHERE c.id_empresa_reg = ? AND c.id_cliente = ?;";
            PreparedStatement stClientes = ConexionBD.obtener().prepareStatement(queryClientes);
            stClientes.setInt(1, empresa.getId());
            stClientes.setInt(2, cliente.getIdCliente());
            ResultSet ress = stClientes.executeQuery();            
            
            if (ress.next()) {
                comparador = ress.getString("id_cliente");                
            }
                        
            //Crea una nueva inserción si no existe con la empresa seleccionada
            if (comparador.isEmpty() || comparador_empresaCliente.isEmpty()) {            
                String query = ("INSERT INTO cliente(telefono, direccion, email, id_empresa_reg, id_persona) VALUES ( ?, ?, ?, ?, ?)");
                PreparedStatement stCliente = ConexionBD.obtener().prepareStatement
                                                (query, Statement.RETURN_GENERATED_KEYS);
                stCliente.setString(1, cliente.getTelefono());
                stCliente.setString(2, cliente.getDireccion());
                stCliente.setString(3, cliente.getEmail());                    
                stCliente.setInt(4, empresa.getId());
                stCliente.setInt(5, cliente.getId());
                int filaInsertada = stCliente.executeUpdate();
                if (filaInsertada > 0) {
                    ResultSet llavePrimariaCliente = stCliente.getGeneratedKeys();                        
                    if (llavePrimariaCliente.next()) {
                        int idCliente = llavePrimariaCliente.getInt(1);                            
                        query = "INSERT INTO empresa_cliente(id_empresa, id_cliente) VALUES (?, ?)";
                        PreparedStatement stEmpresa_Cliente1 = ConexionBD.obtener().prepareStatement(query);
                        stEmpresa_Cliente1.setInt(1, getId());
                        stEmpresa_Cliente1.setInt(2, idCliente);
                        filaInsertada = stEmpresa_Cliente1.executeUpdate();
                        if (filaInsertada > 0) {
                            return true;
                        }
                    }
                }
            } else {
                String queryCliente = ("UPDATE cliente SET telefono = ?, direccion = ?, email = ?  WHERE id_cliente = ?");
                PreparedStatement stCliente = ConexionBD.obtener().prepareStatement(queryCliente);
                stCliente.setString(1, cliente.getTelefono());
                stCliente.setString(2, cliente.getDireccion());
                stCliente.setString(3, cliente.getEmail());                                        
                stCliente.setInt(4, cliente.getIdCliente());
                stCliente.executeUpdate();                
            }
            
            String queryPersona = ("UPDATE persona SET tid = ?, documento = ?, nombres = ?, "
                    + "apellidos = ?, fechaNac = ?, genero = ? WHERE id_persona = ?");
            PreparedStatement stPersona = ConexionBD.obtener().prepareStatement(queryPersona);
            stPersona.setString(1, cliente.getTID());
            stPersona.setString(2, cliente.getDocumento());
            stPersona.setString(3, cliente.getNombre());
            stPersona.setString(4, cliente.getApellidos());            
            stPersona.setDate(5, (java.sql.Date) cliente.getFechaNacimiento());
            stPersona.setString(6, cliente.getGenero());
            stPersona.setInt(7, cliente.getId());
                        
            int filaInsertadaPersona = stPersona.executeUpdate();
            
            return filaInsertadaPersona > 0;            
        
         } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    public boolean duplicados(Cliente cliente) {        
        try {
            String bandera = "";
            String query2 = ("SELECT * FROM persona WHERE documento = ?"); 
            PreparedStatement stPersona2 = ConexionBD.obtener().prepareStatement(query2);
            stPersona2.setString(1, cliente.getDocumento());
            
            ResultSet res = stPersona2.executeQuery();
            
            while(res.next()){
                bandera = res.getString("documento");
            }
            return !bandera.isEmpty();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    public ArrayList<Empleado> obtenerEmpleado(Empresa empresa) {
        ArrayList<Empleado> listarEmpleados = new ArrayList<>();
        
        try {
            PreparedStatement query = ConexionBD.obtener().prepareStatement(""
                    + "SELECT "
                    + "p.id_persona, p.tid, p.documento, p.nombres, p.apellidos, "
                    + "p.fechaNac, p.genero, TIMESTAMPDIFF(YEAR, p.fechaNac, CURRENT_DATE()) AS edad, "                    
                    + "em.id_empleado, em.salario, em.subordinado, em.id_empresa_reg, em.id_directivo "
                    + " FROM persona p "                    
                    + " INNER JOIN empleado em ON p.id_persona = em.id_persona "
                    + " INNER JOIN empresa_empleado ec ON em.id_empresa_reg = ec.id_empresa "
                    + " LEFT JOIN empresa e ON ec.id_empresa = e.id_empresa "                    
                    + " WHERE e.id_empresa = ? ");
            
            query.setInt(1, empresa.getId());                                    
            ResultSet res = query.executeQuery();                        
            while (res.next()){                
                
                listarEmpleados.add(new Empleado(res.getInt("p.id_persona"), res.getString("p.tid"), 
                        res.getString("p.documento"), res.getString("p.nombres"), res.getString("p.apellidos"), 
                        res.getDate("p.fechaNac"), res.getString("p.genero"), res.getInt("edad"),
                        res.getInt("em.id_empresa_reg"),res.getInt("em.id_empleado"), res.getDouble("em.salario"), 
                        res.getString("em.subordinado"), res.getInt("em.id_directivo")));                
            }                        
           
             
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        for (Empleado empleado : listarEmpleados) {
                System.out.println(empleado);
        }
        return listarEmpleados;
    }
    
    /**
     * 
     * @param empleado
     * @return true or false
     * Metodo que permite agregar un empleado de la lista
     */
    public boolean agregarEmpleado(Empleado empleado) {
        try {
            this.empleados.add(empleado);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    /**
     * 
     * @param indiceEmpleado     
     * @return true or false
     * Metodo que permite eliminar un empleado a la lista
     */
    public boolean eliminarEmpleado(int indiceEmpleado) {
        try {
            this.empleados.remove(indiceEmpleado);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    /**
     * 
     * @param indiceEmpleado
     * @param empleado     
     * @return true or false
     * Metodo que permite modificar un empleado de la lista
     */
    public boolean modificarEmpleado(int indiceEmpleado, Empleado empleado) {
        try {
            this.empleados.set(indiceEmpleado, empleado);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }    

    

    
}
