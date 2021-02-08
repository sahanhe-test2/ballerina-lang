/*
 *  Copyright (c) 2020, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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
package io.ballerina.compiler.internal.diagnostics;

import io.ballerina.tools.diagnostics.DiagnosticCode;
import io.ballerina.tools.diagnostics.DiagnosticSeverity;

/**
 * Represents a diagnostic error code.
 *
 * @since 2.0.0
 */
public enum DiagnosticErrorCode implements DiagnosticCode {

    // The member represents a generic syntax error
    // We should use this only when we can't figure out the exact error
    ERROR_SYNTAX_ERROR("BCE0000", "error.syntax.error"),

    // Missing tokens
    ERROR_MISSING_TOKEN("BCE0001", "error.missing.token"),
    ERROR_MISSING_SEMICOLON_TOKEN("BCE0002", "error.missing.semicolon.token"),
    ERROR_MISSING_COLON_TOKEN("BCE0003", "error.missing.colon.token"),
    ERROR_MISSING_OPEN_PAREN_TOKEN("BCE0004", "error.missing.open.paren.token"),
    ERROR_MISSING_CLOSE_PAREN_TOKEN("BCE0005", "error.missing.close.paren.token"),
    ERROR_MISSING_OPEN_BRACE_TOKEN("BCE0006", "error.missing.open.brace.token"),
    ERROR_MISSING_CLOSE_BRACE_TOKEN("BCE0007", "error.missing.close.brace.token"),
    ERROR_MISSING_OPEN_BRACKET_TOKEN("BCE0008", "error.missing.open.bracket.token"),
    ERROR_MISSING_CLOSE_BRACKET_TOKEN("BCE0009", "error.missing.close.bracket.token"),
    ERROR_MISSING_EQUAL_TOKEN("BCE0010", "error.missing.equal.token"),
    ERROR_MISSING_COMMA_TOKEN("BCE0011", "error.missing.comma.token"),
    ERROR_MISSING_PLUS_TOKEN("BCE0012", "error.missing.plus.token"),
    ERROR_MISSING_SLASH_TOKEN("BCE0013", "error.missing.slash.token"),
    ERROR_MISSING_AT_TOKEN("BCE0014", "error.missing.at.token"),
    ERROR_MISSING_QUESTION_MARK_TOKEN("BCE0015", "error.missing.question.mark.token"),
    ERROR_MISSING_GT_TOKEN("BCE0016", "error.missing.gt.token"),
    ERROR_MISSING_GT_EQUAL_TOKEN("BCE0017", "error.missing.gt.equal.token"),
    ERROR_MISSING_LT_TOKEN("BCE0018", "error.missing.lt.token"),
    ERROR_MISSING_LT_EQUAL_TOKEN("BCE0019", "error.missing.lt.equal.token"),
    ERROR_MISSING_RIGHT_DOUBLE_ARROW_TOKEN("BCE0020", "error.missing.right.double.arrow.token"),
    ERROR_MISSING_XML_COMMENT_END_TOKEN("BCE0021", "error.missing.xml.comment.end.token"),
    ERROR_MISSING_XML_PI_END_TOKEN("BCE0022", "error.missing.xml.pi.end.token"),
    ERROR_MISSING_DOUBLE_QUOTE_TOKEN("BCE0023", "error.missing.double.quote.token"),
    ERROR_MISSING_BACKTICK_TOKEN("BCE0024", "error.missing.backtick.token"),
    ERROR_MISSING_OPEN_BRACE_PIPE_TOKEN("BCE0025", "error.missing.open.brace.pipe.token"),
    ERROR_MISSING_CLOSE_BRACE_PIPE_TOKEN("BCE0026", "error.missing.close.brace.pipe.token"),
    ERROR_MISSING_ASTERISK_TOKEN("BCE0027", "error.missing.asterisk.token"),
    ERROR_MISSING_PIPE_TOKEN("BCE0028", "error.missing.pipe.token"),
    ERROR_MISSING_DOT_TOKEN("BCE0029", "error.missing.dot.token"),
    ERROR_MISSING_ELLIPSIS_TOKEN("BCE0030", "error.missing.ellipsis.token"),
    ERROR_MISSING_HASH_TOKEN("BCE0031", "error.missing.hash.token"),
    ERROR_MISSING_SINGLE_QUOTE_TOKEN("BCE0032", "error.missing.single.quote.token"),
    ERROR_MISSING_DOUBLE_EQUAL_TOKEN("BCE0033", "error.missing.double.equal.token"),
    ERROR_MISSING_TRIPPLE_EQUAL_TOKEN("BCE0034", "error.missing.tripple.equal.token"),
    ERROR_MISSING_MINUS_TOKEN("BCE0035", "error.missing.minus.token"),
    ERROR_MISSING_PERCENT_TOKEN("BCE0036", "error.missing.percent.token"),
    ERROR_MISSING_EXCLAMATION_MARK_TOKEN("BCE0037", "error.missing.exclamation.mark.token"),
    ERROR_MISSING_NOT_EQUAL_TOKEN("BCE0038", "error.missing.not.equal.token"),
    ERROR_MISSING_NOT_DOUBLE_EQUAL_TOKEN("BCE0039", "error.missing.not.double.equal.token"),
    ERROR_MISSING_BITWISE_AND_TOKEN("BCE0040", "error.missing.bitwise.and.token"),
    ERROR_MISSING_BITWISE_XOR_TOKEN("BCE0041", "error.missing.bitwise.xor.token"),
    ERROR_MISSING_LOGICAL_AND_TOKEN("BCE0042", "error.missing.logical.and.token"),
    ERROR_MISSING_LOGICAL_OR_TOKEN("BCE0043", "error.missing.logical.or.token"),
    ERROR_MISSING_NEGATION_TOKEN("BCE0044", "error.missing.negation.token"),
    ERROR_MISSING_RIGHT_ARROW_TOKEN("BCE0045", "error.missing.right.arrow.token"),
    ERROR_MISSING_INTERPOLATION_START_TOKEN("BCE0046", "error.missing.interpolation.start.token"),
    ERROR_MISSING_XML_PI_START_TOKEN("BCE0047", "error.missing.xml.pi.start.token"),
    ERROR_MISSING_XML_COMMENT_START_TOKEN("BCE0048", "error.missing.xml.comment.start.token"),
    ERROR_MISSING_SYNC_SEND_TOKEN("BCE0049", "error.missing.sync.send.token"),
    ERROR_MISSING_LEFT_ARROW_TOKEN("BCE0050", "error.missing.left.arrow.token"),
    ERROR_MISSING_DOUBLE_DOT_LT_TOKEN("BCE0051", "error.missing.double.dot.lt.token"),
    ERROR_MISSING_DOUBLE_LT_TOKEN("BCE0052", "error.missing.double.lt.token"),
    ERROR_MISSING_ANNOT_CHAINING_TOKEN("BCE0053", "error.missing.annot.chaining.token"),
    ERROR_MISSING_OPTIONAL_CHAINING_TOKEN("BCE0054", "error.missing.optional.chaining.token"),
    ERROR_MISSING_ELVIS_TOKEN("BCE0055", "error.missing.elvis.token"),
    ERROR_MISSING_DOT_LT_TOKEN("BCE0056", "error.missing.dot.lt.token"),
    ERROR_MISSING_SLASH_LT_TOKEN("BCE0057", "error.missing.slash.lt.token"),
    ERROR_MISSING_DOUBLE_SLASH_DOUBLE_ASTERISK_LT_TOKEN("BCE0058",
            "error.missing.double.slash.double.asterisk.lt.token"),
    ERROR_MISSING_SLASH_ASTERISK_TOKEN("BCE0059", "error.missing.slash.asterisk.token"),
    ERROR_MISSING_DOUBLE_GT_TOKEN("BCE0060", "error.missing.double.gt.token"),
    ERROR_MISSING_TRIPPLE_GT_TOKEN("BCE0061", "error.missing.tripple.gt.token"),

