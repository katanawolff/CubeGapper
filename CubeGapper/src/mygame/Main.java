package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    GameManager gm;
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        
        Node playerModel = new Node();
        
        Box pBox = new Box(1, 1, 1);
        Geometry pgeom = new Geometry("Box", pBox);

        Material pmat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        pmat.setColor("Color", ColorRGBA.Blue);
        pgeom.setMaterial(pmat);
        
        playerModel.setLocalTranslation(0, -1, 0);

        playerModel.attachChild(pgeom);
        flyCam.setEnabled(false);
        
        gm = new GameManager(assetManager, playerModel, rootNode, inputManager, cam, guiNode, guiFont);
        FilterPostProcessor processor 
                = (FilterPostProcessor) assetManager.loadAsset("Shaders/Cartoon.j3f");
        viewPort.addProcessor(processor);
    }

    @Override
    public void simpleUpdate(float tpf) {
        gm.updateScene(tpf);
        gm.updateCamera();
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
