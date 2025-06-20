/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package views.tabs;

import javax.swing.JOptionPane;

import controllers.ReseñaController;
import models.Reseña;
import models.Videojuego;
import views.MainFrame;

/**
 *
 * @author ale
 */
public class PublicarTab extends javax.swing.JPanel {

    /*
     * TODO - DISEÑO INTERFAZ PUBLICAR RESEÑAS:
     * - ComboBox de videojuegos (cargar con VideojuegoController.buscarTodos())
     * - Slider de puntuación (1-10) con JLabel que muestre valor actual
     * - JTextArea para comentario de la reseña (con scroll)
     * - JCheckBox "Contiene spoilers"
     * - JButton "Publicar Reseña"
     * 
     * TODO - FUNCIONALIDAD:
     * - Al hacer clic "Publicar": crear nueva Reseña con usuario logueado
     * - Validar que se haya seleccionado videojuego y escrito comentario
     * - Usar ReseñaController.crear() para guardar en BD
     * - Mostrar mensaje de éxito/error
     * - Limpiar formulario después de publicar
     */

    private MainFrame parent;
    private Videojuego videojuego;
    private ReseñaController reseñaController;

    /**
     * Creates new form PublicartAB
     */
    public PublicarTab() {
        initComponents();
        this.reseñaController = new ReseñaController();
        jScrollPane2.setVisible(false);
    }