    // Missing keywords
    ERROR_MISSING_PUBLIC_KEYWORD("BCE0200", "error.missing.public.keyword"),
    ERROR_MISSING_PRIVATE_KEYWORD("BCE0201", "error.missing.private.keyword"),
    ERROR_MISSING_REMOTE_KEYWORD("BCE0202", "error.missing.remote.keyword"),
    ERROR_MISSING_ABSTRACT_KEYWORD("BCE0203", "error.missing.abstract.keyword"),
    ERROR_MISSING_CLIENT_KEYWORD("BCE0204", "error.missing.client.keyword"),
    ERROR_MISSING_LISTENER_KEYWORD("BCE0205", "error.missing.listener.keyword"),
    ERROR_MISSING_XMLNS_KEYWORD("BCE0206", "error.missing.xmlns.keyword"),
    ERROR_MISSING_RESOURCE_KEYWORD("BCE0207", "error.missing.resource.keyword"),
    ERROR_MISSING_FINAL_KEYWORD("BCE0208", "error.missing.final.keyword"),
    ERROR_MISSING_WORKER_KEYWORD("BCE0209", "error.missing.worker.keyword"),
    ERROR_MISSING_PARAMETER_KEYWORD("BCE0210", "error.missing.parameter.keyword"),
    ERROR_MISSING_RETURNS_KEYWORD("BCE0211", "error.missing.returns.keyword"),
    ERROR_MISSING_RETURN_KEYWORD("BCE0212", "error.missing.return.keyword"),
    ERROR_MISSING_TRUE_KEYWORD("BCE0213", "error.missing.true.keyword"),
    ERROR_MISSING_FALSE_KEYWORD("BCE0214", "error.missing.false.keyword"),
    ERROR_MISSING_ELSE_KEYWORD("BCE0215", "error.missing.else.keyword"),
    ERROR_MISSING_WHILE_KEYWORD("BCE0216", "error.missing.while.keyword"),
    ERROR_MISSING_CHECK_KEYWORD("BCE0217", "error.missing.check.keyword"),
    ERROR_MISSING_CHECKPANIC_KEYWORD("BCE0218", "error.missing.checkpanic.keyword"),
    ERROR_MISSING_PANIC_KEYWORD("BCE0219", "error.missing.panic.keyword"),
    ERROR_MISSING_CONTINUE_KEYWORD("BCE0220", "error.missing.continue.keyword"),
    ERROR_MISSING_BREAK_KEYWORD("BCE0221", "error.missing.break.keyword"),
    ERROR_MISSING_TYPEOF_KEYWORD("BCE0222", "error.missing.typeof.keyword"),
    ERROR_MISSING_IS_KEYWORD("BCE0223", "error.missing.is.keyword"),
    ERROR_MISSING_NULL_KEYWORD("BCE0224", "error.missing.null.keyword"),
    ERROR_MISSING_LOCK_KEYWORD("BCE0225", "error.missing.lock.keyword"),
    ERROR_MISSING_FORK_KEYWORD("BCE0226", "error.missing.fork.keyword"),
    ERROR_MISSING_TRAP_KEYWORD("BCE0227", "error.missing.trap.keyword"),
    ERROR_MISSING_FOREACH_KEYWORD("BCE0228", "error.missing.foreach.keyword"),
    ERROR_MISSING_NEW_KEYWORD("BCE0229", "error.missing.new.keyword"),
    ERROR_MISSING_WHERE_KEYWORD("BCE0230", "error.missing.where.keyword"),
    ERROR_MISSING_SELECT_KEYWORD("BCE0231", "error.missing.select.keyword"),
    ERROR_MISSING_START_KEYWORD("BCE0232", "error.missing.start.keyword"),
    ERROR_MISSING_FLUSH_KEYWORD("BCE0233", "error.missing.flush.keyword"),
    ERROR_MISSING_WAIT_KEYWORD("BCE0234", "error.missing.wait.keyword"),
    ERROR_MISSING_DO_KEYWORD("BCE0235", "error.missing.do.keyword"),
    ERROR_MISSING_TRANSACTION_KEYWORD("BCE0236", "error.missing.transaction.keyword"),
    ERROR_MISSING_TRANSACTIONAL_KEYWORD("BCE0237", "error.missing.transactional.keyword"),
    ERROR_MISSING_COMMIT_KEYWORD("BCE0238", "error.missing.commit.keyword"),
    ERROR_MISSING_ROLLBACK_KEYWORD("BCE0239", "error.missing.rollback.keyword"),
    ERROR_MISSING_RETRY_KEYWORD("BCE0240", "error.missing.retry.keyword"),
    ERROR_MISSING_BASE16_KEYWORD("BCE0241", "error.missing.base16.keyword"),
    ERROR_MISSING_BASE64_KEYWORD("BCE0242", "error.missing.base64.keyword"),
    ERROR_MISSING_MATCH_KEYWORD("BCE0243", "error.missing.match.keyword"),
    ERROR_MISSING_DEFAULT_KEYWORD("BCE0244", "error.missing.default.keyword"),
    ERROR_MISSING_TYPE_KEYWORD("BCE0245", "error.missing.type.keyword"),
    ERROR_MISSING_ON_KEYWORD("BCE0246", "error.missing.on.keyword"),
    ERROR_MISSING_ANNOTATION_KEYWORD("BCE0247", "error.missing.annotation.keyword"),
    ERROR_MISSING_FUNCTION_KEYWORD("BCE0248", "error.missing.function.keyword"),
    ERROR_MISSING_SOURCE_KEYWORD("BCE0249", "error.missing.source.keyword"),
    ERROR_MISSING_ENUM_KEYWORD("BCE0250", "error.missing.enum.keyword"),
    ERROR_MISSING_FIELD_KEYWORD("BCE0251", "error.missing.field.keyword"),
    ERROR_MISSING_VERSION_KEYWORD("BCE0252", "error.missing.version.keyword"),
    ERROR_MISSING_OBJECT_KEYWORD("BCE0253", "error.missing.object.keyword"),
    ERROR_MISSING_RECORD_KEYWORD("BCE0254", "error.missing.record.keyword"),
    ERROR_MISSING_SERVICE_KEYWORD("BCE0255", "error.missing.service.keyword"),
    ERROR_MISSING_AS_KEYWORD("BCE0256", "error.missing.as.keyword"),
    ERROR_MISSING_LET_KEYWORD("BCE0257", "error.missing.let.keyword"),
    ERROR_MISSING_TABLE_KEYWORD("BCE0258", "error.missing.table.keyword"),
    ERROR_MISSING_KEY_KEYWORD("BCE0259", "error.missing.key.keyword"),
    ERROR_MISSING_FROM_KEYWORD("BCE0260", "error.missing.from.keyword"),
    ERROR_MISSING_IN_KEYWORD("BCE0261", "error.missing.in.keyword"),
    ERROR_MISSING_IF_KEYWORD("BCE0262", "error.missing.if.keyword"),
    ERROR_MISSING_IMPORT_KEYWORD("BCE0263", "error.missing.import.keyword"),
    ERROR_MISSING_CONST_KEYWORD("BCE0264", "error.missing.const.keyword"),
    ERROR_MISSING_EXTERNAL_KEYWORD("BCE0265", "error.missing.external.keyword"),
    ERROR_MISSING_ORDER_KEYWORD("BCE0266", "error.missing.order.keyword"),
    ERROR_MISSING_BY_KEYWORD("BCE0267", "error.missing.by.keyword"),
    ERROR_MISSING_CONFLICT_KEYWORD("BCE0268", "error.missing.conflict.keyword"),
    ERROR_MISSING_LIMIT_KEYWORD("BCE0269", "error.missing.limit.keyword"),
    ERROR_MISSING_ASCENDING_KEYWORD("BCE0270", "error.missing.ascending.keyword"),
    ERROR_MISSING_DESCENDING_KEYWORD("BCE0271", "error.missing.descending.keyword"),
    ERROR_MISSING_JOIN_KEYWORD("BCE0272", "error.missing.join.keyword"),
    ERROR_MISSING_OUTER_KEYWORD("BCE0273", "error.missing.outer.keyword"),
    ERROR_MISSING_CLASS_KEYWORD("BCE0274", "error.missing.class.keyword"),
    ERROR_MISSING_FAIL_KEYWORD("BCE0275", "error.missing.fail.keyword"),
    ERROR_MISSING_EQUALS_KEYWORD("BCE0276", "error.missing.equals.keyword"),
    ERROR_MISSING_INT_KEYWORD("BCE0277", "error.missing.int.keyword"),
    ERROR_MISSING_BYTE_KEYWORD("BCE0278", "error.missing.byte.keyword"),
    ERROR_MISSING_FLOAT_KEYWORD("BCE0279", "error.missing.float.keyword"),
    ERROR_MISSING_DECIMAL_KEYWORD("BCE0280", "error.missing.decimal.keyword"),
    ERROR_MISSING_STRING_KEYWORD("BCE0281", "error.missing.string.keyword"),
    ERROR_MISSING_BOOLEAN_KEYWORD("BCE0282", "error.missing.boolean.keyword"),
    ERROR_MISSING_XML_KEYWORD("BCE0283", "error.missing.xml.keyword"),
    ERROR_MISSING_JSON_KEYWORD("BCE0284", "error.missing.json.keyword"),
    ERROR_MISSING_HANDLE_KEYWORD("BCE0285", "error.missing.handle.keyword"),
    ERROR_MISSING_ANY_KEYWORD("BCE0286", "error.missing.any.keyword"),
    ERROR_MISSING_ANYDATA_KEYWORD("BCE0287", "error.missing.anydata.keyword"),
    ERROR_MISSING_NEVER_KEYWORD("BCE0288", "error.missing.never.keyword"),
    ERROR_MISSING_VAR_KEYWORD("BCE0289", "error.missing.var.keyword"),
    ERROR_MISSING_MAP_KEYWORD("BCE0290", "error.missing.map.keyword"),
    ERROR_MISSING_FUTURE_KEYWORD("BCE0291", "error.missing.future.keyword"),
    ERROR_MISSING_TYPEDESC_KEYWORD("BCE0292", "error.missing.typedesc.keyword"),
    ERROR_MISSING_ERROR_KEYWORD("BCE0293", "error.missing.error.keyword"),
    ERROR_MISSING_STREAM_KEYWORD("BCE0294", "error.missing.stream.keyword"),
    ERROR_MISSING_READONLY_KEYWORD("BCE0295", "error.missing.readonly.keyword"),
    ERROR_MISSING_DISTINCT_KEYWORD("BCE0296", "error.missing.distinct.keyword"),

