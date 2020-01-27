package com.cleancoder.args;

import java.util.*;

import static com.cleancoder.args.ArgsException.ErrorCode.*;

public class Args {
  private Map<Character, ArgumentMarshaler> marshalers;
  private Set<Character> argsFound;
  private ListIterator<String> currentArgument;

  public Args(String schema, String[] args) throws ArgsException {
    marshalers = new HashMap<Character, ArgumentMarshaler>();
    argsFound = new HashSet<Character>();

    parseSchema(schema);
    parseArgumentStrings(Arrays.asList(args));
  }

  private void parseSchema(String schema) throws ArgsException {
    for (String element : schema.split(","))
      if (element.length() > 0)
        parseSchemaElement(element.trim());
  }

  private void parseSchemaElement(String element) throws ArgsException {
    char elementId = element.charAt(0);
    String elementTail = element.substring(1);
    validateSchemaElementId(elementId);
    assignMarshaler(elementId, elementTail);
  }

  private void assignMarshaler(char elementId, String elementTail) throws ArgsException {
    if (elementTail.length() == 0)
      marshalers.put(elementId, new BooleanArgumentMarshaler());
    else 
      switch(elementTail) {
        case "*":
          marshalers.put(elementId, new StringArgumentMarshaler());
          break;
        case "#":
          marshalers.put(elementId, new IntegerArgumentMarshaler());
          break;
        case "##":
          marshalers.put(elementId, new DoubleArgumentMarshaler());
          break;
        case "[*]":
          marshalers.put(elementId, new StringArrayArgumentMarshaler());
          break;
        case "&":
          marshalers.put(elementId, new MapArgumentMarshaler());
          break;
        default:
          throw new ArgsException(INVALID_ARGUMENT_FORMAT, elementId, elementTail);
      }
    
  }

  private void validateSchemaElementId(char elementId) throws ArgsException {
    if (!Character.isLetter(elementId))
      throw new ArgsException(INVALID_ARGUMENT_NAME, elementId, null);
  }

  private void parseArgumentStrings(List<String> argsList) throws ArgsException {
    currentArgument = argsList.listIterator();
    while(currentArgument.hasNext()){
      String argString = currentArgument.next();
      if (argString.startsWith("-"))
        parseArgumentCharacters(argString.substring(1));
      else {
        currentArgument.previous();
        break;
      }
    }
  }

  private void parseArgumentCharacters(String argChars) throws ArgsException {
    for (int i = 0; i < argChars.length(); i++)
      getMarshaler(argChars.charAt(i));
  }

  private void getMarshaler(char argChar) throws ArgsException {
    ArgumentMarshaler validMarshaler = marshalers.get(argChar);
    if (validMarshaler == null) 
      throw new ArgsException(UNEXPECTED_ARGUMENT, argChar, null);
    else 
      setIntoMarshaler(argChar);
  }

  private void setIntoMarshaler(char argChar) throws ArgsException {
      ArgumentMarshaler validMarshaler = marshalers.get(argChar);
      argsFound.add(argChar);
      try {
        validMarshaler.setArgsInDataStructure(currentArgument);
      } catch (ArgsException e) {
        e.setErrorArgumentId(argChar);
        throw e;
      }
    }

  public boolean hasCharArg(char arg) {
    return argsFound.contains(arg);
  }

  public int nextArgument() {
    return currentArgument.nextIndex();
  }

  /* The following functions return the value given by the respective 
  marshalers when passed an arg of a particular type */

  public boolean getBoolean(char arg) {
    return BooleanArgumentMarshaler.getValue(marshalers.get(arg));
  }

  public String getString(char arg) {
    return StringArgumentMarshaler.getValue(marshalers.get(arg));
  }

  public int getInt(char arg) {
    return IntegerArgumentMarshaler.getValue(marshalers.get(arg));
  }

  public double getDouble(char arg) {
    return DoubleArgumentMarshaler.getValue(marshalers.get(arg));
  }

  public String[] getStringArray(char arg) {
    return StringArrayArgumentMarshaler.getValue(marshalers.get(arg));
  }

  public Map<String, String> getMap(char arg) {
    return MapArgumentMarshaler.getValue(marshalers.get(arg));
  }
}