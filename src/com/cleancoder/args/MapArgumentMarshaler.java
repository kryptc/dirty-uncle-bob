package com.cleancoder.args;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

import static com.cleancoder.args.ArgsException.ErrorCode.*;

public class MapArgumentMarshaler implements ArgumentMarshaler {
  private Map<String, String> map = new HashMap<>();

  public void setArgsInDataStructure(Iterator<String> currentArgument) throws ArgsException {
    try {
      String[] mapEntries = currentArgument.next().split(",");
      for (String entry : mapEntries) 
        insertToMap(entry);
    } catch (NoSuchElementException e) {
      throw new ArgsException(MISSING_MAP);
    }
  }

  public void insertToMap(String entry) throws ArgsException {
          String[] entryComponents = entry.split(":");
          if (entryComponents.length != 2)
            throw new ArgsException(MALFORMED_MAP);
          map.put(entryComponents[0], entryComponents[1]);
  }

  public static Map<String, String> getValue(ArgumentMarshaler argMarsh) {
    if (argMarsh != null && argMarsh instanceof MapArgumentMarshaler)
      return ((MapArgumentMarshaler) argMarsh).map;
    else
      return new HashMap<>();
  }
}
