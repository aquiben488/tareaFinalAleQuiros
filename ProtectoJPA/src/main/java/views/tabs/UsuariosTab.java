/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package views.tabs;

import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import controllers.UsuarioController;
import models.Usuario;
import views.MainFrame;

/**
 *
 * @author ale
 */
public class UsuariosTab extends javax.swing.JPanel {

    private MainFrame parent;
    private boolean modoAdmin = false;
    private UsuarioController usuarioController;
    
    // Variables para modo selección básico
    private boolean modoSeleccion = false;

    /**
     * Creates new form UsuariosTab
     */
    public UsuariosTab() {
        initComponents();
        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(rBtnAscendente);
        btnGroup.add(rBtnDescendente);
        rBtnAscendente.setSelected(true);
        this.usuarioController = new UsuarioController();
        btnCRUDEditar.setVisible(modoAdmin);
        btnCRUDEliminar.setVisible(modoAdmin);
        MostrarTodosLosUsuarios();
    }
    
    /**
     * Constructor con referencia al MainFrame padre
     */
    public UsuariosTab(MainFrame parent) {
        this();
        this.parent = parent;
        this.modoAdmin = parent.isModoAdmin();
        btnCRUDEditar.setVisible(modoAdmin);
        btnCRUDEliminar.setVisible(modoAdmin);
        
    }
    
    /**
     * Actualiza el estado del modo administrador
     */
    public void actualizarModoAdmin(boolean modoAdmin) {
        this.modoAdmin = modoAdmin;
        if (!modoSeleccion) {
            btnCRUDEditar.setVisible(modoAdmin);
            btnCRUDEliminar.setVisible(modoAdmin);
        }
    }
    
    /**
     * Activa/desactiva el modo selección para CRUD
     */
    public void modoSeleccion() {
        this.modoSeleccion = true;
        btnCRUDEditar.setVisible(false);
        btnCRUDEliminar.setVisible(false);
    }
    
