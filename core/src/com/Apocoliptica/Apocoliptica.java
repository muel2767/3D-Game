package com.Apocoliptica;

import MAP.Map;
import com.Apocoliptica.PLAYER.Bullet;
import com.Apocoliptica.PLAYER.Player;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
//import static com.badlogic.gdx.math.Matrix4.tmp;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.UBJsonReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Apocoliptica extends ApplicationAdapter implements InputProcessor
{
    //private PerspectiveCamera camera;
    ModelBatch modelBatch;
    //private Model box;
    ModelInstance boxInstance;
    Environment environment;
    Model testBox, box;
    Player player;
    Map map;
    ArrayList<ModelInstance> drawableModels=new ArrayList<ModelInstance>();
    int x,y,z=0;
    int mouseX,mouseY;
    float rotSpeed = 0.2f;
    float tempY;
    AnimationController controller;
    Model zombie;
    ModelInstance M;
    Bullet b;
    boolean move=false;
    Sprite cross;
    SpriteBatch batch;

    @Override
   public void create() {
   // Create camera sized to screens width/height with Field of View of 75 degrees
        Gdx.graphics.setDisplayMode(Gdx.graphics.getDesktopDisplayMode().width, Gdx.graphics.getDesktopDisplayMode().height, true);
        Gdx.input.setInputProcessor(this);
        
        batch=new SpriteBatch();
        Texture t=new Texture(Gdx.files.internal("cross.png"));
        cross=new Sprite(t);
        player=new Player();   
        cross.setPosition(Gdx.graphics.getWidth()/2-25, Gdx.graphics.getHeight()/2-25);
        Gdx.input.setCursorCatched(true);
      // Model loader needs a binary json reader to decode
      UBJsonReader jsonReader = new UBJsonReader();
      // Create a model loader passing in our json reader
      G3dModelLoader modelLoader = new G3dModelLoader(jsonReader);
      testBox= modelLoader.loadModel(Gdx.files.getFileHandle("testBox.g3db", FileType.Internal));
      box=modelLoader.loadModel(Gdx.files.getFileHandle("testBox.g3db", FileType.Internal));
      // Move the camera 3 units back along the z-axis and look at the origin
      map=new Map(player);
      player.setMap(map);
      // A ModelBatch is like a SpriteBatch, just for models.  Use it to batch up geometry for OpenGL
      modelBatch = new ModelBatch();

      // A model holds all of the information about an, um, model, such as vertex data and texture info
      // However, you need an instance to actually render it.  The instance contains all the 
      // positioning information ( and more ).  Remember Model==heavy ModelInstance==Light
      Vector3 v3=new Vector3(0,0,0);
      boxInstance = new ModelInstance(testBox, v3);
      drawableModels.add(boxInstance);
      v3=new Vector3(2,0,0);
      boxInstance = new ModelInstance(testBox, v3);
      drawableModels.add(boxInstance);
      v3=new Vector3(2,2,0);
      boxInstance = new ModelInstance(testBox, v3);
      drawableModels.add(boxInstance);
      // Finally we want some light, or we wont see our color.  The environment gets passed in during
      // the rendering process.  Create one, then create an Ambient ( non-positioned, non-directional ) light.
      environment = new Environment();
      environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.8f, 0.8f, 0.8f, 1.0f));
        mouseX = Gdx.input.getX();
        mouseY = Gdx.input.getY();
        boolean lastMoveUp=false;
        tempY=player.camera.direction.y;
        Gdx.input.setCursorPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        //Gdx.input.setCursorCatched(true);
        // Create a model loader passing in our json reader
        //zombie = modelLoader.loadModel(Gdx.files.getFileHandle("Monster/test.g3db", FileType.Internal));
        //M = new ModelInstance(zombie);
         // You use an AnimationController to um, control animations.  Each control is tied to the model instance
        //controller = new AnimationController(M);  
        // Pick the current animation by name
        //controller.setAnimation("move",-1);
