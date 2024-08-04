
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TextEditor {

    public static void main(String[] args) {
        DoublyLinkedList<String> linesList = new DoublyLinkedList<>();
        Scanner input = new Scanner(System.in);
        String fileName = "data.txt";
        String userInput = "";
        while (true) {
            userInput = input.next();
            if (userInput.equals("1")) {
                linesList.moveFirst();
            } else if (userInput.equals("a")) {
                input.nextLine();
                userInput = input.nextLine();
                while (!userInput.equals(".")) {
                    linesList.add(userInput);
                    userInput = input.nextLine();
                }
            } 
            
            else if (userInput.equals("d")) {
                linesList.remove(linesList.currentNode.data);
            } 
            
            else if (userInput.equals("dr")) {
                int fromLine = Integer.parseInt(input.next());
                int toLine = Integer.parseInt(input.next());
                while (toLine >= fromLine) {
                    linesList.removeAt(toLine);
                    toLine--;
                }
            } 
            
            else if (userInput.equals("f")) {
                fileName = input.next();
            }
            
            else if (userInput.equals("g")) {
                linesList.moveTo(Integer.parseInt(input.next()));
            } 
            
            else if (userInput.equals("h")) {
                System.out.println("command         function");
                System.out.println("----------------------------------------------");
                System.out.println("1                    Go to the top");
                System.out.println("a                    Add text after the current line until . on its own line");
                System.out.println("d                    Delete current line");
                System.out.println("dr num num           Delete several lines");
                System.out.println("f name               Change name of the current file (for next write)");
                System.out.println("g num                Go to a numbered line");
                System.out.println("h                    Get help");
                System.out.println("i                    Like append, but add lines before current line");
                System.out.println("m num                Move current line after some other line");
                System.out.println("m num num num        Move several lines as a unit after some other line");
                System.out.println("n                    Toggle whether line numbers are displayed");
                System.out.println("p                    Print current line");
                System.out.println("pr num num           Print several lines");
                System.out.println("q!                   Abort without write");
                System.out.println("r name               Read and paste another file into into the current file");
                System.out.println("s text text          Subtitute text with other text");
                System.out.println("t num                Copy current line to after some other line");
                System.out.println("t num num num        Copy several lines to after some other line");
                System.out.println("w                    Write file to disk");
                System.out.println("x!                   Exit with write");
                System.out.println("$                    Go to the last line");
                System.out.println("-                    Go to up one line");
                System.out.println("+                    Go to down one line");
                System.out.println("=                    Print current line number");
                System.out.println("/ text               Search forward for a pattern");
                System.out.println("?                    Search backward for a pattern");
                System.out.println("#                    Print number of lines and characters in file");
            } 
            
            else if (userInput.equals("i")) {
                input.nextLine();
                userInput = input.nextLine();
                while (!userInput.equals(".")) {
                    linesList.add(userInput, linesList.getNodeIndex(linesList.currentNode.data));
                    userInput = input.nextLine();
                }
            } 
            
            else if (userInput.equals("m")) {
                String data = linesList.currentNode.data;
                linesList.remove(data);
                linesList.add(data, Integer.parseInt(input.next()) + 1);
            } 
            
            else if (userInput.equals("mr")) {
                int fromLine = Integer.parseInt(input.next());
                int toLine = Integer.parseInt(input.next());
                int afterLine = Integer.parseInt(input.next());
                int index = 1, line = fromLine;
                while (fromLine <= toLine) {
                    String data = linesList.getData(line);
                    linesList.remove(data);
                    linesList.add(data, afterLine + index);
                    fromLine++;
                    index++;
                }
            } 
            
            else if (userInput.equals("n")) {
                
            } 
            
            else if (userInput.equals("p")) {
                if (linesList.currentNode != null && linesList.currentNode.data != null) {
                    System.out.println(linesList.currentNode.data);
                }
                
            } 
            
            else if (userInput.equals("pr")) {
                int fromLine = Integer.parseInt(input.next());
                int toLine = Integer.parseInt(input.next());
                while (fromLine <= toLine) {
                    String data = linesList.getData(fromLine);
                    if (data != null)
                        System.out.println(data);
                    fromLine++;
                }
            }
            
            else if (userInput.equals("q!")) {
                System.exit(0);
            }
            
            else if (userInput.equals("r")) {
                linesList.clear();
                try (BufferedReader br = new BufferedReader(new FileReader(new File(input.next())))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        linesList.add(line);
                    }
                }
                catch(IOException e){}
            }
            
            else if (userInput.equals("s")) {
                Node<String> node = linesList.getFirst();
                while(node != null)
                {
                    if(node.data == null)
                        break;
                    String text1 = input.next() , text2 = input.next();
                    if(node.data.contains(text1))
                        node.data.replaceAll(text1, input.next());
                    node = node.next;
                }
            }
            
            else if (userInput.equals("t")) {
                linesList.add(linesList.currentNode.data, Integer.parseInt(input.next()) + 1);
            }
            
            else if (userInput.equals("tr")) {
                int fromLine = Integer.parseInt(input.next());
                int toLine = Integer.parseInt(input.next());
                int afterLine = Integer.parseInt(input.next());
                int index = 1;
                while (fromLine <= toLine) {
                    String data = linesList.getData(fromLine);
                    linesList.add(data, afterLine + index);
                    fromLine++;
                    index++;
                }
            }
            
            else if (userInput.equals("w")) {
                Node<String> node = linesList.getFirst();
                FileOutputStream fos = null;
                try {
                    File fout = new File(fileName);
                    fos = new FileOutputStream(fout);
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
                    while(node != null) {
                        bw.write(node.data);
                        bw.newLine();
                        node = node.next;
                    }   
                    bw.close();
                } catch (Exception ex) {} 
            }
            
            else if (userInput.equals("x!")) {
                Node<String> node = linesList.getFirst();
                FileOutputStream fos = null;
                try {
                    File fout = new File(fileName);
                    fos = new FileOutputStream(fout);
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
                    while(node != null) {
                        bw.write(node.data);
                        bw.newLine();
                        node = node.next;
                    }   
                    bw.close();
                } catch (Exception ex) {} 
                System.exit(0);
            }
            
            else if (userInput.equals("$")) {
                linesList.moveLast();
            }
            
            else if (userInput.equals("-")) {
                linesList.prev();
            }
            
            else if (userInput.equals("+")) {
                linesList.next();
            }
            
            else if (userInput.equals("=")) {
                System.out.println(linesList.getNodeIndex(linesList.currentNode.data));
            }
            
            else if (userInput.equals("/")) {
                Node<String> node = linesList.currentNode;
                while(node != null)
                {
                    if(node.data.matches(input.next()))
                    {
                        System.out.println(linesList.getNodeIndex(node.data));
                        break;
                    }
                    node = node.next;
                }
            }
            
            else if (userInput.equals("?")) {
                Node<String> node = linesList.currentNode;
                while(node != null)
                {
                    if(node.data.matches(input.next()))
                    {
                        System.out.println(linesList.getNodeIndex(node.data));
                        break;
                    }
                    node = node.prev;
                }
            }
            
            else if (userInput.equals("#")) {
                Node<String> node = linesList.getFirst();
                int lines = 0 , chars = 0;
                while(node != null)
                {
                    if(node.data == null)
                        break;
                    chars += node.data.length();
                    lines++;
                    node = node.next;
                }
                System.out.println("lines : " + lines + "   characters : " + chars);
            }
        }
    }

}

