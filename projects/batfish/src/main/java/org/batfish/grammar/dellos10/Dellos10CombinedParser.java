package org.batfish.grammar.dellos10;

import org.batfish.grammar.BatfishCombinedParser;
import org.batfish.grammar.dellos10.DellosParser.Dellos_configurationContext;


public class Dellos10CombinedParser extends BatfishCombinedParser<DellosParser, DellosLexer> {

  @Override
  public Dellos_configurationContext parse() {
    return _parser.dellos10_configuration();
  }
}
