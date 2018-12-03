/*
*  Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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
package org.ballerinalang.util.codegen;

import org.ballerinalang.model.types.BType;
import org.ballerinalang.model.types.TypeTags;
import org.ballerinalang.util.codegen.attributes.AttributeInfo;
import org.ballerinalang.util.codegen.attributes.AttributeInfoPool;
import org.ballerinalang.util.codegen.attributes.LineNumberTableAttributeInfo;
import org.ballerinalang.util.codegen.cpentries.ConstantPool;
import org.ballerinalang.util.codegen.cpentries.ConstantPoolEntry;
import org.ballerinalang.util.exceptions.BallerinaException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * {@code PackageInfo} contains metadata of a Ballerina package entry in the program file.
 *
 * @since 0.87
 */
public class PackageInfo implements ConstantPool, AttributeInfoPool {

    public int orgNameCPIndex;
    public int nameCPIndex;
    public String pkgPath;
    public int versionCPIndex;
    public String pkgVersion;
    private FunctionInfo initFunctionInfo, startFunctionInfo, stopFunctionInfo, testInitFunctionInfo,
            testStartFunctionInfo, testStopFunctionInfo;

    // TODO TEMP Design PackageContext concept
    // This number is used as an index to the global memory area where package level variables are stored.
    public int pkgIndex;

    private ConstantPoolEntry[] constPool;
    private List<ConstantPoolEntry> constantPoolEntries = new ArrayList<>();

    private Instruction[] instructions;
    private List<Instruction> instructionList = new ArrayList<>();

    private Map<String, PackageVarInfo> globalVarInfoMap = new LinkedHashMap<>();

    private Map<String, FunctionInfo> functionInfoMap = new LinkedHashMap<>();

    private Map<String, ServiceInfo> serviceInfoMap = new HashMap<>();

    private Map<String, CustomTypeInfo> structureTypeInfoMap = new HashMap<>();

    private Map<AttributeInfo.Kind, AttributeInfo> attributeInfoMap = new HashMap<>();

    public Map<String, TypeDefInfo> typeDefInfoMap = new HashMap<>();

    // cache values.
    ProgramFile programFile;

    public int getPkgNameCPIndex() {
        return nameCPIndex;
    }

    public String getPkgPath() {
        return pkgPath;
    }

    public int getPackageVersionCPIndex() {
        return versionCPIndex;
    }

    public String getPackageVersion() {
        return pkgVersion;
    }

    public int addCPEntry(ConstantPoolEntry cpEntry) {
        if (constantPoolEntries.contains(cpEntry)) {
            return constantPoolEntries.indexOf(cpEntry);
        }

        constantPoolEntries.add(cpEntry);
        return constantPoolEntries.size() - 1;
    }

    public ConstantPoolEntry getCPEntry(int index) {
        return constantPoolEntries.get(index);
    }

    public int getCPEntryIndex(ConstantPoolEntry cpEntry) {
        return constantPoolEntries.indexOf(cpEntry);
    }

    public ConstantPoolEntry[] getConstPoolEntries() {
        return constPool;
    }

    public PackageVarInfo getPackageVarInfo(String globalVarName) {
        return globalVarInfoMap.get(globalVarName);
    }

    public void addPackageVarInfo(String globalVarName, PackageVarInfo packageVarInfo) {
        globalVarInfoMap.put(globalVarName, packageVarInfo);
    }

    public PackageVarInfo[] getPackageInfoEntries() {
        return globalVarInfoMap.values().toArray(new PackageVarInfo[0]);
    }

    public FunctionInfo getFunctionInfo(String functionName) {
        return functionInfoMap.get(functionName);
    }

    public void addFunctionInfo(String functionName, FunctionInfo functionInfo) {
        functionInfoMap.put(functionName, functionInfo);
    }

    public FunctionInfo[] getFunctionInfoEntries() {
        return functionInfoMap.values().toArray(new FunctionInfo[0]);
    }

    public StructureTypeInfo getStructInfo(String name) {
        TypeInfo typeInfo = typeDefInfoMap.get(name).typeInfo;
        if (typeInfo == null || (typeInfo.getType().getTag() != TypeTags.OBJECT_TYPE_TAG
                && typeInfo.getType().getTag() != TypeTags.RECORD_TYPE_TAG)) {
            throw new BallerinaException("structure - " + name + " does not exist");
        }
        return (StructureTypeInfo) typeDefInfoMap.get(name).typeInfo;
    }

    public TypeInfo getTypeInfo(String name) {
        return typeDefInfoMap.get(name).typeInfo;
    }

    public void addTypeDefInfo(String typeDefinitionName, TypeDefInfo typeDefinitionInfo) {
        typeDefInfoMap.put(typeDefinitionName, typeDefinitionInfo);
        structureTypeInfoMap.put(typeDefinitionName, typeDefinitionInfo);
    }

    public TypeDefInfo[] getTypeDefInfoEntries() {
        return typeDefInfoMap.values().toArray(new TypeDefInfo[0]);
    }

    public TypeDefInfo getTypeDefInfo(String typeDefName) {
        return typeDefInfoMap.get(typeDefName);
    }

    public ServiceInfo[] getServiceInfoEntries() {
        return serviceInfoMap.values().toArray(new ServiceInfo[0]);
    }

