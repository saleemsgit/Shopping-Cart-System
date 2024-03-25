import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;


public class GUIApplication implements ActionListener {

    private JComboBox<String> selectOption;
    private DefaultTableModel tableModel;
    private String productID;
    private JFrame frame;
    private JPanel section1,section2,section3,filter;
    private String[] productItemsAvail;
    private JButton shoppingCart;
    private List<Product> filteredProducts;
    private static ShoppingCart cart = new ShoppingCart();
    private static boolean loggedUser = false;
    private JTable table;
    

    public GUIApplication() {


        frame = new JFrame("Online Shopping System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        section1 = new JPanel();
        section2 = new JPanel(new BorderLayout());
        section3 = new JPanel();



        section1.setPreferredSize(new Dimension(100, 100));
        section2.setPreferredSize(new Dimension(100, 100));
        section3.setPreferredSize(new Dimension(100, 200));

        // FILTER PANEL
        filter = filter();
        section1.add(filter);

        // TABLE PANEL
        JScrollPane scrollPane = table();
        section2.add(scrollPane, BorderLayout.CENTER);

        //PRODUCT DETAILS PANEL



        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                WestminsterShoppingManager saveAcess = new WestminsterShoppingManager();
                saveAcess.saveProducts();
            }
        });

        section3.setBorder(new EmptyBorder(0,10,0,10));

        frame.setLayout(new BorderLayout());
        frame.add(section1, BorderLayout.NORTH);
        frame.add(section2, BorderLayout.CENTER);
        frame.add(section3, BorderLayout.SOUTH);
        frame.setSize(640, 540);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public JPanel filter(){
        filter = new JPanel(new BorderLayout());

        JPanel selectPanel = new JPanel(new FlowLayout());

        JLabel selectLabel = new JLabel("Select Category");
        
        String[] category = {"All", "Electronic", "Clothing"};
        selectOption = new JComboBox<>(category);
        
        selectPanel.add(selectLabel);
        selectPanel.add(selectOption);
        
        filter.add(selectPanel, BorderLayout.CENTER);
        
        shoppingCart = new JButton("View Cart");
        JPanel button = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        button.add(shoppingCart);
        filter.add(button, BorderLayout.NORTH);
        // filter.setLayout(null);
        // section1.setLayout(null);

        // filter.setBounds(0, 0, 640, 100);
        // selectLabel.setBounds(220, 50, 96, 16);
        
        // selectOption.setBounds(320, 50, 100, 16);
        selectOption.addActionListener(this);
        

        shoppingCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    frame.setVisible(false);
        
                    Cart cart = new Cart();
                    cart.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        
                    cart.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            System.out.println("AAAAA");
                            frame.setVisible(true);
                            
                            
                        }
                    });
                });
            }
        });

        shoppingCart.setBounds(520, 10, 100, 24);
        filter.add(shoppingCart, BorderLayout.EAST);

        return filter;
    }

    public JScrollPane table() {

        // https://www.plus2net.com/java_tutorial/swing-jtable.php
        
        String[][] data = getData("");
        String[] columns = new String[]{"Product ID", "Product Name", "Product Category", "Product Price", "Info"};


        tableModel = new DefaultTableModel(data, columns){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);

        List<RowSorter.SortKey> sort = new ArrayList<>();
        int columnNumber = 1;
        sort.add(new RowSorter.SortKey(columnNumber, SortOrder.ASCENDING));
        sorter.setSortKeys(sort);
        sorter.sort();

        table.setDefaultRenderer(Object.class, new RowHighlightRenderer(productItemsAvail));
        
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();

                    productID = (String) tableModel.getValueAt(row, 0);
                    productDetails(productID);
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(0,10,0,10));
        return scrollPane;
    }
    
    public void productDetails(String productID) {
        JLabel detail1,detail2,detail3,detail4,detail5,detail6;

        section3.removeAll();

        BoxLayout productDetails = new BoxLayout(section3, BoxLayout.Y_AXIS);
        
        section3.setLayout(productDetails);


        JLabel productDetailsLabel = new JLabel("Selected Product - Details");
        productDetailsLabel.setMaximumSize(new Dimension(320, 50));
        section3.add(productDetailsLabel);


        for (Product product : filteredProducts) {
            if (productID.equals(product.getProductID())){

                detail1 = new JLabel("Product ID: "+productID);
                section3.add(detail1);

                if (product instanceof Electronic){
                    Electronic electronic = (Electronic) product;
                    detail2 = new JLabel("Product Category: Electronic");
                    section3.add(detail2);

                    detail4 = new JLabel("\nProduct Brand: "+electronic.getProductBrand());
                    section3.add(detail4);
        
                    detail5 = new JLabel("\nProduct Warranty: "+electronic.getProductWarranty());
                    section3.add(detail5);
                }
                else if (product instanceof Clothing){
                    Clothing clothing = (Clothing) product;
                    detail2 = new JLabel("Product Category: Clothing");
                    section3.add(detail2);

                    detail4 = new JLabel("\nProduct Size: "+clothing.getProductSize());
                    section3.add(detail4);
        
                    detail5 = new JLabel("\nProduct Color: "+clothing.getProductColor());
                    section3.add(detail5);
                }
                
                detail3 = new JLabel("\nProduct Name: "+product.getProductName());
                section3.add(detail3);
                
                if (product.getNoProductAvail() <= 0 ){
                    detail6 = new JLabel("No. Items Available: "+product.getNoProductAvail());
                    detail6.setBackground(Color.RED);
                    detail6.setOpaque(true);
                    section3.add(detail6);
                } else if (product.getNoProductAvail() > 0){
                    detail6 = new JLabel("No. Items Available: "+product.getNoProductAvail());
                    section3.add(detail6);
                    
                }
            }
        }


        JButton addToCartButton = new JButton("Add to Cart");

        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Product product : WestminsterShoppingManager.getProducts()) {
                    if (productID.equals(product.getProductID())){
                        if (product.getNoProductAvail() > 0){

                            product.setNoProductAvail(product.getNoProductAvail() - 1);
                            cart.addProduct(product);
                            productDetails(product.getProductID());
                            
                            if (!loggedUser){
                                WestminsterShoppingManager.loggedInUser.setPurchaseHistory();
                                System.out.println(WestminsterShoppingManager.loggedInUser.getPurchaseHistory());
                                loggedUser = true;

                            }
                            updateTableData();
                            table.setDefaultRenderer(Object.class, new RowHighlightRenderer(productItemsAvail));
                            table.repaint();
                            table.revalidate();
                        }
                    }
                }
                
            }
        });

        JPanel addToCartPanel = new JPanel();
        addToCartPanel.setLayout(null);
        addToCartPanel.add(addToCartButton);
        addToCartButton.setBounds(270, 30, 100, 30);
        section3.add(addToCartPanel);
        
        section3.repaint();
        section3.revalidate();
    }

    private void updateTableData() {
        String[][] newData = getData("");
        tableModel.setDataVector(newData, new String[]{"Product ID", "Product Name", "Product Category", "Product Price", "Product Info"});
        table.repaint();
        table.revalidate();
    }

    private String[][] getData(String category) {
        try {
            

            if (category.equals("Electronic")) {
                filteredProducts = WestminsterShoppingManager.getProducts()
                        .stream()
                        .filter(product -> product instanceof Electronic)
                        .collect(Collectors.toList());
            } else if (category.equals("Clothing")) {
                filteredProducts = WestminsterShoppingManager.getProducts()
                        .stream()
                        .filter(product -> product instanceof Clothing)
                        .collect(Collectors.toList());
            } else {
                filteredProducts = WestminsterShoppingManager.getProducts();
            }
            
            String[][] data = new String[filteredProducts.size()][5];
            productItemsAvail = new String[filteredProducts.size()];
    
            for (int index = 0; index < data.length; index++) {
                String[] productDetails = new String[5];
                productDetails[0] = filteredProducts.get(index).getProductID();
                productDetails[1] = filteredProducts.get(index).getProductName();
                if (filteredProducts.get(index) instanceof Electronic) {
                    productDetails[2] = "Electronic";
                    Electronic electronic = (Electronic) filteredProducts.get(index);
                    productDetails[4] = electronic.getProductBrand()+","+Integer.toString(electronic.getProductWarranty());
                } else if (filteredProducts.get(index) instanceof Clothing){
                    productDetails[2] = "Clothing";
                    Clothing clothing = (Clothing) filteredProducts.get(index);
                    productDetails[4] = clothing.getProductSize()+","+clothing.getProductColor();
                }
                productDetails[3] = Integer.toString(filteredProducts.get(index).getProductPrice()); 

                productItemsAvail[index] = Integer.toString(filteredProducts.get(index).getNoProductAvail());
                data[index] = productDetails;
            }

            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox) e.getSource();
        String category = (String) cb.getSelectedItem();


        Object[][] newData = getData(category);
        tableModel.setDataVector(newData, new String[]{"Product ID", "Product Name", "Product Category", "Product Price","Product Info"});

    }

    public static ArrayList<Product> getShoppingList(){
        return cart.getCart();
    }
}