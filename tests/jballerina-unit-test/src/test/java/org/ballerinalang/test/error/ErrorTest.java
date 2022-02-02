/*
 *  Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.ballerinalang.test.error;

import io.ballerina.runtime.api.TypeTags;
import io.ballerina.runtime.api.utils.StringUtils;
import io.ballerina.runtime.api.values.BArray;
import io.ballerina.runtime.api.values.BError;
import io.ballerina.runtime.api.values.BMap;
import io.ballerina.runtime.internal.util.exceptions.BLangRuntimeException;
import org.ballerinalang.test.BAssertUtil;
import org.ballerinalang.test.BCompileUtil;
import org.ballerinalang.test.CompileResult;
import org.ballerinalang.test.JvmRunUtil;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.ballerina.runtime.api.utils.TypeUtils.getType;

/**
 * Test cases for Error.
 *
 * @since 0.985.0
 */
public class ErrorTest {

    private CompileResult errorTestResult;
    private CompileResult distinctErrorTestResult;
    private CompileResult negativeDistinctErrorRes;

    private static final String CONST_ERROR_REASON = "reason one";

    @BeforeClass
    public void setup() {
        errorTestResult = BCompileUtil.compile("test-src/error/error_test.bal");
        distinctErrorTestResult = BCompileUtil.compile("test-src/error/distinct_error_test.bal");
        negativeDistinctErrorRes = BCompileUtil.compile("test-src/error/distinct_error_test_negative.bal");
    }

    @Test
    public void testDistinctFooError() {
        JvmRunUtil.invoke(distinctErrorTestResult, "testFooError");
    }

    @Test
    public void testFunctionCallInDetailArgExpr() {
        JvmRunUtil.invoke(distinctErrorTestResult, "testFunctionCallInDetailArgExpr");
    }

    @Test
    public void testNegativeDistinctError() {
        int i = 0;
        BAssertUtil.validateError(negativeDistinctErrorRes, i++,
                "missing error detail arg for error detail field 'code'", 8, 13);
        BAssertUtil.validateError(negativeDistinctErrorRes, i++,
                "incompatible types: expected 'Foo', found 'error'", 11, 13);
        BAssertUtil.validateError(negativeDistinctErrorRes, i++,
                "incompatible types: expected 'Foo', found 'error'", 15, 12);
        Assert.assertEquals(negativeDistinctErrorRes.getErrorCount(), i);
    }

    @Test
    public void testIndirectErrorCtor() {
        Object returns = JvmRunUtil.invoke(errorTestResult, "testIndirectErrorConstructor");
        BArray errors = (BArray) returns;
        Assert.assertEquals(errors.size(), 4);
        Assert.assertEquals(errors.get(0).toString(), "error UserDefErrorTwoA (\"arg\",message=\"\",data={})");
        Assert.assertEquals(errors.get(1).toString(), "error UserDefErrorTwoA (\"arg\",message=\"\",data={})");
        Assert.assertEquals(errors.get(2), errors.get(0));
        Assert.assertEquals(errors.get(3), errors.get(1));
    }

    @Test
    public void errorConstructReasonTest() {
        JvmRunUtil.invoke(errorTestResult, "errorConstructReasonTest");
    }

    @Test
    public void errorConstructDetailTest() {
        JvmRunUtil.invoke(errorTestResult, "errorConstructDetailTest");
    }

    @Test
    public void errorPanicTest() {
        // Case without panic
        Object[] args = new Object[]{(10)};
        Object returns = JvmRunUtil.invoke(errorTestResult, "errorPanicTest", args);
        Assert.assertEquals(returns.toString(), "done");

        // Now panic
        args = new Object[]{(15)};
        Exception expectedException = null;
        try {
            JvmRunUtil.invoke(errorTestResult, "errorPanicTest", args);
        } catch (Exception e) {
            expectedException = e;
        }
        Assert.assertNotNull(expectedException);
        String message = ((BLangRuntimeException) expectedException).getMessage();

        Assert.assertEquals(message,
                "error: largeNumber {\"message\":\"large number\"}\n" +
                        "\tat error_test:errorPanicCallee(error_test.bal:64)\n" +
                        "\t   error_test:errorPanicTest(error_test.bal:58)");
    }

