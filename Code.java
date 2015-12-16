//Garrett Stoddard 
// Project 9


int fishes = 8;
int group = 5;
float space;
float spaces;
float sunX = 0, sunY = 100;
float waterline;
Boat Fleet[] = new Boat[group];
Squid squad[] = new Squid[fishes];
String squadNames[] = { "Bob", "Mike", "Joe", "Will", "Alice", "Jill", "Jamie", "Tom" };
String fleetNames[] = { "Essix", "Yamato", "Midway", "Ford", "Zumwalt" };
int score = 0;
//used for pause methods
float [] boatTemp = {0,0,0,0,0};
float [] squidTemp = {0,0,0,0,0,0,0,0};
void setup()
{
  size(1200, 675);
  space = width/(fishes+1);
  spaces = width/(group+1);
  reset();
}
// reset the entire scene
void reset()
{
  score = 0;
  waterline = random(height/4, height/3);
  sunY = waterline/2;
  sunX = 0;
  float x = space;
  float y = spaces;
  // setting up squids
  for (int i = 0; i < fishes; i++)
    {
      squad[i] = new Squid( squadNames[i], x);
      x += space;
    }
    
    //setting up ships
  for (int i = 0; i < group; i++)
    {
      Fleet[i] = new Boat( fleetNames[i], y);
      y += spaces;
   }
  
}

void draw ()
{
  scene();
  display();
  if(key == 'a')
  {
    boatReport( 50, Fleet, Fleet.length);
    squidReport( 450, squad, squad.length);
  }
  else movement();
}

