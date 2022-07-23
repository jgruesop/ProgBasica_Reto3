/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
    private ArrayList<Cliente> clientes;

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

//    public Empresa() {
//    }   
    
    
    //Getter y Setter o modificadores *****************************
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
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
    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    /**
     * 
     * @param clientes     
     */
    public void setCliente(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }
    
    
    //Metodos ***********************************
    
    /**
     * Metodo para listar cliestes desde la BD
     * @return 
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
    
    /**
     * Metodo para listar cliestes desde la BD
     * @return 
     */
    public ArrayList<Cliente> obtenerClientes() {
        
        ArrayList<Cliente> listarClientes = new ArrayList<>();
        
        try {
            PreparedStatement query = ConexionBD.obtener().prepareStatement(""
                    + "SELECT "
                    + "c.id_cliente, p.tid, p.documento, p.nombres, p.apellidos, "
                    + "p.fechaNac, p.genero, TIMESTAMPDIFF(YEAR, p.fechaNac, CURRENT_DATE()) AS edad, "
                    + "c.telefono, c.direccion, c.email "
                    + " FROM persona p "
                    + " INNER JOIN cliente c ON p.id_persona = c.id_cliente "
                    + " INNER JOIN empresa_cliente ec ON ec.id_cliente = c.id_cliente "
                    + " INNER JOIN empresa e ON e.id_empresa = ec.id_empresa "
                    + " WHERE e.id_empresa = ?");
            ArrayList<Empresa> idEmpresa = new ArrayList<>();
            idEmpresa = obtenerEmpresas();            
            query.setInt(1, getId());
            ResultSet res = query.executeQuery();            
            
            while (res.next()){                
                
                listarClientes.add(new Cliente(res.getInt("id_cliente"), res.getString("tid"), 
                        res.getString("documento"), res.getString("nombres"), res.getString("apellidos"), 
                        res.getDate("fechaNac"), res.getString("genero"), res.getInt("edad"), res.getString("telefono"), 
                        res.getString("email"),res.getString("direccion")));                
            }                        
            
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
        return listarClientes;
    }
    
    /**
     * 
     * @param cliente
     * @return true or false
     * Metodo que qermite agregar un clientes de la lista
     */
    public boolean agregarCliente(Cliente cliente) {
        try {
            
            return true;
        } catch (Exception e) {
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
    public boolean eliminarCliente(String indiceCliente) {
        try {
            PreparedStatement query = ConexionBD.obtener().prepareStatement(""
                    + "DELETE FROM cliente WHERE id_cliente = ?;");
            query.setInt(1, Integer.parseInt(indiceCliente));
            boolean  res = query.execute();
            return true;
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
     * Metodo que permite modificar un clientes de la lista
     */
    public boolean modificarCliente(int indiceCliente, Cliente cliente) {
        try {
            this.clientes.set(indiceCliente, cliente);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
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