    @Test
    public void errorTrapTest() {
        // Case without panic
        Object[] args = new Object[]{(10)};
        JvmRunUtil.invoke(errorTestResult, "errorTrapTest", args);

        // Now panic
        args = new Object[]{(15)};
        JvmRunUtil.invoke(errorTestResult, "errorTrapTest", args);
    }

    @Test
    public void customErrorDetailsTest() {
        Object returns = JvmRunUtil.invoke(errorTestResult, "testCustomErrorDetails");
        Assert.assertEquals(returns.toString(), "error TrxError (\"trxErr\",message=\"\",data=\"test\")");
        Assert.assertEquals(getType(((BError) returns).getDetails()).getTag(), TypeTags.RECORD_TYPE_TAG);
        Assert.assertEquals(getType(((BError) returns).getDetails()).getName(), "(TrxErrorData & readonly)");
    }

    @Test
    public void testCustomErrorDetails2() {
        Object returns = JvmRunUtil.invoke(errorTestResult, "testCustomErrorDetails2");
        Assert.assertEquals(returns.toString(), "test");
    }

    @Test
    public void testErrorWithErrorConstructor() {
        Object returns = JvmRunUtil.invoke(errorTestResult, "testErrorWithErrorConstructor");
        Assert.assertEquals(returns.toString(), "test");
    }

    @Test
    public void testConsecutiveTraps() {
        Object returns = JvmRunUtil.invoke(errorTestResult, "testConsecutiveTraps");
        Assert.assertEquals(returns.toString(), "[\"Error\",\"Error\"]");
    }

    @Test
    public void testOneLinePanic() {
        Object returns = JvmRunUtil.invoke(errorTestResult, "testOneLinePanic");
        Assert.assertTrue(returns instanceof BArray);
        BArray array = (BArray) returns;
        Assert.assertEquals(array.getString(0), "Error1");
        Assert.assertEquals(array.getString(1), "Error2");
        Assert.assertEquals(array.getString(2), "Something Went Wrong");
        Assert.assertEquals(array.getString(3), "Error3");
        Assert.assertEquals(array.getString(4), "Something Went Wrong");
        Assert.assertEquals(array.getString(5), "1");
    }

    @Test
    public void testGenericErrorWithDetailRecord() {
        Object returns = JvmRunUtil.invoke(errorTestResult, "testGenericErrorWithDetailRecord");
        Assert.assertTrue(returns instanceof Boolean);
        Assert.assertTrue((Boolean) returns);
    }

    @Test
    public void testTrapSuccessScenario() {
        Object returns = JvmRunUtil.invoke(errorTestResult, "testTrapWithSuccessScenario");
        Assert.assertTrue(returns instanceof Long);
        Assert.assertEquals(returns, 1L);
    }

    @Test(dataProvider = "userDefTypeAsReasonTests")
    public void testErrorWithUserDefinedReasonType(String testFunction) {
        JvmRunUtil.invoke(errorTestResult, testFunction);
    }

    @Test(dataProvider = "constAsReasonTests")
    public void testErrorWithConstantAsReason(String testFunction) {
        Object returns = JvmRunUtil.invoke(errorTestResult, testFunction);
        Assert.assertTrue(returns instanceof BError);
        Assert.assertEquals(((BError) returns).getMessage(), CONST_ERROR_REASON);
        Assert.assertEquals(
                ((BMap) ((BError) returns).getDetails()).get(StringUtils.fromString("message")).toString(),
                "error detail message");
    }

//    @Test
//    public void testCustomErrorWithMappingOfSelf() {
//        Object returns = JvmRunUtil.invoke(errorTestResult, "testCustomErrorWithMappingOfSelf");
//        Assert.assertTrue(returns instanceof Boolean);
//        Assert.assertTrue(returns);
//    }

