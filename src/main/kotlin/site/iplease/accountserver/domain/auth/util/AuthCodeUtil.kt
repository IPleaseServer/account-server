package site.iplease.accountserver.domain.auth.util

import site.iplease.accountserver.domain.auth.util.atomic.AuthCodeGenerator
import site.iplease.accountserver.domain.auth.util.atomic.AuthCodeValidator

interface AuthCodeUtil: AuthCodeGenerator, AuthCodeValidator {
}
