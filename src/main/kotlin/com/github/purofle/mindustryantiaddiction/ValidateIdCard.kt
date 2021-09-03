package com.github.purofle.mindustryantiaddiction


object ValidateIdCard {
    fun validate(id: String): Boolean {
        if (id.length != 18) return false
        val regularExpression =
            "(^[1-9]\\d{5}(18|19|20|21)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" + "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)"
        if (id.substring(6..9).toInt() <= 1921) return false // 你他妈能活到100岁?
        val matches = id.matches(regularExpression.toRegex())
        if (!matches) return false
        val charArray = id.toCharArray()
        //前十七位加权因子
        val idCardWi = intArrayOf(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2)
        //这是除以11后，可能产生的11位余数对应的验证码
        val idCardY = arrayOf("1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2")
        var sum = 0
        for (i in idCardWi.indices) {
            val current = Integer.parseInt(charArray[i].toString())
            val count = current * idCardWi[i]
            sum += count
        }
        val idCardLast = charArray[17]
        val idCardMod = sum % 11
        return idCardY[idCardMod].equals(idCardLast.toString(), ignoreCase = true)
    }
}