    @Test
    public void testUnspecifiedErrorDetailFrozenness() {
        Object returns = JvmRunUtil.invoke(errorTestResult, "testUnspecifiedErrorDetailFrozenness");
        Assert.assertTrue(returns instanceof Boolean);
        Assert.assertTrue((Boolean) returns);
    }

    @Test
    public void testLocalErrorTypeWithClosure() {
        JvmRunUtil.invoke(errorTestResult, "testLocalErrorTypeWithClosure");
    }

    @Test
    public void testLocalErrorTypeWithinLambda() {
        JvmRunUtil.invoke(errorTestResult, "testLocalErrorTypeWithinLambda");
    }

    @Test
    public void testErrorNegative() {
        CompileResult negativeCompileResult = BCompileUtil.compile("test-src/error/error_test_negative.bal");
        int i = 0;
        BAssertUtil.validateError(negativeCompileResult, i++,
                "invalid error detail type 'map<any>', expected a subtype of " +
                        "'map<ballerina/lang.value:0.0.0:Cloneable>'", 41, 28);
        BAssertUtil.validateError(negativeCompileResult, i++,
                "invalid error detail type 'boolean', expected a subtype of '" +
                        "map<ballerina/lang.value:0.0.0:Cloneable>'", 42, 28);
        BAssertUtil.validateError(negativeCompileResult, i++,
                "error constructor does not accept additional detail args 'one' when error detail type 'Foo' " +
                        "contains individual field descriptors", 45, 58);
        BAssertUtil.validateError(negativeCompileResult, i++,
                "invalid error detail type 'boolean', expected a subtype of " +
                        "'map<ballerina/lang.value:0.0.0:Cloneable>'", 48, 11);
        BAssertUtil.validateError(negativeCompileResult, i++,
                "incompatible types: expected 'string', found 'boolean'", 48, 30);
        BAssertUtil.validateError(negativeCompileResult, i++, "self referenced variable 'e3'", 54, 22);
        BAssertUtil.validateError(negativeCompileResult, i++, "self referenced variable 'e3'", 54, 36);
        BAssertUtil.validateError(negativeCompileResult, i++, "self referenced variable 'e4'", 55, 34);
        BAssertUtil.validateError(negativeCompileResult, i++, "missing arg within parenthesis", 56, 48);
        BAssertUtil.validateError(negativeCompileResult, i++, "missing arg within parenthesis", 57, 32);
        BAssertUtil.validateError(negativeCompileResult, i++, "error constructor does not accept additional detail " +
                "args 'other' when error detail type 'Bee' contains individual field descriptors", 95, 53);
        BAssertUtil.validateError(negativeCompileResult, i++, "missing error message in error constructor", 96, 32);
        BAssertUtil.validateError(negativeCompileResult, i++, "error constructor does not accept additional detail " +
                "args 'other' when error detail type 'Bee' contains individual field descriptors", 96, 60);
        BAssertUtil.validateError(negativeCompileResult, i++, "missing error message in error constructor", 97, 38);
        BAssertUtil.validateError(negativeCompileResult, i++, "error constructor does not accept additional detail " +
                "args 'other' when error detail type 'Bee' contains individual field descriptors", 97, 66);
        BAssertUtil.validateError(negativeCompileResult, i++,
                "incompatible types: expected 'error', found '(error|int)'", 118, 11);
        BAssertUtil.validateError(negativeCompileResult, i++,
                "incompatible types: expected 'error<record {| " +
                        "string message?; error cause?; int i; anydata...; |}>', found 'int'", 122, 65);
        BAssertUtil.validateError(negativeCompileResult, i++, "invalid error detail type 'string', expected a subtype" +
                " of 'map<ballerina/lang.value:0.0.0:Cloneable>'", 139, 11);
        BAssertUtil.validateError(negativeCompileResult, i++, "invalid token ','", 139, 17);
        BAssertUtil.validateError(negativeCompileResult, i++, "invalid token 'Detail'", 139, 19);
        BAssertUtil.validateError(negativeCompileResult, i++, "invalid error detail type 'string', expected a subtype" +
                " of 'map<ballerina/lang.value:0.0.0:Cloneable>'", 140, 11);
        BAssertUtil.validateError(negativeCompileResult, i++, "invalid error detail type 'int', expected a subtype of" +
                " 'map<ballerina/lang.value:0.0.0:Cloneable>'", 141, 11);
        BAssertUtil.validateError(negativeCompileResult, i++, "unknown error detail arg 'id' passed to closed error " +
                "detail type 'CloseDetail'", 143, 47);
        Assert.assertEquals(negativeCompileResult.getErrorCount(), i);
    }

