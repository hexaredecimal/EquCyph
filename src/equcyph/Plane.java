package equcyph;
/*
    Plane is a cartesian plane to plot functions, in Java.
    
    Copyright (C) 2012 Rafael Rendón Pablo <smart.rendon@gmail.com>
    
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.
    
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.
    
    You should have received a copy of the GNU General Public License
    along with this program. If not, see <http://www.gnu.org/licenses/>.
*/

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Vector;

import java.io.*;

public class Plane extends JPanel implements MouseListener,
                                             MouseWheelListener,
                                             MouseMotionListener {
  private int maxX;
  private int maxY;
  private int centerX;
  private int centerY;
  private int factorIndexX;
  private int factorIndexY;

  private double pixelWidth;
  private double pixelHeight;
  private double gridIntervalX;
  private double gridIntervalY;
  private double[] factors;

  private final double DEFAULT_REAL_WIDTH  = 10;
  private final double DEFAULT_REAL_HEIGHT = 10;
  private double realWidth;
  private double realHeight;
  private double scaleInX;
  private double scaleInY;

  private boolean firstTime;
  private boolean showAxis;
  private boolean showGrid;

  private Point startDrag;

  private Vector<Function> functionList;

  private PrintStream out; // Utility

  public Plane()
  {
    addMouseListener(this);
    addMouseWheelListener(this);
    addMouseMotionListener(this);
    firstTime = true;
    functionList = new Vector<Function>();

    setRealWidth(DEFAULT_REAL_WIDTH);
    setRealHeight(DEFAULT_REAL_HEIGHT);

    // Grid drawing settings
    gridIntervalX = 0.5;
    gridIntervalY = 0.5;

    factors = new double[] {2, 2, 2.5};
    factorIndexX = 0;
    factorIndexY = 0;
    setShowAxis(true);
    out = System.out;
  }

  public double getRealWidth()  { return realWidth;  }
  public double getRealHeight() { return realHeight; }

  public void setRealWidth(double rw)  { realWidth  = rw; }
  public void setRealHeight(double rh) { realHeight = rh; }

  public Vector<Function> getFunctionList()
  {
    return functionList;
  }

  public void setFunctionList(Vector<Function> list)
  {
    functionList = list;
  }

  public void removeFunction(String name)
  {
    for (Function f : functionList) {
      if (f.getName().equals(name)) {
        functionList.remove(f);
        break;
      }
    }
  }

  @Override
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D)g;

    if (firstTime) {
      //initGraphics();
      setScale();
      firstTime = false;
    }

    if (isShowAxis())
      drawAxis(g2d);

    if (isShowGrid())
      drawGrid(g2d);

    //g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
    //                     RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setStroke(new BasicStroke(1.2f));

    Parser parser = new Parser();
    double start = fx(0);
    double end = fx(getWidth() - 1);
    double middle = (start + end)/2;

    Color tempColor = g2d.getColor();
    for (Function f : functionList) {
      if (!f.isActive()) continue;
      double dx = fx(1) - fx(0);

      g2d.setColor(f.getColor());

      // From the middle to the left
      double x0, y0, x1, y1;
      for (x0 = middle; x0 - dx >= start; x0 -= dx) {
        y0 = f.evaluate(x0);

        if (Double.isNaN(y0))
          continue;

        x1 =  x0 - dx;
        y1 = f.evaluate(x1);

        if (Double.isNaN(y1))
          continue;

        //out.println("(" + x0 + ", " + y0);
        //out.println("(" + x1 + ", " + y1);
        g2d.drawLine(ix(x0), iy(y0), ix(x0), iy(y1));
      }

      // From the middle to the right
      for (x0 = middle; x0 + dx <= end; x0 += dx) {
        y0 = f.evaluate(x0);

        if (Double.isNaN(y0))
          continue;

        x1 =  x0 - dx;
        y1 = f.evaluate(x1);

        if (Double.isNaN(y1))
          continue;

        g2d.drawLine(ix(x0), iy(y0), ix(x0), iy(y1));
      }

    }

    g2d.setColor(tempColor);

  }


  /**
   * Calls to paintComponent() to draw the functions.
   */
  public void plot(Function f)
  {
    functionList.add(f);
    repaint();
  }

  /**
   * Returns whether to draw or not the axis.
   * @return true if axis should be drawn.
   */
  public boolean isShowAxis() { return showAxis; }

  /**
   * Sets the showAxes property.
   * @param showAxis the new setting
   */
  public void setShowAxis(boolean showAxis)
  {
    this.showAxis = showAxis;
    repaint();
  }

  /**
   * Toggle showAxis state.
   */
  public void toggleShowAxis()
  {
    showAxis = !showAxis;
    repaint();
  }


  /**
   * Returns true if to draw grid or false if not.
   * @return true or false, draw or not draw grid
   */
  public boolean isShowGrid()
  {
    return showGrid;
  }

  /**
   * Sets if to draw grid or not.
   * @param showGrid boolean, true to draw grid, false if not.
   */
  public void setShowGrid(boolean showGrid)
  {
    this.showGrid = showGrid;
    repaint();
  }


  /**
   * Toggle showGrid state.
   */
  public void toggleShowGrid()
  {
    showGrid = !showGrid;
    repaint();
  }



  /**
   * Initialize the variables needed to use isotropic mapping mode(Computer
   * Graphics for Java Programmers, 2nd. Edition, Leen Ammeraaland, Kang Zhang).
   */
  protected void initGraphics()
  {
    maxX = getWidth() - 1;
    maxY = getHeight() - 1;

    centerX = maxX/2;
    centerY = maxY/2;

    pixelWidth = realWidth/Math.max(maxX, maxY);
    pixelHeight = realHeight/Math.max(maxX, maxY);


    factorIndexX = 0;
    factorIndexY = 0;

    gridIntervalX = 0.5;
    int w = ix(gridIntervalX) - ix(0);
    while (w < 50 || w > 150) {
      if (w < 50) {
        gridIntervalX *= factors[factorIndexX];
        factorIndexX = (factorIndexX + 1)%factors.length;
      } else if (w > 150) {
        factorIndexX = (factorIndexX - 1 + factors.length)%factors.length;
        gridIntervalX /= factors[factorIndexX];
      }

      w = ix(gridIntervalX) - ix(0);
    }

    gridIntervalY = 0.5;
    int h = iy(0) - iy(gridIntervalY);
    while (h < 50 || h > 150) {
      if (h < 50) {
        gridIntervalY *= factors[factorIndexY];
        factorIndexY = (factorIndexY + 1)%factors.length;
      } else if (h > 150) {
        factorIndexY = (factorIndexY - 1 + factors.length)%factors.length;
        gridIntervalY /= factors[factorIndexY];
      }
      h = iy(0) - iy(gridIntervalY);
    }
  }

  /**
   * Returns n rounded to the nearest integer.
   * @param n a double number
   * @return an integer rounded to the nearest integer
   */
  int round(double n)
  {
    return (int)Math.floor(n + 0.5);
  }

  /**
   * Returns the device-coordinate of x.
   * @param x x-coordinate in logical-coordinates
   * @return an integer with the device-coordinate of x
   */
  protected int ix(double x)
  {
    return round(centerX + x/pixelWidth);
  }

  /**
   * Returns the device-coordinate of y.
   * @param y y-coordinate in logical-coordinates
   * @return an integer with the device-coordinate of y
   */
  protected int iy(double y)
  {
    return round(centerY - y/pixelHeight);
  }

  /**
   * Returns the device-coordinate of x using a particular pixel size.
   * @param x x-coordinate in logical-coordinates
   * @param ps pixel size
   * @return an integer with the device-coordinate of x
   */
  public int ix(double x, double ps)
  {
    return round(centerX + x/ps);
  }

  /**
   * Returns the device-coordinate of y using a particular pixel size.
   * @param y y-coordinate in logical-coordinates
   * @param ps pixel size
   * @return an integer with the device-coordinate of y
   */
  public int iy(double y, double ps)
  {
    return round(centerY - y / ps);
  }


  /**
   * Returns the logical-coordinate of x.
   * @param x x-coordinate in device-coordinates
   * @return double, logical coordinate of x
   */
  public double fx(int x) {
    return (double)(x - centerX) * pixelWidth;
  }

  /**
   * Returns the logical-coordinate of y.
   * @param y y-coordinate in device-coordinates
   * @return double, logical coordinate of y
   */
  public double fy(int y)
  {
    return (double)(centerY - y) * pixelHeight;
  }


  /**
   * Returns real with the specified precision.
   * @param real a real number
   * @param precision precision in digits of the output
   * @return real with the specified precision
   */
  public double setPrecision(double real, int precision)
  {
    BigDecimal decimal = new BigDecimal(real, new MathContext(precision));
    return decimal.doubleValue();
  }


  /**
   * Zooms out the plane ten percent with origin in mouse click.
   * @param mx X coordinate of mouse click.
   * @param my Y coordinate of mouse click.
   */
  public void zoomOut(int mx, int my)
  {
    double psx = pixelWidth;
    double psy = pixelHeight;
    Point2D previous = new Point2D(fx(mx), fy(my));
    pixelWidth += pixelWidth/10;
    pixelHeight += pixelHeight/10;

    if (pixelWidth > 1e7 || pixelHeight > 1e7)
      return;

    int dx = ix(previous.x()) - ix(previous.x(), psx);
    int dy = iy(previous.y()) - iy(previous.y(), psy);

    centerX -= dx;
    centerY -= dy;

    repaint();
  }

  /**
   * Zooms in the plane ten percent with origin in mouse click.
   * @param mx X coordinate of mouse click.
   * @param my Y coordinate of mouse click.
   */
  public void zoomIn(int mx, int my)
  {
    double psx = pixelWidth;
    double psy = pixelHeight;
    Point2D previous = new Point2D(fx(mx), fy(my));

    if (pixelWidth < 1e-7 || pixelHeight < 1e-7)
      return;

    pixelWidth -= pixelWidth/10;
    pixelHeight -= pixelHeight/10;

    int dx = ix(previous.x()) - ix(previous.x(), psx);
    int dy = iy(previous.y()) - iy(previous.y(), psy);

    centerX -= dx;
    centerY -= dy;

    repaint();
  }


  /**
   * Restore the original scale.
   */
  public void resetZoom()
  {
    setRealWidth(DEFAULT_REAL_WIDTH);
    setRealHeight(DEFAULT_REAL_HEIGHT);
    initGraphics();

    repaint();
  }


  /**
   * This methods set and get the values for scale of the plane.
   */
  public void setScaleInX(double scale) { scaleInX = scale; }
  public void setScaleInY(double scale) { scaleInY = scale; }

  public double getScaleInX() { return scaleInX; }
  public double getScaleInY() { return scaleInY; }

  /**
   * Set the scale of the plane in the form a:b.
   * Calling this method reset the zoom too.
   */
  private void setScale()
  {
    double a = getScaleInX();
    double b = getScaleInY();
    resetZoom();
    double factor = (double)a/(double)b;
    setRealWidth(getRealWidth() * factor);
    initGraphics();
  }


  /**
   * Translate the plane and set point (x, y) as the center of the viewport.
   * @param x
   * @param y
   */
  public void translate(double x, double y)
  {
    int cx = getWidth()/2;
    int cy = getHeight()/2;
    int dx = ix(x) - cx;
    int dy = iy(y) - cy;

    centerX -= dx;
    centerY -= dy;

    repaint();
  }

  /**
   * Draw grid in the plane.
   * @param g2d Graphics2D object
   */
  public void drawGrid(Graphics2D g2d)
  {
    double left = fx(0);
    double top = fy(0);
    double right = fx(getWidth() - 1);
    double bottom = fy(getHeight() - 1);

    int w = ix(gridIntervalX) - ix(0);
    if (w < 50) {
      gridIntervalX *= factors[factorIndexX];
      factorIndexX = (factorIndexX + 1)%factors.length;
    } else if (w > 150) {
      factorIndexX = (factorIndexX - 1 + factors.length)%factors.length;
      gridIntervalX /= factors[factorIndexX];
    }

    int cX = ix(0);
    int interval = java.lang.Math.max(1, ix(gridIntervalX) - cX);
    int mod = cX % interval;
    double startX = fx(mod) - (fx(mod) % gridIntervalX) - gridIntervalX;


    Stroke dash = new BasicStroke(0.5f, BasicStroke.CAP_SQUARE,
                                  BasicStroke.JOIN_MITER, 10,
                                  new float[] {8,4}, 0);


    g2d.setStroke(dash);
    g2d.setColor(new Color(140, 140, 140));
    for (double i = startX; i <= right; i += gridIntervalX)
      if (ix(i) != ix(0) || !isShowAxis())
        g2d.drawLine(ix(i), iy(top), ix(i), iy(bottom));



    int h = iy(0) - iy(gridIntervalY);
    if (h < 50) {
      gridIntervalY *= factors[factorIndexY];
      factorIndexY = (factorIndexY + 1)%factors.length;
    } else if (h > 150) {
      factorIndexY = (factorIndexY - 1 + factors.length)%factors.length;
      gridIntervalY /= factors[factorIndexY];
    }

    int cY = iy(0);
    interval = java.lang.Math.max(1, iy(gridIntervalY) - cY);
    mod = cY % interval;
    double startY = fy(mod) - (fy(mod) % gridIntervalY) + gridIntervalY;

    for (double i = startY; i >= bottom; i -= gridIntervalY)
      if (iy(i) != iy(0) || !isShowAxis())
        g2d.drawLine(ix(left), iy(i), ix(right), iy(i));

  }// End of drawGrid()

  /**
   * Draw axes in the plane.
   * @param g2d a Graphics2D object
   */
  public void drawAxis(Graphics2D g2d)
  {
    double left = fx(0);
    double top = fy(0);
    double right = fx(getWidth() - 1);
    double bottom = fy(getHeight() - 1);

    int w = ix(gridIntervalX) - ix(0);
    if (w < 50) {
      gridIntervalX *= factors[factorIndexX];
      factorIndexX = (factorIndexX + 1)%factors.length;
    } else if (w > 150) {
      factorIndexX = (factorIndexX - 1 + factors.length)%factors.length;
      gridIntervalX /= factors[factorIndexX];
    }

    int cX = ix(0);
    int interval = java.lang.Math.max(1, ix(gridIntervalX) - cX);
    int mod = cX % interval;
    double startX = fx(mod) - (fx(mod) % gridIntervalX) - gridIntervalX;


    g2d.setStroke(new BasicStroke(1f));
    g2d.setColor(Color.BLACK);

    for (double i = startX; i <= right; i += gridIntervalX) {
      if (ix(i) == ix(0)) {
        g2d.drawLine(ix(i), iy(top), ix(i), iy(bottom));

        //Draws arrows of y axis
        g2d.drawLine(ix(i) - 7, iy(top) + 7, ix(i), iy(top));
        g2d.drawLine(ix(i) + 7, iy(top) + 7, ix(i), iy(top));
        g2d.drawString("y", ix(i) - 20, iy(top) + 10);

        g2d.drawLine(ix(i) - 7, iy(bottom) - 7, ix(i), iy(bottom));
        g2d.drawLine(ix(i) + 7, iy(bottom) - 7, ix(i), iy(bottom));
        g2d.drawString("-y", ix(i) - 20, iy(bottom) - 10);
      } else {
        g2d.drawString("" + setPrecision(i, 5), ix(i) + 5, iy(0) + 15);
      }
      g2d.drawLine(ix(i), iy(0), ix(i), iy(0) + 12);
    }


    int h = iy(0) - iy(gridIntervalY);
    if (h < 50) {
      gridIntervalY *= factors[factorIndexY];
      factorIndexY = (factorIndexY + 1)%factors.length;
    } else if (w > 150) {
      factorIndexY = (factorIndexY - 1 + factors.length)%factors.length;
      gridIntervalY /= factors[factorIndexY];
    }


    int cY = iy(0);
    interval = java.lang.Math.max(1, iy(gridIntervalY) - cY);
    mod = cY % interval;
    double startY = fy(mod) - (fy(mod) % gridIntervalY) + gridIntervalY;

    for (double i = startY; i >= bottom; i -= gridIntervalY) {
      if (iy(i) == iy(0)) {
        g2d.drawLine(ix(left), iy(i), ix(right), iy(i));

        //Draw arrows of x axis
        g2d.drawLine(ix(left) + 7, iy(i) - 7, ix(left), iy(i));
        g2d.drawLine(ix(left) + 7, iy(i) + 7, ix(left), iy(i));
        g2d.drawString("-x", ix(left) + 5, iy(i) - 10);

        g2d.drawLine(ix(right) - 7, iy(i) - 7, ix(right), iy(i));
        g2d.drawLine(ix(right) - 7, iy(i) + 7, ix(right), iy(i));
        g2d.drawString("x", ix(right) - 5, iy(i) - 10);
      } else {
        g2d.drawString("" + setPrecision(i, 5), ix(0) + 10, iy(i) - 5);
      }
      g2d.drawLine(ix(0), iy(i), ix(0) + 12, iy(i));
    }

  }// End of drawAxis()



  /* MouseListener methods. */
  @Override
  public void mouseReleased(MouseEvent event) { }

  @Override
  public void mouseExited(MouseEvent event) { }

  @Override
  public void mouseClicked(MouseEvent event) { }

  @Override
  public void mouseEntered(MouseEvent event) { }

  @Override
  public void mousePressed(MouseEvent event)
  {
    startDrag = event.getPoint();
  }

  /* MouseMotionListener methods. */

  /**
   * Performs plane dragging.
   * @param e MouseEvent with coordinates of mouse position
   */
  @Override
  public void mouseDragged(MouseEvent e)
  {
    int dx = e.getX() - (int)startDrag.getX();
    int dy = e.getY() - (int)startDrag.getY();

    if (dx*dx + dy*dy < 50)
      return;

    startDrag = e.getPoint();
    centerX += dx;
    centerY += dy;
    repaint();
  }

  @Override
  public void mouseMoved(MouseEvent e) { }

  /* MouseWheelListener methods. */

  /**
   * Controls zoom direction.
   * @param event a MouseWheelEvent object
   */
  @Override
  public void mouseWheelMoved(MouseWheelEvent event) {
    int rotation = event.getWheelRotation();
    int x = event.getX();
    int y = event.getY();

    if (rotation > 0)
      zoomIn(x, y);
    else
      zoomOut(x, y);
  }
}


