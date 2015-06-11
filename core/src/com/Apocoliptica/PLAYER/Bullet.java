/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Apocoliptica.PLAYER;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import static com.badlogic.gdx.graphics.Color.tmp;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.UBJsonReader;

/**
 *
 * @author michael
 */
public class Bullet 
{
    public float x,y,z;
    public double angle, ax, ay, az, tempZ;
    UBJsonReader jsonReader = new UBJsonReader();
    // Create a model loader passing in our json reader
    G3dModelLoader modelLoader = new G3dModelLoader(jsonReader);
    Model shot = modelLoader.loadModel(Gdx.files.getFileHandle("Bullet.g3db", Files.FileType.Internal));
    public int count;
    ModelInstance temp;
    Player player;
    String facing;
    public Vector3 position;
    public float[] speed;
    public Vector3 tmp=new Vector3();
    public Bullet(Player p)
    {
        player=p;
        facing =player.setAllDir();//player.facing;
//        x=p.x;
//        y=p.y;
//        z=p.z;
        
        System.out.println(facing);
//        ax=player.camera.direction.x;
//        ay=player.camera.direction.y;
//        az=player.camera.direction.z;
        ax=player.camera.direction.x-player.neutX;//-player.camera.up.x;      //-player.neutX-player.camera.up.x;      //ax=player.camera.up.x-player.neutX;
        ay=player.camera.direction.y;//-player.neutY;//-player.camera.up.y;      //-player.neutY-player.camera.up.y;              //ay=player.camera.up.y-player.neutY;
        az=player.camera.direction.z-player.neutZ;//-player.camera.direction.z-player.neutZ;       //-player.neutZ-player.camera.direction.z;
        //angle=p.getCameraRotation();//-p.fy;
        //System.out.println(angle);
        
        //-(float)Math.atan2(camera.up.x, camera.up.y)*MathUtils.radiansToDegrees + 180
        temp=new ModelInstance(shot, new Vector3(x, y, z));
        //temp.transform.translate(p.camera.direction.x, p.camera.direction.y, p.camera.direction.z);
        //temp.transform.translate(p.camera.unproject(new Vector3(p.camera.direction.x, p.camera.direction.y, p.camera.direction.z)));
        //temp.transform.translate(Vector3.Zero)
        count=8;
        x=player.x;
        y=player.y;
        z=(int)player.z;
        
//        if(facing.equalsIgnoreCase("NW"))
//        {
//
//        }
//        else if(facing.equalsIgnoreCase("N"))
//        {
//            ax=player.camera.direction.x;
//            ay=player.camera.direction.y;
//            az=player.camera.direction.z;
//            if(az>=-0.02409 && az<-.024)
//            {
//                az=0;
//            }
//            else 
//            {
//                if(player.camera.direction.z>=.19)           
//                {
//                    az+=.8;
//                    
//                }
//                else if(player.camera.direction.z<=-.5)     
//                {
//                    az-=.2;
//                }
//            }
//            if(ax>=-.0179 && ax<.005)
//                ax=0;
//            System.out.println(ax+"     "+ay+"      "+az+"  THE ANGLES");
//        }
//        else if(facing.equalsIgnoreCase("NE"))
//        {
//
//        }
//        else if(facing.equalsIgnoreCase("E"))
//        {
//
//        }
//        else if(facing.equalsIgnoreCase("W"))
//        {
//
//        }
//        else if(facing.equalsIgnoreCase("SW"))
//        {
//
//        }
//        else if(facing.equalsIgnoreCase("S"))
//        {
//
//        }
//        else if(facing.equalsIgnoreCase("SE"))
//        {
//
//        }
        position=new Vector3(x,y,z);
        speed=new float[] {player.camera.direction.x, player.camera.direction.y, player.camera.direction.z};
    }
    
    public void move(ModelBatch MB, Environment enviroment)
    {
        if(facing.equals("N"))
        { 
            position.add(tmp.set(speed).scl(1));
//            x+=(ax+.1)*.9;
//            //x+=ax;
//            y+=ay;
//            z+=az;//*.9;//(az* (106.0/100.0));
//            tempZ=z;
//            if(tempZ%1>0 && tempZ<1)
//            {
//                tempZ++;
//                tempZ=(int)tempZ;
//            }
            if(position.z>0 && position.z<1) position.z=1;
        }
        else if(facing.equals("E"))
        { 
            x+=(ax+.1)*.9;
            y+=ay+1.05;
            z+=az*.9;//(az* (106.0/100.0));
            tempZ=z;
            if(tempZ%1>0 && tempZ<1)
            {
                tempZ++;
                tempZ=(int)tempZ;
            }
        }
        else if(facing.equals("W"))
        { 
            x+=(ax+.1)*.9;
            y+=ay+1.05;
            z+=az*.9;//(az* (106.0/100.0));
            tempZ=z;
            if(tempZ%1>0 && tempZ<1)
            {
                tempZ++;
                tempZ=(int)tempZ;
            }
        }
        else if(facing.equals("S"))
        { 
            x+=(ax+.1)*.9;
            y+=ay+1;
            z+=az*.9;//(az* (106.0/100.0));
            tempZ=z;
            if(tempZ%1>0 && tempZ<1)
            {
                tempZ++;
                tempZ=(int)tempZ;
            }
        }
        count--;
        if(position.x<0 || position.y<0 || position.z<0 ||position.x>100 || position.y>100 || position.z>100)
        {
            position.x=player.x;
            position.y=player.y;
            position.z=player.z;
            count=-1;
        }
    }
    public void draw(ModelBatch MB, Environment enviroment)
    {
//        //temp=new ModelInstance(shot);
//        if(facing.equals("N"))
//        {
            MB.render( temp , enviroment);
            temp=new ModelInstance(shot, position);//new Vector3(x, y, (float)z));
            //temp.transform.rotate(Vector3.X, -180);
            //temp.transform.rotate((float)ax, (float)ay, (float)az, (float) angle);
//        }
//        else if(facing.equals("W"))
//        {
//            MB.render( temp , enviroment);
//            temp=new ModelInstance(shot, new Vector3(x, y, (float)tempZ));
//            temp.transform.rotate(Vector3.Y, -90);
//            temp.transform.rotate((float)ax, (float)ay, (float)az, (float) angle);
//        }else if(facing.equals("E"))
//        {
//            MB.render( temp , enviroment);
//            temp=new ModelInstance(shot, new Vector3(x, y, (float)tempZ));
//            temp.transform.rotate(Vector3.Y, 90);
//            temp.transform.rotate((float)ax, (float)ay, (float)az, (float) angle);
//        }else if(facing.equals("S"))
//        {
//            MB.render( temp , enviroment);
//            temp=new ModelInstance(shot, new Vector3(x, y, (float)tempZ));
//            temp.transform.rotate(Vector3.X, -180);
//            temp.transform.rotate((float)ax, (float)ay, (float)az, (float) angle);
//        }
        
    }
}