    @DataProvider(name = "userDefTypeAsReasonTests")
    public Object[][] userDefTypeAsReasonTests() {
        return new Object[][]{
                {"testErrorConstrWithConstForUserDefinedReasonType"},
                {"testErrorConstrWithLiteralForUserDefinedReasonType"}
        };
    }

    @DataProvider(name = "constAsReasonTests")
    public Object[][] constAsReasonTests() {
        return new Object[][]{
                {"testErrorConstrWithConstForConstReason"},
                {"testErrorConstrWithConstLiteralForConstReason"}
        };
    }

    @Test()
    public void errorReasonSubtypeTest() {
        Object arr = JvmRunUtil.invoke(errorTestResult, "errorReasonSubType");
        BArray returns = (BArray) arr;
        Assert.assertEquals(((BError) returns.get(0)).getMessage(), "ErrNo-1");
        Assert.assertEquals(((BError) returns.get(1)).getMessage(), "ErrorNo-2");
        Assert.assertEquals(((BError) returns.get(2)).getMessage(), "ErrNo-1");
        Assert.assertEquals(((BError) returns.get(3)).getMessage(), "ErrorNo-2");
    }

    @Test()
    public void indirectErrorCtorTest() {
        Object returns = JvmRunUtil.invoke(errorTestResult, "indirectErrorCtor");
        Assert.assertEquals(returns.toString(), "[\"foo\",true,error FooError (\"foo\",code=3456)]");
    }

    @Test()
    public void testUnionLhsWithIndirectErrorRhs() {
        Object returns = JvmRunUtil.invoke(errorTestResult, "testUnionLhsWithIndirectErrorRhs");
        Assert.assertEquals(((BError) returns).getMessage(), "Foo");
    }

    @Test()
    public void testOptionalErrorReturn() {
        JvmRunUtil.invoke(errorTestResult, "testOptionalErrorReturn");
    }

    @Test()
    public void testIndirectErrorReturn() {
        Object returns = JvmRunUtil.invoke(errorTestResult, "testIndirectErrorReturn");
        Assert.assertEquals(returns.toString(), "error E1 (\"Foo\",message=\"error msg\")");
    }

    @Test
    public void testStackTraceInNative() {
        Exception expectedException = null;
        try {
            JvmRunUtil.invoke(errorTestResult, "testStackTraceInNative");
        } catch (Exception e) {
            expectedException = e;
        }

        Assert.assertNotNull(expectedException);
        String message = expectedException.getMessage();
        Assert.assertEquals(message, "error: array index out of range: index: 4, size: 2\n\t" +
                "at ballerina.lang.array.0:slice(array.bal:128)\n\t" +
                "   error_test:testStackTraceInNative(error_test.bal:339)");
    }

    @Test
    public void testPanicOnErrorUnion() {
        Object[] args = new Object[]{(0)};
        Object result = JvmRunUtil.invoke(errorTestResult, "testPanicOnErrorUnion", args);
        Assert.assertEquals(result.toString(), "str");
    }

    @Test(expectedExceptions = BLangRuntimeException.class, expectedExceptionsMessageRegExp = "error: x.*")
    public void testPanicOnErrorUnionCustomError() {
        Object[] args = new Object[]{(1)};
        JvmRunUtil.invoke(errorTestResult, "testPanicOnErrorUnion", args);
    }

