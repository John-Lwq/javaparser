/*
 * Copyright (C) 2007-2010 Júlio Vilmar Gesser.
 * Copyright (C) 2011, 2013-2016 The JavaParser Team.
 *
 * This file is part of JavaParser.
 *
 * JavaParser can be used either under the terms of
 * a) the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * b) the terms of the Apache License
 *
 * You should have received a copy of both licenses in LICENCE.LGPL and
 * LICENCE.APACHE. Please refer to those files for details.
 *
 * JavaParser is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 */

package com.github.javaparser.wiki_samples;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.type.VoidType;

import static com.github.javaparser.QuickJavaParser.parseClassOrInterfaceType;
import static com.github.javaparser.QuickJavaParser.parseName;
import static com.github.javaparser.ast.Modifier.Keyword.PUBLIC;
import static com.github.javaparser.ast.Modifier.Keyword.STATIC;
import static com.github.javaparser.ast.Modifier.createModifierList;
import static com.github.javaparser.ast.Modifier.staticModifier;

public class ClassCreator {

    public static void main(String[] args) {
        // creates the compilation unit
        CompilationUnit cu = createCU();

        // prints the created compilation unit
        System.out.println(cu.toString());
    }

    /**
     * creates the compilation unit
     */
    private static CompilationUnit createCU() {
        CompilationUnit cu = new CompilationUnit();
        // set the package
        cu.setPackageDeclaration(new PackageDeclaration(parseName("java.parser.test")));

        // or a shortcut
        cu.setPackageDeclaration("java.parser.test");

        // create the type declaration 
        ClassOrInterfaceDeclaration type = cu.addClass("GeneratedClass");

        // create a method
        NodeList<Modifier> modifiers = createModifierList(PUBLIC);
        MethodDeclaration method = new MethodDeclaration(modifiers, new VoidType(), "main");
        modifiers.add(staticModifier());
        method.setModifiers(modifiers);
        type.addMember(method);
        
        // or a shortcut
        MethodDeclaration main2 = type.addMethod("main2", PUBLIC, STATIC);

        // add a parameter to the method
        Parameter param = new Parameter(parseClassOrInterfaceType("String"), "args");
        param.setVarArgs(true);
        method.addParameter(param);
        
        // or a shortcut
        main2.addAndGetParameter(String.class, "args").setVarArgs(true);

        // add a body to the method
        BlockStmt block = new BlockStmt();
        method.setBody(block);

        // add a statement to the method body
        NameExpr clazz = new NameExpr("System");
        FieldAccessExpr field = new FieldAccessExpr(clazz, "out");
        MethodCallExpr call = new MethodCallExpr(field, "println");
        call.addArgument(new StringLiteralExpr("Hello World!"));
        block.addStatement(call);

        return cu;
    }
}
