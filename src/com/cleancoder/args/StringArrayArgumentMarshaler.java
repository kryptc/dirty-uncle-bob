package com.cleancoder.args;

import static com.cleancoder.args.ArgsException.ErrorCode.*;

import java.util.*;

public class StringArrayArgumentMarshaler implements ArgumentMarshaler {
  private List<String> strings = new ArrayList<String>();

  public void setArgsInDataStructure(Iterator<String> currentArgument) throws ArgsException {
    try {
      strings.add(currentArgument.next());
    } catch (NoSuchElementException e) {
      throw new ArgsException(MISSING_STRING);
    }
  }

  public static String[] getValue(ArgumentMarshaler argMarsh) {
    if (argMarsh != null && argMarsh instanceof StringArrayArgumentMarshaler)
      return ((StringArrayArgumentMarshaler) argMarsh).strings.toArray(new String[0]);
    else
      return new String[0];
  }
}