    @Test(expectedExceptions = BLangRuntimeException.class, expectedExceptionsMessageRegExp = "error: y " +
            "\\{\"code\":4\\}.*")
    public void testPanicOnErrorUnionCustomError2() {
        Object[] args = new Object[]{(2)};
        JvmRunUtil.invoke(errorTestResult, "testPanicOnErrorUnion", args);
    }

    @Test
    public void testErrorUnionPassedToErrorParam() {
        Object result = JvmRunUtil.invoke(errorTestResult, "testErrorUnionPassedToErrorParam");
        Assert.assertEquals(result.toString(), "a1");
    }

    @Test
    public void testStackOverFlow() {
        Object result = JvmRunUtil.invoke(errorTestResult, "testStackOverFlow");
        BArray arr = (BArray) result;
        String expected1 = "{callableName:bar, moduleName:null, fileName:error_test.bal, lineNumber:408}";
        String expected2 = "{callableName:bar2, moduleName:null, fileName:error_test.bal, lineNumber:412}";
        String resultStack = ((BArray) arr.get(0)).getRefValue(0).toString();
        Assert.assertTrue(resultStack.equals(expected1) || resultStack.equals(expected2), "Received unexpected " +
                "stacktrace element: " + resultStack);
        Assert.assertEquals(arr.get(1).toString(), "{ballerina}StackOverflow");
    }

    @Test
    public void testErrorTrapVarReuse() {
        JvmRunUtil.invoke(errorTestResult, "testErrorTrapVarReuse");
    }

    @Test
    public void testErrorBindingPattern() {
        JvmRunUtil.invoke(errorTestResult, "testErrorBindingPattern");
    }

    @Test
    public void testStackTraceWithErrorCauseLocation() {
        Exception expectedException = null;
        try {
            JvmRunUtil.invoke(errorTestResult, "testStackTraceWithErrorCauseLocation");
        } catch (Exception e) {
            expectedException = e;
        }

        Assert.assertNotNull(expectedException);
        String message = expectedException.getMessage();
        Assert.assertEquals(message, "error: error1\n" +
                "\tat error_test:foo(error_test.bal:470)\n" +
                "\t   error_test:testStackTraceWithErrorCauseLocation(error_test.bal:466)\n" +
                "cause: error2\n" +
                "\tat error_test:baz(error_test.bal:479)\n" +
                "\t   error_test:x(error_test.bal:475)\n" +
                "\t   ... 2 more\n" +
                "cause: error3\n" +
                "\tat error_test:foobar(error_test.bal:484)\n" +
                "\t   ... 4 more");
    }

    @Test
    public void testStacktraceWithPanicInsideInitMethod() {
        Exception expectedException = null;
        try {
            JvmRunUtil.invoke(errorTestResult, "testStacktraceWithPanicInsideInitMethod");
        } catch (Exception e) {
            expectedException = e;
        }

        Assert.assertNotNull(expectedException);
        String message = expectedException.getMessage();
        Assert.assertEquals(message, "error: error\n" +
                "\tat Person:init(error_test.bal:495)\n" +
                "\t   error_test:testStacktraceWithPanicInsideInitMethod(error_test.bal:500)");
    }

    @Test
    public void testStacktraceWithPanicInsideAnonymousFunction() {
        Exception expectedException = null;
        try {
            JvmRunUtil.invoke(errorTestResult, "testStacktraceWithPanicInsideAnonymousFunction");
        } catch (Exception e) {
            expectedException = e;
        }

        Assert.assertNotNull(expectedException);
        String message = expectedException.getMessage();
        Assert.assertEquals(message, "error: error!!!\n" +
                "\tat error_test:$lambda$_2(error_test.bal:506)\n" +
                "\t   error_test:testStacktraceWithPanicInsideAnonymousFunction(error_test.bal:509)");
    }

    @AfterClass
    public void cleanup() {
        errorTestResult = null;
        distinctErrorTestResult = null;
        negativeDistinctErrorRes = null;
    }
}
