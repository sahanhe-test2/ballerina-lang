/*
 *  Copyright (c) 2021, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.ballerinalang.langserver.util.documentsymbol;

import io.ballerina.compiler.syntax.tree.ClassDefinitionNode;
import io.ballerina.compiler.syntax.tree.FunctionDefinitionNode;
import io.ballerina.compiler.syntax.tree.IdentifierToken;
import io.ballerina.compiler.syntax.tree.MetadataNode;
import io.ballerina.compiler.syntax.tree.ModulePartNode;
import io.ballerina.compiler.syntax.tree.Node;
import io.ballerina.compiler.syntax.tree.NodeList;
import io.ballerina.compiler.syntax.tree.NodeTransformer;
import io.ballerina.compiler.syntax.tree.ServiceDeclarationNode;
import io.ballerina.compiler.syntax.tree.SyntaxKind;
import io.ballerina.compiler.syntax.tree.Token;
import io.ballerina.projects.Module;
import org.ballerinalang.langserver.commons.DocumentSymbolContext;
import org.eclipse.lsp4j.DocumentSymbol;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.SymbolKind;
import org.eclipse.lsp4j.SymbolTag;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Resolver for Document Symbols.
 *
 * @since 2.0.0
 */
public class DocumentSymbolResolver extends NodeTransformer<Optional<DocumentSymbol>> {

    private List<DocumentSymbol> documentSymbolStore;
    private DocumentSymbolContext context;

    DocumentSymbolResolver(DocumentSymbolContext context) {
        this.context = context;
        documentSymbolStore = new ArrayList<>();
    }

    public List<DocumentSymbol> getDocumentSymbolStore() {
        return this.documentSymbolStore;
    }

    @Override
    public Optional<DocumentSymbol> transform(Token token) {
        return Optional.empty();
    }

    @Override
    protected Optional<DocumentSymbol> transformSyntaxNode(Node node) {
        return Optional.empty();
    }

    @Override
    public Optional<DocumentSymbol> transform(ModulePartNode modulePartNode) {
        Path filePath = context.filePath();
        Optional<Module> module = context.workspace().module(filePath);
        String name;
        if (module.isPresent()) {
            if (module.get().isDefaultModule()) {
                name = "default";
            } else {
                name = module.get().moduleName().moduleNamePart();
            }
        } else {
            name = "module";
        }
        List<DocumentSymbol> children = transformMembers(modulePartNode.members());
        Range range = DocumentSymbolUtil.generateNodeRange(modulePartNode);
        return Optional.ofNullable(createDocumentSymbol(name, SymbolKind.Module,
                null, range, range, false, children, this.context));
    }

    @Override
    public Optional<DocumentSymbol> transform(FunctionDefinitionNode functionDefinitionNode) {
        String name;
        Range range = DocumentSymbolUtil.generateNodeRange(functionDefinitionNode);
        SymbolKind symbolKind;
        Optional<MetadataNode> metadata = functionDefinitionNode.metadata();
        boolean isDeprecated = metadata.isPresent() && DocumentSymbolUtil.isDeprecated(metadata.get());
        switch (functionDefinitionNode.kind()) {
            case FUNCTION_DEFINITION:
                name = functionDefinitionNode.functionName().text();
                symbolKind = SymbolKind.Function;
                break;
            case OBJECT_METHOD_DEFINITION:
                name = functionDefinitionNode.functionName().text();
                symbolKind = SymbolKind.Method;
                break;
            case RESOURCE_ACCESSOR_DEFINITION:
                StringBuilder resourceFuncName = new StringBuilder(functionDefinitionNode.functionName().text());
                for (Node child : functionDefinitionNode.children()) {
                    if (child.kind() == SyntaxKind.IDENTIFIER_TOKEN &&
                            !((IdentifierToken) child).text().equals(functionDefinitionNode.functionName().text())) {
                        resourceFuncName.append(":").append(((IdentifierToken) child).text());
                        break;
                    }
                }
                name = resourceFuncName.toString();
                symbolKind = SymbolKind.Function;
                break;
            default:
                return Optional.empty();
        }
        return Optional.ofNullable(createDocumentSymbol(name, symbolKind,
                null, range, range, isDeprecated, Collections.emptyList(), this.context));
    }

