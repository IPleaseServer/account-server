package site.iplease.accountserver.domain.login.util

import site.iplease.accountserver.domain.login.util.atomic.RefreshTokenDecoder
import site.iplease.accountserver.domain.login.util.atomic.RefreshTokenEncoder
import site.iplease.accountserver.domain.login.util.atomic.RefreshTokenRemover

interface RefreshTokenUtil: RefreshTokenDecoder, RefreshTokenEncoder, RefreshTokenRemover {
}