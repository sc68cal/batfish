lexer grammar DellosLexer;

options {
   superClass = 'org.batfish.grammar.BatfishLexer';
}

AAA: 'aaa';

ADDRESS: 'address';

COLON
:
   ':'
;
CLASS_MAP: 'class-map';

CONTROL_PLANE: 'control-plane';

ETHERNET: 'ethernet';

IPADDRESS: 'ip address';

NO: 'no';

VLAN: 'vlan';

NEWLINE
:
  '\r' '\n'?
  | '\n'
;


