parser grammar DellosParser;

options {
   superClass = 'org.batfish.grammar.BatfishParser';
   tokenVocab = DellosLexer;
}

s_vlan_dellos
:
   NO? VLAN
;
