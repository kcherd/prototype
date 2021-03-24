import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Main extends JFrame {

    private JTable jTable;
    private JTextArea colNames;
    private JTextArea colTypes;
    private JTextArea fileName;
    private JTextArea id1;
    private JTextArea id2;
    private JButton generateTable;
    private JButton saveFile;
    private JButton loadFile;
    private JButton sortTable;
    private JButton sumColumn;
    private Table table;
    private DefaultTableModel tableModel;

    public Main(){
        super("Table");

        JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());

        tableModel = new DefaultTableModel();
        jTable = new JTable(tableModel);
        colNames = new JTextArea("names");
        colTypes = new JTextArea("types");
        fileName = new JTextArea("file name");
        id1 = new JTextArea("id1");
        id2 = new JTextArea("id2");
        generateTable = new JButton("generate");
        saveFile = new JButton("save to file");
        loadFile = new JButton("load from file");
        sortTable = new JButton("sort");
        sumColumn = new JButton("sum columns");

        JScrollPane scrollPane = new JScrollPane(jTable);

        JPanel panel_1 = new JPanel();
        JPanel panel_2 = new JPanel();
        JPanel panel_3 = new JPanel();
        panel_1.setLayout(new GridLayout(3,3));
        panel_2.setLayout(new BorderLayout());
        panel_3.setLayout(new GridLayout(3,3));

        panel_1.add(id1);
        panel_1.add(id2);
        panel_1.add(colNames);
        panel_1.add(colTypes);
        panel_1.add(fileName);


        panel_2.add(scrollPane);

        panel_3.add(generateTable);
        panel_3.add(loadFile);
        panel_3.add(saveFile);
        panel_3.add(sortTable);
        panel_3.add(sumColumn);

        panel1.add(panel_1, BorderLayout.NORTH);
        panel1.add(panel_2, BorderLayout.CENTER);
        panel1.add(panel_3, BorderLayout.SOUTH);

        add(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        generateTable.addActionListener(e -> {
            System.out.println("button generate");
            String names = colNames.getText();
            String [] arrNames = names.split("\n");
            ArrayList<String> arrListNames = new ArrayList<String>(Arrays.asList(arrNames));
            table = new Table(arrListNames);
            String [] arrTypes = colTypes.getText().split("\n");
            ArrayList<String> arrListTypes = new ArrayList<>(Arrays.asList(arrTypes));
            table.addColumns(arrListTypes);

            ArrayList<String> someRow = new ArrayList<>();
            Random random = new Random();
            int min = 0;
            int max = 360;
            int diff = max - min;

            for(int i = 0; i < 100; i++){
                for (String arrListType : arrListTypes) {
                    switch (arrListType) {
                        case "IntColumn" -> {
                            someRow.add(random.nextInt(1000) + "");
                        }
                        case "FloatColumn" -> {
                            someRow.add(random.nextFloat() + "");
                        }
                        case "DateColumn" -> {
                            someRow.add(random.nextInt(25 - 1) + "." + random.nextInt(12 - 1) + "." + random.nextInt(2000 - 1000));
                        }
                        case "TimeColumn" -> {
                            someRow.add(random.nextInt(23) + ":" + random.nextInt(59) + ":" + random.nextInt(59));
                        }
                        case "GPSColumn" -> {
                            someRow.add(random.nextInt(diff) + "." + random.nextInt(100) + ";" + random.nextInt(diff) + "." + random.nextInt(100));
                        }
                    }
                }
                table.addStringRow(someRow);
                someRow.clear();
            }
            table.showTableInGUI(tableModel);
        });

        saveFile.addActionListener(e -> {
            try {
                table.saveToFile(fileName.getText());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        loadFile.addActionListener(e -> {
            try {
                Table table2 = table.loadFromFile(fileName.getText());
                table2.showTableInGUI(tableModel);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        sortTable.addActionListener(e -> {
            table.sort(Integer.parseInt(id1.getText()));
            table.showTableInGUI(tableModel);
        });

        sumColumn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table.addRow(table.sumRows(Integer.parseInt(id1.getText()), Integer.parseInt(id2.getText())));
                table.showTableInGUI(tableModel);
            }
        });

    }
    public static void main(String[] args){
        Main app = new Main();
        app.setVisible(true);
        app.pack();
    }
}
