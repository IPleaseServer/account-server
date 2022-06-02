package site.iplease.accountserver.global.common.exception

import site.iplease.accountserver.global.common.type.PolicyType

class PolicyViolationException(policy: PolicyType, msg: String): RuntimeException("${policy.displayName} 정책을 위반하였습니다! - $msg")