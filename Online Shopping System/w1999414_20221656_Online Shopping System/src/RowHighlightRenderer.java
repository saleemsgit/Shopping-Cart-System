import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class RowHighlightRenderer implements TableCellRenderer {

    private static final Color HIGHLIGHT_COLOR = Color.RED;
    private static final Color DEFAULT_COLOR = Color.WHITE;

    private DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer();

    private String[] productItemsAvail;

    public RowHighlightRenderer(String[] productItemsAvail) {
        this.productItemsAvail = productItemsAvail;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        Component component = defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Check if the value in the third column (index 2) is less than 3
        if (Integer.parseInt(productItemsAvail[row]) < 3) {
            component.setBackground(HIGHLIGHT_COLOR);
            component.setForeground(Color.WHITE); // Set text color to make it visible on a red background
        } else {
            component.setBackground(DEFAULT_COLOR);
            component.setForeground(table.getForeground());
        }

        return component;
    }
}
