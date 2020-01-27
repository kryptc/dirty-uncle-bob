package com.cleancoder.args;

import java.util.Iterator;

public interface ArgumentMarshaler {
  void setArgsInDataStructure(Iterator<String> currentArgument) throws ArgsException;
}
