package com.example.graphi;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Controller {

    private int root;
    private Circle[] nodes;
    private IShortestPathAlgorithm dijkstraAlgorithm;
    private IGraphTraversalAlgorithm bfsAlgorithm;
    private IGraph graph;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TextField textHeight;

    @FXML
    private TextField textWidth;

    @FXML
    private TextField textMin;

    @FXML
    private TextField textMax;

    @FXML
    private RadioButton bfs;

    @FXML
    private RadioButton dijkstra;

    @FXML
    private Label gradientMin;

    @FXML
    private Label gradientMax;

    @FXML
    private TextField programResponse;


    @FXML
    public void initialize() {
        textHeight.setFocusTraversable(false);
        textWidth.setFocusTraversable(false);
        textMin.setFocusTraversable(false);
        textMax.setFocusTraversable(false);
    }

    @FXML
    public void onGenerateClick() {
        programResponse.setText("");
        bfs.setSelected(false);
        dijkstra.setSelected(false);
        anchorPane.getChildren().clear();
        this.dijkstraAlgorithm = null;
        this.bfsAlgorithm = null;
        String heightString = textHeight.getText().trim();
        String widthString = textWidth.getText().trim();
        if (heightString.isEmpty() || widthString.isEmpty() || !hasOnlyNumbers(heightString) || !hasOnlyNumbers(widthString)
        || heightString.charAt(0) == '0' || widthString.charAt(0) == '0') {
            programResponse.setText("Wrong graph dimensions");
            return;
        }
        String minString = textMin.getText().trim();
        String maxString = textMax.getText().trim();
        if (minString.isEmpty() || maxString.isEmpty() || !hasOnlyNumbers(heightString) || !hasOnlyNumbers(widthString)
        || !hasNumbersAndDot(minString) || !hasNumbersAndDot(maxString)) {
            programResponse.setText("Wrong edge weight range");
            return;
        }
        int width = Integer.parseInt(widthString);
        int height = Integer.parseInt(heightString);
        double min = Double.parseDouble(minString);
        double max = Double.parseDouble(maxString);
        if(max < min) {
            programResponse.setText("Wrong edge weight range");
            return;
        }
        double scaledLength;
        if (width > height) {
            scaledLength = (double) 275 / (2 * width - 1);
        } else {
            scaledLength = (double) 275 / (2 * height - 1);
        }
        gradientMin.setText(minString);
        gradientMax.setText(maxString);
        this.graph = Generator.generate(height, width, min, max);
        generateNodes(width*height, scaledLength);
        IGraphDrawer graphDrawer = new GraphDrawer(this.nodes, this.graph);
        graphDrawer.drawGraph(min, max, scaledLength);
        graphDrawer.getNodeGroup().getChildren().addAll(graphDrawer.getEdgeGroup());
        graphDrawer.getEdgeGroup().toBack();
        anchorPane.getChildren().add(graphDrawer.getNodeGroup());
        programResponse.setText("Graph " + heightString + "x" + widthString + " " + minString + ":" + maxString
         + " has been generated.");
    }


    private boolean hasOnlyNumbers(String string) {
        for (int i = 0; i < string.length(); i++) {
            if (!Character.isDigit(string.charAt(i)))
                return false;
        }
        return true;
    }

    private boolean hasNumbersAndDot(String string) {
        boolean dot = false;
        for (int i = 0; i < string.length(); i++) {
            if (!Character.isDigit(string.charAt(i))) {
                if(dot) {
                    return false;
                } else {
                    dot = true;
                }
            }
        }
        return true;
    }

    private void onNodeLeftClick(int index) {
        if(this.bfs.isSelected()) {
            bfs(index);
        } else if (this.dijkstra.isSelected()) {
            dijkstra(index);
        }
    }

    private void bfs(int index) {
        this.bfsAlgorithm = new Bfs(this.graph);
        this.dijkstraAlgorithm = null;
        if(bfsAlgorithm.search(index)) {
            programResponse.setText("Graph is connected");
        } else {
            programResponse.setText("Graph is not connected");
        }
        this.root = index;
        double distance;
        double maxDistance = bfsAlgorithm.getMaxDistance();
        gradientMin.setText("0");
        gradientMax.setText(String.format("%.2f", maxDistance));
        for(int i = 0; i < graph.getHeight()* graph.getWidth(); i++) {
            if(graph.getNode(i).getPredecessor() == -1) {
                this.nodes[i].setFill(Color.GRAY);
                continue;
            }
            distance = bfsAlgorithm.getDistance(i);
            this.nodes[i].setFill(Color.hsb((250 * (maxDistance - distance)/maxDistance), 1, 1));
        }
    }

    private void dijkstra(int index) {
        dijkstraAlgorithm = new Dijkstra(this.graph);
        dijkstraAlgorithm.calculate(index);
        this.root = index;
        double distance;
        double maxDistance = dijkstraAlgorithm.getMaxDistance();
        gradientMin.setText("0");
        gradientMax.setText(String.format("%.2f", maxDistance));
        for(int i = 0; i < graph.getHeight()*graph.getWidth(); i++) {
            if(graph.getNode(i).getPredecessor() == -1) {
                this.nodes[i].setFill(Color.GRAY);
                continue;
            }
            distance = dijkstraAlgorithm.getDistance(i);
            this.nodes[i].setFill(Color.hsb((250 * (maxDistance - distance)/maxDistance), 1, 1));
        }
        programResponse.setText("Dijkstra from " + index + " completed");
    }

    private void onNodeRightClick(int index) {
        if(!dijkstra.isSelected()) {
            return;
        }
        if(dijkstraAlgorithm == null) {
            return;
        }
        int currentNode = index;
        if(graph.getNode(currentNode).getPredecessor() == -1) {
            programResponse.setText("Path from " + this.root + " to " + index + " doesn't exist");
            return;
        }
        while(currentNode != this.root) {
            nodes[currentNode].setFill(Color.WHITE);
            currentNode = graph.getNode(currentNode).getPredecessor();
        }
        nodes[currentNode].setFill(Color.WHITE);
        programResponse.setText("Path from " + this.root + " to " + index + ", distance: " +
                String.format("%.2f", dijkstraAlgorithm.getDistance(index)));
    }

    @FXML
    public void saveButtonClick() {
//        try {
////            int nodeNumber = graph.getWidth()*graph.getHeight();
////            File f = new File("test.txt");
////            FileOutputStream fos = new FileOutputStream(f);
////            PrintWriter pw = new PrintWriter(fos);
////            String string = "";
////            string += "\t" + graph.getHeight() + "\t" + graph.getWidth();
////            pw.write(string);
////            string = "";
////            INode node;
////            for(int i = 0; i < nodeNumber; i++) {
////                string += "\n";
////                node = graph.getNode(i);
////                for(int j = 0; j < node.getConnectionsAmount(); j++) {
////                    string += "\t\t" + node.getConnectedNode(j) + " :" + node.getConnectionWeight(j);
////                }
////                pw.write(string);
////                string = "";
////            }
////            pw.flush();
////            fos.close();
////            pw.close();
////        } catch (IOException ex) {
////            ex.printStackTrace();
////        }
    }

    private void generateNodes(int nodeNumber, double scaledLength) {
        this.nodes = new Circle[nodeNumber];
        for (int j = 0; j < nodeNumber; j++) {
            nodes[j] = new Circle();
            nodes[j].setId(String.valueOf(j));
            nodes[j].setFill(Color.GRAY);
            nodes[j].setRadius(scaledLength*(1 + Math.sqrt(nodeNumber)/100));
            nodes[j].setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    final javafx.scene.Node source = (javafx.scene.Node) mouseEvent.getSource();
                    String id = source.getId();
                    if(mouseEvent.getButton() == MouseButton.PRIMARY) {
                        onNodeLeftClick(Integer.parseInt(id));
                    } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                        onNodeRightClick(Integer.parseInt(id));
                    }
                }
            });
        }
    }
}