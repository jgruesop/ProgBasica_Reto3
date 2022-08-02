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
import views.FrmEmpleados;

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
                    + " LEFT JOIN cliente c ON p.id_persona = c.id_persona "
                    + " LEFT JOIN empresa_cliente ec ON c.id_empresa_reg = ec.id_empresa "
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
                    + "SELECT DISTINCT "
                    + "p.id_persona, p.tid, p.documento, p.nombres, p.apellidos, "
                    + "p.fechaNac, p.genero, TIMESTAMPDIFF(YEAR, p.fechaNac, CURRENT_DATE()) AS edad, "                    
                    + "em.id_empleado, em.salario, em.subordinado, em.id_empresa_reg, em.id_directivo, "
                    + "d.id_directivo, d.categoria "
                    + " FROM persona p "                    
                    + " INNER JOIN empleado em ON p.id_persona = em.id_persona "
                    + " INNER JOIN empresa_empleado ec ON em.id_empresa_reg = ec.id_empresa "
                    + " LEFT JOIN empresa e ON ec.id_empresa = e.id_empresa "
                    + " LEFT JOIN directivo d ON em.id_empleado = d.id_empleado "                    
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
        
        return listarEmpleados;
    }
    
    /**
     * 
     * @param empleado
     * @return true or false
     * Metodo que permite agregar un empleado de la lista
     */
    public boolean agregarEmpleado(Empresa empresa, Empleado empleado) {
        try {            
            String query = ("INSERT INTO persona(tid, documento, nombres, apellidos, "
                    + "fechaNac, genero) VALUES (?, ?, ?, ?, ?, ?)");
            PreparedStatement stPersona = ConexionBD.obtener().prepareStatement
                                        (query, Statement.RETURN_GENERATED_KEYS);            
            stPersona.setString(1, empleado.getTID());
            stPersona.setString(2, empleado.getDocumento());
            stPersona.setString(3, empleado.getNombre());
            stPersona.setString(4, empleado.getApellidos());            
            stPersona.setDate(5, (java.sql.Date) empleado.getFechaNacimiento());
            stPersona.setString(6, empleado.getGenero());
            int filaInsertada = stPersona.executeUpdate();
            if (filaInsertada > 0) {
                ResultSet llavePrimariaPersona = stPersona.getGeneratedKeys();
                if (llavePrimariaPersona.next()) {
                    int idPersona = llavePrimariaPersona.getInt(1);
                    query = ("INSERT INTO empleado(salario, subordinado, id_persona, id_empresa_reg, id_directivo) VALUES ( ?, ?, ?, ?, ?)");
                    PreparedStatement stEmpleado = ConexionBD.obtener().prepareStatement
                                                (query, Statement.RETURN_GENERATED_KEYS);
                    stEmpleado.setDouble(1, empleado.getSalario());
                    stEmpleado.setString(2, empleado.getSubordinado());
                    stEmpleado.setInt(3, idPersona);                    
                    stEmpleado.setInt(4, empresa.getId());
                    stEmpleado.setInt(5, empleado.idDirectivo);
                    filaInsertada = stEmpleado.executeUpdate();
                    String dir = String.valueOf(empleado.getIdDirectivo());
                    if (filaInsertada > 0 ) {
                        ResultSet llavePrimariaCliente = stEmpleado.getGeneratedKeys();                                                
                        if (llavePrimariaCliente.next()) {
                            int idEmpleado = llavePrimariaCliente.getInt(1);                            
                            System.out.println(dir);
                            if (empleado.getSubordinado().equals("") && !dir.equals("0")) {
                                query = "INSERT INTO directivo(categoria, id_empleado) VALUES (?, ?)";
                                PreparedStatement stDirectivo = ConexionBD.obtener().prepareStatement(query);
                                stDirectivo.setInt(1, empleado.getIdDirectivo());
                                stDirectivo.setInt(2, idEmpleado);
                                stDirectivo.executeUpdate();
                            }                            
                            query = "INSERT INTO empresa_empleado(id_empresa, id_empleado) VALUES (?, ?)";
                            PreparedStatement stEmpresa_Cliente = ConexionBD.obtener().prepareStatement(query);
                            stEmpresa_Cliente.setInt(1, empresa.getId());
                            stEmpresa_Cliente.setInt(2, idEmpleado);
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
     * @param indiceEmpleado     
     * @return true or false
     * Metodo que permite eliminar un empleado a la lista
     */
    public boolean eliminarEmpleado(int indiceEmpleado, Empleado empleado, Empresa empresa) {
       try {
            String query = "DELETE FROM empresa_empleado WHERE id_empresa = ? AND id_empleado = ?";
            PreparedStatement stEmpresa_Empleado = ConexionBD.obtener().prepareStatement(query);
            stEmpresa_Empleado.setInt(1, empresa.getId());
            stEmpresa_Empleado.setInt(2, empleado.getIdEmpleado());                       
            
            String queryEmpleado = ("DELETE FROM empleado WHERE id_empleado = ? AND id_empresa_reg = ?");
            PreparedStatement stEmpleado = ConexionBD.obtener().prepareStatement(queryEmpleado);                                          
            stEmpleado.setInt(1, empleado.getIdEmpleado());                                    
            stEmpleado.setInt(2, empresa.getId());

            String queryDirectivo = ("DELETE FROM directivo WHERE id_empleado = ?");
            PreparedStatement stDirectivo = ConexionBD.obtener().prepareStatement(queryDirectivo);                                          
            stDirectivo.setInt(1, empleado.getIdEmpleado());                                                
                       
            stDirectivo.executeUpdate();
            int filaEliminadaEmpresa_Empleado = stEmpresa_Empleado.executeUpdate();
            int filaEliminadaEmpleado = stEmpleado.executeUpdate();
            return filaEliminadaEmpresa_Empleado > 0 || filaEliminadaEmpleado > 0;
        } catch (SQLException e) {
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
    public boolean modificarEmpleado(Empleado empleado, Empresa empresa) {
        try {
            String comparador_empresaEmpleado = "";        
            String dir = String.valueOf(empleado.getIdDirectivo());
            //Verifica si existe un registro con la empresa seleccionada
            String queryEmpresa_Empresa = "SELECT * FROM empresa_empleado ee WHERE ee.id_empresa = ? AND ee.id_empleado = ?;";
            PreparedStatement stEmpresa_Empleado = ConexionBD.obtener().prepareStatement(queryEmpresa_Empresa);
            stEmpresa_Empleado.setInt(1, empresa.getId());
            stEmpresa_Empleado.setInt(2, empleado.getIdEmpleado());
            ResultSet res = stEmpresa_Empleado.executeQuery();
            
            if (res.next()) {
                comparador_empresaEmpleado = res.getString("id_empresa_empleado");                
            }
            
            String comparador = "";
            //Verifica si existe un registro con la empresa seleccionada
            String queryEmpleado = "SELECT * FROM empleado em WHERE em.id_empresa_reg = ? AND em.id_empleado = ?;";
            PreparedStatement stEmpleado = ConexionBD.obtener().prepareStatement(queryEmpleado);
            stEmpleado.setInt(1, empresa.getId());
            stEmpleado.setInt(2, empleado.getIdEmpleado());
            ResultSet ress = stEmpleado.executeQuery();            
            
            if (ress.next()) {
                comparador = ress.getString("id_empleado");                
            }
            
            String comparadorDir = "";
            //Verifica si existe un registro con la empresa seleccionada
            String queryDirectivo1 = "SELECT * FROM directivo d WHERE d.id_empleado = ?;";
            PreparedStatement stDir1 = ConexionBD.obtener().prepareStatement(queryDirectivo1);            
            stDir1.setInt(1, empleado.getIdEmpleado());
            ResultSet resDir = stDir1.executeQuery();            
            
            if (resDir.next()) {
                comparadorDir = resDir.getString("id_directivo");                
            }
                        
            //Crea una nueva inserción si no existe con la empresa seleccionada
            if (comparador.isEmpty() || comparador_empresaEmpleado.isEmpty()) {            
                String query = ("INSERT INTO empleado(salario, subordinado, id_persona, id_empresa_reg, id_directivo) VALUES ( ?, ?, ?, ?, ?)");
                    PreparedStatement stEmpleado1 = ConexionBD.obtener().prepareStatement
                                                (query, Statement.RETURN_GENERATED_KEYS);
                    stEmpleado1.setDouble(1, empleado.getSalario());
                    stEmpleado1.setString(2, empleado.getSubordinado());
                    stEmpleado1.setInt(3, empleado.getId());                    
                    stEmpleado1.setInt(4, empresa.getId());
                    stEmpleado1.setInt(5, empleado.getIdDirectivo());
                    int filaInsertada = stEmpleado1.executeUpdate();
                    if (filaInsertada > 0 ) {
                        ResultSet llavePrimariaCliente = stEmpleado1.getGeneratedKeys();                                                
                        if (llavePrimariaCliente.next()) {
                            int idEmpleado = llavePrimariaCliente.getInt(1);                                                        
                            if (empleado.getSubordinado().equals("") && !dir.equals("0")) {
                                String queryDir = "INSERT INTO directivo(categoria, id_empleado) VALUES (?, ?)";
                                PreparedStatement stDirectivo = ConexionBD.obtener().prepareStatement(queryDir);
                                stDirectivo.setInt(1, empleado.getIdDirectivo());
                                stDirectivo.setInt(2, idEmpleado);
                                stDirectivo.executeUpdate();
                            }                            
                            query = "INSERT INTO empresa_empleado(id_empresa, id_empleado) VALUES (?, ?)";
                            PreparedStatement stEmpresa_Empleado1 = ConexionBD.obtener().prepareStatement(query);
                            stEmpresa_Empleado1.setInt(1, empresa.getId());
                            stEmpresa_Empleado1.setInt(2, idEmpleado);
                            filaInsertada = stEmpresa_Empleado1.executeUpdate();
                        if (filaInsertada > 0) {
                            return true;
                        }
                    }
                }
            } else {                
                String queryEmpleado1 = ("UPDATE empleado SET salario = ?, subordinado = ?,  id_directivo = ? WHERE id_empleado = ?");
                PreparedStatement stEmpleado1 = ConexionBD.obtener().prepareStatement(queryEmpleado1);
                stEmpleado1.setDouble(1, empleado.getSalario());
                stEmpleado1.setString(2, empleado.getSubordinado());
                stEmpleado1.setInt(3, empleado.getIdDirectivo());                                        
                stEmpleado1.setInt(4, empleado.getIdEmpleado());
                
                String queryDir = "UPDATE directivo SET categoria = ?, id_empleado = ? WHERE id_empleado = ?";
                PreparedStatement stDir = ConexionBD.obtener().prepareStatement(queryDir);
                stDir.setInt(1, empleado.getIdDirectivo());
                stDir.setInt(2, empleado.getIdEmpleado());
                stDir.setInt(3, empleado.getIdEmpleado());
                
                stDir.executeUpdate();
                stEmpleado1.executeUpdate();                
            }
            
            
            if (!dir.equals("0") && comparadorDir.equals("")) {
                String queryDir = "INSERT INTO directivo(categoria, id_empleado) VALUES (?, ?)";
                PreparedStatement stDirectivo = ConexionBD.obtener().prepareStatement(queryDir);
                stDirectivo.setInt(1, empleado.getIdDirectivo());
                stDirectivo.setInt(2, empleado.getIdEmpleado());
                stDirectivo.executeUpdate();
            } else {
                if (empleado.getSubordinado().equals("SI")) {
                    String queryDirectivo = ("DELETE FROM directivo WHERE id_empleado = ?");
                    PreparedStatement stDirectivo = ConexionBD.obtener().prepareStatement(queryDirectivo);                                          
                    stDirectivo.setInt(1, empleado.getIdEmpleado());  
                    stDirectivo.executeUpdate(); 
                }
                 
            }
            
            String queryPersona = ("UPDATE persona SET tid = ?, documento = ?, nombres = ?, "
                    + "apellidos = ?, fechaNac = ?, genero = ? WHERE id_persona = ?");
            PreparedStatement stPersona = ConexionBD.obtener().prepareStatement(queryPersona);
            stPersona.setString(1, empleado.getTID());
            stPersona.setString(2, empleado.getDocumento());
            stPersona.setString(3, empleado.getNombre());
            stPersona.setString(4, empleado.getApellidos());            
            stPersona.setDate(5, (java.sql.Date) empleado.getFechaNacimiento());
            stPersona.setString(6, empleado.getGenero());
            stPersona.setInt(7, empleado.getId());
                        
            int filaInsertadaPersona = stPersona.executeUpdate();
            
            return filaInsertadaPersona > 0;            
        
         } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }    
    
    public ArrayList<Empleado> buscarEmpleados(Empresa empresa){
        
        ArrayList<Empleado> buscarDocumento = new ArrayList<>();
        String buscar = FrmEmpleados.txtBuscar.getText();
                    
        try{
            PreparedStatement query = ConexionBD.obtener().prepareStatement(""
                    + "SELECT "
                    + "p.id_persona, p.tid, p.documento, p.nombres, p.apellidos, "
                    + "p.fechaNac, p.genero, TIMESTAMPDIFF(YEAR, p.fechaNac, CURRENT_DATE()) AS edad, "
                    + "em.id_empleado, em.salario, em.subordinado, em.id_empresa_reg, em.id_directivo, "
                    + "d.id_directivo, d.categoria "
                    + " FROM persona p "
                    + " LEFT JOIN empleado em ON p.id_persona = em.id_persona "
                    + " LEFT JOIN empresa_empleado ee ON em.id_empresa_reg = ee.id_empresa "
                    + " LEFT JOIN directivo d ON em.id_empleado = d.id_empleado "  
                    + " WHERE p.documento LIKE '%" + buscar + "%' ");                        

            //query.setInt(1, empresa.getId());        
            ResultSet res = query.executeQuery();
                        
            if(res.next()){
                buscarDocumento.add(new Empleado(res.getInt("p.id_persona"), res.getString("p.tid"), 
                        res.getString("p.documento"), res.getString("p.nombres"), res.getString("p.apellidos"), 
                        res.getDate("p.fechaNac"), res.getString("p.genero"), res.getInt("edad"),
                        res.getInt("em.id_empresa_reg"),res.getInt("em.id_empleado"), res.getDouble("em.salario"), 
                        res.getString("em.subordinado"), res.getInt("em.id_directivo"))); 
            }
            
            return buscarDocumento;
        }catch (SQLException e){  
            System.out.println(e.getMessage());
        }
        return buscarDocumento;
    }
    
    public boolean duplicados(Empleado empleado) {        
        try {
            String bandera = "";
            String query2 = ("SELECT * FROM persona WHERE documento = ?"); 
            PreparedStatement stPersona2 = ConexionBD.obtener().prepareStatement(query2);
            stPersona2.setString(1, empleado.getDocumento());
            
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
    
    public boolean existeDirectivo(int categoria) {        
        try {
            String bandera = "";
            String query = ("SELECT * FROM directivo WHERE categoria = ? "); 
            PreparedStatement stDirectivo = ConexionBD.obtener().prepareStatement(query);
            stDirectivo.setInt(1, categoria);           
            
            ResultSet res = stDirectivo.executeQuery();
            
            while(res.next()){
                bandera = res.getString("id_directivo");
            }            
            
            return bandera.isEmpty();                
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    } 

}
