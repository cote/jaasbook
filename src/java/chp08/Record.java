package chp08;

import util.id.Id;

public class Record
{

  public Id id;
  public String name;
  public String text;
  
  public Record(Id id, String name, String text)
  {
   this.id = id;
   this.name = name;
   this.text = text;
  }
  
  public Id getId()
  {
    return id;
  }

  public String getName()
  {
    return name;
  }

  public String getText()
  {
    return text;
  }
}