    /**
     * Constructor con referencia al MainFrame padre
     */
    public PublicarTab(MainFrame parent) {
        this();
        this.parent = parent;

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        lblVideojuego = new javax.swing.JLabel();
        txtVideojuego = new javax.swing.JTextField();
        btnSeleccionarJuego = new javax.swing.JButton();
        lblPuntuacion = new javax.swing.JLabel();
        txtPuntuacion = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaComentario = new javax.swing.JTextArea();
        lblComentario = new javax.swing.JLabel();
        tBtnSpoilers = new javax.swing.JToggleButton();
        lblSpoilers = new javax.swing.JLabel();
        btnCrear = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAreaErrores = new javax.swing.JTextArea();

        jLabel1.setText("Publicar nueva reseña :");

        lblVideojuego.setText("Videojuego :");

        txtVideojuego.setEditable(false);

        btnSeleccionarJuego.setText("Seleccionar Videojuego");
        btnSeleccionarJuego.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarJuegoActionPerformed(evt);
            }
        });

        lblPuntuacion.setText("Puntuacion :");
        lblPuntuacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblPuntuacionMouseClicked(evt);
            }
        });

        txtAreaComentario.setColumns(20);
        txtAreaComentario.setRows(5);
        jScrollPane1.setViewportView(txtAreaComentario);

        lblComentario.setText("Comentario :");
        lblComentario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblComentarioMouseClicked(evt);
            }
        });

        tBtnSpoilers.setText("No");
        tBtnSpoilers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tBtnSpoilersActionPerformed(evt);
            }
        });

        lblSpoilers.setText("Contiene spoilers ?");

        btnCrear.setText("Crear");
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });

        jLabel2.setText("(Numero 0.0 - 10.0)");

        txtAreaErrores.setEditable(false);
        txtAreaErrores.setColumns(20);
        txtAreaErrores.setRows(5);
        jScrollPane2.setViewportView(txtAreaErrores);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblVideojuego)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtVideojuego)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblSpoilers)
                        .addGap(18, 18, 18)
                        .addComponent(tBtnSpoilers)
                        .addGap(102, 102, 102))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1)
                                .addComponent(btnSeleccionarJuego))
                            .addComponent(lblComentario)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblPuntuacion)
                                .addGap(18, 18, 18)
                                .addComponent(txtPuntuacion, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnCrear)
                                .addGap(102, 102, 102))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(63, 63, 63))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblVideojuego)
                            .addComponent(txtVideojuego, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSeleccionarJuego)
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPuntuacion)
                            .addComponent(txtPuntuacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(lblComentario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tBtnSpoilers)
                            .addComponent(lblSpoilers))
                        .addGap(53, 53, 53)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCrear)))
                .addGap(46, 46, 46))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSeleccionarJuegoActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnSeleccionarJuegoActionPerformed
        parent.getTabPadre().setSelectedComponent(parent.getTabJuegos());
        parent.getJuegosTab().modoSeleccion(false);
    }// GEN-LAST:event_btnSeleccionarJuegoActionPerformed

    private void lblPuntuacionMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_lblPuntuacionMouseClicked
        txtPuntuacion.requestFocus();
    }// GEN-LAST:event_lblPuntuacionMouseClicked

    private void lblComentarioMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_lblComentarioMouseClicked
        txtAreaComentario.requestFocus();
    }// GEN-LAST:event_lblComentarioMouseClicked

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnCrearActionPerformed
        
        // StringBuilder para acumular errores de validación
        StringBuilder mensajeError = new StringBuilder();
        
        // Validaciones una tras otra a lo bruto
        if (videojuego == null) {
            mensajeError.append(mensajeError.isEmpty() ? "" : "\n").append("Debes seleccionar un videojuego");
        }
        
        if (txtPuntuacion.getText().trim().isEmpty()) {
            mensajeError.append(mensajeError.isEmpty() ? "" : "\n").append("Debes escribir una puntuación");
        }
        
        if (txtAreaComentario.getText().trim().isEmpty()) {
            mensajeError.append(mensajeError.isEmpty() ? "" : "\n").append("Debes escribir un comentario");
        }
        
        // Si hay errores, mostrarlos y salir
        if (mensajeError.length() > 0) {
            MostrarError(mensajeError.toString());
            return;
        }
        
        // Si llegamos aquí, todo está bien, intentar crear la reseña
        try {
            // Ocultar errores previos
            jScrollPane2.setVisible(false);
            
            Reseña reseña = new Reseña(
                    txtPuntuacion.getText().trim(),
                    txtAreaComentario.getText().trim(),
                    tBtnSpoilers.isSelected(),
                    parent.getUsuarioLogueado(),
                    videojuego);
            
            reseñaController.crear(reseña);
            
            JOptionPane.showMessageDialog(null, "Reseña creada correctamente");
            
            // Actualizar pestañas relacionadas simulando "pulsar buscar"
            actualizarTrasCrud();
            
            // Limpiar formulario tras éxito
            txtAreaComentario.setText("");
            txtPuntuacion.setText("");
            tBtnSpoilers.setSelected(false);
            tBtnSpoilers.setText("No");
            txtVideojuego.setText("");
            videojuego = null;
            
        } catch (IllegalArgumentException e) {
            // Verificar si es error de reseña duplicada
            if (e.getMessage() != null && 
                (e.getMessage().contains("Ya existe una reseña") || 
                 e.getMessage().contains("Duplicate entry"))) {
                
                // PopUp de confirmación para actualizar reseña existente
                int opcion = JOptionPane.showConfirmDialog(
                    this, 
                    "Ya tienes una reseña para este videojuego.\n¿Quieres sobreescribir tu reseña anterior?",
                    "Reseña ya existe", 
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
                );
                
                if (opcion == JOptionPane.YES_OPTION) {
                    actualizarReseñaExistente();
                }
                // Si es NO_OPTION, no hacer nada (mantener formulario como está)
            } else {
                // Otros errores de validación
                MostrarError("Error: " + e.getMessage());
            }
        } catch (Exception e) {
            MostrarError("Error del sistema: " + e.getMessage());
        }
    }// GEN-LAST:event_btnCrearActionPerformed

    private void tBtnSpoilersActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_tBtnSpoilersActionPerformed
        if (tBtnSpoilers.isSelected()) {
            tBtnSpoilers.setText("Si");
        } else {
            tBtnSpoilers.setText("No");
        }
    }// GEN-LAST:event_tBtnSpoilersActionPerformed

    public void setVideojuego(Videojuego videojuego) {
        this.videojuego = videojuego;
        StringBuilder sb = new StringBuilder();
        sb.append(videojuego.getTitulo())
                .append(" (").append(videojuego.getFechaLanzamiento().getYear()).append(")");
        txtVideojuego.setText(sb.toString());
    }

    /**
     * Muestra un mensaje de error en el área de errores
     * Similar al patrón usado en otros tabs
     */
    public void MostrarError(String mensaje) {
        txtAreaErrores.setText(mensaje);
        jScrollPane2.setVisible(true);
    }

    /**
     * Actualiza las pestañas relacionadas tras operaciones CRUD
     * Simula "pulsar buscar" en pestañas que muestran datos relacionados
     */
    public void actualizarTrasCrud() {
        // Actualizar JuegosTab (estadísticas de videojuegos cambian)
        parent.getJuegosTab().actualizarTrasCrud();
        
        // Actualizar UsuariosTab (estadísticas de usuarios cambian) 
        parent.getUsuariosTab().actualizarTrasCrud();
        
        // Actualizar ReseñasTab (nueva reseña debe aparecer)
        parent.getReseñasTab().actualizarTrasCrud();
    }
    
    /**
     * Actualiza la reseña existente del usuario para el videojuego seleccionado
     */
    private void actualizarReseñaExistente() {
        try {
            // Buscar las reseñas del usuario y filtrar por videojuego
            java.util.List<Reseña> reseñasUsuario = reseñaController.buscarPorUsuario(parent.getUsuarioLogueado());
            
            Reseña reseñaExistente = null;
            for (Reseña reseña : reseñasUsuario) {
                if (reseña.getVideojuego().getIdVideojuego().equals(videojuego.getIdVideojuego())) {
                    reseñaExistente = reseña;
                    break;
                }
            }
            
            if (reseñaExistente != null) {
                // Actualizar con los nuevos datos del formulario
                try {
                    double puntuacion = Double.parseDouble(txtPuntuacion.getText().trim());
                    reseñaExistente.setPuntuacion(puntuacion);
                } catch (NumberFormatException e) {
                    MostrarError("La puntuación debe ser un número válido");
                    return;
                }
                
                reseñaExistente.setComentario(txtAreaComentario.getText().trim());
                reseñaExistente.setSpoilers(tBtnSpoilers.isSelected());
                reseñaExistente.setFechaReseña(java.time.LocalDate.now());
                
                // Actualizar en base de datos
                reseñaController.actualizar(reseñaExistente);
                
                JOptionPane.showMessageDialog(null, "Reseña actualizada correctamente");
                
                // Actualizar pestañas relacionadas
                actualizarTrasCrud();
                
                // Limpiar formulario tras éxito
                txtAreaComentario.setText("");
                txtPuntuacion.setText("");
                tBtnSpoilers.setSelected(false);
                tBtnSpoilers.setText("No");
                txtVideojuego.setText("");
                videojuego = null;
                
            } else {
                MostrarError("No se pudo encontrar la reseña existente");
            }
            
        } catch (Exception e) {
            MostrarError("Error al actualizar la reseña: " + e.getMessage());
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCrear;
    private javax.swing.JButton btnSeleccionarJuego;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblComentario;
    private javax.swing.JLabel lblPuntuacion;
    private javax.swing.JLabel lblSpoilers;
    private javax.swing.JLabel lblVideojuego;
    private javax.swing.JToggleButton tBtnSpoilers;
    private javax.swing.JTextArea txtAreaComentario;
    private javax.swing.JTextArea txtAreaErrores;
    private javax.swing.JTextField txtPuntuacion;
    private javax.swing.JTextField txtVideojuego;
    // End of variables declaration//GEN-END:variables
}
