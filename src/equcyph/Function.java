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
import java.awt.*;

/**
 *  A basic function class, more in the graphic sense than 
 *  in the mathematical sense.
 */
public class Function {
  private String name;
  private String variable;
  private String definition;
  private int degree;
  private boolean active;
  private Parser parser;
  private Color color;


  public Function()
  {
    parser = new Parser();
    setActive(true);
  }

  public Function(String definition, String name)
  {
    parser = new Parser(definition);
    setDefinition(definition);
    setActive(true);
    setName(name);
  }

  public Color getColor()
  {
    return color;
  }

  public void setColor(Color color)
  {
    this.color = color;
  }

  public boolean isActive()
  {
    return active;
  }

  public void setActive(boolean active)
  {
    this.active = active;
  }


  public String getVariable()
  {
    return variable;
  }

  public void setVariable(String variable)
  {
    this.variable = variable;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getDefinition() {
    return definition;
  }

  public void setDefinition(String definition) {
    this.definition = definition;
  }

  public int getDegree() {
    return degree;
  }

  public void setDegree(int degree) {
    this.degree = degree;
  }

  public double evaluate(double x)
  {
    parser.setVariable("x", x);
    double fx = parser.evaluate();
    if (parser.getErrorCode() != Parser.SUCCESS)
      return Double.NaN;
    return fx;
  }

  @Override
  public String toString()
  {
    return name + " = " + getDefinition();
  }
}

