package site.iplease.accountserver.domain.auth.util

import site.iplease.accountserver.global.auth.util.atomic.AuthTokenDecoder
import site.iplease.accountserver.domain.auth.util.atomic.AuthTokenEncoder

interface AuthTokenUtil: AuthTokenEncoder, AuthTokenDecoder