    /**
     * Termina el modo selección
     */
    public void terminarModoSeleccion() {
        btnCRUDEditar.setVisible(modoAdmin);
        btnCRUDEliminar.setVisible(modoAdmin);
        
        // Volver a CrudTab
        parent.getTabPadre().setSelectedComponent(parent.getTabCrud());
        
        this.modoSeleccion = false;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BarraBusqueda = new javax.swing.JTextField();
        BtnBuscar = new javax.swing.JButton();
        rBtnAscendente = new javax.swing.JRadioButton();
        rBtnDescendente = new javax.swing.JRadioButton();
        btnCRUDEditar = new javax.swing.JButton();
        btnCRUDEliminar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        AreaListaUsuarios = new javax.swing.JList<>();
        BtnReset = new javax.swing.JButton();

        BarraBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BarraBusquedaActionPerformed(evt);
            }
        });

        BtnBuscar.setText("Buscar");
        BtnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBuscarActionPerformed(evt);
            }
        });

        rBtnAscendente.setText("Asc");

        rBtnDescendente.setText("Desc");

        btnCRUDEditar.setText("Editar");
        btnCRUDEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCRUDEditarActionPerformed(evt);
            }
        });

        btnCRUDEliminar.setText("Eliminar");
        btnCRUDEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCRUDEliminarActionPerformed(evt);
            }
        });

        jLabel1.setText("Ordenar resultados :");

        AreaListaUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AreaListaUsuariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(AreaListaUsuarios);

        BtnReset.setText("Reset");
        BtnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rBtnAscendente)
                                .addGap(18, 18, 18)
                                .addComponent(rBtnDescendente))
                            .addComponent(jLabel1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(BtnReset)
                        .addGap(18, 18, 18)
                        .addComponent(BarraBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BtnBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCRUDEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCRUDEliminar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnReset)
                    .addComponent(BarraBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnBuscar)
                    .addComponent(btnCRUDEditar)
                    .addComponent(btnCRUDEliminar))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rBtnAscendente)
                            .addComponent(rBtnDescendente))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Hace click en el boton buscar
     * (hacer enter es igual a hacer click en el boton buscar)
     */
    private void BarraBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BarraBusquedaActionPerformed
        BtnBuscarActionPerformed(evt);
    }//GEN-LAST:event_BarraBusquedaActionPerformed

    /**
     * Busca los usuarios por nombre
     * la busqueda es parcial
     * si esta vacio, muestra todos los usuarios
     */
    private void BtnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBuscarActionPerformed
        if (BarraBusqueda.getText().isEmpty()) {
            MostrarTodosLosUsuarios();
        }else{
            FiltrarPorNombre(BarraBusqueda.getText());
        }
    }//GEN-LAST:event_BtnBuscarActionPerformed

    /**
     * Edita un usuario
     * cuando se hace click en el boton editar
     */
    private void btnCRUDEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCRUDEditarActionPerformed
        Usuario usuario = AreaListaUsuarios.getSelectedValue();
        if (usuario != null) {
            // Navegar a CrudTab y cargar datos del usuario
            parent.getTabPadre().setSelectedComponent(parent.getTabCrud());
            parent.getCrudTab().cargarUsuario(usuario);
        } else {
            MostrarError("Selecciona un usuario para editar");
        }
    }//GEN-LAST:event_btnCRUDEditarActionPerformed

    /**
     * Elimina un usuario
     * cuando se hace click en el boton eliminar
     */
    private void btnCRUDEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCRUDEliminarActionPerformed
        Usuario usuario = AreaListaUsuarios.getSelectedValue();
        if (usuario != null) {
            int res = JOptionPane.showConfirmDialog(this,
                    "¿Seguro que deseas eliminar al usuario '" + usuario.getNombre() + "'?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);
            if (res != JOptionPane.YES_OPTION) {
                return;
            }
            try {
                usuarioController.eliminar(usuario.getIdUsuario());
                
                // Actualizar pestañas relacionadas
                parent.getReseñasTab().actualizarTrasCrud(); // Actualiza reseñas (transferidas al usuario 1)
                actualizarTrasCrud(); // Actualiza esta misma pestaña
                
            } catch (IllegalArgumentException e) {
                MostrarError("Error: " + e.getMessage() + ". Inténtelo de nuevo.");
            } catch (RuntimeException e) {
                MostrarError("Error del sistema. No se pudo eliminar el usuario.");
            }
        }
    }//GEN-LAST:event_btnCRUDEliminarActionPerformed

    /**
     * Muestra las reseñas de un usuario
     * cuando se hace doble click en un usuario
     */
    private void AreaListaUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AreaListaUsuariosMouseClicked
        if (evt.getClickCount() == 2) { // Doble clic
            Usuario usuario = AreaListaUsuarios.getSelectedValue();
            if (modoSeleccion) {
                // Seleccionar usuario para CRUD y volver
                parent.getCrudTab().setUsuario(usuario);
                this.terminarModoSeleccion();
            } else {
                if (usuario != null) {
                    parent.irAReseñasDeUsuario(usuario);
                }
            }
        }
    }//GEN-LAST:event_AreaListaUsuariosMouseClicked

    /**
     * Selecciona las opciones por defecto
     * y muestra todos los usuarios
     */
    private void BtnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnResetActionPerformed
        // Seleccionar opciones por defecto
        rBtnAscendente.setSelected(true);
        BarraBusqueda.setText(""); // Limpiar la barra de búsqueda
        MostrarTodosLosUsuarios();
        if (modoSeleccion) {
            terminarModoSeleccion();
        }
    }//GEN-LAST:event_BtnResetActionPerformed

    /**
     * Muestra todos los usuarios
     */
    private void MostrarTodosLosUsuarios() {
        List<Usuario> usuarios = null;
        try {
            usuarios = usuarioController.buscarTodos();
            MostrarUsuarios(usuarios);
        } catch (Exception e) {
            MostrarError(e.getMessage());
        }
    }

    /**
     * Filtrar usuarios por nombre
     * la busqueda es parcial
     */
    private void FiltrarPorNombre(String nombre) {
        List<Usuario> usuarios = null;
        try {
            // Buscar todos los usuarios y filtrar por texto contenido
            usuarios = usuarioController.buscarTodos();
            usuarios = usuarios.stream()
                .filter(u -> u.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .collect(java.util.stream.Collectors.toList());
            
            MostrarUsuarios(usuarios);
        } catch (Exception e) {
            MostrarError(e.getMessage());
        }
    }

    /**
     * Muestra los usuarios en la lista
     * es el unico metodo con la responsabilidad de mostrar los usuarios
     * en la lista
     */
    public void MostrarUsuarios(List<Usuario> usuarios) {
        if (usuarios.isEmpty()) {
            MostrarError("No hay usuarios que mostrar");
        }else{
        DefaultListModel<Usuario> modeloLista = new DefaultListModel<>();
        usuarios.sort(getComparator());
        for (Usuario usuario : usuarios) {
            modeloLista.addElement(usuario);
            }
            AreaListaUsuarios.setModel(modeloLista);
        }
    }

    /**
     * Muestra un mensaje de error
     * como la lista es de usuarios,
     * se crea un usuario temporal con el mensaje de error
     * (mejor que hacer un JOptionPane)
     */
    public void MostrarError(String mensaje) {
        DefaultListModel<Usuario> modeloLista = new DefaultListModel<>();
        // Crear un usuario temporal con el mensaje de error
        modeloLista.addElement(new Usuario() {
            @Override
            public String toString() {
                return mensaje;
            }
        });
        AreaListaUsuarios.setModel(modeloLista);
    }

    /**
     * Obtiene el comparador de usuarios según el radio button seleccionado
     * y el orden ascendente o descendente
     */
    private java.util.Comparator<Usuario> getComparator() {
        java.util.Comparator<Usuario> comparator = (u1, u2) -> {
            return u1.getNombre().compareTo(u2.getNombre());
        };
        
        return (rBtnAscendente.isSelected()) ? comparator : comparator.reversed();
    }

    /**
     * Actualiza la lista tras operaciones CRUD
     * Simula "pulsar buscar" para mantener el contexto actual del usuario
     */
    public void actualizarTrasCrud() {
        BtnBuscarActionPerformed(null);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<Usuario> AreaListaUsuarios;
    private javax.swing.JTextField BarraBusqueda;
    private javax.swing.JButton BtnBuscar;
    private javax.swing.JButton BtnReset;
    private javax.swing.JButton btnCRUDEditar;
    private javax.swing.JButton btnCRUDEliminar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rBtnAscendente;
    private javax.swing.JRadioButton rBtnDescendente;
    // End of variables declaration//GEN-END:variables
}