//        controller.setAnimation("Walk",1, new AnimationListener(){
//
//            @Override
//            public void onEnd(AnimationDesc animation) {
//                // this will be called when the current animation is done. 
//                // queue up another animation called "balloon". 
//                // Passing a negative to loop count loops forever.  1f for speed is normal speed.
//                controller.queue("Walk",-1,1f,null,0f);
//            }
//
//            @Override
//            public void onLoop(AnimationDesc animation) {
//                // TODO Auto-generated method stub
//                
//            }
//            
//        });
        b=new Bullet(player);
        b.count=-1;
        player.cameraShift();
   }
   
 
   @Override
   public void dispose() 
   {
        testBox.dispose();
        modelBatch.dispose();
   }
 
   @Override
   public void render() 
   {
      // You've seen all this before, just be sure to clear the GL_DEPTH_BUFFER_BIT when working in 3D
      Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
      Gdx.gl.glClearColor(1, 1, 1, 1);
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
      //int id=player.a;
      //System.out.println(player.camera.up.x+"       "+player.camera.up.y);
      // For some flavor, lets spin our camera around the Y axis by 1 degree each time render is called
        if(Gdx.input.isKeyJustPressed(Keys.M))
        {
            System.out.println(player.camera.direction.x);
            System.out.println(player.camera.direction.y);
            System.out.println(player.camera.direction.z);
            
        }
      
      else if(Gdx.input.isKeyPressed(Keys.W) && player.canMove())
      {
        player.move('w');
        if(Gdx.input.isKeyJustPressed(Keys.SPACE))
        {
            player.move(' ');
        }
      }
      else if(Gdx.input.isKeyPressed(Keys.S) && player.canMove())
      {
        player.move('s');
        if(Gdx.input.isKeyJustPressed(Keys.SPACE))
        {
            player.move(' ');
        }
      }
      else if(Gdx.input.isKeyPressed(Keys.A) && player.canMove())
      {
        player.move('a');
        if(Gdx.input.isKeyJustPressed(Keys.SPACE))
        {
            player.move(' ');
        }
      }
      else if(Gdx.input.isKeyPressed(Keys.D) && player.canMove())
      {
        player.move('d');
        if(Gdx.input.isKeyJustPressed(Keys.SPACE))
        {
            player.move(' ');
        }
      }
      else if(Gdx.input.isKeyJustPressed(Keys.SPACE))
      {
          player.move(' ');
      }
      else if(Gdx.input.isKeyJustPressed(Keys.Y))
      {
          player.move('y'); 
          //player.change();
      }
      
      // When you change the camera details, you need to call update();
      // Also note, you need to call update() at least once.
      //checkCamera();
        //player.cameraShift();
        player.camera.update();
        //System.out.println(player.x+"      "+ player.y+"     "+ player.z);
        // Like spriteBatch, just with models!  pass in the box Instance and the environment
        modelBatch.begin(player.camera);
                                    //        if(b.count>=0 )
                                    //            b.draw(modelBatch, environment);
        map.draw(modelBatch, environment/*, drawableModels*/);
        if(Gdx.input.isKeyJustPressed(Keys.F))
        {
            b=new Bullet(player);
            player.fire(5, b, modelBatch, environment); 
          //player.change();
            player.respawn();
        }
        //if(Gdx.input.isKeyJustPressed(Keys.L))
        //{
            if(b.count>=0)
            {
//                System.out.println(b.x+"      "+b.y+"       "+b.z);
                b.move(modelBatch, environment);
//                float tempX=b.x;
//                if(b.x%1 >.95)
//                {
//                    tempX+=(1-b.x%1);
//                }
//                float tempY=b.y;
//                if(b.y%1 >.86)
//                {
//                    tempY+=(1-b.y%1);
//                }
//                System.out.println((int)tempX+"      "+(int)tempY+"       "+(int)b.tempZ);
//                
//                if(map.world[(int)tempX][(int)tempY][(int)b.tempZ]=='b')
//                {
//                    b.count=-1;
//                    map.world[(int)tempX][(int)tempY][(int)b.tempZ]=' ';
//                }
                System.out.println(b.position.x+"     "+b.position.y+"      "+b.position.z);
                if(map.world[(int)b.position.x][(int)b.position.y][(int)b.position.z]=='b')
                {
                    b.count=-1;
                    map.world[(int)b.position.x][(int)b.position.y][(int)b.position.z]=' ';
                }
                //player.tmp.set(player.camera.direction).crs(player.camera.up).nor();
                
                //if(b.count>=0 )
                b.draw(modelBatch, environment);
                try {
                  Thread.sleep(150);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Apocoliptica.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //else 
            //    player.respawn();
        //}

        if(Gdx.input.isKeyJustPressed(Keys.Q))
        {
            Gdx.app.exit();
        }
        if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT) && Gdx.input.justTouched())
        {
            //player.setAllDir();
            map.destroy();
        }
        if(map.world[(int)(player.x)][(int)(player.y)][(int)(player.z-.2)]==' ')
        {
            if(   (map.world[(int)(player.x)][(int)(player.y+.5)][(int)(player.z-.2)]!=' ' && player.y%1!=0)  || (map.world[(int)(player.x+.5)][(int)(player.y)][(int)(player.z-.2)]!=' ' && player.x%1!=0))
            {
                
            }
            else
            {
                player.gravity();
            }
        }
        modelBatch.end();
        
        //sprites rendered over the 3D world
        batch.begin();
        cross.draw(batch);
        batch.end();
    }

   public void checkCamera()
   {

   }
   
    @Override
    public boolean keyDown(int i) 
    {
        return false;
    }

    @Override
    public boolean keyUp(int i) 
    {
        return false;
    }

    @Override
    public boolean keyTyped(char c) 
    {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) 
    {
        
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) 
    {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int i2) 
    {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) 
    {
//        if(Math.abs(player.camera.direction.z)>.95)
//            return false;
        if(move==true)//(Gdx.input.getX()==Gdx.graphics.getWidth()/2 && Gdx.input.getY()==Gdx.graphics.getHeight()/2)
        {
            move=false;
            //player.cameraShift();
            return false;
        }
       	float deltaX = -Gdx.input.getDeltaX() * player.degreesPerPixel;
	float deltaY = -Gdx.input.getDeltaY() * player.degreesPerPixel;
        //Gdx.input.setCursorPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        //Gdx.input.
        if(deltaX>0)
        {
            player.camera.rotate(Vector3.Z, (float)2.5);
//            if(Math.abs(player.camera.direction.z)>.9)
//                player.camera.rotate(Vector3.Z, (float)-2.5);
        }
        else if(deltaX<0)
            player.camera.rotate(Vector3.Z, (float)-2.5);
        //player.camera.direction.rotate(player.camera.up, deltaX);
	player.tmp.set(player.camera.direction).crs(player.camera.up).nor();
        if(deltaY>0)
            player.camera.direction.rotate(player.tmp, (float)2.5);
        else if(deltaY<0)
            player.camera.direction.rotate(player.tmp, (float)-2.5);
        else
            player.camera.direction.rotate(player.tmp, 0);
            //System.out.println(player.camera.direction.x+"     "+player.camera.direction.y+"     "+player.camera.direction.z);
        if(player.camera.direction.z>.75)
            undoCameraZ(-2.5f);
        else if(player.camera.direction.z<-.85)
            undoCameraZ(2.5f);
        player.setDir(player.camera.direction.x, player.camera.direction.y);
        Gdx.input.setCursorPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        move=true;
        player.cameraShift();
        return true;
    }

    public void undoCameraZ(float over)
    {
        if(over>0)
            player.camera.direction.rotate(player.tmp, over);
        else if(over<0)
            player.camera.direction.rotate(player.tmp, over);
        
        player.tmp.set(player.camera.direction).crs(player.camera.up).nor();
       
        if(over>0)
            player.camera.direction.rotate(player.tmp, over);
        else if(over<0)
            player.camera.direction.rotate(player.tmp, over);
//        player.camera.direction.z=.75f;
////        player.camera.rotate(Vector3.Z, over);
////
//        //player.camera.direction.rotate(player.camera.up, deltaX);
//	player.tmp.set(player.camera.direction).crs(player.camera.up).nor();
//        player.camera.direction.rotate(player.tmp, 0);
//        //player.setDir(player.camera.direction.x, player.camera.direction.y);
//        Gdx.input.setCursorPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        //player.cameraShift();
    }
    
    @Override
    public boolean scrolled(int i) 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}