    @Override
    public Optional<DocumentSymbol> transform(ClassDefinitionNode classDefinitionNode) {
        String name = classDefinitionNode.className().text();
        SymbolKind symbolKind = SymbolKind.Class;
        Range range = DocumentSymbolUtil.generateNodeRange(classDefinitionNode);
        Optional<MetadataNode> metadata = classDefinitionNode.metadata();
        boolean isDeprecated = metadata.isPresent() &&
                DocumentSymbolUtil.isDeprecated(metadata.get());
        List<DocumentSymbol> children = transformMembers(classDefinitionNode.members());
        return Optional.ofNullable(createDocumentSymbol(name, symbolKind,
                null, range, range, isDeprecated, children, this.context));
    }

    @Override
    public Optional<DocumentSymbol> transform(ServiceDeclarationNode serviceDeclarationNode) {
        String name = "service " + serviceDeclarationNode.absoluteResourcePath().stream()
                .map(Node::toString).collect(Collectors.joining(""))
                + " on " + serviceDeclarationNode.expressions().stream()
                .map(Node::toString).collect(Collectors.joining(""));
        SymbolKind symbolKind = SymbolKind.Interface;
        Range range = DocumentSymbolUtil.generateNodeRange(serviceDeclarationNode);
        Optional<MetadataNode> metadata = serviceDeclarationNode.metadata();
        boolean isDeprecated = metadata.isPresent() &&
                DocumentSymbolUtil.isDeprecated(metadata.get());
        List<DocumentSymbol> children = transformMembers(serviceDeclarationNode.members());
        return Optional.ofNullable(createDocumentSymbol(name, symbolKind, null,
                range, range, isDeprecated, children, this.context));
    }

    /**
     * Provided a ChildNodes list generate the corresponding document symbols.
     *
     * @param nodes {@link NodeList<? extends Node>} Member nodes list.
     * @return {@link List<DocumentSymbol>} Generated list of document symbols.
     */
    private List<DocumentSymbol> transformMembers(NodeList<? extends Node> nodes) {
        List<DocumentSymbol> childSymbols = new ArrayList<>();
        nodes.forEach(node -> {
            node.apply(this).ifPresent(childSymbols::add);
        });
        return childSymbols;
    }

    /**
     * Document symbol builder.
     *
     * @param name           symbol name.
     * @param kind           symbol kind.
     * @param detail         symbol detail.
     * @param range          Range of the symbol.
     * @param selectionRange selection range of the symbol.
     * @param isDeprecated   Whether the symbol is deprecated.
     * @param children       Child document symbols.
     * @param context        Document symbol context.
     * @return
     */
    public DocumentSymbol createDocumentSymbol(String name, SymbolKind kind,
                                               String detail, Range range,
                                               Range selectionRange, boolean isDeprecated,
                                               List<DocumentSymbol> children, DocumentSymbolContext context) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        DocumentSymbol documentSymbol = new DocumentSymbol();
        documentSymbol.setName(name);
        documentSymbol.setKind(kind);
        documentSymbol.setDetail(detail);
        documentSymbol.setRange(range);
        documentSymbol.setSelectionRange(selectionRange);
        if (isDeprecated && context.deprecatedSupport()) {
            documentSymbol.setTags(List.of(SymbolTag.Deprecated));
        }
        if (context.getHierarchicalDocumentSymbolSupport()) {
            documentSymbol.setChildren(children);
        } else {
            this.documentSymbolStore.add(documentSymbol);
        }
        return documentSymbol;
    }
}
