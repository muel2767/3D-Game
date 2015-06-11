/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MAP;

import com.Apocoliptica.PLAYER.Player;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.UBJsonReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author michael
 */
public class Map 
{
    public char [][][] world;
    Player player;
   public int worldX, worldY, worldZ;
    final int size=2;
    final int view=25;
    int shift;
    public Map(Player p)
    {
        player=p;
        worldX=1024/6;
        worldY=1024/6;
        worldZ=255;
        shift=0;
        world=new char[worldX][worldY][worldZ];
        
        for(int z=0; z<worldZ; ++z)
        {
            for(int x=0; x<worldX; ++x)
            {
                for(int y=0; y<worldY; ++y)
                {
                    if(z==0 || (z==1&&y==49 && x==0) || (z==1 && x==49 &&y==0))
                        world[x][y][z]='a';
                    else
                        world[x][y][z]=' ';
                    if((x>49 || y>49) && z==1)
                        world[x][y][z]='b';
                }
            }
        }
        //System.out.println(worldX+"  "+worldY+"   "+worldZ);
        world[8][5][1]='b';
        world[8][5][2]='b';
        world[8][5][3]='b';
        world[10][5][1]='b';
        world[10][5][2]='b';
        world[10][5][3]='b';
        world[12][5][1]='b';
        world[12][5][2]='b';
        world[12][5][3]='b';

        world[5][8][1]='b';
        world[5][8][2]='b';
        world[5][8][3]='b';
        world[5][10][1]='b';
        world[5][10][2]='b';
        world[5][10][3]='b';
        world[5][12][1]='b';
        world[5][12][2]='b';
        world[5][12][3]='b';
        
        world[8][15][1]='b';
        world[8][15][2]='b';
        world[8][15][3]='b';
        world[10][15][1]='b';
        world[10][15][2]='b';
        world[10][15][3]='b';
        world[12][15][1]='b';
        world[12][15][2]='b';
        world[12][15][3]='b';
        
        
        world[15][8][1]='b';
        world[15][8][2]='b';
        world[15][8][3]='b';
        world[15][10][1]='b';
        world[15][10][2]='b';
        world[15][10][3]='b';
        world[15][12][1]='b';
        world[15][12][2]='b';
        world[15][12][3]='b';  
    }
    
    public void draw(ModelBatch MB, Environment enviroment/*, ArrayList<ModelInstance> models*/)
    {
        UBJsonReader jsonReader = new UBJsonReader();
        // Create a model loader passing in our json reader
        G3dModelLoader modelLoader = new G3dModelLoader(jsonReader);
        Model testBox = modelLoader.loadModel(Gdx.files.getFileHandle("testBox.g3db", Files.FileType.Internal));
        Model redBox = modelLoader.loadModel(Gdx.files.getFileHandle("redBox.g3db", Files.FileType.Internal));
        Model Zombie = modelLoader.loadModel(Gdx.files.getFileHandle("walkingZombie.g3db", Files.FileType.Internal));
        Vector3 v3=new Vector3(0,0,0);
        ModelInstance boxInstance = new ModelInstance(testBox, v3);
        DefaultShader.defaultCullFace = 0;
        for(int z=0; z<(int)player.z+view; ++z)
        {
            if(z>=0 && z<worldZ)
            {
                for(int y=(int)player.y-view; y<(int)player.y+view; ++y)
                {
                    if(y>=0 && y<worldY)
                    {
                        for(int x=(int)player.x-view; x<player.x+view; ++x)
                        {
                            if(x>=0 && x<worldX)
                            {
                                if(world[x][y][z]=='a')
                                {
                                    MB.render( new ModelInstance(redBox, new Vector3(x*size-player.x, y*size-player.y, z*size-player.z)) , enviroment);
                                }
                                else if(world[x][y][z]=='b')
                                {
                                    MB.render( new ModelInstance(testBox, new Vector3(x*size-player.x, y*size-player.y, z*size-player.z)) , enviroment);
                                }
                            }
                        }
                    }
                }
            }    
        }
        DefaultShader.defaultCullFace = -1;
//                    else if(world[x][y][z]=='4')
//                    {
//                        ModelInstance m=new ModelInstance(Zombie, new Vector3(x*size, y*size, z*size));
//                        //fbx-conv is supposed to perform this rotation for you... it doesnt seem to
//                        m.transform.rotate(1, 0, 0, 90);
//                        //move the model down a bit on the screen ( in a z-up world, down is -z ).
//                        //m.transform.translate(0, 0, -2);
//                        MB.render(m, enviroment);
//                    }
//                }
//            }
//        }
    }
    
