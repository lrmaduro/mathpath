/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

/**
 *
 * @author luisr
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import coil.prototipo.logica.ListaPreguntas;
import coil.prototipo.logica.Pregunta;

public class PreguntaChecklistPanel extends JPanel {
    private DefaultListModel<Pregunta> listModel;
    private JList<Pregunta> checklist;
    private java.util.List<Boolean> checkedItems;

    public PreguntaChecklistPanel(ArrayList<Pregunta> preguntas) {
        setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        checkedItems = new ArrayList<>();

        // Load Pregunta objects dynamically
        for (Pregunta p : preguntas) {
            listModel.addElement(p);
            checkedItems.add(false);
        }

        checklist = new JList<>(listModel);
        checklist.setCellRenderer(new CheckboxListRenderer());

        checklist.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = checklist.locationToIndex(e.getPoint());
                if (index >= 0) {
                    checkedItems.set(index, !checkedItems.get(index));
                    checklist.repaint();
                }
            }
        });

        add(new JScrollPane(checklist), BorderLayout.CENTER);
    }

    // Get all selected Pregunta objects
    public ListaPreguntas getCheckedPreguntas() {
        ArrayList<Pregunta> seleccionadas = new ArrayList<>();
        for (int i = 0; i < listModel.getSize(); i++) {
            if (checkedItems.get(i)) {
                seleccionadas.add(listModel.getElementAt(i));
            }
        }
        return new ListaPreguntas(seleccionadas);
    }

    // Dynamically add a Pregunta at runtime
    public void addPregunta(Pregunta p) {
        listModel.addElement(p);
        checkedItems.add(false);
        checklist.repaint();
    }

    // Renderer: shows each Pregunta’s enunciado with a checkbox
    private class CheckboxListRenderer extends JCheckBox implements ListCellRenderer<Pregunta> {
        @Override
        public Component getListCellRendererComponent(
                JList<? extends Pregunta> list, Pregunta value, int index,
                boolean isSelected, boolean cellHasFocus) {

            setText(value.getEnunciado());
            setSelected(checkedItems.get(index));
            setBackground(isSelected ? Color.LIGHT_GRAY : Color.WHITE);
            setForeground(Color.BLACK);
            return this;
        }
    }
}

