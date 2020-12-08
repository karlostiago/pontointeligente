package com.ctsousa.pontointeligente.exception

import java.time.Instant

class GlobalErrorException (val code: Int, val message: String, var timestamp: String? = null)