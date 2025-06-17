/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views;

import models.Usuario;
import java.awt.BorderLayout;

/**
 *
 * @author ale
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    private Usuario usuarioLogueado;
    private boolean modoAdmin = false;
    private boolean mostrarSpoilers = true;
    
    // Instancias de cada pestaña para comunicación
    private PublicarTab publicarTab;
    private CategoriasTab categoriasTab;
    private JuegosTab juegosTab;
    private UsuariosTab usuariosTab;
    private ReseñasTab reseñasTab;
    private CrudTab crudTab;
    private AjustesTab ajustesTab;
    
    public MainFrame() {
        initComponents();
        setLocationRelativeTo(null);
        
        // Login
        LoginDialolog loginDialog = new LoginDialolog(this, true);
        loginDialog.setVisible(true);
        usuarioLogueado = loginDialog.getUsuarioLogueado();
        if (usuarioLogueado == null) {
            System.exit(0);
        }
        PopUpAjustes ajustesDialog = new PopUpAjustes(this, true);
        ajustesDialog.setVisible(true);

        // Configurar pestañas
        configurarPestañas();
        
        this.setVisible(true);
    }
    
    private void configurarPestañas() {
        // Crear instancias de cada pestaña
        publicarTab = new PublicarTab(this);
        categoriasTab = new CategoriasTab(this);
        juegosTab = new JuegosTab(this);
        usuariosTab = new UsuariosTab(this);
        reseñasTab = new ReseñasTab(this);
        crudTab = new CrudTab(this);
        ajustesTab = new AjustesTab(this);
        
        // Configurar layout de cada JPanel contenedor
        tabBuscar.setLayout(new BorderLayout());
        tabCategorias.setLayout(new BorderLayout());
        tabJuegos.setLayout(new BorderLayout());
        tabUsuarios.setLayout(new BorderLayout());
        tabReseñas.setLayout(new BorderLayout());
        tabCrud.setLayout(new BorderLayout());
        tabAjustes.setLayout(new BorderLayout());
        
        // Añadir contenido a cada JPanel contenedor
        tabBuscar.add(publicarTab, BorderLayout.CENTER);
        tabCategorias.add(categoriasTab, BorderLayout.CENTER);
        tabJuegos.add(juegosTab, BorderLayout.CENTER);
        tabUsuarios.add(usuariosTab, BorderLayout.CENTER);
        tabReseñas.add(reseñasTab, BorderLayout.CENTER);
        tabCrud.add(crudTab, BorderLayout.CENTER);
        tabAjustes.add(ajustesTab, BorderLayout.CENTER);
        
        // Configurar visibilidad inicial de pestaña CRUD
        actualizarVisibilidadCrud();
    }
    
    // Métodos para navegación entre pestañas
    public void irAReseñasDeJuego(models.Videojuego juego) {
        reseñasTab.mostrarReseñasDeJuego(juego);
        tabPadre.setSelectedComponent(tabReseñas);
    }
    
    public void irAReseñasDeUsuario(models.Usuario usuario) {
        reseñasTab.mostrarReseñasDeUsuario(usuario);
        tabPadre.setSelectedComponent(tabReseñas);
    }
    
    public void irAJuegosDeCategoria(models.Categoria categoria) {
        juegosTab.MostrarJuegosPorCategoria(categoria);
        tabPadre.setSelectedComponent(tabJuegos);
    }
    
    // Métodos para gestión de configuración
    public void setModoAdmin(boolean modoAdmin) {
        this.modoAdmin = modoAdmin;
        actualizarVisibilidadCrud();
        
        // Actualizar modo admin en todas las pestañas
        if (categoriasTab != null) {
            categoriasTab.actualizarModoAdmin(modoAdmin);
        }
        if (juegosTab != null) {
            juegosTab.actualizarModoAdmin(modoAdmin);
        }
        if (usuariosTab != null) {
            usuariosTab.actualizarModoAdmin(modoAdmin);
        }
        if (reseñasTab != null) {
            reseñasTab.actualizarModoAdmin(modoAdmin);
        }
    }
    
    public void setMostrarSpoilers(boolean mostrarSpoilers) {
        this.mostrarSpoilers = mostrarSpoilers;
    }
    
    private void actualizarVisibilidadCrud() {
        int indiceCrud = tabPadre.indexOfTab("Crud");
        
        if (modoAdmin) {
            // Mostrar pestaña CRUD si no está visible
            if (indiceCrud == -1) {
                tabPadre.insertTab("Crud", null, tabCrud, null, tabPadre.getTabCount() - 1);
            }
        } else {
            // Ocultar pestaña CRUD si está visible
            if (indiceCrud != -1) {
                tabPadre.removeTabAt(indiceCrud);
            }
        }
    }
    
    // Getters para acceso desde pestañas
    public Usuario getUsuarioLogueado() {
        return usuarioLogueado;
    }
    
    public boolean isModoAdmin() {
        return modoAdmin;
    }
    
    public boolean isMostrarSpoilers() {
        return mostrarSpoilers;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabPadre = new javax.swing.JTabbedPane();
        tabCategorias = new javax.swing.JPanel();
        tabJuegos = new javax.swing.JPanel();
        tabUsuarios = new javax.swing.JPanel();
        tabReseñas = new javax.swing.JPanel();
        tabCrud = new javax.swing.JPanel();
        tabAjustes = new javax.swing.JPanel();
        tabBuscar = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout tabCategoriasLayout = new javax.swing.GroupLayout(tabCategorias);
        tabCategorias.setLayout(tabCategoriasLayout);
        tabCategoriasLayout.setHorizontalGroup(
            tabCategoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 645, Short.MAX_VALUE)
        );
        tabCategoriasLayout.setVerticalGroup(
            tabCategoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 365, Short.MAX_VALUE)
        );

        tabPadre.addTab("Categorias", tabCategorias);

        javax.swing.GroupLayout tabJuegosLayout = new javax.swing.GroupLayout(tabJuegos);
        tabJuegos.setLayout(tabJuegosLayout);
        tabJuegosLayout.setHorizontalGroup(
            tabJuegosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 645, Short.MAX_VALUE)
        );
        tabJuegosLayout.setVerticalGroup(
            tabJuegosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 365, Short.MAX_VALUE)
        );

        tabPadre.addTab("Juegos", tabJuegos);

        javax.swing.GroupLayout tabUsuariosLayout = new javax.swing.GroupLayout(tabUsuarios);
        tabUsuarios.setLayout(tabUsuariosLayout);
        tabUsuariosLayout.setHorizontalGroup(
            tabUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 645, Short.MAX_VALUE)
        );
        tabUsuariosLayout.setVerticalGroup(
            tabUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 365, Short.MAX_VALUE)
        );

        tabPadre.addTab("Usuarios", tabUsuarios);

        javax.swing.GroupLayout tabReseñasLayout = new javax.swing.GroupLayout(tabReseñas);
        tabReseñas.setLayout(tabReseñasLayout);
        tabReseñasLayout.setHorizontalGroup(
            tabReseñasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 645, Short.MAX_VALUE)
        );
        tabReseñasLayout.setVerticalGroup(
            tabReseñasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 365, Short.MAX_VALUE)
        );

        tabPadre.addTab("Reseñas", tabReseñas);

        javax.swing.GroupLayout tabCrudLayout = new javax.swing.GroupLayout(tabCrud);
        tabCrud.setLayout(tabCrudLayout);
        tabCrudLayout.setHorizontalGroup(
            tabCrudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 645, Short.MAX_VALUE)
        );
        tabCrudLayout.setVerticalGroup(
            tabCrudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 365, Short.MAX_VALUE)
        );

        tabPadre.addTab("Crud", tabCrud);

        javax.swing.GroupLayout tabAjustesLayout = new javax.swing.GroupLayout(tabAjustes);
        tabAjustes.setLayout(tabAjustesLayout);
        tabAjustesLayout.setHorizontalGroup(
            tabAjustesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 645, Short.MAX_VALUE)
        );
        tabAjustesLayout.setVerticalGroup(
            tabAjustesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 365, Short.MAX_VALUE)
        );

        tabPadre.addTab("Ajustes", tabAjustes);

        javax.swing.GroupLayout tabBuscarLayout = new javax.swing.GroupLayout(tabBuscar);
        tabBuscar.setLayout(tabBuscarLayout);
        tabBuscarLayout.setHorizontalGroup(
            tabBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 645, Short.MAX_VALUE)
        );
        tabBuscarLayout.setVerticalGroup(
            tabBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 365, Short.MAX_VALUE)
        );

        tabPadre.addTab("Publicar", tabBuscar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabPadre, javax.swing.GroupLayout.PREFERRED_SIZE, 645, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabPadre)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel tabAjustes;
    private javax.swing.JPanel tabBuscar;
    private javax.swing.JPanel tabCategorias;
    private javax.swing.JPanel tabCrud;
    private javax.swing.JPanel tabJuegos;
    private javax.swing.JTabbedPane tabPadre;
    private javax.swing.JPanel tabReseñas;
    private javax.swing.JPanel tabUsuarios;
    // End of variables declaration//GEN-END:variables
}
