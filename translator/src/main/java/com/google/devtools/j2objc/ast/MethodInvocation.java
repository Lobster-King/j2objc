/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.devtools.j2objc.ast;

import com.google.devtools.j2objc.types.Types;

import org.eclipse.jdt.core.dom.IMethodBinding;

import java.util.List;

/**
 * Method invocation node type.
 */
public class MethodInvocation extends Expression {

  private IMethodBinding methodBinding = null;
  private ChildLink<Expression> expression = ChildLink.create(this);
  private ChildList<Expression> arguments = ChildList.create(this);

  public MethodInvocation(org.eclipse.jdt.core.dom.MethodInvocation jdtNode) {
    super(jdtNode);
    methodBinding = Types.getMethodBinding(jdtNode);
    expression.set((Expression) TreeConverter.convert(jdtNode.getExpression()));
    for (Object argument : jdtNode.arguments()) {
      arguments.add((Expression) TreeConverter.convert(argument));
    }
  }

  public MethodInvocation(MethodInvocation other) {
    super(other);
    methodBinding = other.getMethodBinding();
    expression.copyFrom(other.getExpression());
    arguments.copyFrom(other.getArguments());
  }

  public IMethodBinding getMethodBinding() {
    return methodBinding;
  }

  public Expression getExpression() {
    return expression.get();
  }

  public List<Expression> getArguments() {
    return arguments;
  }

  @Override
  protected void acceptInner(TreeVisitor visitor) {
    if (visitor.visit(this)) {
      expression.accept(visitor);
      arguments.accept(visitor);
    }
    visitor.endVisit(this);
  }

  @Override
  public MethodInvocation copy() {
    return new MethodInvocation(this);
  }
}
