package site.iplease.accountserver.domain.login.util

import site.iplease.accountserver.domain.login.util.atomic.AccessTokenEncoder
import site.iplease.accountserver.global.login.util.atomic.AccessTokenDecoder

interface AccessTokenUtil: AccessTokenEncoder, AccessTokenDecoder