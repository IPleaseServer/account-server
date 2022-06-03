package site.iplease.accountserver.domain.login.util

import site.iplease.accountserver.domain.login.util.atomic.AccessTokenDecoder
import site.iplease.accountserver.domain.login.util.atomic.AccessTokenEncoder

interface AccessTokenUtil: AccessTokenEncoder, AccessTokenDecoder