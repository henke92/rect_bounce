/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Henke
 */
public class Rect 
{
    private double x0;
    private double y0;
    private double width;
    private double angle;
    private double speed;
    private double [] x_corners;
    private double [] y_corners;
    private int collision_partner;
    
    private double dx;
    private double g_height;
    private int dir;
    private double FRICTION = 0.995;
    private double GRAVITY = 3.0;
    
    private int counter = 0;
    
    
    public Rect(double x0, double y0, double w, double speed, double angle)
    {
        this.x0 = x0;
        this.y0 = y0;
        this.width = w;
        this.speed = speed;
        this.angle = angle;
        x_corners = new double[4];
        y_corners = new double[4];
        collision_partner = -1;
        this.g_height = 0;
        this.dir = 1;
        
        
    }
    
    public void update()
    {
        if(this.g_height >= 0.1)
        {
            this.angle += speed;
        }
            
        this.dx *= FRICTION;
        this.x0 += this.dx;
        
        this.g_height += 0.1 * this.dir;
        this.y0 = this.y0 + this.dir * GRAVITY * Math.pow(g_height, 2);
        
        if(this.dir == -1 && this.g_height <= 0.25)
        {
            this.dir *= -1;
        }
    }
    
     public void set_dir(int dir)
    {
        this.dir = dir;
    }
    
    public void set_gravity_height(double h)
    {
        this.g_height = h;
    }
    
    public void set_dx(double dx)
    {
        this.dx = dx;
    }
    
    public double get_dx()
    {
        return this.dx;
    }
    
    public int get_dir()
    {
        return this.dir;
    }
    
    public double get_gravity_height()
    {
        return this.g_height;
    }
    
    public void set_gravity(double g)
    {
        this.GRAVITY = g;
    }
    
    public int get_collision_partner()
    {
        return this.collision_partner;
    }
    
    public void set_collision_partner(int x)
    {
        this.collision_partner = x;
    }
    
    public double get_y_corner(int ix)
    {
        return y_corners[ix];
    }
    
    public double get_x_corner(int ix)
    {
        return x_corners[ix];
    }
    
    public void set_y_corners(double [] cords)
    {
        for(int ix = 0; ix < 4; ix++)
        {
            y_corners[ix] = cords[ix];
        }
    }
    
    public void set_x_corners(double [] cords)
    {
        for(int ix = 0; ix < 4; ix++)
        {
            x_corners[ix] = cords[ix];
        }
    }
    
    public void set_speed(double s)
    {
        this.speed = s;
    }
    
    public void set_angle(double a)
    {
        this.angle = a;
    }
    
    public void set_width(double w)
    {
        this.width = w;
    }
    
    public void set_y0(double y)
    {
        this.y0 = y;
    }
    
    public void set_x0(double x)
    {
        this.x0 = x;
    }
    
    public double get_speed()
    {
        return this.speed;
    }
    
    public double get_angle()
    {
        return this.angle;
    }
    
    public double get_width()
    {
        return this.width;
    }
    
    public double get_y1()
    {
        return this.y0 + this.width;
    }
    
    public double get_x1()
    {
        return this.x0 + this.width;
    }
    
    public double get_center_y()
    {
        return this.y0 + this.width/2;
    }
    
    public double get_center_x()
    {
        return this.x0 + this.width/2;
    }
    
    public double get_y0()
    {
        return this.y0;
    }
    
    public double get_x0()
    {
        return this.x0;
    }
    
    public double corner_sum(double x, double y)
    {
        double sum = 0;
        return sum;
    }
    
}