    public boolean legal(int x, int y, int z)
    {
        //
//        x=(x)/2;
//        y=(y)/2;
//        z=(z)/2;   
        //boolean canWalk=false;
        //System.out.println(player.x+"  "+player.y+"   ");
        if(x>=0 && x<worldX && y>=0 && y<worldY && z>=0 && z<worldZ)
        {
            
            //System.out.println(x+"  "+y+"   "+z);
            if(world[x][y][z]!=' ')
            {
               // System.out.println(x+"  "+y+"   "+z);
                return false;
            }
            else
            {
                
                if(player.facing.equalsIgnoreCase("SOUTH"))
                {
                    System.out.println("SOUTH");
                    if( world[(int)(player.x+.5)][y][z]!=' ' && player.x%1!=0 )// && world[(int)(player.x+.5)][y][z]==' ') )//|| world[(int)(player.x+.5)][y][z]==' ' )
                    {
                        return false;
                    }
                    
                }
                else if(player.facing.equalsIgnoreCase("NORTH"))
                {
                    //System.out.println("NORTH");
                    if( world[(int)(player.x+.5)][y][z]!=' ' && player.x%1!=0 )// && world[(int)(player.x+.5)][y][z]==' ') )//|| world[(int)(player.x+.5)][y][z]==' ' )
                    {
                        return false;
                    }    
                }
                else if(player.facing.equalsIgnoreCase("EAST"))
                {
                    System.out.println("EAST");
                    if( world[x][(int)(player.y+.5)][z]!=' ' && player.y%1!=0 )// && world[(int)(player.x+.5)][y][z]==' ') )//|| world[(int)(player.x+.5)][y][z]==' ' )
                    {
                        return false;
                    }    
                }
                else if(player.facing.equalsIgnoreCase("WEST"))
                {
                    System.out.println("WEST");
                    if( world[x][(int)(player.y+.5)][z]!=' ' && player.y%1!=0 )// && world[(int)(player.x+.5)][y][z]==' ') )//|| world[(int)(player.x+.5)][y][z]==' ' )
                    {
                        return false;
                    }    
                }
                return true;
            }
        }
        else 
        {
            return false;
        }
        //return false;
//        if(x>=0 && x<worldX && y>=0 && y<worldY && z>=0 && z<worldZ)
//        {
//            System.out.println(x+"  "+y+"   "+z);
//            if(world[x][y][z]!=' ')
//            {
//               // System.out.println(x+"  "+y+"   "+z);
//                return false;
//            }
//            else
//            {
//                
//                return true;
//            }
//        }
//        else 
//        {
//            return false;
//        }
    }
    
    public void destroy()
    {
        String dir=player.setAllDir();
        int vert=0;
        if(player.camera.direction.z>=.19)           vert=1;
        else if(player.camera.direction.z<=-.5)     vert=-1;
        for(int i=0; i<4; ++i)
        {
            if(dir.equalsIgnoreCase("NW"))
            {
                if(world[(int)player.x-i][(int)player.y+i][(int)player.z+vert]=='b')
                {
                    world[(int)player.x-i][(int)player.y+i][(int)player.z+vert]=' ';
                    return;
                }
            }
            else if(dir.equalsIgnoreCase("N"))
            {
                if(world[(int)player.x][(int)player.y+i][(int)player.z+vert]=='b')
                {
                    world[(int)player.x][(int)player.y+i][(int)player.z+vert]=' ';
                    return;
                }
            }
            else if(dir.equalsIgnoreCase("NE"))
            {
                if(world[(int)player.x+i][(int)player.y+i][(int)player.z+vert]=='b')
                    {
                    world[(int)player.x+i][(int)player.y+i][(int)player.z+vert]=' ';
                    return;    
                    }
            }
            else if(dir.equalsIgnoreCase("E"))
            {
                if(world[(int)player.x+i][(int)player.y][(int)player.z+vert]=='b')
                {
                    world[(int)player.x+i][(int)player.y][(int)player.z+vert]=' ';
                    return;    
                }
            }
            else if(dir.equalsIgnoreCase("W"))
            {
                if(world[(int)(player.x-i)][(int)player.y][(int)player.z+vert]=='b')
                {
                    world[(int)(player.x-i)][(int)player.y][(int)player.z+vert]=' ';
                    return;    
                }
            }
            else if(dir.equalsIgnoreCase("SW"))
            {
                if(world[(int)player.x-i][(int)player.y-i][(int)player.z+vert]=='b')
                {
                    world[(int)player.x-i][(int)player.y-i][(int)player.z+vert]=' ';
                    return;    
                }
            }
            else if(dir.equalsIgnoreCase("S"))
            {
                if(world[(int)player.x][(int)player.y-i][(int)player.z+vert]=='b')
                {
                    world[(int)player.x][(int)player.y-i][(int)player.z+vert]=' ';
                    return;    
                }
            }
            else if(dir.equalsIgnoreCase("SE"))
            {
                if(world[(int)player.x+i][(int)player.y-i][(int)player.z+vert]=='b')
                {
                    world[(int)player.x+i][(int)player.y-i][(int)player.z+vert]=' ';
                    return;
                }
            }
        }
    }
    
    
}
