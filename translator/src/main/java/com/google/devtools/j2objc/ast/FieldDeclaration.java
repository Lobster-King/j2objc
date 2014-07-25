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

import java.util.List;

/**
 * Node for a field declaration.
 */
public class FieldDeclaration extends BodyDeclaration {

  private ChildLink<Type> type = ChildLink.create(this);
  private ChildList<VariableDeclarationFragment> fragments = ChildList.create(this);

  public FieldDeclaration(org.eclipse.jdt.core.dom.FieldDeclaration jdtNode) {
    super(jdtNode);
    type.set((Type) TreeConverter.convert(jdtNode.getType()));
    for (Object fragment : jdtNode.fragments()) {
      fragments.add((VariableDeclarationFragment) TreeConverter.convert(fragment));
    }
  }

  public FieldDeclaration(FieldDeclaration other) {
    super(other);
    type.copyFrom(other.getType());
    fragments.copyFrom(other.getFragments());
  }

  public Type getType() {
    return type.get();
  }

  public List<VariableDeclarationFragment> getFragments() {
    return fragments;
  }

  @Override
  protected void acceptInner(TreeVisitor visitor) {
    if (visitor.visit(this)) {
      type.accept(visitor);
      fragments.accept(visitor);
    }
    visitor.endVisit(this);
  }

  @Override
  public FieldDeclaration copy() {
    return new FieldDeclaration(this);
  }
}
