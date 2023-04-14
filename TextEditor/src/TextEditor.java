import com.sun.jdi.connect.Connector;

import javax.imageio.IIOException;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class TextEditor implements ActionListener {
    // declering data members or properties of texteditor
    //get the predefined in javax.swing
    JFrame  frame;
    // set the menu bar
    JMenuBar menuBar;

    // create the menus
    JMenu file,edit;

    // create the menu items

    // create file menu items
    JMenuItem  newFile,openFile, saveFile;
    // create  edit menu items
    JMenuItem undo,redo,copy,cut,paste,selectAll,close;

    // create undo manager


    // create a textarea
    JTextArea textArea;


  // create undomanger whcih collects all the edits happend through the buttons
    UndoManager undoManager = new UndoManager();


    TextEditor(){
        // initialize the frame
        frame = new JFrame();

        // intialize the menubar
        menuBar = new JMenuBar();

        // intialize the textarea
        textArea  = new JTextArea();

        // intialize the menus
        file = new JMenu("File");
        edit = new JMenu("Edit");

        newFile = new JMenuItem("new Window");
        openFile = new JMenuItem("open");
        saveFile = new JMenuItem("save File");

        // add action listeners to file menu items
        newFile.addActionListener(this);
        openFile.addActionListener(this);
        saveFile.addActionListener(this);

        // add file menu items to file menu
        file.add(newFile);
        file.add(openFile);
        file.add(saveFile);

        // intialize edit menu items
        undo = new JMenuItem("Undo");
        redo = new JMenuItem("Redo");
        copy = new JMenuItem("Copy");
        cut = new JMenuItem("Cut");
        paste = new JMenuItem("Paste");
        selectAll = new JMenuItem("Select All");
        close = new JMenuItem("Close");

        // add the action listeners to edit menu items
        undo.addActionListener(this);
        redo.addActionListener(this);
        copy.addActionListener(this);
        cut.addActionListener(this);
        paste.addActionListener(this);
        selectAll.addActionListener(this);
        close.addActionListener(this);

        // add edit menu items to edit menu;

        edit.add(undo);
        edit.add(redo);
        edit.add(copy);
        edit.add(cut);
        edit.add(paste);
        edit.add(selectAll);
        edit.add(close);





        // add menus to menubar
        menuBar.add(file);
        menuBar.add(edit);

      // set menubar to frame
        frame.setJMenuBar(menuBar);

        textArea.getDocument().addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                undoManager.addEdit(e.getEdit());
            }
        });
     // create  content panel
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(5,5,5,5));
        panel.setLayout(new BorderLayout(0,0));
        // add the text area to the panel
        panel.add(textArea, BorderLayout.CENTER);
        // creat scroll pane
        JScrollPane scrollPane = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        // add the scroll pane to the panel
        panel.add(scrollPane);
        // add panel to frame
        frame.add(panel);
        // set dimensions of frame
        frame.setBounds(100,100,400,400);
        frame.setTitle("Text Pad");
        frame.setVisible(true);
        frame.setLayout(null);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent){


        if(actionEvent.getSource()==undo){

            try{
                undoManager.undo();
            }
            catch (Exception e){

            }
        }

        if(actionEvent.getSource()==redo){
            try {
                undoManager.redo();
            }
            catch (Exception e){

            }
        }



        if(actionEvent.getSource()==copy){

            // performs copy operation
            textArea.copy();
        }

        if(actionEvent.getSource()==cut){
            // performs cut operation
            textArea.cut();
        }
        if(actionEvent.getSource()==paste){
            // performs paste operation
            textArea.paste();
        }
        if(actionEvent.getSource()==selectAll){
            // performs select all operation
            textArea.selectAll();
        }
        if(actionEvent.getSource()==close){
            // performs closing editor operation
            System.exit(1);
        }
        if(actionEvent.getSource()==openFile){
            // open a file chooser
            JFileChooser fileChooser = new JFileChooser("C:");
            int chooseOption = fileChooser.showOpenDialog(null);

            // if we clicked on open button
            if(chooseOption == JFileChooser.APPROVE_OPTION){
                // getting the selected file
                File file = fileChooser.getSelectedFile();
                // get the path of selected file
                String filePath = file.getPath();

                try{
                    // intialize the file reader
                    FileReader fileReader = new FileReader(filePath);

                    // intilize the buffered reader
                    BufferedReader bufferedReader = new BufferedReader(fileReader);


                    String intermediate="", output="";
                    // reading all the content of file line by line
                    while ((intermediate=bufferedReader.readLine())!=null){
                        output+=intermediate+"\n";
                    }
                    // set the output string to text area
                    textArea.setText(output);

                }
                catch (IOException ioException){
                    ioException.printStackTrace();
                }

            }

        }

        if(actionEvent.getSource()==saveFile){
            // intilize the file picer
            JFileChooser fileChooser = new JFileChooser("C:");

            // get choose option frome file chooser
            int chooseOption = fileChooser.showSaveDialog(null);
            // check we clicked on save button
            if(chooseOption==JFileChooser.APPROVE_OPTION){

                // create a new file with choosen directory path and file name
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath()+".txt");
                try {
                    // intilize the file writer
                    FileWriter fileWriter = new FileWriter(file);
                    // Intialize the bufferd writer
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    // write contents of text area to file
                    textArea.write(bufferedWriter);
                    bufferedWriter.close();


                }
                catch (IOException ioException){
                        ioException.printStackTrace();
                }
            }
        }

        if(actionEvent.getSource()==newFile){
            TextEditor newTextEditor = new TextEditor();
        }

    }
    public static void main(String[] args) {

        // create a instance for texteditor
        TextEditor textEditor = new TextEditor();



        }

}
