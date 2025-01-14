parser grammar Fortios_firewall;

options {
  tokenVocab = FortiosLexer;
}

c_firewall: FIREWALL (
  cf_address
  | cf_addrgrp
  | cf_policy
  | cf_service
);