    public ServiceInfo getServiceInfo(String serviceName) {
        return serviceInfoMap.get(serviceName);
    }

    public ServiceInfo getServiceInfo(BType serviceType) {
        return serviceInfoMap.values().stream()
                .filter(serviceInfo -> serviceInfo.serviceType.getType().equals(serviceType)).findFirst().orElse(null);
    }

    public void addServiceInfo(String serviceName, ServiceInfo serviceInfo) {
        serviceInfo.setPackageInfo(this);
        serviceInfoMap.put(serviceName, serviceInfo);
    }

    public CustomTypeInfo getStructureTypeInfo(String structureTypeName) {
        return structureTypeInfoMap.get(structureTypeName);
    }

    public int addInstruction(Instruction instruction) {
        instructionList.add(instruction);
        return instructionList.size() - 1;
    }

    public Instruction[] getInstructions() {
        return instructions;
    }

    public int getInstructionCount() {
        return instructionList.size();
    }

    public LineNumberInfo getLineNumberInfo(LineNumberInfo lineNumberInfo) {
        LineNumberTableAttributeInfo lineNumberTableAttributeInfo = (LineNumberTableAttributeInfo) attributeInfoMap
                .get(AttributeInfo.Kind.LINE_NUMBER_TABLE_ATTRIBUTE);
        List<LineNumberInfo> lineNumberInfos = lineNumberTableAttributeInfo.getLineNumberInfoList();
        int index = lineNumberInfos.indexOf(lineNumberInfo);
        if (index >= 0) {
            return lineNumberInfos.get(index);
        }
        return null;
    }

    /**
     * This gets the line number info given the IP. The following example can be taken as a reference
     * to explain the below algorithm.
     *
     *     Code Line                           IP
     *     =======================================
     *     int a = 1 + 1;                      136
     *     runtime:CallStackElement trace1;    138
     *     runtime:CallStackElement trace2;    138
     *     myFunc(a + 1);                      138
     *     int x = 1 + 2;                      140
     *     int g = 1 + 3;                      142
     *
     * @param currentIP the current IP
     * @return the resolved line number information
     */
    public LineNumberInfo getLineNumberInfo(int currentIP) {
        LineNumberTableAttributeInfo lineNumberTableAttributeInfo = (LineNumberTableAttributeInfo) attributeInfoMap
                .get(AttributeInfo.Kind.LINE_NUMBER_TABLE_ATTRIBUTE);
        List<LineNumberInfo> lineNumberInfos = new ArrayList<>(lineNumberTableAttributeInfo.getLineNumberInfoList());
        Collections.reverse(lineNumberInfos);
        for (LineNumberInfo lineNumberInfo : lineNumberInfos) {
            if (currentIP >= lineNumberInfo.getIp()) {
                return lineNumberInfo;
            }
        }
        return null;
    }

    public ProgramFile getProgramFile() {
        return programFile;
    }

    public void setProgramFile(ProgramFile programFile) {
        this.programFile = programFile;
    }

    public FunctionInfo getInitFunctionInfo() {
        return initFunctionInfo;
    }

    public void setInitFunctionInfo(FunctionInfo initFunctionInfo) {
        this.initFunctionInfo = initFunctionInfo;
    }

    public FunctionInfo getStartFunctionInfo() {
        return startFunctionInfo;
    }

    public void setStartFunctionInfo(FunctionInfo startFunctionInfo) {
        this.startFunctionInfo = startFunctionInfo;
    }

    public FunctionInfo getStopFunctionInfo() {
        return stopFunctionInfo;
    }

    public void setStopFunctionInfo(FunctionInfo stopFunctionInfo) {
        this.stopFunctionInfo = stopFunctionInfo;
    }

    public void complete() {
        this.constPool = constantPoolEntries.toArray(new ConstantPoolEntry[0]);
        this.instructions = instructionList.toArray(new Instruction[0]);
    }

    public FunctionInfo getTestInitFunctionInfo() {
        return testInitFunctionInfo;
    }

    public void setTestInitFunctionInfo(FunctionInfo testInitFunctionInfo) {
        this.testInitFunctionInfo = testInitFunctionInfo;
    }

    public FunctionInfo getTestStartFunctionInfo() {
        return testStartFunctionInfo;
    }

    public void setTestStartFunctionInfo(FunctionInfo testStartFunctionInfo) {
        this.testStartFunctionInfo = testStartFunctionInfo;
    }

    public FunctionInfo getTestStopFunctionInfo() {
        return testStopFunctionInfo;
    }

    public void setTestStopFunctionInfo(FunctionInfo testStopFunctionInfo) {
        this.testStopFunctionInfo = testStopFunctionInfo;
    }

    @Override
    public AttributeInfo getAttributeInfo(AttributeInfo.Kind attributeKind) {
        return attributeInfoMap.get(attributeKind);
    }

    @Override
    public void addAttributeInfo(AttributeInfo.Kind attributeKind, AttributeInfo attributeInfo) {
        attributeInfoMap.put(attributeKind, attributeInfo);
    }

    @Override
    public AttributeInfo[] getAttributeInfoEntries() {
        return attributeInfoMap.values().toArray(new AttributeInfo[0]);
    }
}