    // Missing other tokens
    ERROR_MISSING_IDENTIFIER("BCE0400", "error.missing.identifier"),
    ERROR_MISSING_STRING_LITERAL("BCE0401", "error.missing.string.literal"),
    ERROR_MISSING_DECIMAL_INTEGER_LITERAL("BCE0402", "error.missing.decimal.integer.literal"),
    ERROR_MISSING_HEX_INTEGER_LITERAL("BCE0403", "error.missing.hex.integer.literal"),
    ERROR_MISSING_DECIMAL_FLOATING_POINT_LITERAL("BCE0404", "error.missing.decimal.floating.point.literal"),
    ERROR_MISSING_HEX_FLOATING_POINT_LITERAL("BCE0405", "error.missing.hex.floating.point.literal"),
    ERROR_MISSING_XML_TEXT_CONTENT("BCE0406", "error.missing.xml.text.content"),
    ERROR_MISSING_TEMPLATE_STRING("BCE0407", "error.missing.template.string"),
    ERROR_MISSING_BYTE_ARRAY_CONTENT("BCE0408", "error.missing.byte.array.content"),
    ERROR_MISSING_DIGIT_AFTER_EXPONENT_INDICATOR("BCE0409", "error.missing.digit.after.exponent.indicator"),
    ERROR_MISSING_HEX_DIGIT_AFTER_DOT("BCE0410", "error.missing.hex.digit.after.dot"),
    ERROR_MISSING_DOUBLE_QUOTE("BCE0411", "error.missing.double.quote"),
    ERROR_MISSING_ENTITY_REFERENCE_NAME("BCE0412", "error.missing.entity.reference.name"),
    ERROR_MISSING_SEMICOLON_IN_XML_REFERENCE("BCE0413", "error.missing.semicolon.in.xml.reference"),

