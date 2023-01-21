package org.s1gma.tutorial;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.io.FileFilter;
import java.util.Scanner;

public class Notepad extends JFrame implements ActionListener {
    private final JTextArea txt = new JTextArea();

    private JMenuBar newMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        String[] menuNames = {"File"};
        String[][] dropdownItems = {{"Open", "Save"}};
        for(int i = 0; i < menuNames.length; i++) {
            String menuItems = menuNames[i];
            String[] dropItems = dropdownItems[i];
            menuBar.add(newMenu(menuItems, dropItems));

        }
        return menuBar;
    }

    private JMenu newMenu(String title, String[] dropItems) {
        JMenu menu = new JMenu(title);
        for(String element : dropItems) {
            JMenuItem menuItems = new JMenuItem(element);
            menu.add(menuItems);
            menuItems.addActionListener(this);
        }
        return menu;
    }

    private Notepad() {
        setTitle("Untitled");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        setSize(1500, 800);
        setJMenuBar(newMenuBar());
        JScrollPane scroller = new JScrollPane(txt);
        add(scroller);
    }

    public static void main(String[] args) {
        new Notepad().setVisible(true);
    }


    public void actionPerformed(ActionEvent actionEvent) {
        String cmd = actionEvent.getActionCommand();
        if(cmd.equals("Save")) {
            JFileChooser chooser = new JFileChooser();
            int option = chooser.showSaveDialog(this);
            if(option == JFileChooser.APPROVE_OPTION) {
                try {
                    BufferedWriter buf = new BufferedWriter(
                            new FileWriter(chooser.getSelectedFile().getAbsolutePath())
                    );

                    buf.write(txt.getText());
                    setTitle(chooser.getSelectedFile().getName());
                    buf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if(cmd.equals("Open")) {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new Filter());
            int option = chooser.showOpenDialog(this);
            if(option == JFileChooser.APPROVE_OPTION) {
                try {
                    Scanner scanner = new Scanner(chooser.getSelectedFile());
                    while(scanner.hasNext()) {
                        String data = scanner.nextLine();
                        txt.setText(data);
                    }
                    setTitle(chooser.getSelectedFile().getName());
                    scanner.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Filter extends javax.swing.filechooser.FileFilter implements FileFilter {

        public boolean accept(File file) {
            return file.getName().endsWith(".txt") || file.getName().endsWith(".java");
        }

        @Override
        public String getDescription() {
            return "Text File (.txt)";
        }
    }
}