// displays the background
void scene()
{
  // sky
  background(#29D2FF);
  // sun
  if( sunX > width + 50)
    {
      sunX = - 50;
      sunY = 200;
    }
  sunX++;
  fill(#EDCC0E);
  ellipse(sunX, sunY - 150*sin( PI * sunX/width), 40, 40);
  // water
  fill(#078976);
  noStroke();
  rect(0, waterline, width, height- waterline);
  text( "SCORE:  "+score, width*3/4, 20 );
}

//move all of the objects
void movement()
{
  for( int i = 0; i< fishes; i ++)
    {
      squad[i].move();
    }
  for (int i = 0; i < group; i++)
    {
      Fleet[i].move();
    }
}

// shows all of the objects
void display()
{
  for( int i = 0; i < fishes; i++)
  {
    squad[i].show();
  }
  for( int i = 0; i < group; i++)
  {
    Fleet[i].show();
  }
}

// Prints the report of all the boats
void boatReport(float top, Boat[] b, int many)
{
  float x = 50, h = top+ 20;
  
  fill(#BFC5FF);
  rect(x, top, width - 100, 50 + 20*many);
  fill(0);
  text("Ship", x+ 40, h);
  text("Cargo", x+ 90, h);
  text("x", x+ 140, h);
  text("dx", x+ 240, h);
  for( int i = 0; i < many; i++)
  {
    h += 15;
    text( i+1, x, h);
    text(b[i].name, x+ 40, h);
    text(b[i].cargo, x+ 90, h);
    text(b[i].boatX, x+ 140, h);
    text(boatTemp[i], x+ 240, h);
  }
}

// Prints the report of all the squids
void squidReport(float top, Squid[] b, int many)
{
  float x = 50, h = top+ 20;
  
  fill(#BFC5FF);
  rect(x, top, width - 100, 50 + 20*many);
  fill(0);
  text("Squid", x+ 40, h);
  text("legs", x+ 90, h);
  text("x", x+ 140, h);
  text("y", x+ 240, h);
  text("dy", x+ 340, h);
  for( int i = 0; i < many; i++)
  {
    h += 15;
    text( i+1, x, h);
    text(b[i].name, x+ 40, h);
    text(b[i].legs, x+ 90, h);
    text(b[i].squidX, x+ 140, h);
    text(b[i].squidY, x+ 240, h);
    text(squidTemp[i], x+ 340, h);
  }
}

//                                  ----          Objects          ----
class Squid
{
  // properties
  // location
  float squidX, squidY;
  //movement
  float squidDY = 0;
  //size
  float squidW = 40, squidH = 40;
  //legs
  int legs = 8;
  //name
  String name = "";
  //color
  float r, g, b;
  // times hit bottom
  int counter = 0;
  
  
  // constructor
  Squid( String N, float X)
    {
      this.name = N;
      this.squidX = X;
      bot();
      // color 
      r=  random(13, 255);
      g=  random(5, 100);
      b=  random(7, 255);
      legs = int(random(1, 8.9));
    }
    
    Squid()
    {
    }
  
 // --- methods ---
     void bot()
       {
         squidY = height ;
         squidDY = -random(.3 , 2);
       }
       
     void move()
       {
         squidY+= squidDY;
         if( squidY > height)
         {
           bot();
           counter ++;
         }
         else if (squidY < waterline)
         {
           squidDY = -2* squidDY;
         }
       }
      void show()
       {
         //I was going to change the look slighly, but the rectangle gave a really nice spot to put the name
         fill(r, g, b);
         stroke(r, g, b);
         rect(squidX-squidW/2,squidY, squidW, squidH/2);
         triangle(squidX-(squidW/2+10), squidY+(squidH/8 - 5), squidX+(squidW/2+10), squidY+(squidH/8 - 5), squidX, squidY-(squidH+5));
         
         fill(255);
         float Ianimation = 10;
         if ( squidY % 100 > 80)
         {
           Ianimation = 2;
         }
         ellipse(squidX - squidW/5, squidY-squidH/2, 10, Ianimation);
         ellipse(squidX + squidW/5, squidY-squidH/2, 10, Ianimation);
         
         fill(r, g, b);
         float legX = squidX - squidW/2, foot = 0;
         // this made the animation of the legs really easy, but i didnt really understand it. 
         foot=  squidDY>=0 ? 0 : (squidY%47 > 23) ? 5 : -5;
         strokeWeight(2);
         for( int i = 0; i < legs; i++ )
           {
             line(legX, squidY + squidH/2, legX + foot, 20 + squidY + squidH/2);
             legX += squidW/(legs-1);
           }
         strokeWeight(2);
         fill( 0);
         text(name, squidX - squidW/2, squidY- 10 + squidH/2);
         //text(legs, squidX + 2 - squidW/2, squidY + squidH/2);
         if( counter > 0)
         {
           //text( counter, squidX, squidY + squidH/2);
         }
         // really simple hit method
         noStroke();
       }
     boolean connect( float x, float y)
      {
        return (dist(x, y, squidX, squidY) < squidH);
      }
       
       
}

// boat class
class Boat 
{
  String name = "";
  float boatX, boatY = waterline, boatDX = random(.5, 2);
  int cargo = 0, caught = 0;
  
  //constructors
  Boat()
  {
  }
  Boat(String N, float X)
  {
    name = N;
    boatX = X;
    
  }
  Boat(String N, float X, float DX, int car, int cau)
  {
    name = N;
    boatX = X;
    boatDX = DX; 
    cargo = car;
    caught = cau;
  }
    void move()
    {
      caught = 0;
      for( int i = 0; i < fishes; i++)
        {
          for( int j = 0; j < group; j++)
          if( squad[i].connect( Fleet[j].boatX, Fleet[j].boatY))
          {
            // simply adds 5,  hopefully more consistant, and I don't see the need to make it " random"
            Fleet[j].caught ++;
          }
        }
      this.cargo += caught;
      boatX += boatDX;
      if (caught > 0) boatX += boatDX;
      if (boatX < 0)
        {
          score += cargo;
          boatDX = random(1, 3);
          cargo = 0;
        }
      if (boatX > width)
        {
          boatDX = -random(.5, 2);
        }
      
    }
  
    void show()
    {
      // drawing the silly thing
      fill(#835B04);
      rect(boatX, waterline - 25, 60, 25);
      triangle(boatX - 20, waterline - 25, boatX+1, waterline - 25, boatX+1, waterline);
      triangle(boatX + 80, waterline - 25, boatX+59, waterline - 25, boatX+59, waterline);
      strokeWeight(4);
      stroke(#835B04);
      line(boatX + 30, waterline - 24, boatX + 30, waterline - 75);
      noStroke();
      fill(255);
      
      if( boatDX > 0)
      {
        triangle(boatX + 34, waterline - 75, boatX + 34, waterline - 27, boatX + 78, waterline - 27);
        triangle( boatX + 26, waterline - 65, boatX + 26, waterline - 27, boatX, waterline - 27);
      }
      else
      {
        triangle(boatX + 34, waterline - 65, boatX + 34, waterline - 27, boatX + 60, waterline - 27);
        triangle( boatX + 26, waterline - 75, boatX + 26, waterline - 27, boatX - 20, waterline - 27);
      }  
      strokeWeight(1);
      noStroke();
      text( name, boatX, waterline - 10);
      text( cargo, boatX + 50, waterline - 10);
    }
  
}


//         ---- handelers ----

void keyPressed()
{
  if( key == 'B')
  {
    sortBoatX(Fleet); 
    pause();
  }
  if( key == 'D')
  {
    sortBoatDX(Fleet);
    pause();
  }
  if( key == 'F')
  {
    sortBoatCargo(Fleet);
    pause();
  }
  if(key =='X')
  {
    sortSquidX(squad);
    pause();
  }
  if(key =='Y')
  {
    sortSquidY(squad);
    pause();
  }
  if(key =='S')
  {
    sortSquidDY(squad);
    pause();
  }
  if(key =='L')
  {
    sortSquidL(squad);
    pause();
  }
  if(key == 'r')
  {
    reset();
  }
  if(key == 'm')
  {
    resume();
  }
}


//            ---- Sorting methods ----
//sorting numbers
// searches the array for the smallest number, then sets it to the "i" index, then moves on to the rest of the array
void sortBoatX(Boat[] armada )// sorting the X values
{
 
  Boat swap = new Boat();
  
    for(int i = 0; i < armada.length; i++)
      {
        int temp1 = i;
        for(int j = i; j < armada.length; j++)
        {
          if (armada[j].boatX < armada[i].boatX)
          {
            temp1 = j;
          }
          
        }
        swap.boatX = armada[i].boatX;
        swap.boatDX = armada[i].boatDX;
        swap.name = armada[i].name;
        swap.caught = armada[i].caught;
        swap.cargo = armada[i].cargo;
        
        armada[i].boatX = armada[temp1].boatX;
        armada[i].boatDX = armada[temp1].boatDX;
        armada[i].name = armada[temp1].name;
        armada[i].caught = armada[temp1].caught;
        armada[i].cargo = armada[temp1].cargo;
        
        armada[temp1].boatX = swap.boatX;
        armada[temp1].boatDX = swap.boatDX;
        armada[temp1].name = swap.name;
        armada[temp1].caught = swap.caught;
        armada[temp1].cargo = swap.cargo;
      
  }
}

void sortBoatDX(Boat[] armada )// sorting the DX values
{
  
  Boat swap = new Boat();
  
  
    for(int i = 0; i < armada.length; i++)
      {
        int temp1 = i;
        for(int j = i; j < armada.length; j++)
        {
          if (armada[j].boatDX < armada[i].boatDX)
          {
            temp1 = j;
          }
          
        }
        swap.boatX = armada[i].boatX;
        swap.boatDX = armada[i].boatDX;
        swap.name = armada[i].name;
        swap.caught = armada[i].caught;
        swap.cargo = armada[i].cargo;
        
        armada[i].boatX = armada[temp1].boatX;
        armada[i].boatDX = armada[temp1].boatDX;
        armada[i].name = armada[temp1].name;
        armada[i].caught = armada[temp1].caught;
        armada[i].cargo = armada[temp1].cargo;
        
        armada[temp1].boatX = swap.boatX;
        armada[temp1].boatDX = swap.boatDX;
        armada[temp1].name = swap.name;
        armada[temp1].caught = swap.caught;
        armada[temp1].cargo = swap.cargo;
      }
  
  
}

void sortBoatCargo(Boat[] armada )// sorting the cargo values
{
  
  Boat swap = new Boat();
  
  
    for(int i = 0; i < armada.length; i++)
      {
        int temp1 = i;
        for(int j = i; j < armada.length; j++)
        {
          if (armada[j].cargo < armada[i].cargo)
          {
            temp1 = j;
          }
          
        }
        swap.boatX = armada[i].boatX;
        swap.boatDX = armada[i].boatDX;
        swap.name = armada[i].name;
        swap.caught = armada[i].caught;
        swap.cargo = armada[i].cargo;
        
        armada[i].boatX = armada[temp1].boatX;
        armada[i].boatDX = armada[temp1].boatDX;
        armada[i].name = armada[temp1].name;
        armada[i].caught = armada[temp1].caught;
        armada[i].cargo = armada[temp1].cargo;
        
        armada[temp1].boatX = swap.boatX;
        armada[temp1].boatDX = swap.boatDX;
        armada[temp1].name = swap.name;
        armada[temp1].caught = swap.caught;
        armada[temp1].cargo = swap.cargo;
      }
  
  
}

void sortSquidX(Squid[] flock)// sorting the X values
{
  int z = 0;
  Squid swap = new Squid();
    while(z<4)
    {
    for(int i = 0; i < flock.length; i++)
      {
        int temp1 = i;
        for(int j = i; j < flock.length; j++)
        {
          if (flock[j].squidX < flock[i].squidX)
          {
            temp1 = j;
          }
          
        }
        swap.squidX = flock[i].squidX;
        swap.squidY = flock[i].squidY;
        swap.squidDY = flock[i].squidDY;
        swap.name = flock[i].name;
        swap.legs = flock[i].legs;
        
        flock[i].squidX = flock[temp1].squidX;
        flock[i].squidY = flock[temp1].squidY;
        flock[i].squidDY = flock[temp1].squidDY;
        flock[i].name = flock[temp1].name;
        flock[i].legs = flock[temp1].legs;

        flock[temp1].squidX = swap.squidX;
        flock[temp1].squidY = swap.squidY;
        flock[temp1].squidDY = swap.squidDY;
        flock[temp1].name = swap.name;
        flock[temp1].legs = swap.legs;
   
      }
      z++;
    }
}

void sortSquidY(Squid[] flock)// sorting the Y values
{
  int z = 0;
  Squid swap = new Squid();
    while(z<4)
    {
    for(int i = 0; i < flock.length; i++)
      {
        int temp1 = i;
        for(int j = i; j < flock.length; j++)
        {
          if (flock[j].squidY < flock[i].squidY)
          {
            temp1 = j;
          }
          
        }
        swap.squidX = flock[i].squidX;
        swap.squidY = flock[i].squidY;
        swap.squidDY = flock[i].squidDY;
        swap.name = flock[i].name;
        swap.legs = flock[i].legs;
        
        flock[i].squidX = flock[temp1].squidX;
        flock[i].squidY = flock[temp1].squidY;
        flock[i].squidDY = flock[temp1].squidDY;
        flock[i].name = flock[temp1].name;
        flock[i].legs = flock[temp1].legs;

        flock[temp1].squidX = swap.squidX;
        flock[temp1].squidY = swap.squidY;
        flock[temp1].squidDY = swap.squidDY;
        flock[temp1].name = swap.name;
        flock[temp1].legs = swap.legs;
   
    }
    z++;
   }
}

void sortSquidDY(Squid[] flock)// sorting the DY values
{
 int z = 0;
  Squid swap = new Squid();
  while(z<4)
  {
    for(int i = 0; i < flock.length; i++)
      {
        int temp1 = i;
        for(int j = i; j < flock.length; j++)
        {
          if (flock[j].squidDY < flock[i].squidDY)
          {
            temp1 = j;
          }
          
        }
        swap.squidX = flock[i].squidX;
        swap.squidY = flock[i].squidY;
        swap.squidDY = flock[i].squidDY;
        swap.name = flock[i].name;
        swap.legs = flock[i].legs;
        
        flock[i].squidX = flock[temp1].squidX;
        flock[i].squidY = flock[temp1].squidY;
        flock[i].squidDY = flock[temp1].squidDY;
        flock[i].name = flock[temp1].name;
        flock[i].legs = flock[temp1].legs;

        flock[temp1].squidX = swap.squidX;
        flock[temp1].squidY = swap.squidY;
        flock[temp1].squidDY = swap.squidDY;
        flock[temp1].name = swap.name;
        flock[temp1].legs = swap.legs;
   
  }
  z++;
  }
}

void sortSquidL(Squid[] flock)// sorting the Leg values
{
 int z = 0;
  Squid swap = new Squid();
  while( z < 4)
  {
    for(int i = 0; i < flock.length; i++)
      {
        int temp1 = i;
        for(int j = i; j < flock.length; j++)
        {
          if (flock[j].legs < flock[i].legs)
          {
            temp1 = j;
          }
          
        }
        swap.squidX = flock[i].squidX;
        swap.squidY = flock[i].squidY;
        swap.squidDY = flock[i].squidDY;
        swap.name = flock[i].name;
        swap.legs = flock[i].legs;
        
        flock[i].squidX = flock[temp1].squidX;
        flock[i].squidY = flock[temp1].squidY;
        flock[i].squidDY = flock[temp1].squidDY;
        flock[i].name = flock[temp1].name;
        flock[i].legs = flock[temp1].legs;

        flock[temp1].squidX = swap.squidX;
        flock[temp1].squidY = swap.squidY;
        flock[temp1].squidDY = swap.squidDY;
        flock[temp1].name = swap.name;
        flock[temp1].legs = swap.legs;
   
    }
  z++;
  }
}
//        ---- random methods ----
//pauses the movement, to allow the user to observe the boats and squids
void pause()
{
  for( int i = 0; i < Fleet.length; i++)
  {
    boatTemp[i] = Fleet[i].boatDX;
    Fleet[i].boatDX = 0;
  }
  for(int j = 0; j < squad.length; j++)
  {
    squidTemp[j] = squad[j].squidDY;
    squad[j].squidDY = 0;
  }
}

void resume()
{
  for( int v = 0; v < Fleet.length; v++)
  {
    if(boatTemp[v] != 0)
    {
      Fleet[v].boatDX = boatTemp[v];
    }
  }
  for(int k = 0; k < squad.length; k++)
  {
    if(squidTemp[k] != 0)
    {
      squad[k].squidDY = squidTemp[k];
    }
  }
}
