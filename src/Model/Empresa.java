/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import java.util.ArrayList;

/**
 *
 * @author Jhoantan Grueso Perea
 * @since 02/07/2022
 * @version 1.0
 */
public class Empresa {

    //Atributos
    private String nombre;
    private String NIT;
    private String telefono;
    private String direccion;
    private String email;
    private String actEconomica;
    private ArrayList<Empleado> empleados;
    private ArrayList<Cliente> clientes;

    //Contructor
    public Empresa(String nombre, String NIT, String telfono, String direccion, 
            String email, String actEconomica) {
        this.nombre = nombre;
        this.NIT = NIT;
        this.telefono = telfono;
        this.direccion = direccion;
        this.email = email;
        this.actEconomica = actEconomica;
        empleados = new ArrayList<>();
        clientes = new ArrayList<>();
    }   

    
    
    
    //Getter y Setter o modificadores *****************************
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
     * 
     * @param cliente
     * @return true or false
     * Metodo que qermite agregar un clientes de la lista
     */
    public boolean agregarCliente(Cliente cliente) {
        try {
            clientes.add(cliente);
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
    public boolean eliminarCliente(int indiceCliente) {
        try {
            clientes.remove(indiceCliente);
            return true;
        } catch (Exception e) {
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
            this.empleados.add(indiceEmpleado, empleado);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