    // Missing non-terminal nodes
    ERROR_MISSING_FUNCTION_NAME("BCE0500", "error.missing.function.name"),
    ERROR_MISSING_TYPE_DESC("BCE0501", "error.missing.type.desc"),
    ERROR_MISSING_EXPRESSION("BCE0502", "error.missing.expression"),
    ERROR_MISSING_SELECT_CLAUSE("BCE0503", "error.missing.select.clause"),
    ERROR_MISSING_RECEIVE_FIELD_IN_RECEIVE_ACTION("BCE0504", "error.missing.receive.field.in.receive.action"),
    ERROR_MISSING_WAIT_FIELD_IN_WAIT_ACTION("BCE0505", "error.missing.wait.field.in.wait.action"),
    ERROR_MISSING_WAIT_FUTURE_EXPRESSION("BCE0506", "error.missing.wait.future.expression"),
    ERROR_MISSING_ENUM_MEMBER("BCE0507", "error.missing.enum.member"),
    ERROR_MISSING_XML_ATOMIC_NAME_PATTERN("BCE0508", "error.missing.xml.atomic.name.pattern"),
    ERROR_MISSING_TUPLE_MEMBER("BCE0509", "error.missing.tuple.member"),
    ERROR_MISSING_ORDER_KEY("BCE0510", "error.missing.order.key"),
    ERROR_MISSING_ANNOTATION_ATTACH_POINT("BCE0511", "error.missing.annotation.attach.point"),
    ERROR_MISSING_LET_VARIABLE_DECLARATION("BCE0512", "error.missing.let.variable.declaration"),
    ERROR_MISSING_NAMED_WORKER_DECLARATION_IN_FORK_STMT("BCE0513",
            "error.missing.named.worker.declaration.in.fork.stmt"),
    ERROR_MISSING_KEY_EXPR_IN_MEMBER_ACCESS_EXPR("BCE0514", "error.missing.key.expr.in.member.access.expr"),
    ERROR_MISSING_ERROR_MESSAGE_BINDING_PATTERN("BCE0515", "error.missing.error.message.binding.pattern"),
    ERROR_CONFIGURABLE_VARIABLE_MUST_BE_INITIALIZED("BCE0516",
            "error.configurable.variable.must.be.initialized"),
    ERROR_MISSING_RESOURCE_PATH_IN_RESOURCE_ACCESSOR_DEFINITION("BCE0517",
            "error.missing.resource.path.in.resource.accessor.definition"),
    ERROR_MISSING_RESOURCE_PATH_IN_RESOURCE_ACCESSOR_DECLARATION("BCE0518",
            "error.missing.resource.path.in.resource.accessor.declaration"),
    ERROR_MISSING_POSITIONAL_ARG_IN_ERROR_CONSTRUCTOR("BCE0519", "error.missing.positional.arg.in.error.constructor"),
    ERROR_MISSING_ARG_WITHIN_PARENTHESIS("BCE0520", "error.missing.arg.within.parenthesis"),

