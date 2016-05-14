package org.batfish.question.boolean_expr.node;

import org.batfish.datamodel.Configuration;
import org.batfish.question.Environment;
import org.batfish.question.node_expr.NodeExpr;

public final class OspfConfiguredNodeBooleanExpr extends NodeBooleanExpr {

   public OspfConfiguredNodeBooleanExpr(NodeExpr caller) {
      super(caller);
   }

   @Override
   public Boolean evaluate(Environment environment) {
      Configuration node = _caller.evaluate(environment);
      return node.getOspfProcess() != null;
   }

}
