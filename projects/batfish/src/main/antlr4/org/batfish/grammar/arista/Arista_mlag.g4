parser grammar Arista_mlag;

import Legacy_common;

options {
   tokenVocab = AristaLexer;
}


eos_mlag_domain
:
   DOMAIN_ID id = variable NEWLINE
;

eos_mlag_local_interface
:
   LOCAL_INTERFACE iface = variable NEWLINE
;

eos_mlag_peer_address
:
   PEER_ADDRESS HEARTBEAT? ip = IP_ADDRESS NEWLINE
;

eos_mlag_peer_link
:
   PEER_LINK iface = variable NEWLINE
;

eos_mlag_reload_delay
:
   RELOAD_DELAY (MLAG | NON_MLAG)? period = (INFINITY | UINT8 | UINT16 | UINT32 | DEC) NEWLINE
;

eos_mlag_reload_mode
:
   MODE LACP STANDBY NEWLINE
;

eos_mlag_shutdown
:
   (NO)? SHUTDOWN NEWLINE
;

s_eos_mlag
:
   MLAG CONFIGURATION NEWLINE
   (
      eos_mlag_domain
      | eos_mlag_local_interface
      | eos_mlag_peer_address
      | eos_mlag_peer_link
      | eos_mlag_reload_delay
      | eos_mlag_shutdown
   )*
;

