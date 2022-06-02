package site.iplease.accountserver.domain.register.exception

import site.iplease.accountserver.domain.register.data.type.PolicyType

class PolicyViolationException(policy: PolicyType, msg: String): RuntimeException("${policy.displayName} 정책을 위반하였습니다! - $msg")