/*****************************************************************************************************/

class Node<AnyType> {

    public AnyType data;
    public Node<AnyType> next;
    public Node<AnyType> prev;

    public Node(AnyType data, Node<AnyType> next, Node<AnyType> prev) {
        this.data = data;
        this.next = next;
        this.prev = prev;
    }
}

/*****************************************************************************************************/

class DoublyLinkedList<Type> {

    Node<Type> currentNode;

    public DoublyLinkedList() {
        currentNode = new Node<>(null, null, null);
    }

    public void add(Type data) {
        if (currentNode.data == null && currentNode.next == null && currentNode.prev == null) {
            currentNode.data = data;
        } else {
            currentNode.next = new Node<>(data, null, null);
            currentNode.next.prev = currentNode;
            currentNode = currentNode.next;
        }
    }

    public void add(Type data, int line) {
        Node<Type> node = getFirst();
        int index = 1;
        while (node.next != null && index != line) {
            node = node.next;
            index++;
        }
        Node<Type> newNode = new Node<>(data, null, null);
        if (node == null || line != index) {
            add(data);
            return;
        } 
        else if (node.prev != null) {
            node.prev.next = newNode;
            newNode.prev = node.prev;
        }
        node.prev = newNode;
        newNode.next = node;
        currentNode = newNode;
    }

    public void moveFirst() {
        while (currentNode.prev != null) {
            currentNode = currentNode.prev;
        }
    }

    public Node<Type> getFirst() {
        Node<Type> first = currentNode;
        while (first.prev != null) {
            first = first.prev;
        }
        return first;
    }

    public void moveLast() {
        while (currentNode.next != null) {
            currentNode = currentNode.next;
        }
    }

    public void moveTo(int index) {
        int currentIndex = 1;
        currentNode = getFirst();
        while (currentIndex < index && currentNode.next != null) {
            currentIndex++;
            currentNode = currentNode.next;
        }
    }

    public Node<Type> getLast() {
        Node<Type> first = currentNode;
        while (first.next != null) {
            first = first.next;
        }
        return first;
    }

    public void next() {
        if(currentNode.next != null)
            currentNode = currentNode.next;
    }

    public void prev() {
        if(currentNode.prev != null)
            currentNode = currentNode.prev;
    }

    public void removeAt(int index) {
        int currentIndex = 1;
        Node<Type> node = getFirst();
        while (currentIndex != index && currentIndex < index && node.next != null) {
            currentIndex++;
            node = node.next;
        }
        if (node.data != null) {
            remove(node.data);
        }
    }

    public void remove(Type data) {
        if (currentNode.data != null && currentNode.data.equals(data)) {
            if (currentNode.prev != null) 
                currentNode.prev.next = currentNode.next;
            if (currentNode.next != null)
                currentNode.next.prev = currentNode.prev;
            
            if (currentNode.prev == null && currentNode.next != null)
                currentNode = currentNode.next;
            else if(currentNode.prev != null)
                currentNode = currentNode.prev;
            else
                currentNode.data = null;
        } else {
            Node<Type> node = getFirst();
            while (node != null && !node.data.equals(data)) {
                node = node.next;
            }
            if (node == null) {
                return;
            }
            if (node.prev != null) {
                node.prev.next = node.next;
            }
            if (node.next != null) {
                node.next.prev = node.prev;
            }
        }
    }

    public int getNodeIndex(Type data) {
        int index = 1;
        Node<Type> node = getFirst();
        while (node != null) {
            if (node.data.equals(data)) {
                return index;
            }
            index++;
            node = node.next;
        }
        return index;
    }

    public Type getData(int index) {
        int currentIndex = 1;
        Node<Type> node = getFirst();
        while (node != null) {
            if (currentIndex == index) {
                return node.data;
            }
            currentIndex++;
            node = node.next;
        }
        return null;
    }
    
    public void clear()
    {
        moveFirst();
        while(currentNode.data != null)
            remove(currentNode.data);
    }
}
