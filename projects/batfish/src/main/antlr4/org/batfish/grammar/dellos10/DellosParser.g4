parser grammar DellosParser;

options {
   superClass = 'org.batfish.grammar.BatfishParser';
   tokenVocab = DellosLexer;
}

s_vlan_dellos
:
   NO? VLAN
;

dellos10_configuration
:
   NEWLINE?
   (
      sl += stanza
   )+ COLON? NEWLINE? EOF
;


stanza
:
   s_vlan_dellos

;
