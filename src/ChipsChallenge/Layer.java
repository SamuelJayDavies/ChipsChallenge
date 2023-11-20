package ChipsChallenge;

public class Layer {
    public static void main(String[] args) {
        System.out.println("testing if i can commit Em");
    }
}
/**

//this should be added to the main class after
public class Layer extends Application {
    // Add a canvas for each layer
    private Canvas tileCanvas;
    private Canvas itemCanvas;
    private Canvas actorCanvas;

    // Method to initialize the tile layer
    //probably will be implemented later
    public void initTileLayer() {
    }
    
    public void initItemLayer() {
        
    }

    public void initActorLayer() {
        
    }
    //and this should be added to the primaryStage class after
    @Override
    public void start(Stage primaryStage) {
        //Three instances of the Layer class are created
        Layer tileLayer = new Layer();
        Layer itemLayer = new Layer();
        Layer actorLayer = new Layer();

        //this will be initialized later, theyre just here for now
        tileLayer.initTileLayer();
        itemLayer.initItemLayer();
        actorLayer.initActorLayer();

        // Create canvases for each layer
        tileLayer.tileCanvas = new Canvas();
        itemLayer.itemCanvas = new Canvas();
        actorLayer.actorCanvas = new Canvas();

        // Set dimensions for each canvas from the Main class
        tileLayer.tileCanvas.setWidth(Main.CANVAS_WIDTH);
        tileLayer.tileCanvas.setHeight(Main.CANVAS_HEIGHT);

        itemLayer.itemCanvas.setWidth(Main.CANVAS_WIDTH);
        itemLayer.itemCanvas.setHeight(Main.CANVAS_HEIGHT);

        actorLayer.actorCanvas.setWidth(Main.CANVAS_WIDTH);
        actorLayer.actorCanvas.setHeight(Main.CANVAS_HEIGHT);

        // The canvases for each layer are added as children to this pane. 
        Pane root = new Pane();
        root.getChildren().addAll(tileLayer.tileCanvas, itemLayer.itemCanvas, actorLayer.actorCanvas);

        // Display the scene on the stage
	//set up the main scene for the javafx application
        primaryStage.setScene(new Scene(root, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT));
        primaryStage.show();
    }
}
*/
