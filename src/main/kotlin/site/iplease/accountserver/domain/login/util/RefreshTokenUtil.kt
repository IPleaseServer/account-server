package site.iplease.accountserver.domain.login.util

import site.iplease.accountserver.domain.login.util.atomic.RefreshTokenDecoder
import site.iplease.accountserver.domain.login.util.atomic.RefreshTokenEncoder

interface RefreshTokenUtil: RefreshTokenDecoder, RefreshTokenEncoder {
}