package org.batfish.question.ipsec_vpn_expr.ipsec_vpn;

import org.batfish.datamodel.IpsecVpn;
import org.batfish.question.Environment;
import org.batfish.question.ipsec_vpn_expr.IpsecVpnExpr;

public final class RemoteIpsecVpnIpsecVpnExpr extends IpsecVpnIpsecVpnExpr {

   public RemoteIpsecVpnIpsecVpnExpr(IpsecVpnExpr caller) {
      super(caller);
   }

   @Override
   public IpsecVpn evaluate(Environment environment) {
      environment.initRemoteIpsecVpns();
      IpsecVpn caller = _caller.evaluate(environment);
      return caller.getRemoteIpsecVpn();
   }

}