    // Invalid nodes
    ERROR_INVALID_TOKEN("BCE0600", "error.invalid.token"),
    ERROR_EXPRESSION_EXPECTED_ACTION_FOUND("BCE0601", "error.expression.expected.action.found"),
    ERROR_ONLY_TYPE_REFERENCE_ALLOWED_AS_TYPE_INCLUSIONS("BCE0602",
            "error.only.type.reference.allowed.as.type.inclusions"),
    ERROR_NAMED_WORKER_NOT_ALLOWED_HERE("BCE0603", "error.named.worker.not.allowed.here"),
    ERROR_ONLY_NAMED_WORKERS_ALLOWED_HERE("BCE0604", "error.only.named.workers.allowed.here"),
    ERROR_IMPORT_DECLARATION_AFTER_OTHER_DECLARATIONS("BCE0605", "error.import.declaration.after.other.declarations"),
    ERROR_ANNOTATIONS_ATTACHED_TO_EXPRESSION("BCE0606", "error.annotations.attached.to.expression"),
    ERROR_INVALID_EXPRESSION_IN_START_ACTION("BCE0607", "error.invalid.expression.in.start.action"),
    ERROR_DUPLICATE_QUALIFIER("BCE0608", "error.duplicate.qualifier"),
    ERROR_QUALIFIER_NOT_ALLOWED("BCE0609", "error.qualifier.not.allowed"),
    ERROR_TYPE_INCLUSION_IN_OBJECT_CONSTRUCTOR("BCE0610", "error.type.inclusion.in.object.constructor"),
    ERROR_MAPPING_CONSTRUCTOR_EXPR_AS_A_WAIT_EXPR("BCE0611", "error.mapping.constructor.expr.as.a.wait.expr"),
    ERROR_INVALID_PARAM_LIST_IN_INFER_ANONYMOUS_FUNCTION_EXPR("BCE0612",
            "error.invalid.param.list.in.infer.anonymous.function.expr"),
    ERROR_MORE_RECORD_FIELDS_AFTER_REST_FIELD("BCE0613", "error.more.record.fields.after.rest.field"),
    ERROR_INVALID_XML_NAMESPACE_URI("BCE0614", "error.invalid.xml.namespace.uri"),
    ERROR_INTERPOLATION_IS_NOT_ALLOWED_FOR_XML_TAG_NAMES("BCE0615",
            "error.interpolation.is.not.allowed.for.xml.tag.names"),
    ERROR_INTERPOLATION_IS_NOT_ALLOWED_WITHIN_ELEMENT_TAGS("BCE0616",
            "error.interpolation.is.not.allowed.within.element.tags"),
    ERROR_INTERPOLATION_IS_NOT_ALLOWED_WITHIN_XML_COMMENTS("BCE0617",
            "error.interpolation.is.not.allowed.within.xml.comments"),
    ERROR_INTERPOLATION_IS_NOT_ALLOWED_WITHIN_XML_PI("BCE0618", "error.interpolation.is.not.allowed.within.xml.pi"),
    ERROR_INVALID_EXPR_IN_ASSIGNMENT_LHS("BCE0619", "error.invalid.expr.in.assignment.lhs"),
    ERROR_INVALID_EXPR_IN_COMPOUND_ASSIGNMENT_LHS("BCE0620", "error.invalid.expr.in.compound.assignment.lhs"),
    ERROR_INVALID_METADATA("BCE0621", "error.invalid.metadata"),
    ERROR_INVALID_QUALIFIER("BCE0622", "error.invalid.qualifier"),
    ERROR_ANNOTATIONS_ATTACHED_TO_STATEMENT("BCE0623", "error.annotations.attached.to.statement"),
    ERROR_ACTION_AS_A_WAIT_EXPR("BCE0625", "error.action.as.a.wait.expr"),
    ERROR_INVALID_USAGE_OF_VAR("BCE0626", "error.invalid.usage.of.var"),
    ERROR_MATCH_PATTERN_AFTER_REST_MATCH_PATTERN("BCE0627", "error.match.pattern.after.rest.match.pattern"),
    ERROR_MATCH_PATTERN_NOT_ALLOWED("BCE0628", "error.match.pattern.not.allowed"),
    ERROR_MATCH_STATEMENT_SHOULD_HAVE_ONE_OR_MORE_MATCH_CLAUSES("BCE0629",
            "error.match.statement.should.have.one.or.more.match.clauses"),
    ERROR_PARAMETER_AFTER_THE_REST_PARAMETER("BCE0630", "error.parameter.after.the.rest.parameter"),
    ERROR_REQUIRED_PARAMETER_AFTER_THE_DEFAULTABLE_PARAMETER("BCE0631",
            "error.required.parameter.after.the.defaultable.parameter"),
    ERROR_NAMED_ARG_FOLLOWED_BY_POSITIONAL_ARG("BCE0632", "error.named.arg.followed.by.positional.arg"),
    ERROR_REST_ARG_FOLLOWED_BY_ANOTHER_ARG("BCE0633", "error.rest.arg.followed.by.another.arg"),
    ERROR_BINDING_PATTERN_NOT_ALLOWED("BCE0634", "error.binding.pattern.not.allowed"),
    ERROR_INVALID_BASE16_CONTENT_IN_BYTE_ARRAY_LITERAL("BCE0635", "error.invalid.base16.content.in.byte.array.literal"),
    ERROR_INVALID_BASE64_CONTENT_IN_BYTE_ARRAY_LITERAL("BCE0636", "error.invalid.base64.content.in.byte.array.literal"),
    ERROR_INVALID_CONTENT_IN_BYTE_ARRAY_LITERAL("BCE0637", "error.invalid.content.in.byte.array.literal"),
    ERROR_INVALID_EXPRESSION_STATEMENT("BCE0638", "error.invalid.expression.statement"),
    ERROR_INVALID_ARRAY_LENGTH("BCE0639", "error.invalid.array.length"),
    ERROR_SELECT_CLAUSE_IN_QUERY_ACTION("BCE0640", "error.select.clause.in.query.action"),
    ERROR_MORE_CLAUSES_AFTER_SELECT_CLAUSE("BCE0641", "error.more.clauses.after.select.clause"),
    ERROR_QUERY_CONSTRUCT_TYPE_IN_QUERY_ACTION("BCE0642", "error.query.construct.type.in.query.action"),
    ERROR_NO_WHITESPACES_ALLOWED_IN_RIGHT_SHIFT_OP("BCE0643", "error.no.whitespaces.allowed.in.right.shift.op"),
    ERROR_NO_WHITESPACES_ALLOWED_IN_UNSIGNED_RIGHT_SHIFT_OP("BCE0644",
            "error.no.whitespaces.allowed.in.unsigned.right.shift.op"),
    ERROR_INVALID_WHITESPACE_IN_SLASH_LT_TOKEN("BCE0645", "error.invalid.whitespace.in.slash.lt.token"),
    ERROR_LOCAL_TYPE_DEFINITION_NOT_ALLOWED("BCE0646", "error.local.type.definition.not.allowed"),
    ERROR_LEADING_ZEROS_IN_NUMERIC_LITERALS("BCE0647", "error.leading.zeros.in.numeric.literals"),
    ERROR_INVALID_STRING_NUMERIC_ESCAPE_SEQUENCE("BCE0648", "error.invalid.string.numeric.escape.sequence"),
    ERROR_INVALID_ESCAPE_SEQUENCE("BCE0649", "error.invalid.escape.sequence"),
    ERROR_INVALID_WHITESPACE_BEFORE("BCE0650", "error.invalid.whitespace.before"),
    ERROR_INVALID_WHITESPACE_AFTER("BCE0651", "error.invalid.whitespace.after"),
    ERROR_INVALID_XML_NAME("BCE0652", "error.invalid.xml.name"),
    ERROR_INVALID_CHARACTER_IN_XML_ATTRIBUTE_VALUE("BCE0653", "error.invalid.character.in.xml.attribute.value"),
    ERROR_INVALID_ENTITY_REFERENCE_NAME_START("BCE0654", "error.invalid.entity.reference.name.start"),
    ERROR_DOUBLE_HYPHEN_NOT_ALLOWED_WITHIN_XML_COMMENT("BCE0655", "error.double.hyphen.not.allowed.within.xml.comment"),
    ERROR_VERSION_IN_IMPORT_DECLARATION_NO_LONGER_SUPPORTED("BCE0656",
            "error.version.in.import.declaration.no.longer.supported"),
    ERROR_MORE_THAN_ONE_OBJECT_NETWORK_QUALIFIERS("BCE657", "error.more.than.one.object.network.qualifiers"),
    ERROR_REMOTE_METHOD_HAS_A_VISIBILITY_QUALIFIER("BCE658", "error.remote.method.has.a.visibility.qualifier"),
    ERROR_PRIVATE_QUALIFIER_IN_OBJECT_MEMBER_DESCRIPTOR("BCE659",
            "error.private.qualifier.in.object.member.descriptor"),
    ERROR_RESOURCE_PATH_IN_FUNCTION_DEFINITION("BCE660", "error.resource.path.in.function.definition"),
    ERROR_REST_PARAM_MUST_BE_THE_LAST_SEGMENT_OF_RESOURCE_PATH("BCE0661",
            "error.rest.param.must.be.the.last.segment.of.resource.path"),
    ERROR_REST_ARG_IN_ERROR_CONSTRUCTOR("BCE0662", "error.rest.arg.in.error.constructor"),
    ERROR_ADDITIONAL_POSITIONAL_ARG_IN_ERROR_CONSTRUCTOR("BCE0663",
            "error.additional.positional.arg.in.error.constructor"),
    ERROR_DEFAULTABLE_PARAMETER_CANNOT_BE_INCLUDED_RECORD_PARAMETER("BCE664",
            "error.defaultable.parameter.cannot.be.included.record.parameter"),
    ERROR_INCOMPLETE_QUOTED_IDENTIFIER("BCE665", "error.incomplete.quoted.identifier"),
    ERROR_OPEN_RECORD_CAN_NOT_CONTAIN_RECORD_REST_DESCRIPTOR("BCE666",
            "error.open.record.can.not.contain.record.rest.descriptor")
    ;

    String diagnosticId;
    String messageKey;

    DiagnosticErrorCode(String diagnosticId, String messageKey) {
        this.diagnosticId = diagnosticId;
        this.messageKey = messageKey;
    }

    @Override
    public DiagnosticSeverity severity() {
        return DiagnosticSeverity.ERROR;
    }

    @Override
    public String diagnosticId() {
        return diagnosticId;
    }

    @Override
    public String messageKey() {
        return messageKey;
    }

    public boolean equals(DiagnosticCode code) {
        return this.messageKey.equals(code.messageKey());
    }
}
